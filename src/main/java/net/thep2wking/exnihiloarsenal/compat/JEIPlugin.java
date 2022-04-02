package net.thep2wking.exnihiloarsenal.compat;

import mezz.jei.api.IModPlugin;

import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.thep2wking.exnihiloarsenal.ExNihiloArsenal;
import net.thep2wking.exnihiloarsenal.init.ModItems;
import novamachina.exnihilosequentia.common.compat.jei.crook.CrookRecipeCategory;
import novamachina.exnihilosequentia.common.compat.jei.hammer.HammerRecipeCategory;

@JeiPlugin
public class JEIPlugin implements IModPlugin
{
	@Override
	public ResourceLocation getPluginUid() 
	{
		return new ResourceLocation(ExNihiloArsenal.MODID, "jei_plugin");
	}
	
	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) 
	{
        registration.addRecipeCatalyst(new ItemStack(ModItems.FLUX_SLEDGE_HAMMER.get().asItem()), HammerRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(ModItems.FLUX_CROOK.get().asItem()), CrookRecipeCategory.UID);
	}
}