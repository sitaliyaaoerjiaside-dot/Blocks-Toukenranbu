package com.Equatorial.toukenranbu.block;

import com.Equatorial.toukenranbu.block.custom.ModFlammableRotatedPillarBlock;
import com.Equatorial.toukenranbu.item.ModItems;
import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.world.tree.CharredTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ToukenRanbuMod.MOD_ID);

    public static final RegistryObject<Block> WOOTZ_STEEL_BLOCK =
            registerBlock("wootz_steel_block", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F,3.0F)));
    public static final RegistryObject<Block> COOLANT_BLOCK =
            registerBlock("coolant_block", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F,3.0F)));
    public static final RegistryObject<Block> WHETSTONE_BLOCK =
            registerBlock("whetstone_block", () -> new Block(BlockBehaviour.Properties.of().strength(1.5F,3.0F)));
    public static final RegistryObject<Block> WOOTZ_STEEL_ORE =
            registerBlock("wootz_steel_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> COOLANT_ORE =
            registerBlock("coolant_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> WHETSTONE_ORE =
            registerBlock("whetstone_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<StairBlock> WOOTZ_STEEL_STAIRS =
            registerBlock("wootz_steel_stairs",
                    () -> new StairBlock(() -> WOOTZ_STEEL_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of().strength(3.0F, 2.0f)));
    public static final RegistryObject<SlabBlock> WOOTZ_STEEL_SLAB =
            registerBlock("wootz_steel_slab",
                    () -> new SlabBlock(BlockBehaviour.Properties.of().strength(3.0F, 2.0f)));
    public static final RegistryObject<ButtonBlock> WOOTZ_STEEL_BUTTON =
            registerBlock("wootz_steel_button",
                    () -> new ButtonBlock(BlockBehaviour.Properties.of().strength(3.0F, 2.0f), BlockSetType.OAK, 40, false));
    public static final RegistryObject<PressurePlateBlock> WOOTZ_STEEL_PRESSURE_PLATE =
            registerBlock("wootz_steel_pressure_plate",
                    () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().strength(1.0F, 2.0f), BlockSetType.OAK));
    public static final RegistryObject<FenceGateBlock> WOOTZ_STEEL_FENCE_GATE =
            registerBlock("wootz_steel_fence_gate",
                    () -> new FenceGateBlock(BlockBehaviour.Properties.of().strength(3.0F, 2.0f), WoodType.OAK));
    public static final RegistryObject<FenceBlock> WOOTZ_STEEL_FENCE =
            registerBlock("wootz_steel_fence",
                    () -> new FenceBlock(BlockBehaviour.Properties.of().strength(3.0F, 2.0f)));
    public static final RegistryObject<WallBlock> WOOTZ_STEEL_WALL =
            registerBlock("wootz_steel_wall",
                    () -> new WallBlock(BlockBehaviour.Properties.of().strength(3.0F, 2.0f)));
    public static final RegistryObject<DoorBlock> WOOTZ_STEEL_DOOR =
            registerBlock("wootz_steel_door",
                    () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0F, 2.0f).noOcclusion(),BlockSetType.IRON));
    public static final RegistryObject<TrapDoorBlock> WOOTZ_STEEL_TRAPDOOR =
            registerBlock("wootz_steel_trapdoor",
                    () -> new TrapDoorBlock(BlockBehaviour.Properties.of().strength(3.0F, 2.0f).noOcclusion(),BlockSetType.OAK));

    public static final RegistryObject<StairBlock> WHETSTONE_STAIRS =
            registerBlock("whetstone_stairs",
                    () -> new StairBlock(() -> WHETSTONE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of().strength(3.0F, 2.0f)));
    public static final RegistryObject<SlabBlock> WHETSTONE_SLAB =
            registerBlock("whetstone_slab",
                    () -> new SlabBlock(BlockBehaviour.Properties.of().strength(3.0F, 2.0f)));
    public static final RegistryObject<ButtonBlock> WHETSTONE_BUTTON =
            registerBlock("whetstone_button",
                    () -> new ButtonBlock(BlockBehaviour.Properties.of().strength(3.0F, 2.0f), BlockSetType.OAK, 40, false));
    public static final RegistryObject<PressurePlateBlock> WHETSTONE_PRESSURE_PLATE =
            registerBlock("whetstone_pressure_plate",
                    () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().strength(1.0F, 2.0f), BlockSetType.OAK));
    public static final RegistryObject<FenceGateBlock> WHETSTONE_FENCE_GATE =
            registerBlock("whetstone_fence_gate",
                    () -> new FenceGateBlock(BlockBehaviour.Properties.of().strength(3.0F, 2.0f), WoodType.OAK));
    public static final RegistryObject<FenceBlock> WHETSTONE_FENCE =
            registerBlock("whetstone_fence",
                    () -> new FenceBlock(BlockBehaviour.Properties.of().strength(3.0F, 2.0f)));
    public static final RegistryObject<WallBlock> WHETSTONE_WALL =
            registerBlock("whetstone_wall",
                    () -> new WallBlock(BlockBehaviour.Properties.of().strength(3.0F, 2.0f)));
    public static final RegistryObject<DoorBlock> WHETSTONE_DOOR =
            registerBlock("whetstone_door",
                    () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0F, 2.0f).noOcclusion(),BlockSetType.IRON));
    public static final RegistryObject<TrapDoorBlock> WHETSTONE_TRAPDOOR =
            registerBlock("whetstone_trapdoor",
                    () -> new TrapDoorBlock(BlockBehaviour.Properties.of().strength(3.0F, 2.0f).noOcclusion(),BlockSetType.OAK));

    public static final RegistryObject<Block> CHARRED_LOG =
            registerBlock("charred_log",
                    () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3f)));
    public static final RegistryObject<Block> CHARRED_WOOD =
            registerBlock("charred_wood",
                    () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(3f)));
    public static final RegistryObject<Block> STRIPPED_CHARRED_LOG =
            registerBlock("stripped_charred_log",
                    () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).strength(3f)));
    public static final RegistryObject<Block> STRIPPED_CHARRED_WOOD =
            registerBlock("stripped_charred_wood",
                    () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).strength(3f)));
    public static final RegistryObject< Block> CHARRED_SAPLING = registerBlock("charred_sapling",
            () -> new SaplingBlock(new CharredTreeGrower(),BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    public static final RegistryObject<Block> CHARRED_PLANKS =
            registerBlock("charred_planks",
                    () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
                        @Override
                        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                            return true;
                        }

                        @Override
                        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                            return 20;
                        }

                        @Override
                        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                            return 5;
                        }
                    });
    public static final RegistryObject<Block> CHARRED_LEAVES =
            registerBlock("charred_leaves",
                    () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)) {
                        @Override
                        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                            return true;
                        }

                        @Override
                        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                            return 60;
                        }

                        @Override
                        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                            return 30;
                        }
                    });
    public static final RegistryObject<StairBlock> CHARRED_STAIRS = registerBlock("charred_stairs",
                    () -> new StairBlock(() -> CHARRED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(CHARRED_PLANKS.get())));
    public static final RegistryObject<SlabBlock> CHARRED_SLAB = registerBlock("charred_slab",
                    () -> new SlabBlock(BlockBehaviour.Properties.copy(CHARRED_PLANKS.get())));
    public static final RegistryObject<ButtonBlock> CHARRED_BUTTON = registerBlock("charred_button",
                    () -> new ButtonBlock(BlockBehaviour.Properties.copy(CHARRED_PLANKS.get()),
                            BlockSetType.OAK, 30, true));
    public static final RegistryObject<PressurePlateBlock> CHARRED_PRESSURE_PLATE =
            registerBlock("charred_pressure_plate",
                    () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
                            BlockBehaviour.Properties.copy(CHARRED_PLANKS.get()), BlockSetType.OAK));
    public static final RegistryObject<FenceGateBlock> CHARRED_FENCE_GATE =
            registerBlock("charred_fence_gate",
                    () -> new FenceGateBlock(BlockBehaviour.Properties.copy(CHARRED_PLANKS.get()), WoodType.OAK));
    public static final RegistryObject<FenceBlock> CHARRED_FENCE =
            registerBlock("charred_fence", () -> new FenceBlock(
                            BlockBehaviour.Properties.copy(CHARRED_PLANKS.get())));
    public static final RegistryObject<DoorBlock> CHARRED_DOOR =
            registerBlock("charred_door",
                    () -> new DoorBlock(BlockBehaviour.Properties.copy(CHARRED_PLANKS.get())
                            .noOcclusion().strength(3.0F), BlockSetType.OAK));
    public static final RegistryObject<TrapDoorBlock> CHARRED_TRAPDOOR =
            registerBlock("charred_trapdoor",
                    () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(CHARRED_PLANKS.get())
                            .noOcclusion(), BlockSetType.OAK));
    public static final RegistryObject<Block> CHARRED_DIRT =
            registerBlock("charred_dirt", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT)));
    public static final RegistryObject<Block> CHARRED_GRASS_BLOCK =
            registerBlock("charred_grass_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    //public static final RegistryObject<Block> SWORD_FORGE = registerBlock("sword_forge",
            //() -> new SwordForgeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    private static <T extends Block> void registerBlockItems(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name,() -> new BlockItem(block.get(), new Item.Properties()));
    }
private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> blocks = BLOCKS.register(name, block);
        registerBlockItems(name, blocks);
        return blocks;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
