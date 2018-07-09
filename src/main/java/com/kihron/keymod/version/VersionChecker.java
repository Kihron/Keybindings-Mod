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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

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
            player.sendMessage(new TextComponentString(TextFormatting.WHITE
                    + I18n.format("update.ready", TextFormatting.GOLD + name + TextFormatting.WHITE)));
            player.sendMessage(new TextComponentString(TextFormatting.WHITE
                    + I18n.format("update.version", TextFormatting.DARK_RED + currentVersion + TextFormatting.WHITE,
                    TextFormatting.DARK_GREEN + version)));
            ITextComponent changeLogAndVersion = new TextComponentString("");
            if (!Strings.isNullOrEmpty(downloadURL))
                changeLogAndVersion.appendSibling(new TextComponentString(TextFormatting.WHITE + "["
                        + TextFormatting.DARK_AQUA + I18n.format("update.download") + TextFormatting.WHITE + "] ")
                        .setStyle(new Style()
                                .setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, downloadURL)).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(downloadURL)))));
            if (changeLog.size() > 0) {
                String changeLogString = "";
                for (String log : changeLog) {
                    if (log.startsWith("*"))
                        changeLogString += TextFormatting.AQUA;
                    else if (log.startsWith("-"))
                        changeLogString += TextFormatting.RED;
                    else if (log.startsWith("+"))
                        changeLogString += TextFormatting.GREEN;
                    else
                        changeLogString += TextFormatting.WHITE;
                    changeLogString += log + "\n";
                }
                changeLogString = changeLogString.substring(0, changeLogString.length() - 1);
                changeLogAndVersion.appendSibling(new TextComponentString(
                        "[" + TextFormatting.DARK_AQUA + I18n.format("update.changelog") + TextFormatting.WHITE + "]")
                        .setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                new TextComponentString(changeLogString)))));
            }
            player.sendMessage((changeLogAndVersion));
        }
    }

}