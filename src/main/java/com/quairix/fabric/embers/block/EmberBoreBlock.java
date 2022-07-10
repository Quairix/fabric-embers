package com.quairix.fabric.embers.block;

import com.quairix.fabric.embers.entity.EmberBoreBlockEntity;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;


public class EmberBoreBlock extends BlockWithEntity {

	public static final Block EMBER_BORE_BLOCK = new EmberBoreBlock(FabricBlockSettings.of(Material.STONE).strength(0.3f));

	protected static final VoxelShape SHAPE = VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);

	public EmberBoreBlock(final Settings settings) {
		super(settings.nonOpaque());
	}

	public static void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier("embers", "ember_bore_block"), EMBER_BORE_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("embers", "ember_bore_block"), new BlockItem(EMBER_BORE_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new EmberBoreBlockEntity(pos, state);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
//		if (world.isClient) return ActionResult.SUCCESS;
		final var blockEntityBase = world.getBlockEntity(blockPos);

		if (blockEntityBase == null || !(blockEntityBase instanceof EmberBoreBlockEntity)) {
			return ActionResult.FAIL;
		}

		final var blockEntity = (EmberBoreBlockEntity) blockEntityBase;
		if (!player.getStackInHand(hand).isEmpty()) {
			// Check what is the first open slot and put an item from the player's hand there
			if (blockEntity.getStack(0).isEmpty()) {
				// Put the stack the player is holding into the inventory
				blockEntity.setStack(0, player.getStackInHand(hand).copy());
				// Remove the stack from the player's hand
				player.getStackInHand(hand).setCount(0);
			}
		} else {
			// If the player is not holding anything we'll get give him the items in the block entity one by one

			// Find the first slot that has an item and give it to the player
			if (!blockEntity.getStack(0).isEmpty()) {
				player.getInventory().offerOrDrop(blockEntity.getStack(0));
				blockEntity.removeStack(0);
			}
		}
		return ActionResult.SUCCESS;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
//
//	@Override
//	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
//		return checkType(type, EMBER_BORE_BLOCK_ENTITY, (world1, pos, state1, be) -> be.tick(world1, pos, state1, be));
//	}
}
