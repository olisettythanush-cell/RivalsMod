package com.yourname.rivalsmod.util;

import com.yourname.rivalsmod.init.ModWeapons;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

@Mod.EventBusSubscriber(modid = "rivalsmod")
public class RegistryHandler {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {

        event.getRegistry().registerAll(
                ModWeapons.BULLET,
                ModWeapons.ASSAULT_RIFLE,
                ModWeapons.EXOGUN,
                ModWeapons.KATANA,
                ModWeapons.FREEZE_RAY
        );
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegister(ModelRegistryEvent event) {

        registerModel(ModWeapons.BULLET);
        registerModel(ModWeapons.ASSAULT_RIFLE);
        registerModel(ModWeapons.EXOGUN);
        registerModel(ModWeapons.KATANA);
        registerModel(ModWeapons.FREEZE_RAY);
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
