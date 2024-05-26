package net.hollowed.rsapi.common.block.entity.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TanningRackRenderer implements BlockEntityRenderer<TanningRackTile> {
    private final ItemRenderer itemRenderer;

    public TanningRackRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    private static final Vec3 ITEM_POS = new Vec3(0.5, 0.125, 0.5); // Position 2 pixels above the ground

    @Override
    public void render(TanningRackTile tanningRackTile, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack heldItem = tanningRackTile.getHeldItem();

        if (!heldItem.isEmpty()) {
            renderItem(heldItem, ITEM_POS, tanningRackTile, poseStack, bufferSource, packedLight, packedOverlay);
        }
    }

    private void renderItem(ItemStack itemStack, Vec3 offset, TanningRackTile tanningRackTile, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        int renderId = (int) tanningRackTile.getBlockPos().asLong();

        poseStack.translate(offset.x, offset.y, offset.z);

        // Adjust the rotation so the item lies flat instead of spinning
        poseStack.mulPose(Axis.XP.rotationDegrees(90));

        poseStack.scale(0.65f, 0.65f, 0.65f);

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(Objects.requireNonNull(tanningRackTile.getLevel()), tanningRackTile.getBlockPos()), packedOverlay, poseStack, bufferSource, tanningRackTile.getLevel(), renderId);
        poseStack.popPose();
    }
}
