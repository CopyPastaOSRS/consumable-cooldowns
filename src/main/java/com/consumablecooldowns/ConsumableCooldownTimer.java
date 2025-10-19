package com.consumablecooldowns;

import java.awt.Color;
import java.awt.image.BufferedImage;
import net.runelite.client.ui.overlay.infobox.InfoBox;

public class ConsumableCooldownTimer extends InfoBox
{
	private final ConsumableCooldownsPlugin plugin;
	private final ConsumableItemCooldown cooldown;

	ConsumableCooldownTimer(BufferedImage image, int ticks, int clientTicks, ConsumableCooldownsPlugin plugin)
	{
		super(image, plugin);
		this.plugin = plugin;
		this.cooldown = new ConsumableItemCooldown(ticks, clientTicks);
	}

	@Override
	public boolean render()
	{
		return cooldown.getTicks() > 0;
	}

	@Override
	public boolean cull()
	{
		return cooldown.getTicks() < 1;
	}

	@Override
	public String getText()
	{
		if (cooldown.getTicks() < 1)
		{
			return null;
		}

		switch (plugin.config.infoboxCooldownTextMode())
		{
			case GAME_TICKS:
				String tickText = cooldown.getTicks() == 1 ? " tick" : " ticks";
				return cooldown.toGameTicks() + tickText;
			case GAME_TICKS_COMPACT:
				return cooldown.toGameTicks() + "t";
			case GAME_TICKS_NO_SUFFIX:
				return cooldown.toGameTicks();
			case SECONDS_MILLISECONDS:
				return cooldown.toSecondsMilliseconds();
			case MINUTES_SECONDS:
				return cooldown.toMinuteSeconds();
			case SECONDS:
				return cooldown.toSeconds();
			case NONE:
			default:
				return null;
		}
	}

	@Override
	public Color getTextColor()
	{
		return Color.WHITE;
	}

	public void updateCooldownTicks(int ticks)
	{
		cooldown.setTicks(ticks);
	}

	public void updateCooldownClientTicks(int clientTicks)
	{
		cooldown.setClientTicks(clientTicks);
	}
}
