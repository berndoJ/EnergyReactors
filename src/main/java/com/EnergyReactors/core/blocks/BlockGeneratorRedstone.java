package com.EnergyReactors.core.blocks;

import java.util.Random;

import com.EnergyReactors.core.EnergyReactors;
import com.EnergyReactors.handlers.GuiHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGeneratorRedstone extends BlockContainer {

	public BlockGeneratorRedstone() {
		super(Material.iron);
		this.setStepSound(soundTypeMetal);
		this.setBlockName("GeneratorRedstone");
		this.setBlockTextureName(EnergyReactors.MODID + ":generator_redstone");
		this.setHardness(3F);
		this.setResistance(4F);
		this.setCreativeTab(EnergyReactors.modTab);
	}

	
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote){
			player.openGui(EnergyReactors.instance, GuiHandler.GENERATOR_REDSTONE_ID, world, x, y, z);
		}
		return true;
	}



	@Override
	public TileEntity createNewTileEntity(World world, int par1) {
		return new TileEntityGeneratorRedstone();
	}


	
	
	
}
