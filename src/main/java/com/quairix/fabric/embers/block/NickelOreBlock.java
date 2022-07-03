package com.quairix.fabric.embers.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class NickelOreBlock extends OreBlock {

	public static final Block NICKEL_ORE_BLOCK = new NickelOreBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f).requiresTool());

	public NickelOreBlock(final Settings settings) {
		super(settings);
	}

	public static void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier("embers", "nickel_ore_block"), NICKEL_ORE_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("embers", "nickel_ore_block"), new BlockItem(NICKEL_ORE_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
	}
}
