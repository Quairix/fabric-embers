package com.quairix.fabric.embers.base;

import com.quairix.fabric.embers.EmbersMod;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.WorldAccess;

import java.util.concurrent.Executor;

public class EmbersEvents {
	public static void register(){
		ServerWorldEvents.LOAD.register(EmbersEvents::onLoadWorld);
		ServerWorldEvents.UNLOAD.register(EmbersEvents::onUnloadWorld);
	}

	private static void onUnloadWorld(Executor executor, WorldAccess world) {
		EmbersMod.EMBERS_NETWORK_PROVIDER.onUnloadWorld(world);
	}

	private static void onLoadWorld(Executor executor, WorldAccess world) {
		EmbersMod.EMBERS_NETWORK_PROVIDER.onLoadWorld(world);
	}
}
