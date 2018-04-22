package Main.Factions.FactionPlayers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import Main.Factions.Factions.Faction;
import Main.Factions.Factions.FactionManager;
import Main.Factions.Factions.FactionMySql;
import Main.Factions.MySql.MySqlConnection;
import Main.Utils.Utils.Boxes.Box;
import Main.Utils.Utils.Boxes.BoxesFunctions;

public class FactionPlayerMySql {

	public static MySqlConnection connection = new MySqlConnection();
	public static FactionPlayersManager factionplayersmanager = new FactionPlayersManager(); 
	public static FactionMySql factionmysql = new FactionMySql();
	public static FactionManager factionmanager =  new FactionManager();
	
	public boolean isFactionPlayerExists(Player p)
	{
		try 
		{
			java.sql.PreparedStatement statement = connection.getConection().prepareStatement("SELECT * FROM Players WHERE UUID=?");
			statement.setString(1, p.getUniqueId().toString());
			
			ResultSet results = statement.executeQuery();
			
			if(results.next())
			{
				return true;
			}
			
			results = null;
			statement.close();
			statement.cancel();
			statement = null;
		} 
		catch (SQLException e){}
		
		return false;
	}
	
	public void createPlayerFaction(Player p)
	{
		try 
		{	
			java.sql.PreparedStatement insert = connection.getConection().prepareStatement("INSERT INTO Players (UUID,Name,FactionUUID,Money,Kills,Deaths,Power,MaxPower,Cash,LastLogin, Orbs) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
				
				
			insert.setString(1, p.getUniqueId().toString());
			insert.setString(2, p.getName());
			insert.setInt(3, 0);
			insert.setInt(4, 0);
			insert.setInt(5, 0);
			insert.setInt(6, 0);
			insert.setInt(7, 5);
			insert.setInt(8, 5);
			insert.setInt(9, 0);
			insert.setLong(10, new Date().getTime());
			insert.setInt(11, 0);

			insert.executeUpdate();
			
			insert = connection.getConection().prepareStatement("INSERT INTO Boxes (UUID,Name) VALUES (?,?)");
			insert.setString(1, p.getUniqueId().toString());
			insert.setString(2, p.getName());
			
			insert.executeUpdate();
			insert.close();
			insert.cancel();
			insert = null;
			
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
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Integer> convertObj(String string) throws IOException
	  {
	      try
	      {
			  ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(string));
		      BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
		      dataInput.close();
		      return (HashMap<String, Integer>)dataInput.readObject();
	      }
	      catch (ClassNotFoundException e){return null;}
	      
	  }
	  
	@SuppressWarnings("static-access")
	public void loadPlayerFaction(Player p){
		if(isFactionPlayerExists(p)==false) createPlayerFaction(p);
		
		try 
		{
			java.sql.PreparedStatement statement = connection.getConection().prepareStatement("SELECT * FROM Players WHERE UUID=?");
			statement.setString(1, p.getUniqueId().toString());
			
			ResultSet results = statement.executeQuery();
			results.next();

			FactionPlayer fp = new FactionPlayer(p, results.getInt("Money"),results.getInt("Kills"), results.getInt("Deaths"), results.getInt("Cash"), results.getString("FactionRank"), results.getInt("Orbs"));
			fp.setPower(results.getInt("Power"));
			fp.setMaxPower(results.getInt("MaxPower"));
			fp.setLastLogin(new Date().getTime());
			fp.setFaction(factionmysql.loadFaction(results.getInt("FactionUUID")));
			
			if(fp.getFaction()!=null){
				fp.getFaction().getMembers().set(fp.getFaction().getMemberId(fp), fp.getName());
				fp.getFaction().getMembersOnline().add(fp);
			}
			
			
			statement = connection.getConection().prepareStatement("SELECT * FROM Boxes WHERE UUID=?");
			statement.setString(1, p.getUniqueId().toString());
			
			results = statement.executeQuery();
			results.next();
			
			for(Box b : BoxesFunctions.Boxes){
				 fp.getBoxKeys().put(b, results.getInt(b.getName()));
			}
			
			factionplayersmanager.factionplayer.add(fp);
			
			fp=null;
			results = null;
			statement.close();
			statement.cancel();
			statement = null;
			
			Bukkit.broadcastMessage("§a[Jogador] §7Jogador "+p.getName()+" carregado com sucesso!");
			
			
		} 
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public void loadPlayerofFaction(Faction f) 
	{	
		try 
		{
			java.sql.PreparedStatement statement = connection.getConection().prepareStatement("SELECT * FROM Players WHERE FactionUUID=?");
			statement.setInt(1, f.getUUID());
			
			ResultSet results = statement.executeQuery();
			f.setTotalDeaths(0);
			f.setTotalKills(0);
			f.setPower(0);
			
			while(results.next() == true){
				f.setTotalKills(f.getTotalKills()+results.getInt("Kills"));
				f.setTotalDeaths(f.getTotalDeaths()+results.getInt("Deaths"));
				f.setPower(f.getPower()+results.getInt("Power"));
				f.getMembers().add(results.getString("Name"));
				
			}
			
			results = null;
			statement.close();
			statement.cancel();
			statement = null;
			
		} 
		catch (SQLException e){e.printStackTrace();}

	}
	
	public void loadAllPlayersFaction(){
		try{
			Thread t = new Thread(() -> {
				for(Player p : Bukkit.getOnlinePlayers()){
					loadPlayerFaction(p);
				}
			});
			t.start();
			t = null;
		}catch(Exception  ex){
            ex.printStackTrace();

		}	
	}
	
	@SuppressWarnings("static-access")
	public void savePlayerFaction(FactionPlayer factionplayer, boolean remove)
	{
		try {
			java.sql.PreparedStatement insert = connection.getConection().prepareStatement("UPDATE Players SET FactionUUID=?, Money=?, Kills=?, Deaths=?, Power=?, MaxPower=?, Cash=?, FactionRank=?, LastLogin=?, Orbs=? WHERE UUID=?");
			
			int faction;
			if(factionplayer.getFaction()==null)faction=0;
			else faction = factionplayer.getFaction().getUUID();
			
			insert.setInt(1, faction);
			insert.setInt(2, factionplayer.getMoney());
			insert.setInt(3, (int)factionplayer.getKills());
			insert.setInt(4, (int)factionplayer.getDeaths());
			insert.setInt(5, (int)factionplayer.getPower());
			insert.setInt(6, (int)factionplayer.getMaxPower());
			insert.setInt(7, factionplayer.getCash());
			insert.setString(8, factionplayer.getFactionRank());
			insert.setLong(9, new Date().getTime());
			insert.setInt(10, factionplayer.getOrbs());
			insert.setString(11, factionplayer.getPlayer().getUniqueId().toString());
			insert.executeUpdate();
						
			for(Box b : BoxesFunctions.Boxes){
				
				if(factionplayer.getBoxKeys()!=null){
					java.sql.PreparedStatement insert1 = connection.getConection().prepareStatement("UPDATE Boxes SET "+b.getName()+"=? WHERE UUID=?");
					insert1.setInt(1, factionplayer.getBoxKeys().get(b));
					insert1.setString(2, factionplayer.getPlayer().getUniqueId().toString());
					insert1.executeUpdate();
				}
				
			}
			
			if(remove==true){
				factionplayersmanager.getFactionPlayerList().remove(factionplayer);
				
				if(factionplayer.getFaction()!=null){
					factionplayer.getFaction().getMembersOnline().remove(factionplayer);
					if(factionplayer.getFaction().getMembersOnline().size()==0){
						factionmanager.factions.remove(factionplayer.getFaction());
						Bukkit.broadcastMessage("§a[Factions] §7"+factionplayer.getFaction().getName()+" foi salva com sucesso!");
					}
				}
				
			}
			
			
			for(Box b : BoxesFunctions.Boxes){
				insert = connection.getConection().prepareStatement("UPDATE Boxes SET "+b.getName()+"=? WHERE UUID=?");
				insert.setInt(1, factionplayer.getBoxKeys().get(b));
				insert.setString(2, factionplayer.getPlayer().getUniqueId().toString());
				insert.executeUpdate();
			}
			
			
			factionplayer=null;
			insert.close();
			insert.cancel();
			insert = null;
		} catch (SQLException e){e.printStackTrace();}
	}
	
	@SuppressWarnings("static-access")
	public void saveAllPlayersFaction(){
		
		try{
			for(Player p : Bukkit.getOnlinePlayers()){			
				savePlayerFaction(factionplayersmanager.getFactionPlayer(p),true);
			}
		}catch(Exception e){}
	}
}
