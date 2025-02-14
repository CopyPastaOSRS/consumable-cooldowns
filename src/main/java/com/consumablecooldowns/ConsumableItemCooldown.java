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
}
