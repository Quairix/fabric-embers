package com.quairix.fabric.embers.block;

import java.util.Objects;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ChargeableBlock extends Block {
	public static final BooleanProperty CHARGED = BooleanProperty.of("charged");
	// The block instance. You can place it anywhere.
	public static final ChargeableBlock CHARGEABLE_BLOCK = Registry.register(Registry.BLOCK, new Identifier("embers", "chargeable_block"),
			new ChargeableBlock(FabricBlockSettings.of(Material.STONE).strength(4.0f)));

	public ChargeableBlock(Settings settings) {
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(CHARGED, false));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(CHARGED);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		player.playSound(SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, 1, 1);
		world.setBlockState(pos, state.with(CHARGED, true));
		return ActionResult.SUCCESS;
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (world.getBlockState(pos).get(CHARGED)){
			// Summoning the Lighting Bolt at the block
			LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
			Objects.requireNonNull(lightningEntity).refreshPositionAfterTeleport(Vec3d.ofBottomCenter(pos));
			world.spawnEntity(lightningEntity);
		}

		world.setBlockState(pos, state.with(CHARGED, false));
		super.onSteppedOn(world, pos, state, entity);
	}
}
