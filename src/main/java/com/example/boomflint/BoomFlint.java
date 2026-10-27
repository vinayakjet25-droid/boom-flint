package com.example.boomflint;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BoomFlint implements ModInitializer {
    /** Explosion strength. TNT is 4.0, creeper is 3.0. */
    private static final float POWER = 3.0F;

    @Override
    public void onInitialize() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            ItemStack stack = player.getItemInHand(hand);
            if (!stack.is(Items.FLINT_AND_STEEL)) {
                return InteractionResult.PASS;
            }
            if (player.isSpectator()) {
                return InteractionResult.PASS;
            }
            if (level.isClientSide()) {
                return InteractionResult.SUCCESS; // server does the real work
            }

            BlockPos pos = hitResult.getBlockPos();
            BlockState state = level.getBlockState(pos);

            // Remove the clicked block first (skips unbreakable ones like bedrock)
            if (state.getDestroySpeed(level, pos) >= 0.0F) {
                level.removeBlock(pos, false);
            }

            Vec3 c = Vec3.atCenterOf(pos);
            level.explode(null, c.x, c.y, c.z, POWER, Level.ExplosionInteraction.TNT);

            if (!player.getAbilities().instabuild) {
                EquipmentSlot slot = hand == InteractionHand.MAIN_HAND
                        ? EquipmentSlot.MAINHAND
                        : EquipmentSlot.OFFHAND;
                stack.hurtAndBreak(1, player, slot);
            }
            return InteractionResult.SUCCESS;
        });
    }
}
