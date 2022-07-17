package com.quairix.fabric.embers.block;

import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;

import com.quairix.fabric.embers.entity.EmberBoreBlockEntity;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;


public class EmberBoreBlock extends BlockWithEntity {

	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	public static final BooleanProperty LIT = Properties.LIT;
	public static final Block EMBER_BORE_BLOCK = new EmberBoreBlock(FabricBlockSettings.of(Material.STONE).strength(0.3f));

	public EmberBoreBlock(final Settings settings) {
		super(settings.nonOpaque());
	}

	public static void onInitialize() {
		Registry.register(Registry.BLOCK, new Identifier("embers", "ember_bore_block"), EMBER_BORE_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("embers", "ember_bore_block"), new BlockItem(EMBER_BORE_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
	}

	@Override
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new EmberBoreBlockEntity(pos, state);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
		if (state.getBlock() instanceof EmberBoreBlock) {
			Direction dir = state.get(FACING);
			return switch (dir) {
				case NORTH -> VoxelShapes.cuboid(-1.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
				case WEST -> VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 2.0f, 2.0f, 1.0f);
				case SOUTH -> VoxelShapes.cuboid(0.0f, 0.0f, -1.0f, 1.0f, 2.0f, 1.0f);
				case EAST -> VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 2.0f);
				default -> VoxelShapes.fullCube();
			};
		}
		return VoxelShapes.fullCube();
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, LIT);
	}

	@Override
	public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
//		if (world.isClient) return ActionResult.SUCCESS;
		final var blockEntityBase = world.getBlockEntity(blockPos);

		if (blockEntityBase == null || !(blockEntityBase instanceof final EmberBoreBlockEntity blockEntity)) {
			return ActionResult.FAIL;
		}

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
	public void onBroken(final WorldAccess world, final BlockPos pos, final BlockState state) {
		super.onBroken(world, pos, state);
	}

	//
//	@Override
//	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
//		return checkType(type, EMBER_BORE_BLOCK_ENTITY, (world1, pos, state1, be) -> be.tick(world1, pos, state1, be));
//	}
}
