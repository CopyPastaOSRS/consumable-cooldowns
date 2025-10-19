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


import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.FontType;
import net.runelite.client.config.Range;

@ConfigGroup(ConsumableCooldownsConfig.GROUP_NAME)
public interface ConsumableCooldownsConfig extends Config
{
	String GROUP_NAME = "consumablecooldowns";

	@ConfigSection(
		name = "Item cooldown text",
		description = "Options for the consumable item cooldown text",
		position = 99
	)
	String itemCooldownTextSection = "itemCooldownText";

	@ConfigSection(
		name = "Item cooldown indicator",
		description = "Options for the consumable item cooldown indicator",
		position = 100
	)
	String itemCooldownIndicatorSection = "itemCooldownIndicator";

	@ConfigSection(
		name = "Delayed heal infobox",
		description = "Options for the delayed heal infobox",
		position = 101
	)
	String delayedHealInfoboxSection = "delayedHealInfobox";

	@ConfigSection(
		name = "Bottom to top indicator",
		description = "Options for the bottom to top consumable item cooldown indicator",
		closedByDefault = true,
		position = 102
	)
	String itemBottomToTopCooldownIndicatorSection = "itemBottomToTopCooldownIndicator";

	@ConfigItem(
		position = 0,
		keyName = "showItemCooldownPreview",
		name = "Show item cooldown preview",
		description = "Display a preview of the item cooldown indicator and text on all consumable items for configuration purposes"
	)
	default boolean showItemCooldownPreview()
	{
		return false;
	}

	@ConfigItem(
		position = 1,
		keyName = "showDelayedHealInfobox",
		name = "Show delayed heal infobox",
		description = "Show an infobox with the time remaining until the delayed heal from consumables such as cooked moonlight antelope"
	)
	default boolean showDelayedHealInfobox()
	{
		return true;
	}

	@ConfigItem(
		position = 2,
		keyName = "cooldownTextMode",
		name = "Mode",
		description = "Mode used for the cooldown text that displays the duration a consumable item is on cooldown",
		section = itemCooldownTextSection
	)
	default CooldownTextMode cooldownTextMode()
	{
		return CooldownTextMode.GAME_TICKS;
	}

	@ConfigItem(
		position = 3,
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
		position = 4,
		keyName = "textShadowColor",
		name = "Text shadow color",
		description = "Color of consumable item cooldown text shadow",
		section = itemCooldownTextSection
	)
	default Color getTextShadowColor()
	{
		return Color.BLACK;
	}

	@ConfigItem(
		position = 5,
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
		position = 6,
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
		position = 7,
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
		position = 8,
		keyName = "cooldownIndicatorMode",
		name = "Mode",
		description = "Indicator mode which should be used to display that a consumable item is on cooldown",
		section = itemCooldownIndicatorSection
	)
	default CooldownIndicatorMode getCooldownIndicatorMode()
	{
		return CooldownIndicatorMode.BOTTOM_TO_TOP;
	}

	@ConfigItem(
		position = 9,
		keyName = "itemCooldownIndicatorFillColor",
		name = "Fill color",
		description = "Color of item cooldown indicator fill",
		section = itemCooldownIndicatorSection
	)
	default Color getItemCooldownIndicatorFillColor()
	{
		return new Color(26, 26, 26);
	}

	@Range(max = 100)
	@ConfigItem(
		position = 10,
		keyName = "itemCooldownIndicatorFillOpacity",
		name = "Fill opacity",
		description = "Opacity of item cooldown indicator fill color when on cooldown. From 0 to 100",
		section = itemCooldownIndicatorSection
	)
	default int getItemCooldownIndicatorFillOpacity()
	{
		return 80;
	}

	@ConfigItem(
		position = 11,
		keyName = "infoboxCooldownTextMode",
		name = "Text mode",
		description = "Mode used for the cooldown text that displays the time remaining until the delayed heal from consumables such as cooked moonlight antelope",
		section = delayedHealInfoboxSection
	)
	default InfoBoxTextMode infoboxCooldownTextMode()
	{
		return InfoBoxTextMode.GAME_TICKS;
	}

	@ConfigItem(
		position = 12,
		keyName = "bottomToTopFullFillDuration",
		name = "Full fill duration",
		description = "The duration the cooldown indicator fill fully covers the item icon at the start of a cooldown. " +
			"This can make the cooldown easier to notice. Only works when using the bottom to top cooldown indicator",
		section = itemBottomToTopCooldownIndicatorSection
	)
	default BottomToTopCooldownIndicatorStartDelay getBottomToTopFullFillDuration()
	{
		return BottomToTopCooldownIndicatorStartDelay.NORMAL;
	}
}
