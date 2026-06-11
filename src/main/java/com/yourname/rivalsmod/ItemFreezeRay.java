package com.yourname.rivalsmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemFreezeRay extends Item {

    public ItemFreezeRay() {

        setUnlocalizedName("freeze_ray");
        setRegistryName("rivalsmod", "freeze_ray");
        setCreativeTab(CreativeTabs.COMBAT);
        setMaxStackSize(1);
        setMaxDamage(200);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(
            World world,
            EntityPlayer player,
            EnumHand hand) {

        ItemStack stack = player.getHeldItem(hand);

        if (!world.isRemote) {

            player.sendMessage(
                    new TextComponentString(
                            "Freeze Ray Activated!"));

            world.playSound(
                    null,
                    player.posX,
                    player.posY,
                    player.posZ,
                    SoundEvents.BLOCK_GLASS_PLACE,
                    SoundCategory.PLAYERS,
                    1.0F,
                    0.8F);

            stack.damageItem(1, player);

            player.getCooldownTracker()
                    .setCooldown(this, 20);
        }

        return new ActionResult<>(
                EnumActionResult.SUCCESS,
                stack);
    }
}