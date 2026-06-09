package com.yourname.rivalsmod.init;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ModWeapons {

    // =========================
    // BULLET ITEM
    // =========================
    public static final Item BULLET = new ItemBullet();

    public static class ItemBullet extends Item {

        public ItemBullet() {

    setUnlocalizedName("bullet");
    setRegistryName("bullet");
    setCreativeTab(net.minecraft.creativetab.CreativeTabs.MATERIALS);
    setMaxStackSize(64);
}

    // =========================
    // ASSAULT RIFLE
    // =========================
    public static final Item ASSAULT_RIFLE = new ItemAssaultRifle();

    public static class ItemAssaultRifle extends Item {

        private static final int MAG_SIZE = 30;

        public ItemAssaultRifle() {

    setUnlocalizedName("assault_rifle");
    setRegistryName("assault_rifle");
    setCreativeTab(net.minecraft.creativetab.CreativeTabs.COMBAT);
    setMaxStackSize(1);
    setMaxDamage(200);
}

        private int getAmmo(ItemStack stack) {
            if (!stack.hasTagCompound()) {
                stack.setTagCompound(new NBTTagCompound());
                stack.getTagCompound().setInteger("Ammo", MAG_SIZE);
            }
            return stack.getTagCompound().getInteger("Ammo");
        }

        private void setAmmo(ItemStack stack, int ammo) {
            if (!stack.hasTagCompound()) {
                stack.setTagCompound(new NBTTagCompound());
            }
            stack.getTagCompound().setInteger("Ammo", ammo);
        }

        private boolean consumeBullet(EntityPlayer player) {

            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {

                ItemStack stack = player.inventory.getStackInSlot(i);

                if (!stack.isEmpty() && stack.getItem() == BULLET) {

                    stack.shrink(1);

                    if (stack.isEmpty()) {
                        player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                    }

                    return true;
                }
            }

            return false;
        }

        @Override
        public ActionResult<ItemStack> onItemRightClick(
                World world,
                EntityPlayer player,
                EnumHand hand) {

            ItemStack stack = player.getHeldItem(hand);
            int ammo = getAmmo(stack);

            if (!world.isRemote) {

                if (ammo > 0 && consumeBullet(player)) {

                    Vec3d look = player.getLookVec();

                    EntityTippedArrow bullet =
                            new EntityTippedArrow(world, player);

                    bullet.shoot(
                            look.x,
                            look.y,
                            look.z,
                            3.0F,
                            0.5F);

                    bullet.setDamage(12.0D);

                    world.spawnEntity(bullet);

                    world.playSound(
                            null,
                            player.posX,
                            player.posY,
                            player.posZ,
                            SoundEvents.ENTITY_GENERIC_EXPLODE,
                            SoundCategory.PLAYERS,
                            0.5F,
                            2.0F);

                    setAmmo(stack, ammo - 1);

                    stack.damageItem(1, player);

                    player.getCooldownTracker()
                            .setCooldown(this, 3);

                } else {

                    player.sendMessage(
                            new TextComponentString(
                                    "Out of ammo!"));

                }
            }

            return new ActionResult<>(
                    EnumActionResult.SUCCESS,
                    stack);
        }
    }

    // =========================
    // EXOGUN
    // =========================
    public static final Item EXOGUN = new ItemExogun();

    public static class ItemExogun extends Item {

        private static final float BLAST_RADIUS = 3.0F;

        public ItemExogun() {

    setUnlocalizedName("exogun");
    setRegistryName("exogun");
    setCreativeTab(net.minecraft.creativetab.CreativeTabs.COMBAT);
    setMaxStackSize(1);
    setMaxDamage(150);
}

        @Override
        public ActionResult<ItemStack> onItemRightClick(
                World world,
                EntityPlayer player,
                EnumHand hand) {

            ItemStack stack = player.getHeldItem(hand);

            if (!world.isRemote) {

                Vec3d look = player.getLookVec();

                double x = player.posX + look.x * 3;
                double y = player.posY + player.getEyeHeight() + look.y * 3;
                double z = player.posZ + look.z * 3;

                world.newExplosion(
                        player,
                        x,
                        y,
                        z,
                        BLAST_RADIUS,
                        false,
                        false);

                world.playSound(
                        null,
                        player.posX,
                        player.posY,
                        player.posZ,
                        SoundEvents.BLOCK_ANVIL_PLACE,
                        SoundCategory.PLAYERS,
                        0.6F,
                        1.5F);

                stack.damageItem(1, player);

                player.getCooldownTracker()
                        .setCooldown(this, 15);
            }

            return new ActionResult<>(
                    EnumActionResult.SUCCESS,
                    stack);
        }
    }

    // =========================
    // RIVALS KATANA
    // =========================
    public static final Item KATANA = new ItemRivalsKatana();

    public static class ItemRivalsKatana extends ItemSword {

        public ItemRivalsKatana() {

    super(ToolMaterial.DIAMOND);

    setUnlocalizedName("rivals_katana");
    setRegistryName("rivals_katana");
    setCreativeTab(net.minecraft.creativetab.CreativeTabs.COMBAT);
    setMaxDamage(500);
}

        @Override
        public ActionResult<ItemStack> onItemRightClick(
                World world,
                EntityPlayer player,
                EnumHand hand) {

            if (!world.isRemote) {

                player.sendMessage(
                        new TextComponentString(
                                "Katana Parry Ready!"));
            }

            player.getCooldownTracker()
                    .setCooldown(this, 80);

            return new ActionResult<>(
                    EnumActionResult.SUCCESS,
                    player.getHeldItem(hand));
        }

        @Override
        public boolean hitEntity(
                ItemStack stack,
                EntityLivingBase target,
                EntityLivingBase attacker) {

            if (attacker instanceof EntityPlayer) {

                target.attackEntityFrom(
                        net.minecraft.util.DamageSource.causePlayerDamage(
                                (EntityPlayer) attacker),
                        45.0F);

                Vec3d look = attacker.getLookVec();

                target.addVelocity(
                        look.x * 1.5,
                        0.4,
                        look.z * 1.5);
            }

            stack.damageItem(1, attacker);

            return true;
        }
    }
    }
