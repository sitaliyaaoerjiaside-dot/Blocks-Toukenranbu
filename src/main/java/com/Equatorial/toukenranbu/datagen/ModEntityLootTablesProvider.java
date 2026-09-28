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
        // 普通
        addTantouLoot();
        addWakizashiLoot();
        addUchigatanaLoot();
        addTachiLoot();
        addOotachiLoot();
        addYariLoot();
        addNaginataLoot();

        // 特化
        addTantouPlusLoot();
        addWakizashiPlusLoot();
        addUchigatanaPlusLoot();
        addTachiPlusLoot();
        addOotachiPlusLoot();
        addYariPlusLoot();
        addNaginataPlusLoot();

        // 极化
        addTantouMaxLoot();
        addWakizashiMaxLoot();
        addUchigatanaMaxLoot();
        addTachiMaxLoot();
        addOotachiMaxLoot();
        addYariMaxLoot();
        addNaginataMaxLoot();

        // 检非违使
        addKebiishiCommonLoot(ModEntityTypes.KEBIISHI_TACHI.get());
        addKebiishiCommonLoot(ModEntityTypes.KEBIISHI_OOTACHI.get());
        addKebiishiCommonLoot(ModEntityTypes.KEBIISHI_YARI.get());
        addKebiishiCommonLoot(ModEntityTypes.KEBIISHI_NAGINATA.get());
        addKebiishiLeaderLoot(ModEntityTypes.KEBIISHI_LEADER.get());

        // BOSS
        addWhiteMikazukiMunechikaLoot();
    }

    // ==================== 短刀 ====================
    private void addTantouLoot() {
        add(ModEntityTypes.TANTOU.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
                        .add(LootItem.lootTableItem(Items.BONE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.IMANOTSURUGI.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.GOTOU_TOUSHIROU.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.2f))
                )
        );
    }

    private void addTantouPlusLoot() {
        add(ModEntityTypes.TANTOU_PLUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.IMANOTSURUGI.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.GOTOU_TOUSHIROU.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.2f))
                )
        );
    }

    private void addTantouMaxLoot() {
        add(ModEntityTypes.TANTOU_MAX.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 5))))
                        .add(LootItem.lootTableItem(ModItems.COOLANT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                        .add(LootItem.lootTableItem(ModItems.WHETSTONE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                        .add(LootItem.lootTableItem(ModItems.WOOTZ_STEEL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.IMANOTSURUGI.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.GOTOU_TOUSHIROU.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.2f))
                )
        );
    }

    // ==================== 胁差 ====================
    private void addWakizashiLoot() {
        add(ModEntityTypes.WAKIZASHI.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
                        .add(LootItem.lootTableItem(Items.SPIDER_EYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.NIKKARI_AOE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.HORIKAWA_KUNIHIRO.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.2f))
                )
        );
    }

    private void addWakizashiPlusLoot() {
        add(ModEntityTypes.WAKIZASHI_PLUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.NIKKARI_AOE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.HORIKAWA_KUNIHIRO.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.2f))
                )
        );
    }

    private void addWakizashiMaxLoot() {
        add(ModEntityTypes.WAKIZASHI_MAX.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 5))))
                        .add(LootItem.lootTableItem(ModItems.COOLANT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                        .add(LootItem.lootTableItem(ModItems.WHETSTONE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                        .add(LootItem.lootTableItem(ModItems.WOOTZ_STEEL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.NIKKARI_AOE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.HORIKAWA_KUNIHIRO.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.2f))
                )
        );
    }

    // ==================== 打刀 ====================
    private void addUchigatanaLoot() {
        add(ModEntityTypes.UCHIGATANA.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.HESHIKIRI_HASEBE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.YAMATONOKAMI_YASUSADA.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.OOKURIKARA.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.YAMANBAGIRI_KUNIHIRO.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.KASEN_KANESADA.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.MUTSUNOKAMI_YOSHIYUKI.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.KASHUU_KIYOMITSU.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.HACHISUKA_KOTETSU.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.2f))
                )
        );
    }

    private void addUchigatanaPlusLoot() {
        add(ModEntityTypes.UCHIGATANA_PLUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5))))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.HESHIKIRI_HASEBE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.YAMATONOKAMI_YASUSADA.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.OOKURIKARA.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.YAMANBAGIRI_KUNIHIRO.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.KASEN_KANESADA.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.MUTSUNOKAMI_YOSHIYUKI.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.KASHUU_KIYOMITSU.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.HACHISUKA_KOTETSU.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.2f))
                )
        );
    }

    private void addUchigatanaMaxLoot() {
        add(ModEntityTypes.UCHIGATANA_MAX.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))
                        .add(LootItem.lootTableItem(ModItems.COOLANT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                        .add(LootItem.lootTableItem(ModItems.WHETSTONE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                        .add(LootItem.lootTableItem(ModItems.WOOTZ_STEEL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.HESHIKIRI_HASEBE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.YAMATONOKAMI_YASUSADA.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.OOKURIKARA.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.YAMANBAGIRI_KUNIHIRO.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.KASEN_KANESADA.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.MUTSUNOKAMI_YOSHIYUKI.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.KASHUU_KIYOMITSU.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.HACHISUKA_KOTETSU.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.2f))
                )
        );
    }

    // ==================== 太刀 ====================
    private void addTachiLoot() {
        add(ModEntityTypes.TACHI.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 3))))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.ICHIGO_HITOFURI.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.TSURUMARU_KUNINAGA.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.SHOKUDAIKIRI_MITSUTADA.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
    }

    private void addTachiPlusLoot() {
        add(ModEntityTypes.TACHI_PLUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 5))))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.ICHIGO_HITOFURI.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.TSURUMARU_KUNINAGA.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.SHOKUDAIKIRI_MITSUTADA.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
    }

    private void addTachiMaxLoot() {
        add(ModEntityTypes.TACHI_MAX.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 6))))
                        .add(LootItem.lootTableItem(ModItems.COOLANT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                        .add(LootItem.lootTableItem(ModItems.WHETSTONE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                        .add(LootItem.lootTableItem(ModItems.WOOTZ_STEEL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.ICHIGO_HITOFURI.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.TSURUMARU_KUNINAGA.get()).setWeight(1))
                        .add(LootItem.lootTableItem(ModItems.SHOKUDAIKIRI_MITSUTADA.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
    }

    // ==================== 大太刀 ====================
    private void addOotachiLoot() {
        add(ModEntityTypes.OOTACHI.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.ISHIKIRIMARU.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
    }

    private void addOotachiPlusLoot() {
        add(ModEntityTypes.OOTACHI_PLUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))  // 修复：原为 loot_pool
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.ISHIKIRIMARU.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
    }

    private void addOotachiMaxLoot() {
        add(ModEntityTypes.OOTACHI_MAX.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 7))))
                        .add(LootItem.lootTableItem(ModItems.COOLANT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                        .add(LootItem.lootTableItem(ModItems.WHETSTONE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                        .add(LootItem.lootTableItem(ModItems.WOOTZ_STEEL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.ISHIKIRIMARU.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
    }

    // ==================== 枪 ====================
    private void addYariLoot() {
        add(ModEntityTypes.YARI.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TONBOKIRI.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
    }

    private void addYariPlusLoot() {
        add(ModEntityTypes.YARI_PLUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5))))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TONBOKIRI.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
    }

    private void addYariMaxLoot() {
        add(ModEntityTypes.YARI_MAX.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))
                        .add(LootItem.lootTableItem(ModItems.COOLANT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                        .add(LootItem.lootTableItem(ModItems.WHETSTONE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                        .add(LootItem.lootTableItem(ModItems.WOOTZ_STEEL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TONBOKIRI.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
    }

    // ==================== 薙刀 ====================
    private void addNaginataLoot() {
        add(ModEntityTypes.NAGINATA.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TOMOEGATA_NAGINATA.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
    }

    private void addNaginataPlusLoot() {
        add(ModEntityTypes.NAGINATA_PLUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TOMOEGATA_NAGINATA.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
    }

    private void addNaginataMaxLoot() {
        add(ModEntityTypes.NAGINATA_MAX.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 7))))
                        .add(LootItem.lootTableItem(ModItems.COOLANT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                        .add(LootItem.lootTableItem(ModItems.WHETSTONE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                        .add(LootItem.lootTableItem(ModItems.WOOTZ_STEEL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 8))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.TOMOEGATA_NAGINATA.get()).setWeight(1))
                        .when(LootItemRandomChanceCondition.randomChance(0.1f))
                )
        );
    }

    // ==================== 检非违使 ====================
    private void addKebiishiCommonLoot(EntityType<?> type) {
        add(type, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.GOLD_OMAMORI.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                )
        );
    }

    private void addKebiishiLeaderLoot(EntityType<?> type) {
        add(type, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(2))
                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.MIKAZUKI_MUNECHIKA.get())
                                .setWeight(20))
                        .add(LootItem.lootTableItem(ModItems.GOLD_OMAMORI.get())
                                .setWeight(95)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                )
        );
    }

    // ==================== 白三日月宗近 BOSS ====================
    private void addWhiteMikazukiMunechikaLoot() {
        add(ModEntityTypes.WHITE_MIKAZUKI_MUNECHIKA.get(), LootTable.lootTable()
                // 池 1：必掉武器，100%
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.WHITE_MIKAZUKI_MUNECHIKA.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                )
                // 池 2：大量经验瓶
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(32, 64))))
                )
                // 池 3：极御守，必掉 3 个
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.SUPREME_AMULET.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3))))
                )
                // 池 4：珍稀材料，各概率掉落
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.PURE_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(12, 16))))
                        .add(LootItem.lootTableItem(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 16))))
                        .add(LootItem.lootTableItem(ModItems.WOOTZ_STEEL.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(6, 12))))
                        .add(LootItem.lootTableItem(ModItems.WHETSTONE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(6, 12))))
                        .add(LootItem.lootTableItem(ModItems.COOLANT.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(6, 12))))
                )
                // 池 5：金刀装，概率掉落 3~8 个
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.GOLD_OMAMORI.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8))))
                        .when(LootItemRandomChanceCondition.randomChance(0.5f))
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
                ModEntityTypes.KEBIISHI_LEADER.get(),
                ModEntityTypes.WHITE_MIKAZUKI_MUNECHIKA.get()
        );
    }
}