package com.quairix.fabric.embers.util;

import javax.annotation.Nonnull;

import net.minecraft.util.Identifier;

public class EmberUtils {
	public static Identifier getIdentifier(@Nonnull final String name) {
		return new Identifier("embers", name);
	}
}
