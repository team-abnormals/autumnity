package com.minecraftabnormals.autumnity.core.other;

import com.minecraftabnormals.abnormals_core.common.advancement.EmptyTrigger;
import com.minecraftabnormals.autumnity.common.advancements.criterion.FeedSnailTrigger;
import com.minecraftabnormals.autumnity.core.Autumnity;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Autumnity.MOD_ID)
public class AutumnityCriteriaTriggers {
	public static final FeedSnailTrigger FEED_SNAIL = CriteriaTriggers.register(new FeedSnailTrigger());
	public static final EmptyTrigger CURE_FOUL_TASTE = CriteriaTriggers.register(new EmptyTrigger(new ResourceLocation(Autumnity.MOD_ID, "cure_foul_taste")));
}