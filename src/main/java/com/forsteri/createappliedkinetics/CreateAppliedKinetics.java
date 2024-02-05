package com.forsteri.createappliedkinetics;

import com.forsteri.createappliedkinetics.config.CreateAppliedKineticsConfig;
import com.forsteri.createappliedkinetics.config.OverwriteAE2RecipesCondition;
import com.forsteri.createappliedkinetics.entry.Registration;
import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreateAppliedKinetics.MODID)
public class CreateAppliedKinetics {

    // Directly reference a slf4j logger
    @SuppressWarnings("unused") 
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String MODID = "createappliedkinetics";

    public CreateAppliedKinetics() {
        REGISTERATE.registerEventListeners(FMLJavaModLoadingContext.get()
                .getModEventBus());

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CreateAppliedKineticsConfig.SPEC);

        Registration.register();
    }


    public static final CreateRegistrate REGISTERATE = CreateRegistrate.create(MODID);

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void registerRecipeCondition(RegisterEvent event) {
            if (event.getRegistryKey().equals(ForgeRegistries.Keys.RECIPE_SERIALIZERS)) {
                OverwriteAE2RecipesCondition.register();
            }
        }
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }
}
