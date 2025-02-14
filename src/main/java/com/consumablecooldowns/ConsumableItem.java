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

import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.Constants;

/**
 * Item that incurs a delay when consumed
 */
@Getter(AccessLevel.MODULE)
public class ConsumableItem
{
	private final ConsumableItemType type;
	private final int actionCooldownTicks;
	private final int eatCooldownTicks;
	private final int comboEatCooldownTicks;
	private final int drinkCooldownTicks;
	private final int delayedHealTicks;
	private final Predicate<Integer> itemFilter;

	public ConsumableItem(ConsumableItemType type, int actionCooldownTicks, int eatCooldownTicks, int delayedHealTicks, Predicate<Integer> itemFilter)
	{
		this.type = type;
		this.actionCooldownTicks = actionCooldownTicks;
		this.eatCooldownTicks = eatCooldownTicks;
		this.comboEatCooldownTicks = 0;
		this.drinkCooldownTicks = 0;
		this.delayedHealTicks = delayedHealTicks;
		this.itemFilter = itemFilter;
	}

	public ConsumableItem(ConsumableItemType type, int actionCooldownTicks, int eatCooldownTicks, Predicate<Integer> itemFilter)
	{
		this.type = type;
		this.actionCooldownTicks = actionCooldownTicks;
		this.eatCooldownTicks = eatCooldownTicks;
		this.comboEatCooldownTicks = 0;
		this.drinkCooldownTicks = 0;
		this.delayedHealTicks = 0;
		this.itemFilter = itemFilter;
	}

	public ConsumableItem(ConsumableItemType type, int actionCooldownTicks, int eatCooldownTicks, int comboEatCooldownTicks, int drinkCooldownTicks, Predicate<Integer> itemFilter)
	{
		this.type = type;
		this.actionCooldownTicks = actionCooldownTicks;
		this.eatCooldownTicks = eatCooldownTicks;
		this.comboEatCooldownTicks = comboEatCooldownTicks;
		this.drinkCooldownTicks = drinkCooldownTicks;
		this.delayedHealTicks = 0;
		this.itemFilter = itemFilter;
	}

	public ConsumableItemCooldown getFullCooldown()
	{
		switch (type)
		{
			case FOOD:
			case COOKED_CRAB_MEAT:
			case CAKE:
			case F2P_FIRST_SLICE:
			case F2P_SECOND_SLICE:
			case P2P_PIE:
			case OVERTIME_FOOD:
				return new ConsumableItemCooldown(eatCooldownTicks, cooldownTicksToClientTicks(eatCooldownTicks));
			case DRINK:
				return new ConsumableItemCooldown(drinkCooldownTicks, cooldownTicksToClientTicks(drinkCooldownTicks));
			case COMBO_FOOD:
				return new ConsumableItemCooldown(comboEatCooldownTicks, cooldownTicksToClientTicks(comboEatCooldownTicks));
		}

		return null;
	}

	public ConsumableItemCooldown getFullTimeUntilDelayedHeal()
	{
		if (type != ConsumableItemType.OVERTIME_FOOD)
		{
			return null;
		}

		return new ConsumableItemCooldown(delayedHealTicks, cooldownTicksToClientTicks(delayedHealTicks));
	}

	/**
	 * Converts game ticks to client ticks. The given tick count is decremented by 1 to account that the first tick
	 * is already done when the client notices the item is consumed.
	 *
	 * @param tickCount consumable cooldown in game ticks
	 * @return client ticks count
	 */
	public int cooldownTicksToClientTicks(int tickCount)
	{
		return ((tickCount - 1) * Constants.GAME_TICK_LENGTH) / Constants.CLIENT_TICK_LENGTH;
	}
}