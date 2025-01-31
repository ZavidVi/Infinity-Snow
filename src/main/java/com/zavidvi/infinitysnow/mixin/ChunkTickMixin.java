package com.zavidvi.infinitysnow.mixin;

import com.zavidvi.infinitysnow.common.InfinitySnow;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ServerLevel.class})
public abstract class ChunkTickMixin {

     public ChunkTickMixin() {
    }

    @Inject(
            at = {@At("HEAD")},
            cancellable = true,
            method = {"Lnet/minecraft/server/level/ServerLevel;tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V"}
    )
    private void chunkTick(LevelChunk levelChunk, int randomTickSpeed, CallbackInfo info) {
        InfinitySnow.chunkTick(levelChunk, randomTickSpeed);
    }
}
