package com.quairix.fabric.embers;

import com.quairix.fabric.embers.block.NickelOreBlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quairix.fabric.embers.item.MystFuelItem;
import com.simibubi.create.Create;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
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
				stacks.add(ItemStack.EMPTY);
				stacks.add(new ItemStack(Items.IRON_SHOVEL));
			})
			.build();

	@Override
	public void onInitialize() {

		LOGGER.info("Init modId [{}] with version [{}]!", ID, Create.VERSION);
		MystFuelItem.registerItem();
		NickelOreBlock.onInitialize();
	}
}
