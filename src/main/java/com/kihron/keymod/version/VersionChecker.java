package com.kihron.keymod.version;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.kihron.keymod.client.KeyMod;
import com.kihron.keymod.client.config.Config;
import joptsimple.internal.Strings;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class VersionChecker {

    public static final String KeyModURL = "https://raw.githubusercontent.com/Kihron/Electritech/master/update-key.json";

    public static boolean isUpdate(String url, String currentVersion) {
        try {
            URL URL = new URL(url);
            JsonReader jr = new JsonReader(new InputStreamReader(URL.openStream()));
            JsonElement je = new JsonParser().parse(jr);
            JsonObject jo = je.getAsJsonObject();
            String version = jo.get("version").getAsString();
            if (!version.equals(currentVersion))
                return true;
        } catch (Exception e) {
            KeyMod.logger.catching(e);
        }
        return false;
    }

    public static void checkForUpdate(String url, String modid, String currentVersion, EntityPlayer player) {
        if (!Config.UPDATE_CHECKER_MODS.containsKey(modid) || !Config.UPDATE_CHECKER_MODS.get(modid))
            return;
        String version = "";
        String name = "";
        List<String> changeLog = new ArrayList<String>();
        String downloadURL = "";
        boolean updateRequired = false;
        try {
            URL URL = new URL(url);
            JsonReader jr = new JsonReader(new InputStreamReader(URL.openStream()));
            JsonElement je = new JsonParser().parse(jr);
            JsonObject jo = je.getAsJsonObject();
            version = jo.get("version").getAsString();
            if (!version.equals(currentVersion))
                updateRequired = true;
            if (updateRequired) {
                name = jo.get("name").getAsString();
                JsonArray cl = jo.get("changelog").getAsJsonArray();
                for (JsonElement e : cl) {
                    changeLog.add(e.getAsString());
                }
                if(jo.has("download"))
                    downloadURL = jo.get("download").getAsString();
            }
        } catch (Exception e) {
            KeyMod.logger.info("Error reading update url: " + url);
            KeyMod.logger.catching(e);
            return;
        }
        if (updateRequired) {
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE
                    + I18n.format("update.ready", EnumChatFormatting.GOLD + name + EnumChatFormatting.WHITE)));
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE
                    + I18n.format("update.version", EnumChatFormatting.DARK_RED + currentVersion + EnumChatFormatting.WHITE,
                    EnumChatFormatting.DARK_GREEN + version)));
            IChatComponent changeLogAndVersion = new ChatComponentText("");
            if (!Strings.isNullOrEmpty(downloadURL))
                changeLogAndVersion.appendSibling(new ChatComponentText(EnumChatFormatting.WHITE + "["
                        + EnumChatFormatting.DARK_AQUA + I18n.format("update.download") + EnumChatFormatting.WHITE + "] ")
                        .setChatStyle(new ChatStyle()
                                .setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, downloadURL)).setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(downloadURL)))));
            if (changeLog.size() > 0) {
                String changeLogString = "";
                for (String log : changeLog) {
                    if (log.startsWith("*"))
                        changeLogString += EnumChatFormatting.AQUA;
                    else if (log.startsWith("-"))
                        changeLogString += EnumChatFormatting.RED;
                    else if (log.startsWith("+"))
                        changeLogString += EnumChatFormatting.GREEN;
                    else
                        changeLogString += EnumChatFormatting.WHITE;
                    changeLogString += log + "\n";
                }
                changeLogString = changeLogString.substring(0, changeLogString.length() - 1);
                changeLogAndVersion.appendSibling(new ChatComponentText(
                        "[" + EnumChatFormatting.DARK_AQUA + I18n.format("update.changelog") + EnumChatFormatting.WHITE + "]")
                        .setChatStyle(new ChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                new ChatComponentText(changeLogString)))));
            }
            player.addChatMessage((changeLogAndVersion));
        }
    }

}