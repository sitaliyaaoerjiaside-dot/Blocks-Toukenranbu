package com.Equatorial.toukenranbu.datagen;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.block.ModBlocks;
import com.Equatorial.toukenranbu.item.ModItems;
import com.Equatorial.toukenranbu.tag.ModItemTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
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

        oreSmelting(pWriter, List.of(ModItems.TURBID_SPIRITUAL_ENERGY.get()),
                RecipeCategory.MISC, ModItems.PURE_SPIRITUAL_ENERGY.get(),
                0.5f, 1000, "pure_spiritual_energy");

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

//需要工作台
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SUPREME_AMULET.get())
                .pattern("|||")
                .pattern("|#|")
                .pattern("|||")
                .define('#', ModItems.AMULET.get())
                .define('|', Blocks.GOLD_BLOCK)
                .unlockedBy(getHasName(ModItems.AMULET.get()), has(ModItems.AMULET.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModItems.CAPTAIN_BADGE.get())
                .pattern("###")
                .pattern("|@|")
                .pattern("$$$")
                .define('#', ModItems.A_SET_OF_PAPER_AND_PEN.get())
                .define('|', Items.STICK)
                .define('@', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .define('$', Items.PAPER)
                .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModItems.A_SET_OF_PAPER_AND_PEN.get())
                .pattern("##|")
                .pattern("##|")
                .define('#', Items.PAPER)
                .define('|', Items.STICK)
                .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
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
                .requires(ModBlocks.CHARRED_PLANKS.get())
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
                .define('#', ModBlocks.CHARRED_PLANKS.get())
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

        //分割线————————————————————————
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CHERRY_WOOD.get(), 3)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.BLUE_CHERRY_LOG.get())
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_LOG.get()), has(ModBlocks.BLUE_CHERRY_LOG.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CHERRY_STAIRS.get(), 4)
                .pattern("  #")
                .pattern(" ##")
                .pattern("###")
                .define('#', ModBlocks.BLUE_CHERRY_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CHERRY_SLAB.get(), 6)
                .pattern("###")
                .define('#', ModBlocks.BLUE_CHERRY_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ModBlocks.BLUE_CHERRY_BUTTON.get())
                .requires(ModBlocks.BLUE_CHERRY_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.BLUE_CHERRY_PRESSURE_PLATE.get())
                .pattern("##")
                .define('#', ModBlocks.BLUE_CHERRY_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CHERRY_FENCE.get(), 3)
                .pattern("#|#")
                .pattern("#|#")
                .define('#', ModBlocks.BLUE_CHERRY_PLANKS.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CHERRY_FENCE_GATE.get())
                .pattern("|#|")
                .pattern("|#|")
                .define('#', ModBlocks.BLUE_CHERRY_PLANKS.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.BLUE_CHERRY_DOOR.get(), 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.BLUE_CHERRY_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.BLUE_CHERRY_TRAPDOOR.get(), 2)
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.BLUE_CHERRY_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.BLUE_CHERRY_PLANKS.get(), 4)
                .requires(ModBlocks.BLUE_CHERRY_LOG.get())
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_LOG.get()), has(ModBlocks.BLUE_CHERRY_LOG.get()))
                .save(pWriter);

        // 用月见樱木板合成原版物品（带独立 save 名，不覆盖原版配方）
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Blocks.CHEST)
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .define('#', ModBlocks.BLUE_CHERRY_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:chest_from_blue_cherry_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Items.STICK, 4)
                .pattern("#")
                .pattern("#")
                .define('#', ModBlocks.BLUE_CHERRY_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:stick_from_blue_cherry_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.BOOKSHELF)
                .pattern("###")
                .pattern("|||")
                .pattern("###")
                .define('#', ModBlocks.BLUE_CHERRY_PLANKS.get())
                .define('|', Items.BOOK)
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:bookshelf_from_blue_cherry_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.LECTERN)
                .pattern("###")
                .pattern(" | ")
                .pattern(" # ")
                .define('#', ModBlocks.BLUE_CHERRY_PLANKS.get())
                .define('|', Blocks.BOOKSHELF)
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:lectern_from_blue_cherry_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.CRAFTING_TABLE)
                .pattern("##")
                .pattern("##")
                .define('#', ModBlocks.BLUE_CHERRY_PLANKS.get())
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:crafting_table_from_blue_cherry_planks");

        // 用月见樱木板合成木制工具（带独立 save 名，不覆盖原版配方）
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.WOODEN_SWORD)
                .pattern(" # ")
                .pattern(" # ")
                .pattern(" | ")
                .define('#', ModBlocks.BLUE_CHERRY_PLANKS.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:wooden_sword_from_blue_cherry_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.WOODEN_AXE)
                .pattern("## ")
                .pattern("#| ")
                .pattern(" | ")
                .define('#', ModBlocks.BLUE_CHERRY_PLANKS.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:wooden_axe_from_blue_cherry_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.WOODEN_PICKAXE)
                .pattern("###")
                .pattern(" | ")
                .pattern(" | ")
                .define('#', ModBlocks.BLUE_CHERRY_PLANKS.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:wooden_pickaxe_from_blue_cherry_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.WOODEN_SHOVEL)
                .pattern(" # ")
                .pattern(" | ")
                .pattern(" | ")
                .define('#', ModBlocks.BLUE_CHERRY_PLANKS.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:wooden_shovel_from_blue_cherry_planks");
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.WOODEN_HOE)
                .pattern("## ")
                .pattern(" | ")
                .pattern(" | ")
                .define('#', ModBlocks.BLUE_CHERRY_PLANKS.get())
                .define('|', Items.STICK)
                .unlockedBy(getHasName(ModBlocks.BLUE_CHERRY_PLANKS.get()), has(ModBlocks.BLUE_CHERRY_PLANKS.get()))
                .save(pWriter, "toukenranbu_mod:wooden_hoe_from_blue_cherry_planks");

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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CAPTURE_BALL.get(), 2)
                .pattern(" # ")
                .pattern(" | ")
                .pattern(" @ ")
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .define('|', Items.PAPER)
                .define('@', ModItems.TURBID_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GROUP_CAPTURE_BALL.get(), 1)
                .pattern("#|@")
                .pattern("#|@")
                .pattern("#|@")
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .define('|', Items.PAPER)
                .define('@', ModItems.TURBID_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.POWER_OF_ATTORNEY.get(), 2)
                .pattern(" # ")
                .pattern(" | ")
                .pattern(" # ")
                .define('#', ModItems.TURBID_SPIRITUAL_ENERGY.get())
                .define('|', Items.PAPER)
                .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SPEED_UP_POTION.get(), 2)
                .pattern(" @ ")
                .pattern(" | ")
                .pattern(" @ ")
                .define('|', Items.PAPER)
                .define('@', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MOON_PHASE.get(), 1)
                .pattern(" | ")
                .pattern(" @ ")
                .pattern(" | ")
                .define('|', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .define('@', ModItems.DICE.get())
                .unlockedBy(getHasName(ModItems.DICE.get()), has(ModItems.DICE.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.WHITE_NIGHT_PORTAL_FRAME.get(), 1)
                .pattern(" @ ")
                .pattern("|||")
                .pattern(" @ ")
                .define('|', ModItems.TURBID_SPIRITUAL_ENERGY.get())
                .define('@', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.TURBID_SPIRITUAL_ENERGY.get()), has(ModItems.TURBID_SPIRITUAL_ENERGY.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EMA.get(), 1)
                .pattern("@|@")
                .define('|', Items.PAPER)
                .define('@', ModItems.A_SET_OF_PAPER_AND_PEN.get())
                .unlockedBy(getHasName(ModItems.A_SET_OF_PAPER_AND_PEN.get()), has(ModItems.A_SET_OF_PAPER_AND_PEN.get()))
                .save(pWriter);

// ========== 三日月宗近 ==========
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MIKAZUKI_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.MIKAZUKI_MUNECHIKA.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.MIKAZUKI_MUNECHIKA.get()), has(ModItems.MIKAZUKI_MUNECHIKA.get()))
                .save(pWriter);

        // ========== 太刀 ==========
// 一期一振
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ICHIGO_HITOFURI_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.ICHIGO_HITOFURI.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.ICHIGO_HITOFURI.get()), has(ModItems.ICHIGO_HITOFURI.get()))
                .save(pWriter);

// 鹤丸国永
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TSURUMARU_KUNINAGA_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.TSURUMARU_KUNINAGA.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.TSURUMARU_KUNINAGA.get()), has(ModItems.TSURUMARU_KUNINAGA.get()))
                .save(pWriter);

// 烛台切光忠
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SHOKUDAIKIRI_MITSUTADA_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.SHOKUDAIKIRI_MITSUTADA.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.SHOKUDAIKIRI_MITSUTADA.get()), has(ModItems.SHOKUDAIKIRI_MITSUTADA.get()))
                .save(pWriter);

// ========== 大太刀 ==========
// 石切丸
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ISHIKIRIMARU_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.ISHIKIRIMARU.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.ISHIKIRIMARU.get()), has(ModItems.ISHIKIRIMARU.get()))
                .save(pWriter);

// ========== 打刀 ==========
// 压切长谷部
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HESHIKIRI_HASEBE_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.HESHIKIRI_HASEBE.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.HESHIKIRI_HASEBE.get()), has(ModItems.HESHIKIRI_HASEBE.get()))
                .save(pWriter);

// 大和守安定
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.YAMATONOKAMI_YASUSADA_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.YAMATONOKAMI_YASUSADA.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.YAMATONOKAMI_YASUSADA.get()), has(ModItems.YAMATONOKAMI_YASUSADA.get()))
                .save(pWriter);

// 山姥切长义
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.YAMANBAGIRI_CHOGI_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.YAMANBAGIRI_CHOGI.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.YAMANBAGIRI_CHOGI.get()), has(ModItems.YAMANBAGIRI_CHOGI.get()))
                .save(pWriter);

// 大俱利伽罗
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.OOKURIKARA_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.OOKURIKARA.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.OOKURIKARA.get()), has(ModItems.OOKURIKARA.get()))
                .save(pWriter);

// 加州清光
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.KASHUU_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.KASHUU_KIYOMITSU.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.KASHUU_KIYOMITSU.get()), has(ModItems.KASHUU_KIYOMITSU.get()))
                .save(pWriter);

// 蜂须贺虎彻
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HACHISUKA_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.HACHISUKA_KOTETSU.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.HACHISUKA_KOTETSU.get()), has(ModItems.HACHISUKA_KOTETSU.get()))
                .save(pWriter);

// 山姥切国广
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.YAMANBAGIRI_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.YAMANBAGIRI_KUNIHIRO.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.YAMANBAGIRI_KUNIHIRO.get()), has(ModItems.YAMANBAGIRI_KUNIHIRO.get()))
                .save(pWriter);

// 歌仙兼定
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.KASEN_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.KASEN_KANESADA.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.KASEN_KANESADA.get()), has(ModItems.KASEN_KANESADA.get()))
                .save(pWriter);

// 陆奥守吉行
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MUTSUNOKAMI_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.MUTSUNOKAMI_YOSHIYUKI.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.MUTSUNOKAMI_YOSHIYUKI.get()), has(ModItems.MUTSUNOKAMI_YOSHIYUKI.get()))
                .save(pWriter);

// ========== 枪 ==========
// 蜻蛉切
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TONBOKIRI_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.TONBOKIRI.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.TONBOKIRI.get()), has(ModItems.TONBOKIRI.get()))
                .save(pWriter);

// ========== 薙刀 ==========
// 巴形薙刀
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TOMOEGATA_NAGINATA_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.TOMOEGATA_NAGINATA.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.TOMOEGATA_NAGINATA.get()), has(ModItems.TOMOEGATA_NAGINATA.get()))
                .save(pWriter);

// ========== 胁差 ==========
// 笑面青江
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NIKKARI_AOE_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.NIKKARI_AOE.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.NIKKARI_AOE.get()), has(ModItems.NIKKARI_AOE.get()))
                .save(pWriter);

// 堀川国广
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HORIKAWA_KUNIHIRO_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.HORIKAWA_KUNIHIRO.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.HORIKAWA_KUNIHIRO.get()), has(ModItems.HORIKAWA_KUNIHIRO.get()))
                .save(pWriter);

// ========== 短刀 ==========
// 今剑
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IMANOTSURUGI_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.IMANOTSURUGI.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.IMANOTSURUGI.get()), has(ModItems.IMANOTSURUGI.get()))
                .save(pWriter);

// 后藤藤四郎
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOTOU_TOUSHIROU_BLADE.get(), 1)
                .pattern(" @ ")
                .pattern("$|%")
                .pattern(" # ")
                .define('|', ModItems.GOTOU_TOUSHIROU.get())
                .define('@', ModBlocks.WHETSTONE_BLOCK.get())
                .define('$', ModBlocks.COOLANT_BLOCK.get())
                .define('#', ModBlocks.WOOTZ_STEEL_BLOCK.get())
                .define('%', Blocks.COAL_BLOCK)
                .unlockedBy(getHasName(ModItems.GOTOU_TOUSHIROU.get()), has(ModItems.GOTOU_TOUSHIROU.get()))
                .save(pWriter);

        // ========== 三日月宗近 ==========
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MIKAZUKI_MUNECHIKA.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.MIKAZUKI_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.MIKAZUKI_BLADE.get()), has(ModItems.MIKAZUKI_BLADE.get()))
                .save(pWriter);

        // ========== 太刀 ==========
// 一期一振
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ICHIGO_HITOFURI.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.ICHIGO_HITOFURI_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.ICHIGO_HITOFURI_BLADE.get()), has(ModItems.ICHIGO_HITOFURI_BLADE.get()))
                .save(pWriter);

// 鹤丸国永
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TSURUMARU_KUNINAGA.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.TSURUMARU_KUNINAGA_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.TSURUMARU_KUNINAGA_BLADE.get()), has(ModItems.TSURUMARU_KUNINAGA_BLADE.get()))
                .save(pWriter);

// 烛台切光忠
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SHOKUDAIKIRI_MITSUTADA.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.SHOKUDAIKIRI_MITSUTADA_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.SHOKUDAIKIRI_MITSUTADA_BLADE.get()), has(ModItems.SHOKUDAIKIRI_MITSUTADA_BLADE.get()))
                .save(pWriter);

// ========== 大太刀 ==========
// 石切丸
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ISHIKIRIMARU.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.ISHIKIRIMARU_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.ISHIKIRIMARU_BLADE.get()), has(ModItems.ISHIKIRIMARU_BLADE.get()))
                .save(pWriter);

// ========== 打刀 ==========
// 压切长谷部
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HESHIKIRI_HASEBE.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.HESHIKIRI_HASEBE_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.HESHIKIRI_HASEBE_BLADE.get()), has(ModItems.HESHIKIRI_HASEBE_BLADE.get()))
                .save(pWriter);

// 大和守安定
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.YAMATONOKAMI_YASUSADA.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.YAMATONOKAMI_YASUSADA_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.YAMATONOKAMI_YASUSADA_BLADE.get()), has(ModItems.YAMATONOKAMI_YASUSADA_BLADE.get()))
                .save(pWriter);

// 山姥切长义
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.YAMANBAGIRI_CHOGI.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.YAMANBAGIRI_CHOGI_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.YAMANBAGIRI_CHOGI_BLADE.get()), has(ModItems.YAMANBAGIRI_CHOGI_BLADE.get()))
                .save(pWriter);

// 大俱利伽罗
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.OOKURIKARA.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.OOKURIKARA_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.OOKURIKARA_BLADE.get()), has(ModItems.OOKURIKARA_BLADE.get()))
                .save(pWriter);

// 加州清光
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.KASHUU_KIYOMITSU.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.KASHUU_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.KASHUU_BLADE.get()), has(ModItems.KASHUU_BLADE.get()))
                .save(pWriter);

// 蜂须贺虎彻
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HACHISUKA_KOTETSU.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.HACHISUKA_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.HACHISUKA_BLADE.get()), has(ModItems.HACHISUKA_BLADE.get()))
                .save(pWriter);

// 山姥切国广
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.YAMANBAGIRI_KUNIHIRO.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.YAMANBAGIRI_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.YAMANBAGIRI_BLADE.get()), has(ModItems.YAMANBAGIRI_BLADE.get()))
                .save(pWriter);

// 歌仙兼定
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.KASEN_KANESADA.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.KASEN_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.KASEN_BLADE.get()), has(ModItems.KASEN_BLADE.get()))
                .save(pWriter);

// 陆奥守吉行
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MUTSUNOKAMI_YOSHIYUKI.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.MUTSUNOKAMI_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.MUTSUNOKAMI_BLADE.get()), has(ModItems.MUTSUNOKAMI_BLADE.get()))
                .save(pWriter);

// ========== 枪 ==========
// 蜻蛉切
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TONBOKIRI.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.TONBOKIRI_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.TONBOKIRI_BLADE.get()), has(ModItems.TONBOKIRI_BLADE.get()))
                .save(pWriter);

// ========== 薙刀 ==========
// 巴形薙刀
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TOMOEGATA_NAGINATA.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.TOMOEGATA_NAGINATA_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.TOMOEGATA_NAGINATA_BLADE.get()), has(ModItems.TOMOEGATA_NAGINATA_BLADE.get()))
                .save(pWriter);

// ========== 胁差 ==========
// 笑面青江
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NIKKARI_AOE.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.NIKKARI_AOE_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.NIKKARI_AOE_BLADE.get()), has(ModItems.NIKKARI_AOE_BLADE.get()))
                .save(pWriter);

// 堀川国广
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HORIKAWA_KUNIHIRO.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.HORIKAWA_KUNIHIRO_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.HORIKAWA_KUNIHIRO_BLADE.get()), has(ModItems.HORIKAWA_KUNIHIRO_BLADE.get()))
                .save(pWriter);

// ========== 短刀 ==========
// 今剑
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IMANOTSURUGI.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.IMANOTSURUGI_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.IMANOTSURUGI_BLADE.get()), has(ModItems.IMANOTSURUGI_BLADE.get()))
                .save(pWriter);

// 后藤藤四郎
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOTOU_TOUSHIROU.get(), 1)
                .pattern("###")
                .pattern("#|#")
                .pattern("###")
                .define('|', ModItems.GOTOU_TOUSHIROU_BLADE.get())
                .define('#', ModItems.PURE_SPIRITUAL_ENERGY.get())
                .unlockedBy(getHasName(ModItems.GOTOU_TOUSHIROU_BLADE.get()), has(ModItems.GOTOU_TOUSHIROU_BLADE.get()))
                .save(pWriter);

        // ========== 刀男召唤物品 → 附魔之瓶 ==========

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 36)
                .requires(ModItems.MIKAZUKI_MUNECHIKA.get())
                .unlockedBy(getHasName(ModItems.MIKAZUKI_MUNECHIKA.get()), has(ModItems.MIKAZUKI_MUNECHIKA.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "mikazuki_munechika_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 12)
                .requires(ModItems.YAMANBAGIRI_KUNIHIRO.get())
                .unlockedBy(getHasName(ModItems.YAMANBAGIRI_KUNIHIRO.get()), has(ModItems.YAMANBAGIRI_KUNIHIRO.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "yamanbagiri_kunihiro_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 12)
                .requires(ModItems.KASHUU_KIYOMITSU.get())
                .unlockedBy(getHasName(ModItems.KASHUU_KIYOMITSU.get()), has(ModItems.KASHUU_KIYOMITSU.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "kashuu_kiyomitsu_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 12)
                .requires(ModItems.HACHISUKA_KOTETSU.get())
                .unlockedBy(getHasName(ModItems.HACHISUKA_KOTETSU.get()), has(ModItems.HACHISUKA_KOTETSU.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "hachisuka_kotetsu_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 12)
                .requires(ModItems.KASEN_KANESADA.get())
                .unlockedBy(getHasName(ModItems.KASEN_KANESADA.get()), has(ModItems.KASEN_KANESADA.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "kasen_kanesada_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 12)
                .requires(ModItems.MUTSUNOKAMI_YOSHIYUKI.get())
                .unlockedBy(getHasName(ModItems.MUTSUNOKAMI_YOSHIYUKI.get()), has(ModItems.MUTSUNOKAMI_YOSHIYUKI.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "mutsunokami_yoshiyuki_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 18)
                .requires(ModItems.ICHIGO_HITOFURI.get())
                .unlockedBy(getHasName(ModItems.ICHIGO_HITOFURI.get()), has(ModItems.ICHIGO_HITOFURI.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "ichigo_hitofuri_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 18)
                .requires(ModItems.TSURUMARU_KUNINAGA.get())
                .unlockedBy(getHasName(ModItems.TSURUMARU_KUNINAGA.get()), has(ModItems.TSURUMARU_KUNINAGA.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "tsurumaru_kuninaga_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 12)
                .requires(ModItems.SHOKUDAIKIRI_MITSUTADA.get())
                .unlockedBy(getHasName(ModItems.SHOKUDAIKIRI_MITSUTADA.get()), has(ModItems.SHOKUDAIKIRI_MITSUTADA.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "shokudaikiri_mitsutada_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 12)
                .requires(ModItems.ISHIKIRIMARU.get())
                .unlockedBy(getHasName(ModItems.ISHIKIRIMARU.get()), has(ModItems.ISHIKIRIMARU.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "ishikirimaru_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 12)
                .requires(ModItems.HESHIKIRI_HASEBE.get())
                .unlockedBy(getHasName(ModItems.HESHIKIRI_HASEBE.get()), has(ModItems.HESHIKIRI_HASEBE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "heshikiri_hasebe_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 12)
                .requires(ModItems.YAMATONOKAMI_YASUSADA.get())
                .unlockedBy(getHasName(ModItems.YAMATONOKAMI_YASUSADA.get()), has(ModItems.YAMATONOKAMI_YASUSADA.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "yamatonokami_yasusada_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 12)
                .requires(ModItems.YAMANBAGIRI_CHOGI.get())
                .unlockedBy(getHasName(ModItems.YAMANBAGIRI_CHOGI.get()), has(ModItems.YAMANBAGIRI_CHOGI.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "yamanbagiri_chogi_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 12)
                .requires(ModItems.OOKURIKARA.get())
                .unlockedBy(getHasName(ModItems.OOKURIKARA.get()), has(ModItems.OOKURIKARA.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "ookurikara_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 16)
                .requires(ModItems.TONBOKIRI.get())
                .unlockedBy(getHasName(ModItems.TONBOKIRI.get()), has(ModItems.TONBOKIRI.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "tonbokiri_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 16)
                .requires(ModItems.TOMOEGATA_NAGINATA.get())
                .unlockedBy(getHasName(ModItems.TOMOEGATA_NAGINATA.get()), has(ModItems.TOMOEGATA_NAGINATA.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "tomoegata_naginata_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 8)
                .requires(ModItems.NIKKARI_AOE.get())
                .unlockedBy(getHasName(ModItems.NIKKARI_AOE.get()), has(ModItems.NIKKARI_AOE.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "nikkari_aoe_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 8)
                .requires(ModItems.HORIKAWA_KUNIHIRO.get())
                .unlockedBy(getHasName(ModItems.HORIKAWA_KUNIHIRO.get()), has(ModItems.HORIKAWA_KUNIHIRO.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "horikawa_kunihiro_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 4)
                .requires(ModItems.IMANOTSURUGI.get())
                .unlockedBy(getHasName(ModItems.IMANOTSURUGI.get()), has(ModItems.IMANOTSURUGI.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "imanotsurugi_to_exp_bottle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE, 4)
                .requires(ModItems.GOTOU_TOUSHIROU.get())
                .unlockedBy(getHasName(ModItems.GOTOU_TOUSHIROU.get()), has(ModItems.GOTOU_TOUSHIROU.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "gotou_toushirou_to_exp_bottle"));

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
