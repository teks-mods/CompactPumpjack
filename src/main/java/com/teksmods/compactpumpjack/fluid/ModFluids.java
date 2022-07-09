package com.teksmods.compactpumpjack.fluid;

import com.teksmods.compactpumpjack.CompactPumpjack;
import com.teksmods.compactpumpjack.block.ModBlocks;
import com.teksmods.compactpumpjack.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFluids {
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");

    public static final DeferredRegister<Fluid> FLUIDS
            = DeferredRegister.create(ForgeRegistries.FLUIDS, CompactPumpjack.MOD_ID);

    public static final RegistryObject<FlowingFluid> EXPLOSIVEWATER_FLUID
            = FLUIDS.register("ph2o_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.PH2O_PROPERTIES));

    public static final RegistryObject<FlowingFluid> EXPLOSIVEWATER_FLOWING
            = FLUIDS.register("ph2o_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.PH2O_PROPERTIES));

    public static final ForgeFlowingFluid.Properties PH2O_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> EXPLOSIVEWATER_FLUID.get(), () -> EXPLOSIVEWATER_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
            .color(0x88ff3399) //A, R, G, B
            .luminosity(15)
            .rarity(Rarity.EPIC)
            .viscosity(5)
            .temperature(99999999)
            .overlay(WATER_OVERLAY_RL)
            .density(9).sound(SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE)).slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(() -> ModFluids.PH2O_BLOCK.get()).bucket(() -> ModItems.PH2O_BUCKET.get());

    public static final RegistryObject<FlowingFluidBlock> PH2O_BLOCK = ModBlocks.BLOCKS.register("ph2o",
            () -> new FlowingFluidBlock(() -> ModFluids.EXPLOSIVEWATER_FLUID.get(), AbstractBlock.Properties.create(Material.WATER)
                    .doesNotBlockMovement()
                    .hardnessAndResistance(100f)
                    .noDrops()));

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
