package com.kihron.keymod.client.handler;

import com.kihron.keymod.client.GetScoreboard;
import com.kihron.keymod.client.Names;
import com.kihron.keymod.client.settings.Keybindings;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.util.HashMap;
import java.util.Map;

public class KeyInputEventHandler {

    public KeyInputEventHandler() {
        buildLists();
        selected = 0;
    }

    private String[] options;

    private int selected;

    private int lastSelected;

    private int lastGame;

    //Builds maps and Lists
    private void buildLists() {
        options = new String[]{"Skywars","Bedwars","Zombies","Bridges","Duels","TNT"};
        gameStrings = new HashMap<>();
        for (String key: options) {
            gameStrings.put(key, new HashMap<>());
        }

        Map<Integer, String> skywars = gameStrings.get("Skywars");
        skywars.put(1, "solo_insane");
        skywars.put(2, "teams_insane");
        skywars.put(3, "ranked_normal");

        Map<Integer, String> bedwars = gameStrings.get("Bedwars");
        bedwars.put(1, "bedwars_eight_two");
        bedwars.put(2, "bedwars_four_three");
        bedwars.put(3, "bedwars_four_four");

        Map<Integer, String> zombies = gameStrings.get("Zombies");
        zombies.put(1, "prototype_zombies_story_normal");
        zombies.put(2, "prototype_zombies_story_hard");

        Map<Integer, String> bridges = gameStrings.get("Bridges");
        bridges.put(1, "prototype_bridge_1v1");
        bridges.put(2, "prototype_bridge_2v2");
        bridges.put(3, "prototype_bridge_4v4");
        bridges.put(4, "prototype_bridge_2v2v2v2");
        bridges.put(5, "prototype_bridge_3v3v3v3");

        Map<Integer, String> duels = gameStrings.get("Duels");
        duels.put(1, "duels_uhc_duel");
        duels.put(2, "duels_uhc_doubles");

        Map<Integer, String> tnt = gameStrings.get("TNT");
        tnt.put(1, "tnt_tntrun");
        tnt.put(2, "tnt_pvprun");
        tnt.put(3, "tnt_bowspleef");
        tnt.put(4, "tnt_tntag");
        tnt.put(5, "tnt_capture");
    }

    private boolean lockOn = false;

    private Map<String, Map<Integer, String>> gameStrings;

    //Makes the player say something
    private static void sendMessage(String message) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/" + message);
    }

    //Sends message to player
    public static void addMessage(String message) {
        addMessage(new ChatComponentText(message));
    }

    public static void addMessage(IChatComponent message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(message);
    }

    public static void addMessage(IChatComponent... message) {
        IChatComponent m = message[0];
        for (int i = 1; i < message.length; i++) {
            m = m.appendSibling(message[i]);
        }
        addMessage(m);
    }

    private IChatComponent getGameComponent(int i, String s) {
        return new ChatComponentTranslation("keys.keymod.a" + (i + 1) + "." + s);
    }

    private void printGame() {
        addMessage("Currently Selected: " + EnumChatFormatting.GREEN + getCurrentGame());
    }

    private void playGame(int id) {
        playGame(id, false);
    }

    private void playGame(int id, boolean override){
        if (lockOn && !override) {
            addMessage("Lock is " + EnumChatFormatting.GREEN + "On");
            return;
        }
        if (gameStrings.get(getCurrentGame()).keySet().size() >= id) {
            sendMessage("play " + gameStrings.get(getCurrentGame()).get(id));
            lastSelected = selected;
            lastGame = id;
        }
    }

    private String getCurrentGame() {
        return options[selected];
    }

    private void selectGame(int game) {
        selected = game;
        printGame();
        Keybindings.a1.setKeyDescription(Names.Keys.A1 + "." + getCurrentGame().toLowerCase());
        Keybindings.a2.setKeyDescription(Names.Keys.A2 + "." + getCurrentGame().toLowerCase());
        Keybindings.a3.setKeyDescription(Names.Keys.A3 + "." + getCurrentGame().toLowerCase());
        Keybindings.a4.setKeyDescription(Names.Keys.A4 + "." + getCurrentGame().toLowerCase());
        Keybindings.a5.setKeyDescription(Names.Keys.A5 + "." + getCurrentGame().toLowerCase());
        Keybindings.a6.setKeyDescription(Names.Keys.A6 + "." + getCurrentGame().toLowerCase());
        Keybindings.a7.setKeyDescription(Names.Keys.A7 + "." + getCurrentGame().toLowerCase());
        Keybindings.a8.setKeyDescription(Names.Keys.A8 + "." + getCurrentGame().toLowerCase());
        Keybindings.a9.setKeyDescription(Names.Keys.A9 + "." + getCurrentGame().toLowerCase());
    }

    @SubscribeEvent
    public void handleKeyInputEvents(InputEvent.KeyInputEvent event) {
        String sb = GetScoreboard.getBoardTitle(Minecraft.getMinecraft().thePlayer.getWorldScoreboard());

        //Hub
        if (Keybindings.hb.isPressed()) {
            sendMessage("hub");
        }

        //Guild
        if (Keybindings.gp.isPressed()) {
            sendMessage("g party");
        }
        if (Keybindings.pd.isPressed()) {
            sendMessage("p disband");
        }

        if (Keybindings.pw.isPressed()) {
            sendMessage("p warp");
        }

        //Arrows
        if (Keybindings.ra.isPressed()) {
            selectGame((selected + 1) % options.length);
        }

        if (Keybindings.la.isPressed()) {
            selectGame((selected + options.length - 1) % options.length);
        }

        if (Keybindings.lock.isPressed()) {
            lockOn = !lockOn;
            addMessage("Lock " + (lockOn ? EnumChatFormatting.GREEN + "Enabled": EnumChatFormatting.RED + "Disabled"));
        }

        if (Keybindings.sc.isPressed()) {
            if (sb != null) {
                switch (sb.toLowerCase()) {
                    case "skywars":
                        selectGame(0);
                        break;
                    case "bedwars":
                        selectGame(1);
                        break;
                    case "zombies":
                        selectGame(2);
                        break;
                    case "thebridges":
                        selectGame(3);
                        break;
                    case "duels":
                        selectGame(4);
                        break;
                    case "thetntgames":
                        selectGame(5);
                        break;
                    default:
                        addMessage("Could not find game.");
                        return;
                }
            } else {
                addMessage(EnumChatFormatting.RED + "Could not find game.");
            }
        }

        if (Keybindings.a1.isPressed()) {
            playGame(1);
        }

        if (Keybindings.a2.isPressed()) {
            playGame(2);
        }

        if (Keybindings.a3.isPressed()) {
            playGame(3);
        }

        if (Keybindings.a4.isPressed()) {
            playGame(4);
        }

        if (Keybindings.a5.isPressed()) {
            playGame(5);
        }

        if (Keybindings.a6.isPressed()) {
            playGame(6);
        }

        if (Keybindings.a7.isPressed()) {
            playGame(7);
        }

        if (Keybindings.a8.isPressed()) {
            playGame(8);
        }

        if (Keybindings.a9.isPressed()) {
            playGame(9);
        }

        if (Keybindings.rp.isPressed()) {
            if (lastGame != 0) {
                if (selected != lastSelected)
                    selectGame(lastSelected);
                playGame(lastGame, true);
            }
            else {
                addMessage(EnumChatFormatting.RED + "No previous game found.");
            }
        }

        if (Keybindings.lg.isPressed()) {
            printGame();
            for (int i = 0; i < gameStrings.get(getCurrentGame()).size(); i++) {
                addMessage(new ChatComponentText((i + 1) + " - "), getGameComponent(i, getCurrentGame().toLowerCase()));
            }
            addMessage( new ChatComponentText("Last Played: "), getGameComponent(lastGame, options[lastSelected].toLowerCase()));
        }
    }
}