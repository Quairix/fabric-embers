package com.quairix.fabric.embers.item;

import static com.quairix.fabric.embers.EmbersMod.MYST_ITEM_GROUP;

import com.quairix.fabric.embers.util.EmberUtils;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class MythrilIngotItem extends Item {

	public static final Item MYTHRIL_INGOT_ITEM = new MythrilIngotItem(new FabricItemSettings()
			.group(MYST_ITEM_GROUP)
			.maxCount(64));

	public MythrilIngotItem(Settings settings) {
		super(settings);
	}

	public static void registerItem() {
		Registry.register(Registry.ITEM, EmberUtils.getIdentifier("mythril_ingot_item"), MYTHRIL_INGOT_ITEM);
	}
}
