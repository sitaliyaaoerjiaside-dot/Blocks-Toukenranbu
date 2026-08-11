package com.Equatorial.toukenranbu.entity.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class YariMAXEntity extends YariEntity {

    public YariMAXEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 75.0D)
                .add(Attributes.ATTACK_DAMAGE, 30.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.ARMOR, 14.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4f)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0D)
                .build();
    }

    // 你这就不知道了，因为作者在本体游戏里深受高速枪的伤害，所以不让他进船是由于作者的怨念导致的。
    //为了保证极化枪的强度，如果整合包作者要把本模组加进整合包里，那么这里就是可以改数值的。
    // 不论改动如何，极化枪的伤害不允许低于30。（太难了可以自己试着改改，不过我觉得加的模组多了应该是不会的吧......？）
    //极化枪的伤害是真实伤害，并且在其之上的检非违使怪物还会额外往上拉上限，建议出门多带点人。
    //还有就是抗性提升最多-50％的伤害，想要完全免疫是不可能的。

    @Override
    public boolean startRiding(Entity entity, boolean force) {
        //极化枪不会被船困住。
        if (entity instanceof net.minecraft.world.entity.vehicle.Boat
                || entity instanceof net.minecraft.world.entity.vehicle.AbstractMinecart) {
            return false;
        }
        return super.startRiding(entity, force);
    }
}