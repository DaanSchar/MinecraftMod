package com.example.examplemod.common.block.tileentity;

import com.example.examplemod.common.init.TileEntityTypesInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public class ChestTileEntity extends TileEntity implements ITickableTileEntity {

    public ChestTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public ChestTileEntity() {
        this(TileEntityTypesInit.CHEST_TILE_ENTITY_TYPE.get());
    }

    private BlockPos currentPos;
    private int tick = 0;
    private int i = 0;
    private int k = 0;
    private boolean reverse;
    private List<BlockState> blockBag = new LinkedList<>();
    private List<BlockPos> posBag = new LinkedList<>();

    @Override
    public void tick() {
        tick++;
        tick++;

        if (tick > 10) {
            tick = 0;

            if (k > 20)
                reverse = true;

            if (!reverse)
                removeBlockFromLayer();

            if (reverse && posBag.size() > 0) {
                placeBackBlock();
                System.out.println("placing back block");
            }

            if (posBag.isEmpty() && reverse) {
                reverse = false;
                currentPos = getBlockPos().below();
                i = 0;
                k = 0;
            }

            System.out.println(k);

        }
    }

    private void placeBackBlock() {
        placeBlock(posBag.remove(posBag.size()-1), blockBag.remove(blockBag.size()-1));
    }

    private void removeBlockFromLayer() {
        if (i == getPosLayer(currentPos).length) {
            i = 0;
            currentPos = currentPos.below();
        }

        BlockPos pos = getPosLayer(currentPos)[i];

        posBag.add(pos);
        blockBag.add(this.getLevel().getBlockState(pos).getBlock().defaultBlockState());

        removeBlock(pos);
        i++;
        k++;
    }

    private void removeBlock(BlockPos pos) {
            this.getLevel().setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
    }

    private void placeBlock(BlockPos pos, BlockState block) {
        this.getLevel().setBlock(pos, block, 0);
    }

    private BlockPos[] getPosLayer(BlockPos pos) {
        BlockPos[] posLayer = new BlockPos[9];

        posLayer[0] = pos.north().west();
        posLayer[1] = pos.north();
        posLayer[2] = pos.north().east();

        posLayer[3] = pos.west();
        posLayer[4] = pos;
        posLayer[5] = pos.east();

        posLayer[6] = pos.south().west();
        posLayer[7] = pos.south();
        posLayer[8] = pos.south().east();
        return posLayer;
    }


    @Override
    public void onLoad() {
        super.onLoad();
        currentPos = getBlockPos().below();
    }
}
