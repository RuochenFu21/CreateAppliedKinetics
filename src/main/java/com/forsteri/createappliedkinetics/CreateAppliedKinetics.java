package com.forsteri.createappliedkinetics;

import com.forsteri.createappliedkinetics.config.CreateAppliedKineticsConfig;
import com.forsteri.createappliedkinetics.content.meProxy.MEProxyBlockEntity;
import com.forsteri.createappliedkinetics.entry.Registration;
import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreateAppliedKinetics.MODID)
public class CreateAppliedKinetics {

    // Directly reference a slf4j logger
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String MODID = "createappliedkinetics";

    public CreateAppliedKinetics(net.neoforged.bus.api.IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, CreateAppliedKineticsConfig.SPEC);

//        NeoForge.EVENT_BUS.register(this);
        CreateAppliedKinetics.REGISTERATE.defaultCreativeTab("createappliedkinetics");
        REGISTERATE.registerEventListeners(modEventBus);

        Registration.register(modEventBus);
    }


    public static final CreateRegistrate REGISTERATE = CreateRegistrate.create(MODID);

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void registerCapabilitiesEvent(RegisterCapabilitiesEvent event) {
            MEProxyBlockEntity.registerCapabilities(event);
        }
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
