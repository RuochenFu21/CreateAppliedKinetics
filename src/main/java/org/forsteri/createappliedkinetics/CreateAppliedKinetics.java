package org.forsteri.createappliedkinetics;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.forsteri.createappliedkinetics.entry.Registration;
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

        REGISTERATE.registerEventListeners(FMLJavaModLoadingContext.get()
                .getModEventBus());

        Registration.register();
    }


    public static final CreateRegistrate REGISTERATE = CreateRegistrate.create(MODID);
}
