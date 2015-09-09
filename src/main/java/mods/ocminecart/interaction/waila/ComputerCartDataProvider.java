package mods.ocminecart.interaction.waila;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mods.ocminecart.OCMinecart;
import mods.ocminecart.common.items.ModItems;
import mods.ocminecart.common.minecart.ComputerCart;

public class ComputerCartDataProvider implements IWailaEntityProvider {

	@Override
	public Entity getWailaOverride(IWailaEntityAccessor accessor, IWailaConfigHandler config) {
		return accessor.getEntity();
	}

	@Override
	public List<String> getWailaHead(Entity entity, List<String> currenttip,
			IWailaEntityAccessor accessor, IWailaConfigHandler config) {	//Unused
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(Entity entity, List<String> currenttip,
			IWailaEntityAccessor accessor, IWailaConfigHandler config) {
		NBTTagCompound tag = accessor.getNBTData();
		if(config.getConfig("oc.address") && tag.hasKey("address")) 
			currenttip.add(EnumChatFormatting.GOLD+"Address: "+EnumChatFormatting.WHITE+tag.getString("address"));
		
		if(config.getConfig("oc.energy") && tag.hasKey("energy") && tag.hasKey("maxenergy")) 
			currenttip.add(EnumChatFormatting.GOLD+"Stored Energy: "+EnumChatFormatting.WHITE+tag.getInteger("energy")
					+"/"+tag.getInteger("maxenergy"));
		
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(Entity entity, List<String> currenttip,
			IWailaEntityAccessor accessor, IWailaConfigHandler config) {    //Unused
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, Entity ent,NBTTagCompound tag, World world) {
		NBTTagCompound newtag = new NBTTagCompound();
		if(ent instanceof ComputerCart){
			ComputerCart cart = (ComputerCart) ent;
			newtag.setInteger("energy", (int)Math.floor(cart.getEnergy()+0.5D));
			newtag.setInteger("maxenergy", (int)Math.floor(cart.getMaxEnergy()));
			newtag.setString("address", cart.node().address());
		}
		return newtag;
	}

}
