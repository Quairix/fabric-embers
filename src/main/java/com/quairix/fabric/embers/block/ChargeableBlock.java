package com.quairix.fabric.embers.block;

import static com.quairix.fabric.embers.entity.DemoBlockEntity.DEMO_BLOCK_ENTITY;

import java.util.Objects;

import org.jetbrains.annotations.Nullable;

import com.quairix.fabric.embers.entity.ChargeableBlockEntity;
import com.quairix.fabric.embers.util.EmberUtils;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ChargeableBlock extends BlockWithEntity {
	public static final BooleanProperty CHARGED = BooleanProperty.of("charged");
	// The block instance. You can place it anywhere.
	public static final ChargeableBlock CHARGEABLE_BLOCK = new ChargeableBlock(FabricBlockSettings.of(Material.STONE).strength(4.0f));

	public ChargeableBlock(Settings settings) {
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(CHARGED, false));
	}

	public static void onInitialize() {
		Registry.register(Registry.BLOCK, EmberUtils.getIdentifier("chargeable_block"), CHARGEABLE_BLOCK);
		Registry.register(Registry.ITEM, EmberUtils.getIdentifier("chargeable_block"), new BlockItem(CHARGEABLE_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(CHARGED);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		player.playSound(SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, 1, 1);
		final var entity = world.getBlockEntity(pos);
		if (entity == null || !(entity instanceof final ChargeableBlockEntity chargeableBlockEntity)) {
			return ActionResult.FAIL;
		}
		player.sendMessage(new LiteralText("Charge status: " +
				chargeableBlockEntity.getChargeValue() + " / " + chargeableBlockEntity.getMaxChargeValue()), false);
		world.setBlockState(pos, state.with(CHARGED, true));
		return ActionResult.SUCCESS;
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (Boolean.TRUE.equals(world.getBlockState(pos).get(CHARGED))){
			// Summoning the Lighting Bolt at the block
			LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
			Objects.requireNonNull(lightningEntity).refreshPositionAfterTeleport(Vec3d.ofBottomCenter(pos));
			world.spawnEntity(lightningEntity);
			final var blockEntity = world.getBlockEntity(pos);
			if (entity != null && blockEntity instanceof final ChargeableBlockEntity chargeableBlockEntity) {
				chargeableBlockEntity.setChargeValue(0L);
			}
		}

		world.setBlockState(pos, state.with(CHARGED, false));
		super.onSteppedOn(world, pos, state, entity);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(final BlockPos pos, final BlockState state) {
		return new DispenserBlockEntity(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, DEMO_BLOCK_ENTITY, (world1, pos, state1, be) ->  be.tick(world1, pos, state1, be));
	}
}
