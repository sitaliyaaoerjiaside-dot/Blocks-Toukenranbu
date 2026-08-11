package com.Equatorial.toukenranbu.datagen;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.block.ModBlocks;
import com.Equatorial.toukenranbu.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModZhCnLangProvider extends LanguageProvider {
    public ModZhCnLangProvider(PackOutput output) {
        super(output, ToukenRanbuMod.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        //材料、物品，杂七杂八，不包括没有上线的物品（做了目前不知道什么时候才能用得上的也在里面）
        add(ModItems.WOOTZ_STEEL.get(), "玉钢");
        add(ModItems.COOLANT.get(), "冷却材");
        add(ModItems.WHETSTONE.get(), "砥石");
        add(ModItems.AMULET.get(), "御守");
        add(ModItems.SUPREME_AMULET.get(), "极御守");
        add(ModItems.A_SET_OF_PAPER_AND_PEN.get(), "一套纸笔");
        add(ModItems.DAMAGED_SWORD_FRAGMENTS.get(), "断裂刀剑碎片");
        add(ModItems.POWER_OF_ATTORNEY.get(), "委托符");
        add(ModItems.SPEED_UP_POTION.get(), "加速符");
        add(ModItems.SMALL_KOBAN.get(), "小判");
        add(ModItems.GOLD_OMAMORI.get(), "金刀装");
        add(ModItems.SILVER_OMAMORI.get(), "银刀装");
        add(ModItems.BRONZE_OMAMORI.get(), "铜刀装");

        add(ModItems.TANTOU_SPAWN_EGG.get(), "敌短刀刷怪蛋");
        add(ModItems.WAKIZASHI_SPAWN_EGG.get(), "敌胁差刷怪蛋");
        add(ModItems.UCHIGATANA_SPAWN_EGG.get(), "敌打刀刷怪蛋");
        add(ModItems.TACHI_SPAWN_EGG.get(), "敌太刀刷怪蛋");
        add(ModItems.NAGINATA_SPAWN_EGG.get(), "敌薙刀刷怪蛋");
        add(ModItems.OOTACHI_SPAWN_EGG.get(), "敌大太刀刷怪蛋");
        add(ModItems.YARI_SPAWN_EGG.get(), "敌枪刷怪蛋");

        add(ModItems.TANTOU_PLUS_SPAWN_EGG.get(), "敌特化短刀刷怪蛋");
        add(ModItems.TACHI_PLUS_SPAWN_EGG.get(), "敌特化太刀刷怪蛋");
        add(ModItems.NAGINATA_PLUS_SPAWN_EGG.get(), "敌特化薙刀刷怪蛋");
        add(ModItems.OOTACHI_PLUS_SPAWN_EGG.get(), "敌特化大太刀刷怪蛋");
        add(ModItems.YARI_PLUS_SPAWN_EGG.get(), "敌特化枪刷怪蛋");
        add(ModItems.UCHIGATANA_PLUS_SPAWN_EGG.get(), "敌特化打刀刷怪蛋");
        add(ModItems.WAKIZASHI_PLUS_SPAWN_EGG.get(), "敌特化胁差刷怪蛋");

        add(ModItems.TANTOU_MAX_SPAWN_EGG.get(), "敌极化短刀刷怪蛋");
        add(ModItems.TACHI_MAX_SPAWN_EGG.get(), "敌极化太刀刷怪蛋");
        add(ModItems.NAGINATA_MAX_SPAWN_EGG.get(), "敌极化薙刀刷怪蛋");
        add(ModItems.OOTACHI_MAX_SPAWN_EGG.get(), "敌极化大太刀刷怪蛋");
        add(ModItems.UCHIGATANA_MAX_SPAWN_EGG.get(), "敌极化打刀刷怪蛋");
        add(ModItems.WAKIZASHI_MAX_SPAWN_EGG.get(), "敌极化胁差刷怪蛋");
        add(ModItems.YARI_MAX_SPAWN_EGG.get(), "敌极化枪刷怪蛋");

        add(ModItems.KEBIISHI_TACHI_SPAWN_EGG.get(), "检非违使：太刀刷怪蛋");
        add(ModItems.KEBIISHI_NAGINATA_SPAWN_EGG.get(), "检非违使：薙刀刷怪蛋");
        add(ModItems.KEBIISHI_OOTACHI_SPAWN_EGG.get(), "检非违使：大太刀刷怪蛋");
        add(ModItems.KEBIISHI_LEADER_SPAWN_EGG.get(), "检非违使：长柄枪刷怪蛋");
        add(ModItems.KEBIISHI_YARI_SPAWN_EGG.get(), "检非违使：枪刷怪蛋");

        add(ModItems.KONNOSUKE_SPAWN_EGG.get(), "狐之助刷怪蛋");

//刀剑男士
        add(ModItems.MIKAZUKI_MUNECHIKA.get(), "三日月宗近");
        add(ModItems.YAMANBAGIRI_KUNIHIRO.get(), "山姥切国广");
        add(ModItems.KASHUU_KIYOMITSU.get(), "加州清光");
        add(ModItems.HACHISUKA_KOTETSU.get(), "蜂须贺虎彻");
        add(ModItems.KASEN_KANESADA.get(), "歌仙兼定");
        add(ModItems.MUTSUNOKAMI_YOSHIYUKI.get(), "陆奥守吉行");

//食物
        add(ModItems.A_BITE_OF_DANGO.get(), "一口团子");
        add(ModItems.IMMORTAL_DUMPLINGS.get(), "仙人团子");
        add(ModItems.A_STRING_OF_DANGO.get(), "一串团子");
        add(ModItems.TURBID_SPIRITUAL_ENERGY.get(), "浑浊灵力");
        add(ModItems.PURE_SPIRITUAL_ENERGY.get(), "纯净灵力");
        add(ModItems.TROOP_CANDY.get(), "根兵糖（大）");
        add(ModItems.SOLDIER_CANDY.get(), "根兵糖（小）");
        add(ModItems.OIL_TOFU.get(), "油豆腐");
//由一个纯净灵力+一个浑浊灵力合成得到的空白灵力。属于燃料
        add(ModItems.VOID_SPIRITUAL_ENERGY.get(), "空白灵力");

//方块相关
        add(ModBlocks.WOOTZ_STEEL_BLOCK.get(), "玉钢方块");
        add(ModBlocks.COOLANT_BLOCK.get(), "冷却材方块");
        add(ModBlocks.WHETSTONE_BLOCK.get(), "砥石方块");
        add(ModBlocks.WOOTZ_STEEL_ORE.get(), "玉钢矿石");
        add(ModBlocks.COOLANT_ORE.get(), "冷却材矿石");
        add(ModBlocks.WHETSTONE_ORE.get(), "砥石矿石");

//建筑材料
        add(ModBlocks.WOOTZ_STEEL_STAIRS.get(),"玉钢楼梯");
        add(ModBlocks.WOOTZ_STEEL_SLAB.get(),"玉钢台阶");
        add(ModBlocks.WOOTZ_STEEL_BUTTON.get(),"玉钢按钮");
        add(ModBlocks.WOOTZ_STEEL_PRESSURE_PLATE.get(),"玉钢压力板");
        add(ModBlocks.WOOTZ_STEEL_FENCE.get(),"玉钢栅栏");
        add(ModBlocks.WOOTZ_STEEL_FENCE_GATE.get(),"玉钢栅栏门");
        add(ModBlocks.WOOTZ_STEEL_WALL.get(),"玉钢墙");
        add(ModBlocks.WOOTZ_STEEL_DOOR.get(),"玉钢门");
        add(ModBlocks.WOOTZ_STEEL_TRAPDOOR.get(),"玉钢活板门");
        add(ModBlocks.WHETSTONE_STAIRS.get(),"砥石楼梯");
        add(ModBlocks.WHETSTONE_SLAB.get(),"砥石台阶");
        add(ModBlocks.WHETSTONE_BUTTON.get(),"砥石按钮");
        add(ModBlocks.WHETSTONE_PRESSURE_PLATE.get(),"砥石压力板");
        add(ModBlocks.WHETSTONE_FENCE.get(),"砥石栅栏");
        add(ModBlocks.WHETSTONE_FENCE_GATE.get(),"砥石栅栏门");
        add(ModBlocks.WHETSTONE_WALL.get(),"砥石墙");
        add(ModBlocks.WHETSTONE_DOOR.get(),"砥石门");
        add(ModBlocks.WHETSTONE_TRAPDOOR.get(),"砥石活板门");

        add(ModBlocks.CHARRED_LOG.get(),"灰烬原木");
        add(ModBlocks.CHARRED_WOOD.get(),"灰烬木");
        add(ModBlocks.CHARRED_PLANKS.get(),"灰烬木板");
        add(ModBlocks.CHARRED_LEAVES.get(),"灰烬树叶");
        add(ModBlocks.STRIPPED_CHARRED_LOG.get(),"灰烬去皮原木");
        add(ModBlocks.STRIPPED_CHARRED_WOOD.get(),"去皮灰烬木");
        add(ModBlocks.CHARRED_STAIRS.get(),"灰烬木楼梯");
        add(ModBlocks.CHARRED_SLAB.get(),"焦燃木台阶");
        add(ModBlocks.CHARRED_FENCE.get(),"灰烬木栅栏");
        add(ModBlocks.CHARRED_FENCE_GATE.get(),"灰烬木栅栏门");
        add(ModBlocks.CHARRED_DOOR.get(),"灰烬木门");
        add(ModBlocks.CHARRED_TRAPDOOR.get(),"灰烬木活板门");
        add(ModBlocks.CHARRED_PRESSURE_PLATE.get(),"灰烬木压力板");
        add(ModBlocks.CHARRED_BUTTON.get(),"灰烬木按钮");
        add(ModBlocks.CHARRED_SAPLING.get(),"灰烬树苗");
        add(ModBlocks.CHARRED_GRASS_BLOCK.get(),"灰烬草方块");
        add(ModBlocks.CHARRED_DIRT.get(),"灰烬泥土");

//武器、工具等
        add(ModItems.WOOTZ_STEEL_SWORD.get(), "玉钢剑");
        add(ModItems.WOOTZ_STEEL_PICKAXE.get(), "玉钢镐");
        add(ModItems.WOOTZ_STEEL_AXE.get(), "玉钢斧");
        add(ModItems.WOOTZ_STEEL_SHOVEL.get(), "玉钢铲");
        add(ModItems.WOOTZ_STEEL_HOE.get(), "玉钢锄");
        add(ModItems.WHETSTONE_SWORD.get(), "砥石剑");
        add(ModItems.WHETSTONE_PICKAXE.get(), "砥石镐");
        add(ModItems.WHETSTONE_AXE.get(), "砥石斧");
        add(ModItems.WHETSTONE_SHOVEL.get(), "砥石铲");
        add(ModItems.WHETSTONE_HOE.get(), "砥石锄");

        add(ModItems.WOOTZ_STEEL_HELMET.get(), "玉钢头盔");
        add(ModItems.WOOTZ_STEEL_CHESTPLATE.get(), "玉钢胸甲");
        add(ModItems.WOOTZ_STEEL_LEGGINGS.get(), "玉钢护腿");
        add(ModItems.WOOTZ_STEEL_BOOTS.get(), "玉钢靴子");
        add(ModItems.WHETSTONE_HELMET.get(), "砥石头盔");
        add(ModItems.WHETSTONE_CHESTPLATE.get(), "砥石胸甲");
        add(ModItems.WHETSTONE_LEGGINGS.get(), "砥石护腿");
        add(ModItems.WHETSTONE_BOOTS.get(), "砥石靴子");

        add(ModItems.DICE.get(),"骰子");

        add("effect.toukenranbu_mod.spirit_regen", "灵力恢复加成");
        add("container.sword_forge", "锻刀炉");
        //add(ModBlocks.SWORD_FORGE.get(), "锻刀炉");

        add("itemGroup.toukenranbu_tab", "方块与刀剑乱舞");

        add("entity.minecraft.villager.toukenranbu_mod.bladesmith","刀剑师");

        add("message.toukenranbu_mod.summon_mikazuki_munechika",
                "我是三日月宗近。因锻造时形成的刃纹较多，故而名为三日月。请多关照。");
        add("message.toukenranbu_mod.summon_yamanbagiri_kunihiro",
                "山姥切国广。……你那是什么眼神，介意我是仿造品吗？");
        add("message.toukenranbu_mod.summon_hachisuka_kotetsu",
                "我是蜂须贺虎彻。希望你不要把我和赝品混为一谈。");
        add("message.toukenranbu_mod.summon_kasen_kanesada",
                "我是歌仙兼定，喜爱风雅的文系名刀。请多指教。");
        add("message.toukenranbu_mod.summon_kashuu_kiyomitsu",
                "啊－我是川下之子，加州清光。虽然不好上手，但性能很不错的喔。");
        add("message.toukenranbu_mod.summon_mutsunokami_yoshiyuki",
                "我是陆奥守吉行。好不容易来到这么豪华的地方，就抓住世界吧！");

        add("gui.toukenranbu.touken_danshi.title","刀剑男士");
        add("gui.toukenranbu.button.sit", "坐下");
        add("gui.toukenranbu.button.stand", "起身");
        add("gui.toukenranbu.button.follow", "跟随");
        add("gui.toukenranbu.button.unfollow", "停止跟随");
        add("gui.toukenranbu.button.farm", "畑当番");
        add("gui.toukenranbu.button.stop_farm", "畑当番终止");
        add("gui.toukenranbu.button.farm_escaping", "逃番中");
        add("gui.toukenranbu.button.farm_no_seeds", "畑当番(无种子)");

// 实体
        add("entity.toukenranbu_mod.mikazuki_munechika", "三日月宗近");
        add("entity.toukenranbu_mod.yamanbagiri_kunihiro", "山姥切国广");
        add("entity.toukenranbu_mod.hachisuka_kotetsu", "蜂须贺虎彻");
        add("entity.toukenranbu_mod.kasen_kanesada", "歌仙兼定");
        add("entity.toukenranbu_mod.kashuu_kiyomitsu", "加州清光");
        add("entity.toukenranbu_mod.mutsunokami_yoshiyuki", "陆奥守吉行");

// GUI 标题
        add("gui.toukenranbu.touken_danshi.title.mikazuki_munechika", "三日月宗近");
        add("gui.toukenranbu.touken_danshi.title.yamanbagiri_kunihiro", "山姥切国广");
        add("gui.toukenranbu.touken_danshi.title.hachisuka_kotetsu", "蜂须贺虎彻");
        add("gui.toukenranbu.touken_danshi.title.kasen_kanesada", "歌仙兼定");
        add("gui.toukenranbu.touken_danshi.title.kashuu_kiyomitsu", "加州清光");
        add("gui.toukenranbu.touken_danshi.title.mutsunokami_yoshiyuki", "陆奥守吉行");

        add("entity.toukenranbu_mod.tantou", "敌短刀");
        add("entity.toukenranbu_mod.wakizashi", "敌胁差");
        add("entity.toukenranbu_mod.uchigatana", "敌打刀");
        add("entity.toukenranbu_mod.tachi", "敌太刀");
        add("entity.toukenranbu_mod.ootachi", "敌大太刀");
        add("entity.toukenranbu_mod.naginata", "敌薙刀");
        add("entity.toukenranbu_mod.yari", "敌枪");

        add("entity.toukenranbu_mod.tantou_plus", "敌特化短刀");
        add("entity.toukenranbu_mod.wakizashi_plus", "敌特化胁差");
        add("entity.toukenranbu_mod.uchigatana_plus", "敌特化打刀");
        add("entity.toukenranbu_mod.tachi_plus", "敌特化太刀");
        add("entity.toukenranbu_mod.ootachi_plus", "敌特化大太刀");
        add("entity.toukenranbu_mod.naginata_plus", "敌特化薙刀");
        add("entity.toukenranbu_mod.yari_plus", "敌特化枪");

        add("entity.toukenranbu_mod.tantou_max", "敌极化短刀");
        add("entity.toukenranbu_mod.wakizashi_max", "敌极化胁差");
        add("entity.toukenranbu_mod.uchigatana_max", "敌极化打刀");
        add("entity.toukenranbu_mod.tachi_max", "敌极化太刀");
        add("entity.toukenranbu_mod.ootachi_max", "敌极化大太刀");
        add("entity.toukenranbu_mod.naginata_max", "敌极化薙刀");
        add("entity.toukenranbu_mod.yari_max", "敌极化枪");

        add("entity.toukenranbu_mod.kebiishi_leader", "检非违使：长柄枪");
        add("entity.toukenranbu_mod.kebiishi_tachi", "检非违使：太刀");
        add("entity.toukenranbu_mod.kebiishi_ootachi", "检非违使：大太刀");
        add("entity.toukenranbu_mod.kebiishi_naginata", "检非违使：薙刀");
        add("entity.toukenranbu_mod.kebiishi_yari", "检非违使：枪");

        add("entity.toukenranbu_mod.konnosuke","狐之助");
        // 以后加新实体：
        // add("entity.toukenranbu_mod.新刀名", "中文刀名");

        add("death.toukenranbu_mod.mikazuki_munechika", "嘛，有形之物终将消逝，不过是在今日而已。");
        add("death.toukenranbu_mod.yamanbagiri_kunihiro", "啊啊……真不愉快……即使消失之后，我也依然会被比较吗……");
        add("death.toukenranbu_mod.hachisuka_kotetsu", "我输了啊……这样……世间的赝品……岂不是……更加胡作非为……了吗。");
        add("death.toukenranbu_mod.kasen_kanesada", "啊啊……这就是彼岸吗。可得吟诵一首……谁能，把我的笔……");
        add("death.toukenranbu_mod.kashuu_kiyomitsu", "我……一直到最后都被爱着吗……？");
        add("death.toukenranbu_mod.mutsunokami_yoshiyuki", "什么啊……咱就到此为止了吗……新时代……见不到了……");

        add("toukenranbu.book.name", "刀剑乱舞百科全书");
        add("toukenranbu.landing_text", "欢迎来到刀剑乱舞的世界。《刀剑乱舞百科全书》涵盖刀剑乱舞模组的一切具体数据（不包括建筑方块等），" +
                "你所发现的这里面都会有记录。");

        add("message.toukenranbu_mod.kebiishi_tier_up", "§c§l【历史修正力上升】§r 检非违使的警戒等级提升至 %s ！");
        add("message.toukenranbu_mod.kebiishi_spawn", "§c§l【检非违使出现】§r 历史修正力正在向 %s 的位置聚集...");
        add("message.toukenranbu_mod.kebiishi.on", "§a检非违使生成已 §l开启");
        add("message.toukenranbu_mod.kebiishi.off", "§c检非违使生成已 §l关闭");
        add("message.toukenranbu_mod.kebiishi.status.on", "§a状态：开启");
        add("message.toukenranbu_mod.kebiishi.status.off", "§c状态：关闭");
        add("message.toukenranbu_mod.kebiishi.status.format", "%s §7| 下次刷新倒计时：%s分钟");
        add("message.toukenranbu_mod.kebiishi.countdown", "【历史修正力】检非违使预计 %s 分钟后到达...");
        add("message.toukenranbu_mod.kebiishi.warning", "【检非违使出现】历史修正力正在向 %s 的位置聚集...30秒后降临！");
        add("message.toukenranbu_mod.kebiishi.spawned", "【检非违使出现】历史修正力已降临！");
        add("message.toukenranbu_mod.kebiishi.nearby", "【历史修正力】附近已有检非违使，暂时停止聚集。");

// ========== 成就标题 & 描述 ==========
        add("advancements.toukenranbu_mod.new_start.title", "新的开始");
        add("advancements.toukenranbu_mod.new_start.description", "从物品栏自选一把初始刀开始你新的冒险。");

        add("advancements.toukenranbu_mod.use_starter_sword.title", "初阵");
        add("advancements.toukenranbu_mod.use_starter_sword.description", "从初始刀里使用并召唤任意一位刀剑男士。");

        add("advancements.toukenranbu_mod.defeat_the_enemy_forces.title", "战胜敌军部队");
        add("advancements.toukenranbu_mod.defeat_the_enemy_forces.description", "击杀时间溯行军敌人，直到掉落至少一个浑浊灵力。");

        add("advancements.toukenranbu_mod.trade_with_bladesmith.title", "不卖刀剑");
        add("advancements.toukenranbu_mod.trade_with_bladesmith.description", "跟刀剑师村民交易并获得纯净灵力。");

        add("advancements.toukenranbu_mod.toast_to_the_moon.title", "举杯邀月");
        add("advancements.toukenranbu_mod.toast_to_the_moon.description", "获得天下五剑：三日月宗近。");

        add("advancements.toukenranbu_mod.use_mikazuki_munechika.title", "月的物语");
        add("advancements.toukenranbu_mod.use_mikazuki_munechika.description", "召唤出天下五剑：三日月宗近。");

        add("advancements.toukenranbu_mod.has_amulet.title", "只是普通的......");
        add("advancements.toukenranbu_mod.has_amulet.description", "获得一个普通御守。");

        add("advancements.toukenranbu_mod.has_supreme_amulet.title", "金光闪闪");
        add("advancements.toukenranbu_mod.has_supreme_amulet.description", "获得一个极御守。");

        add("advancements.toukenranbu_mod.use_amulet.title", "命悬一线");
        add("advancements.toukenranbu_mod.use_amulet.description", "触发一次御守效果躲过死亡。");

        add("advancements.toukenranbu_mod.use_supreme_amulet.title", "生生不绝");
        add("advancements.toukenranbu_mod.use_supreme_amulet.description", "触发一次极御守的效果躲过死亡。");

        add("advancements.toukenranbu_mod.more_koban.title", "大富翁");
        add("advancements.toukenranbu_mod.more_koban.description", "你获得了许多小判。它们现在是你的了！");

        add("advancements.toukenranbu_mod.omamori.title", "这是什么？刀装！");
        add("advancements.toukenranbu_mod.omamori.description", "获得至少一个刀装。");

        add("advancements.toukenranbu_mod.sweet.title", "来一口团子");
        add("advancements.toukenranbu_mod.sweet.description", "获得一口团子、仙人团子、一串团子之一。");

        add("advancements.toukenranbu_mod.wootz_steel.title", "玉钢裹身");
        add("advancements.toukenranbu_mod.wootz_steel.description", "拥有一整套玉钢制作的护甲装备。");

        add("advancements.toukenranbu_mod.whetstone.title", "砥石裹身");
        add("advancements.toukenranbu_mod.whetstone.description", "拥有一整套砥石制作的护甲装备。");

        add("advancements.toukenranbu_mod.has_dice.title","特命调查");
        add("advancements.toukenranbu_mod.has_dice.description","接受时之政府的特命。");

        add("advancements.toukenranbu_mod.enter_abandoned_history.title", "无望的未来");
        add("advancements.toukenranbu_mod.enter_abandoned_history.description", "你已经进入了被篡改历史的世界。");

        add("advancements.toukenranbu_mod.three_way_meeting.title", "三方会晤");
        add("advancements.toukenranbu_mod.three_way_meeting.description", "历史修正力已至，审神者、刀剑男士与检非违使的宿命交锋即将开始。");

    }
}
