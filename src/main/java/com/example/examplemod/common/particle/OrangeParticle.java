package com.example.examplemod.common.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class OrangeParticle extends SpriteTexturedParticle {

    protected OrangeParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);

        float f = this.rand.nextFloat() * 1.0f;
        this.particleRed = f;
        this.particleGreen = f;
        this.particleBlue = f;

        this.setSize(0.02f, 0.02f);
        this.particleScale *= this.rand.nextFloat() * 1.1f;
        this.motionX *= 0.02f;
        this.motionY *= 0.02f;
        this.motionZ *= 0.02f;
        this.setMaxAge((int)(20.0d / (Math.random() * 1.0d)));

    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.maxAge <= 0) {
            this.setExpired();
        } else {
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 1.0d;
            this.motionY *= 1.0d;
            this.motionZ *= 1.0d;

        }
    }
    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite sprite;

        public Factory(IAnimatedSprite sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            OrangeParticle orangeParticle = new OrangeParticle(worldIn,x,y,z,xSpeed,ySpeed,zSpeed);
            orangeParticle.setColor(1.0f, 1.0f, 1.0f);
            orangeParticle.selectSpriteRandomly(this.sprite);
            return orangeParticle;
        }
    }
}
