package com.Equatorial.toukenranbu.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BreezePetalParticle extends TextureSheetParticle {

    // 全局风的方向（x, z 分量，单位是每 tick 的加速度）
    // 倾斜着吹，像右下角吹向左上角，或者反过来，自己改
    private static final double WIND_X = 0.013;
    private static final double WIND_Z = 0.005;

    protected BreezePetalParticle(ClientLevel level, double x, double y, double z,
                                  double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        if (sprites == null) { this.remove(); return; }
        this.setSprite(sprites.get(level.random));
        this.lifetime = 200 + level.random.nextInt(100); // 存活时间拉长，飞得远
        this.gravity = 0.0F;
        this.quadSize = 0.1F;
        this.friction = 0.99F;
        this.hasPhysics = false;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
            return;
        }

        // 1. 持续受到横向风力影响，斜着飞
        this.xd += WIND_X;
        this.zd += WIND_Z;

        // 2. 缓慢下落（重力极小，飘很久）
        this.yd -= 0.004;

        // 3. 空气阻力，让速度稳定下来
        this.xd *= 0.99;
        this.yd *= 0.99;
        this.zd *= 0.99;

        // 4. 旋转
        this.oRoll = this.roll;
        this.roll += 0.08F;

        this.move(this.xd, this.yd, this.zd);

        // 碰地就消失，避免在地上堆积
        if (this.onGround) {
            this.remove();
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public Provider(SpriteSet sprites) { this.sprites = sprites; }
        @Override
        public net.minecraft.client.particle.Particle createParticle(SimpleParticleType type, ClientLevel level,
                                                                     double x, double y, double z,
                                                                     double xSpeed, double ySpeed, double zSpeed) {
            return new BreezePetalParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }
}