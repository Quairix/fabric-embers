package com.quairix.fabric.embers.block;

import java.util.Arrays;

import com.quairix.fabric.embers.util.EmberUtils;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

public class MythrilOreBlock extends OreBlock {

	public static final Block MYTHRIL_ORE_BLOCK = new MythrilOreBlock(FabricBlockSettings.of(Material.METAL).strength(3.0F, 3.0F).requiresTool());

	// todo: генерация в структуре
	private static final ConfiguredFeature<?, ?> OVERWORLD_MYTHRIL_ORE_CONFIGURED_FEATURE = new ConfiguredFeature
			(Feature.ORE, new OreFeatureConfig(
					OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
					MYTHRIL_ORE_BLOCK.getDefaultState(),
					9)); // vein size

	public static final PlacedFeature OVERWORLD_MYTHRIL_ORE_PLACED_FEATURE = new PlacedFeature(
			RegistryEntry.of(OVERWORLD_MYTHRIL_ORE_CONFIGURED_FEATURE),
			Arrays.asList(
					CountPlacementModifier.of(20), // number of veins per chunk
					SquarePlacementModifier.of(), // spreading horizontally
					HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(64))
			)); // height

	public MythrilOreBlock(final Settings settings) {
		super(settings);
	}

	public static void onInitialize() {
		Registry.register(Registry.BLOCK, EmberUtils.getIdentifier("mythril_ore_block"), MYTHRIL_ORE_BLOCK);
		Registry.register(Registry.ITEM, EmberUtils.getIdentifier("mythril_ore_block"), new BlockItem(MYTHRIL_ORE_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
				EmberUtils.getIdentifier("overworld_mythril_ore"), OVERWORLD_MYTHRIL_ORE_CONFIGURED_FEATURE);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, EmberUtils.getIdentifier("overworld_mythril_ore"),
				OVERWORLD_MYTHRIL_ORE_PLACED_FEATURE);
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES,
				RegistryKey.of(Registry.PLACED_FEATURE_KEY,
						EmberUtils.getIdentifier("overworld_mythril_ore")));
	}
}
