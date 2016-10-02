package com.EnergyReactors.core.blocks;

import com.EnergyReactors.core.container.slot.SlotGeneratorRedstone;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityGeneratorRedstone extends TileEntity implements IInventory, IEnergyProvider {

	private ItemStack[] INVENTORY;
	private String customName;
	
	private int inventorySlotSize = 1;
	
	private final int maxRF = 100000;
	private final int productionRF = 20;
	private int currentRF;
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
		if (INVENTORY[index] != null)
	        return this.INVENTORY[index];
	    return null;
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
	    super.writeToNBT(nbt);
	}


	@Override
	public void readFromNBT(NBTTagCompound nbt) {
	    NBTTagList list = nbt.getTagList("Items", 10);
	    for (int i = 0; i < list.tagCount(); ++i) {
	        NBTTagCompound stackTag = list.getCompoundTagAt(i);
	        int slot = stackTag.getByte("Slot") & 255;
	        this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
	    }
	    currentRF = nbt.getInteger("EnergyStored");
	    currentBurnTime = nbt.getInteger("CurrentBurnTime");
	    super.readFromNBT(nbt);
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
			int RFBefore = currentRF;
			currentRF = 0;
			return RFBefore;
		}
	}
	
	public int extractEnergy(int maxExtract, boolean simulate){
		if(currentRF >= maxExtract){
			currentRF -= maxExtract;
			return maxExtract;
		} else {
			int RFBefore = currentRF;
			currentRF = 0;
			return RFBefore;
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
	
	public int getMaxRF(){
		return maxRF;
	}
	
	public int getCurrentRF(){
		return currentRF;
	}
	
	public int getProductionRate(){
		return productionRF;
	}
	
	public int getCurrentBurnTime(){
		return currentBurnTime;
	}

	private void generateRF() {
		if(SlotGeneratorRedstone.isItemValidForSlot(this.getStackInSlot(0)) && this.currentBurnTime == 0 && !(this.currentRF == maxRF)){
			if(!SlotGeneratorRedstone.isItemBlock){
				this.currentBurnTime = this.burnTimeDust;
			} else {
				this.currentBurnTime = this.burnTimeBlock;
			}
			this.INVENTORY[0].stackSize -= 1;
			if(this.INVENTORY[0].stackSize == 0){
				this.INVENTORY[0] = null;
			}
		}
		if (this.currentBurnTime > 0){
			this.currentBurnTime -= 1;
			if(!(this.currentRF + this.productionRF > this.maxRF)){
				this.currentRF += this.productionRF;
			} else {
				this.currentRF = this.maxRF;
			}
		}
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		this.markDirty();
	}	
	
	@Override
	public void updateEntity() {
		if(!this.worldObj.isRemote){
			this.generateRF();
		}
		this.pushEnergy();
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		int metadata = getBlockMetadata();
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, metadata, nbtTagCompound);
	}
		
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
	}
	
	public void pushEnergy(){
		if ((this.currentRF > 0)) {
			for (int i = 0; i < 6; i++){
				TileEntity tile = worldObj.getTileEntity(xCoord + ForgeDirection.getOrientation(i).offsetX, yCoord + ForgeDirection.getOrientation(i).offsetY, zCoord + ForgeDirection.getOrientation(i).offsetZ);
				if (tile != null && tile instanceof IEnergyHandler) {
					this.extractEnergy(((IEnergyHandler)tile).receiveEnergy(ForgeDirection.getOrientation(i).getOpposite(), 100, false), false);
				}
			}
		}
	}
}
