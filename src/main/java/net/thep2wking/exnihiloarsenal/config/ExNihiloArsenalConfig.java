package net.thep2wking.exnihiloarsenal.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.thep2wking.exnihiloarsenal.ExNihiloArsenal;

public class ExNihiloArsenalConfig
{
	//flux sledge hammer
    public static BooleanValue flux_sledge_hammer_recipe;
    public static DoubleValue flux_sledge_hammer_destroy_speed_modifier;
    
	//flux crook
    public static BooleanValue flux_crook_recipe;
    public static DoubleValue flux_crook_destroy_speed_modifier;
    
    
    public static void init() 
    {
        Pair<ConfigLoderCommon, ForgeConfigSpec> resources_common_config = new ForgeConfigSpec.Builder().configure(ConfigLoderCommon::new); 
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, resources_common_config.getRight());
        
    	ExNihiloArsenal.LOGGER.info("Registerd Config for " + ExNihiloArsenal.MODID + "!");
    }
    
    public static class ConfigLoderCommon
    {
        public ConfigLoderCommon(ForgeConfigSpec.Builder builder) 
        {
            builder.push("Flux Sledge Hammer");
            flux_sledge_hammer_recipe = builder.comment("Toggle flux sledge hammer recipe [Default: true]").define("enable_recipe", true);
            flux_sledge_hammer_destroy_speed_modifier = builder.comment("Set destroy speed modifier [Default: 1.5]").defineInRange("destroy_speed_modifier", 1.5, 0.5, 10.0);
            builder.pop();
            
            builder.push("Flux Crook");
            flux_crook_recipe = builder.comment("Toggle flux crook recipe. [Default: true]").define("enable_recipe", true);
            flux_crook_destroy_speed_modifier = builder.comment("Set destroy speed modifier [Default: 1.5]").defineInRange("destroy_speed_modifier", 1.5, 0.5, 10.0);
            builder.pop();
        }
    }
}