package com.forsteri.createappliedkinetics.entry;

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
import com.forsteri.createappliedkinetics.CreateAppliedKinetics;
import com.forsteri.createappliedkinetics.content.energyProvider.EnergyProviderBlock;
import com.forsteri.createappliedkinetics.content.energyProvider.EnergyProviderBlockEntity;
import com.forsteri.createappliedkinetics.content.meProxy.MEProxyBlock;
import com.forsteri.createappliedkinetics.content.meProxy.MEProxyBlockEntity;
import com.forsteri.createappliedkinetics.content.meProxy.MEProxyBlockItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockBehaviour;
import com.forsteri.createappliedkinetics.content.energyProvider.EnergyProviderBlockItem;
import org.jetbrains.annotations.NotNull;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

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
        CreateAppliedKinetics.REGISTERATE.addRawLang("itemGroup.createappliedkinetics", "Create Applied Kinetics");
        CreateAppliedKinetics.REGISTERATE.addRawLang("tooltip.createappliedkinetics.energy_provider", "Provides energy to AE's §fEnergy Acceptor");
        CreateAppliedKinetics.REGISTERATE.addRawLang("tooltip.createappliedkinetics.me_proxy", "A proxy to access §fAE2 §rnetwork's item/fluid");
    }

    private static void sequencedIngredient(String name) {
        CreateAppliedKinetics.REGISTERATE.item(name, SequencedAssemblyItem::new)
                .register();
    }

    private static ItemEntry<SequencedAssemblyItem> sequencedIngredient(String name, ResourceLocation model) {
        return CreateAppliedKinetics.REGISTERATE.item(name, SequencedAssemblyItem::new)
                .model((c, p) -> p.withExistingParent(c.getName(), model))
                .register();
    }

    public static BlockEntry<EnergyProviderBlock> energyProviderBlock = CreateAppliedKinetics.REGISTERATE
            .setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE)
            .andThen(TooltipModifier.mapNull(KineticStats.create(item))))
            .creativeModeTab(() -> Registration.TAB).block("energy_provider", EnergyProviderBlock::new)
            .blockstate(BlockStateGen.directionalBlockProvider(false))
            .properties(BlockBehaviour.Properties::noOcclusion)
            .transform(BlockStressDefaults.setImpact(4.0))
            .item(EnergyProviderBlockItem::new)
            .transform(customItemModel())
            .register();

    public static BlockEntityEntry<EnergyProviderBlockEntity> energyProviderBlockEntity = CreateAppliedKinetics.REGISTERATE.blockEntity("energy_provider", EnergyProviderBlockEntity::new)
            .instance(() -> HalfShaftInstance::new)
            .renderer(() -> ShaftRenderer::new)
            .validBlock(energyProviderBlock)
            .register();

    public static BlockEntry<MEProxyBlock> meProxyBlock = CreateAppliedKinetics.REGISTERATE
            .block("me_proxy", MEProxyBlock::new)
            .lang("ME Proxy")
            .properties(BlockBehaviour.Properties::noOcclusion)
            .item(MEProxyBlockItem::new)
            .build()
            .register();

    public static BlockEntityEntry<MEProxyBlockEntity> meProxyBlockEntity = CreateAppliedKinetics.REGISTERATE.blockEntity("me_proxy", MEProxyBlockEntity::new)
            .validBlock(meProxyBlock)
            .onRegister(blockEntityType -> meProxyBlock.get().setBlockEntity(MEProxyBlockEntity.class, blockEntityType, (p_155253_, p_155254_, p_155255_, p_155256_) -> {}, (p_155253_, p_155254_, p_155255_, p_155256_) -> {}))
            .register();

    public static CreativeModeTab TAB = new CreativeModeTab(CreateAppliedKinetics.MODID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(energyProviderBlock.get().asItem());
        }
    };

    public static void register(){}
}
