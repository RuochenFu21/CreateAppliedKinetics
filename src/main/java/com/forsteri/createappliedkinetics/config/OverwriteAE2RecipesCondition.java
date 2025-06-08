package com.forsteri.createappliedkinetics.config;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;

import static com.forsteri.createappliedkinetics.config.CreateAppliedKineticsConfig.OVERWRITE_AE2_RECIPES;

public class OverwriteAE2RecipesCondition implements ICondition {
    public static final OverwriteAE2RecipesCondition INSTANCE = new OverwriteAE2RecipesCondition();

    public static MapCodec<OverwriteAE2RecipesCondition> CODEC = MapCodec.unit(INSTANCE).stable();

    private OverwriteAE2RecipesCondition() {}

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }

    @Override
    public String toString() {
        return "ae2_overwrite";
    }

    @Override
    public boolean test(IContext context) {
        return OVERWRITE_AE2_RECIPES.get();
    }
}
