package com.forsteri.createappliedkinetics.content.meProxy;

import appeng.api.networking.GridHelper;
import appeng.block.AEBaseEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MEProxyBlock extends AEBaseEntityBlock<MEProxyBlockEntity> {
    public MEProxyBlock(Properties props) {
        super(props);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!level.isClientSide()) {
            MEProxyBlockEntity be = getBlockEntity(level, pos);
            if (be != null) {
                GridHelper.onFirstTick(be, (nbe) -> nbe.scheduleReconnect());
            }
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        if (!level.isClientSide()) {
            MEProxyBlockEntity be = getBlockEntity(level, pos);
            BlockEntity neighborBe = level.getBlockEntity(neighborPos);
            if (be != null && neighborBe != null) {
                GridHelper.onFirstTick(neighborBe, (nbe) -> be.scheduleReconnect());
            }
        }
    }
}