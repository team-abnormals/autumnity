package com.markus1002.autumnity.core;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config
{
    public static class Common
    {
        public final ForgeConfigSpec.ConfigValue<List<String>> snailSpawnBiomes;
        public final ForgeConfigSpec.ConfigValue<List<String>> snailBreedingItems;
        public final ForgeConfigSpec.ConfigValue<List<String>> snailFood;
        public final ForgeConfigSpec.ConfigValue<List<String>> snailBlockFood;
        public final ForgeConfigSpec.ConfigValue<List<String>> slipperySnailSlimeBlocks;
        public final ForgeConfigSpec.ConfigValue<List<String>> mapleTreeBiomes;
		public final ForgeConfigSpec.ConfigValue<Integer> mapleForestWeight;
		public final ForgeConfigSpec.ConfigValue<Integer> pumpkinFieldsWeight;

        Common(ForgeConfigSpec.Builder builder)
        {
            builder.push("snail");
            snailSpawnBiomes = builder
                    .comment("A list of biomes where snails can spawn. The list doesn't include maple forests.")
                    .define("Snail Spawn Biomes", Lists.newArrayList());
            snailBreedingItems = builder
                    .comment("A list of items that can be used to breed snails.")
                    .define("Snail Breeding Items", Lists.newArrayList("minecraft:mushroom_stew", "minecraft:suspicious_stew"));
            snailFood = builder
                    .comment("A list of items that can be fed to snails to produce snail slime.")
                    .define("Snail Food", Lists.newArrayList("minecraft:brown_mushroom", "minecraft:red_mushroom", "minecraft:crimson_fungus", "minecraft:warped_fungus", "quark:glowshroom"));
            snailBlockFood = builder
                    .comment("A list of blocks snails can eat to produce snail slime.")
                    .define("Snail Block Food", Lists.newArrayList("minecraft:brown_mushroom", "minecraft:red_mushroom", "minecraft:crimson_fungus", "minecraft:warped_fungus", "quark:glowshroom"));
            slipperySnailSlimeBlocks = builder
                    .comment("A list of blocks that make snail slime blocks slippery when placed next to them.")
                    .define("Slippery Snail Slime Blocks", Lists.newArrayList("minecraft:wet_sponge"));
            builder.pop();
            builder.push("biomes");
            mapleTreeBiomes = builder
                    .comment("A list of biomes where maple trees can naturally generate. The list doesn't include maple forests.")
                    .define("Maple Tree Biomes", Lists.newArrayList("minecraft:forest", "minecraft:wooded_hills", "minecraft:flower_forest"));
            mapleForestWeight = builder
                    .comment("The greater the number the more common the biome is.")
                    .define("Maple Forest Weight", 6);
            pumpkinFieldsWeight = builder
                    .comment("The greater the number the more common the biome is.")
                    .define("Pumpkin Fields Weight", 1);
            builder.pop();
        }
    }

    static final ForgeConfigSpec COMMON_SPEC;
    public static final Config.Common COMMON;

    static
    {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config.Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}