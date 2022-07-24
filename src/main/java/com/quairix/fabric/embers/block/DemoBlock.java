package com.quairix.fabric.embers.block;

import static com.quairix.fabric.embers.entity.DemoBlockEntity.DEMO_BLOCK_ENTITY;

import com.quairix.fabric.embers.entity.DemoBlockEntity;
import com.quairix.fabric.embers.util.EmberUtils;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;


public class DemoBlock extends BlockWithEntity {

	public static final Block DEMO_BLOCK = new DemoBlock(FabricBlockSettings.of(Material.STONE).strength(0.3f));

	public DemoBlock(final Settings settings) {
		super(settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new DemoBlockEntity(pos, state);
	}

	public static void onInitialize() {
		Registry.register(Registry.BLOCK, EmberUtils.getIdentifier("demo_block"), DEMO_BLOCK);
		Registry.register(Registry.ITEM, EmberUtils.getIdentifier("demo_block"), new BlockItem(DEMO_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, DEMO_BLOCK_ENTITY, (world1, pos, state1, be) ->  be.tick(world1, pos, state1, be));
	}
}
