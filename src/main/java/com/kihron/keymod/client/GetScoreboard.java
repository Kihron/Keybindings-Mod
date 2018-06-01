package com.kihron.keymod.client;

import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.StringUtils;

public class GetScoreboard
{
    public static String getBoardTitle(Scoreboard board) {
        ScoreObjective titleObjective = board.getObjectiveInDisplaySlot(1);
         if(board.getObjectiveInDisplaySlot(1) != null)
         {
             return StringUtils.stripControlCodes(titleObjective.getDisplayName().replaceAll("\\s",""));
         }
         else {
             return null;
         }
    }
}