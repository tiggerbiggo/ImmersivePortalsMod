package com.qouteall.immersive_portals.mixin.client.multiworld_awareness;

import com.qouteall.immersive_portals.ducks.IEParticleManager;
import com.qouteall.immersive_portals.render.context_management.PortalRendering;
import com.qouteall.immersive_portals.render.context_management.RenderStates;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class MixinParticleManager implements IEParticleManager {
    @Shadow
    protected ClientWorld world;
    
    // skip particle rendering for far portals
    @Inject(
        method = "renderParticles",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onBeginRenderParticles(
        MatrixStack matrixStack, VertexConsumerProvider.Immediate immediate,
        LightmapTextureManager lightmapTextureManager, Camera camera, float f, CallbackInfo ci
    ) {
        if (PortalRendering.isRendering()) {
            if (RenderStates.getRenderedPortalNum() > 4) {
                ci.cancel();
            }
        }
    }
    
    @Override
    public void mySetWorld(ClientWorld world_) {
        world = world_;
    }
}
