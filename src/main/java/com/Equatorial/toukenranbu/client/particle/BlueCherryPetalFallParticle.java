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
public class BlueCherryPetalFallParticle extends TextureSheetParticle {

    protected BlueCherryPetalFallParticle(ClientLevel level, double x, double y, double z,
                                          double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        if (sprites == null) { this.remove(); return; }
        this.setSprite(sprites.get(level.random));
        this.lifetime = 180 + level.random.nextInt(80);
        this.gravity = 0.0F;       // 不用基类的重力，我们自己在 tick 里控制
        this.quadSize = 0.12F;
        this.friction = 0.98F;
        this.hasPhysics = false;   // 花瓣不参与方块碰撞
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

        // 1. 让粒子持续向下加速
        this.yd -= 0.015;

        // 2. 随机横向风力（不要太大，否则会乱飘）
        if (this.random.nextInt(10) == 0) {
            this.xd += (this.random.nextDouble() - 0.5) * 0.008;
            this.zd += (this.random.nextDouble() - 0.5) * 0.008;
        }

        // 3. 空气阻力
        this.xd *= 0.96;
        this.yd *= 0.98;   // 垂直方向阻力小一点，保证一直往下
        this.zd *= 0.96;

        // 4. 旋转
        this.oRoll = this.roll;
        this.roll += 0.08F;

        // 5. 移动
        this.move(this.xd, this.yd, this.zd);

        // 6. 落地就消失
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
            return new BlueCherryPetalFallParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }
}