package net.hollowed.rsapi.common.block.entity.custom;

import net.hollowed.rsapi.common.block.BlockRegistry;
import net.hollowed.rsapi.common.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class TanningRackTile extends BlockEntity {

    private static final String NBT_HELD_ITEM = "heldItem";
    private static final String NBT_TIMER = "timer";
    private static final int TANNING_TIME = 9600; // 8 minutes - 9600 ticks

    private ItemStack heldItem = ItemStack.EMPTY;
    private int timer = 0;

    public TanningRackTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BlockRegistry.TANNING_RACK_TILE.get(), pWorldPosition, pBlockState);
    }

    public ItemStack getHeldItem() {
        return heldItem;
    }

    @Override
    protected void saveAdditional(@Nonnull CompoundTag tag) {
        writeNBT(tag);
    }

    public void drops() {
        SimpleContainer simpleContainer = new SimpleContainer(heldItem);
        Containers.dropContents(this.level, this.worldPosition, simpleContainer);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        readNBT(nbt);
    }

    public void setHeldItem(ItemStack newItem) {
        heldItem = newItem;
        timer = TANNING_TIME;
        setChanged();
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        writeNBT(tag);
        return tag;
    }

    private CompoundTag writeNBT(CompoundTag nbt) {
        nbt.put(NBT_HELD_ITEM, heldItem.serializeNBT());
        nbt.putInt(NBT_TIMER, timer);
        return nbt;
    }

    @Override
    public boolean triggerEvent(int pId, int pType) {
        return super.triggerEvent(pId, pType);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag nbt = writeNBT(new CompoundTag());
        return ClientboundBlockEntityDataPacket.create(this, (block) -> nbt);
    }

    private void readNBT(CompoundTag nbt) {
        if (nbt.contains(NBT_HELD_ITEM)) {
            heldItem = ItemStack.of(nbt.getCompound(NBT_HELD_ITEM));
        }
        if (nbt.contains(NBT_TIMER)) {
            timer = nbt.getInt(NBT_TIMER);
        }
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        handleUpdateTag(pkt.getTag());
        assert level != null;
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        if (tag != null) {
            load(tag);
        }
    }

    /**
     * Processes the held item and converts it to tanned leather or rabbit hide if applicable.
     */
    private void processItem() {
        if (heldItem.isEmpty() || level == null) {
            return;
        }

        // Check if it's daytime and the block is exposed to direct sunlight
        boolean isDaytime = level.isDay();
        boolean isExposedToSun = level.canSeeSky(worldPosition.above());

        if (isDaytime && isExposedToSun) {
            Item item = heldItem.getItem();
            if (item == Items.LEATHER) {
                heldItem = new ItemStack(ModItems.TANNED_LEATHER.get());
            } else if (item == Items.RABBIT_HIDE) {
                heldItem = new ItemStack(ModItems.TANNED_RABBIT_HIDE.get());
            }
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    public static void tick(Level p_155014_, BlockPos p_155015_, BlockState p_155016_, TanningRackTile p_155017_) {
        if (!p_155017_.heldItem.isEmpty()) {
            boolean isDaytime = p_155014_.isDay();
            boolean isExposedToSun = p_155014_.canSeeSky(p_155015_.above());

            if (isDaytime && isExposedToSun && p_155017_.timer > 0) {
                p_155017_.timer--;
                if (p_155017_.timer <= 0) {
                    p_155017_.processItem();
                }
                p_155017_.setChanged();
            }
        }
    }
}
