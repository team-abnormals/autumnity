package com.markus1002.autumnity.core.other;

import com.markus1002.autumnity.common.advancements.criterion.FeedSnailTrigger;
import com.markus1002.autumnity.core.Reference;
import com.teamabnormals.abnormals_core.common.advancement.EmptyTrigger;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class AutumnityCriteriaTriggers
{
	public static final FeedSnailTrigger FEED_SNAIL = CriteriaTriggers.register(new FeedSnailTrigger());
	public static final EmptyTrigger CURE_FOUL_TASTE = CriteriaTriggers.register(new EmptyTrigger(Reference.location("cure_foul_taste")));
}