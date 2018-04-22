package Main.Factions.Utils.Others;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import Main.Factions.FactionPlayers.FactionPlayersManager;
import Main.Factions.Factions.FactionManager;
import Main.Factions.Factions.Invites.FactionsInvitesManager;
import Main.Factions.Utils.APIScoreBoard;
import Main.Utils.Utils.Essentials.Tell;
import Main.Utils.Utils.Essentials.Vanish;
import Main.Utils.Utils.Essentials.Whitelist;
import Main.Utils.Utils.TPA.TPAManager;



public class ScoreBoardManager {
	
	public static FactionPlayersManager factionsplayersmanager = new FactionPlayersManager();
	public static FactionManager factionmanager = new FactionManager();
	public static FactionsInvitesManager factionsinvitesmanager = new FactionsInvitesManager();
	public static TPAManager tpamanager = new TPAManager();
	public static Vanish vanish = new Vanish();
	public static Tell tell = new Tell();
	
	public static HashMap<Player, APIScoreBoard> scoreboards = new HashMap<>();
	
	public static HashMap<Player, APIScoreBoard> scoreboardsList(){
		return scoreboards;
	}
	
    public String Timer(int num){
    	if(num/60==0) return num%60+"s";
    	else return num/60+"m "+num%60+"s";
    }
    
	public void staff(Player p){
		APIScoreBoard board = new APIScoreBoard(p, "§aStaff"){
			@Override
			public String placeHolders(String str) {
				return str
						.replace("%vanish%", ""+vanish.checkVanish(p))
						.replace("%tell%", ""+tell.checkSpy(p))
						.replace("%whitelist%", ""+Whitelist.getWhitelist())
						.replace("%online%", ""+Bukkit.getServer().getOnlinePlayers().size())
						.replace("%maxonline%", ""+Bukkit.getServer().getMaxPlayers())
						.replace("%mem%", (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1048576+""
						);
						
			}
		};
		
		board.blank();
		board.add(" §eServidor:");	
		board.add("   §7RAM: §e%mem%§7/§e"+Runtime.getRuntime().totalMemory()/1048576);	
		board.add("   §7Jogadores: §e%online%§7/§e%maxonline%  ");
		board.add("   §7Manutenção: %whitelist%  ");
		board.blank();
		board.add(" §eJogador:");	
		board.add("   §7Vanish: %vanish%  ");
		board.add("   §7Espiar: %tell%  ");
		board.blank();
		
		board.updateScoreboard();
		
		scoreboards.put(p, board);
	}
	
}