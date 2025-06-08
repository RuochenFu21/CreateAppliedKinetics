package com.forsteri.createappliedkinetics.content.meProxy;

import appeng.api.inventories.InternalInventory;
import appeng.blockentity.grid.AENetworkedPoweredBlockEntity;
import com.forsteri.createappliedkinetics.entry.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class MEProxyBlockEntity extends AENetworkedPoweredBlockEntity {
    public MEProxyBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState blockState) {
        super(blockEntityType, pos, blockState);
        this.getMainNode().setIdlePowerUsage(0.0F);
    }

    @Override
    public InternalInventory getInternalInventory() {
        return InternalInventory.empty();
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                Registration.meProxyBlockEntity.get(),
                (be, context) -> new MEProxyInventoryHandler(be)
        );
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                Registration.meProxyBlockEntity.get(),
                (be, context) -> new MEProxyInventoryHandler(be)
        );
    }
}
