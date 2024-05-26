package net.hollowed.rsapi;

import com.mojang.logging.LogUtils;
import net.hollowed.rsapi.common.block.BlockRegistry;
import net.hollowed.rsapi.common.block.ItemRegistry;
import net.hollowed.rsapi.common.item.ModCreativeModeTab;
import net.hollowed.rsapi.common.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RSMod.MODID)
public class RSMod
{
    public static final String MODID = "rsapi";
    private static final Logger LOGGER = LogUtils.getLogger();

    public RSMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        ModItems.register(modEventBus);
        ModCreativeModeTab.register(modEventBus);

        BlockRegistry.register(modEventBus);
        ItemRegistry.register(modEventBus);

        // Register the item to a creative tab

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTab() == ModCreativeModeTab.RS_TAB.get()) {
            event.accept(ModItems.BLANK_STONE_RUNE);
            event.accept(ModItems.BLANK_CHARRED_RUNE);
            event.accept(ModItems.BLANK_VOID_RUNE);
            event.accept(ModItems.ANCIENT_TOME);
            event.accept(ModItems.TANNED_LEATHER);
            event.accept(ModItems.TANNED_RABBIT_HIDE);
            event.accept(ModItems.PARCHMENT);
            event.accept(BlockRegistry.TANNING_RACK);
            event.accept(ModItems.VERDANT_RUNE);
            event.accept(ModItems.AZURE_RUNE);
            event.accept(ModItems.GOLDEN_RUNE);
            event.accept(ModItems.VIOLET_RUNE);
            event.accept(ModItems.SCARLET_RUNE);
            event.accept(ModItems.VOID_RUNE);
            event.accept(ModItems.VERDANT_TOME);
            event.accept(ModItems.AZURE_TOME);
            event.accept(ModItems.GOLDEN_TOME);
            event.accept(ModItems.VIOLET_TOME);
            event.accept(ModItems.SCARLET_TOME);
            event.accept(ModItems.VOID_TOME);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("Runes & Spells API successfully loaded");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("Runes & Spells API successfully loaded on server");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("Runes & Spells API successfully loaded on client");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
