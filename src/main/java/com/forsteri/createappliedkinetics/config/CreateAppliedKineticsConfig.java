package com.forsteri.createappliedkinetics.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CreateAppliedKineticsConfig {
    public static final ForgeConfigSpec.BooleanValue OVERWRITE_AE2_RECIPES;
    public static final ForgeConfigSpec SPEC;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        OVERWRITE_AE2_RECIPES =  builder
                .comment("If AE2 Inscriber recipes should be remove and replaced with Create Sequenced Assembly recipes",
                        "instead. This also removes the inscriber crafting recipe, and the recipes for crushing various",
                        "things in the inscriber as Create has compatibility for these built in.")
                .define("overwrite_ae2_recipes", true);

        SPEC = builder.build();
    }
}
