package com.serilum.inventorytotem.events;

import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DeathProtection;
import net.minecraft.world.level.gameevent.GameEvent;

public class TotemEvent {
	public static boolean allowPlayerDeath (ServerLevel world, ServerPlayer player, DamageSource damageSource) {
		if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			return true;
		}

		if (player.getMainHandItem().has(DataComponents.DEATH_PROTECTION) || player.getOffhandItem().has(DataComponents.DEATH_PROTECTION)) {
			return true;
		}

		Inventory inv = player.getInventory();

		ItemStack totemstack = null;
		for(int i = 0; i < inv.getContainerSize(); i++) {
			ItemStack stack = inv.getItem(i);
			if (stack.has(DataComponents.DEATH_PROTECTION)) {
				totemstack = stack;
				break;
			}
		}

		if (totemstack == null) {
			return true;
		}

		DeathProtection deathProtection = totemstack.get(DataComponents.DEATH_PROTECTION);

		player.awardStat(Stats.ITEM_USED.get(totemstack.getItem()));
		CriteriaTriggers.USED_TOTEM.trigger(player, totemstack);
		totemstack.causeUseVibration(player, GameEvent.ITEM_INTERACT_FINISH);

		player.setHealth(1.0F);
		deathProtection.applyEffects(totemstack, player);
		world.broadcastEntityEvent(player, (byte)35);
		totemstack.shrink(1);
		return false;
	}
}
