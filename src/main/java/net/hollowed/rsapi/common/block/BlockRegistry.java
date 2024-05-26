package net.hollowed.rsapi.common.block;

import net.hollowed.rsapi.RSMod;
import net.hollowed.rsapi.common.block.custom.TanningRackBlock;
import net.hollowed.rsapi.common.block.entity.custom.TanningRackTile;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RSMod.MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RSMod.MODID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
    }

    public static final RegistryObject<Block> TANNING_RACK = BLOCKS.register("tanning_rack", TanningRackBlock::new);

    public static final RegistryObject<BlockEntityType<TanningRackTile>> TANNING_RACK_TILE = BLOCK_ENTITIES.register("tanning_rack",
            () -> BlockEntityType.Builder.of(TanningRackTile::new, TANNING_RACK.get()).build(null));
}
