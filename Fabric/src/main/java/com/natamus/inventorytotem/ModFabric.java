package com.natamus.inventorytotem;

import com.natamus.collective.check.RegisterMod;
import com.natamus.inventorytotem.events.TotemEvent;
import com.natamus.inventorytotem.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		ServerPlayerEvents.ALLOW_DEATH.register((ServerPlayer player, DamageSource damageSource, float damageAmount) -> {
			return TotemEvent.allowPlayerDeath((ServerLevel)player.getCommandSenderWorld(), player);
		});
	}

	private static void setGlobalConstants() {

	}
}
