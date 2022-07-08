package com.teksmods.compactpumpjack.tileentity;

import com.teksmods.compactpumpjack.CompactPumpjack;
import com.teksmods.compactpumpjack.block.ModBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, CompactPumpjack.MOD_ID);

    public static RegistryObject<TileEntityType<CompactPumpjackTile>> COMPACT_PUMPJACK_TILE =
            TILE_ENTITIES.register("compact_pumpjack_tile", () ->
                    TileEntityType.Builder.create(
                            CompactPumpjackTile::new, ModBlocks.COMPACT_PUMPJACK.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}
