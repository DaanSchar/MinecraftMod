package com.example.examplemod.common.block.tileentity;

import com.example.examplemod.ThisMod;
import com.example.examplemod.common.block.container.DisplayCaseContainer;
import com.example.examplemod.common.init.TileEntityTypesInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class DisplayCaseTileEntity extends LockableLootTileEntity implements ITickableTileEntity {

    private static final Logger LOGGER = LogManager.getLogger();

    public static int slots = 5;

    private BlockPos currentPos;
    private int tick = 0;
    private int i = 0;
    private boolean reverse;
    private List<BlockState> blockBag = new LinkedList<>();
    private List<BlockPos> posBag = new LinkedList<>();

    @Override
    public void tick() {
        tick++;

        if(world.isRemote){
            if (tick > 10) {
                tick = 0;
                LOGGER.info("reverse = " + reverse);

                if (!reverse) {
                    LOGGER.info("removing blocks");
                    removeBlockFromLayer();
                }

                if (reverse && posBag.size() > 0) {
                    placeBackBlock();
                    LOGGER.info("placing back blocks \n");
                }

                if (posBag.isEmpty() && reverse) {
                    LOGGER.info("posbag is empty and reverse = " + reverse);
                    currentPos = pos.down();
                    i = 0;
                }
            }
        }
    }

    private void placeBackBlock() {
        placeBlock(posBag.remove(posBag.size()-1), blockBag.remove(blockBag.size()-1));
    }

    private void removeBlockFromLayer() {
        if (i == getPosLayer(currentPos).length) {
            i = 0;
            currentPos = currentPos.down();
        }

        BlockPos pos = getPosLayer(currentPos)[i];

        posBag.add(pos);
        blockBag.add(this.world.getBlockState(pos).getBlock().getDefaultState());

        removeBlock(pos);
        i++;
    }

    private void removeBlock(BlockPos pos) {
        insertIntoTileInventory(pos);
        this.world.setBlockState(pos, Blocks.AIR.getDefaultState(), 0);
    }

    private void placeBlock(BlockPos pos, BlockState block) {
        removeFromTileInventory(block.getBlock().asItem());
        this.world.setBlockState(pos, block, 0);
    }

    private void removeFromTileInventory(Item item) {
        ItemStack itemStack = new ItemStack(item);
        for (int i = 0; i < slots; i++) {
            ItemStack itemInSlot = getStackInSlot(i);
            if (itemStack.isItemEqual(itemInSlot)) {
                itemInSlot.setCount(itemInSlot.getCount() - 1);
            }
        }
    }

    private void insertIntoTileInventory(BlockPos pos) {
        ItemStack itemStack = new ItemStack(world.getBlockState(pos).getBlock().asItem());

        int index = 0;
        while (index < slots) {
            if (isItemValidForSlot(index, itemStack)) {
                ItemStack stack = getStackInSlot(index);
                itemStack.setCount(stack.getCount() + 1);
                setInventorySlotContents(index, itemStack);
                break;
            } else {
                index++;
            }
        }
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
        currentPos = pos.down();
    }

    public void setReverse(boolean bool) {
        NonNullList<ItemStack> list = NonNullList.withSize(slots, new ItemStack(Items.PUMPKIN));
        this.reverse = bool;
        setItems(list);
    }

    public boolean getReverse() {
        return reverse;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        ItemStack item = getStackInSlot(index);

        if (item.isEmpty())
            return true;
        if (item.isItemEqual(stack))
            return true;
        if (item.isItemEqual(new ItemStack(Items.AIR)))
            return true;

        return false;
    }


    // --------------- Container part ----------------

    protected NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);

    protected DisplayCaseTileEntity(TileEntityType<?> typeIn) {
        super(typeIn);
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + ThisMod.MOD_ID + ".display_case");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new DisplayCaseContainer(id, player, this);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.items = itemsIn;
    }

    public DisplayCaseTileEntity() {
        this(TileEntityTypesInit.DISPLAY_CASE_TILE_ENTITY_TYPE.get());
    }

    @Override
    public int getSizeInventory() {
        return slots;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);

        if(!this.checkLootAndWrite(compound))
            ItemStackHelper.saveAllItems(compound, items);

        return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.items = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
        if (this.checkLootAndRead(nbt)) {
            ItemStackHelper.loadAllItems(nbt, this.items);
        }
    }
}
