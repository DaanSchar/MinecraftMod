package com.example.examplemod.common.item;

import com.example.examplemod.common.init.Registry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

public class ModItem {

    public static final RegistryObject<Item> SILVER_INGOT = Registry.ITEMS.register("silver_ingot", () ->
            new Item(new Item.Properties().group(ItemGroup.MATERIALS)));

    public static void register(){}

}
