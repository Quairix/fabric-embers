package com.quairix.fabric.embers.item;

import static com.quairix.fabric.embers.EmbersMod.MYST_ITEM_GROUP;

import java.util.List;

import com.quairix.fabric.embers.util.EmberUtils;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class MystFuelItem extends Item {

	public static final Item MYST_FUEL_ITEM = new MystFuelItem(new FabricItemSettings()
			.group(MYST_ITEM_GROUP)
			.maxCount(64));

	public static void registerItem() {
		Registry.register(Registry.ITEM, EmberUtils.getIdentifier("myst_fuel_item"), MYST_FUEL_ITEM);
		FuelRegistry.INSTANCE.add(MYST_FUEL_ITEM, 3000);
	}

	public MystFuelItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		playerEntity.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0F, 1.0F);
		return TypedActionResult.success(playerEntity.getStackInHand(hand));
	}

	@Override
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {

		// default white text
		tooltip.add( new TranslatableText("item.embers.myst_fuel_item.tooltip") );
	}
}
