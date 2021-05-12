package com.example.examplemod.common.block;

import com.example.examplemod.common.init.TileEntityTypesInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class ChestBlock extends Block {

    private TileEntity tileEntity;

    public ChestBlock() {
        super(AbstractBlock.Properties.create(Material.WOOD).harvestLevel(1).harvestTool(ToolType.AXE).sound(SoundType.WOOD));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        this.tileEntity = TileEntityTypesInit.CHEST_TILE_ENTITY_TYPE.get().create();
        return tileEntity;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        System.out.println(tileEntity.getPos());
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
