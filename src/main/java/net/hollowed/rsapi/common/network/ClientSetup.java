package net.hollowed.rsapi.common.network;

import net.hollowed.rsapi.RSMod;
import net.hollowed.rsapi.common.block.BlockRegistry;
import net.hollowed.rsapi.common.block.entity.custom.TanningRackRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RSMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void rendererRegister(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockRegistry.TANNING_RACK_TILE.get(), TanningRackRenderer::new);
    }
}


