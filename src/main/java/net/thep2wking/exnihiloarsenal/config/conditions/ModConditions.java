package net.thep2wking.exnihiloarsenal.config.conditions;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.thep2wking.exnihiloarsenal.ExNihiloArsenal;

public class ModConditions
{
	@SubscribeEvent
	public void onRegisterSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) 
	{
		//recipes
		CraftingHelper.register(FluxSledgeHammerRecipeCondition.Serializer.INSTANCE);
		CraftingHelper.register(FluxCrookRecipeCondition.Serializer.INSTANCE);
		
		ExNihiloArsenal.LOGGER.info("Registerd Serializers for " + ExNihiloArsenal.MODID + "!");
	}
}