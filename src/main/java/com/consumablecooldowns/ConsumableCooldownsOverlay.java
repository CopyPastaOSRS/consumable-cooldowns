/*
 * Copyright (c) 2023, Copy Pasta
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.consumablecooldowns;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.NullItemID;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.ImageUtil;

@Slf4j
public class ConsumableCooldownsOverlay extends WidgetItemOverlay
{
	private final ConsumableCooldownsPlugin plugin;
	private final ConsumableCooldownsConfig config;
	private final ItemManager itemManager;
	private final Cache<Long, Image> fillCache;
	private final Cache<Long, InventoryIconInfo> inventoryIconInfoCache;

	@Inject
	public ConsumableCooldownsOverlay(ItemManager itemManager, ConsumableCooldownsPlugin plugin,
									  ConsumableCooldownsConfig config)
	{
		this.itemManager = itemManager;
		this.plugin = plugin;
		this.config = config;
		fillCache = CacheBuilder.newBuilder()
			.concurrencyLevel(1)
			.maximumSize(32)
			.build();
		inventoryIconInfoCache = CacheBuilder.newBuilder()
			.concurrencyLevel(1)
			.maximumSize(32)
			.build();

		showOnInventory();
	}

	@Override
	public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem)
	{
		renderConsumableCooldowns(graphics, widgetItem);
	}

	void invalidateFillCache()
	{
		fillCache.invalidateAll();
	}

	void invalidateInventoryIconInfoCache()
	{
		inventoryIconInfoCache.invalidateAll();
	}

	private void renderConsumableCooldowns(Graphics2D graphics, WidgetItem widgetItem)
	{
		if (plugin.isNoConsumableCooldownActive())
		{
			return;
		}

		Rectangle slotBounds = widgetItem.getCanvasBounds();
		int itemId = widgetItem.getId();

		// Empty inventory item
		if (itemId == NullItemID.NULL_6512)
		{
			return;
		}

		ConsumableItem consumableItem = plugin.getConsumableItemFromId(itemId);
		if (consumableItem == null)
		{
			return;
		}

		ConsumableItemCooldown cooldownRemaining = plugin.getCooldownForConsumableItem(consumableItem);
		if (cooldownRemaining == null)
		{
			return;
		}

		int opacity = (int) (config.getItemCooldownIndicatorFillOpacity() * 2.55f);
		switch (config.getCooldownIndicatorMode())
		{
			case FILL:
				renderCooldownFill(graphics, widgetItem, slotBounds, opacity);
				break;
			case BOTTOM_TO_TOP:
				renderCooldownBottomToTop(
					graphics,
					getCooldownPercent(consumableItem, cooldownRemaining),
					widgetItem,
					slotBounds,
					opacity
				);
				break;
			case PIE:
				renderCooldownPie(
					graphics,
					getCooldownPercent(consumableItem, cooldownRemaining),
					widgetItem,
					slotBounds,
					opacity
				);
				break;
			case NONE:
				break;
		}
	}

	private float getCooldownPercent(ConsumableItem consumableItem, ConsumableItemCooldown cooldownRemaining)
	{
		ConsumableItemCooldown fullCooldown = plugin.getLastCooldownSourceByType(consumableItem.getType());
		if (fullCooldown == null)
		{
			log.warn("No full cooldown found for consumable item type: {}", consumableItem.getType());
			return 1;
		}

		int elapsedCooldownClientTicks = fullCooldown.getClientTicks() - cooldownRemaining.getClientTicks();
		return (float) elapsedCooldownClientTicks / fullCooldown.getClientTicks();
	}

	private void renderCooldownFill(Graphics2D graphics, WidgetItem widgetItem, Rectangle slotBounds, int opacity)
	{
		final Image image = getFillImage(config.getItemCooldownIndicatorFillColor(), opacity, widgetItem.getId(), widgetItem.getQuantity());
		graphics.drawImage(image, (int) slotBounds.getX(), (int) slotBounds.getY(), null);
	}

	private void renderCooldownBottomToTop(Graphics2D graphics, float percent, WidgetItem widgetItem, Rectangle slotBounds, int opacity)
	{
		int itemId = widgetItem.getId();
		int quantity = widgetItem.getQuantity();

		final Image image = getFillImage(config.getItemCooldownIndicatorFillColor(), opacity, itemId, quantity);
		final InventoryIconInfo inventoryIconInfo = getInventoryIconInfo(itemId, quantity, image);

		int clipHeight;
		Rectangle indicatorBoundingBox;

		// If the item has no icon, or the icon is too small, use the whole slot
		if (inventoryIconInfo.width < 0 || inventoryIconInfo.height < 5)
		{
			clipHeight = Math.min((int) slotBounds.getHeight(), (int) ((1 - percent) * slotBounds.getHeight()));
			indicatorBoundingBox = slotBounds;
		}
		else
		{
			int itemIconHeight = inventoryIconInfo.height + config.getBottomToTopFullFillDuration().ordinal();
			clipHeight = Math.min(itemIconHeight, (int) ((1 - percent) * itemIconHeight));
			indicatorBoundingBox = new Rectangle(
				(int) slotBounds.getX(),
				(int) slotBounds.getY() + inventoryIconInfo.minY,
				inventoryIconInfo.width,
				itemIconHeight
			);
		}

		graphics.setClip((int) slotBounds.getX(), (int) indicatorBoundingBox.getY(), (int) slotBounds.getWidth(), clipHeight);
		graphics.drawImage(image, (int) slotBounds.getX(), (int) slotBounds.getY(), null);
		graphics.setClip(slotBounds); // reset clip
	}

	private void renderCooldownPie(
		Graphics2D graphics,
		float percent,
		WidgetItem widgetItem,
		Rectangle slotBounds,
		int opacity
	)
	{
		int itemId = widgetItem.getId();
		int quantity = widgetItem.getQuantity();

		final Image inventoryItemImage = getFillImage(config.getItemCooldownIndicatorFillColor(), opacity, itemId, quantity);
		final InventoryIconInfo inventoryIconInfo = getInventoryIconInfo(itemId, quantity, inventoryItemImage);

		int halfLength = (int) Math.ceil(inventoryIconInfo.visibleRadius);
		int length = halfLength * 2;
		Rectangle outerFrame = new Rectangle(
			slotBounds.x + (int) (inventoryIconInfo.centroid.getX() - halfLength),
			slotBounds.y + (int) (inventoryIconInfo.centroid.getY() - halfLength),
			length,
			length
		);

		Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
		arc.setAngleStart(90); // start at top
		arc.setAngleExtent((1 - percent) * 360); // percentage of pie to draw
		arc.setFrame(outerFrame);

		graphics.setClip(arc);
		graphics.drawImage(inventoryItemImage, (int) slotBounds.getX(), (int) slotBounds.getY(), null);
		graphics.setClip(slotBounds); // reset clip
	}

	private InventoryIconInfo getInventoryIconInfo(int itemId, int quantity, Image image)
	{
		long key = (((long) itemId) << 32) | quantity;
		InventoryIconInfo inventoryIconInfo = inventoryIconInfoCache.getIfPresent(key);
		if (inventoryIconInfo != null)
		{
			return inventoryIconInfo;
		}

		inventoryIconInfo = getInventoryIconInfoFromImage((BufferedImage) image);
		inventoryIconInfoCache.put(key, inventoryIconInfo);
		return inventoryIconInfo;
	}

	private Image getFillImage(Color color, int opacity, int itemId, int qty)
	{
		long key = (((long) itemId) << 32) | qty;
		Image image = fillCache.getIfPresent(key);
		if (image == null)
		{
			final Color fillColor = ColorUtil.colorWithAlpha(color, opacity);
			image = ImageUtil.fillImage(itemManager.getImage(itemId, qty, false), fillColor);
			fillCache.put(key, image);
		}
		return image;
	}

	private static double getImageVisibleRadius(final BufferedImage image, Point2D center)
	{
		double radius = 0;
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = 0; y < image.getHeight(); y++)
			{
				int pixel = image.getRGB(x, y);
				int pixelAlpha = pixel >>> 24;
				if (pixelAlpha == 0)
				{
					continue;
				}

				radius = Math.max(radius, center.distanceSq(x, y));
			}
		}

		return Math.sqrt(radius);
	}

	private InventoryIconInfo getInventoryIconInfoFromImage(BufferedImage inventoryIconImage)
	{
		int minX = inventoryIconImage.getWidth(), minY = inventoryIconImage.getHeight(), maxX = 0, maxY = 0;

		int pixelCount = 0;
		double centroidX = 0;
		double centroidY = 0;

		for (int y = 0; y < (inventoryIconImage.getHeight() - 1); y++)
		{
			for (int x = 0; x < (inventoryIconImage.getWidth() - 1); x++)
			{
				int pixel = inventoryIconImage.getRGB(x, y);
				int pixelAlpha = pixel >>> 24;
				if (pixelAlpha == 0)
				{
					continue;
				}

				minX = Math.min(minX, x);
				minY = Math.min(minY, y);
				maxX = Math.max(maxX, x);
				maxY = Math.max(maxY, y);

				if (config.getCooldownIndicatorMode() == CooldownIndicatorMode.PIE)
				{
					centroidX += x;
					centroidY += y;
					pixelCount++;
				}
			}
		}

		if (config.getCooldownIndicatorMode() == CooldownIndicatorMode.PIE)
		{
			Point2D centroid = new Point2D.Double(centroidX / pixelCount, centroidY / pixelCount);
			double visibleRadius = getImageVisibleRadius(inventoryIconImage, centroid);
			return new InventoryIconInfo(minX, minY, maxX - minX, maxY - minY, centroid, visibleRadius);
		}

		return new InventoryIconInfo(minX, minY, maxX - minX, maxY - minY);
	}
}