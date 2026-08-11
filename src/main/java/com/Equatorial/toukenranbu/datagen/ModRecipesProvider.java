package com.Equatorial.toukenranbu.datagen;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.block.ModBlocks;
import com.Equatorial.toukenranbu.item.ModItems;
import com.Equatorial.toukenranbu.tag.ModItemTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipesProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipesProvider(PackOutput pOutput) {
        super(pOutput);
    }

    public static final List<ItemLike> WOOTZ_STEEL = List.of(ModBlocks.WOOTZ_STEEL_ORE.get());
    public static final List<ItemLike> COOLANT = List.of(ModBlocks.COOLANT_ORE.get());
    public static final List<ItemLike> WHETSTONE = List.of(ModBlocks.WHETSTONE_ORE.get());

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreSmelting(pWriter, WOOTZ_STEEL, RecipeCategory.MISC, ModItems.WOOTZ_STEEL.get(), 0.25f, 200, "wootz_steel");
        oreBlasting(pWriter, WOOTZ_STEEL, RecipeCategory.MISC, ModItems.WOOTZ_STEEL.get(), 0.25f, 100, "wootz_steel");
        oreSmelting(pWriter, COOLANT, RecipeCategory.MISC, ModItems.COOLANT.get(), 0.25f, 200, "coolant");
        oreBlasting(pWriter, COOLANT, RecipeCategory.MISC, ModItems.COOLANT.get(), 0.25f, 100, "coolant");
        oreSmelting(pWriter, WHETSTONE, RecipeCategory.MISC, ModItems.WHETSTONE.get(), 0.25f, 200, "whetstone");
        oreBlasting(pWriter, WHETSTONE, RecipeCategory.MISC, ModItems.WHETSTONE.get(), 0.25f, 100, "whetstone");

//分割线，往下是玉钢类建材
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WOOTZ_STEEL_STAIRS.get(), 4)
                .pattern("  #")
                .pattern(" ##")
                .pattern("###")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WOOTZ_STEEL_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ModBlocks.WOOTZ_STEEL_BUTTON.get())
                .requires(ModItems.WOOTZ_STEEL.get())
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.WOOTZ_STEEL_PRESSURE_PLATE.get())
                .pattern("##")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WOOTZ_STEEL_FENCE.get(), 3)
                .pattern("#|#")
                .pattern("#|#")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WOOTZ_STEEL_FENCE_GATE.get())
                .pattern("|#|")
                .pattern("|#|")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WOOTZ_STEEL_WALL.get(), 4)
                .pattern(" # ")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.WOOTZ_STEEL_DOOR.get(), 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.WOOTZ_STEEL_TRAPDOOR.get(), 2)
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WOOTZ_STEEL.get(), 9)
                .requires(ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.WOOTZ_STEEL_BLOCK.get()), has(ModBlocks.WOOTZ_STEEL_BLOCK.get()))
                .save(pWriter);

//这里也是分割线分一下，看的头晕眼花的。冷却材不能做装备也不能做工具更不能做建材
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COOLANT_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.COOLANT.get())
                .unlockedBy(getHasName(ModItems.COOLANT.get()), has(ModItems.COOLANT.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COOLANT.get(), 9)
                .requires(ModBlocks.COOLANT_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.COOLANT_BLOCK.get()), has(ModBlocks.COOLANT_BLOCK.get()))
                .save(pWriter);

//分割线分一下，看的头晕眼花的，往下是砥石类建材
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WHETSTONE_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.WHETSTONE.get())
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WHETSTONE_STAIRS.get(), 4)
                .pattern("  #")
                .pattern(" ##")
                .pattern("###")
                .define('#', ModItems.WHETSTONE.get())
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WHETSTONE_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModItems.WHETSTONE.get())
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ModBlocks.WHETSTONE_BUTTON.get())
                .requires(ModItems.WHETSTONE.get())
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.WHETSTONE_PRESSURE_PLATE.get())
                .pattern("##")
                .define('#', ModItems.WHETSTONE.get())
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WHETSTONE_FENCE.get(), 3)
                .pattern("#|#")
                .pattern("#|#")
                .define('#', ModItems.WHETSTONE.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WHETSTONE_FENCE_GATE.get())
                .pattern("|#|")
                .pattern("|#|")
                .define('#', ModItems.WHETSTONE.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WHETSTONE_WALL.get(), 4)
                .pattern(" # ")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.WHETSTONE.get())
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.WHETSTONE_DOOR.get(), 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', ModItems.WHETSTONE.get())
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.WHETSTONE_TRAPDOOR.get(), 2)
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.WHETSTONE.get())
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WHETSTONE.get(), 9)
                .requires(ModBlocks.WHETSTONE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.WHETSTONE_BLOCK.get()), has(ModBlocks.WHETSTONE_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, Items.SUGAR, 3)
                .pattern("###")
                .define('#', ModItemTags.SUGER_TAG)
                .unlockedBy(getHasName(Items.BEETROOT), has(Items.BEETROOT))
                .save(pWriter, ToukenRanbuMod.MOD_ID + ":"+ "sugar_from_beetroot");

//这是工具的制造方式，设定为必须有序合成。
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.WOOTZ_STEEL_SWORD.get())
                .pattern(" # ")
                .pattern(" # ")
                .pattern(" | ")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.WOOTZ_STEEL_AXE.get())
                .pattern("## ")
                .pattern("#| ")
                .pattern(" | ")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.WOOTZ_STEEL_PICKAXE.get())
                .pattern("###")
                .pattern(" | ")
                .pattern(" | ")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.WOOTZ_STEEL_SHOVEL.get())
                .pattern(" # ")
                .pattern(" | ")
                .pattern(" | ")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.WOOTZ_STEEL_HOE.get())
                .pattern("## ")
                .pattern(" | ")
                .pattern(" | ")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.WHETSTONE_SWORD.get())
                .pattern(" # ")
                .pattern(" # ")
                .pattern(" | ")
                .define('#', ModItems.WHETSTONE.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.WHETSTONE_AXE.get())
                .pattern("## ")
                .pattern("#| ")
                .pattern(" | ")
                .define('#', ModItems.WHETSTONE.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.WHETSTONE_PICKAXE.get())
                .pattern("###")
                .pattern(" | ")
                .pattern(" | ")
                .define('#', ModItems.WHETSTONE.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.WHETSTONE_SHOVEL.get())
                .pattern(" # ")
                .pattern(" | ")
                .pattern(" | ")
                .define('#', ModItems.WHETSTONE.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.WHETSTONE_HOE.get())
                .pattern("## ")
                .pattern(" | ")
                .pattern(" | ")
                .define('#', ModItems.WHETSTONE.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);

//往下是盔甲类型的制造方式
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.WOOTZ_STEEL_HELMET.get())
                .pattern("###")
                .pattern("# #")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.WOOTZ_STEEL_CHESTPLATE.get())
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.WOOTZ_STEEL_LEGGINGS.get())
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.WOOTZ_STEEL_BOOTS.get())
                .pattern("# #")
                .pattern("# #")
                .define('#', ModItems.WOOTZ_STEEL.get())
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.WHETSTONE_HELMET.get())
                .pattern("###")
                .pattern("# #")
                .define('#', ModItems.WHETSTONE.get())
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.WHETSTONE_CHESTPLATE.get())
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.WHETSTONE.get())
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.WHETSTONE_LEGGINGS.get())
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', ModItems.WHETSTONE.get())
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.WHETSTONE_BOOTS.get())
                .pattern("# #")
                .pattern("# #")
                .define('#', ModItems.WHETSTONE.get())
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);

//极御守的制造方式，需要工作台
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SUPREME_AMULET.get())
                .pattern("|||")
                .pattern("|#|")
                .pattern("|||")
                .define('#', ModItems.AMULET.get())
                .define('|', Blocks.GOLD_BLOCK)
                .unlockedBy(getHasName(ModItems.AMULET.get()), has(ModItems.AMULET.get()))
                .save(pWriter);

//空白灵力燃料的合成方式
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.VOID_SPIRITUAL_ENERGY.get())
                .requires(ModItems.TURBID_SPIRITUAL_ENERGY.get())
                .requires(ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.TURBID_SPIRITUAL_ENERGY.get()), has(ModItems.TURBID_SPIRITUAL_ENERGY.get()))
                .unlockedBy(getHasName(ModItems.PURE_SPIRITUAL_ENERGY.get()), has(ModItems.PURE_SPIRITUAL_ENERGY.get()))
                .save(pWriter);

//合成矿石，不知道为什么要加反正来都来了
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WOOTZ_STEEL_ORE.get())
                .requires(ModItems.WOOTZ_STEEL.get())
                .requires(Blocks.STONE)
                .unlockedBy(getHasName(ModItems.WOOTZ_STEEL.get()), has(ModItems.WOOTZ_STEEL.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COOLANT_ORE.get())
                .requires(ModItems.COOLANT.get())
                .requires(Blocks.STONE)
                .unlockedBy(getHasName(ModItems.COOLANT.get()), has(ModItems.COOLANT.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WHETSTONE_ORE.get())
                .requires(ModItems.WHETSTONE.get())
                .requires(Blocks.STONE)
                .unlockedBy(getHasName(ModItems.WHETSTONE.get()), has(ModItems.WHETSTONE.get()))
                .save(pWriter);

//分割线————————————————————————
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHARRED_WOOD.get(),3)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.CHARRED_LOG.get())
                .unlockedBy(getHasName(ModBlocks.CHARRED_LOG.get()), has(ModBlocks.CHARRED_LOG.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHARRED_STAIRS.get(), 4)
                .pattern("  #")
                .pattern(" ##")
                .pattern("###")
                .define('#', ModBlocks.CHARRED_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHARRED_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.CHARRED_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ModBlocks.CHARRED_BUTTON.get())
                .requires(ModItems.WHETSTONE.get())
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.CHARRED_PRESSURE_PLATE.get())
                .pattern("##")
                .define('#', ModBlocks.CHARRED_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHARRED_FENCE.get(), 3)
                .pattern("#|#")
                .pattern("#|#")
                .define('#', ModItems.WHETSTONE.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHARRED_FENCE_GATE.get())
                .pattern("|#|")
                .pattern("|#|")
                .define('#', ModBlocks.CHARRED_PLANKS.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.CHARRED_DOOR.get(), 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.CHARRED_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.CHARRED_TRAPDOOR.get(), 2)
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.CHARRED_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Blocks.CHEST)
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', ModBlocks.CHARRED_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:chest_from_charred_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.STICK, 4)
                .pattern("#")
                .pattern("#")
                .define('#', ModBlocks.CHARRED_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:stick_from_charred_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.BOOKSHELF)
                .pattern("###")
                .pattern("|||")
                .pattern("###")
                .define('#', ModBlocks.CHARRED_PLANKS.get())
                .define('|', Items.BOOK)
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:bookshelf_from_charred_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.LECTERN)
                .pattern("###")
                .pattern(" | ")
                .pattern(" # ")
                .define('#', ModBlocks.CHARRED_PLANKS.get())
                .define('|', Blocks.BOOKSHELF)
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:lectern_from_charred_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.CRAFTING_TABLE)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.CHARRED_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:crafting_table_from_charred_planks");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHARRED_PLANKS.get(),4)
                .requires(ModBlocks.CHARRED_LOG.get())
                .unlockedBy(getHasName(ModBlocks.CHARRED_LOG.get()), has(ModBlocks.CHARRED_LOG.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.WOODEN_SWORD)
                .pattern(" # ")
                .pattern(" # ")
                .pattern(" | ")
                .define('#', ModBlocks.CHARRED_PLANKS.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:wooden_sword_from_charred_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.WOODEN_AXE)
                .pattern("## ")
                .pattern("#| ")
                .pattern(" | ")
                .define('#', ModBlocks.CHARRED_PLANKS.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:wooden_axe_from_charred_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.WOODEN_PICKAXE)
                .pattern("###")
                .pattern(" | ")
                .pattern(" | ")
                .define('#', ModBlocks.CHARRED_PLANKS.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:wooden_pickaxe_from_charred_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.WOODEN_SHOVEL)
                .pattern(" # ")
                .pattern(" | ")
                .pattern(" | ")
                .define('#', ModBlocks.CHARRED_PLANKS.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:wooden_shovel_from_charred_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.WOODEN_HOE)
                .pattern("## ")
                .pattern(" | ")
                .pattern(" | ")
                .define('#', ModBlocks.CHARRED_PLANKS.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.CHARRED_PLANKS.get()), has(ModBlocks.CHARRED_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:wooden_hoe_from_charred_planks");

//食物——————————
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModItems.A_BITE_OF_DANGO.get())
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(Items.SUGAR), has(Items.SUGAR))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.A_STRING_OF_DANGO.get())
                .pattern(" # ")
                .pattern(" # ")
                .pattern(" | ")
                .define('#', Items.SUGAR)
                .define('|', Items.STICK)
                .unlockedBy(getHasName(Items.SUGAR), has(Items.STICK))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.IMMORTAL_DUMPLINGS.get())
                .pattern("###")
                .pattern("###")
                .pattern("|||")
                .define('#', Items.SUGAR)
                .define('|', Items.STICK)
                .unlockedBy(getHasName(Items.SUGAR), has(Items.STICK))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.SOLDIER_CANDY.get())
                .pattern("###")
                .pattern("###")
                .pattern("|||")
                .define('#', Items.SUGAR)
                .define('|', Items.PAPER)
                .unlockedBy(getHasName(Items.SUGAR), has(Items.STICK))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.TROOP_CANDY.get())
                .pattern("###")
                .pattern("###")
                .pattern("|||")
                .define('#', ModItems.SOLDIER_CANDY.get())
                .define('|', Items.PAPER)
                .unlockedBy(getHasName(Items.SUGAR), has(Items.STICK))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLD_OMAMORI.get())
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('#', Items.GOLD_INGOT)
                .define('|', Items.GOLD_BLOCK)
                .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SILVER_OMAMORI.get())
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('#', ModItems.COOLANT.get())
                .define('|', ModBlocks.COOLANT_BLOCK.get())
                .unlockedBy(getHasName(ModItems.COOLANT.get()), has(ModItems.COOLANT.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRONZE_OMAMORI.get())
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('#', Items.COPPER_INGOT)
                .define('|', Items.COPPER_BLOCK)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(pWriter);
        // 油豆腐：海带 + 糖 + 面包 竖直有序合成 → 油豆腐 × 4
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.OIL_TOFU.get(), 4)
                .pattern(" # ")
                .pattern(" | ")
                .pattern(" @ ")
                .define('#', Items.KELP)
                .define('|', Items.SUGAR)
                .define('@', Items.BREAD)
                .unlockedBy(getHasName(Items.KELP), has(Items.KELP))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DICE.get())
                .pattern(" # ")
                .pattern("#|#")
                .pattern(" # ")
                .define('#', ModItems.TURBID_SPIRITUAL_ENERGY.get())
                .define('|', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.TURBID_SPIRITUAL_ENERGY.get()), has(ModItems.TURBID_SPIRITUAL_ENERGY.get()))
                .save(pWriter);

    }
    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime,
                    pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, ToukenRanbuMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }
}
