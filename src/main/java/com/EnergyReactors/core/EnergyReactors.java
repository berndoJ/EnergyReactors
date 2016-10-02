package com.EnergyReactors.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;

import com.EnergyReactors.core.blocks.BlockGeneratorRedstone;
import com.EnergyReactors.core.blocks.TileEntityGeneratorRedstone;
import com.EnergyReactors.core.proxies.CommonProxy;
import com.EnergyReactors.handlers.GuiHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

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
    
	//Instance of this Class if needed
	@Instance(value=MODID)
	public static EnergyReactors instance;
	
	//Add the Proxies
	@SidedProxy(clientSide = "com.EnergyReactors.core.proxies.ClientProxy", serverSide = "com.EnergyReactors.core.proxies.CommonProxy")
	public static CommonProxy proxy;
    
    //Creative Tab Field
    public static final CreativeTabs modTab = new CreativeTabs("tabEnergyReactors") {
		@Override
		public Item getTabIconItem() {
			return Items.redstone;
		}
	};
	
    //Block Fields
    public static final BlockGeneratorRedstone blockGeneratorRedstone = new BlockGeneratorRedstone();
    
    //Initialization-Events
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	this.registerModBlocks();
    }
    
    @EventHandler
    public void load(FMLInitializationEvent event) {
    	proxy.registerRenderers();
    	NetworkRegistry.INSTANCE.registerGuiHandler(this.instance, new GuiHandler());
    	this.registerCorrespondingTileEntities();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	
    }
    
    //Registration Methods
    
    /**
     * Registers all Blocks added by the Mod.
     */
    private void registerModBlocks(){
    	GameRegistry.registerBlock(blockGeneratorRedstone, "GeneratorRedstone");
    }
    
    /**
     * Registers all Tile Entities
     */
    private void registerCorrespondingTileEntities(){
    	GameRegistry.registerTileEntity(TileEntityGeneratorRedstone.class, "generator_redstone");
    }
    

}
