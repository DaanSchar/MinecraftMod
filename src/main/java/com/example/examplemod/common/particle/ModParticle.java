package com.example.examplemod.common.particle;

import com.example.examplemod.common.init.Registry;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.fml.RegistryObject;

public class ModParticle {

    public static final RegistryObject<BasicParticleType> ORANGE_PARTICLE = Registry.PARTICLES.register("orange_particle", () -> new BasicParticleType(true));

    public static void register(){}

}
