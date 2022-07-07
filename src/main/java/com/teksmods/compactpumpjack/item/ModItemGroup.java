package com.teksmods.compactpumpjack.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {

    public static final ItemGroup CPJ_GROUP = new ItemGroup("CPJ: Compact Pumpjack") {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModItems.CPJCREATIVETAB.get());
        }
    };
}
