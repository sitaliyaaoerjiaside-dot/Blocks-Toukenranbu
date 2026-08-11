package com.Equatorial.toukenranbu.datagen;

import com.Equatorial.toukenranbu.entity.ModEntityTypes;
import com.Equatorial.toukenranbu.item.ModItems;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.stream.Stream;

public class ModEntityLootTablesProvider extends EntityLootSubProvider {

    public ModEntityLootTablesProvider() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        // ===== 普通时间溯行军 =====
        addTantouLoot();
        addWakizashiLoot();
        addJikkoBaseLoot(ModEntityTypes.UCHIGATANA.get(), 1, 3);
        addJikkoBaseLoot(ModEntityTypes.TACHI.get(), 2, 3);
        addJikkoBaseLoot(ModEntityTypes.OOTACHI.get(), 2, 4);
        addJikkoBaseLoot(ModEntityTypes.YARI.get(), 1, 3);
        addJikkoBaseLoot(ModEntityTypes.NAGINATA.get(), 2, 4);

        // ===== 特化时间溯行军 =====
        addJikkoPlusLoot(ModEntityTypes.TANTOU_PLUS.get(), 2, 4);
        addJikkoPlusLoot(ModEntityTypes.WAKIZASHI_PLUS.get(), 2, 4);
        addJikkoPlusLoot(ModEntityTypes.UCHIGATANA_PLUS.get(), 2, 5);
        addJikkoPlusLoot(ModEntityTypes.TACHI_PLUS.get(), 3, 5);
        addJikkoPlusLoot(ModEntityTypes.OOTACHI_PLUS.get(), 3, 6);
        addJikkoPlusLoot(ModEntityTypes.YARI_PLUS.get(), 2, 5);
        addJikkoPlusLoot(ModEntityTypes.NAGINATA_PLUS.get(), 3, 6);

        // ===== 极化时间溯行军 =====
        addJikkoMaxLoot(ModEntityTypes.TANTOU_MAX.get(), 3, 5);
        addJikkoMaxLoot(ModEntityTypes.WAKIZASHI_MAX.get(), 3, 5);
        addJikkoMaxLoot(ModEntityTypes.UCHIGATANA_MAX.get(), 3, 6);
        addJikkoMaxLoot(ModEntityTypes.TACHI_MAX.get(), 4, 6);
        addJikkoMaxLoot(ModEntityTypes.OOTACHI_MAX.get(), 4, 7);
        addJikkoMaxLoot(ModEntityTypes.YARI_MAX.get(), 3, 6);
        addJikkoMaxLoot(ModEntityTypes.NAGINATA_MAX.get(), 4, 7);

        // ===== 检非违使队员 =====
        addKebiishiCommonLoot(ModEntityTypes.KEBIISHI_TACHI.get());
        addKebiishiCommonLoot(ModEntityTypes.KEBIISHI_OOTACHI.get());
        addKebiishiCommonLoot(ModEntityTypes.KEBIISHI_YARI.get());
        addKebiishiCommonLoot(ModEntityTypes.KEBIISHI_NAGINATA.get());

        // ===== 检非违使队长 =====
        addKebiishiLeaderLoot(ModEntityTypes.KEBIISHI_LEADER.get());
    }

    // 短刀专属：骨头 + 10%浑浊灵力
    private void addTantouLoot() {
        add(ModEntityTypes.TANTOU.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
                        .add(LootItem.lootTableItem(Items.BONE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                )
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
    }

    // 胁差专属：蜘蛛眼 + 10%浑浊灵力
    private void addWakizashiLoot() {
        add(ModEntityTypes.WAKIZASHI.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
                        .add(LootItem.lootTableItem(Items.SPIDER_EYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                )
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
    }

    // 其他普通溯行军基础掉落
    private void addJikkoBaseLoot(EntityType<?> type, int ironMin, int ironMax) {
        add(type, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(ironMin, ironMax))))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
                )
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
    }

    // 特化溯行军
    private void addJikkoPlusLoot(EntityType<?> type, int ironMin, int ironMax) {
        add(type, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(ironMin, ironMax))))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                )
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
    }

    // 极化溯行军
    private void addJikkoMaxLoot(EntityType<?> type, int ironMin, int ironMax) {
        add(type, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(ironMin, ironMax))))
                        .add(LootItem.lootTableItem(ModItems.COOLANT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                        .add(LootItem.lootTableItem(ModItems.WHETSTONE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                        .add(LootItem.lootTableItem(ModItems.WOOTZ_STEEL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                )
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
    }

    // 检非违使普通队员
    private void addKebiishiCommonLoot(EntityType<?> type) {
        add(type, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.GOLD_OMAMORI.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                )
        );
    }

    // 检非违使队长：金苹果 + 经验瓶 + 5%三日月宗近
    private void addKebiishiLeaderLoot(EntityType<?> type) {
        add(type, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(2))
                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                )
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.MIKAZUKI_MUNECHIKA.get())
                                .setWeight(5))
                        .add(LootItem.lootTableItem(ModItems.GOLD_OMAMORI.get())
                                .setWeight(95)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                )
        );
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return Stream.of(
                ModEntityTypes.TANTOU.get(), ModEntityTypes.TANTOU_PLUS.get(), ModEntityTypes.TANTOU_MAX.get(),
                ModEntityTypes.WAKIZASHI.get(), ModEntityTypes.WAKIZASHI_PLUS.get(), ModEntityTypes.WAKIZASHI_MAX.get(),
                ModEntityTypes.UCHIGATANA.get(), ModEntityTypes.UCHIGATANA_PLUS.get(), ModEntityTypes.UCHIGATANA_MAX.get(),
                ModEntityTypes.TACHI.get(), ModEntityTypes.TACHI_PLUS.get(), ModEntityTypes.TACHI_MAX.get(),
                ModEntityTypes.OOTACHI.get(), ModEntityTypes.OOTACHI_PLUS.get(), ModEntityTypes.OOTACHI_MAX.get(),
                ModEntityTypes.YARI.get(), ModEntityTypes.YARI_PLUS.get(), ModEntityTypes.YARI_MAX.get(),
                ModEntityTypes.NAGINATA.get(), ModEntityTypes.NAGINATA_PLUS.get(), ModEntityTypes.NAGINATA_MAX.get(),
                ModEntityTypes.KEBIISHI_TACHI.get(), ModEntityTypes.KEBIISHI_OOTACHI.get(),
                ModEntityTypes.KEBIISHI_YARI.get(), ModEntityTypes.KEBIISHI_NAGINATA.get(),
                ModEntityTypes.KEBIISHI_LEADER.get()
        );
    }
}