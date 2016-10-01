package com.EnergyReactors.core.container.slot;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotGeneratorRedstone extends Slot {

	public static boolean isItemBlock;
	
	public SlotGeneratorRedstone(IInventory inv, int par2, int par3, int par4) {
		super(inv, par2, par3, par4);
	}
	
	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return isItemValidForSlot(itemstack);
	}
	
	public static boolean isItemValidForSlot(ItemStack itemstack){
		if(itemstack != null){
			if(itemstack.getItem() == Items.redstone){
				isItemBlock = false;
				return true;
			} else if (itemstack.getItem() == Item.getItemFromBlock(Blocks.redstone_block)){
				isItemBlock = true;
				return true;
			} else {
				return false;
			}
		} else return false;
	}
	
	
}
