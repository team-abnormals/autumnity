package com.teamabnormals.autumnity.core.other;

import com.teamabnormals.autumnity.common.advancements.criterion.FeedSnailTrigger;
import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.advancements.CriteriaTriggers;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = Autumnity.MOD_ID)
public class AutumnityCriteriaTriggers {
	public static final FeedSnailTrigger FEED_SNAIL = CriteriaTriggers.register(new FeedSnailTrigger());
	public static final EmptyTrigger CURE_FOUL_TASTE = CriteriaTriggers.register(new EmptyTrigger(Autumnity.location("cure_foul_taste")));
}