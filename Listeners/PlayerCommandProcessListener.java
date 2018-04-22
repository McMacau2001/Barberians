package Main.Factions.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import Main.Factions.Config.ConfigWarps;
import Main.Factions.FactionPlayers.FactionPlayersManager;
import Main.Utils.Utils.Teleport.TeleportDelaySystem;

public class PlayerCommandProcessListener implements Listener
{

	public static ConfigWarps configwarps = new ConfigWarps();
	public static TeleportDelaySystem teleportdelaysystem = new TeleportDelaySystem();
	public static FactionPlayersManager factionplayermanager =new FactionPlayersManager();
	
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void PlayerCommandProcessEvent(PlayerCommandPreprocessEvent e)
	{
			
			if(configwarps.Warps.containsKey(e.getMessage().replace("/", "").toLowerCase()))
			{
				e.setCancelled(true);
				if(teleportdelaysystem.isInTeleportDelay(e.getPlayer()))teleportdelaysystem.stopTeleportDelay(e.getPlayer());
				teleportdelaysystem.playerTeleportDelay(e.getPlayer(), configwarps.Warps.get(e.getMessage().replace("/", "").toLowerCase()), 5, "a warp "+e.getMessage().replace("/", ""));
			}
	}
}
