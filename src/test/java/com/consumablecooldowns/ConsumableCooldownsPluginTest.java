package com.consumablecooldowns;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ConsumableCooldownsPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ConsumableCooldownsPlugin.class);
		RuneLite.main(args);
	}
}