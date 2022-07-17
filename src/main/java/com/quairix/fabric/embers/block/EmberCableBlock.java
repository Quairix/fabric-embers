package com.quairix.fabric.embers.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;

public class EmberCableBlock extends Block {
	public EmberCableBlock(Settings settings) {
		super(settings);
	}

	public static final Block EMBER_CABLE_BLOCK = new EmberCableBlock(FabricBlockSettings.of(Material.METAL).strength(3.0f).requiresTool());

}
