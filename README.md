# 方块与刀剑乱舞 (Touken Ranbu Mod)

基于 Minecraft 1.20.1 / Forge 的《刀剑乱舞》非官方同人模组。

## 当前内容

### 刀剑男士
- 收录三日月宗近、山姥切国广、加州清光、蜂须贺虎彻、歌仙兼定、陆奥守吉行、一期一振、鹤丸国永、烛台切光忠、石切丸、压切长谷部、大和守安定、山姥切长义、大俱利伽罗、蜻蛉切、巴形薙刀、笑面青江、堀川国广、今剑、后藤藤四郎等数十位刀剑男士
- 每位刀剑男士拥有独立的六维属性、等级成长、疲劳度、刀装、护甲、马匹与本体刀系统
- 支持阵型系统、手合系统、跟随、种田、挖矿、巡逻、矿洞清缴等工作 AI
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
    - 仅生成刀剑男士与动物，不生成原版怪物
- 废弃的历史（Abandoned History）：被篡改历史后的荒芜战场

### 新矿石与材料
- 冷却材、砥石、玉钢，配套完整的工具、护甲与建材系列

### 道具系统
- 御守 / 极御守：抵挡致命伤害
- 刀装：金 / 银 / 铜三档
- 绘马：绑定与召唤刀剑男士
- 收容符 / 群体收容符：收容与释放刀剑男士
- 骰子、月相、委托符、加速符、小判等特命道具
- 刀剑乱舞百科全书：集成 Patchouli，支持中英双语

### 系统
- 基于 LDLib2 的灵力值 UI 与刀男能力面板
- 阵型、疲劳度、灵力恢复、混合伤害等战斗机制

## 未实装内容（规划中）
- BOSS：白三日月宗近（舞台剧设定，失去主人后走向消亡前的形态）——尚未实装
- 白夜之庭内的祭坛结构与 BOSS 召唤机制
- 更多自定义结构

## 开发环境
- Minecraft 1.20.1
- Forge 47.4.0
- GeckoLib 4（实体动画）
- LDLib2（UI）
  - 原版仓库：https://github.com/Low-Drag-MC/LDLib2
  - 1.20.1 Forge 社区移植版：https://github.com/weiliangyan/LDLib2-1.20.1-Forge
  - 原作者 / 版权所有者：KilaBash / Low-Drag-MC
  - 许可协议：GNU Lesser General Public License v3.0
- Custom Portal API Reforged（自定义传送门）
- Kotlin For Forge
- Patchouli（可选，手册）
- KubeJS（可选，脚本支持）

## 反馈与支持
- 仓库地址：https://github.com/sitaliyaaoerjiaside-dot/Blocks-Toukenranbu
- 问题反馈：https://github.com/sitaliyaaoerjiaside-dot/Blocks-Toukenranbu/issues
- 如有 Bug、建议或兼容性问题，欢迎在 Issues 中提出。

## 第三方组件与致谢
本项目使用了以下第三方开源组件，按各自协议分发：

- **LDLib2** (LGPL-3.0)
  - 原版仓库：https://github.com/Low-Drag-MC/LDLib2
  - 原作者：KilaBash / Low-Drag-MC
- **LDLib2 1.20.1 Forge 移植版** (LGPL-3.0)
  - 移植版仓库：https://github.com/weiliangyan/LDLib2-1.20.1-Forge
  - 移植版维护者：weiliangyan
  - 原项目文档：https://low-drag-mc.github.io/LowDragMC-Doc/en/ldlib2/
- **GeckoLib** — 实体动画
- **Custom Portal API Reforged** — 自定义传送门
- **Kotlin For Forge** — Kotlin 运行支持
- **Patchouli** — 游戏内手册

感谢以上项目的作者与维护者的开源贡献。

## 许可证
见 [LICENSE.txt](./LICENSE.txt)

---

# Blocks & Touken Ranbu Mod

An unofficial fan-made Minecraft mod based on *Touken Ranbu*, built for **Minecraft 1.20.1** with **Forge**.

## Features

### Touken Danshi Entities
- Includes Mikazuki Munechika, Yamanbagiri Kunihiro, Kashuu Kiyomitsu, Hachisuka Kotetsu, Kasen Kanesada, Mutsunokami Yoshiyuki, Ichigo Hitofuri, Tsurumaru Kuninaga, Shokudaikiri Mitsutada, Ishikirimaru, Heshikiri Hasebe, Yamatonokami Yasusada, Yamanbagiri Chogi, Ookurikara, Tonbokiri, Tomoegata Naginata, Nikkari Aoe, Horikawa Kunihiro, Imanotsurugi, Gotou Toushirou, and more
- Each Danshi has its own six-stat growth, leveling, fatigue, knife equipment, armor, mount, and blade system
- Supports formation, sparring, following, farming, mining, patrol, and cave clearance AI
- Konnosuke roams the dimension as a neutral animal

### Enemy Factions
- History Retrograde Army: Tantou, Wakizashi, Uchigatana, Tachi, Ootachi, Naginata, Yari (with Plus and Max variants)
- Kebiishi: Tachi, Ootachi, Naginata, Yari, Leader
- Triggered when Historical Revision Force rises

### New Dimensions
- White Night Garden: A serene dimension under eternal night with day-night cycle
    - Custom blue gradient sky and cool-toned lighting
    - Pink and blue cherry groves interleaved
    - Falling Tsukimi cherry petals and flickering moonlight particles
    - Only Touken Danshi and animals spawn; no vanilla monsters
- Abandoned History: A wasteland of tampered history

### New Ores & Materials
- Coolant, Whetstone, and Wootz Steel with full tool, armor, and building block sets

### Items & Charms
- Amulet / Supreme Amulet: prevent lethal damage
- Knife equipment: Gold / Silver / Bronze tiers
- Ema: bind and summon Touken Danshi
- Capture Talisman / Group Capture Talisman: capture and release Danshi
- Dice, Moon Phase, Power of Attorney, Speed-up Potion, and other special items
- Touken Ranbu Encyclopedia: Patchouli integration (EN / ZH_CN)

### Systems
- LDLib2-based Spirit Power HUD and Danshi ability panel
- Formation, fatigue, spirit recovery, and mixed damage mechanics

## Planned but Not Yet Implemented
- Boss: White Mikazuki Munechika (a form from the stage play, before his demise) — not yet implemented
- Altar structure and boss summon mechanic in the White Night Garden
- More custom structures

## Development Environment
- Minecraft 1.20.1
- Forge 47.4.0
- GeckoLib 4 (entity animations)
- LDLib2 (UI)
  - Upstream repository: https://github.com/Low-Drag-MC/LDLib2
  - 1.20.1 Forge community port: https://github.com/weiliangyan/LDLib2-1.20.1-Forge
  - Original author / copyright holder: KilaBash / Low-Drag-MC
  - License: GNU Lesser General Public License v3.0
- Custom Portal API Reforged (custom portals)
- Kotlin For Forge
- Patchouli (optional, guidebook)
- KubeJS (optional, scripting)

## Feedback & Support
- Repository: https://github.com/sitaliyaaoerjiaside-dot/Blocks-Toukenranbu
- Issue Tracker: https://github.com/sitaliyaaoerjiaside-dot/Blocks-Toukenranbu/issues
- Bug reports, suggestions, and compatibility issues are welcome.

## Credits & Third-Party Components
This project uses the following open-source components, distributed under their respective licenses:

- **LDLib2** (LGPL-3.0)
  - Upstream repository: https://github.com/Low-Drag-MC/LDLib2
  - Original author: KilaBash / Low-Drag-MC
- **LDLib2 1.20.1 Forge Port** (LGPL-3.0)
  - Port repository: https://github.com/weiliangyan/LDLib2-1.20.1-Forge
  - Port maintainer: weiliangyan
  - Documentation: https://low-drag-mc.github.io/LowDragMC-Doc/en/ldlib2/
- **GeckoLib** — entity animations
- **Custom Portal API Reforged** — custom portals
- **Kotlin For Forge** — Kotlin runtime support
- **Patchouli** — in-game guidebook

Thanks to the authors and maintainers of the above projects for their open-source contributions.

## License
See [LICENSE.txt](./LICENSE.txt)