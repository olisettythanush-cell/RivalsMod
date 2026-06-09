package com.yourname.rivalsmod.events;

import com.yourname.rivalsmod.init.ModWeapons;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class KatanaFixes {

    private static final int PARRY_WINDOW_TICKS = 10; // short timing window feel

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {

        EntityLivingBase target = event.getEntityLiving();
        DamageSource source = event.getSource();
        Entity attackerEntity = source.getImmediateSource();

        if (!(target instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer) target;
        World world = player.world;

        // Must hold katana
        if (player.getHeldItemMainhand().getItem() != ModWeapons.KATANA) return;

        // Only handle projectiles we want (arrows only)
        if (!(attackerEntity instanceof EntityArrow)) return;

        EntityArrow arrow = (EntityArrow) attackerEntity;

        // Cancel incoming damage
        event.setCanceled(true);

        // Reverse direction (reflection)
        arrow.motionX *= -1.2;
        arrow.motionY *= -1.0;
        arrow.motionZ *= -1.2;

        arrow.velocityChanged = true;
        arrow.shootingEntity = player;

        // Small damage boost on reflection
        arrow.setDamage(arrow.getDamage() * 1.5);

        // Visual + sound feedback (parry feel)
        if (!world.isRemote) {

            world.spawnParticle(
                    EnumParticleTypes.CRIT,
                    player.posX,
                    player.posY + 1.0,
                    player.posZ,
                    0.2, 0.2, 0.2
            );

            world.playSound(
                    null,
                    player.posX,
                    player.posY,
                    player.posZ,
                    net.minecraft.init.SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP,
                    net.minecraft.util.SoundCategory.PLAYERS,
                    0.8F,
                    1.2F
            );
        }
    }
}
