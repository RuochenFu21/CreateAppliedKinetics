package com.forsteri.createappliedkinetics.config;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

import static com.forsteri.createappliedkinetics.CreateAppliedKinetics.asResource;
import static com.forsteri.createappliedkinetics.config.CreateAppliedKineticsConfig.OVERWRITE_AE2_RECIPES;

public class OverwriteAE2RecipesCondition implements ICondition {

    private static final OverwriteAE2RecipesCondition INSTANCE = new OverwriteAE2RecipesCondition();

    private static final ResourceLocation NAME = asResource( "ae2_overwrite");
    @Override
    public ResourceLocation getID() {
        return NAME;
    }

    @Override
    public boolean test(IContext context) {
        return OVERWRITE_AE2_RECIPES.get();
    }

    public static void register() {
        CraftingHelper.register(Serializer.INSTANCE);
    }

    public static class Serializer implements IConditionSerializer<OverwriteAE2RecipesCondition> {

        private static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, OverwriteAE2RecipesCondition value) {}

        @Override
        public OverwriteAE2RecipesCondition read(JsonObject json) {
            return OverwriteAE2RecipesCondition.INSTANCE;
        }

        @Override
        public ResourceLocation getID() {
            return NAME;
        }
    }
}
