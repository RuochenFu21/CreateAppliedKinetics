package org.forsteri.createappliedkinetics.entry;

import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.forsteri.createappliedkinetics.CreateAppliedKinetics;
import org.forsteri.createappliedkinetics.content.EnergyProviderBlock;
import org.forsteri.createappliedkinetics.content.EnergyProviderBlockEntity;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static org.forsteri.createappliedkinetics.CreateAppliedKinetics.REGISTERATE;

@SuppressWarnings("unused")
public class Registration {

    static {
        sequencedIngredient("incomplete_printed_calculation_circuit");
        sequencedIngredient("incomplete_printed_engineering_circuit");
        sequencedIngredient("incomplete_printed_logic_circuit");
        sequencedIngredient("incomplete_silicon_print");
    }

    static {
        sequencedIngredient("incomplete_calculation_processor_press");
        sequencedIngredient("incomplete_engineering_processor_press");
        sequencedIngredient("incomplete_logic_processor_press");
        sequencedIngredient("incomplete_silicon_press");
    }

    static {
        sequencedIngredient("incomplete_calculation_processor");
        sequencedIngredient("incomplete_engineering_processor");
        sequencedIngredient("incomplete_logic_processor");
    }

    static {
        REGISTERATE.addRawLang("itemGroup.createappliedkinetics", "Create Applied Kinetics");
        REGISTERATE.addRawLang("tooltip.createappliedkinetics.energy_provider", "§7Provides energy to AE's §rEnergy Acceptor");
    }

    private static ItemEntry<SequencedAssemblyItem> sequencedIngredient(String name) {
        return REGISTERATE.item(name, SequencedAssemblyItem::new)
                .register();
    }

    private static ItemEntry<SequencedAssemblyItem> sequencedIngredient(String name, ResourceLocation model) {
        return REGISTERATE.item(name, SequencedAssemblyItem::new)
                .model((c, p) -> p.withExistingParent(c.getName(), model))
                .register();
    }

    public static BlockEntry<EnergyProviderBlock> energyProviderBlock = REGISTERATE
            .setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE)
            .andThen(context -> context.getToolTip().add(new TranslatableComponent("tooltip.createappliedkinetics.energy_provider")))
            .andThen(TooltipModifier.mapNull(KineticStats.create(item))))
            .creativeModeTab(() -> Registration.TAB).block("energy_provider", EnergyProviderBlock::new)
            .blockstate(BlockStateGen.directionalBlockProvider(false))
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(BlockStressDefaults.setImpact(4.0))
            .item()
            .transform(customItemModel())
            .register();

    public static BlockEntityEntry<EnergyProviderBlockEntity> energyProviderBlockEntity = REGISTERATE.blockEntity("energy_provider", EnergyProviderBlockEntity::new)
            .instance(() -> HalfShaftInstance::new)
            .renderer(() -> ShaftRenderer::new)
            .validBlock(energyProviderBlock)
            .register();

    public static CreativeModeTab TAB = new CreativeModeTab(CreateAppliedKinetics.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(energyProviderBlock.get().asItem());
        }
    };

    public static void register(){}
}
