package com.quairix.fabric.embers.entity;

import static com.quairix.fabric.embers.block.DemoBlock.DEMO_BLOCK;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.quairix.fabric.embers.block.base.ChargeableInterfaceBlock;

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

public class ChargeableBlockEntity extends BlockEntity implements ChargeableInterfaceBlock {
	public Long energyValue = 100L;
	public Long maxEnergyValue = 100L;

	public static BlockEntityType<ChargeableBlockEntity> CHARGABLE_BLOCK_ENTITY;

	public ChargeableBlockEntity(@Nonnull BlockPos pos, BlockState state) {
		super(CHARGABLE_BLOCK_ENTITY, pos, state);
	}

	public void tick(World world, BlockPos pos, BlockState state, ChargeableBlockEntity be) {
		if (getChargeValue() < getMaxChargeValue()) {
			addChargeValue(1L);
		}
	}

	// Serialize the BlockEntity
	@Override
	public void writeNbt(NbtCompound tag) {
		// Save the current value of the number to the tag
		tag.putLong("energyValue", getChargeValue());
		tag.putLong("maxEnergyValue", getMaxChargeValue());

		super.writeNbt(tag);
	}

	// Deserialize the BlockEntity
	@Override
	public void readNbt(NbtCompound tag) {
		super.readNbt(tag);

		setChargeValue( tag.getLong("energyValue"));
		setMaxChargeValue(tag.getLong("maxEnergyValue"));
	}

	@Override
	public Long getChargeValue() {
		return energyValue;
	}

	@Override
	public void setChargeValue(Long value) {
		energyValue = value;
	}

	@Override
	public void addChargeValue(Long value) {
		energyValue += value;
	}

	@Override
	public Long getMaxChargeValue() {
		return maxEnergyValue;
	}

	@Override
	public Boolean canCharge() {
		return true;
	}

	@Override
	public Boolean canBeCharged() {
		return false;
	}

	@Override
	public void setMaxChargeValue(final Long value) {
		maxEnergyValue = value;
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
		CHARGABLE_BLOCK_ENTITY = Registry.register(
				Registry.BLOCK_ENTITY_TYPE, new Identifier("embers", "chargeable_block_entity"),
				FabricBlockEntityTypeBuilder.create(ChargeableBlockEntity::new, DEMO_BLOCK).build(null));
	}
}
