package com.natamus.inventorytotem.forge.events;

import com.natamus.inventorytotem.events.TotemEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgeTotemEvent {
	public static void registerEventsInBus() {
		// BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeTotemEvent.class);

		LivingDeathEvent.BUS.addListener(ForgeTotemEvent::onPlayerDeath);
	}

	@SubscribeEvent
	public static boolean onPlayerDeath(LivingDeathEvent e) {
		Entity entity = e.getEntity();
		Level level = entity.level();
		if (level.isClientSide) {
			return false;
		}
		
		if (!(entity instanceof Player)) {
			return false;
		}

		if (!TotemEvent.allowPlayerDeath((ServerLevel)level, (ServerPlayer)entity)) {
			return true;
		}
		return false;
	}
}
