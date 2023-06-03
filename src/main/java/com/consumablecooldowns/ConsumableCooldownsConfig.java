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

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup(ConsumableCooldownsConfig.GROUP_NAME)
public interface ConsumableCooldownsConfig extends Config
{
	String GROUP_NAME = "consumablecooldowns";

	@ConfigSection(
			name = "Item cooldown text",
			description = "Options for consumable item cooldown text",
			position = 99
	)
	String itemCooldownTextSection = "itemCooldownText";

	@ConfigSection(
			name = "Item cooldown fill",
			description = "Options for consumable item cooldown fill",
			position = 100
	)
	String itemCooldownFillSection = "itemCooldownFill";

	@ConfigSection(
			name = "Item cooldown pie",
			description = "Options for consumable item cooldown pie",
			position = 101
	)
	String itemCooldownPieSection = "itemCooldownPie";

	@ConfigItem(
			position = 0,
			keyName = "showItemCooldownPreview",
			name = "Show item cooldown preview",
			description = "Display a preview of the item cooldown fill and text on all consumable items for configuration purposes"
	)
	default boolean showItemCooldownPreview()
	{
		return false;
	}

	@ConfigItem(
			position = 1,
			keyName = "showItemCooldownText",
			name = "Show item cooldown text",
			description = "Whether or not cooldown text (in ticks) should be shown on consumable items when on cooldown",
			section = itemCooldownTextSection
	)
	default boolean showItemCooldownText()
	{
		return true;
	}

	@ConfigItem(
			position = 2,
			keyName = "textColor",
			name = "Text color",
			description = "Color of consumable item cooldown text",
			section = itemCooldownTextSection
	)
	default Color getTextColor()
	{
		return new Color(17, 255, 255);
	}

	@ConfigItem(
			position = 3,
			keyName = "fontType",
			name = "Text font type",
			description = "Font type used for consumable item cooldown text",
			section = itemCooldownTextSection
	)
	default FontType getFontType()
	{
		return FontType.BOLD;
	}

	@Range(min = -15, max = 20)
	@ConfigItem(
			position = 4,
			keyName = "textXOffset",
			name = "Text width offset",
			description = "X-axis offset for consumable item cooldown text position. Default value is in the center of the item",
			section = itemCooldownTextSection
	)
	default int getTextXOffset()
	{
		return 3;
	}

	@Range(min = -15, max = 15)
	@ConfigItem(
			position = 5,
			keyName = "textYOffset",
			name = "Text height offset",
			description = "Y-axis offset for consumable item cooldown text position. Default value is in the center of the item",
			section = itemCooldownTextSection
	)
	default int getTextYOffset()
	{
		return 0;
	}

	@ConfigItem(
			position = 6,
			keyName = "showItemCooldownFill",
			name = "Show item cooldown fill",
			description = "Whether or not the consumable item should be filled with a color when on cooldown",
			section = itemCooldownFillSection
	)
	default boolean showItemCooldownFill()
	{
		return true;
	}

	@ConfigItem(
			position = 7,
			keyName = "itemFillColor",
			name = "Fill color",
			description = "Color of item fill when on cooldown",
			section = itemCooldownFillSection
	)
	default Color getItemFillColor()
	{
		return new Color(36, 36, 36);
	}

	@ConfigItem(
			position = 8,
			keyName = "itemFillOpacity",
			name = "Fill opacity",
			description = "Opacity of item fill color when on cooldown",
			section = itemCooldownFillSection
	)
	default int getItemFillOpacity()
	{
		return 90;
	}

	@ConfigItem(
			position = 1,
			keyName = "showItemCooldownPie",
			name = "Show item cooldown pie",
			description = "Whether or not the cooldown pie should be shown on consumable items when on cooldown",
			section = itemCooldownPieSection
	)
	default boolean showItemCooldownPie()
	{
		return true;
	}

	@Range(max = 100)
	@ConfigItem(
			position = 8,
			keyName = "itemPieOpacity",
			name = "Pie opacity",
			description = "Opacity of item fill when on cooldown. From 0 to 100.",
			section = itemCooldownPieSection
	)
	default int getItemPieOpacity()
	{
		return 50;
	}

}
