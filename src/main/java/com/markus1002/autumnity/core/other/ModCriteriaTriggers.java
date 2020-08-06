package com.markus1002.autumnity.core.other;

import com.markus1002.autumnity.common.advancements.criterion.FeedSnailTrigger;
import com.markus1002.autumnity.core.Reference;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class ModCriteriaTriggers
{
	public static final FeedSnailTrigger FEED_SNAIL = CriteriaTriggers.register(new FeedSnailTrigger());
}