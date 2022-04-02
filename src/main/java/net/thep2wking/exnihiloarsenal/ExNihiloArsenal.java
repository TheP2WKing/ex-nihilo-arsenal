package net.thep2wking.exnihiloarsenal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.thep2wking.exnihiloarsenal.config.ExNihiloArsenalConfig;
import net.thep2wking.exnihiloarsenal.config.conditions.ModConditions;
import net.thep2wking.exnihiloarsenal.init.ModItems;

@Mod(ExNihiloArsenal.MODID)
public class ExNihiloArsenal
{
	public static final String MODID = "exnihiloarsenal";
	public static final String NAME = "Ex Nihilo: Arsenal";
	public static final String VERSION = "1.16.5-1.1.0";
	
	public static ExNihiloArsenal INSTANCE;
	
    public static final Logger LOGGER = LogManager.getLogger();

    public ExNihiloArsenal() 
    {
    	ExNihiloArsenalConfig.init();
    	
    	IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	
    	eventBus.register(new ModConditions());
    	
    	ModItems.register(eventBus);
    	
    	eventBus.addListener(this::onCommonSetup);
    	eventBus.addListener(this::onClientSetup);
    	eventBus.addListener(this::onEnqueueSetup);
    	eventBus.addListener(this::onloadComplete);
        
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void onCommonSetup(final FMLCommonSetupEvent event)
    {
    	LOGGER.info("Registerd Common Setup for " + MODID + "!");
    }

	private void onClientSetup(final FMLClientSetupEvent event) 
    {
    	LOGGER.info("Registerd Client Setup for " + MODID + "!");
    }
	
	private void onEnqueueSetup(final InterModEnqueueEvent event)
	{
		LOGGER.info("Registerd IMC Setup for " + MODID + "!");
	}
	
	private void onloadComplete(final FMLLoadCompleteEvent event) 
	{
    	LOGGER.info(NAME + " " + VERSION + " successfully loaded!");
	}		
}