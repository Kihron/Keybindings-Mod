package com.kihron.keymod.client.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kihron.keymod.client.Reference;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Config {

    public static final String CATEGORY_NAME_VERSION_CHECKER = "version_checker";

    /*
     * Update Checkers Config
     */
    /**
     * Whether or not a specific mod's update checker is disabled (the key is
     * the mod's modid)
     */
    public static Map<String, Boolean> UPDATE_CHECKER_MODS = new HashMap<>();

    public static List<Pair<String, Property>> UPDATE_CHECKER_MOD_PROPERTIES = new ArrayList<>();

    private static Configuration config = null;

    public static void preInit() {
        File configFile = new File(Loader.instance().getConfigDir(), "Electritech.cfg");
        config = new Configuration(configFile);
        syncFromFile();
    }

    public static void clientPreInit() {
        MinecraftForge.EVENT_BUS.register(new ConfigEventHandler());
    }

    public static Configuration getConfig() {
        return config;
    }

    public static void syncFromFile() {
        syncConfig(true, true);
    }

    public static void syncFromGUI() {
        syncConfig(false, true);
    }

    /**
     * Sync from changes in the fields
     */
    public static void syncFromFields() {
        syncConfig(false, false);
    }

    /**
     * Actually sync the config
     *
     * @param loadConfigFromFile
     *            Load the config from the file?
     * @param readFieldsFromConfig
     *            Read the fields from the config?
     */
    private static void syncConfig(boolean loadConfigFromFile, boolean readFieldsFromConfig) {
        if (loadConfigFromFile)
            config.load();

        /*
         * Update Checkers Config
         */
        List<String> propertyOrderUpdateChecker = new ArrayList<String>();
        Iterator<String> mods = UPDATE_CHECKER_MODS.keySet().iterator();
        while (mods.hasNext()) {
            String modid = mods.next();
            Property propertyUpdateCheckerEnabled = config.get(CATEGORY_NAME_VERSION_CHECKER, modid, true);
            propertyUpdateCheckerEnabled.setComment("Whether the update checker for " + modid + " is enabled");
            propertyUpdateCheckerEnabled.setLanguageKey("gui.config.update_checker.enabled.name");
            UPDATE_CHECKER_MOD_PROPERTIES.add(Pair.of(modid, propertyUpdateCheckerEnabled));
            propertyOrderUpdateChecker.add(propertyUpdateCheckerEnabled.getName());
        }
        config.setCategoryPropertyOrder(CATEGORY_NAME_VERSION_CHECKER, propertyOrderUpdateChecker);

        if (readFieldsFromConfig) {
            /*
             * Update Checkers Config
             */
            for (Pair<String, Property> mod : UPDATE_CHECKER_MOD_PROPERTIES) {
                UPDATE_CHECKER_MODS.remove(mod.getLeft());
                UPDATE_CHECKER_MODS.put(mod.getLeft(), mod.getRight().getBoolean());
            }
        }

        /*
         * Update Checkers Config
         */
        for (Pair<String, Property> mod : UPDATE_CHECKER_MOD_PROPERTIES) {
            mod.getRight().set(UPDATE_CHECKER_MODS.get(mod.getLeft()));
        }

        if (config.hasChanged())
            config.save();
    }

    public static class ConfigEventHandler {

        @SubscribeEvent(priority = EventPriority.NORMAL)
        public void onEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (Reference.MOD_ID.equalsIgnoreCase(event.getModID())) {
                syncFromGUI();
            }
        }

    }

}