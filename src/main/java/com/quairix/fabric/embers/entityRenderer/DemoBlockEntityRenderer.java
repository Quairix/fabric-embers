package com.quairix.fabric.embers.entityRenderer;

import com.quairix.fabric.embers.entity.DemoBlockEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class DemoBlockEntityRenderer implements BlockEntityRenderer<DemoBlockEntity> {

	private static ItemStack stack = new ItemStack(Items.JUKEBOX, 1);
	public DemoBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

	@Override
	public void render(DemoBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
//		final var stack = blockEntity.getStack(0);
		if (stack == null || !stack.isEmpty()){
			return;
		}

		// Calculate the current offset in the y value
		double offset = Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 8.0) / 4.0;
		// Move the item
		matrices.translate(0.5, 1.25 + offset, 0.5);
		// Rotate the item
		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));

		int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
		MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);

		// Mandatory call after GL calls
		matrices.pop();
	}
}
