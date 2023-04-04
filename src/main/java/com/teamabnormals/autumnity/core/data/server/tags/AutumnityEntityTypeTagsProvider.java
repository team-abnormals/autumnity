package com.teamabnormals.autumnity.core.data.server.tags;

import com.teamabnormals.autumnity.core.Autumnity;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.teamabnormals.autumnity.core.other.tags.AutumnityEntityTypeTags.*;

public class AutumnityEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public AutumnityEntityTypeTagsProvider(DataGenerator generator, ExistingFileHelper fileHelper) {
        super(generator, Autumnity.MOD_ID, fileHelper);
    }

    @Override
    public void addTags() {
        this.tag(CAN_WEAR_TURKEY).add(EntityType.PLAYER, EntityType.ZOMBIE, EntityType.HUSK, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.ZOMBIFIED_PIGLIN).addTag(EntityTypeTags.SKELETONS);
    }
}
