package com.EnergyReactors.core.gui;

import java.util.ArrayList;
import java.util.List;

import com.EnergyReactors.api.GUIMouse;
import com.EnergyReactors.api.ProgressBar;
import com.EnergyReactors.core.EnergyReactors;
import com.EnergyReactors.core.blocks.TileEntityGeneratorRedstone;
import com.EnergyReactors.core.container.ContainerGeneratorRedstone;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiGeneratorRedstone extends GuiContainer {

	private IInventory playerInv;
	private TileEntityGeneratorRedstone tileentity;
	
	public GuiGeneratorRedstone(IInventory playerInv, TileEntityGeneratorRedstone tileentity) {
		super(new ContainerGeneratorRedstone(playerInv, tileentity));
		this.playerInv = playerInv;
		this.tileentity = tileentity;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.mc.getTextureManager().bindTexture(new ResourceLocation(EnergyReactors.MODID + ":textures/gui/container/gui_generator_redstone.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.tileentity.getInventoryName();
		this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		
		GUIMouse mouse = new GUIMouse(width, height, xSize, ySize, mouseX, mouseY);
		
		if(this.tileentity.getCurrentBurnTime() == 0){
			this.fontRendererObj.drawString("RF/t: 0", 37, 23, 16777215);
		} else {
			this.fontRendererObj.drawString("RF/t: " + this.tileentity.getProductionRate(), 37, 23, 16777215);
		}
		this.fontRendererObj.drawString("Burn Time Left: " + (this.tileentity.getCurrentBurnTime() / 20) + "s", 37, 33, 16777215);
		
		if(this.tileentity.getCurrentRF() != 0){
			this.mc.getTextureManager().bindTexture(new ResourceLocation(EnergyReactors.MODID + ":textures/gui/container/gui_generator_redstone.png"));
			this.drawTexturedModalRect(152, 17, 176, 0, 16, 60 - getProgressLevel(60));
		} else {
			this.mc.getTextureManager().bindTexture(new ResourceLocation(EnergyReactors.MODID + ":textures/gui/container/gui_generator_redstone.png"));
			this.drawTexturedModalRect(152, 17, 176, 0, 16, 60);
		}
		
		if(mouse.getActualMouseX() >= 151 && mouse.getActualMouseX() <= 168 && mouse.getActualMouseY() >= 16 && mouse.getActualMouseY() <= 77){
			List<String> hoverText = new ArrayList<String>();
			hoverText.add(this.tileentity.getCurrentRF() + " RF/" + this.tileentity.getMaxRF() + " RF");
			this.drawHoveringText(hoverText, mouse.getActualMouseX(), mouse.getActualMouseY(), fontRendererObj);
		}
	}
	
	private int getProgressLevel(int progressIndicatorPixelHeight) {
		int rf = this.tileentity.getCurrentRF();
		int maxRF = this.tileentity.getMaxRF();
		return maxRF != 0 && rf != 0 ? (rf * progressIndicatorPixelHeight) / maxRF : 0;
	}
	
	private int getProgressPercent(){
		return (this.tileentity.getCurrentRF() / this.tileentity.getMaxRF());
	}
}
