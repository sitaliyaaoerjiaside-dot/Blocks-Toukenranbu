# 方块与刀剑乱舞 (Touken Ranbu Mod)

基于 Minecraft 1.20.1 / Forge 的《刀剑乱舞》非官方同人模组。

## 📢 1.4.0 版本更新日志

· 此版本移除了 LDLib2 的全部相关资源，本模组不再作为前置模组使用。
· 从此版本开始服务器可以使用本模组。
· 从此版本开始将不对任何修复的问题进行罗列。

· 修复了部分已知问题。
· 将召唤出来的刀剑男士的默认模式设置为跟随而非闲逛。
· 重做刀剑男士的 UI 页面。
· 重新加入了未实装的宝物格。
· 将白三日月宗近的 BOSS 血量提高至 200 万。
· 调整了时之政府分部奖励箱内刀剑召唤物品的刷新频率。
· 加入了更多小判可兑换的村民/流浪商人货品分支。
· 加入了可供自由编辑的气泡文本。（注：服务器目前不支持自由改文本，想改自己的请在单人环境下操作）
· 完善了白三日月宗近的部分机制。（仍未完成）
· 为废弃历史维度加入了部分原版生物群系。
· 将村民加入白夜之庭维度的可生成生物名单。
· 因为森罗物语新版本更新，移除了与模组森罗物语相关的联动机制。
· 因为白三日月宗近的机制更改，现在可以拿去自由斗蛐蛐了。（会很有节目效果的）

---

## ⚠️ 服务器游玩注意事项
· 服务器已可以使用本模组进行游玩，如果有相关 Bug 请报告作者。
· **注意，作者不会对以下服务器问题作出回应**：
- 因为不当操作导致服务器没有被正常关闭导致的数据丢失、操作回滚；
- 因为服务器没有及时保存数据导致的数据丢失等。
  · 如果要在服务器上进行游玩，请注意正确开关服务器以及保存数据。若出现因为以上原因造成的各种各样的问题均后果自负。建议使用 `/stop` 正常关服或定期 `/save-all`。

---

## 当前内容

### 刀剑男士
- 收录三日月宗近、山姥切国广、加州清光、蜂须贺虎彻、歌仙兼定、陆奥守吉行、一期一振、鹤丸国永、烛台切光忠、石切丸、压切长谷部、大和守安定、山姥切长义、大俱利伽罗、蜻蛉切、巴形薙刀、笑面青江、堀川国广、今剑、后藤藤四郎等数十位刀剑男士
- 每位刀剑男士拥有独立的六维属性、等级成长、疲劳度、刀装、护甲、马匹与本体刀系统
- 支持阵型系统、手合系统、跟随、种田、挖矿、巡逻、矿洞清缴等工作 AI
- 召唤默认处于跟随状态，拥有全新的 UI 页面与宝物格占位
- 狐之助作为中立动物游荡于维度之中

### 敌对势力
- 时间溯行军：短刀、胁差、打刀、太刀、大太刀、薙刀、枪，含特化与极化版本
- 检非违使：太刀、大太刀、薙刀、枪、长柄枪
- 历史修正力提升后会主动降临

### 新维度
- 白夜之庭（White Night Garden）：永夜与昼夜交替共存的静谧维度
  - 自定义蓝色渐变天空与冷色调光照
  - 粉色与蓝色樱花林交错生成
  - 漫天飘落的月见樱花瓣与忽明忽暗的月光光点
  - 仅生成刀剑男士与动物，不生成原版怪物（现已加入村民生成）
- 废弃的历史（Abandoned History）：被篡改历史后的荒芜战场（现已加入部分原版生物群系，如沙漠、恶地等）

### 新矿石与材料
- 冷却材、砥石、玉钢，配套完整的工具、护甲与建材系列

### 道具系统
- 御守 / 极御守：抵挡致命伤害
- 刀装：金 / 银 / 铜三档
- 绘马：绑定与召唤刀剑男士
- 收容符 / 群体收容符：收容与释放刀剑男士
- 骰子、月相、委托符、加速符、小判等特命道具（小判现可兑换更多村民/流浪商人商品）
- 刀剑乱舞百科全书：集成 Patchouli，支持中英双语

### 系统
- 全新的原版绘制 UI 系统，无需 LDLib2 前置
- 阵型、疲劳度、灵力恢复、混合伤害等战斗机制
- 支持通过 KubeJS 自由编辑气泡文本（仅限单人/客户端）
- 白三日月宗近 BOSS 拥有独立的阶段机制与血量（200万）

## 开发环境
- Minecraft 1.20.1
- Forge 47.4.0
- GeckoLib 4（实体动画）
- Custom Portal API Reforged（自定义传送门）
- Patchouli（可选，手册）
- KubeJS（可选，脚本支持）
- JEI（可选，配方查看；开发端未安装 JEI 不影响构建与数据生成）

## 已知问题
- 其他已知问题将在此处持续更新。

## 反馈与支持
- 仓库地址：https://github.com/sitaliyaaoerjiaside-dot/Blocks-Toukenranbu
- 问题反馈：https://github.com/sitaliyaaoerjiaside-dot/Blocks-Toukenranbu/issues
- 如有 Bug、建议或兼容性问题，欢迎在 Issues 中提出。

## 第三方组件与致谢
本项目使用了以下第三方开源组件，按各自协议分发：

- **GeckoLib** — 实体动画
- **Custom Portal API Reforged** — 自定义传送门
- **Patchouli** — 游戏内手册

感谢以上项目的作者与维护者的开源贡献。

## 许可证
见 [LICENSE.txt](./LICENSE.txt)

---
---

# Blocks & Touken Ranbu Mod

An unofficial fan-made Minecraft mod based on *Touken Ranbu*, built for **Minecraft 1.20.1** with **Forge**.

## 📢 Version 1.4.0 Changelog

- Removed all resources related to LDLib2. This mod no longer requires it as a dependency.
- Servers can now use this mod starting from this version.
- Starting from this version, fixed issues will no longer be listed.

- Fixed some known issues.
- Changed the default mode of summoned Touken Danshi to Follow instead of Wander.
- Redesigned the UI page for Touken Danshi.
- Re-added the unimplemented Treasure slot.
- Increased the BOSS HP of White Mikazuki Munechika to 2,000,000.
- Adjusted the drop rates of Touken summon items in the Government Branch loot chests.
- Added more trade branches for Small Koban with villagers/wandering traders.
- Added freely editable bubble text. (Note: Servers currently do not support custom text; edit this in single-player).
- Improved some mechanics of White Mikazuki Munechika. (Still incomplete)
- Added some vanilla biomes to the Abandoned History dimension.
- Added villagers to the spawn list of the White Night Garden dimension.
- Removed the integration with the mod Kaleidoscope Cookery due to its new version update.
- Due to the mechanic changes of White Mikazuki Munechika, he can now be used for fun boss battles. (Very entertaining)

---

## ⚠️ Server Play Notice
- Servers can now use this mod. If you find any bugs, please report them to the author.
- **Please note, the author will NOT respond to the following server issues**:
  - Data loss or rollbacks caused by improper server shutdowns (e.g., force-killing the process).
  - Data loss caused by the server not saving data in time.
- If you are playing on a server, please ensure you shut down the server properly and save data. You are solely responsible for any consequences caused by the above reasons. It is recommended to use `/stop` or `/save-all` regularly.

---

## Features

### Touken Danshi Entities
- Includes Mikazuki Munechika, Yamanbagiri Kunihiro, Kashuu Kiyomitsu, Hachisuka Kotetsu, Kasen Kanesada, Mutsunokami Yoshiyuki, Ichigo Hitofuri, Tsurumaru Kuninaga, Shokudaikiri Mitsutada, Ishikirimaru, Heshikiri Hasebe, Yamatonokami Yasusada, Yamanbagiri Chogi, Ookurikara, Tonbokiri, Tomoegata Naginata, Nikkari Aoe, Horikawa Kunihiro, Imanotsurugi, Gotou Toushirou, and more.
- Each Danshi has its own six-stat growth, leveling, fatigue, knife equipment, armor, mount, and blade system.
- Supports formation, sparring, following, farming, mining, patrol, and cave clearance AI.
- Summons default to Follow mode. Features a new UI and a placeholder Treasure slot.
- Konnosuke roams the dimension as a neutral animal.

### Enemy Factions
- History Retrograde Army: Tantou, Wakizashi, Uchigatana, Tachi, Ootachi, Naginata, Yari (with Plus and Max variants).
- Kebiishi: Tachi, Ootachi, Naginata, Yari, Leader.
- Triggered when Historical Revision Force rises.

### New Dimensions
- White Night Garden: A serene dimension under eternal night with a day-night cycle.
  - Custom blue gradient sky and cool-toned lighting.
  - Pink and blue cherry groves interleaved.
  - Falling Tsukimi cherry petals and flickering moonlight particles.
  - Only Touken Danshi and animals spawn; no vanilla monsters. (Villagers are now included in the spawn list).
- Abandoned History: A wasteland of tampered history. (Now includes some vanilla biomes like deserts and badlands).

### New Ores & Materials
- Coolant, Whetstone, and Wootz Steel with full tool, armor, and building block sets.

### Items & Charms
- Amulet / Supreme Amulet: Prevent lethal damage.
- Knife equipment: Gold / Silver / Bronze tiers.
- Ema: Bind and summon Touken Danshi.
- Capture Talisman / Group Capture Talisman: Capture and release Danshi.
- Dice, Moon Phase, Power of Attorney, Speed-up Potion, and other special items.
- Touken Ranbu Encyclopedia: Patchouli integration (EN / ZH_CN).

### Systems
- Newly redesigned vanilla-rendered UI (no longer requires LDLib2).
- Formation, fatigue, spirit recovery, and mixed damage mechanics.
- Supports freely editable bubble text via KubeJS (single-player/client only).
- White Mikazuki Munechika BOSS features unique phase mechanics and 2,000,000 HP.

## Development Environment
- Minecraft 1.20.1
- Forge 47.4.0
- GeckoLib 4 (entity animations)
- Custom Portal API Reforged (custom portals)
- Patchouli (optional, guidebook)
- KubeJS (optional, scripting)
- JEI (optional, recipe viewer)

## Known Issues
- Other known issues will be updated here.

## Feedback & Support
- Repository: https://github.com/sitaliyaaoerjiaside-dot/Blocks-Toukenranbu
- Issue Tracker: https://github.com/sitaliyaaoerjiaside-dot/Blocks-Toukenranbu/issues
- Bug reports, suggestions, and compatibility issues are welcome.

## Credits & Third-Party Components
This project uses the following open-source components, distributed under their respective licenses:

- **GeckoLib** — Entity animations
- **Custom Portal API Reforged** — Custom portals
- **Patchouli** — In-game guidebook

Thanks to the authors and maintainers of the above projects for their open-source contributions.

## License
See [LICENSE.txt](./LICENSE.txt)