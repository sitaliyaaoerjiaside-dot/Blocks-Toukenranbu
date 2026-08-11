package com.Equatorial.toukenranbu.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class YariPLUSEntity extends YariEntity {

    public YariPLUSEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 56.0D)
                .add(Attributes.ATTACK_DAMAGE, 12.0f)
                .add(Attributes.ATTACK_SPEED, 0.4f)
                .add(Attributes.ARMOR, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23f)
                .add(Attributes.FOLLOW_RANGE, 18.0D)
                .build();
    }
}