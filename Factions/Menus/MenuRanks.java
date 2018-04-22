package Main.Factions.Factions.Menus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Main.Factions.Factions.Faction;
import Main.Factions.MySql.MySqlConnection;

public class MenuRanks 
{
	public static MySqlConnection connection = new MySqlConnection();
	
	Inventory Inv;
	ItemStack Item;
	ItemMeta Meta;
	List<String> Lore = new ArrayList<>();
	
	private void loadFactyionRank(Faction f, String  rank){
		
		try 
		{
			java.sql.PreparedStatement statement = connection.getConection().prepareStatement("SELECT * FROM Players WHERE FactionUUID=?");
			statement.setInt(1, f.getUUID());
			
			ResultSet results = statement.executeQuery();
			
			while(results.next())
				if(results.getString("FactionRank").equals(rank))Lore.add("    "+results.getString("FactionRank")+" §7"+results.getString("Name")+"  ");
		
			
			statement.close();
			statement.cancel();		
			
		} 
		catch (SQLException e){e.printStackTrace();}
		
	}
	
	public Inventory openMenuRanks(Faction pf)
	{
		Inv = Bukkit.createInventory(null, 27, "§aGerir Ranks da Facção:");
		
		Item = new ItemStack(Material.WOOD_SWORD);
		Meta = Item.getItemMeta();
		
		Meta.setDisplayName("§7[Recruta]");
		Lore.add("");
		Lore.add("  §7Todos os jogadores do rank Recruta:  ");
		loadFactyionRank(pf, "§7[Recruta]");
		Meta.setLore(Lore);
		Item.setItemMeta(Meta);
		
		Inv.setItem(11, Item); Lore.clear();
		
		Item = new ItemStack(Material.GOLD_SWORD);
		Meta = Item.getItemMeta();
		
		Meta.setDisplayName("§8[Membro]");
		Lore.add("");
		Lore.add("  §7Todos os jogadores do rank Membro:  ");
		loadFactyionRank(pf, "§8[Membro]");
		Meta.setLore(Lore);
		Item.setItemMeta(Meta);
		
		Inv.setItem(13, Item); Lore.clear();
		
		Item = new ItemStack(Material.DIAMOND_SWORD);
		Meta = Item.getItemMeta();
		
		Meta.setDisplayName("§e[Capitão]");
		Lore.add("");
		Lore.add("  §7Todos os jogadores do rank Capitão:  ");
		loadFactyionRank(pf, "§e[Capitão]");
		Meta.setLore(Lore);
		Item.setItemMeta(Meta);
		
		Inv.setItem(15, Item); Lore.clear();
		
		Item = new ItemStack(Material.ARROW);
		ItemMeta ItemMeta = Item.getItemMeta();
		
		ItemMeta.setDisplayName("§eVoltar ao menu");
		
		Lore.add("");
		Lore.add("  §7Clica para voltares ao menu principal  ");
		Lore.add("  §7da tua Facção.");
		Lore.add("");
		
		ItemMeta.setLore(Lore);
		Item.setItemMeta(ItemMeta);
		
		
		Inv.setItem(22, Item);Lore.clear();
		
		return Inv;
	}
	
	public Inventory openMenuRanks(Faction f, String Rank){
		
		Inv = Bukkit.createInventory(null, 27, "§aGerir Permissões:");
		
		for(int x=0; x<14; x++)
		{
			Item = new ItemStack(Material.PAPER);
			Meta = Item.getItemMeta();
			
			Meta.setDisplayName("§cBrevemente");
			Item.setItemMeta(Meta);
			
			Inv.setItem(x, Item);
		}
		
		Item = new ItemStack(Material.ARROW);
		ItemMeta ItemMeta = Item.getItemMeta();
		
		ItemMeta.setDisplayName("§eVoltar ao menu");
		
		Lore.add("");
		Lore.add("  §7Clica para voltares ao menu principal  ");
		Lore.add("  §7da tua Facção.");
		Lore.add("");
		
		ItemMeta.setLore(Lore);
		Item.setItemMeta(ItemMeta);
		
		
		Inv.setItem(22, Item);Lore.clear();
		
		return Inv;
	}
}
