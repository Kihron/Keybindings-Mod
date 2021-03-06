package com.kihron.keymod.client.settings;

import com.kihron.keymod.client.Names.Keys;
import com.kihron.keymod.client.handler.BetterKeyBinding;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class Keybindings
{
    //Guild
    public static BetterKeyBinding gp = new BetterKeyBinding(Keys.GP, Keyboard.KEY_LBRACKET, Keys.GUILD);
    public static BetterKeyBinding pd = new BetterKeyBinding(Keys.PD, Keyboard.KEY_RBRACKET, Keys.GUILD);
    public static BetterKeyBinding pw = new BetterKeyBinding(Keys.PW, Keyboard.KEY_P, Keys.GUILD);

    //Hub
    public static BetterKeyBinding hb = new BetterKeyBinding(Keys.HB, Keyboard.KEY_H, Keys.ALL);

    //Cycle
    public static BetterKeyBinding la = new BetterKeyBinding(Keys.LA, Keyboard.KEY_LEFT, Keys.ALL);
    public static BetterKeyBinding ra = new BetterKeyBinding(Keys.RA, Keyboard.KEY_RIGHT, Keys.ALL);

    //Replay
    public static BetterKeyBinding rp = new BetterKeyBinding(Keys.RP, Keyboard.KEY_NUMPAD0, Keys.ALL);

    //Select Current
    public static BetterKeyBinding sc = new BetterKeyBinding(Keys.SC, Keyboard.KEY_UP, Keys.ALL);

    //List Games
    public static BetterKeyBinding lg = new BetterKeyBinding(Keys.LG, Keyboard.KEY_DOWN, Keys.ALL);

    public static BetterKeyBinding lock = new BetterKeyBinding(Keys.LOCK, Keyboard.KEY_DECIMAL, Keys.ALL);

    //All Games
    public static BetterKeyBinding a1 = new BetterKeyBinding(Keys.A1 + ".default", Keyboard.KEY_NUMPAD1, Keys.ALL);
    public static BetterKeyBinding a2 = new BetterKeyBinding(Keys.A2 + ".default", Keyboard.KEY_NUMPAD2, Keys.ALL);
    public static BetterKeyBinding a3 = new BetterKeyBinding(Keys.A3 + ".default", Keyboard.KEY_NUMPAD3, Keys.ALL);
    public static BetterKeyBinding a4 = new BetterKeyBinding(Keys.A4 + ".default", Keyboard.KEY_NUMPAD4, Keys.ALL);
    public static BetterKeyBinding a5 = new BetterKeyBinding(Keys.A5 + ".default", Keyboard.KEY_NUMPAD5, Keys.ALL);
    public static BetterKeyBinding a6 = new BetterKeyBinding(Keys.A6 + ".default", Keyboard.KEY_NUMPAD6, Keys.ALL);
    public static BetterKeyBinding a7 = new BetterKeyBinding(Keys.A7 + ".default", Keyboard.KEY_NUMPAD7, Keys.ALL);
    public static BetterKeyBinding a8 = new BetterKeyBinding(Keys.A8 + ".default", Keyboard.KEY_NUMPAD8, Keys.ALL);
    public static BetterKeyBinding a9 = new BetterKeyBinding(Keys.A9 + ".default", Keyboard.KEY_NUMPAD9, Keys.ALL);
}
