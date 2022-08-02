package com.teamabnormals.autumnity.core.other;

import com.teamabnormals.autumnity.common.advancements.criterion.FeedSnailTrigger;
import com.teamabnormals.autumnity.core.Autumnity;
import com.teamabnormals.blueprint.common.advancement.EmptyTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Autumnity.MOD_ID)
public class AutumnityCriteriaTriggers {
	public static final FeedSnailTrigger FEED_SNAIL = CriteriaTriggers.register(new FeedSnailTrigger());
	public static final EmptyTrigger CURE_FOUL_TASTE = CriteriaTriggers.register(new EmptyTrigger(new ResourceLocation(Autumnity.MOD_ID, "cure_foul_taste")));
}