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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Constants;
import net.runelite.api.NullItemID;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.ImageUtil;

import javax.inject.Inject;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@Slf4j
public class ConsumableCooldownsOverlay extends WidgetItemOverlay
{
	private static final boolean DEBUG = false;
	private static final String ITEM_COOLDOWN_PREVIEW_DELAY_TEXT = "2";

	private final Client client;
	private final ConsumableCooldownsPlugin plugin;
	private final ConsumableCooldownsConfig config;
	private final ItemManager itemManager;
	private final Cache<Long, ItemImage> fillCache;

	@Inject
	public ConsumableCooldownsOverlay(Client client, ItemManager itemManager, ConsumableCooldownsPlugin plugin,
									  ConsumableCooldownsConfig config)
	{
		this.itemManager = itemManager;
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		fillCache = CacheBuilder.newBuilder()
				.concurrencyLevel(1)
				.maximumSize(32)
				.build();

		showOnInventory();
	}

	@Override
	public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem)
	{
		graphics.setFont(config.getFontType().getFont());
		renderConsumableCooldowns(graphics, widgetItem);
		if (DEBUG) {
			renderDebug(graphics, widgetItem);
		}
	}

	void invalidateCache()
	{
		fillCache.invalidateAll();
	}

	private void renderConsumableCooldowns(Graphics2D graphics, WidgetItem widgetItem)
	{
		Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);
		if (inventoryWidget == null || inventoryWidget.isHidden())
		{
			return;
		}

		if (plugin.isNoConsumableCooldownActive() && !config.showItemCooldownPreview())
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

		if (config.showItemCooldownPreview())
		{
			renderCooldownFill(graphics, widgetItem, slotBounds);
			renderCooldownText(graphics, ITEM_COOLDOWN_PREVIEW_DELAY_TEXT, slotBounds);
			return;
		}

		Integer delay = plugin.getDelayForConsumableItem(consumableItem);
		if (delay == null)
		{
			return;
		}

		renderCooldownFill(graphics, widgetItem, slotBounds);
		renderCooldownPie(graphics, consumableItem, widgetItem, delay, slotBounds);
		renderCooldownText(graphics, String.valueOf(delay), slotBounds);
	}

	private void renderDebug(Graphics2D graphics, WidgetItem widgetItem)
	{
		Rectangle slotBounds = widgetItem.getCanvasBounds();

		final ItemImage itemImage = getFillImage(config.getItemFillColor(), 0, widgetItem.getId(), widgetItem.getQuantity());
		final Point2D centroid = itemImage.visibleCentroid;

		int halfLength = (int) Math.ceil(itemImage.visibleRadius);
		int length = halfLength * 2;
		graphics.setColor(Color.BLUE);
		int ovalX = slotBounds.x + (int) (centroid.getX() - halfLength);
		int ovalY = slotBounds.y + (int) (centroid.getY() - halfLength);
		graphics.drawOval(ovalX, ovalY, length, length);

		int pixelX = slotBounds.x + (int) centroid.getX();
		int pixelY = slotBounds.y + (int) centroid.getY();
		graphics.setColor(Color.RED);
		graphics.drawLine(pixelX, pixelY, pixelX, pixelY);
	}

	private void renderCooldownText(Graphics2D graphics, String delayText, Rectangle slotBounds)
	{
		if (!config.showItemCooldownText())
		{
			return;
		}

		FontMetrics fm = graphics.getFontMetrics();
		Rectangle2D textBounds = fm.getStringBounds(delayText, graphics);

		int textX = (int) (slotBounds.getX() + (slotBounds.getWidth() / 2) - (textBounds.getWidth() / 2)) - config.getTextXOffset();
		int textY = (int) (slotBounds.getY() + (slotBounds.getHeight() / 2) + (textBounds.getHeight() / 2))  - config.getTextYOffset();

		graphics.setColor(Color.BLACK);
		graphics.drawString(delayText, textX + 1, textY + 1);
		graphics.setColor(config.getTextColor());
		graphics.drawString(delayText, textX, textY);
	}

	private void renderCooldownFill(Graphics2D graphics, WidgetItem widgetItem, Rectangle slotBounds)
	{
		if (!config.showItemCooldownFill())
		{
			return;
		}

		ItemImage itemImage = getFillImage(config.getItemFillColor(), config.getItemFillOpacity(), widgetItem.getId(), widgetItem.getQuantity());
		graphics.drawImage(itemImage.image, (int) slotBounds.getX(), (int) slotBounds.getY(), null);
	}

	private void renderCooldownPie(
			Graphics2D graphics,
			ConsumableItem consumableItem,
			WidgetItem widgetItem,
			int delay,
			Rectangle slotBounds
	)
	{
		if (!config.showItemCooldownPie())
		{
			return;
		}

		// todo I don't think this is very tick accurate, you may be able to consume while the pie is still up
		Instant deadline = plugin.getPreviousTickInstant().plusMillis((long) Constants.GAME_TICK_LENGTH * delay);
		Instant clickInstant = plugin.getPreviousInteractionInstantForConsumableItem(consumableItem);
		long elapsedMillis = Duration.between(Instant.now(), clickInstant).toMillis();
		long delayDurationMillis = Duration.between(deadline, clickInstant).toMillis();
		float percent = (float) elapsedMillis / delayDurationMillis;

		int opacity = (int) (config.getItemPieOpacity() * 2.55f);
		final ItemImage itemImage = getFillImage(Color.BLACK, opacity, widgetItem.getId(), widgetItem.getQuantity());
		final Point2D centroid = itemImage.visibleCentroid;

		int halfLength = (int) Math.ceil(itemImage.visibleRadius);
		int length = halfLength * 2;
		Rectangle outerFrame = new Rectangle(
				slotBounds.x + (int) (centroid.getX() - halfLength),
				slotBounds.y + (int) (centroid.getY() - halfLength),
				length,
				length
		);

		Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
		arc.setAngleStart(90); // start at top
		arc.setAngleExtent((1 - percent) * 360); // percentage of pie to draw
		arc.setFrame(outerFrame);

		graphics.setClip(arc);
		graphics.drawImage(itemImage.image, (int) slotBounds.getX(), (int) slotBounds.getY(), null);
		graphics.setClip(slotBounds); // reset clip
	}

	private ItemImage getFillImage(Color color, int opacity, int itemId, int qty)
	{
		long key = Objects.hash(color, opacity, itemId, qty);
		ItemImage itemImage = fillCache.getIfPresent(key);
		if (itemImage == null)
		{
			final Color fillColor = ColorUtil.colorWithAlpha(color, opacity);
			BufferedImage originalImage = itemManager.getImage(itemId, qty, false);
			BufferedImage filledImage = ImageUtil.fillImage(originalImage, fillColor);
			Rectangle visibleRect = getVisibleImageRectangle(originalImage);
			Point2D centroid = getVisibleCentroid(originalImage);
			double visibleRadius = getVisibleRadius(originalImage, centroid);
			itemImage = new ItemImage(filledImage, visibleRect, centroid, visibleRadius);
			log.debug("visible measurement data for id {}: {}, centroid: {}, radius: {}",
					itemId, itemImage.visibleRectangle, itemImage.visibleCentroid, itemImage.visibleRadius);
			fillCache.put(key, itemImage);
		}
		return itemImage;
	}

	private static Rectangle getVisibleImageRectangle(final BufferedImage image)
	{
		int left = image.getWidth(), top = image.getHeight(), right = 0, bottom = 0;
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = 0; y < image.getHeight(); y++)
			{
				int pixel = image.getRGB(x, y);
				int a = pixel >>> 24;
				if (a == 0)
				{
					continue;
				}

				left = Math.min(left, x);
				top = Math.min(top, y);
				right = Math.max(right, x);
				bottom = Math.max(bottom, y);
			}
		}
		return new Rectangle(left, top, right - left, bottom - top);
	}

	private static Point2D getVisibleCentroid(final BufferedImage image)
	{
		int pixelCount = 0;
		double centroidX = 0;
        double centroidY = 0;
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = 0; y < image.getHeight(); y++)
			{
				int pixel = image.getRGB(x, y);
				int a = pixel >>> 24;
				if (a == 0)
				{
					continue;
				}

				centroidX += x;
				centroidY += y;
				pixelCount++;
			}
		}
		return new Point2D.Double(centroidX / pixelCount, centroidY / pixelCount);
	}

	private static double getVisibleRadius(final BufferedImage image, Point2D center)
	{
		double radius = 0;
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = 0; y < image.getHeight(); y++)
			{
				int pixel = image.getRGB(x, y);
				int a = pixel >>> 24;
				if (a == 0)
				{
					continue;
				}

				radius = Math.max(radius, center.distanceSq(x, y));
			}
		}
		return Math.sqrt(radius);
	}

	@AllArgsConstructor
	private static class ItemImage {
		private final BufferedImage image;
		private final Rectangle visibleRectangle;
        private final Point2D visibleCentroid;
		private final double visibleRadius;
	}
}
