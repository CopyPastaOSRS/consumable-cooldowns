package com.consumablecooldowns;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CooldownTextMode
{
	SECONDS_MILLISECONDS("Seconds & ms"),
	GAME_TICKS("Game ticks"),
	NONE("None");

	private final String value;

	public String toString()
	{
		return this.value;
	}
}
