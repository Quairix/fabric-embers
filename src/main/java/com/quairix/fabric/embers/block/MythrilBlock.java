package com.quairix.fabric.embers.block;

import com.quairix.fabric.embers.util.EmberUtils;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class MythrilBlock extends Block {

	public static final Block MYTHRIL_BLOCK = new MythrilBlock(FabricBlockSettings.of(Material.METAL).strength(3.0F, 3.0F).requiresTool());

	// todo: генерация в структуре

	public MythrilBlock(final Settings settings) {
		super(settings);
	}

	public static void onInitialize() {
		Registry.register(Registry.BLOCK, EmberUtils.getIdentifier("mythril_block"), MYTHRIL_BLOCK);
		Registry.register(Registry.ITEM, EmberUtils.getIdentifier("mythril_block"), new BlockItem(MYTHRIL_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
	}
}
