package com.kihron.keymod.client.handler;

import com.kihron.keymod.client.Reference;
import com.kihron.keymod.version.VersionChecker;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class KeyEventHandler {

    private boolean hasRun;

    @SubscribeEvent
    public void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
        if (event.entity.worldObj.isRemote && event.entity == Minecraft.getMinecraft().thePlayer) {
            if (!hasRun) {
                EntityPlayer player = (EntityPlayer) event.entity;
                VersionChecker.checkForUpdate(VersionChecker.KeyModURL, Reference.MOD_ID, Reference.VERSION, player);
                hasRun = true;
            }
        }
    }
}