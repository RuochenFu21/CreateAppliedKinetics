package org.forsteri.createappliedkinetics.content.meProxy;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEFluidKey;
import appeng.api.stacks.AEItemKey;
import appeng.me.storage.NetworkStorage;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.EmptyFluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MEProxyInventoryHandler implements IItemHandler, IFluidHandler {
    NetworkStorage storage;

    public MEProxyInventoryHandler(NetworkStorage storage) {
        this.storage = storage;
    }

    List<AEFluidKey> getFluidKeys() {
        return storage.getAvailableStacks().keySet().stream().filter(aeKey -> aeKey instanceof AEFluidKey).map(aeKey -> ((AEFluidKey) aeKey)).toList();
    }

    List<AEItemKey> getItemKeys() {
        return storage.getAvailableStacks().keySet().stream().filter(aeKey -> aeKey instanceof AEItemKey).map(aeKey -> ((AEItemKey) aeKey)).toList();
    }

    @Override
    public int getTanks() {
        return getFluidKeys().size();
    }

    @NotNull
    @Override
    public FluidStack getFluidInTank(int tank) {
        if (tank >= getFluidKeys().size())
            return FluidStack.EMPTY;

        return getFluidKeys().get(tank).toStack(((int) storage.extract(getFluidKeys().get(tank), Integer.MAX_VALUE, Actionable.SIMULATE, IActionSource.empty())));
    }

    @Override
    public int getTankCapacity(int tank) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return storage.insert(AEFluidKey.of(stack), stack.getAmount(), Actionable.SIMULATE, IActionSource.empty()) > 0;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return (int) storage.insert(AEFluidKey.of(resource), resource.getAmount(), action == FluidAction.EXECUTE ? Actionable.MODULATE : Actionable.SIMULATE, IActionSource.empty());
    }

    @NotNull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        FluidStack copied = resource.copy();

        if (copied.getFluid() instanceof EmptyFluid)
            return FluidStack.EMPTY;

        copied.setAmount(((int) storage.extract(AEFluidKey.of(resource), resource.getAmount(), action == FluidAction.EXECUTE ? Actionable.MODULATE : Actionable.SIMULATE, IActionSource.empty())));
        return copied.getAmount() > 0 ? copied : FluidStack.EMPTY;
    }

    @NotNull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return drain(getFluidInTank(0), action);
    }

    @Override
    public int getSlots() {
        return getItemKeys().size() + 16; // Allocate 16 slots for input
    }

    @NotNull
    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot >= getItemKeys().size())
            return ItemStack.EMPTY;

        return getItemKeys().get(slot).toStack(((int) storage.extract(getItemKeys().get(slot), 64, Actionable.SIMULATE, IActionSource.empty())));
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        ItemStack copied = stack.copy();

        copied.setCount(copied.getCount() - (int) storage.insert(AEItemKey.of(stack), stack.getCount(), simulate ? Actionable.SIMULATE : Actionable.MODULATE, IActionSource.empty()));

        return copied;
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return storage.extract(AEItemKey.of(getStackInSlot(slot)), amount, simulate ? Actionable.SIMULATE : Actionable.MODULATE, IActionSource.empty()) > 0 ? getStackInSlot(slot) : ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return insertItem(slot, stack, true).getCount() == 0;
    }
}
