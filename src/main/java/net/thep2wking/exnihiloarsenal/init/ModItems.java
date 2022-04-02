package net.thep2wking.exnihiloarsenal.init;

import cofh.redstonearsenal.init.RSAItems;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.thep2wking.exnihiloarsenal.ExNihiloArsenal;
import net.thep2wking.exnihiloarsenal.content.FluxCrookItem;
import net.thep2wking.exnihiloarsenal.content.FluxSledgeHammerItem;

public class ModItems 
{
	//deferred register
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExNihiloArsenal.MODID);
	
	//hammer
	public static final RegistryObject<Item> FLUX_SLEDGE_HAMMER = ITEMS.register("flux_sledge_hammer", 
			() -> new FluxSledgeHammerItem(RSAItems.MATERIAL_FLUX_METAL, 1, (float) 0.5, 800000, 10000, new Item.Properties().rarity(Rarity.UNCOMMON).tab(ModTabs.TAB)));
	//crook
	public static final RegistryObject<Item> FLUX_CROOK = ITEMS.register("flux_crook", 
			() -> new FluxCrookItem(RSAItems.MATERIAL_FLUX_METAL, 1, (float) 0.5, 800000, 10000, new Item.Properties().rarity(Rarity.UNCOMMON).tab(ModTabs.TAB)));

	public static void register(IEventBus enevtBus)
	{
		ITEMS.register(enevtBus);
		
		ExNihiloArsenal.LOGGER.info("Registerd Items for " + ExNihiloArsenal.MODID + "!");
	}
}