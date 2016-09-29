package com.EnergyReactors.core;

import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Main Class of EnergyReactors Mod
 * @author Johannes
 *
 */
@Mod(modid = EnergyReactors.MODID, version = EnergyReactors.VERSION)
public class EnergyReactors {
	
	//Mod-Constants
    public static final String MODID = "energyreactors";
    public static final String VERSION = "1.0_1.7.10";
    
    //Initialization-Events
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
		
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	
    }
    

}
