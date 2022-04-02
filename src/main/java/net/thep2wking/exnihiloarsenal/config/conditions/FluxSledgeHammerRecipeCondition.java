package net.thep2wking.exnihiloarsenal.config.conditions;

import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.thep2wking.exnihiloarsenal.ExNihiloArsenal;
import net.thep2wking.exnihiloarsenal.config.ExNihiloArsenalConfig;

public class FluxSledgeHammerRecipeCondition implements ICondition
{ 
	private static final ResourceLocation ID = new ResourceLocation(ExNihiloArsenal.MODID, "flux_sledge_hammer_recipe");

	@Override
	public ResourceLocation getID() 
	{
		return ID;
	}

	@Override
	public boolean test() 
	{
		return ExNihiloArsenalConfig.flux_sledge_hammer_recipe.get();
	}

	public static class Serializer implements IConditionSerializer<FluxSledgeHammerRecipeCondition> 
	{
		public static final FluxSledgeHammerRecipeCondition.Serializer INSTANCE = new FluxSledgeHammerRecipeCondition.Serializer();

		public void write(JsonObject json, FluxSledgeHammerRecipeCondition value) 
		{

		}

		public FluxSledgeHammerRecipeCondition read(JsonObject json) 
		{
			return new FluxSledgeHammerRecipeCondition();
		}

		public ResourceLocation getID() 
		{
			return FluxSledgeHammerRecipeCondition.ID;
		}
	}
}