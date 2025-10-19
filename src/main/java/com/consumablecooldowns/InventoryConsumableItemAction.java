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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.Item;
import net.runelite.api.ItemID;

@AllArgsConstructor
@Getter(AccessLevel.MODULE)
public class InventoryConsumableItemAction
{
	private final Item[] oldInventory;
	private final int itemId;
	private final int itemSlot;
	private final int actionTick;

	boolean isItemConsumed(Item[] newInventory)
	{
		Item oldItem = oldInventory[itemSlot];
		Item newItem = newInventory[itemSlot];
		boolean multiQuantityConsumable = isMultiQuantityInSameSlotConsumable(oldItem.getId());
		boolean isItemStillInInventorySlot = oldItem.getId() == newItem.getId();
		if (!isItemStillInInventorySlot && !multiQuantityConsumable)
		{
			return true;
		}

		if (!multiQuantityConsumable)
		{
			return false;
		}

		int quantityDifference = oldItem.getQuantity() - newItem.getQuantity();
		return quantityDifference > 0;
	}

	private boolean isMultiQuantityInSameSlotConsumable(int itemId)
	{
		return itemId == ItemID.PURPLE_SWEETS || itemId == ItemID.PURPLE_SWEETS_10476 || itemId == ItemID.HONEY_LOCUST;
	}
}