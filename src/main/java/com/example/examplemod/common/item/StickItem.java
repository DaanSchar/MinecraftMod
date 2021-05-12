package com.example.examplemod.common.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StickItem extends Item{

    public StickItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (!playerIn.isOnGround())
            jump(playerIn);

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public void  jump(PlayerEntity playerIn) {
        playerIn.jumpMovementFactor = 0.1f;
        playerIn.jump();
        playerIn.getCooldownTracker().setCooldown(this, 50);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        entity.setFire(10);
        return super.onLeftClickEntity(stack, player, entity);
    }

}
