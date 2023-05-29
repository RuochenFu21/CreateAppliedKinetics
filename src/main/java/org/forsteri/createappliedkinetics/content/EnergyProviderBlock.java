package org.forsteri.createappliedkinetics.content;

import appeng.block.networking.EnergyAcceptorBlock;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.forsteri.createappliedkinetics.entry.Registration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EnergyProviderBlock extends DirectionalKineticBlock implements IBE<EnergyProviderBlockEntity> {
    public EnergyProviderBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING)
                .getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(FACING);
    }

    @Override
    public Class<EnergyProviderBlockEntity> getBlockEntityClass() {
        return EnergyProviderBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends EnergyProviderBlockEntity> getBlockEntityType() {
        return Registration.energyProviderBlockEntity.get();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canSurvive(BlockState p_60525_, LevelReader p_60526_, BlockPos p_60527_) {
        return p_60526_.getBlockState(p_60527_.relative(p_60525_.getValue(FACING).getOpposite())).getBlock() instanceof EnergyAcceptorBlock;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState p_60541_, @NotNull Direction p_60542_, @NotNull BlockState p_60543_, @NotNull LevelAccessor p_60544_, @NotNull BlockPos p_60545_, @NotNull BlockPos p_60546_) {
        return canSurvive(p_60541_, p_60544_, p_60545_) ? super.updateShape(p_60541_, p_60542_, p_60543_, p_60544_, p_60545_, p_60546_) : Blocks.AIR.defaultBlockState();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (super.getStateForPlacement(context) == null) {
            return null;
        }

        if (canSurvive(Objects.requireNonNull(super.getStateForPlacement(context)), context.getLevel(), context.getClickedPos())) {
            return super.getStateForPlacement(context);
        }

        List<Direction> beside = new ArrayList<>();

        switch (Objects.requireNonNull(super.getStateForPlacement(context)).getValue(FACING).getAxis()) {
            case Y -> {
                beside.add(Direction.NORTH);
                beside.add(Direction.SOUTH);
                beside.add(Direction.EAST);
                beside.add(Direction.WEST);
            }
            case X -> {
                beside.add(Direction.UP);
                beside.add(Direction.DOWN);
                beside.add(Direction.EAST);
                beside.add(Direction.WEST);
            }
            case Z -> {
                beside.add(Direction.NORTH);
                beside.add(Direction.SOUTH);
                beside.add(Direction.UP);
                beside.add(Direction.DOWN);
            }
        }

        for (Direction direction : beside) {
            if (canSurvive(Objects.requireNonNull(super.getStateForPlacement(context)).setValue(FACING, direction), context.getLevel(), context.getClickedPos())) {
                return Objects.requireNonNull(super.getStateForPlacement(context)).setValue(FACING, direction);
            }
        }

        return canSurvive(Objects.requireNonNull(super.getStateForPlacement(context)).setValue(FACING, Objects.requireNonNull(super.getStateForPlacement(context)).getValue(FACING).getOpposite()), context.getLevel(), context.getClickedPos()) ? Objects.requireNonNull(super.getStateForPlacement(context)).setValue(FACING, Objects.requireNonNull(super.getStateForPlacement(context)).getValue(FACING).getOpposite()) : null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return AllShapes.CASING_11PX.get(state.getValue(FACING));
    }
}
