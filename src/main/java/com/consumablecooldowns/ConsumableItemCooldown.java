package com.consumablecooldowns;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ConsumableItemCooldown
{
	private final int ticks;
	private final int clientTicks;

	String toGameTicks()
	{
		return String.valueOf(ticks);
	}

	String toSecondsMilliseconds()
	{
		return Math.abs((clientTicks / 5) / 10) + "." + (Math.abs((clientTicks / 5)) % 10);
	}
}
