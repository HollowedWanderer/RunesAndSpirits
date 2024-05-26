package net.hollowed.rsapi.common.item;

import net.hollowed.rsapi.RSMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = RSMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            RSMod.MODID);

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static RegistryObject<CreativeModeTab> RS_TAB = CREATIVE_MODE_TABS.register("rs_tab", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.BLANK_STONE_RUNE.get())).title(Component.literal("Runes & Spirits")).build());
}
