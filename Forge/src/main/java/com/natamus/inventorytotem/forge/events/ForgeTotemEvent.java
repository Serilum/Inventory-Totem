package com.natamus.inventorytotem.forge.events;

import com.natamus.inventorytotem.events.TotemEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ForgeTotemEvent {
	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent e) {
		Entity entity = e.getEntity();
		Level level = entity.getCommandSenderWorld();
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
