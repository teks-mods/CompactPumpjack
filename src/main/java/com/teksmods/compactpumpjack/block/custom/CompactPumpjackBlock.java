package com.teksmods.compactpumpjack.block.custom;

import com.teksmods.compactpumpjack.inventory.container.CompactPumpjackContainer;
import com.teksmods.compactpumpjack.tileentity.CompactPumpjackTile;
import com.teksmods.compactpumpjack.tileentity.ModTileEntities;
import flaxbeard.immersivepetroleum.api.crafting.pumpjack.ReservoirWorldInfo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.network.NetworkHooks;
import blusunrize.immersiveengineering.api.DimensionChunkCoords;

import javax.annotation.Nullable;

import java.lang.reflect.Array;

import static flaxbeard.immersivepetroleum.api.crafting.pumpjack.PumpjackHandler.*;


public class CompactPumpjackBlock extends Block {

    public static Fluid getFluid(World worldIn, int chunkX, int chunkZ){
        if(!worldIn.isRemote)
            return null;

        ReservoirWorldInfo info = getOrCreateOilWorldInfo(worldIn, chunkX, chunkZ);

        if(info == null || info.getType() == null){
            return null;
        }else{
            return info.getType().getFluid();
        }
    }



    public CompactPumpjackBlock(Properties properties) {
        super(properties);
    }

    @Override
        public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);

            if(!player.isCrouching()) {
                if(tileEntity instanceof CompactPumpjackTile) {
                    INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);

                    NetworkHooks.openGui(((ServerPlayerEntity) player), containerProvider, tileEntity.getPos());
                } else {
                    throw new IllegalStateException("Container Provider is Missing");
                }

            } else {
                if(tileEntity instanceof CompactPumpjackTile) {
                    ChunkPos here = new ChunkPos(pos);
                    int imhereX = here.x;
                    int imhereZ = here.z;
                    String imhereFluid = (here.x + "@@@" + here.z);
                    //String brokeit = new ChunkPos(pos).getClass().getSimpleName();
                    depleteFluid(worldIn, here.x, here.z, 1000);

                    ITextComponent msg = ITextComponent.getTextComponentOrEmpty(imhereFluid);
                    Minecraft.getInstance().player.sendMessage(msg, Util.DUMMY_UUID);
                    String reservoirLeftOver = String.valueOf(getFluidAmount(worldIn, here.x, here.z));
                    msg = ITextComponent.getTextComponentOrEmpty("The reservoir has " + reservoirLeftOver + ".");
                    Minecraft.getInstance().player.sendMessage(msg, Util.DUMMY_UUID);
                    String SCap = String.valueOf(CompactPumpjackTile.capacity);
                    msg = ITextComponent.getTextComponentOrEmpty("I have a capacity of: " + SCap + ".");
                    Minecraft.getInstance().player.sendMessage(msg, Util.DUMMY_UUID);

                    if(worldIn.isThundering()) {
                        EntityType.LIGHTNING_BOLT.spawn(((ServerWorld) worldIn), null, player,
                                pos, SpawnReason.TRIGGERED, true, true);
                        ((CompactPumpjackTile)tileEntity).lightningHasStruck();
                    }
                }
            }

        }
        return ActionResultType.SUCCESS;
    }

    private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
        return new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.compactpumpjack.compact_pumpjack");
            }

            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                return new CompactPumpjackContainer(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.COMPACT_PUMPJACK_TILE.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
