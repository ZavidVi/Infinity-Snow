package com.zavidvi.infinitysnow.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public class InfinitySnow {
    
    public static void chunkTick(LevelChunk chunk, int randomTickSpeed) {
        ServerLevel world = (ServerLevel)chunk.getLevel();
        ChunkPos chunkpos = chunk.getPos();
        boolean flag = world.isRaining();
        int i = chunkpos.getMinBlockX();
        int j = chunkpos.getMinBlockZ();
        if (world.random.nextInt(32) == 0) {
            BlockPos blockpos2 = world.getHeightmapPos(Types.MOTION_BLOCKING, world.getBlockRandomPos(i, 0, j, 15));
            Holder<Biome> biome = world.getBiome(blockpos2);
            if (flag && shouldSnow(biome.value(), world, blockpos2)) {
                if (world.getBlockState(blockpos2).getBlock() != Blocks.GRASS && !world.getBlockState(blockpos2).is(BlockTags.FLOWERS)) {
                    int state = world.getBlockState(blockpos2).getValue(SnowLayerBlock.LAYERS);
                    if (state < 8) {
                        world.setBlockAndUpdate(blockpos2, Blocks.SNOW.defaultBlockState().setValue(SnowLayerBlock.LAYERS, state + 1));
                    }
                    else if (state == 8){
                        world.setBlockAndUpdate(blockpos2, Blocks.SNOW_BLOCK.defaultBlockState());
                    }
                }
            }
        }

    }

    public static boolean shouldSnow(Biome biome, LevelReader levelReader, BlockPos blockPos) {
        if (!biome.coldEnoughToSnow(blockPos)) {
            return false;
        } else {
            if (blockPos.getY() >= levelReader.getMinBuildHeight() && blockPos.getY() < levelReader.getMaxBuildHeight() && levelReader.getBrightness(LightLayer.BLOCK, blockPos) < 10) {
                BlockState blockstate = levelReader.getBlockState(blockPos);
                if (blockstate.getBlock() == Blocks.SNOW || blockstate.getBlock() == Blocks.GRASS || blockstate.is(BlockTags.SMALL_FLOWERS)) {
                    return true;
                }
            }
            return false;
        }
    }

}
