package com.kihron.keymod.client.handler;

import net.minecraft.client.settings.KeyBinding;

public class BetterKeyBinding extends KeyBinding {

    private String desc;

    public BetterKeyBinding(String name, int keyCode, String category) {
        super(name, keyCode, category);
        this.desc = name;
    }

    @Override
    public String getKeyDescription() {
        return desc;
    }

    public void setKeyDescription(String desc) {
        this.desc = desc;
    }
}
