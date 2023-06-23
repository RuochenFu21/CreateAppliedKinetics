package org.forsteri.createappliedkinetics.content;

import appeng.api.config.Actionable;
import appeng.api.networking.IGrid;
import appeng.blockentity.networking.EnergyAcceptorBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class EnergyProviderBlockEntity extends KineticBlockEntity {
    public EnergyProviderBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState blockState) {
        super(blockEntityType, pos, blockState);
    }

    @Override
    public void tick() {
        assert getLevel() != null;

        EnergyAcceptorBlockEntity blockEntity = ((EnergyAcceptorBlockEntity) getLevel().getBlockEntity(worldPosition.relative(getBlockState().getValue(EnergyProviderBlock.FACING).getOpposite())));

        if (blockEntity == null)
            return;

        IGrid grid = blockEntity.getMainNode().getGrid();
        if (grid != null) {
            grid.getEnergyService().injectPower(Math.abs(getSpeed()) * 2, Actionable.MODULATE);
        } else {
            blockEntity.injectAEPower(Math.abs(getSpeed()) * 2, Actionable.MODULATE);
        }

        super.tick();
    }
}
