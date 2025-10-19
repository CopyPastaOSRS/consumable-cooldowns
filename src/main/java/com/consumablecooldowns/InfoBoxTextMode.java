package com.consumablecooldowns;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum InfoBoxTextMode
{
	SECONDS_MILLISECONDS("Seconds & ms"),
	MINUTES_SECONDS("Minutes & seconds"),
	SECONDS("Seconds"),
	GAME_TICKS("Ticks"),
	GAME_TICKS_COMPACT("Ticks (compact)"),
	GAME_TICKS_NO_SUFFIX("Ticks (no suffix)"),
	NONE("None");

	private final String value;

	public String toString()
	{
		return this.value;
	}
}
