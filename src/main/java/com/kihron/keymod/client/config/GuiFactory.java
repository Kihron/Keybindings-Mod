package com.kihron.keymod.client.config;

import com.kihron.keymod.client.Reference;
import com.kihron.keymod.client.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GuiFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {
    }

    /**
     * No runtime gui categories
     */
    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    public static class EConfigGui extends GuiConfig {

        public EConfigGui(GuiScreen parentScreen) {
            super(parentScreen, getConfigElements(), Reference.MOD_ID, false, false, I18n.format("gui.config.main_title"));
        }

        private static List<IConfigElement> getConfigElements() {
            List<IConfigElement> list = new ArrayList<IConfigElement>();
            list.add(new DummyConfigElement.DummyCategoryElement(I18n.format("gui.config.category.update_checker"),
                    "gui.config.category.update_checker", CategoryEntryVersionChecker.class));
            return list;
        }

        public static class CategoryEntryVersionChecker extends GuiConfigEntries.CategoryEntry {

            public CategoryEntryVersionChecker(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
                                               IConfigElement configElement) {
                super(owningScreen, owningEntryList, configElement);
            }

            @Override
            protected GuiScreen buildChildScreen() {
                Configuration config = Config.getConfig();
                ConfigElement category_version_checker = new ConfigElement(
                        config.getCategory(Config.CATEGORY_NAME_VERSION_CHECKER));
                List<IConfigElement> propertiesOnThisScreen = category_version_checker.getChildElements();
                String windowTitle = I18n.format("gui.config.category.update_checker");
                return new GuiConfig(this.owningScreen, propertiesOnThisScreen, this.owningScreen.modID,
                        Config.CATEGORY_NAME_VERSION_CHECKER,
                        this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart,
                        this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart, windowTitle);
            }

        }

    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new EConfigGui(parentScreen);
    }

}