package com.kihron.keymod.proxy;

import com.kihron.keymod.client.config.Config;
import com.kihron.keymod.client.settings.Keybindings;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    public void registerKeyBindings() {

    }

    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        Config.clientPreInit();
        ClientRegistry.registerKeyBinding(Keybindings.gp);
        ClientRegistry.registerKeyBinding(Keybindings.pd);
        ClientRegistry.registerKeyBinding(Keybindings.a1);
        ClientRegistry.registerKeyBinding(Keybindings.a2);
        ClientRegistry.registerKeyBinding(Keybindings.a3);
        ClientRegistry.registerKeyBinding(Keybindings.a4);
        ClientRegistry.registerKeyBinding(Keybindings.a5);
        ClientRegistry.registerKeyBinding(Keybindings.a6);
        ClientRegistry.registerKeyBinding(Keybindings.a7);
        ClientRegistry.registerKeyBinding(Keybindings.a8);
        ClientRegistry.registerKeyBinding(Keybindings.a9);
        ClientRegistry.registerKeyBinding(Keybindings.ra);
        ClientRegistry.registerKeyBinding(Keybindings.la);
        ClientRegistry.registerKeyBinding(Keybindings.sc);
        ClientRegistry.registerKeyBinding(Keybindings.rp);
        ClientRegistry.registerKeyBinding(Keybindings.hb);
        ClientRegistry.registerKeyBinding(Keybindings.pw);
        ClientRegistry.registerKeyBinding(Keybindings.lg);
        ClientRegistry.registerKeyBinding(Keybindings.lock);
    }

    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }

    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }
}
