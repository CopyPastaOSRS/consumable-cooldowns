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

import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;
import javax.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Constants;
import net.runelite.api.GameState;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.Text;

@Slf4j
@PluginDescriptor(
	name = "Consumable Cooldowns",
	description = "Displays cooldowns of consumable items (food & drinks) in your inventory",
	tags = {"inventory", "timer", "pvm", "overlay", "cd", "food", "pots", "potion", "highlight"}
)
public class ConsumableCooldownsPlugin extends Plugin
{
	private static final ImmutableSet<ConsumableItem> CONSUMABLES = ImmutableSet.of(
		new ConsumableItem(ConsumableItemType.FOOD, 3, 3, ConsumableItemIds.FOOD_ITEM_IDS::contains),
		new ConsumableItem(ConsumableItemType.POTION, 0, 3, 0, 3, ConsumableItemIds.DRINK_ITEM_IDS::contains),
		new ConsumableItem(ConsumableItemType.COMBO_FOOD, 2, 3, 3, 3, ConsumableItemIds.COMBO_FOOD_ITEM_IDS::contains),
		new ConsumableItem(ConsumableItemType.CAKE, 3, 2, ConsumableItemIds.CAKE_ITEM_IDS::contains),
		new ConsumableItem(ConsumableItemType.F2P_FIRST_SLICE, 3, 1, ConsumableItemIds.F2P_FIRST_SLICE_ITEM_IDS::contains),
		new ConsumableItem(ConsumableItemType.F2P_SECOND_SLICE, 3, 2, ConsumableItemIds.F2P_SECOND_SLICE_ITEM_IDS::contains),
		new ConsumableItem(ConsumableItemType.P2P_PIE, 3, 1, ConsumableItemIds.P2P_PIE_ITEM_IDS::contains),
		new ConsumableItem(ConsumableItemType.COOKED_CRAB_MEAT, 2, 2, ConsumableItemIds.COOKED_CRAB_MEAT_ITEM_IDS::contains)
	);
	private static final Pattern EAT_PATTERN = Pattern.compile("^eat");
	private static final Pattern DRINK_PATTERN = Pattern.compile("^drink");
	private static final int ITEM_COOLDOWN_PREVIEW_TICKS = 2;
	private static final int ITEM_COOLDOWN_PREVIEW_CLIENT_TICKS = 60;
	private static final int ITEM_COOLDOWN_PREVIEW_GRACE_PERIOD_CLIENT_TICKS = -(Constants.GAME_TICK_LENGTH / Constants.CLIENT_TICK_LENGTH);

	@Inject
	private Client client;

	@Inject
	private ConsumableCooldownsConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ConsumableCooldownsOverlay overlay;

	private int actionCooldownTicks;

	private int eatCooldownTicks;
	private int eatCooldownClientTicks;

	private int drinkCooldownTicks;
	private int drinkCooldownClientTicks;

	private int comboEatCooldownTicks;
	private int comboEatCooldownClientTicks;

	private int previewCooldownTicks;
	private int previewCooldownClientTicks;

	@Getter
	private List<InventoryConsumableItemAction> inventoryConsumableItemActions;

	@Provides
	ConsumableCooldownsConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ConsumableCooldownsConfig.class);
	}

	private void setupDelays()
	{
		actionCooldownTicks = 0;
		eatCooldownTicks = 0;
		comboEatCooldownTicks = 0;
		drinkCooldownTicks = 0;
		eatCooldownClientTicks = 0;
		comboEatCooldownClientTicks = 0;
		drinkCooldownClientTicks = 0;
		previewCooldownTicks = ITEM_COOLDOWN_PREVIEW_TICKS;
		previewCooldownClientTicks = ITEM_COOLDOWN_PREVIEW_CLIENT_TICKS;
	}

	@Override
	protected void startUp() throws Exception
	{
		inventoryConsumableItemActions = new ArrayList<>();
		setupDelays();
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		inventoryConsumableItemActions = null;
		setupDelays();
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!event.getGroup().equals(ConsumableCooldownsConfig.GROUP_NAME))
		{
			return;
		}

		switch (event.getKey())
		{
			case "itemCooldownIndicatorFillColor":
			case "itemCooldownIndicatorFillOpacity":
				overlay.invalidateCache();
				break;
			case "showItemCooldownPreview":
				if (config.showItemCooldownPreview())
				{
					previewCooldownTicks = ITEM_COOLDOWN_PREVIEW_TICKS;
					previewCooldownClientTicks = ITEM_COOLDOWN_PREVIEW_CLIENT_TICKS;
				}
				break;
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		GameState gameState = event.getGameState();
		if (gameState == GameState.LOGGING_IN || gameState == GameState.CONNECTION_LOST || gameState == GameState.HOPPING)
		{
			inventoryConsumableItemActions.clear();
			setupDelays();
		}
	}

	@Subscribe
	public void onClientTick(ClientTick event)
	{
		if (client.getGameState() != GameState.LOGGED_IN)
		{
			return;
		}

		if (eatCooldownClientTicks > 0)
		{
			eatCooldownClientTicks--;
		}

		if (comboEatCooldownClientTicks > 0)
		{
			comboEatCooldownClientTicks--;
		}

		if (drinkCooldownClientTicks > 0)
		{
			drinkCooldownClientTicks--;
		}

		if (config.showItemCooldownPreview() && previewCooldownClientTicks > ITEM_COOLDOWN_PREVIEW_GRACE_PERIOD_CLIENT_TICKS)
		{
			previewCooldownClientTicks--;
		}
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (client.getGameState() != GameState.LOGGED_IN)
		{
			return;
		}

		if (actionCooldownTicks > 0)
		{
			actionCooldownTicks--;
		}

		if (eatCooldownTicks > 0)
		{
			eatCooldownTicks--;
		}

		if (comboEatCooldownTicks > 0)
		{
			comboEatCooldownTicks--;
		}

		if (drinkCooldownTicks > 0)
		{
			drinkCooldownTicks--;
		}

		if (config.showItemCooldownPreview() && previewCooldownTicks > -1)
		{
			previewCooldownTicks--;
		}
		else if (config.showItemCooldownPreview())
		{
			previewCooldownTicks = ITEM_COOLDOWN_PREVIEW_TICKS;
			previewCooldownClientTicks = ITEM_COOLDOWN_PREVIEW_CLIENT_TICKS;
		}

		ListIterator<InventoryConsumableItemAction> actionsIterator = inventoryConsumableItemActions.listIterator();
		while (actionsIterator.hasNext())
		{
			InventoryConsumableItemAction itemAction = actionsIterator.next();
			int ticksSinceAction = client.getTickCount() - itemAction.getActionTick();
			if (ticksSinceAction <= 1)
			{
				continue;
			}

			actionsIterator.remove();
			log.warn("{} - Removed item action (id: {}, slot: {}) from queue (size: {}). More than 1 tick since action on tick: {}",
				client.getTickCount(), itemAction.getItemId(), itemAction.getItemSlot(), inventoryConsumableItemActions.size(), itemAction.getActionTick());
		}
	}

	@Subscribe
	private void onMenuOptionClicked(final MenuOptionClicked event)
	{
		String menuOption = Text.removeTags(event.getMenuOption()).toLowerCase();
		log.debug("{} - Menu option clicked: {}. Slot: {}", client.getTickCount(), menuOption, event.getMenuEntry().getParam0());
		if (!isConsumableMenuOption(menuOption) || isMenuOptionItemInInventoryActions(event))
		{
			log.debug("{} - Menu option skipped. Consumable: {}, already in actions: {}", client.getTickCount(), isConsumableMenuOption(menuOption), isMenuOptionItemInInventoryActions(event));
			return;
		}

		ItemContainer oldInventory = client.getItemContainer(InventoryID.INVENTORY);
		if (oldInventory == null)
		{
			return;
		}

		int inventorySlot = event.getMenuEntry().getParam0();
		Item item = oldInventory.getItems()[inventorySlot];
		inventoryConsumableItemActions.add(new InventoryConsumableItemAction(
			oldInventory.getItems(), item.getId(), inventorySlot, client.getTickCount())
		);
		log.debug("{} - Added item in slot: {} with id: {} to queue", client.getTickCount(), inventorySlot, item.getId());
	}

	@Subscribe
	private void onItemContainerChanged(ItemContainerChanged event)
	{
		if (event.getContainerId() != InventoryID.INVENTORY.getId())
		{
			return;
		}

		processInventoryChanges(event.getItemContainer());
		log.debug("{} - Inventory changed. Queue size: {}", client.getTickCount(), inventoryConsumableItemActions.size());
	}

	private void processInventoryChanges(ItemContainer itemContainer)
	{
		ListIterator<InventoryConsumableItemAction> actionsIterator = inventoryConsumableItemActions.listIterator();
		while (actionsIterator.hasNext())
		{
			InventoryConsumableItemAction itemAction = actionsIterator.next();
			log.debug("{} - Item in slot: {} with id: {} and idx: {} with size: {} peeked from queue", client.getTickCount(), itemAction.getItemSlot(), itemAction.getItemId(), actionsIterator.nextIndex(), inventoryConsumableItemActions.size());

			int itemId = itemAction.getItemId();
			ConsumableItem consumableItem = getConsumableItemFromId(itemId);
			if (consumableItem == null)
			{
				log.debug("{} - Item in slot: {} with id: {} skipped. No consumable item found", client.getTickCount(), itemAction.getItemSlot(), itemAction.getItemId());
				actionsIterator.remove();
				continue;
			}

			if (!itemAction.isItemConsumed(itemContainer.getItems()))
			{
				log.debug("{} - Item in slot: {} with id: {} skipped. Item still in slot", client.getTickCount(), itemAction.getItemSlot(), itemAction.getItemId());

				// An item can be consumed one or multiple ticks later than the initial action click due to latency
				// So leave it on the queue if the item is still there on the action tick as it may be consumed the next tick
				if (itemAction.getActionTick() != client.getTickCount())
				{
					actionsIterator.remove();
				}

				continue;
			}

			actionsIterator.remove();
			itemConsumed(consumableItem, itemAction);
		}
	}

	public ConsumableItem getConsumableItemFromId(int itemId)
	{
		for (ConsumableItem consumableItem : CONSUMABLES)
		{
			if (consumableItem.getItemFilter().test(itemId))
			{
				return consumableItem;
			}
		}

		return null;
	}

	public ConsumableItemCooldown getCooldownForConsumableItem(ConsumableItem consumableItem)
	{
		if (config.showItemCooldownPreview())
		{
			return new ConsumableItemCooldown(previewCooldownTicks, previewCooldownClientTicks);
		}

		switch (consumableItem.getType())
		{
			case FOOD:
			case COOKED_CRAB_MEAT:
			case CAKE:
			case F2P_FIRST_SLICE:
			case F2P_SECOND_SLICE:
			case P2P_PIE:
				if (eatCooldownTicks <= 0)
				{
					return null;
				}

				return new ConsumableItemCooldown(eatCooldownTicks, eatCooldownClientTicks);
			case POTION:
				if (drinkCooldownTicks <= 0)
				{
					return null;
				}

				return new ConsumableItemCooldown(drinkCooldownTicks, drinkCooldownClientTicks);
			case COMBO_FOOD:
				if (comboEatCooldownTicks <= 0)
				{
					return null;
				}

				return new ConsumableItemCooldown(comboEatCooldownTicks, comboEatCooldownClientTicks);
		}

		return null;
	}

	public boolean isNoConsumableCooldownActive()
	{
		return (!config.showItemCooldownPreview() && eatCooldownTicks <= 0 && drinkCooldownTicks <= 0 && comboEatCooldownTicks <= 0) ||
			(config.showItemCooldownPreview() && previewCooldownTicks <= 0);
	}

	private void itemConsumed(ConsumableItem consumableItem, InventoryConsumableItemAction itemAction)
	{
		ConsumableItemType consumableItemType = consumableItem.getType();
		log.debug("{} - {} item with id: {} was consumed", client.getTickCount(), consumableItemType, itemAction.getItemId());

		switch (consumableItemType)
		{
			case FOOD:
			case COOKED_CRAB_MEAT:
			case CAKE:
			case F2P_FIRST_SLICE:
			case F2P_SECOND_SLICE:
			case P2P_PIE:
				eatCooldownTicks = consumableItem.getEatCooldownTicks();
				eatCooldownClientTicks = consumableItem.cooldownTicksToClientTicks(eatCooldownTicks);
				actionCooldownTicks += consumableItem.getActionCooldownTicks();
				log.debug("{} - FOOD - eat: {}, comboEat: {}, drink: {}, action: {}", client.getTickCount(), eatCooldownTicks, comboEatCooldownTicks, drinkCooldownTicks, actionCooldownTicks);
				break;
			case POTION:
				drinkCooldownTicks = consumableItem.getDrinkCooldownTicks();
				drinkCooldownClientTicks = consumableItem.cooldownTicksToClientTicks(drinkCooldownTicks);
				eatCooldownTicks = consumableItem.getEatCooldownTicks();
				eatCooldownClientTicks = consumableItem.cooldownTicksToClientTicks(eatCooldownTicks);
				actionCooldownTicks += consumableItem.getActionCooldownTicks();
				log.debug("{} - POTION - eat: {}, comboEat: {}, drink: {}, action: {}", client.getTickCount(), eatCooldownTicks, comboEatCooldownTicks, drinkCooldownTicks, actionCooldownTicks);
				break;
			case COMBO_FOOD:
				eatCooldownTicks = consumableItem.getEatCooldownTicks();
				eatCooldownClientTicks = consumableItem.cooldownTicksToClientTicks(eatCooldownTicks);
				comboEatCooldownTicks = consumableItem.getComboEatCooldownTicks();
				comboEatCooldownClientTicks = consumableItem.cooldownTicksToClientTicks(comboEatCooldownTicks);
				drinkCooldownTicks = consumableItem.getDrinkCooldownTicks();
				drinkCooldownClientTicks = consumableItem.cooldownTicksToClientTicks(drinkCooldownTicks);
				actionCooldownTicks += consumableItem.getActionCooldownTicks();
				log.debug("{} - COMBO - eat: {}, comboEat: {}, drink: {}, action: {}", client.getTickCount(), eatCooldownTicks, comboEatCooldownTicks, drinkCooldownTicks, actionCooldownTicks);
				break;
		}
	}

	private boolean isConsumableMenuOption(String menuOption)
	{
		return EAT_PATTERN.matcher(menuOption).find() || DRINK_PATTERN.matcher(menuOption).find();
	}

	private boolean isMenuOptionItemInInventoryActions(MenuOptionClicked event)
	{
		return inventoryConsumableItemActions.stream().anyMatch(itemAction ->
			itemAction.getItemId() == event.getItemId() && itemAction.getItemSlot() == event.getParam0());
	}
}
