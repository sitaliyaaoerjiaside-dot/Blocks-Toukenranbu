package com.Equatorial.toukenranbu.datagen;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.block.ModBlocks;
import com.Equatorial.toukenranbu.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnUsLangProvider extends LanguageProvider {
    public ModEnUsLangProvider(PackOutput output) {
        super(output, ToukenRanbuMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModItems.WOOTZ_STEEL.get(), "Wootz Steel");
        add(ModItems.COOLANT.get(), "Coolant");
        add(ModItems.WHETSTONE.get(), "Whetstone");
        add(ModItems.AMULET.get(), "Amulet");
        add(ModItems.SUPREME_AMULET.get(), "Supreme Amulet");
        add(ModItems.A_SET_OF_PAPER_AND_PEN.get(), "A Set of Paper and Pen");
        add(ModItems.DAMAGED_SWORD_FRAGMENTS.get(), "Damaged Sword Fragments");
        add(ModItems.POWER_OF_ATTORNEY.get(), "Power of Attorney");
        add(ModItems.SPEED_UP_POTION.get(), "Speed Up Potion");
        add(ModItems.SMALL_KOBAN.get(), "Small Koban");
        add(ModItems.GOLD_OMAMORI.get(), "gold omamori");
        add(ModItems.SILVER_OMAMORI.get(), "silver omamori");
        add(ModItems.BRONZE_OMAMORI.get(), "bronze omamori");
        add(ModItems.HORSE_KOHIBARI.get(), "Kohibari");
        add(ModItems.HORSE_MIKUNIGURO.get(), "Mikuni-guro");
        add(ModItems.HORSE_MATSUKAZE.get(), "Matsukaze");
        add(ModItems.HORSE_OUTEI.get(), "Outei");
        add(ModItems.HORSE_TAKADONOGURO.get(), "Takadono-guro");

        add(ModItems.TANTOU_SPAWN_EGG.get(), "Tantou Spawn Egg");
        add(ModItems.WAKIZASHI_SPAWN_EGG.get(), "Wakizashi Spawn Egg");
        add(ModItems.UCHIGATANA_SPAWN_EGG.get(), "Uchigatana Spawn egg");
        add(ModItems.TACHI_SPAWN_EGG.get(), "Tachi Spawn Egg");
        add(ModItems.NAGINATA_SPAWN_EGG.get(), "Naginata Spawn Egg");
        add(ModItems.OOTACHI_SPAWN_EGG.get(), "Ootachi Spawn egg");
        add(ModItems.YARI_SPAWN_EGG.get(), "yari Spawn Egg");

        add(ModItems.TANTOU_PLUS_SPAWN_EGG.get(), "Tantou Plus Spawn egg");
        add(ModItems.TACHI_PLUS_SPAWN_EGG.get(), "Tachi Plus Spawn Egg");
        add(ModItems.NAGINATA_PLUS_SPAWN_EGG.get(), "Naginata Plus Spawn Egg");
        add(ModItems.OOTACHI_PLUS_SPAWN_EGG.get(), "Ootachi Plus Spawn Egg");
        add(ModItems.YARI_PLUS_SPAWN_EGG.get(), "Yari Plus Spawn Egg");
        add(ModItems.UCHIGATANA_PLUS_SPAWN_EGG.get(), "Uchigatana Plus Spawn egg");
        add(ModItems.WAKIZASHI_PLUS_SPAWN_EGG.get(), "Wakizashi Plus Spawn Egg");

        add(ModItems.TANTOU_MAX_SPAWN_EGG.get(), "Tantou Max Spawn egg");
        add(ModItems.UCHIGATANA_MAX_SPAWN_EGG.get(), "Uchigatana Max Spawn egg");
        add(ModItems.TACHI_MAX_SPAWN_EGG.get(), "Tachi Max Spawn Egg");
        add(ModItems.NAGINATA_MAX_SPAWN_EGG.get(), "Naginata Max Spawn Egg");
        add(ModItems.OOTACHI_MAX_SPAWN_EGG.get(), "Ootachi Max Spawn Egg");
        add(ModItems.YARI_MAX_SPAWN_EGG.get(), "Yari Max Spawn Egg");
        add(ModItems.WAKIZASHI_MAX_SPAWN_EGG.get(), "Wakizashi Max Spawn Egg");

        add(ModItems.KEBIISHI_TACHI_SPAWN_EGG.get(), "Kebiishi Tachi Spawn Egg");
        add(ModItems.KEBIISHI_NAGINATA_SPAWN_EGG.get(), "Kebiishi Naginata Spawn Egg");
        add(ModItems.KEBIISHI_OOTACHI_SPAWN_EGG.get(), "Kebiishi Ootachi Spawn Egg");
        add(ModItems.KEBIISHI_LEADER_SPAWN_EGG.get(), "Kebiishi Leader Spawn Egg");
        add(ModItems.KEBIISHI_YARI_SPAWN_EGG.get(), "Kebiishi Yari Spawn Egg");

        add(ModItems.KONNOSUKE_SPAWN_EGG.get(), "Konnosuke Spawn Egg");

        add(ModItems.MIKAZUKI_MUNECHIKA.get(), "mikazuki munechika");
        add(ModItems.YAMANBAGIRI_KUNIHIRO.get(), "yamanbagiri kunihiro");
        add(ModItems.KASHUU_KIYOMITSU.get(), "kashuu kiyomitsu");
        add(ModItems.HACHISUKA_KOTETSU.get(), "hachisuka kotetsu");
        add(ModItems.KASEN_KANESADA.get(), "kasen kanesada");
        add(ModItems.MUTSUNOKAMI_YOSHIYUKI.get(), "mutsunokami yoshiyuki");

        add(ModItems.MIKAZUKI_BLADE.get(), "Mikazuki Munechika [Blade]");
        add(ModItems.YAMANBAGIRI_BLADE.get(), "Yamanbagiri Kunihiro [Blade]");
        add(ModItems.KASHUU_BLADE.get(), "Kashuu Kiyomitsu [Blade]");
        add(ModItems.HACHISUKA_BLADE.get(), "Hachisuka Kotetsu [Blade]");
        add(ModItems.KASEN_BLADE.get(), "Kasen Kanesada [Blade]");
        add(ModItems.MUTSUNOKAMI_BLADE.get(), "Mutsunokami Yoshiyuki [Blade]");
        add("gui.toukenranbu.label.blade", "Blade");

        add(ModItems.A_BITE_OF_DANGO.get(), "A Bite of Dango");
        add(ModItems.IMMORTAL_DUMPLINGS.get(), "Immortal Dumplings");
        add(ModItems.A_STRING_OF_DANGO.get(), "A String of Dango");
        add(ModItems.TURBID_SPIRITUAL_ENERGY.get(), "Turbid Spiritual Energy");
        add(ModItems.PURE_SPIRITUAL_ENERGY.get(), "Pure Spiritual Energy");
        add(ModItems.TROOP_CANDY.get(), "Troop Candy");
        add(ModItems.SOLDIER_CANDY.get(), "Solder Candy");
        add(ModItems.OIL_TOFU.get(), "Oil Tofu");

        add(ModItems.VOID_SPIRITUAL_ENERGY.get(), "Void Spiritual Energy");

        add(ModBlocks.WOOTZ_STEEL_BLOCK.get(), "Wootz Steel Block");
        add(ModBlocks.COOLANT_BLOCK.get(), "Coolant Block");
        add(ModBlocks.WHETSTONE_BLOCK.get(), "Whetstone Block");
        add(ModBlocks.WOOTZ_STEEL_ORE.get(), "Wootz Steel Ore");
        add(ModBlocks.COOLANT_ORE.get(), "Coolant Ore");
        add(ModBlocks.WHETSTONE_ORE.get(), "Whetstone Ore");

        add(ModBlocks.WOOTZ_STEEL_STAIRS.get(), "Wootz Steel Stairs");
        add(ModBlocks.WOOTZ_STEEL_SLAB.get(), "Wootz Steel Slab");
        add(ModBlocks.WOOTZ_STEEL_BUTTON.get(), "Wootz Steel Button");
        add(ModBlocks.WOOTZ_STEEL_PRESSURE_PLATE.get(), "Wootz Steel Pressure Plate");
        add(ModBlocks.WOOTZ_STEEL_FENCE_GATE.get(), "Wootz Steel Fence Gate");
        add(ModBlocks.WOOTZ_STEEL_FENCE.get(), "Wootz Steel Fence");
        add(ModBlocks.WOOTZ_STEEL_WALL.get(), "Wootz Steel Wall");
        add(ModBlocks.WOOTZ_STEEL_DOOR.get(), "Wootz Steel Door");
        add(ModBlocks.WOOTZ_STEEL_TRAPDOOR.get(), "Wootz Steel Trapdoor");
        add(ModBlocks.WHETSTONE_STAIRS.get(), "Whetstone Stairs");
        add(ModBlocks.WHETSTONE_SLAB.get(), "Whetstone Slab");
        add(ModBlocks.WHETSTONE_BUTTON.get(), "Whetstone Button");
        add(ModBlocks.WHETSTONE_PRESSURE_PLATE.get(), "Whetstone Pressure Plate");
        add(ModBlocks.WHETSTONE_FENCE_GATE.get(), "Whetstone Fence Gate");
        add(ModBlocks.WHETSTONE_FENCE.get(), "Whetstone Fence");
        add(ModBlocks.WHETSTONE_WALL.get(), "Whetstone Wall");
        add(ModBlocks.WHETSTONE_DOOR.get(), "Whetstone Door");
        add(ModBlocks.WHETSTONE_TRAPDOOR.get(), "Whetstone Trapdoor");

        add(ModBlocks.CHARRED_LOG.get(),"charred log");
        add(ModBlocks.CHARRED_WOOD.get(),"charred wood");
        add(ModBlocks.CHARRED_PLANKS.get(),"charred planks");
        add(ModBlocks.CHARRED_LEAVES.get(),"charred leaves");
        add(ModBlocks.STRIPPED_CHARRED_LOG.get(),"stripped charred log");
        add(ModBlocks.STRIPPED_CHARRED_WOOD.get(),"stripped charred wood");
        add(ModBlocks.CHARRED_STAIRS.get(),"charred stairs");
        add(ModBlocks.CHARRED_SLAB.get(),"charred slab");
        add(ModBlocks.CHARRED_FENCE.get(),"charred fence");
        add(ModBlocks.CHARRED_FENCE_GATE.get(),"charred fence gate");
        add(ModBlocks.CHARRED_DOOR.get(),"charred door");
        add(ModBlocks.CHARRED_TRAPDOOR.get(),"charred trapdoor");
        add(ModBlocks.CHARRED_BUTTON.get(),"charred button");
        add(ModBlocks.CHARRED_PRESSURE_PLATE.get(),"charred pressure plate");
        add(ModBlocks.CHARRED_SAPLING.get(),"charred sapling");
        add(ModBlocks.CHARRED_GRASS_BLOCK.get(),"charred grass block");
        add(ModBlocks.CHARRED_DIRT.get(),"charred dirt");

        add(ModItems.WOOTZ_STEEL_SWORD.get(), "Wootz Steel Sword");
        add(ModItems.WOOTZ_STEEL_PICKAXE.get(), "Wootz Steel Pickaxe");
        add(ModItems.WOOTZ_STEEL_AXE.get(), "Wootz Steel Axe");
        add(ModItems.WOOTZ_STEEL_SHOVEL.get(), "Wootz Steel Shovel");
        add(ModItems.WOOTZ_STEEL_HOE.get(), "Wootz Steel Hoe");
        add(ModItems.WHETSTONE_SWORD.get(), "Whetstone Sword");
        add(ModItems.WHETSTONE_PICKAXE.get(), "Whetstone Pickaxe");
        add(ModItems.WHETSTONE_AXE.get(), "Whetstone Axe");
        add(ModItems.WHETSTONE_SHOVEL.get(), "Whetstone Shovel");
        add(ModItems.WHETSTONE_HOE.get(), "Whetstone Hoe");

        add(ModItems.WOOTZ_STEEL_HELMET.get(), "Wootz Steel Helmet");
        add(ModItems.WOOTZ_STEEL_CHESTPLATE.get(), "Wootz Steel Chestplate");
        add(ModItems.WOOTZ_STEEL_LEGGINGS.get(), "Wootz Steel Leggings");
        add(ModItems.WOOTZ_STEEL_BOOTS.get(), "Wootz Steel Boots");
        add(ModItems.WHETSTONE_HELMET.get(), "Whetstone Helmet");
        add(ModItems.WHETSTONE_CHESTPLATE.get(), "Whetstone Chestplate");
        add(ModItems.WHETSTONE_LEGGINGS.get(), "Whetstone Leggings");
        add(ModItems.WHETSTONE_BOOTS.get(), "Whetstone Boots");

        add(ModItems.DICE.get(),"dice");
        add(ModItems.CAPTURE_BALL.get(),"Capture Talisman");

        add("effect.toukenranbu_mod.spirit_regen", "spirit_regen");
        add("container.sword_forge", "Sword Forge");
        //add(ModBlocks.SWORD_FORGE.get(), "Sword Forge");

        add("itemGroup.toukenranbu_tab", "Blocks ＆ Touken Ranbu");

        add("entity.minecraft.villager.toukenranbu_mod.bladesmith","bladesmith");

        add("message.toukenranbu_mod.summon_mikazuki_munechika",
                "I am Mikazuki Munechika. Named Mikazuki for the many patterns formed during forging. Please take care of me.");
        add("message.toukenranbu_mod.summon_yamanbagiri_kunihiro",
                "Yamanbagiri Kunihiro. ...What's with that look? Does it bother you that I'm a replica?");
        add("message.toukenranbu_mod.summon_hachisuka_kotetsu",
                "I'm Hachisuka Kotetsu. I hope you won't confuse me with a counterfeit.");
        add("message.toukenranbu_mod.summon_kasen_kanesada",
                "I'm Kasen Kanesada, a cultured sword who appreciates elegance and refinement. Pleased to make your acquaintance.");
        add("message.toukenranbu_mod.summon_kashuu_kiyomitsu",
                "Ah—I'm Kashuu Kiyomitsu, child of the river. I may be difficult to handle, but my performance is excellent.");
        add("message.toukenranbu_mod.summon_mutsunokami_yoshiyuki",
                "I'm Mutsunokami Yoshiyuki. We've finally made it to this grand place, so let's seize the world!");

        add("gui.toukenranbu.label.entity_inventory", "Touken Danshi's Inventory");
        add("gui.toukenranbu.touken_danshi.title","touken_danshi");
        add("gui.toukenranbu.button.sit", "Sit");
        add("gui.toukenranbu.button.stand", "Stand");
        add("gui.toukenranbu.button.follow", "Follow");
        add("gui.toukenranbu.button.unfollow", "Stop Following");
        add("gui.toukenranbu.button.farm", "Farm");
        add("gui.toukenranbu.button.stop_farm", "Stop Farming");
        add("gui.toukenranbu.button.farm_escaping", "Escaping");
        add("gui.toukenranbu.button.farm_no_seeds", "Farm (No Seeds)");
        add("gui.toukenranbu.button.mine", "Mine");
        add("gui.toukenranbu.button.stop_mine", "Stop Mining");
        add("gui.toukenranbu.button.mine_no_pickaxe", "Mine (No Pickaxe)");
        add("gui.toukenranbu.status.mining", "[Mining]");
        add("gui.toukenranbu.button.patrol", "Patrol");
        add("gui.toukenranbu.button.patrolling", "Patrolling");
        add("gui.toukenranbu.button.sparrow", "Spar");
        add("gui.toukenranbu.button.sparring", "Sparring");
        add("gui.toukenranbu.message.sparrow_no_partner", "No available partner nearby for sparring");
        add("gui.toukenranbu.button.cave_clear", "Cave Clear");
        add("gui.toukenranbu.button.cave_clearing", "Stop Clearing");
        add("gui.toukenranbu.button.cave_clear_no_torches", "No Torches");
        add("gui.toukenranbu.label.auto_pickup", "Auto Pickup");

        add("gui.toukenranbu.message.auto_seal", "%s is critically injured and has been sealed!");
        add("gui.toukenranbu.formation.status_with_count", "%s (%d)");
        add("gui.toukenranbu.message.feed_full", "%s is full and can't eat any more!");

//阵型
        add("gui.toukenranbu.formation.none", "No Formation");
        add("gui.toukenranbu.formation.fish_scale", "Fish Scale");
        add("gui.toukenranbu.formation.crane_wing", "Crane Wing");
        add("gui.toukenranbu.formation.goose_line", "Goose Line");
        add("gui.toukenranbu.formation.square", "Square");


// Status Panel
        add("gui.toukenranbu.hp", "HP: %1$s/%2$s");
        add("gui.toukenranbu.stat.impact", "Impact %1$s");
        add("gui.toukenranbu.stat.mobility", "Mobility %1$s");
        add("gui.toukenranbu.stat.killing", "Killing %1$s");
        add("gui.toukenranbu.stat.scouting", "Scouting %1$s");
        add("gui.toukenranbu.stat.concealment", "Concealment %1$s");
        add("gui.toukenranbu.stat.troops", "Troops %1$s");
        add("gui.toukenranbu.stat.fatigue", "Fatigue");
// Fatigue States
        add("gui.toukenranbu.status.sakura", "Sakura Fubuki");
        add("gui.toukenranbu.status.normal", "Normal");
        add("gui.toukenranbu.status.tired", "Tired");
        add("gui.toukenranbu.status.exhausted", "Severely Tired");

//物品栏标签文字
        add( "gui.toukenranbu.label.armor", "Armor");
        add("gui.toukenranbu.label.knife", "Knife Equipment");
        add("gui.toukenranbu.label.mount", "Mount");
        add("gui.toukenranbu.label.treasure", "Not Implemented");
        add("gui.toukenranbu.label.inventory", "Inventory");

// entity name
        add("entity.toukenranbu_mod.mikazuki_munechika", "Mikazuki Munechika");
        add("entity.toukenranbu_mod.yamanbagiri_kunihiro", "Yamanbagiri Kunihiro");
        add("entity.toukenranbu_mod.hachisuka_kotetsu", "Hachisuka Kotetsu");
        add("entity.toukenranbu_mod.kasen_kanesada", "Kasen Kanesada");
        add("entity.toukenranbu_mod.kashuu_kiyomitsu", "Kashuu Kiyomitsu");
        add("entity.toukenranbu_mod.mutsunokami_yoshiyuki", "Mutsunokami Yoshiyuki");

        add("entity.toukenranbu.touken_danshi.mikazuki_munechika", "Mikazuki Munechika");
        add("entity.toukenranbu.touken_danshi.yamanbagiri_kunihiro", "Yamanbagiri Kunihiro");
        add("entity.toukenranbu.touken_danshi.hachisuka_kotetsu", "Hachisuka Kotetsu");
        add("entity.toukenranbu.touken_danshi.kasen_kanesada", "Kasen Kanesada");
        add("entity.toukenranbu.touken_danshi.kashuu_kiyomitsu", "Kashuu Kiyomitsu");
        add("entity.toukenranbu.touken_danshi.mutsunokami_yoshiyuki", "Mutsunokami Yoshiyuki");

// GUI titles
        add("gui.toukenranbu.touken_danshi.title.yamanbagiri_kunihiro", "Yamanbagiri Kunihiro");
        add("gui.toukenranbu.touken_danshi.title.hachisuka_kotetsu", "Hachisuka Kotetsu");
        add("gui.toukenranbu.touken_danshi.title.kasen_kanesada", "Kasen Kanesada");
        add("gui.toukenranbu.touken_danshi.title.kashuu_kiyomitsu", "Kashuu Kiyomitsu");
        add("gui.toukenranbu.touken_danshi.title.mutsunokami_yoshiyuki", "Mutsunokami Yoshiyuki");
        add("gui.toukenranbu.touken_danshi.title.mikazuki_munechika", "Mikazuki Munechika");

// GUI Tabs
        add("gui.toukenranbu.tab.status", "Status");
        add("gui.toukenranbu.tab.items", "Items");

        add("entity.toukenranbu_mod.tantou", "Tantou");
        add("entity.toukenranbu_mod.wakizashi", "Wakizashi");
        add("entity.toukenranbu_mod.uchigatana", "Uchigatana");
        add("entity.toukenranbu_mod.tachi", "Tachi");
        add("entity.toukenranbu_mod.ootachi", "Ootachi");
        add("entity.toukenranbu_mod.naginata", "Naginata");
        add("entity.toukenranbu_mod.yari", "Yari");

        add("entity.toukenranbu_mod.tantou_plus", "Tantou Plus");
        add("entity.toukenranbu_mod.wakizashi_plus", "Wakizashi Plus");
        add("entity.toukenranbu_mod.uchigatana_plus", "Uchigatana Plus");
        add("entity.toukenranbu_mod.tachi_plus", "Tachi Plus");
        add("entity.toukenranbu_mod.ootachi_plus", "Ootachi Plus");
        add("entity.toukenranbu_mod.naginata_plus", "Naginata Plus");
        add("entity.toukenranbu_mod.yari_plus", "Yari Plus");

        add("entity.toukenranbu_mod.tantou_max", "Tantou Max");
        add("entity.toukenranbu_mod.wakizashi_max", "Wakizashi Max");
        add("entity.toukenranbu_mod.uchigatana_max", "Uchigatana Max");
        add("entity.toukenranbu_mod.tachi_max", "Tachi Max");
        add("entity.toukenranbu_mod.ootachi_max", "Ootachi Max");
        add("entity.toukenranbu_mod.naginata_max", "Naginata Max");
        add("entity.toukenranbu_mod.yari_max", "Yari Max");

        add("entity.toukenranbu_mod.kebiishi_leader", "Kebiishi Leader");
        add("entity.toukenranbu_mod.kebiishi_tachi", "Kebiishi Tachi");
        add("entity.toukenranbu_mod.kebiishi_ootachi", "Kebiishi Ootachi");
        add("entity.toukenranbu_mod.kebiishi_naginata", "Kebiishi Naginata");
        add("entity.toukenranbu_mod.kebiishi_yari", "Kebiishi Yari");

        add("entity.toukenranbu_mod.konnosuke","Konnosuke");
        // 以后加新实体就按这个格式：
        // add("entity.toukenranbu_mod.新刀名", "英文刀名");

        add("death.toukenranbu_mod.mikazuki_munechika", "All that has form will eventually perish. It simply happens to be today.");
        add("death.toukenranbu_mod.yamanbagiri_kunihiro", "Ahh... How unpleasant... Even after I vanish, will I still be compared...?");
        add("death.toukenranbu_mod.hachisuka_kotetsu", "So I've lost... At this rate... the counterfeits in this world... will only grow more brazen... won't they?");
        add("death.toukenranbu_mod.kasen_kanesada", "Ahh... so this is the far shore... I ought to compose a poem... Who... will bring me my brush...");
        add("death.toukenranbu_mod.kashuu_kiyomitsu", "Was I... loved... until the very end...?");
        add("death.toukenranbu_mod.mutsunokami_yoshiyuki", "What... so this is where I end? ...The new era... I won't get to see it...");

        add("toukenranbu.book.name", "Touken Ranbu Encyclopedia");
        add("toukenranbu.landing_text", "Welcome to the world of Touken Ranbu." +
                "The Touken Ranbu Encyclopedia covers all detailed data of the Touken Ranbu mod (excluding building blocks and the like)." +
                "Everything you discover will be recorded within.");

        add("message.toukenranbu_mod.kebiishi_tier_up", "§c§l[Historical Correction Rising]§r Kebiishi alert level increased to %s!");
        add("message.toukenranbu_mod.kebiishi_spawn", "§c§l[Kebiishi Detected]§r Historical correction is converging on %s...");
        add("message.toukenranbu_mod.kebiishi.on", "§aKebiishi spawning §lenabled");
        add("message.toukenranbu_mod.kebiishi.off", "§cKebiishi spawning §ldisabled");
        add("message.toukenranbu_mod.kebiishi.status.on", "§aStatus: Enabled");
        add("message.toukenranbu_mod.kebiishi.status.off", "§cStatus: Disabled");
        add("message.toukenranbu_mod.kebiishi.status.format", "%s §7| Next spawn in: %s min");
        add("message.toukenranbu_mod.kebiishi.countdown", "[Historical Revision] Kebiishi estimated to arrive in %s minutes...");
        add("message.toukenranbu_mod.kebiishi.warning", "[Kebiishi Appear] Historical revision force is gathering at %s's location... Arrival in 30 seconds!");
        add("message.toukenranbu_mod.kebiishi.spawned", "[Kebiishi Appear] Historical revision force has arrived!");
        add("message.toukenranbu_mod.kebiishi.nearby", "[Historical Revision] Kebiishi already nearby, halting accumulation.");

        add("item.toukenranbu_mod.capture_ball.prefix.captured", "[Captured] ");
        add("message.toukenranbu_mod.capture.fail_player", "Cannot capture players!");
        add("message.toukenranbu_mod.capture.fail_full", "This talisman already contains an entity!");
        add("message.toukenranbu_mod.capture.success", "Capture successful!");
        add("message.toukenranbu_mod.release.fail_empty", "This talisman is empty!");
        add("message.toukenranbu_mod.release.fail_owner", "You are not the owner of this talisman!");
        add("message.toukenranbu_mod.release.success", "Released: %s");
        add("tooltip.toukenranbu_mod.capture_ball.entity", "Entity: %s");
        add("tooltip.toukenranbu_mod.capture_ball.type", "Type: %s");
        add("tooltip.toukenranbu_mod.capture_ball.owner", "Owner: %s");
        add("tooltip.toukenranbu_mod.capture_ball.empty", "Empty Capture Talisman");
        add("tooltip.toukenranbu_mod.capture_ball.usage", "Right-click entity to capture, right-click block to release");

// ========== 成就标题 & 描述 ==========
        add("advancements.toukenranbu_mod.new_start.title", "A New Beginning");
        add("advancements.toukenranbu_mod.new_start.description", "Choose your starter blade from the inventory and begin your new adventure.");

        add("advancements.toukenranbu_mod.use_starter_sword.title", "first_sortie");
        add("advancements.toukenranbu_mod.use_starter_sword.description", "Use a starter sword to summon any Touken Danshi.");

        add("advancements.toukenranbu_mod.defeat_the_enemy_forces.title", "Defeat the Enemy Forces");
        add("advancements.toukenranbu_mod.defeat_the_enemy_forces.description", "Defeat Retrograde Army enemies until at least one Turbid Spiritual Energy drops.");

        add("advancements.toukenranbu_mod.trade_with_bladesmith.title", "Not for Sale");
        add("advancements.toukenranbu_mod.trade_with_bladesmith.description", "Trade with a Bladesmith villager and obtain Pure Spiritual Energy.");

        add("advancements.toukenranbu_mod.toast_to_the_moon.title", "Toast to the Moon");
        add("advancements.toukenranbu_mod.toast_to_the_moon.description", "Obtain one of the Five Great Swords: Mikazuki Munechika.");

        add("advancements.toukenranbu_mod.use_mikazuki_munechika.title", "tale_of_the_moon");
        add("advancements.toukenranbu_mod.use_mikazuki_munechika.description", "Summon one of the Five Great Swords: Mikazuki Munechika.");

        add("advancements.toukenranbu_mod.has_amulet.title", "Just Ordinary...");
        add("advancements.toukenranbu_mod.has_amulet.description", "Obtain a regular amulet.");

        add("advancements.toukenranbu_mod.has_supreme_amulet.title", "All That Glitters");
        add("advancements.toukenranbu_mod.has_supreme_amulet.description", "Obtain a supreme amulet.");

        add("advancements.toukenranbu_mod.use_amulet.title", "Hanging by a Thread");
        add("advancements.toukenranbu_mod.use_amulet.description", "Trigger an amulet's effect to cheat death.");

        add("advancements.toukenranbu_mod.use_supreme_amulet.title", "Endless Vitality");
        add("advancements.toukenranbu_mod.use_supreme_amulet.description", "Trigger a Supreme amulet's effect to cheat death.");

        add("advancements.toukenranbu_mod.more_koban.title", "Tycoon");
        add("advancements.toukenranbu_mod.more_koban.description", "You have acquired many Koban. They are yours now!");

        add("advancements.toukenranbu_mod.omamori.title", "What's This? A omamori!");
        add("advancements.toukenranbu_mod.omamori.description", "Obtain at least one Omamori.");

        add("advancements.toukenranbu_mod.sweet.title", "A Bite of Dango");
        add("advancements.toukenranbu_mod.sweet.description", "Obtain either A Bite of Dango, Immortal Dumplings, or A String of Dango.");

        add("advancements.toukenranbu_mod.wootz_steel.title", "Clad in Tamahagane");
        add("advancements.toukenranbu_mod.wootz_steel.description", "Equip a full set of Wootz Steel armor.");

        add("advancements.toukenranbu_mod.whetstone.title", "Clad in Whetstone");
        add("advancements.toukenranbu_mod.whetstone.description", "Equip a full set of Whetstone armor.");

        add("advancements.toukenranbu_mod.has_dice.title", "Special Investigation");
        add("advancements.toukenranbu_mod.has_dice.description", "Accept the special orders from the Government of Time.");

        add("advancements.toukenranbu_mod.enter_abandoned_history.title", "Hopeless Future");
        add("advancements.toukenranbu_mod.enter_abandoned_history.description", "You have entered a world of tampered history.");

        add("advancements.toukenranbu_mod.three_way_meeting.title", "Three-Way Meeting");
        add("advancements.toukenranbu_mod.three_way_meeting.description", "The force of historical revision has arrived. The fateful confrontation between the Saniwa, the Touken Danshi, and the Kebiishi is about to begin.");

    }
}
