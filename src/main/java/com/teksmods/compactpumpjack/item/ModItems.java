package com.teksmods.compactpumpjack.item;

import com.teksmods.compactpumpjack.fluid.ModFluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import com.teksmods.compactpumpjack.CompactPumpjack;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CompactPumpjack.MOD_ID);

    public static final RegistryObject<Item> CPJTEST = ITEMS.register("cpjtest",
            () -> new Item(new Item.Properties().group(ModItemGroup.CPJ_GROUP)));
    public static final RegistryObject<Item> CPJCREATIVETAB = ITEMS.register("cpjcreativetab",() -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PH2O_BUCKET = ITEMS.register("ph2o_bucket",
            () -> new BucketItem(() -> ModFluids.EXPLOSIVEWATER_FLUID.get(),
                    new Item.Properties().maxStackSize(1).group(ModItemGroup.CPJ_GROUP)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
