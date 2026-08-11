package com.Equatorial.toukenranbu.data;

import com.Equatorial.toukenranbu.advancement.ModCriteriaTriggers;
import com.Equatorial.toukenranbu.advancement.UseAmuletTrigger;
import com.Equatorial.toukenranbu.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.function.Consumer;

public class ModAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {

        // ==================== 根成就：新的开始 ====================
        Advancement root = Advancement.Builder.advancement()
                .display(
                        ModItems.MIKAZUKI_MUNECHIKA.get(),
                        Component.translatable("advancements.toukenranbu_mod.new_start.title"),
                        Component.translatable("advancements.toukenranbu_mod.new_start.description"),
                        ResourceLocation.withDefaultNamespace("textures/gui/advancements/backgrounds/adventure.png"),
                        FrameType.TASK,
                        true, true, false
                )
                .addCriterion("has_kashuu_kiyomisu", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.KASHUU_KIYOMITSU.get()))
                .addCriterion("has_kasen_kanesada", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.KASEN_KANESADA.get()))
                .addCriterion("has_hachisuka_kotetsu", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.HACHISUKA_KOTETSU.get()))
                .addCriterion("has_yamanbagiri_kunihiro", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.YAMANBAGIRI_KUNIHIRO.get()))
                .addCriterion("has_mutsunokami_yoshiyuki", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.MUTSUNOKAMI_YOSHIYUKI.get()))
                .requirements(new String[][] {{"has_kasen_kanesada", "has_hachisuka_kotetsu", "has_kashuu_kiyomisu","has_yamanbagiri_kunihiro","has_mutsunokami_yoshiyuki"}})
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "new_start"), existingFileHelper);

        // ==================== 线性链：初阵 ====================
        Advancement useStarter = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.KASHUU_KIYOMITSU.get(),
                        Component.translatable("advancements.toukenranbu_mod.use_starter_sword.title"),
                        Component.translatable("advancements.toukenranbu_mod.use_starter_sword.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .addCriterion("use_starter_sword", new UseAmuletTrigger.Instance(
                        ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "use_starter_sword"),
                        ContextAwarePredicate.ANY
                ))
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "use_starter_sword"), existingFileHelper);

        // ==================== 线性链：团子 ====================
        Advancement sweet = Advancement.Builder.advancement()
                .parent(useStarter)
                .display(
                        ModItems.IMMORTAL_DUMPLINGS.get(),
                        Component.translatable("advancements.toukenranbu_mod.sweet.title"),
                        Component.translatable("advancements.toukenranbu_mod.sweet.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .addCriterion("a_bite_of_dango", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.A_BITE_OF_DANGO.get()))
                .addCriterion("immortal_dumplings", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.IMMORTAL_DUMPLINGS.get()))
                .addCriterion("a_string_of_dango", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.A_STRING_OF_DANGO.get()))
                .requirements(new String[][] {{"a_bite_of_dango", "immortal_dumplings", "a_string_of_dango"}})
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "sweet"), existingFileHelper);

        // ==================== 线性链：大富翁 ====================
        Advancement koban = Advancement.Builder.advancement()
                .parent(sweet)
                .display(
                        ModItems.SMALL_KOBAN.get(),
                        Component.translatable("advancements.toukenranbu_mod.more_koban.title"),
                        Component.translatable("advancements.toukenranbu_mod.more_koban.description"),
                        null,
                        FrameType.GOAL,
                        true, true, false
                )
                .addCriterion("more_koban", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SMALL_KOBAN.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "more_koban"), existingFileHelper);

        // ==================== 线性链：刀装 ====================
        Advancement omamori = Advancement.Builder.advancement()
                .parent(koban)
                .display(
                        ModItems.GOLD_OMAMORI.get(),
                        Component.translatable("advancements.toukenranbu_mod.omamori.title"),
                        Component.translatable("advancements.toukenranbu_mod.omamori.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .addCriterion("has_bronze", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.BRONZE_OMAMORI.get()))
                .addCriterion("has_silver", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SILVER_OMAMORI.get()))
                .addCriterion("has_gold", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GOLD_OMAMORI.get()))
                .requirements(new String[][] {{"has_bronze", "has_silver", "has_gold"}})
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "omamori"), existingFileHelper);

        // ==================== 线性链：不卖刀剑 ====================
        Advancement another = Advancement.Builder.advancement()
                .parent(omamori)
                .display(
                        ModItems.PURE_SPIRITUAL_ENERGY.get(),
                        Component.translatable("advancements.toukenranbu_mod.trade_with_bladesmith.title"),
                        Component.translatable("advancements.toukenranbu_mod.trade_with_bladesmith.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .addCriterion("trade_with_bladesmith", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PURE_SPIRITUAL_ENERGY.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "trade_with_bladesmith"), existingFileHelper);

        // ==================== 线性链：有御守 ====================
        Advancement has_amulet = Advancement.Builder.advancement()
                .parent(another)
                .display(
                        ModItems.AMULET.get(),
                        Component.translatable("advancements.toukenranbu_mod.has_amulet.title"),
                        Component.translatable("advancements.toukenranbu_mod.has_amulet.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .addCriterion("has_amulet", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.AMULET.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "has_amulet"), existingFileHelper);

        // ==================== 线性链：有极御守 ====================
        Advancement has_supreme_amulet = Advancement.Builder.advancement()
                .parent(has_amulet)
                .display(
                        ModItems.SUPREME_AMULET.get(),
                        Component.translatable("advancements.toukenranbu_mod.has_supreme_amulet.title"),
                        Component.translatable("advancements.toukenranbu_mod.has_supreme_amulet.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .addCriterion("has_supreme_amulet", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SUPREME_AMULET.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "has_supreme_amulet"), existingFileHelper);

        // ==================== 线性链：玉钢 ====================
        Advancement wootz_steel = Advancement.Builder.advancement()
                .parent(has_supreme_amulet)
                .display(
                        ModItems.WOOTZ_STEEL_HELMET.get(),
                        Component.translatable("advancements.toukenranbu_mod.wootz_steel.title"),
                        Component.translatable("advancements.toukenranbu_mod.wootz_steel.description"),
                        null,
                        FrameType.GOAL,
                        true, true, false
                )
                .addCriterion("wootz_steel_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WOOTZ_STEEL_HELMET.get()))
                .addCriterion("wootz_steel_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WOOTZ_STEEL_CHESTPLATE.get()))
                .addCriterion("wootz_steel_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WOOTZ_STEEL_LEGGINGS.get()))
                .addCriterion("wootz_steel_boots", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WOOTZ_STEEL_BOOTS.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "wootz_steel"), existingFileHelper);

        // ==================== 线性链：砥石 ====================
        Advancement whetstone = Advancement.Builder.advancement()
                .parent(wootz_steel)
                .display(
                        ModItems.WHETSTONE_HELMET.get(),
                        Component.translatable("advancements.toukenranbu_mod.whetstone.title"),
                        Component.translatable("advancements.toukenranbu_mod.whetstone.description"),
                        null,
                        FrameType.GOAL,
                        true, true, false
                )
                .addCriterion("whetstone_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WHETSTONE_HELMET.get()))
                .addCriterion("whetstone_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WHETSTONE_CHESTPLATE.get()))
                .addCriterion("whetstone_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WHETSTONE_LEGGINGS.get()))
                .addCriterion("whetstone_boots", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WHETSTONE_BOOTS.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "whetstone"), existingFileHelper);

        // ==================== 挑战分支：举杯邀月（直接挂 root） ====================
        Advancement mikazuki = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.MIKAZUKI_MUNECHIKA.get(),
                        Component.translatable("advancements.toukenranbu_mod.toast_to_the_moon.title"),
                        Component.translatable("advancements.toukenranbu_mod.toast_to_the_moon.description"),
                        null,
                        FrameType.CHALLENGE,
                        true, true, false
                )
                .addCriterion("toast_to_the_moon", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.MIKAZUKI_MUNECHIKA.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "toast_to_the_moon"), existingFileHelper);

        // ==================== 线性链：月相 ====================
        Advancement usemikazuki = Advancement.Builder.advancement()
                .parent(mikazuki)
                .display(
                        ModItems.MIKAZUKI_MUNECHIKA.get(),
                        Component.translatable("advancements.toukenranbu_mod.use_mikazuki_munechika.title"),
                        Component.translatable("advancements.toukenranbu_mod.use_mikazuki_munechika.description"),
                        null,
                        FrameType.GOAL,
                        true, true, false
                )
                .addCriterion("use_mikazuki_munechika", new UseAmuletTrigger.Instance(
                        ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "use_mikazuki_munechika"),
                        ContextAwarePredicate.ANY
                ))
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "use_mikazuki_munechika"), existingFileHelper);

        // ==================== 挑战分支：战胜敌军部队（直接挂 root） ====================
        Advancement child = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.TANTOU_MAX_SPAWN_EGG.get(),
                        Component.translatable("advancements.toukenranbu_mod.defeat_the_enemy_forces.title"),
                        Component.translatable("advancements.toukenranbu_mod.defeat_the_enemy_forces.description"),
                        null,
                        FrameType.CHALLENGE,
                        true, true, false
                )
                .addCriterion("defeat_the_enemy_forces", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.TURBID_SPIRITUAL_ENERGY.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "defeat_the_enemy_forces"), existingFileHelper);

        // ==================== 挑战分支：命悬一线（在杀死足够多的时间溯行军的时候一不小心出事了） ====================
        Advancement useAmulet = Advancement.Builder.advancement()
                .parent(child)
                .display(
                        ModItems.AMULET.get(),
                        Component.translatable("advancements.toukenranbu_mod.use_amulet.title"),
                        Component.translatable("advancements.toukenranbu_mod.use_amulet.description"),
                        null,
                        FrameType.CHALLENGE,
                        true, true, false
                )
                .addCriterion("used_amulet", new UseAmuletTrigger.Instance(
                        ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "use_amulet"),
                        ContextAwarePredicate.ANY
                ))
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "use_amulet"), existingFileHelper);

        // ==================== 挑战分支：生生不绝（在使用过普通御守之后） ====================
        Advancement useSupremeAmulet = Advancement.Builder.advancement()
                .parent(useAmulet)
                .display(
                        ModItems.SUPREME_AMULET.get(),
                        Component.translatable("advancements.toukenranbu_mod.use_supreme_amulet.title"),
                        Component.translatable("advancements.toukenranbu_mod.use_supreme_amulet.description"),
                        null,
                        FrameType.CHALLENGE,
                        true, true, false
                )
                .addCriterion("used_supreme_amulet", new UseAmuletTrigger.Instance(
                        ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "use_supreme_amulet"),
                        ContextAwarePredicate.ANY
                ))
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "use_supreme_amulet"), existingFileHelper);

        // ==================== 挑战分支：三方会晤（检非违使出现） ====================
        Advancement threeWayMeeting = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.KEBIISHI_TACHI_SPAWN_EGG.get(),
                        Component.translatable("advancements.toukenranbu_mod.three_way_meeting.title"),
                        Component.translatable("advancements.toukenranbu_mod.three_way_meeting.description"),
                        null,
                        FrameType.CHALLENGE,
                        true, true, false
                )
                .addCriterion("kebiishi_spawn", new ModCriteriaTriggers.KebiishiSpawnTrigger.TriggerInstance(
                        ModCriteriaTriggers.KebiishiSpawnTrigger.ID, ContextAwarePredicate.ANY))
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "three_way_meeting"), existingFileHelper);

        // ==================== 线性链：有骰子 ====================
        Advancement has_dice = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.DICE.get(),
                        Component.translatable("advancements.toukenranbu_mod.has_dice.title"),
                        Component.translatable("advancements.toukenranbu_mod.has_dice.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .addCriterion("has_dice", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.DICE.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "has_dice"), existingFileHelper);

        // ==================== 探索分支：特命调查·废弃历史 ====================
        Advancement enterAbandonedHistory = Advancement.Builder.advancement()
                .parent(has_dice)
                .display(
                        ModItems.DICE.get(),
                        Component.translatable("advancements.toukenranbu_mod.enter_abandoned_history.title"),
                        Component.translatable("advancements.toukenranbu_mod.enter_abandoned_history.description"),
                        null,
                        FrameType.TASK,
                        true, true, false
                )
                .addCriterion("enter_abandoned_history", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(
                ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "abandoned_history"))))
                .save(saver, ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "enter_abandoned_history"), existingFileHelper);

    }
}
