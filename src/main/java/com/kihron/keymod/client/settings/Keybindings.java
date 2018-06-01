package com.kihron.keymod.client.settings;

import com.kihron.keymod.client.Names;
import com.kihron.keymod.client.handler.BetterKeyBinding;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class Keybindings
{
    //Guild
    public static KeyBinding gp = new KeyBinding(Names.Keys.GP, Keyboard.KEY_LBRACKET, Names.Keys.GUILD);
    public static KeyBinding pd = new KeyBinding(Names.Keys.PD, Keyboard.KEY_RBRACKET, Names.Keys.GUILD);
    public static KeyBinding pw = new KeyBinding(Names.Keys.PW, Keyboard.KEY_P, Names.Keys.GUILD);

    //Hub
    public static KeyBinding hb = new KeyBinding(Names.Keys.HB, Keyboard.KEY_H, Names.Keys.ALL);

    //Cycle
    public static KeyBinding la = new KeyBinding(Names.Keys.LA, Keyboard.KEY_LEFT, Names.Keys.ALL);
    public static KeyBinding ra = new KeyBinding(Names.Keys.RA, Keyboard.KEY_RIGHT, Names.Keys.ALL);

    //Replay
    public static KeyBinding rp = new KeyBinding(Names.Keys.RP, Keyboard.KEY_R, Names.Keys.ALL);

    //Select Current
    public static KeyBinding sc = new KeyBinding(Names.Keys.SC, Keyboard.KEY_UP, Names.Keys.ALL);

    //List Games
    public static KeyBinding lg = new KeyBinding(Names.Keys.LG, Keyboard.KEY_DOWN, Names.Keys.ALL);

    //All Games
    public static BetterKeyBinding a1 = new BetterKeyBinding(Names.Keys.A1 + ".default", Keyboard.KEY_NUMPAD1, Names.Keys.ALL);
    public static BetterKeyBinding a2 = new BetterKeyBinding(Names.Keys.A2 + ".default", Keyboard.KEY_NUMPAD2, Names.Keys.ALL);
    public static BetterKeyBinding a3 = new BetterKeyBinding(Names.Keys.A3 + ".default", Keyboard.KEY_NUMPAD3, Names.Keys.ALL);
    public static BetterKeyBinding a4 = new BetterKeyBinding(Names.Keys.A4 + ".default", Keyboard.KEY_NUMPAD4, Names.Keys.ALL);
    public static BetterKeyBinding a5 = new BetterKeyBinding(Names.Keys.A5 + ".default", Keyboard.KEY_NUMPAD5, Names.Keys.ALL);
    public static BetterKeyBinding a6 = new BetterKeyBinding(Names.Keys.A6 + ".default", Keyboard.KEY_NUMPAD6, Names.Keys.ALL);
    public static BetterKeyBinding a7 = new BetterKeyBinding(Names.Keys.A7 + ".default", Keyboard.KEY_NUMPAD7, Names.Keys.ALL);
    public static BetterKeyBinding a8 = new BetterKeyBinding(Names.Keys.A8 + ".default", Keyboard.KEY_NUMPAD8, Names.Keys.ALL);
    public static BetterKeyBinding a9 = new BetterKeyBinding(Names.Keys.A9 + ".default", Keyboard.KEY_NUMPAD9, Names.Keys.ALL);
}
