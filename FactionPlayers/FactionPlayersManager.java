package Main.Factions.FactionPlayers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class FactionPlayersManager 
{

	public static List<FactionPlayer> factionplayer = new ArrayList<>();
	
	public List<FactionPlayer> getFactionPlayerList()
	{
		return factionplayer;
	}
	
	public static boolean isPlayerExists(Player p)
	{
		for(FactionPlayer fp : factionplayer)
		{
			if(fp.getPlayer().getUniqueId().equals(p.getUniqueId())) return true;
		}
		return false;
	}
	
	public static FactionPlayer getFactionPlayer(Player p)
	{
		for(FactionPlayer fp : factionplayer)
		{
			if(fp.getPlayer().getUniqueId().equals(p.getUniqueId())) return fp;
		}
		return null;
	}
}
