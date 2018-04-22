package Main.Factions.Factions.Menus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import Main.Factions.FactionPlayers.FactionPlayer;
import Main.Factions.Factions.Faction;
import Main.Factions.MySql.MySqlConnection;

public class MenuMembersList 
{

	ItemStack Item;
	BannerMeta BannerMeta;
	SkullMeta SkullMeta;
	List<String> Lore = new ArrayList<>();
	Inventory Inv;
	
	public static MySqlConnection connection = new MySqlConnection();
	
	public float getKDR(int Kills, int Deaths) 
	{
		try{
			return (int)Kills/(int)Deaths;
		}
		catch(Exception e){return 0;}
	}
	public String getMoney(int Money){
		return new Long((int)Money).longValue()+"";
	}
	public boolean checkbyName(String Name, Faction f){
		for(FactionPlayer fp : f.getMembersOnline())if(fp.getName().equals(Name))return true;
		return false;
	}

	public void loadofflinePlayers(Faction f)
	{
		try 
		{
				try 
				{
					java.sql.PreparedStatement statement = connection.getConection().prepareStatement("SELECT * FROM Players WHERE FactionUUID=?");
					statement.setInt(1, f.getUUID());
					
					ResultSet results = statement.executeQuery();
					
					int slot =28;
					while(results.next())
					{
						while(checkbyName(results.getString("Name"),f))results.next();
						if(slot==36)slot=37;
						
						Long date=System.currentTimeMillis()-results.getLong("LastLogin");
						String dateString="";
					
						if((date/86400000)%30>1)dateString = dateString+""+ ((date/86400000)%30)+ " Dias ";
						else if((date/86400000)%30 >0) dateString = dateString+""+ ((date/86400000)%30)+ " Dia ";
						if((date/3600000)%24>1)dateString = dateString+""+ ((date/3600000)%24)+ " Horas ";
						else if((date/3600000)%24 >0) dateString = dateString+""+ ((date/3600000)%24)+ " Hora ";
						if((date/60000)%60>1)dateString = dateString+""+ ((date/60000)%60)+ " Minutos ";
						else if((date/60000)%60 >0)dateString = dateString+""+ ((date/60000)%60)+ " Minuto ";
						else dateString="Á pouco tempo";
		
						Item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
						SkullMeta = (org.bukkit.inventory.meta.SkullMeta)Item.getItemMeta();
						
						SkullMeta.setOwner(results.getString("Name"));
						SkullMeta.setDisplayName(results.getString("FactionRank")+" §7"+results.getString("Name"));
						Lore.add("");
						Lore.add("  §eInformações:");
						Lore.add("    §7Estado: §c§lOffline");
						Lore.add("    §7Dinheiro: §c"+getMoney(results.getInt("Money"))+"€");
						Lore.add("    §7Poder: §c"+results.getInt("Power")+"/"+results.getInt("MaxPower"));
						Lore.add("    §7Kills: §c"+results.getInt("Kills"));
						Lore.add("    §7Mortes: §c"+results.getInt("Deaths"));
						Lore.add("    §7KDR: §c"+getKDR(results.getInt("Kills"),results.getInt("Deaths")));
						Lore.add("");
						Lore.add("   §7Ultima entrada: "+dateString);
						Lore.add("");
						
						SkullMeta.setLore(Lore);
						Item.setItemMeta(SkullMeta);
						
						Inv.setItem(slot, Item);
						Lore.clear();
						slot++;
					}
					
					statement.close();
					statement.cancel();
		
				} 
				catch (SQLException e){}

		} 
		catch (Exception e){e.printStackTrace();}
	}
	
	public Inventory openMenuMembersList(FactionPlayer FactionPlayer)
	{
		Faction f = FactionPlayer.getFaction();
		int PlayersOnline=f.getOnlineMembers(), PlayersOffline=f.getMembers().size()-f.getOnlineMembers();
		
		Inv = Bukkit.createInventory(null, 54, "§aMembros da Facção:");
		
		Item= new ItemStack(Material.BANNER);
		BannerMeta = (BannerMeta) Item.getItemMeta();
		
		BannerMeta.setBaseColor(DyeColor.LIME);
		BannerMeta.setDisplayName("§eMembros §a§lOnline");
		
		Lore.add("");
		Lore.add("  §7Lista de todos os membros da facção");
		Lore.add("  §7que se encontram online.");
		Lore.add("");
		BannerMeta.setLore(Lore);
		
		Item.setItemMeta(BannerMeta);
		Item.setAmount(PlayersOnline);
		
		Lore.clear();
		
		Inv.setItem(9, Item);
		
		Item= new ItemStack(Material.BANNER);
		BannerMeta = (BannerMeta) Item.getItemMeta();
		
		BannerMeta.setBaseColor(DyeColor.RED);
		BannerMeta.setDisplayName("§eMembros §c§lOffline");
		
		Lore.add("");
		Lore.add("  §7Lista de todos os membros da facção");
		Lore.add("  §7que se encontram offline.");
		Lore.add("");
		BannerMeta.setLore(Lore);
		
		Item.setItemMeta(BannerMeta);
		Item.setAmount(PlayersOffline);
		
		Lore.clear();
		Inv.setItem(27, Item);
		
		loadofflinePlayers(f);
		
		int slot=10;
		for(FactionPlayer fp : f.getMembersOnline())
		{
			if(slot==18)slot=19;
			Item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
			SkullMeta = (org.bukkit.inventory.meta.SkullMeta)Item.getItemMeta();
			
			SkullMeta.setOwner(fp.getName());
			SkullMeta.setDisplayName(fp.getFactionRank()+" §7"+fp.getName());
			Lore.add("");
			Lore.add("  §eInformações:");
			Lore.add("    §7Estado: §a§lOnline");
			Lore.add("    §7Dinheiro: §a"+fp.getMoney()+"€");
			Lore.add("    §7Poder: §a"+fp.getPower()+"/"+fp.getMaxPower());
			Lore.add("    §7Kills: §a"+fp.getKills());
			Lore.add("    §7Mortes: §a"+fp.getDeaths());
			Lore.add("    §7KDR: §a"+fp.getKDR());
			Lore.add("");
			
			SkullMeta.setLore(Lore);
			Item.setItemMeta(SkullMeta);
			
			Inv.setItem(slot, Item);
			Lore.clear();
			slot++;
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
		
		
		Inv.setItem(49, Item);
		
		Lore.clear();
			
		return Inv;
	}
	
}
