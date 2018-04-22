package Main.Factions.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import Main.Factions.Main;
import Main.Factions.FactionPlayers.FactionPlayerMySql;
import Main.Factions.FactionPlayers.FactionPlayersManager;
import Main.Factions.Utils.Others.ScoreBoardManager;

public class PlayerQuitListener implements Listener
{

	public static FactionPlayersManager factionplayermanager = new FactionPlayersManager();
	public static FactionPlayerMySql factionplayermysql = new FactionPlayerMySql();
	public static ScoreBoardManager scoreboardmanager = new ScoreBoardManager();
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void PlayerQuit(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		
		e.setQuitMessage(null);
		
		if(factionplayermanager.isPlayerExists(p)==true)
		{
			if(scoreboardmanager.scoreboardsList().containsKey(p))scoreboardmanager.scoreboardsList().remove(p);
			
			Bukkit.getScheduler().runTaskAsynchronously(Main.main, new Runnable() {
				
				@Override
				public void run() {
					factionplayermysql.savePlayerFaction(factionplayermanager.getFactionPlayer(p),true);
				}
			});
		}
		
	}
}
