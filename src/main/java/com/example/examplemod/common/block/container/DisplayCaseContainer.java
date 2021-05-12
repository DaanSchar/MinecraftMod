package com.example.examplemod.common.block.container;

import com.example.examplemod.common.block.ModBlock;
import com.example.examplemod.common.block.tileentity.DisplayCaseTileEntity;
import com.example.examplemod.common.init.ContainerTypesInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

import java.util.Objects;


public class DisplayCaseContainer extends Container {

    public final DisplayCaseTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;

    public DisplayCaseContainer(final int windowId, final PlayerInventory playerInventory, final DisplayCaseTileEntity tileEntity) {
        super(ContainerTypesInit.DISPLAY_CASE_CONTAINER_TYPE.get(), windowId);
        this.tileEntity = tileEntity;
        this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        // Tile Entity
        //this.addSlot(new Slot( tileEntity, 0, 80, 35));
        for (int col = 0; col < 5; col++) {
            this.addSlot(new Slot(tileEntity, col, 8 + col * 18, 35));
        }

        // Main Player Inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 166 - (4 - row) * 18 - 10));
            }
        }

        // Player Hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    public DisplayCaseContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory,data));
    }

    private static DisplayCaseTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "Player inventory cannot be null.");
        Objects.requireNonNull(data, "Packet buffer cannot be null.");
        final TileEntity te = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (te instanceof DisplayCaseTileEntity) {
            return (DisplayCaseTileEntity) te;
        }
        throw new IllegalStateException("Tile Entity is not correct");
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, ModBlock.DISPLAY_CASE.get());
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();
            if (index < DisplayCaseTileEntity.slots && !this.mergeItemStack(stack1, DisplayCaseTileEntity.slots, this.inventorySlots.size(), true)) {
                return ItemStack.EMPTY;
            }
            if (!this.mergeItemStack(stack1, 0, DisplayCaseTileEntity.slots, false)) {
                return ItemStack.EMPTY;
            }
            if (stack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return stack;
    }
}
