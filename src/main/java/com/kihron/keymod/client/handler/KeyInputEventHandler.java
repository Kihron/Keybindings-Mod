package com.kihron.keymod.client.handler;

import com.kihron.keymod.client.GetScoreboard;
import com.kihron.keymod.client.json.Game;
import com.kihron.keymod.client.json.JSONReader;
import com.kihron.keymod.client.settings.Keybindings;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.io.IOException;
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

        Map<Integer, Game> skywars = gameStrings.get("Skywars");
        skywars.put(1, new Game("solo_insane", "Skywars Solo Insane"));
        skywars.put(2, new Game("teams_insane", "Skywars Teams Insane"));
        skywars.put(3, new Game("ranked_normal", "Skywars Ranked"));

        Map<Integer, Game> bedwars = gameStrings.get("Bedwars");
        bedwars.put(1, new Game("bedwars_eight_two", "Bedwars Doubles"));
        bedwars.put(2, new Game("bedwars_four_three", "Bedwars Threes"));
        bedwars.put(3, new Game("bedwars_four_four", "Bedwars Fours"));

        Map<Integer, Game> zombies = gameStrings.get("Zombies");
        zombies.put(1, new Game("prototype_zombies_story_normal", "Zombies Normal"));
        zombies.put(2, new Game("prototype_zombies_story_hard", "Zombies Hard"));

        Map<Integer, Game> bridges = gameStrings.get("Bridges");
        bridges.put(1, new Game("prototype_bridge_1v1", "Bridges 1v1"));
        bridges.put(2, new Game("prototype_bridge_2v2", "Bridges 2v2"));
        bridges.put(3, new Game("prototype_bridge_4v4", "Bridges 4v4"));
        bridges.put(4, new Game("prototype_bridge_2v2v2v2", "Bridges 2v2v2v2"));
        bridges.put(5, new Game("prototype_bridge_3v3v3v3", "Bridges 3v3v3v3"));

        Map<Integer, Game> duels = gameStrings.get("Duels");
        duels.put(1, new Game("duels_uhc_duel", "Duels 1v1"));
        duels.put(2, new Game("duels_uhc_doubles", "Duels 2v2"));

        Map<Integer, Game> tnt = gameStrings.get("TNT");
        tnt.put(1, new Game("tnt_tntrun", "TNT Run"));
        tnt.put(2, new Game("tnt_pvprun", "PvP Run"));
        tnt.put(3, new Game("tnt_bowspleef", "Bow Spleef"));
        tnt.put(4, new Game("tnt_tntag", "TNT Tag"));
        tnt.put(5, new Game("tnt_capture", "TNT Wizards"));

        Map<String, Map<Integer, Game>> tempK = null;
        try {
            tempK = JSONReader.readMap("https://raw.githubusercontent.com/Kihron/Electritech/master/keys.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tempK != null)
            gameStrings = tempK;

        String[] tempC = null;
        try {
            tempC = JSONReader.readCategories("https://raw.githubusercontent.com/Kihron/Electritech/master/keys.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tempC != null)
            options = tempC;
    }

    private boolean lockOn = false;

    private Map<String, Map<Integer, Game>> gameStrings;

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
            sendMessage("play " + gameStrings.get(getCurrentGame()).get(id).getGameString());
            lastSelected = selected;
            lastGame = id;
        }
    }

    private Game getLastGame(int i) {
        return gameStrings.get(getCurrentGame()).get(i);
    }

    private String getCurrentGame() {
        return options[selected];
    }

    private void selectGame(int game) {
        selectGame(game, true);
    }

    private void selectGame(int game, boolean print) {
        selected = game;
        if (print)
        printGame();
        Keybindings.a1.setKeyDescription(getLastGameTranslateString(1));
        Keybindings.a2.setKeyDescription(getLastGameTranslateString(2));
        Keybindings.a3.setKeyDescription(getLastGameTranslateString(3));
        Keybindings.a4.setKeyDescription(getLastGameTranslateString(4));
        Keybindings.a5.setKeyDescription(getLastGameTranslateString(5));
        Keybindings.a6.setKeyDescription(getLastGameTranslateString(6));
        Keybindings.a7.setKeyDescription(getLastGameTranslateString(7));
        Keybindings.a8.setKeyDescription(getLastGameTranslateString(8));
        Keybindings.a9.setKeyDescription(getLastGameTranslateString(9));
    }

    private String getLastGameTranslateString(int id) {
        return getLastGame(id) == null ? "Action " + id : getLastGame(id).getTranslateString();
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
                addMessage((i + 1) + " - " + getLastGameTranslateString(i + 1));
            }
            if (selected != lastSelected && lastGame != 0) {
                int temp = selected;
                selectGame(lastSelected, false);
                addMessage("Last Played: " + getLastGameTranslateString(lastGame));
                selectGame(temp, false);
            } else if (lastGame != 0)
                addMessage("Last Played: " + getLastGameTranslateString(lastGame));
        }
    }
}
