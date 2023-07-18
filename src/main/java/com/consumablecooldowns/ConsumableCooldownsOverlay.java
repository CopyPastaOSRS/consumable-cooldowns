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
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
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

		ConsumableItemCooldown fullCooldown = consumableItem.getFullCooldown();
		int elapsedCooldownClientTicks = fullCooldown.getClientTicks() - cooldownRemaining.getClientTicks();
		float percent = (float) elapsedCooldownClientTicks / fullCooldown.getClientTicks();
		int opacity = (int) (config.getItemCooldownIndicatorFillOpacity() * 2.55f);

		switch (config.cooldownIndicatorMode())
		{
			case FILL:
				renderCooldownFill(graphics, widgetItem, slotBounds, opacity);
				break;
			case BOTTOM_TO_TOP:
				renderCooldownBottomToTop(graphics, percent, widgetItem, slotBounds, opacity);
				break;
			case NONE:
				break;
		}

		switch (config.cooldownTextMode())
		{
			case GAME_TICKS:
				renderCooldownText(graphics, cooldownRemaining.toGameTicks(), slotBounds);
				break;
			case SECONDS_MILLISECONDS:
				renderCooldownText(graphics, cooldownRemaining.toSecondsMilliseconds(), slotBounds);
				break;
			case NONE:
				break;
		}
	}

	private void renderCooldownText(Graphics2D graphics, String delayText, Rectangle slotBounds)
	{
		if (delayText.equals("0.0"))
		{
			return;
		}

		FontMetrics fm = graphics.getFontMetrics();
		Rectangle2D textBounds = fm.getStringBounds(delayText, graphics);

		int textX = (int) (slotBounds.getX() + (slotBounds.getWidth() / 2) - (textBounds.getWidth() / 2)) - config.getTextXOffset();
		int textY = (int) (slotBounds.getY() + (slotBounds.getHeight() / 2) + (textBounds.getHeight() / 2)) - config.getTextYOffset();

		graphics.setColor(config.getTextShadowColor());
		graphics.drawString(delayText, textX + 1, textY + 1);
		graphics.setColor(config.getTextColor());
		graphics.drawString(delayText, textX, textY);
	}

	private void renderCooldownFill(Graphics2D graphics, WidgetItem widgetItem, Rectangle slotBounds, int opacity)
	{
		final Image image = getFillImage(config.getItemCooldownIndicatorFillColor(), opacity, widgetItem.getId(), widgetItem.getQuantity());
		graphics.drawImage(image, (int) slotBounds.getX(), (int) slotBounds.getY(), null);
	}

	private void renderCooldownBottomToTop(Graphics2D graphics, float percent, WidgetItem widgetItem, Rectangle slotBounds, int opacity)
	{
		int clipHeight = Math.min((int) slotBounds.getHeight(), (int) ((1 - percent) * slotBounds.getHeight()));
		final Image image = getFillImage(config.getItemCooldownIndicatorFillColor(), opacity, widgetItem.getId(), widgetItem.getQuantity());

		graphics.setClip((int) slotBounds.getX(), (int) slotBounds.getY(), (int) slotBounds.getWidth(), clipHeight);
		graphics.drawImage(image, (int) slotBounds.getX(), (int) slotBounds.getY(), null);
		graphics.setClip(slotBounds); // reset clip
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
}