package com.forsteri.createappliedkinetics;

import com.mojang.logging.LogUtils;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.ModFilePackResources;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.utility.Components;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import com.forsteri.createappliedkinetics.entry.Registration;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreateAppliedKinetics.MODID)
public class CreateAppliedKinetics {

    // Directly reference a slf4j logger
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String MODID = "createappliedkinetics";

    public CreateAppliedKinetics() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        REGISTERATE.registerEventListeners(eventBus);

        Registration.register(eventBus);
    }


    public static final CreateRegistrate REGISTERATE = CreateRegistrate.create(MODID);

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void addPackFinders(AddPackFindersEvent event) {
            if (event.getPackType() == PackType.SERVER_DATA) {
                IModFileInfo modFileInfo = ModList.get().getModFileById(CreateAppliedKinetics.MODID);
                if (modFileInfo == null) {
                    Create.LOGGER.error("Could not find Create Applied Kinetics mod file info; built-in resource packs will be missing!");
                    return;
                }
                IModFile modFile = modFileInfo.getFile();
                event.addRepositorySource(consumer -> {
                    Pack pack = Pack.readMetaAndCreate(
                        CreateAppliedKinetics.asResource("remove_ae_recipes").toString(),
                        Components.literal("Remove AE Recipes"),
                        false,
                        id -> new ModFilePackResources(id, modFile, "datapacks/remove_ae_recipes"),
                        PackType.SERVER_DATA,
                        Pack.Position.TOP,
                        PackSource.DEFAULT);

                    if (pack != null)
                        consumer.accept(pack);
                });
            }
        }
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }
}
