package net.hollowed.rsapi.common.item;

import net.hollowed.rsapi.RSMod;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, RSMod.MODID);

    //Blank Runes
    public static final RegistryObject<Item> BLANK_STONE_RUNE = ITEMS.register("blank_stone_rune",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    public static final RegistryObject<Item> BLANK_CHARRED_RUNE = ITEMS.register("blank_charred_rune",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    public static final RegistryObject<Item> BLANK_VOID_RUNE = ITEMS.register("blank_void_rune",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    //Runes
    public static final RegistryObject<Item> SCARLET_RUNE = ITEMS.register("scarlet_rune",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    public static final RegistryObject<Item> VOID_RUNE = ITEMS.register("void_rune",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    public static final RegistryObject<Item> VERDANT_RUNE = ITEMS.register("verdant_rune",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    public static final RegistryObject<Item> AZURE_RUNE = ITEMS.register("azure_rune",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    public static final RegistryObject<Item> GOLDEN_RUNE = ITEMS.register("golden_rune",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    public static final RegistryObject<Item> VIOLET_RUNE = ITEMS.register("violet_rune",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    //Tomes
    public static final RegistryObject<Item> ANCIENT_TOME = ITEMS.register("ancient_tome",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    public static final RegistryObject<Item> SCARLET_TOME = ITEMS.register("scarlet_tome",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    public static final RegistryObject<Item> VOID_TOME = ITEMS.register("void_tome",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    public static final RegistryObject<Item> VERDANT_TOME = ITEMS.register("verdant_tome",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    public static final RegistryObject<Item> AZURE_TOME = ITEMS.register("azure_tome",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    public static final RegistryObject<Item> GOLDEN_TOME = ITEMS.register("golden_tome",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    public static final RegistryObject<Item> VIOLET_TOME = ITEMS.register("violet_tome",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    //Tanned items & related
    public static final RegistryObject<Item> TANNED_LEATHER = ITEMS.register("tanned_leather",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    public static final RegistryObject<Item> TANNED_RABBIT_HIDE = ITEMS.register("tanned_rabbit_hide",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    public static final RegistryObject<Item> PARCHMENT = ITEMS.register("parchment",
            () -> new Item(new Item.Properties().stacksTo(64)
            ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
