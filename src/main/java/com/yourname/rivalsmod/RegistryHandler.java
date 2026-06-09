package com.yourname.rivalsmod.util;

import com.yourname.rivalsmod.init.ModWeapons;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class RegistryHandler {

    // =========================
    // ITEM REGISTRATION
    // =========================
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {

        event.getRegistry().registerAll(
                ModWeapons.ASSAULT_RIFLE,
                ModWeapons.EXOGUN,
                ModWeapons.KATANA,
                ModWeapons.BULLET
        );
    }

    // =========================
    // MODEL REGISTRATION
    // =========================
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegister(ModelRegistryEvent event) {

        registerModel(ModWeapons.ASSAULT_RIFLE);
        registerModel(ModWeapons.EXOGUN);
        registerModel(ModWeapons.KATANA);
        registerModel(ModWeapons.BULLET);
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(Item item) {

        ModelLoader.setCustomModelResourceLocation(
                item,
                0,
                new ModelResourceLocation(item.getRegistryName(), "inventory")
        );
    }
}