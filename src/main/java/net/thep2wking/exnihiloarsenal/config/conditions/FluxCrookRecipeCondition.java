package net.thep2wking.exnihiloarsenal.config.conditions;

import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.thep2wking.exnihiloarsenal.ExNihiloArsenal;
import net.thep2wking.exnihiloarsenal.config.ExNihiloArsenalConfig;

public class FluxCrookRecipeCondition implements ICondition
{ 
	private static final ResourceLocation ID = new ResourceLocation(ExNihiloArsenal.MODID, "flux_crook_recipe");

	@Override
	public ResourceLocation getID() 
	{
		return ID;
	}

	@Override
	public boolean test() 
	{
		return ExNihiloArsenalConfig.flux_crook_recipe.get();
	}

	public static class Serializer implements IConditionSerializer<FluxCrookRecipeCondition> 
	{
		public static final FluxCrookRecipeCondition.Serializer INSTANCE = new FluxCrookRecipeCondition.Serializer();

		public void write(JsonObject json, FluxCrookRecipeCondition value) 
		{

		}

		public FluxCrookRecipeCondition read(JsonObject json) 
		{
			return new FluxCrookRecipeCondition();
		}

		public ResourceLocation getID() 
		{
			return FluxCrookRecipeCondition.ID;
		}
	}
}