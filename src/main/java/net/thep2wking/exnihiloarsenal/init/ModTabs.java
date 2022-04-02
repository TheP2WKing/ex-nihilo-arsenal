package net.thep2wking.exnihiloarsenal.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModTabs 
{
	public static final ItemGroup TAB = new ItemGroup("exnihiloarsenal.tab") 
	{	
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack makeIcon()
		{
			return new ItemStack(ModItems.FLUX_SLEDGE_HAMMER.get());
		}
	};
}