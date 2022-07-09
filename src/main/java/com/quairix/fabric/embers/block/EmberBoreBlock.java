package com.quairix.fabric.embers.block;

import static com.quairix.fabric.embers.entity.EmberBoreBlockEntity.EMBER_BORE_BLOCK_ENTITY;

import com.quairix.fabric.embers.entity.DemoBlockEntity;

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
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;


public class EmberBoreBlock extends BlockWithEntity {

	public static final Block EMBER_BORE_BLOCK = new EmberBoreBlock(FabricBlockSettings.of(Material.STONE).strength(0.3f));

	public EmberBoreBlock(final Settings settings) {
		super(settings.nonOpaque());
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new DemoBlockEntity(pos, state);
	}

	public static void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier("embers", "ember_bore_block"), EMBER_BORE_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("embers", "ember_bore_block"), new BlockItem(EMBER_BORE_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, EMBER_BORE_BLOCK_ENTITY, (world1, pos, state1, be) ->  be.tick(world1, pos, state1, be));
	}
}
