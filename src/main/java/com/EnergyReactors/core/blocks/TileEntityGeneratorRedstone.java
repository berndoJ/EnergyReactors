package com.EnergyReactors.core.blocks;

import com.EnergyReactors.core.container.slot.SlotGeneratorRedstone;

import cofh.api.energy.IEnergyProvider;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityGeneratorRedstone extends TileEntity implements IInventory, IEnergyProvider {

	private ItemStack[] INVENTORY;
	private String customName;
	
	private int inventorySlotSize = 1;
	
	private final static int maxRF = 100000;
	private final int productionRF = 20;
	private static int currentRF;
	private final int burnTimeDust = 40; //In ticks
	private final int burnTimeBlock = 360; //In ticks
	private int currentBurnTime;
	
	public TileEntityGeneratorRedstone() {
		this.INVENTORY = new ItemStack[this.getSizeInventory()];
	}
	
	public String getCustomName(){
		return customName;
	}
	
	public void setCustomName(String s){
		this.customName = s;
	}

	@Override
	public int getSizeInventory() {
		return this.inventorySlotSize;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
	    if (index < 0 || index >= this.getSizeInventory())
	        return null;
	    return this.INVENTORY[index];
	} 

	@Override
	public ItemStack decrStackSize(int index, int count) {
	    if (this.getStackInSlot(index) != null) {
	        ItemStack itemstack;

	        if (this.getStackInSlot(index).stackSize <= count) {
	            itemstack = this.getStackInSlot(index);
	            this.setInventorySlotContents(index, null);
	            this.markDirty();
	            return itemstack;
	        } else {
	            itemstack = this.getStackInSlot(index).splitStack(count);

	            if (this.getStackInSlot(index).stackSize <= 0) {
	                this.setInventorySlotContents(index, null);
	            } else {
	                this.setInventorySlotContents(index, this.getStackInSlot(index));
	            }
	            this.markDirty();
	            return itemstack;
	        }
	    } else {
	        return null;
	    }
	}
	


	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		if (index < 0 || index >= this.getSizeInventory())
	        return null;
	    return this.INVENTORY[index];
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
	    if (index < 0 || index >= this.getSizeInventory())
	        return;

	    if (stack != null && stack.stackSize > this.getInventoryStackLimit())
	        stack.stackSize = this.getInventoryStackLimit();
	        
	    if (stack != null && stack.stackSize == 0)
	        stack = null;

	    this.INVENTORY[index] = stack;
	    this.markDirty();
	}


	@Override
	public String getInventoryName() {
		return "Redstone Generator";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && !this.customName.equals("");
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64;
	}

	//Not Needed
	@Override
	public void openInventory() {  }

	//Not Needed
	@Override
	public void closeInventory() {  }

	@Override
	public boolean isItemValidForSlot(int index, ItemStack itemstack) {
		return SlotGeneratorRedstone.isItemValidForSlot(itemstack);
	}
	
	public void clear(){
		for(int i = 0; i < this.getSizeInventory(); i++)
			this.setInventorySlotContents(i, null);
	}
	
	public ItemStack removeStackFromSlot(int index) {
	    ItemStack stack = this.getStackInSlot(index);
	    this.setInventorySlotContents(index, null);
	    return stack;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
	    super.writeToNBT(nbt);

	    NBTTagList list = new NBTTagList();
	    for (int i = 0; i < this.getSizeInventory(); ++i) {
	        if (this.getStackInSlot(i) != null) {
	            NBTTagCompound stackTag = new NBTTagCompound();
	            stackTag.setByte("Slot", (byte) i);
	            this.getStackInSlot(i).writeToNBT(stackTag);
	            list.appendTag(stackTag);
	        }
	    }
	    nbt.setTag("Items", list);
	    nbt.setInteger("EnergyStored", this.currentRF);
	    nbt.setInteger("CurrentBurnTime", this.currentBurnTime);

	    if (this.hasCustomInventoryName()) {
	        nbt.setString("CustomName", this.getCustomName());
	    }
	}


	@Override
	public void readFromNBT(NBTTagCompound nbt) {
	    super.readFromNBT(nbt);

	    NBTTagList list = nbt.getTagList("Items", 10);
	    for (int i = 0; i < list.tagCount(); ++i) {
	        NBTTagCompound stackTag = list.getCompoundTagAt(i);
	        int slot = stackTag.getByte("Slot") & 255;
	        this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
	    }
	    currentRF = nbt.getInteger("EnergyStored");
	    currentBurnTime = nbt.getInteger("CurrentBurnTime");

	    if (nbt.hasKey("CustomName", 8)) {
	        this.setCustomName(nbt.getString("CustomName"));
	    }
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if(currentRF >= maxExtract){
			currentRF -= maxExtract;
			return maxExtract;
		} else {
			currentRF = 0;
			return currentRF;
		}
	}
	
	@Override
	public int getEnergyStored(ForgeDirection from) {
		return this.currentRF;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return this.maxRF;
	}
	
	public static int getMaxRF(){
		return maxRF;
	}
	
	public static int getCurrentRF(){
		return currentRF;
	}

	/*@Override
	public void tick() {
		if(this.worldObj.isRemote){
			generateRF();
		}
	}*/

	private void generateRF() {
		if(SlotGeneratorRedstone.isItemValidForSlot(this.getStackInSlot(0)) && this.currentBurnTime == 0){
			if(!SlotGeneratorRedstone.isItemBlock){
				this.currentBurnTime = this.burnTimeDust;
			} else {
				this.currentBurnTime = this.burnTimeBlock;
			}
			this.decrStackSize(0, 1);
			this.markDirty();
		}
		if (this.currentBurnTime > 0){
			this.currentBurnTime -= 1;
			if(!(this.currentRF + this.productionRF > this.maxRF)){
				this.currentRF += this.productionRF;
			} else {
				this.currentRF = this.maxRF;			}
			this.markDirty();
		}
	}	
	
	@Override
	public void updateEntity() {
		if(this.worldObj.isRemote){
			generateRF();
		}
	}
}
