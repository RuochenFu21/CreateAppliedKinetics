package com.forsteri.createappliedkinetics.content.energyProvider;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnergyProviderBlockItem extends BlockItem {
    public EnergyProviderBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack p_40572_, @Nullable Level p_40573_, @NotNull List<Component> p_40574_, @NotNull TooltipFlag p_40575_) {
        super.appendHoverText(p_40572_, p_40573_, p_40574_, p_40575_);

        p_40574_.add(Component.translatable("tooltip.createappliedkinetics.energy_provider").setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
    }
}
