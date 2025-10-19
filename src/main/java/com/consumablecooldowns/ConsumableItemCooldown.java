package com.consumablecooldowns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConsumableItemCooldown
{
	private int ticks;
	private int clientTicks;

	String toGameTicks()
	{
		return String.valueOf(ticks);
	}

	String toSecondsMilliseconds()
	{
		return Math.abs((clientTicks / 5) / 10) + "." + (Math.abs((clientTicks / 5)) % 10);
	}

	String toSeconds()
	{
		return String.valueOf(Math.abs((clientTicks / 5) / 10));
	}

	String toMinuteSeconds()
	{
		int seconds = Math.abs((clientTicks / 5) / 10);
		if (seconds < 10)
		{
			return "0:0" + seconds;
		}
		else if (seconds < 60)
		{
			return "0:" + seconds;
		}
		else
		{
			return (seconds / 60) + ":" + (seconds % 60);
		}
	}
}
