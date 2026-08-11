package com.Equatorial.toukenranbu.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class OotachiPLUSEntity extends OotachiEntity {

    public OotachiPLUSEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 78.0D)
                .add(Attributes.ATTACK_DAMAGE, 9.0f)
                .add(Attributes.ATTACK_SPEED, 0.55f)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15f)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .build();
    }
}