package Main.Factions.Factions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import Main.Factions.Main;
import Main.Factions.FactionPlayers.FactionPlayer;
import Main.Factions.FactionPlayers.FactionPlayerMySql;
import Main.Factions.FactionPlayers.FactionPlayersManager;
import Main.Factions.MySql.MySqlConnection;

public class FactionMySql {

	public static MySqlConnection connection = new MySqlConnection();
	public static FactionPlayersManager factionplayersmanager = new FactionPlayersManager(); 
	public static FactionPlayerMySql factionplayermysql = new FactionPlayerMySql();
	public static FactionManager factionmanager = new FactionManager();
	
	public boolean isFactionExists(int UUID)
	{
		try 
		{
			java.sql.PreparedStatement statement = connection.getConection().prepareStatement("SELECT * FROM Factions WHERE UUID=?");
			statement.setInt(1, UUID);
			
			ResultSet results = statement.executeQuery();
			
			if(results.next())
			{
				return true;
			}
			
			statement.close();
			statement.cancel();
		} 
		catch (SQLException e){}
		
		return false;
	}
	
	public boolean isFactionNameExists(String name)
	{
		try 
		{
			java.sql.PreparedStatement statement = connection.getConection().prepareStatement("SELECT * FROM Factions WHERE Name=?");
			statement.setString(1, name);
			
			ResultSet results = statement.executeQuery();
			
			if(results.next())
			{
				return true;
			}
			
			statement.close();
			statement.cancel();
		} 
		catch (SQLException e){}
		
		return false;
	}
	
	public void createFaction(Faction faction)
	{
		try 
		{
			java.sql.PreparedStatement insert = connection.getConection().prepareStatement("INSERT INTO Factions (UUID,Name,TAG,Owner,Description,Home,Banner,Money,Power,TotalKills,TotalDeaths,Date) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
			
			String home="";
			if(faction.getHome()==null)home="null";
			else home = faction.getHome().toString();
			
			insert.setInt(1, faction.getUUID());
			insert.setString(2, faction.getName());
			insert.setString(3, faction.getTag());
			insert.setString(4, faction.getOwner());
			insert.setString(5, faction.getDescription());
			insert.setString(6, home);
			insert.setString(7, convertArray(faction.getBanner()));
			insert.setInt(8, faction.getMoney());
			insert.setInt(9, faction.getPower());
			insert.setInt(10, (int)faction.getTotalKills());
			insert.setInt(11, (int)faction.getTotalDeaths());
			insert.setString(12, faction.getDate());
			SaveInventoryConfig(faction.getChest(), faction);

			insert.executeUpdate();
				
			insert.close();
			insert.cancel();
			
		} 
		catch (SQLException e){e.printStackTrace();}
	}
	
	
	  public static String convertArray(Object obj)throws IllegalStateException
	  {
		  try
		  {
			  ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			  BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
			      
			  dataOutput.writeObject(obj);
			  dataOutput.close();
			  return Base64Coder.encodeLines(outputStream.toByteArray());
		  }
		  catch (Exception e){return null;}
	  }

	  
	public ItemStack LoadBanner(String string) throws IOException
	  {
	      try
	      {
			  ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(string));
		      BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
		      dataInput.close();
		      return (ItemStack)dataInput.readObject();
	      }
	      catch (ClassNotFoundException e){return null;}
	      
	  }
	
	  
	public Location LoadHome(String string) throws IOException
	  {
	      try
	      {
			  ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(string));
		      BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
		      dataInput.close();
		      return (Location)dataInput.readObject();
	      }
	      catch (ClassNotFoundException e){return null;}
	      
	  }
	
	
	public void SaveInventoryConfig(Inventory inv, Faction f){
		File file = new File(Main.main.getDataFolder(), "FactionsChests/"+f.getUUID()+".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		for(int x=0; x<f.getChest().getSize(); x++)
		{
			config.set("Menu."+x, f.getChest().getItem(x));
		}
		
		
		try{config.save(file);}catch(Exception e){}
	}
	
	public Inventory LoadInventoryConfig(Faction f){
		File file = new File(Main.main.getDataFolder(), "FactionsChests/"+f.getUUID()+".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		Inventory inv = Bukkit.createInventory(null, 54, "§aBau da Facção:");
		for(int x=0; x<inv.getSize(); x++)
		{
			if(config.getString("Menu."+x)!="null")inv.setItem(x, (ItemStack)config.get("Menu."+x));
		}
		
		return inv;
	}
	  
	   
	@SuppressWarnings("static-access")
	public Faction loadFaction(int UUID)
	{
		if(isFactionExists(UUID)==false) return null;
		if(factionmanager.getFaction(UUID)!=null){
			return factionmanager.getFaction(UUID);
		}
		try 
		{	
			java.sql.PreparedStatement statement = connection.getConection().prepareStatement("SELECT * FROM Factions WHERE UUID=?");
			statement.setInt(1, UUID);
			
			ResultSet results = statement.executeQuery();
			results.next();
			Faction f = new Faction(UUID, results.getString("Owner") , results.getString("TAG"), results.getString("Name"));
			f.setTotalKills(results.getInt("TotalKills"));
			f.setTotalDeaths(results.getInt("TotalDeaths"));
			try{f.setHome(LoadHome(results.getString("Home")));}
            catch (Exception e){}
            try{f.setBanner(LoadBanner(results.getString("Banner")));}
            catch (Exception e){}
			
			f.setPower(results.getInt("Power"));
			f.setDescription(results.getString("Description"));
			f.setMoney(results.getInt("Money"));
			f.setDate(results.getString("Date"));
		    f.setChest(LoadInventoryConfig(f));
		    
			factionplayermysql.loadPlayerofFaction(f);
			factionmanager.factions.add(f);
			
			statement.close();
			statement.cancel();

			return f;
		} 
		catch (SQLException e){e.printStackTrace();}
		
		return null;
	}
	
	public void saveFaction(int UUID)
	{
		try {
			java.sql.PreparedStatement insert = connection.getConection().prepareStatement("UPDATE Factions SET Owner=?, Description=?, Home=?, Banner=?, Money=?, Power=?, TotalKills=?, TotalDeaths=? WHERE UUID=?");
			Faction f = factionmanager.getFaction(UUID);
			
			insert.setString(1, f.getOwner());
			insert.setString(2, f.getDescription());
			insert.setString(3, convertArray(f.getHome()));
			insert.setString(4, convertArray(f.getBanner()));
			insert.setInt(5, f.getMoney());
			insert.setInt(6, f.getPower());
			insert.setInt(7, (int)f.getTotalKills());
			insert.setInt(8, (int)f.getTotalDeaths());
			insert.setInt(9, (int)UUID);
			insert.executeUpdate();
			
			SaveInventoryConfig(f.getChest(), f);
			
			insert.close();
			insert.cancel();
		} catch (SQLException e){}
	}
	
	@SuppressWarnings("static-access")
	public void saveAllFaction()
	{
		try{
			for(Faction f : factionmanager.factions){
				saveFaction(f.getUUID());
				factionmanager.factions.remove(f);
			}
		}catch(Exception e){}
	}
	
	@SuppressWarnings("static-access")
	public void deleteFaction(Faction f)
	{
		try 
		{
			java.sql.PreparedStatement insert = connection.getConection().prepareStatement("DELETE FROM Factions WHERE UUID=?");
			insert.setInt(1, f.getUUID());
			
			
			f.sendAlert("§eA tua facção foi deletada e nao pode ser recuperada novamente.");
			for(FactionPlayer player : f.getMembersOnline()){
				try{
					factionplayersmanager.getFactionPlayer(Bukkit.getPlayer(player.getName())).setFaction(null);
				}
				catch(Exception e){}
			}	
				
			java.sql.PreparedStatement insertp = connection.getConection().prepareStatement("UPDATE Players SET FactionUUID=?,FactionRank=? WHERE FactionUUID=?");
			insertp.setInt(1, 0);
			insertp.setString(2, "null");
			insertp.setInt(3, f.getUUID());
			
			insertp.executeUpdate();
			insert.executeUpdate();
			insert.close();
			insertp.close();
			insertp.cancel();
			
			File file = new File(Main.main.getDataFolder(), "FactionsChests/"+f.getUUID()+".yml");
			file.delete();
			factionmanager.factions.remove(f);
			f=null;
	
			
		} catch (SQLException e){}
	}
	
}
