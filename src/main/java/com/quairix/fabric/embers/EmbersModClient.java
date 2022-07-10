package com.quairix.fabric.embers;

import static com.quairix.fabric.embers.entity.DemoBlockEntity.DEMO_BLOCK_ENTITY;

import com.quairix.fabric.embers.entityRenderer.DemoBlockEntityRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class EmbersModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
//		BlockEntityRendererRegistry.register(EMBER_BORE_BLOCK_ENTITY, DemoBlockEntityRenderer::new);
		BlockEntityRendererRegistry.register(DEMO_BLOCK_ENTITY, DemoBlockEntityRenderer::new);
	}
}
