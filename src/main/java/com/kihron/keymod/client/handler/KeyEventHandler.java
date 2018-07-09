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
        if (event.getEntity().world.isRemote && event.getEntity() == Minecraft.getMinecraft().player) {
            if (!hasRun) {
                EntityPlayer player = (EntityPlayer) event.getEntity();
                VersionChecker.checkForUpdate(VersionChecker.KeyModURL, Reference.MOD_ID, Reference.VERSION, player);
                hasRun = true;
            }
        }
    }
}