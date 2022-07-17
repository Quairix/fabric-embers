package com.quairix.fabric.embers.base;

import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.HashMap;
import java.util.Map;

public class EmbersNetworkProvider {
	static Map<WorldAccess, Map<Long, EmberNetwork>> networks = new HashMap<>();

	public void onLoadWorld(WorldAccess world) {
		networks.put(world, new HashMap<>());
	}

	public void onUnloadWorld(WorldAccess world) {
		networks.remove(world);
	}

	public  EmberNetwork getOrCreateNetworkFor(EmberTileEntity te) {
		Long id = te.network;
		EmberNetwork network;
		Map<Long, EmberNetwork> map = networks.get(te.getWorld());
		if (id == null)
			return null;

		if (!map.containsKey(id)) {
			network = new EmberNetwork();
			network.id = te.network;
			map.put(id, network);
		}
		network = map.get(id);
		return network;
	}
}
