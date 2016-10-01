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
		ProgressBar pBar = new ProgressBar(this.tileentity.getCurrentRF(), this.tileentity.getMaxRF(), ProgressBar.TOP_TO_BOTTOM);
		
		int progress = pBar.getProgressLevel(16, 60);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(EnergyReactors.MODID + ":textures/gui/container/gui_generator_redstone.png"));
		this.drawTexturedModalRect(152, 17, 176, 59, 16, 60 - progress);
		
		if(mouse.getActualMouseX() >= 151 && mouse.getActualMouseX() <= 168 && mouse.getActualMouseY() >= 16 && mouse.getActualMouseY() <= 77){
			List<String> hoverText = new ArrayList<String>();
			hoverText.add(this.tileentity.getCurrentRF() + " RF/" + this.tileentity.getMaxRF() + " RF");
			System.out.println(tileentity.xCoord + " " + tileentity.yCoord + " " + tileentity.zCoord);
			this.drawHoveringText(hoverText, mouse.getActualMouseX(), mouse.getActualMouseY(), fontRendererObj);
		}
	}

}
