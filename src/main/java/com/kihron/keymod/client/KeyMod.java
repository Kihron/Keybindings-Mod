package com.kihron.keymod.client;

import com.kihron.keymod.client.config.Config;
import com.kihron.keymod.client.handler.KeyEventHandler;
import com.kihron.keymod.client.handler.KeyInputEventHandler;
import com.kihron.keymod.proxy.CommonProxy;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME , version = Reference.MOD_VERSION, guiFactory = Reference.GUI_FACTORY, acceptedMinecraftVersions = Reference.ACCEPTED_MC_VERSIONS, customProperties = {
@Mod.CustomProperty(k = "useVersionChecker", v = "true") }, useMetadata = true)

public class KeyMod {

    public static final List<String> DEPENDANTS = new ArrayList<String>();
    public static final Logger logger = LogManager.getFormatterLogger(Reference.MOD_NAME);

    public static void updateDependants() {
        Config.UPDATE_CHECKER_MODS.put(Reference.MOD_ID, true);
        for (ModContainer mod : Loader.instance().getActiveModList()) {
            for (ArtifactVersion version : mod.getDependencies()) {
                if (version.getLabel().equals(Reference.MOD_ID)) {
                    if (!DEPENDANTS.contains(mod.getModId())) {
                        DEPENDANTS.add(mod.getModId());
                        if (mod.getCustomModProperties().containsKey("useVersionChecker")) {
                            if (Boolean.valueOf(mod.getCustomModProperties().get("useVersionChecker"))) {
                                if (!Config.UPDATE_CHECKER_MODS.containsKey(mod.getModId())) {
                                    Config.UPDATE_CHECKER_MODS.put(mod.getModId(), true);
                                }
                            }
                        } else {
                            logger.error("Mod " + mod.getModId() + " does not say whether it uses an version checker! Please fix this!");
                            FMLCommonHandler.instance().exitJava(0, false);
                        }
                    }
                }
            }
            for (ArtifactVersion version : mod.getRequirements()) {
                if (version.getLabel().equals(Reference.MOD_ID)) {
                    if (!DEPENDANTS.contains(mod.getModId())) {
                        DEPENDANTS.add(mod.getModId());
                        if (mod.getCustomModProperties().containsKey("useVersionChecker")) {
                            if (Boolean.valueOf(mod.getCustomModProperties().get("useVersionChecker"))) {
                                if (!Config.UPDATE_CHECKER_MODS.containsKey(mod.getModId())) {
                                    Config.UPDATE_CHECKER_MODS.put(mod.getModId(), true);
                                }
                            }
                        } else {
                            logger.error("Mod " + mod.getModId() + " does not say whether it uses a version checker! Please fix this!");
                            FMLCommonHandler.instance().exitJava(0, false);
                        }
                    }
                }
            }
        }
        DEPENDANTS.forEach(mod -> {
            KeyMod.logger.info("Found dependant: " + mod);
        });
        Config.UPDATE_CHECKER_MODS.forEach((key, value) -> {
            KeyMod.logger
                    .info("Mod " + key + " says it has a version checker!");
        });
    }

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
            updateDependants();
            proxy.preInit(event);
            Config.preInit();
        }

    @EventHandler
    public static void init(FMLInitializationEvent event)
    {
        proxy.init(event);
        FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());
        FMLCommonHandler.instance().bus().register(new KeyEventHandler());
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }
}