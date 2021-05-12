package com.example.examplemod.common.block;

import com.example.examplemod.common.init.TileEntityTypesInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class ChestBlock extends Block {

    public ChestBlock() {
        super(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).harvestLevel(1).harvestTool(ToolType.AXE).sound(SoundType.WOOD));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityTypesInit.CHEST_TILE_ENTITY_TYPE.get().create();
    }
}
