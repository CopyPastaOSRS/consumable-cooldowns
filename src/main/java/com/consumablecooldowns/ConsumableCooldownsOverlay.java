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
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
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
import java.awt.geom.Rectangle2D;

@Slf4j
public class ConsumableCooldownsOverlay extends WidgetItemOverlay
{
	private final String ITEM_COOLDOWN_PREVIEW_DELAY_TEXT = "2";

	private final Client client;
	private final ConsumableCooldownsPlugin plugin;
	private final ConsumableCooldownsConfig config;
	private final ItemManager itemManager;
	private final Cache<Long, Image> fillCache;

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

		String delayText = plugin.getDelayTextForConsumableItem(consumableItem);
		if (delayText == null)
		{
			return;
		}

		renderCooldownFill(graphics, widgetItem, slotBounds);
		renderCooldownText(graphics, delayText, slotBounds);
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

		final Image image = getFillImage(config.getItemFillColor(), widgetItem.getId(), widgetItem.getQuantity());
		graphics.drawImage(image, (int) slotBounds.getX(), (int) slotBounds.getY(), null);
	}

	private Image getFillImage(Color color, int itemId, int qty)
	{
		long key = (((long) itemId) << 32) | qty;
		Image image = fillCache.getIfPresent(key);
		if (image == null)
		{
			final Color fillColor = ColorUtil.colorWithAlpha(color, config.getItemFillOpacity());
			image = ImageUtil.fillImage(itemManager.getImage(itemId, qty, false), fillColor);
			fillCache.put(key, image);
		}
		return image;
	}
}