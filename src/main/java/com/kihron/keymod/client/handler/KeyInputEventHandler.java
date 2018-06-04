package com.kihron.keymod.client.handler;

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


    /**
     * builds lists
     */
    public KeyInputEventHandler() {
        buildLists();
        buildBindings();
    }


    private void buildBindings(){
        Keybindings.a1.setOnPress(() -> playGame(1));
        Keybindings.a2.setOnPress(() -> playGame(2));
        Keybindings.a3.setOnPress(() -> playGame(3));
        Keybindings.a4.setOnPress(() -> playGame(4));
        Keybindings.a5.setOnPress(() -> playGame(5));
        Keybindings.a6.setOnPress(() -> playGame(6));
        Keybindings.a7.setOnPress(() -> playGame(7));
        Keybindings.a8.setOnPress(() -> playGame(8));
        Keybindings.a9.setOnPress(() -> playGame(9));

        Keybindings.gp.setOnPress(() -> sendCommand("g party"));
        Keybindings.lock.setOnPress(() -> {
            lockOn = !lockOn;
            addMessage("Lock: " + (lockOn ? EnumChatFormatting.GREEN + "Enabled" : EnumChatFormatting.RED + "Disabled"));
        });
        Keybindings.hb.setOnPress(() -> sendCommand("hub"));
        Keybindings.pd.setOnPress(() -> sendCommand("p disband"));
        Keybindings.pw.setOnPress(() -> sendCommand("p warp"));

        Keybindings.ra.setOnPress(() -> selectCategory(getAbove(selected, options.length)));
        Keybindings.la.setOnPress(() -> selectCategory(getBelow(selected, options.length)));
        Keybindings.sc.setOnPress(() -> {
            for (int i = 0; i < options.length; i++) {
                EnumChatFormatting color = EnumChatFormatting.WHITE;
                if(i == selected)
                    color = EnumChatFormatting.DARK_GREEN;
                if(i == getAbove(selected, options.length) || i == getBelow(selected, options.length))
                    color = EnumChatFormatting.GREEN;
                addMessage((i + 1) + " - " + color + options[i]);
            }
        });

        Keybindings.rp.setOnPress(() -> {
            if (lastGame != null) {
                playGame(lastGame, true);
            }
            else {
                addMessage(EnumChatFormatting.RED + "No previous game found.");
            }
        });
        Keybindings.lg.setOnPress(() -> {
            printCategory();
            for (int i = 0; i < gameStrings.get(getCurrentCategory()).size(); i++) {
                addMessage((i + 1) + " - " + getControl(i + 1));
            }
            if (lastGame != null) {
                addMessage("Last Played: " + lastGame.getTranslateString());
            }
        });
    }

    /**
     * stores categories you can select in order
     */
    private String[] options;


    private static int getAbove(int index, int maxNum){
        return (index + 1) % maxNum;
    }

    private static int getBelow(int index, int maxNum){
        return (index - 1 + maxNum) % maxNum;
    }

    /**
     * stores which option is currently selected
     */
    private int selected = 0;


    /**
     * stores which game was last played
     */
    private Game lastGame;


    /**
     * builds a default for options and gameStrings, then tries to access said values from the internet
     */
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


    /**
     * stores whether or not numpad buttons can switch games
     */
    private boolean lockOn = false;


    /**
     * stores all the different games using categories as strings and numpad buttons
     */
    private Map<String, Map<Integer, Game>> gameStrings;

    
    /**
     * @param command a command to send
     *                so if you enter "g disband" the player will send the message "/g disband" into chat
     */
    private static void sendCommand(String command) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/" + command);
    }


    /**
     * @param message sends a string message directly to the player
     *                so if you enter "Lock: disabled" it will send a client side message to the player in chat
     */
    private static void addMessage(String message) {
        addMessage(new ChatComponentText(message));
    }


    /**
     * @param message a chat component to send to only the player
     *                so if you enter "Lock: disabled" it will send a client side message to the player in chat
     */
    private static void addMessage(IChatComponent message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(message);
    }


    /**
     * prints current category
     */
    private void printCategory() {
        addMessage("Currently Selected: " + EnumChatFormatting.GREEN + getCurrentCategory());
    }


    /**
     * @param game a game to play
     *             makes the player run the /play command with the correct gameString from the Game
     */
    private void playGame(Game game){
        playGame(game, false);
    }


    /**
     * @param game a game to play
     * @param override whether or not to override the lock
     *                 makes the player run the /play command with the correct gameString from the Game
     */
    private void playGame(Game game, boolean override){
        if(game == null) return;
        if (lockOn && !override) {
            addMessage("Lock is " + EnumChatFormatting.GREEN + "On");
            return;
        }
        sendCommand("play " + game.getGameString());
        lastGame = game;

    }


    /**
     * @param id id of game in gameStrings to play
     *           makes the player run the /play command with the correct gameString from the Game
     */
    private void playGame(int id) {
        playGame(id, false);
    }


    /**
     * @param id id of game in gameStrings to play
     * @param override whether or not to override the lock
     *                 makes the player run the /play command with the correct gameString from the Game
     */
    private void playGame(int id, boolean override){
        playGame(getGameFromID(id), override);
    }


    /**
     * @param id id of game in gameStrings
     * @return the game the id corresponds to
     *                  gets a game from the selected category based on the map.
     */
    private Game getGameFromID(int id) {
        return gameStrings.get(getCurrentCategory()).get(id);
    }


    /**
     * @return get the category that is selected
     */
    private String getCurrentCategory() {
        return options[selected];
    }


    /**
     * @param category a category to select
     *                 selects given category
     */
    private void selectCategory(int category) {
        selected = category;
        printCategory();
        Keybindings.a1.setKeyDescription(getControl(1));
        Keybindings.a2.setKeyDescription(getControl(2));
        Keybindings.a3.setKeyDescription(getControl(3));
        Keybindings.a4.setKeyDescription(getControl(4));
        Keybindings.a5.setKeyDescription(getControl(5));
        Keybindings.a6.setKeyDescription(getControl(6));
        Keybindings.a7.setKeyDescription(getControl(7));
        Keybindings.a8.setKeyDescription(getControl(8));
        Keybindings.a9.setKeyDescription(getControl(9));
    }


    /**
     * @param id id
     * @return a translate string from a given id based on the last game
     *                  returns Action + id if id doesn't correspond to a game,
     *                  otherwise returns translate string of said game
     */
    private String getControl(int id) {
        return getGameFromID(id) == null ? "Action " + id : getGameFromID(id).getTranslateString();
    }

    /**
     * @param event the key event
     *              runs whenever a key is pressed
     */
    /*String sb = GetScoreboard.getBoardTitle(Minecraft.getMinecraft().thePlayer.getWorldScoreboard());
                    if (sb != null) {
                        switch (sb.toLowerCase()) {
                            case "skywars":
                                selectCategory(0);
                                break;
                            case "bedwars":
                                selectCategory(1);
                                break;
                            case "zombies":
                                selectCategory(2);
                                break;
                            case "thebridges":
                                selectCategory(3);
                                break;
                            case "duels":
                                selectCategory(4);
                                break;
                            case "thetntgames":
                                selectCategory(5);
                                break;
                            default:
                                addMessage("Could not find game.");
                        }
                    } else {
                        addMessage(EnumChatFormatting.RED + "Could not find game.");
                    }*/
    @SubscribeEvent
    public void handleKeyInputEvents(InputEvent.KeyInputEvent event) {
        Keybindings.la.runIfPressed();
        Keybindings.ra.runIfPressed();
        Keybindings.sc.runIfPressed();
        Keybindings.hb.runIfPressed();
        Keybindings.gp.runIfPressed();
        Keybindings.lg.runIfPressed();
        Keybindings.pd.runIfPressed();
        Keybindings.pw.runIfPressed();
        Keybindings.rp.runIfPressed();
        Keybindings.lock.runIfPressed();
        Keybindings.a1.runIfPressed();
        Keybindings.a2.runIfPressed();
        Keybindings.a3.runIfPressed();
        Keybindings.a4.runIfPressed();
        Keybindings.a5.runIfPressed();
        Keybindings.a6.runIfPressed();
        Keybindings.a7.runIfPressed();
        Keybindings.a8.runIfPressed();
        Keybindings.a9.runIfPressed();
    }
}
