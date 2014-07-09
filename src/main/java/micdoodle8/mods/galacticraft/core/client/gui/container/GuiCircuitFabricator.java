package micdoodle8.mods.galacticraft.core.client.gui.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.inventory.ContainerCircuitFabricator;
import micdoodle8.mods.galacticraft.core.tile.TileEntityCircuitFabricator;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiCircuitFabricator extends GuiContainer
{
	private static final ResourceLocation circuitFabricatorTexture = new ResourceLocation(GalacticraftCore.ASSET_DOMAIN, "textures/gui/circuitFabricator.png");

	private TileEntityCircuitFabricator tileEntity;

	public GuiCircuitFabricator(InventoryPlayer par1InventoryPlayer, TileEntityCircuitFabricator tileEntity)
	{
		super(new ContainerCircuitFabricator(par1InventoryPlayer, tileEntity));
		this.tileEntity = tileEntity;
		this.ySize = 192;
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of
	 * the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString(this.tileEntity.getInventoryName(), 10, 6, 4210752);
		String displayText;

		if (this.tileEntity.processTicks > 0)
		{
			displayText = EnumColor.BRIGHT_GREEN + GCCoreUtil.translate("gui.status.running.name");
		}
		else
		{
			displayText = EnumColor.ORANGE + GCCoreUtil.translate("gui.status.idle.name");
		}

		String str = GCCoreUtil.translate("gui.message.status.name") + ":";
		this.fontRendererObj.drawString(str, 115 - this.fontRendererObj.getStringWidth(str) / 2, 80, 4210752);
		this.fontRendererObj.drawString(displayText, 115 - this.fontRendererObj.getStringWidth(displayText) / 2, 90, 4210752);
		this.fontRendererObj.drawString(GCCoreUtil.translate("container.inventory"), 8, this.ySize - 93, 4210752);
		str = "" + this.tileEntity.storage.getMaxExtract();
		this.fontRendererObj.drawString(str, 5, 42, 4210752);
		//		str = ElectricityDisplay.getDisplay(this.tileEntity.getVoltage(), ElectricUnit.VOLTAGE);
		this.fontRendererObj.drawString(str, 5, 52, 4210752);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the
	 * items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		this.mc.renderEngine.bindTexture(GuiCircuitFabricator.circuitFabricatorTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int containerWidth = (this.width - this.xSize) / 2;
		int containerHeight = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);

		int scale;

		if (this.tileEntity.processTicks > 0)
		{
			scale = (int) ((double) this.tileEntity.processTicks / (double) TileEntityCircuitFabricator.PROCESS_TIME_REQUIRED * 51);
			this.drawTexturedModalRect(containerWidth + 88, containerHeight + 20, 176, 17 + this.tileEntity.processTicks % 9 / 3 * 10, scale, 10);
		}

		if (this.tileEntity.getEnergyStoredGC() > 0)
		{
			scale = this.tileEntity.getScaledElecticalLevel(54);
			this.drawTexturedModalRect(containerWidth + 116 - 98, containerHeight + 89, 176, 0, scale, 7);
			this.drawTexturedModalRect(containerWidth + 4, containerHeight + 88, 176, 7, 11, 10);
		}
	}
}
