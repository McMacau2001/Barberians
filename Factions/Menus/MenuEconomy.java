package Main.Factions.Factions.Menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import Main.Factions.FactionPlayers.FactionPlayer;

public class MenuEconomy 
{

	ItemStack Item;
	ItemMeta ItemMeta;
	SkullMeta SkullMeta;
	List<String> Lore = new ArrayList<>();
	
	public Inventory  openMenuEconomy(FactionPlayer FactionPlayer)
	{
		Inventory Inv = Bukkit.createInventory(null, 27, "§aBanco da Facção:");
		
		Item = new ItemStack(Material.DOUBLE_PLANT);
		ItemMeta = Item.getItemMeta();
		
		ItemMeta.setDisplayName("§eDinheiro");
		
		Lore.add("");
		Lore.add("  §7Dinheiro da Facção: §e"+FactionPlayer.getFaction().getMoney()+"€  ");
		Lore.add("");

		ItemMeta.setLore(Lore);
		Item.setItemMeta(ItemMeta);
		
		Lore.clear();
		
		Inv.setItem(4, Item);
		
		Item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta = (org.bukkit.inventory.meta.SkullMeta)Item.getItemMeta();
		
		SkullMeta.setOwner("MHF_ArrowDown");
		SkullMeta.setDisplayName("§eDepositar Dinheiro");
		Lore.add("");
		Lore.add("  §7Clica para depositares dinheiro na tua");
		Lore.add("  §7Facção, mas tem atenção que ele pode ser");
		Lore.add("  §7retirado por outros membros da facção.");
		Lore.add("");
		
		SkullMeta.setLore(Lore);
		Item.setItemMeta(SkullMeta);
		
		Inv.setItem(11, Item);
		Lore.clear();
		
		Item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta = (org.bukkit.inventory.meta.SkullMeta)Item.getItemMeta();
		
		SkullMeta.setOwner("MHF_ArrowUp");
		SkullMeta.setDisplayName("§eLevantar Dinheiro");
		Lore.add("");
		Lore.add("  §7Clica para levantares dinheiro");
		Lore.add("  §7da tua Facção");
		Lore.add("");
		
		SkullMeta.setLore(Lore);
		Item.setItemMeta(SkullMeta);
		
		Inv.setItem(15, Item);
		Lore.clear();
		
		Item = new ItemStack(Material.ARROW);
		ItemMeta ItemMeta = Item.getItemMeta();
		
		ItemMeta.setDisplayName("§eVoltar ao menu");
		
		Lore.add("");
		Lore.add("  §7Clica para voltares ao menu principal  ");
		Lore.add("  §7da tua Facção.");
		Lore.add("");
		
		ItemMeta.setLore(Lore);
		Item.setItemMeta(ItemMeta);
		
		
		Inv.setItem(22, Item);
		Lore.clear();
		
		return Inv;
	}
}
