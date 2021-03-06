package com.qouteall.hiding_in_the_bushes.mixin.common;

import com.qouteall.immersive_portals.Global;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerManager.class)
public class MixinPlayerManager_MA {
    @Inject(
        method = "respawnPlayer",
        at = @At("HEAD")
    )
    private void onPlayerRespawn(
        ServerPlayerEntity player,
        boolean bl,
        CallbackInfoReturnable<ServerPlayerEntity> cir
    ) {
        Global.chunkDataSyncManager.onPlayerRespawn(player);
    }
}
