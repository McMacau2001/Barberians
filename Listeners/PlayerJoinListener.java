package Main.Factions.Listeners;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import Main.Factions.Main;
import Main.Factions.FactionPlayers.FactionPlayerMySql;
import Main.Factions.FactionPlayers.FactionPlayersManager;
import Main.Factions.Utils.Others.ScoreBoardManager;
import Main.Factions.Utils.Others.TabListManager;
import Main.Utils.Utils.Essentials.Vanish;
import Main.Utils.Utils.Essentials.Whitelist;
import Main.Utils.Utils.Group.GroupTag;

public class PlayerJoinListener implements Listener
{
	
	public static FactionPlayersManager factionplayermanager = new FactionPlayersManager();
	public static FactionPlayerMySql factionplayermysql = new FactionPlayerMySql();
	public static ScoreBoardManager scoreboardmanager = new ScoreBoardManager();
	public static Vanish vanish = new Vanish();
	
	@EventHandler
	public void PlayerLongingListener(PlayerLoginEvent e)
	{
		if(!e.getPlayer().isOp() && Whitelist.getState()==true)e.disallow(Result.KICK_WHITELIST, Whitelist.getReason());
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void PlayerJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
			
		e.setJoinMessage(null);
		
		if(p.isOp())scoreboardmanager.staff(p);
		else vanish.hideVanishPlayers(p);
		
		Bukkit.getScheduler().runTaskAsynchronously(Main.main, new Runnable() {
			
			@Override
			public void run() {
				
				factionplayermysql.loadPlayerFaction(p);
				GroupTag.loadTags();
				TabListManager.loadTab(p);
			}
		});

		
	}

}
