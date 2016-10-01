package com.EnergyReactors.core.gui;

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
		//GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(EnergyReactors.MODID + ":textures/gui/container/gui_generator_redstone.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = this.tileentity.getInventoryName();
		this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
	}

}
