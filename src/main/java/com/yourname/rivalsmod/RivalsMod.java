package com.yourname.rivalsmod;

import com.yourname.rivalsmod.events.KatanaFixes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = RivalsMod.MODID, name = RivalsMod.NAME, version = RivalsMod.VERSION)
public class RivalsMod {

    public static final String MODID = "rivalsmod";
    public static final String NAME = "Roblox Rivals Mod";
    public static final String VERSION = "1.0";

    @Mod.Instance
    public static RivalsMod instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Nothing needed here currently
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new KatanaFixes());
    }
}
