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
import java.util.Set;
import net.runelite.api.ItemID;

public class ConsumableItemIds
{
	public static final Set<Integer> OVERTIME_FOOD_ITEM_IDS = ImmutableSet.of(
		ItemID.COOKED_WILD_KEBBIT,
		ItemID.COOKED_LARUPIA,
		ItemID.COOKED_BARBTAILED_KEBBIT,
		ItemID.COOKED_GRAAHK,
		ItemID.COOKED_KYATT,
		ItemID.COOKED_PYRE_FOX,
		ItemID.COOKED_SUNLIGHT_ANTELOPE,
		ItemID.COOKED_DASHING_KEBBIT,
		ItemID.COOKED_MOONLIGHT_ANTELOPE
	);
	public static final Set<Integer> FOOD_ITEM_IDS = ImmutableSet.of(
		ItemID.ANGLERFISH, ItemID.BLIGHTED_ANGLERFISH,
		ItemID.DARK_CRAB,
		ItemID.TUNA_POTATO,
		ItemID.MANTA_RAY, ItemID.BLIGHTED_MANTA_RAY,
		ItemID.SEA_TURTLE,
		ItemID.SHARK, ItemID.SHARK_6969, ItemID.SHARK_20390,
		ItemID.PADDLEFISH,
		ItemID.PYSK_FISH_0, ItemID.SUPHI_FISH_1, ItemID.LECKISH_FISH_2, ItemID.BRAWK_FISH_3, ItemID.MYCIL_FISH_4, ItemID.ROQED_FISH_5, ItemID.KYREN_FISH_6,
		ItemID.GUANIC_BAT_0, ItemID.PRAEL_BAT_1, ItemID.GIRAL_BAT_2, ItemID.PHLUXIA_BAT_3, ItemID.KRYKET_BAT_4, ItemID.MURNG_BAT_5, ItemID.PSYKK_BAT_6,
		ItemID.UGTHANKI_KEBAB, ItemID.UGTHANKI_KEBAB_1885, ItemID.SUPER_KEBAB,
		ItemID.MUSHROOM_POTATO,
		ItemID.CURRY,
		ItemID.EGG_POTATO,
		ItemID.POTATO_WITH_CHEESE,
		ItemID.MONKFISH, ItemID.MONKFISH_20547,
		ItemID.COOKED_JUBBLY,
		ItemID.COOKED_OOMLIE_WRAP,
		ItemID.CHILLI_POTATO,
		ItemID.POTATO_WITH_BUTTER,
		ItemID.SWORDFISH,
		ItemID.BASS,
		ItemID.TUNA_AND_CORN,
		ItemID.LOBSTER,
		ItemID.STEW,
		ItemID.JUG_OF_WINE,
		ItemID.LAVA_EEL,
		ItemID.CAVE_EEL,
		ItemID.MUSHROOM__ONION,
		ItemID.RAINBOW_FISH,
		ItemID.COOKED_FISHCAKE,
		ItemID.COOKED_CHOMPY, ItemID.COOKED_CHOMPY_7228,
		ItemID.COOKED_SWEETCORN, ItemID.SWEETCORN_7088,
		ItemID.KEBAB,
		ItemID.DRAGONFRUIT,
		ItemID.TUNA, ItemID.CHOPPED_TUNA,
		ItemID.SALMON,
		ItemID.EGG_AND_TOMATO,
		ItemID.PEACH,
		ItemID.COOKED_SLIMY_EEL,
		ItemID.PIKE,
		ItemID.COD,
		ItemID.ROAST_BEAST_MEAT,
		ItemID.TROUT,
		ItemID.PAPAYA_FRUIT,
		ItemID.SPIDER_ON_STICK, ItemID.SPIDER_ON_STICK_6297, ItemID.SPIDER_ON_SHAFT, ItemID.SPIDER_ON_SHAFT_6299, ItemID.SPIDER_ON_SHAFT_6303,
		ItemID.FAT_SNAIL_MEAT,
		ItemID.MACKEREL,
		ItemID.GIANT_CARP,
		ItemID.ROAST_BIRD_MEAT,
		ItemID.FROG_SPAWN,
		ItemID.COOKED_MYSTERY_MEAT,
		ItemID.COOKED_RABBIT,
		ItemID.CHILLI_CON_CARNE,
		ItemID.FRIED_MUSHROOMS,
		ItemID.FRIED_ONIONS,
		ItemID.SCRAMBLED_EGG,
		ItemID.HERRING,
		ItemID.THIN_SNAIL_MEAT, ItemID.LEAN_SNAIL_MEAT,
		ItemID.BREAD,
		ItemID.BAKED_POTATO,
		ItemID.ONION__TOMATO,
		ItemID.SLICE_OF_CAKE, ItemID.CHOCOLATE_SLICE,
		ItemID.SARDINE,
		ItemID.UGTHANKI_MEAT,
		ItemID.COOKED_MEAT, ItemID.COOKED_MEAT_4293,
		ItemID.COOKED_CHICKEN, ItemID.COOKED_CHICKEN_4291,
		ItemID.SPICY_SAUCE,
		ItemID.CHEESE,
		ItemID.SPICY_MINCED_MEAT,
		ItemID.MINCED_MEAT,
		ItemID.BANANA, ItemID.SLICED_BANANA,
		ItemID.TOMATO, ItemID.CHOPPED_TOMATO, ItemID.SPICY_TOMATO,
		ItemID.ANCHOVIES,
		ItemID.SHRIMPS,
		ItemID.POTATO,
		ItemID.WATERMELON_SLICE, ItemID.PINEAPPLE_RING, ItemID.PINEAPPLE_CHUNKS,
		ItemID.ONION, ItemID.CHOPPED_ONION,
		ItemID.ORANGE, ItemID.ORANGE_SLICES,
		ItemID.STRAWBERRY,
		ItemID.CABBAGE,
		ItemID.MINT_CAKE,
		ItemID.PURPLE_SWEETS, ItemID.PURPLE_SWEETS_10476,
		ItemID.HONEY_LOCUST,
		ItemID.BANDAGES, ItemID.BANDAGES_25202, ItemID.BANDAGES_25730,
		ItemID.STRANGE_FRUIT,
		ItemID.WHITE_TREE_FRUIT,
		ItemID.GOUT_TUBER,
		ItemID.JANGERBERRIES,
		ItemID.DWELLBERRIES,
		ItemID.CAVE_NIGHTSHADE,
		ItemID.POT_OF_CREAM,
		ItemID.EQUA_LEAVES,
		ItemID.EDIBLE_SEAWEED,
		ItemID.SCARRED_SCRAPS,
		ItemID.RATIONS,
		ItemID.BAT_SHISH,
		ItemID.COATED_FROGS_LEGS,
		ItemID.FINGERS,
		ItemID.FROGBURGER,
		ItemID.FROGSPAWN_GUMBO,
		ItemID.GREEN_GLOOP_SOUP,
		ItemID.COOKED_BREAM, ItemID.COOKED_MOSS_LIZARD,
		// These brews work as food
		ItemID.CUP_OF_TEA, ItemID.CUP_OF_TEA_1978, ItemID.CUP_OF_TEA_4242, ItemID.CUP_OF_TEA_4243, ItemID.CUP_OF_TEA_4245, ItemID.CUP_OF_TEA_4246,
		ItemID.CUP_OF_TEA_4838, ItemID.CUP_OF_TEA_7730, ItemID.CUP_OF_TEA_7731, ItemID.CUP_OF_TEA_7733, ItemID.CUP_OF_TEA_7734, ItemID.CUP_OF_TEA_7736, ItemID.CUP_OF_TEA_7737,
		ItemID.NETTLE_TEA, ItemID.NETTLE_TEA_4240, ItemID.CHOCOLATEY_MILK, ItemID.TEA_FLASK, ItemID.TEA_FLASK_25617,
		ItemID.DWARVEN_STOUT, ItemID.DWARVEN_STOUTM, ItemID.DWARVEN_STOUT4, ItemID.DWARVEN_STOUT3, ItemID.DWARVEN_STOUT2, ItemID.DWARVEN_STOUT1,
		ItemID.WIZARDS_MIND_BOMB, ItemID.MIND_BOMB4, ItemID.MIND_BOMB3, ItemID.MIND_BOMB2, ItemID.MIND_BOMB1,
		ItemID.MIND_BOMBM4, ItemID.MIND_BOMBM3, ItemID.MIND_BOMBM2, ItemID.MIND_BOMBM1,
		ItemID.CIDER, ItemID.CIDER_7752, ItemID.CIDER4, ItemID.CIDER3, ItemID.CIDER2, ItemID.CIDER1,
		ItemID.CIDERM4, ItemID.CIDERM3, ItemID.CIDERM2, ItemID.CIDERM1, ItemID.MATURE_CIDER,
		ItemID.SLAYERS_RESPITE, ItemID.SLAYERS_RESPITEM, ItemID.SLAYERS_RESPITE4, ItemID.SLAYERS_RESPITE3, ItemID.SLAYERS_RESPITE2, ItemID.SLAYERS_RESPITE1,
		ItemID.SLAYERS_RESPITEM4, ItemID.SLAYERS_RESPITEM3, ItemID.SLAYERS_RESPITEM2, ItemID.SLAYERS_RESPITEM1,
		ItemID.MOONLIGHT_MEAD, ItemID.MOONLIGHT_MEAD_7750, ItemID.MOONLIGHT_MEAD4, ItemID.MOONLIGHT_MEAD3, ItemID.MOONLIGHT_MEAD2, ItemID.MOONLIGHT_MEAD1,
		ItemID.MOONLIGHT_MEADM, ItemID.MOONLIGHT_MEADM4, ItemID.MOONLIGHT_MEADM3, ItemID.MOONLIGHT_MEADM2, ItemID.MOONLIGHT_MEADM1,
		ItemID.DRAGON_BITTER, ItemID.DRAGON_BITTERM, ItemID.DRAGON_BITTERM4, ItemID.DRAGON_BITTERM3, ItemID.DRAGON_BITTERM2, ItemID.DRAGON_BITTERM1,
		ItemID.DRAGON_BITTER_7748, ItemID.DRAGON_BITTER_8243, ItemID.DRAGON_BITTER_8524, ItemID.DRAGON_BITTER4, ItemID.DRAGON_BITTER3, ItemID.DRAGON_BITTER2, ItemID.DRAGON_BITTER1,
		ItemID.CHEFS_DELIGHT, ItemID.CHEFS_DELIGHTM, ItemID.CHEFS_DELIGHTM4, ItemID.CHEFS_DELIGHTM3, ItemID.CHEFS_DELIGHTM2, ItemID.CHEFS_DELIGHTM1,
		ItemID.CHEFS_DELIGHT4, ItemID.CHEFS_DELIGHT3, ItemID.CHEFS_DELIGHT2, ItemID.CHEFS_DELIGHT1,
		ItemID.CHEFS_DELIGHT_7754, ItemID.CHEFS_DELIGHT_8244, ItemID.CHEFS_DELIGHT_8526,
		ItemID.AXEMANS_FOLLY, ItemID.AXEMANS_FOLLYM, ItemID.AXEMANS_FOLLYM4, ItemID.AXEMANS_FOLLYM3, ItemID.AXEMANS_FOLLYM2, ItemID.AXEMANS_FOLLYM1,
		ItemID.AXEMANS_FOLLY4, ItemID.AXEMANS_FOLLY3, ItemID.AXEMANS_FOLLY2, ItemID.AXEMANS_FOLLY1,
		ItemID.ASGARNIAN_ALE, ItemID.ASGARNIAN_ALE_7744, ItemID.ASGARNIAN_ALE_8241, ItemID.ASGARNIAN_ALE_8520,
		ItemID.ASGARNIAN_ALEM4, ItemID.ASGARNIAN_ALEM3, ItemID.ASGARNIAN_ALEM2, ItemID.ASGARNIAN_ALEM1,
		ItemID.ASGARNIAN_ALE4, ItemID.ASGARNIAN_ALE3, ItemID.ASGARNIAN_ALE2, ItemID.ASGARNIAN_ALE1,
		ItemID.GREENMANS_ALE, ItemID.GREENMANS_ALE_7746, ItemID.GREENMANS_ALE_8242, ItemID.GREENMANS_ALE_8522,
		ItemID.GREENMANS_ALEM4, ItemID.GREENMANS_ALEM3, ItemID.GREENMANS_ALEM2, ItemID.GREENMANS_ALEM1,
		ItemID.GREENMANS_ALE4, ItemID.GREENMANS_ALE3, ItemID.GREENMANS_ALE2, ItemID.GREENMANS_ALE1,
		ItemID.LIZARDKICKER, ItemID.GROG,
		ItemID.ELVEN_DAWN, ItemID.BLOODY_BRACER, ItemID.BLOOD_PINT, ItemID.BEER, ItemID.BEER_7740, ItemID.BANDITS_BREW,
		ItemID.SUNBEAM_ALE, ItemID.STEAMFORGE_BREW, ItemID.ECLIPSE_WINE, ItemID.MOONLITE, ItemID.SUNSHINE, ItemID.TRAPPERS_TIPPLE,
		ItemID.FRUIT_BLAST, ItemID.FRUIT_BLAST_9514, ItemID.PREMADE_FR_BLAST, ItemID.DIRTY_BLAST, ItemID.PINEAPPLE_PUNCH, ItemID.PINEAPPLE_PUNCH_9512,
		ItemID.PREMADE_P_PUNCH, ItemID.WIZARD_BLIZZARD, ItemID.WIZARD_BLIZZARD_9487, ItemID.WIZARD_BLIZZARD_9489, ItemID.WIZARD_BLIZZARD_9508, ItemID.PREMADE_WIZ_BLZD,
		ItemID.SHORT_GREEN_GUY, ItemID.SHORT_GREEN_GUY_9510, ItemID.PREMADE_SGG, ItemID.DRUNK_DRAGON, ItemID.DRUNK_DRAGON_9516, ItemID.PREMADE_DR_DRAGON,
		ItemID.CHOC_SATURDAY, ItemID.CHOC_SATURDAY_9518, ItemID.PREMADE_CHOC_SDY, ItemID.BLURBERRY_SPECIAL, ItemID.BLURBERRY_SPECIAL_9520, ItemID.PREMADE_BLURB_SP,
		ItemID.WHISKY, ItemID.VODKA, ItemID.KARAMJAN_RUM, ItemID.KARAMJAN_RUM_3164, ItemID.KARAMJAN_RUM_3165,
		ItemID.HALF_FULL_WINE_JUG, ItemID.GIN, ItemID.BRAINDEATH_RUM, ItemID.BRANDY, ItemID.BOTTLE_OF_WINE, ItemID.RUM_28896,
		ItemID.KEG_OF_BEER, ItemID.KEG_OF_BEER_3801, ItemID.KOVACS_GROG, ItemID.BEER_TANKARD
	);
	public static final Set<Integer> DRINK_ITEM_IDS = ImmutableSet.of(
		ItemID.GUTHIX_REST4, ItemID.GUTHIX_REST3, ItemID.GUTHIX_REST2, ItemID.GUTHIX_REST1,
		ItemID.ATTACK_POTION4, ItemID.ATTACK_POTION3, ItemID.ATTACK_POTION2, ItemID.ATTACK_POTION1,
		ItemID.ATTACK_MIX2, ItemID.ATTACK_MIX1, ItemID.SUPERATTACK_MIX2, ItemID.SUPERATTACK_MIX1,
		ItemID.SUPER_ATTACK4, ItemID.SUPER_ATTACK3, ItemID.SUPER_ATTACK2, ItemID.SUPER_ATTACK1,
		ItemID.DIVINE_SUPER_ATTACK_POTION4, ItemID.DIVINE_SUPER_ATTACK_POTION3, ItemID.DIVINE_SUPER_ATTACK_POTION2, ItemID.DIVINE_SUPER_ATTACK_POTION1,
		ItemID.STRENGTH_POTION4, ItemID.STRENGTH_POTION3, ItemID.STRENGTH_POTION2, ItemID.STRENGTH_POTION1,
		ItemID.STRENGTH_MIX2, ItemID.STRENGTH_MIX1, ItemID.SUPER_STR_MIX2, ItemID.SUPER_STR_MIX1,
		ItemID.SUPER_STRENGTH4, ItemID.SUPER_STRENGTH3, ItemID.SUPER_STRENGTH2, ItemID.SUPER_STRENGTH1,
		ItemID.DIVINE_SUPER_STRENGTH_POTION4, ItemID.DIVINE_SUPER_STRENGTH_POTION3, ItemID.DIVINE_SUPER_STRENGTH_POTION2, ItemID.DIVINE_SUPER_STRENGTH_POTION1,
		ItemID.DEFENCE_POTION4, ItemID.DEFENCE_POTION3, ItemID.DEFENCE_POTION2, ItemID.DEFENCE_POTION1,
		ItemID.DEFENCE_MIX2, ItemID.DEFENCE_MIX1, ItemID.SUPER_DEF_MIX2, ItemID.SUPER_DEF_MIX1,
		ItemID.SUPER_DEFENCE4, ItemID.SUPER_DEFENCE3, ItemID.SUPER_DEFENCE2, ItemID.SUPER_DEFENCE1,
		ItemID.DIVINE_SUPER_DEFENCE_POTION4, ItemID.DIVINE_SUPER_DEFENCE_POTION3, ItemID.DIVINE_SUPER_DEFENCE_POTION2, ItemID.DIVINE_SUPER_DEFENCE_POTION1,
		ItemID.COMBAT_POTION4, ItemID.COMBAT_POTION3, ItemID.COMBAT_POTION2, ItemID.COMBAT_POTION1,
		ItemID.COMBAT_POTION4_26150, ItemID.COMBAT_POTION3_26151, ItemID.COMBAT_POTION2_26152, ItemID.COMBAT_POTION1_26153,
		ItemID.COMBAT_MIX2, ItemID.COMBAT_MIX1,
		ItemID.SUPER_COMBAT_POTION4, ItemID.SUPER_COMBAT_POTION3, ItemID.SUPER_COMBAT_POTION2, ItemID.SUPER_COMBAT_POTION1,
		ItemID.SUPER_COMBAT_POTION4_23543, ItemID.SUPER_COMBAT_POTION3_23545, ItemID.SUPER_COMBAT_POTION2_23547, ItemID.SUPER_COMBAT_POTION1_23549,
		ItemID.DIVINE_SUPER_COMBAT_POTION4, ItemID.DIVINE_SUPER_COMBAT_POTION3, ItemID.DIVINE_SUPER_COMBAT_POTION2, ItemID.DIVINE_SUPER_COMBAT_POTION1,
		ItemID.RANGING_POTION4, ItemID.RANGING_POTION3, ItemID.RANGING_POTION2, ItemID.RANGING_POTION1,
		ItemID.RANGING_POTION4_23551, ItemID.RANGING_POTION3_23553, ItemID.RANGING_POTION2_23555, ItemID.RANGING_POTION1_23557,
		ItemID.RANGING_MIX2, ItemID.RANGING_MIX1,
		ItemID.SUPER_RANGING_4, ItemID.SUPER_RANGING_3, ItemID.SUPER_RANGING_2, ItemID.SUPER_RANGING_1,
		ItemID.DIVINE_RANGING_POTION4, ItemID.DIVINE_RANGING_POTION3, ItemID.DIVINE_RANGING_POTION2, ItemID.DIVINE_RANGING_POTION1,
		ItemID.BASTION_POTION4, ItemID.BASTION_POTION3, ItemID.BASTION_POTION2, ItemID.BASTION_POTION1,
		ItemID.DIVINE_BASTION_POTION4, ItemID.DIVINE_BASTION_POTION3, ItemID.DIVINE_BASTION_POTION2, ItemID.DIVINE_BASTION_POTION1,
		ItemID.STAMINA_POTION4, ItemID.STAMINA_POTION3, ItemID.STAMINA_POTION2, ItemID.STAMINA_POTION1,
		ItemID.STAMINA_POTION4_23583, ItemID.STAMINA_POTION3_23585, ItemID.STAMINA_POTION2_23587, ItemID.STAMINA_POTION1_23589,
		ItemID.STAMINA_MIX2, ItemID.STAMINA_MIX1,
		ItemID.SUPER_ENERGY4, ItemID.SUPER_ENERGY3, ItemID.SUPER_ENERGY2, ItemID.SUPER_ENERGY1,
		ItemID.SUPER_ENERGY_MIX2, ItemID.SUPER_ENERGY_MIX1,
		ItemID.SUPER_ENERGY4_20548, ItemID.SUPER_ENERGY3_20549, ItemID.SUPER_ENERGY2_20550, ItemID.SUPER_ENERGY1_20551,
		ItemID.ENERGY_POTION4, ItemID.ENERGY_POTION3, ItemID.ENERGY_POTION2, ItemID.ENERGY_POTION1,
		ItemID.ENERGY_MIX2, ItemID.ENERGY_MIX1,
		ItemID.ANTIDOTE4, ItemID.ANTIDOTE3, ItemID.ANTIDOTE2, ItemID.ANTIDOTE1,
		ItemID.ANTIDOTE_MIX2, ItemID.ANTIDOTE_MIX1,
		ItemID.ANTIDOTE4_5952, ItemID.ANTIDOTE3_5954, ItemID.ANTIDOTE2_5956, ItemID.ANTIDOTE1_5958,
		ItemID.SUPERANTIPOISON4, ItemID.SUPERANTIPOISON3, ItemID.SUPERANTIPOISON2, ItemID.SUPERANTIPOISON1,
		ItemID.ANTIPOISON_SUPERMIX2, ItemID.ANTIPOISON_SUPERMIX1,
		ItemID.ANTIPOISON4, ItemID.ANTIPOISON3, ItemID.ANTIPOISON2, ItemID.ANTIPOISON1,
		ItemID.ANTIPOISON_MIX2, ItemID.ANTIPOISON_MIX1,
		ItemID.ANTIVENOM4, ItemID.ANTIVENOM3, ItemID.ANTIVENOM2, ItemID.ANTIVENOM1,
		ItemID.ANTIVENOM4_12913, ItemID.ANTIVENOM3_12915, ItemID.ANTIVENOM2_12917, ItemID.ANTIVENOM1_12919,
		ItemID.EXTENDED_ANTIVENOM4, ItemID.EXTENDED_ANTIVENOM3, ItemID.EXTENDED_ANTIVENOM2, ItemID.EXTENDED_ANTIVENOM1, 
		ItemID.SARADOMIN_BREW4, ItemID.SARADOMIN_BREW3, ItemID.SARADOMIN_BREW2, ItemID.SARADOMIN_BREW1,
		ItemID.SARADOMIN_BREW4_23575, ItemID.SARADOMIN_BREW3_23577, ItemID.SARADOMIN_BREW2_23579, ItemID.SARADOMIN_BREW1_23581,
		ItemID.SUPER_RESTORE4, ItemID.SUPER_RESTORE3, ItemID.SUPER_RESTORE2, ItemID.SUPER_RESTORE1,
		ItemID.SUPER_RESTORE4_23567, ItemID.SUPER_RESTORE3_23569, ItemID.SUPER_RESTORE2_23571, ItemID.SUPER_RESTORE1_23573,
		ItemID.SUPER_RESTORE_MIX2, ItemID.SUPER_RESTORE_MIX1,
		ItemID.BLIGHTED_SUPER_RESTORE4, ItemID.BLIGHTED_SUPER_RESTORE3, ItemID.BLIGHTED_SUPER_RESTORE2, ItemID.BLIGHTED_SUPER_RESTORE1,
		ItemID.RESTORE_POTION4, ItemID.RESTORE_POTION3, ItemID.RESTORE_POTION2, ItemID.RESTORE_POTION1,
		ItemID.RESTORE_MIX2, ItemID.RESTORE_MIX1,
		ItemID.SANFEW_SERUM4, ItemID.SANFEW_SERUM3, ItemID.SANFEW_SERUM2, ItemID.SANFEW_SERUM1,
		ItemID.SANFEW_SERUM4_23559, ItemID.SANFEW_SERUM3_23561, ItemID.SANFEW_SERUM2_23563, ItemID.SANFEW_SERUM1_23565,
		ItemID.PRAYER_POTION4, ItemID.PRAYER_POTION3, ItemID.PRAYER_POTION2, ItemID.PRAYER_POTION1,
		ItemID.PRAYER_POTION4_20393, ItemID.PRAYER_POTION3_20394, ItemID.PRAYER_POTION2_20395, ItemID.PRAYER_POTION1_20396,
		ItemID.PRAYER_MIX2, ItemID.PRAYER_MIX1,
		ItemID.MAGIC_POTION4, ItemID.MAGIC_POTION3, ItemID.MAGIC_POTION2, ItemID.MAGIC_POTION1,
		ItemID.MAGIC_MIX2, ItemID.MAGIC_MIX1,
		ItemID.SUPER_MAGIC_POTION_4, ItemID.SUPER_MAGIC_POTION_3, ItemID.SUPER_MAGIC_POTION_2, ItemID.SUPER_MAGIC_POTION_1,
		ItemID.DIVINE_MAGIC_POTION4, ItemID.DIVINE_MAGIC_POTION3, ItemID.DIVINE_MAGIC_POTION2, ItemID.DIVINE_MAGIC_POTION1,
		ItemID.MAGIC_ESSENCE4, ItemID.MAGIC_ESSENCE3, ItemID.MAGIC_ESSENCE2, ItemID.MAGIC_ESSENCE1,
		ItemID.MAGIC_ESSENCE_MIX2, ItemID.MAGIC_ESSENCE_MIX1,
		ItemID.ANTIFIRE_POTION4, ItemID.ANTIFIRE_POTION3, ItemID.ANTIFIRE_POTION2, ItemID.ANTIFIRE_POTION1,
		ItemID.ANTIFIRE_MIX2, ItemID.ANTIFIRE_MIX1,
		ItemID.EXTENDED_ANTIFIRE4, ItemID.EXTENDED_ANTIFIRE3, ItemID.EXTENDED_ANTIFIRE2, ItemID.EXTENDED_ANTIFIRE1,
		ItemID.EXTENDED_ANTIFIRE_MIX2, ItemID.EXTENDED_ANTIFIRE_MIX1,
		ItemID.SUPER_ANTIFIRE_POTION4, ItemID.SUPER_ANTIFIRE_POTION3, ItemID.SUPER_ANTIFIRE_POTION2, ItemID.SUPER_ANTIFIRE_POTION1,
		ItemID.SUPER_ANTIFIRE_MIX2, ItemID.SUPER_ANTIFIRE_MIX1,
		ItemID.EXTENDED_SUPER_ANTIFIRE4, ItemID.EXTENDED_SUPER_ANTIFIRE3, ItemID.EXTENDED_SUPER_ANTIFIRE2, ItemID.EXTENDED_SUPER_ANTIFIRE1,
		ItemID.EXTENDED_SUPER_ANTIFIRE_MIX2, ItemID.EXTENDED_SUPER_ANTIFIRE_MIX1,
		ItemID.ANCIENT_BREW4, ItemID.ANCIENT_BREW3, ItemID.ANCIENT_BREW2, ItemID.ANCIENT_BREW1,
		ItemID.ANCIENT_MIX2, ItemID.ANCIENT_MIX1,
		ItemID.FORGOTTEN_BREW4, ItemID.FORGOTTEN_BREW3, ItemID.FORGOTTEN_BREW2, ItemID.FORGOTTEN_BREW1,
		ItemID.ZAMORAK_BREW4, ItemID.ZAMORAK_BREW3, ItemID.ZAMORAK_BREW2, ItemID.ZAMORAK_BREW1,
		ItemID.ZAMORAK_MIX2, ItemID.ZAMORAK_MIX1,
		ItemID.RELICYMS_BALM4, ItemID.RELICYMS_BALM3, ItemID.RELICYMS_BALM2, ItemID.RELICYMS_BALM1,
		ItemID.RELICYMS_MIX2, ItemID.RELICYMS_MIX1,
		ItemID.MENAPHITE_REMEDY4, ItemID.MENAPHITE_REMEDY3, ItemID.MENAPHITE_REMEDY2, ItemID.MENAPHITE_REMEDY1,
		ItemID.BATTLEMAGE_POTION4, ItemID.BATTLEMAGE_POTION3, ItemID.BATTLEMAGE_POTION2, ItemID.BATTLEMAGE_POTION1,
		ItemID.DIVINE_BATTLEMAGE_POTION4, ItemID.DIVINE_BATTLEMAGE_POTION3, ItemID.DIVINE_BATTLEMAGE_POTION2, ItemID.DIVINE_BATTLEMAGE_POTION1,
		ItemID.HUNTER_POTION4, ItemID.HUNTER_POTION3, ItemID.HUNTER_POTION2, ItemID.HUNTER_POTION1,
		ItemID.HUNTING_MIX2, ItemID.HUNTING_MIX1,
		ItemID.AGILITY_POTION4, ItemID.AGILITY_POTION3, ItemID.AGILITY_POTION2, ItemID.AGILITY_POTION1,
		ItemID.AGILITY_MIX2, ItemID.AGILITY_MIX1,
		ItemID.FISHING_POTION4, ItemID.FISHING_POTION3, ItemID.FISHING_POTION2, ItemID.FISHING_POTION1,
		ItemID.FISHING_MIX2, ItemID.FISHING_MIX1,
		ItemID.PRAYER_ENHANCE_4, ItemID.PRAYER_ENHANCE_3, ItemID.PRAYER_ENHANCE_2, ItemID.PRAYER_ENHANCE_1,
		ItemID.PRAYER_ENHANCE_4_20968, ItemID.PRAYER_ENHANCE_3_20967, ItemID.PRAYER_ENHANCE_2_20966, ItemID.PRAYER_ENHANCE_1_20965,
		ItemID.PRAYER_ENHANCE_4_20972, ItemID.PRAYER_ENHANCE_3_20971, ItemID.PRAYER_ENHANCE_2_20970, ItemID.PRAYER_ENHANCE_1_20969,
		ItemID.XERICS_AID_4, ItemID.XERICS_AID_3, ItemID.XERICS_AID_2, ItemID.XERICS_AID_1,
		ItemID.XERICS_AID_4_20980, ItemID.XERICS_AID_3_20979, ItemID.XERICS_AID_2_20978, ItemID.XERICS_AID_1_20977,
		ItemID.XERICS_AID_4_20984, ItemID.XERICS_AID_3_20983, ItemID.XERICS_AID_2_20982, ItemID.XERICS_AID_1_20981,
		ItemID.REVITALISATION_4, ItemID.REVITALISATION_3, ItemID.REVITALISATION_2, ItemID.REVITALISATION_1,
		ItemID.REVITALISATION_4_20960, ItemID.REVITALISATION_3_20959, ItemID.REVITALISATION_2_20958, ItemID.REVITALISATION_1_20957,
		ItemID.REVITALISATION_POTION_4, ItemID.REVITALISATION_POTION_3, ItemID.REVITALISATION_POTION_2, ItemID.REVITALISATION_POTION_1,
		ItemID.TWISTED_POTION_4, ItemID.TWISTED_POTION_3, ItemID.TWISTED_POTION_2, ItemID.TWISTED_POTION_1,
		ItemID.TWISTED_4, ItemID.TWISTED_3, ItemID.TWISTED_2, ItemID.TWISTED_1,
		ItemID.TWISTED_4_20936, ItemID.TWISTED_3_20935, ItemID.TWISTED_2_20934, ItemID.TWISTED_1_20933,
		ItemID.ELDER_POTION_4, ItemID.ELDER_POTION_3, ItemID.ELDER_POTION_2, ItemID.ELDER_POTION_1,
		ItemID.ELDER_4, ItemID.ELDER_3, ItemID.ELDER_2, ItemID.ELDER_1,
		ItemID.ELDER_4_20924, ItemID.ELDER_3_20923, ItemID.ELDER_2_20922, ItemID.ELDER_1_20921,
		ItemID.KODAI_POTION_4, ItemID.KODAI_POTION_3, ItemID.KODAI_POTION_2, ItemID.KODAI_POTION_1,
		ItemID.KODAI_4, ItemID.KODAI_3, ItemID.KODAI_2, ItemID.KODAI_1,
		ItemID.KODAI_4_20948, ItemID.KODAI_3_20947, ItemID.KODAI_2_20946, ItemID.KODAI_1_20945,
		ItemID.ANTIPOISON_POTION_4, ItemID.ANTIPOISON_POTION_3, ItemID.ANTIPOISON_POTION_2, ItemID.ANTIPOISON_POTION_1,
		ItemID.OVERLOAD_4, ItemID.OVERLOAD_3, ItemID.OVERLOAD_2, ItemID.OVERLOAD_1,
		ItemID.OVERLOAD_4_20988, ItemID.OVERLOAD_3_20987, ItemID.OVERLOAD_2_20986, ItemID.OVERLOAD_1_20985,
		ItemID.OVERLOAD_4_20992, ItemID.OVERLOAD_3_20991, ItemID.OVERLOAD_2_20990, ItemID.OVERLOAD_1_20989,
		ItemID.OVERLOAD_4_20996, ItemID.OVERLOAD_3_20995, ItemID.OVERLOAD_2_20994, ItemID.OVERLOAD_1_20993,
		ItemID.TEARS_OF_ELIDINIS_4, ItemID.TEARS_OF_ELIDINIS_3, ItemID.TEARS_OF_ELIDINIS_2, ItemID.TEARS_OF_ELIDINIS_1,
		ItemID.NECTAR_4, ItemID.NECTAR_3, ItemID.NECTAR_2, ItemID.NECTAR_1,
		ItemID.BLESSED_CRYSTAL_SCARAB_2, ItemID.BLESSED_CRYSTAL_SCARAB_1,
		ItemID.SMELLING_SALTS_2, ItemID.SMELLING_SALTS_1,
		ItemID.LIQUID_ADRENALINE_2, ItemID.LIQUID_ADRENALINE_1,
		ItemID.EGNIOL_POTION_4, ItemID.EGNIOL_POTION_3, ItemID.EGNIOL_POTION_2, ItemID.EGNIOL_POTION_1,
		ItemID.CASTLEWARS_BREW4, ItemID.CASTLEWARS_BREW3, ItemID.CASTLEWARS_BREW2, ItemID.CASTLEWARS_BREW1,
		ItemID.POTION_OF_POWER4, ItemID.POTION_OF_POWER3, ItemID.POTION_OF_POWER2, ItemID.POTION_OF_POWER1,
		ItemID.WINTER_SQIRKJUICE, ItemID.SPRING_SQIRKJUICE, ItemID.SUMMER_SQIRKJUICE, ItemID.AUTUMN_SQIRKJUICE,
		ItemID.SHRINKMEQUICK, ItemID.BRAVERY_POTION, ItemID.STRANGE_POTION_28383, ItemID.STRANGLER_SERUM,
		ItemID.SAPPHIRE_GLACIALIS_MIX_2, ItemID.SAPPHIRE_GLACIALIS_MIX_1,
		ItemID.SNOWY_KNIGHT_MIX_2, ItemID.SNOWY_KNIGHT_MIX_1,
		ItemID.RUBY_HARVEST_MIX_2, ItemID.RUBY_HARVEST_MIX_1,
		ItemID.BLACK_WARLOCK_MIX_2, ItemID.BLACK_WARLOCK_MIX_1,
		ItemID.SUNLIGHT_MOTH_MIX_2, ItemID.SUNLIGHT_MOTH_MIX_1,
		ItemID.MOONLIGHT_MOTH_MIX_2, ItemID.MOONLIGHT_MOTH_MIX_1,
		ItemID.MOONLIGHT_POTION4, ItemID.MOONLIGHT_POTION3, ItemID.MOONLIGHT_POTION2, ItemID.MOONLIGHT_POTION1
	);
	public static final Set<Integer> COMBO_FOOD_ITEM_IDS = ImmutableSet.of(
		ItemID.COOKED_KARAMBWAN, ItemID.COOKED_KARAMBWAN_3147, ItemID.COOKED_KARAMBWAN_23533, ItemID.BLIGHTED_KARAMBWAN, ItemID.CRYSTAL_PADDLEFISH,
		ItemID.TOAD_CRUNCHIES, ItemID.TOAD_CRUNCHIES_9538, ItemID.PREMADE_TD_CRUNCH, ItemID.SPICY_CRUNCHIES, ItemID.SPICY_CRUNCHIES_9540,
		ItemID.PREMADE_SY_CRUNCH, ItemID.WORM_CRUNCHIES, ItemID.PREMADE_WM_CRUN, ItemID.CHOCCHIP_CRUNCHIES, ItemID.PREMADE_CH_CRUNCH,
		ItemID.FRUIT_BATTA, ItemID.FRUIT_BATTA_9527, ItemID.PREMADE_FRT_BATTA, ItemID.TOAD_BATTA, ItemID.TOAD_BATTA_9529,
		ItemID.PREMADE_TD_BATTA, ItemID.WORM_BATTA, ItemID.WORM_BATTA_9531, ItemID.PREMADE_WM_BATTA, ItemID.VEGETABLE_BATTA,
		ItemID.VEGETABLE_BATTA_9533, ItemID.PREMADE_VEG_BATTA, ItemID.CHEESETOM_BATTA, ItemID.CHEESETOM_BATTA_9535, ItemID.PREMADE_CT_BATTA,
		ItemID.WORM_HOLE, ItemID.WORM_HOLE_9547, ItemID.PREMADE_WORM_HOLE, ItemID.VEG_BALL, ItemID.VEG_BALL_9549, ItemID.PREMADE_VEG_BALL,
		ItemID.CHOCOLATE_BOMB, ItemID.CHOCOLATE_BOMB_9553, ItemID.PREMADE_CHOC_BOMB, ItemID.TANGLED_TOADS_LEGS, ItemID.TANGLED_TOADS_LEGS_9551,
		ItemID.PREMADE_TTL, ItemID.TOADS_LEGS, ItemID.KING_WORM
	);
	public static final Set<Integer> CAKE_ITEM_IDS = ImmutableSet.of(
		ItemID.CAKE, ItemID.CAKE_24549, ItemID.CHOCOLATE_CAKE, ItemID._23_CAKE, ItemID._23_CHOCOLATE_CAKE
	);
	public static final Set<Integer> F2P_FIRST_SLICE_ITEM_IDS = ImmutableSet.of(
		ItemID.REDBERRY_PIE, ItemID.MEAT_PIE, ItemID.APPLE_PIE, ItemID.PLAIN_PIZZA, ItemID.MEAT_PIZZA, ItemID.ANCHOVY_PIZZA, ItemID.PINEAPPLE_PIZZA
	);
	public static final Set<Integer> F2P_SECOND_SLICE_ITEM_IDS = ImmutableSet.of(
		ItemID.HALF_A_REDBERRY_PIE, ItemID.HALF_A_MEAT_PIE, ItemID._12_PLAIN_PIZZA, ItemID._12_MEAT_PIZZA, ItemID._12_ANCHOVY_PIZZA, ItemID._12_PINEAPPLE_PIZZA
	);
	public static final Set<Integer> P2P_PIE_ITEM_IDS = ImmutableSet.of(
		ItemID.GARDEN_PIE, ItemID.HALF_A_GARDEN_PIE, ItemID.FISH_PIE, ItemID.HALF_A_FISH_PIE, ItemID.BOTANICAL_PIE, ItemID.HALF_A_BOTANICAL_PIE,
		ItemID.MUSHROOM_PIE, ItemID.HALF_A_MUSHROOM_PIE, ItemID.ADMIRAL_PIE, ItemID.HALF_AN_ADMIRAL_PIE, ItemID.DRAGONFRUIT_PIE, ItemID.HALF_A_DRAGONFRUIT_PIE,
		ItemID.WILD_PIE, ItemID.HALF_A_WILD_PIE, ItemID.SUMMER_PIE, ItemID.HALF_A_SUMMER_PIE
	);
	public static final Set<Integer> COOKED_CRAB_MEAT_ITEM_IDS = ImmutableSet.of(
		ItemID.COOKED_CRAB_MEAT, ItemID.COOKED_CRAB_MEAT_7523, ItemID.COOKED_CRAB_MEAT_7524, ItemID.COOKED_CRAB_MEAT_7525, ItemID.COOKED_CRAB_MEAT_7526
	);
}
