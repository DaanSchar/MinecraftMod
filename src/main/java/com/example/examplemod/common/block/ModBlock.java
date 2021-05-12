package com.example.examplemod.common.block;

import com.example.examplemod.common.init.Registry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModBlock {

    public static final RegistryObject<Block> SILVER_ORE = register("silver_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).harvestLevel(2).sound(SoundType.ANCIENT_DEBRIS)));
    public static final RegistryObject<Block> CHEST = register("chest", () -> new ChestBlock());
    public static final RegistryObject<Block> DISPLAY_CASE = register("display_case", () -> new DisplayCaseBlock());

    /**
     * registers a block when no item of that block is required.
     */
    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Registry.BLOCKS.register(name, block);
    }

    /**
     * registers a block and also and item for that block.
     */
    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        Registry.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
        return ret;
    }


    public static void register() {}
}
