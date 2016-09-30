package com.EnergyReactors.core.blocks;

import java.util.Random;

import com.EnergyReactors.core.EnergyReactors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
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
	public TileEntity createNewTileEntity(World world, int par2) {
		return new TileEntityGeneratorRedstone();
	}
	
	Random rand;
	
	public void breakBlock(World world, int x, int y, int z, Block oldblock, int oldMetadata) {
		TileEntityGeneratorRedstone tileentity = (TileEntityGeneratorRedstone) world.getTileEntity(x, y, z);

		if(tileentity != null) {
		for(int i = 0; i < tileentity.getSizeInventory(); i++) {
		ItemStack itemstack = tileentity.getStackInSlot(i);

		if(itemstack != null) {
		float f = this.rand.nextFloat() * 0.8F + 0.1F;
		float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
		float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

		while(itemstack.stackSize > 0) {
		int j = this.rand.nextInt(21) + 10;

		if(j > itemstack.stackSize) {
		j = itemstack.stackSize;
		}

		itemstack.stackSize -= j;

		EntityItem item = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

		if(itemstack.hasTagCompound()) {
		item.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
		}

		world.spawnEntityInWorld(item);
		}
		}
		}

		world.func_147453_f(x, y, z, oldblock);
		}

		super.breakBlock(world, x, y, z, oldblock, oldMetadata);
		}

	

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		if (stack.hasDisplayName()) {
			((TileEntityGeneratorRedstone) world.getTileEntity(x, y, z)).setCustomName(stack.getDisplayName());
		}
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
	}
	
}
