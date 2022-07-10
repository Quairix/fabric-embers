package com.quairix.fabric.embers.block.base;

import com.quairix.fabric.embers.entity.ChargeableBlockEntity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ChargeableInterfaceBlock {
	Long getChargeValue();

	void setChargeValue(Long value);

	default void addChargeValue(Long value) {
		setChargeValue(getChargeValue() + value);
	}

	Long getMaxChargeValue();

	void setMaxChargeValue(Long value);

	Boolean canCharge();

	Boolean canBeCharged();

	void tick(World world, BlockPos pos, BlockState state, ChargeableBlockEntity be);
}
