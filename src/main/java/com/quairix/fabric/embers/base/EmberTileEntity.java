package com.quairix.fabric.embers.base;

import com.simibubi.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.contraptions.goggles.IHaveHoveringInformation;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;

import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;

import io.github.fabricators_of_create.porting_lib.block.CustomRenderBoundingBoxBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

import java.util.List;

public class EmberTileEntity extends SmartTileEntity
	implements IHaveGoggleInformation, IHaveHoveringInformation, CustomRenderBoundingBoxBlockEntity {

	public @Nullable Long network;
	public @Nullable BlockPos source;
	public boolean updateSpeed;

	protected float capacity;
	protected  boolean wasMoved;

	private int flickerTally;
	private  int networkSize;
	private int validationCountdown;
	protected float lastCapacityProvided;

	public EmberTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
		updateSpeed = true;
	}

	@Override
	public void addBehaviours(List<TileEntityBehaviour> behaviours) {

	}

	@Override
	public void initialize() {
		/*if (hasNetwork() && !world.isClient){
			EmberNetwork network = getOrCreateNetwork();
			if (!network.initialized)
				network.initFromTE(capacity,networkSize);
			network.addSilently(this,lastCapacityProvided);
		}*/
		super.initialize();
	}


	public boolean hasNetwork() {
		return network != null;
	}
}
