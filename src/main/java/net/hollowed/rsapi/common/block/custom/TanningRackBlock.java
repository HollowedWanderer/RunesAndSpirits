package net.hollowed.rsapi.common.block.custom;

import net.hollowed.rsapi.common.block.BlockRegistry;
import net.hollowed.rsapi.common.block.entity.custom.TanningRackTile;
import net.hollowed.rsapi.common.network.ClientSetup;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class TanningRackBlock extends BaseEntityBlock {

    public static final VoxelShape SHAPE_BOTTOM = Block.box(0, 0, 0, 16, 2, 16);

    public static final VoxelShape SHAPE = Shapes.or(SHAPE_BOTTOM);
    public TanningRackBlock() {
        super(Properties.copy(Blocks.SCAFFOLDING).noOcclusion());
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level pLevel, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pos);
            if (entity instanceof TanningRackTile tanningRackTile) {

                ItemStack currentTanningItem = tanningRackTile.getHeldItem();
                ItemStack handItem = player.getItemInHand(hand);

                ItemStack playerItem = currentTanningItem.copy();
                if (handItem.isEmpty() || handItem.getCount() == 1) {
                    player.setItemInHand(hand, playerItem);
                } else {
                    dropItem(playerItem, player);
                }
                tanningRackTile.setHeldItem(ItemStack.EMPTY);


                currentTanningItem = handItem.copy();
                if (!currentTanningItem.isEmpty()) {
                    currentTanningItem.setCount(1);
                    tanningRackTile.setHeldItem(currentTanningItem);
                    handItem.shrink(1);
                }
                pLevel.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    private void dropItem(ItemStack itemstack, Player owner) {
        if (owner instanceof ServerPlayer serverplayer) {
            ItemEntity itementity = serverplayer.drop(itemstack, false);
            if (itementity != null) {
                itementity.setNoPickUpDelay();
                itementity.setThrower(serverplayer.getUUID());
            }
        }
    }

    @Override
    public void onRemove(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof TanningRackTile) {
                ((TanningRackTile) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new TanningRackTile(pos, state);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTanningTicker(level, type, BlockRegistry.TANNING_RACK_TILE.get());
    }

    @Nullable
    private <T extends BlockEntity> BlockEntityTicker<T> createTanningTicker(Level level, BlockEntityType<T> givenType, BlockEntityType<? extends TanningRackTile> expectedType) {
        return level.isClientSide ? null : createTickerHelper(givenType, expectedType, TanningRackTile::tick);
    }
}