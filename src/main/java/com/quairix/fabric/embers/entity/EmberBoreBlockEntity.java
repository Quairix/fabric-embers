package com.quairix.fabric.embers.entity;

import static com.quairix.fabric.embers.block.DemoBlock.DEMO_BLOCK;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.quairix.fabric.embers.inventory.ImplementedInventory;
import com.quairix.fabric.embers.util.EmberUtils;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public class EmberBoreBlockEntity extends BlockEntity implements ImplementedInventory, SidedInventory {

	private int number = 7;

	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);
	public static BlockEntityType<EmberBoreBlockEntity> EMBER_BORE_BLOCK_ENTITY;

	public EmberBoreBlockEntity(@Nonnull BlockPos pos, BlockState state) {
		super(EMBER_BORE_BLOCK_ENTITY, pos, state);
	}


	@Override
	public DefaultedList<ItemStack> getItems() {
		return items;
	}

	@Override
	public int[] getAvailableSlots(Direction var1) {
		// Just return an array of all slots
		int[] result = new int[getItems().size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = i;
		}

		return result;
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, Direction direction) {
		return direction != Direction.UP;
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction direction) {
		return true;
	}

	// Serialize the BlockEntity
	@Override
	public void writeNbt(NbtCompound tag) {
		// Save the current value of the number to the tag
		tag.putInt("number", number);

		Inventories.writeNbt(tag, items);

		super.writeNbt(tag);
	}

	// Deserialize the BlockEntity
	@Override
	public void readNbt(NbtCompound tag) {
		super.readNbt(tag);

		Inventories.readNbt(tag, items);
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
				Registry.BLOCK_ENTITY_TYPE, EmberUtils.getIdentifier("ember_bore_block_entity"),
				FabricBlockEntityTypeBuilder.create(EmberBoreBlockEntity::new, DEMO_BLOCK).build(null));
	}
}
