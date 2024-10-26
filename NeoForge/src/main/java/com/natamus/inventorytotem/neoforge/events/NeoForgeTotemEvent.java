package com.natamus.inventorytotem.neoforge.events;

import com.natamus.inventorytotem.events.TotemEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class NeoForgeTotemEvent {
	@SubscribeEvent
	public static void onPlayerDeath(LivingDeathEvent e) {
		Entity entity = e.getEntity();
		Level level = entity.level();
		if (level.isClientSide) {
			return;
		}
		
		if (!(entity instanceof Player)) {
			return;
		}

		if (!TotemEvent.allowPlayerDeath((ServerLevel)level, (ServerPlayer)entity)) {
			e.setCanceled(true);
		}
	}
}
