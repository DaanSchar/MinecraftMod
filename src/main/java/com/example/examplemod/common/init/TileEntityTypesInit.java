package com.example.examplemod.common.init;

import com.example.examplemod.ThisMod;
import com.example.examplemod.common.block.ModBlock;
import com.example.examplemod.common.block.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypesInit {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ThisMod.MOD_ID);

    public static final RegistryObject<TileEntityType<ChestTileEntity>> CHEST_TILE_ENTITY_TYPE = TILE_ENTITY_TYPE.register("chest", () -> TileEntityType.Builder.of(ChestTileEntity::new, ModBlock.CHEST.get()).build(null));

}
