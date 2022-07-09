package com.quairix.fabric.embers.entity;

import static com.quairix.fabric.embers.block.DemoBlock.DEMO_BLOCK;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class EmberBoreBlockEntity extends BlockEntity {

	private int number = 7;
//	private boolean isLava = false;
	public static BlockEntityType<EmberBoreBlockEntity> EMBER_BORE_BLOCK_ENTITY;

	public EmberBoreBlockEntity(@Nonnull BlockPos pos, BlockState state) {
		super(EMBER_BORE_BLOCK_ENTITY, pos, state);
	}

	public void tick(World world, BlockPos pos, BlockState state, EmberBoreBlockEntity be) {
//		if (number++ > 5) {
//			world.setBlockState(pos.add(0, 0, 1), isLava ? Blocks.REDSTONE_BLOCK.getDefaultState() : Blocks.GOLD_BLOCK.getDefaultState());
//			number = 0;
//			isLava = !isLava;
//		}
	}

	// Serialize the BlockEntity
	@Override
	public void writeNbt(NbtCompound tag) {
		// Save the current value of the number to the tag
		tag.putInt("number", number);

		super.writeNbt(tag);
	}

	// Deserialize the BlockEntity
	@Override
	public void readNbt(NbtCompound tag) {
		super.readNbt(tag);

		number = tag.getInt("number");
	}

	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt() {
		return createNbt();
	}

	public static void onInit() {
		EMBER_BORE_BLOCK_ENTITY = Registry.register(
				Registry.BLOCK_ENTITY_TYPE, new Identifier("embers", "ember_bore_block_entity"),
				FabricBlockEntityTypeBuilder.create(EmberBoreBlockEntity::new, DEMO_BLOCK).build(null));
	}
}
