package com.quairix.fabric.embers;

import static com.quairix.fabric.embers.block.EmberBoreBlock.EMBER_BORE_BLOCK;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quairix.fabric.embers.base.EmbersEvents;
import com.quairix.fabric.embers.base.EmbersNetworkProvider;
import com.quairix.fabric.embers.block.ChargeableBlock;
import com.quairix.fabric.embers.block.DemoBlock;
import com.quairix.fabric.embers.block.EmberBoreBlock;
import com.quairix.fabric.embers.block.MythrilBlock;
import com.quairix.fabric.embers.block.MythrilOreBlock;
import com.quairix.fabric.embers.block.NickelOreBlock;
import com.quairix.fabric.embers.entity.ChargeableBlockEntity;
import com.quairix.fabric.embers.entity.DemoBlockEntity;
import com.quairix.fabric.embers.entity.EmberBoreBlockEntity;
import com.quairix.fabric.embers.item.MystFuelItem;
import com.quairix.fabric.embers.item.MythrilIngotItem;
import com.simibubi.create.Create;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;

public class EmbersMod implements ModInitializer {
	public static final String ID = "embers";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	public static final ItemGroup MYST_ITEM_GROUP = FabricItemGroupBuilder.build(
			new Identifier("ember", "general"),
			() -> new ItemStack(Blocks.COBBLESTONE));

	public static final ItemGroup MYST_OTHER_GROUP = FabricItemGroupBuilder.create(
					new Identifier("ember", "other"))
			.icon(() -> new ItemStack(Items.BOWL))
			.appendItems(stacks -> {
				stacks.add(new ItemStack(Blocks.BONE_BLOCK));
				stacks.add(new ItemStack(Items.APPLE));
				stacks.add(PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER));
				stacks.add(new ItemStack(Items.IRON_SHOVEL));
			})
			.build();

	//Мои инициализаторы - не понимаешь спрашивай. Делитать нельзя
	public static final EmbersNetworkProvider EMBERS_NETWORK_PROVIDER = new EmbersNetworkProvider();

	@Override
	public void onInitialize() {

		LOGGER.info("Init modId [{}] with version [{}]!", ID, Create.VERSION);
		DemoBlockEntity.onInit();
		DemoBlock.onInitialize();
		ChargeableBlockEntity.onInit();
		ChargeableBlock.onInitialize();
		EmberBoreBlockEntity.onInit();
		EmberBoreBlock.onInitialize();
		BlockRenderLayerMap.INSTANCE.putBlock(EMBER_BORE_BLOCK, RenderLayer.getCutout());
		MystFuelItem.registerItem();
		NickelOreBlock.onInitialize();
		MythrilOreBlock.onInitialize();
		MythrilBlock.onInitialize();
		MythrilIngotItem.registerItem();

		//Автобус событий - тут хуки на все события кубача
		EmbersEvents.register();
	}
}
