package com.forsteri.createappliedkinetics.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CreateAppliedKineticsConfig {
    public static final ModConfigSpec.BooleanValue OVERWRITE_AE2_RECIPES;
    public static final ModConfigSpec SPEC;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        OVERWRITE_AE2_RECIPES =  builder
                .comment("If AE2 Inscriber recipes should be remove and replaced with Create Sequenced Assembly recipes",
                        "instead. This also removes the inscriber crafting recipe, and the recipes for crushing various",
                        "things in the inscriber as Create has compatibility for these built in.")
                .define("overwrite_ae2_recipes", true);

        SPEC = builder.build();
    }
}
