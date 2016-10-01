package com.EnergyReactors.handlers;

import com.EnergyReactors.core.blocks.TileEntityGeneratorRedstone;
import com.EnergyReactors.core.container.ContainerGeneratorRedstone;
import com.EnergyReactors.core.gui.GuiGeneratorRedstone;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	//Id's of the diffrent GUI's
	public static final int GENERATOR_REDSTONE_ID = 0;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID){
		case GENERATOR_REDSTONE_ID:
			return new ContainerGeneratorRedstone(player.inventory, (TileEntityGeneratorRedstone) world.getTileEntity(x, y, z));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID){
		case GENERATOR_REDSTONE_ID:
			return new GuiGeneratorRedstone(player.inventory, (TileEntityGeneratorRedstone) world.getTileEntity(x, y, z));
		}
		return null;
	}

}
