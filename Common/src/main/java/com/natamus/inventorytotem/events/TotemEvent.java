package com.natamus.inventorytotem.events;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TotemEvent {
	public static boolean allowPlayerDeath (ServerLevel world, ServerPlayer player) {
		if (player.getMainHandItem().getItem().equals(Items.TOTEM_OF_UNDYING) || player.getOffhandItem().getItem().equals(Items.TOTEM_OF_UNDYING)) {
			return true;
		}
		
		Inventory inv = player.getInventory();

		ItemStack totemstack = null;
		for(int i = 0; i < inv.getContainerSize(); i++) {
			ItemStack stack = inv.getItem(i);
			if (stack.getItem().equals(Items.TOTEM_OF_UNDYING)) {
				totemstack = stack;
				break;
			}
		}
		
		if (totemstack == null) {
			return true;
		}

		player.awardStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING));
		CriteriaTriggers.USED_TOTEM.trigger(player, totemstack);

		player.setHealth(1.0F);
		player.removeAllEffects();
		player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
		player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
		player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
		world.broadcastEntityEvent(player, (byte)35);
		totemstack.shrink(1);
		return false;
	}
}
