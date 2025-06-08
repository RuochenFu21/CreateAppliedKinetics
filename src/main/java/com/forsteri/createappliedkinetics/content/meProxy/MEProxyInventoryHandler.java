package com.forsteri.createappliedkinetics.content.meProxy;

import appeng.api.config.Actionable;
import appeng.api.networking.IGrid;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEFluidKey;
import appeng.api.stacks.AEItemKey;
import appeng.me.storage.NetworkStorage;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.EmptyFluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class MEProxyInventoryHandler implements IItemHandler, IFluidHandler {
    MEProxyBlockEntity blockEntity;

    public MEProxyInventoryHandler(MEProxyBlockEntity meProxyBlockEntity) {
        this.blockEntity = meProxyBlockEntity;
    }

    private Optional<NetworkStorage> getStorage() {
        IGrid grid = blockEntity.getMainNode().getGrid();
        if (grid == null)
            return Optional.empty();
        return Optional.of((NetworkStorage) grid.getStorageService().getInventory());
    }

    List<AEFluidKey> getFluidKeys() {
        return getStorage().map(storage ->
                storage.getAvailableStacks().keySet().stream().filter(aeKey -> aeKey instanceof AEFluidKey).map(aeKey -> ((AEFluidKey) aeKey)).toList()
        ).orElse(List.of());
    }

    List<AEItemKey> getItemKeys() {
        return getStorage().map(storage ->
                storage.getAvailableStacks().keySet().stream().filter(aeKey -> aeKey instanceof AEItemKey).map(aeKey -> ((AEItemKey) aeKey)).toList()
        ).orElse(List.of());
    }

    @Override
    public int getTanks() {
        return getFluidKeys().size();
    }

    @NotNull
    @Override
    public FluidStack getFluidInTank(int tank) {
        if (getStorage().isEmpty())
            return FluidStack.EMPTY;


        if (tank >= getFluidKeys().size())
            return FluidStack.EMPTY;

        return getFluidKeys().get(tank).toStack(((int) getStorage().orElse(null).extract(getFluidKeys().get(tank), Integer.MAX_VALUE, Actionable.SIMULATE, IActionSource.empty())));
    }

    @Override
    public int getTankCapacity(int tank) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        if (getStorage().isEmpty())
            return false;

        return getStorage().orElse(null).insert(AEFluidKey.of(stack), stack.getAmount(), Actionable.SIMULATE, IActionSource.empty()) > 0;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (getStorage().isEmpty())
            return 0;

        return (int) getStorage().orElse(null).insert(AEFluidKey.of(resource), resource.getAmount(), action == FluidAction.EXECUTE ? Actionable.MODULATE : Actionable.SIMULATE, IActionSource.empty());
    }

    @NotNull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (getStorage().isEmpty())
            return FluidStack.EMPTY;

        FluidStack copied = resource.copy();

        if (copied.getFluid() instanceof EmptyFluid)
            return FluidStack.EMPTY;

        copied.setAmount(
                ((int) getStorage().orElse(null).extract(AEFluidKey.of(resource), resource.getAmount(), action == FluidAction.EXECUTE ? Actionable.MODULATE : Actionable.SIMULATE, IActionSource.empty())));
        return copied.getAmount() > 0 ? copied : FluidStack.EMPTY;
    }

    @NotNull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        FluidStack copied = getFluidInTank(0).copy();

        if (copied.getFluid() instanceof EmptyFluid)
            return FluidStack.EMPTY;

        if (copied.getAmount() > maxDrain)
            copied.setAmount(maxDrain);

        return drain(copied, action);
    }

    @Override
    public int getSlots() {
        return getItemKeys().size() + 16; // Allocate 16 slots for input
    }

    @NotNull
    @Override
    public ItemStack getStackInSlot(int slot) {
        if (getStorage().isEmpty())
            return ItemStack.EMPTY;

        if (slot >= getItemKeys().size())
            return ItemStack.EMPTY;

        return getItemKeys().get(slot).toStack(((int) getStorage().orElse(null).extract(getItemKeys().get(slot), Integer.MAX_VALUE, Actionable.SIMULATE, IActionSource.empty())));
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (getStorage().isEmpty())
            return stack;

        ItemStack copied = stack.copy();

        copied.setCount(copied.getCount() - (int) getStorage().orElse(null).insert(AEItemKey.of(stack), stack.getCount(), simulate ? Actionable.SIMULATE : Actionable.MODULATE, IActionSource.empty()));

        return copied;
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (getStorage().isEmpty())
            return ItemStack.EMPTY;

        ItemStack stackInSlot = getStackInSlot(slot).copy();

        AEItemKey key = AEItemKey.of(stackInSlot);
        if (key == null) return ItemStack.EMPTY;

        stackInSlot.setCount((int) getStorage().orElse(null).extract(key, amount, simulate ? Actionable.SIMULATE : Actionable.MODULATE, IActionSource.empty()));

        return stackInSlot;
    }

    @Override
    public int getSlotLimit(int slot) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return insertItem(slot, stack, true).getCount() == 0;
    }

//    @Override
//    public void setStackInSlot(int slot, ItemStack stack) {
//
//
//    }
}
