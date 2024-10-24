package com.natamus.inventorytotem;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import com.natamus.inventorytotem.events.TotemEvent;
import com.natamus.inventorytotem.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		ServerLivingEntityEvents.ALLOW_DEATH.register((LivingEntity livingEntity, DamageSource damageSource, float damageAmount) -> {
			if (livingEntity instanceof ServerPlayer) {
				return TotemEvent.allowPlayerDeath((ServerLevel)livingEntity.level(), (ServerPlayer)livingEntity);
			}
			return true;
		});
	}

	private static void setGlobalConstants() {

	}
}
