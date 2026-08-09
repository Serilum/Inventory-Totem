package com.natamus.inventorytotem.events;

import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DeathProtection;
import net.minecraft.world.level.gameevent.GameEvent;

public class TotemEvent {
	public static boolean allowPlayerDeath (ServerLevel world, ServerPlayer player) {
		if (player.getMainHandItem().has(DataComponents.DEATH_PROTECTION) || player.getOffhandItem().has(DataComponents.DEATH_PROTECTION)) {
			return true;
		}
		
		Inventory inv = player.getInventory();

		ItemStack protectionItemStack = null;
		DeathProtection protection = null;
		for(int i = 0; i < inv.getContainerSize(); i++) {
			ItemStack stack = inv.getItem(i);
			if (stack.has(DataComponents.DEATH_PROTECTION)) {
				protectionItemStack = stack;
				protection = stack.get(DataComponents.DEATH_PROTECTION);
				break;
			}
		}
		
		if (protectionItemStack == null || protection == null) {
			return true;
		}

		player.awardStat(Stats.ITEM_USED.get(protectionItemStack.getItem()));
		CriteriaTriggers.USED_TOTEM.trigger(player, protectionItemStack);
		protectionItemStack.causeUseVibration(player, GameEvent.ITEM_INTERACT_FINISH);

		player.setHealth(1.0F);
		protection.applyEffects(protectionItemStack, player);
		world.broadcastEntityEvent(player, (byte)35);
		protectionItemStack.shrink(1);
		return false;
	}
}
