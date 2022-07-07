package com.teksmods.compactpumpjack.block;

import com.teksmods.compactpumpjack.CompactPumpjack;
import com.teksmods.compactpumpjack.item.ModItemGroup;
import com.teksmods.compactpumpjack.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, CompactPumpjack.MOD_ID);

    public static final RegistryObject<Block> CPJTESTBLOCK = registerBlock("cpjtextblock",
            () -> new Block(AbstractBlock.Properties
            .create(Material.IRON)
            .harvestLevel(2)
            .harvestTool(ToolType.PICKAXE)
            .setRequiresTool()
            .hardnessAndResistance(5f)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ModItemGroup.CPJ_GROUP)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}