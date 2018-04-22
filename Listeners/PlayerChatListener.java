package Main.Factions.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import Main.Factions.Main;
import Main.Factions.FactionPlayers.FactionPlayer;
import Main.Factions.FactionPlayers.FactionPlayersManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

@SuppressWarnings("deprecation")
public class PlayerChatListener implements Listener{
	
	public static FactionPlayersManager factionplayermanager =new FactionPlayersManager();
	
	public static List<Player> Faction = new ArrayList<>();
	public static List<Player> Ally = new ArrayList<>();
	
	public void changePlayerChat(String Chat, Player p)
	{
		if(Chat.equalsIgnoreCase("Faction") || Chat.equalsIgnoreCase("Fac") || Chat.equalsIgnoreCase("Facçao") || Chat.equalsIgnoreCase("Faccao")){
			if(Ally.contains(p))Ally.remove(p);
			Faction.add(p);
			
			p.sendMessage("§aChat alterado para Facção");
		}
		else if (Chat.equalsIgnoreCase("Ally") || Chat.equalsIgnoreCase("Aliados")){
			if(Faction.contains(p))Faction.remove(p);
			Ally.add(p);
			
			p.sendMessage("§aChat alterado para Aliados");
		}
		else{
			if(Ally.contains(p))Ally.remove(p);
			if(Faction.contains(p))Faction.remove(p);
			
			p.sendMessage("§aChat alterado para Global");
		}
	}
	
	@EventHandler
	public void PlayerChatEvent(PlayerChatEvent e)
	{
		
		e.setCancelled(true);
		Bukkit.getScheduler().runTaskAsynchronously(Main.main, new Runnable() {
			
			@SuppressWarnings("static-access")
			@Override
			public void run() {				
				if(e.getPlayer().hasPermission("faction.colorchat"))e.setMessage(e.getMessage().replace("&", "§"));
				if(factionplayermanager.getFactionPlayer(e.getPlayer()).getMute()==null){
					if(Faction.contains(e.getPlayer())){
						
						FactionPlayer fp = factionplayermanager.getFactionPlayer(e.getPlayer());
						factionplayermanager.getFactionPlayer(e.getPlayer()).getFaction().sendPlayerFactionMenssage(fp.getFaction().getTag(), fp.getFactionRank(), fp.getName(), e.getMessage());
						fp=null;
					}
					else if(factionplayermanager.getFactionPlayer(e.getPlayer()).getFaction()!=null){
						
						PermissionUser user = PermissionsEx.getUser(e.getPlayer());
						FactionPlayer fp = factionplayermanager.getFactionPlayer(e.getPlayer());
						
						TextComponent menssage = new TextComponent(""+user.getPrefix());
						TextComponent menssage1 = new TextComponent("§7[#"+fp.getFaction().getTag()+"] ");
						menssage1.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("  §eFacção: §7"+fp.getFaction().getName()+"  \n  §eDono: §7"+fp.getFaction().getOwner()+"  \n  §eMembros: §7"+fp.getFaction().getOnlineMembers()+"/"+fp.getFaction().getTotalMembers()+"  \n  §ePoder: §7"+fp.getFaction().getPower()+"  ").create() ) );
						TextComponent menssage2 = new TextComponent("§7"+e.getPlayer().getName()+": ");
						menssage2.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("  §eDinheiro: §7"+fp.getMoney()+"€  \n  §eKDR: §7"+fp.getKDR()+"  \n  §ePoder: §7"+fp.getPower()+"/"+fp.getMaxPower()).create() ) );
						menssage2.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "/sussurrar "+e.getPlayer().getName()+" "));
						TextComponent menssage3 = new TextComponent("§f"+e.getMessage());
						menssage.addExtra(menssage1);
						menssage.addExtra(menssage2);
						menssage.addExtra(menssage3);
						
						for(Player p : Bukkit.getOnlinePlayers())p.spigot().sendMessage( menssage );
						
					}
					else{
						
						PermissionUser user = PermissionsEx.getUser(e.getPlayer());
						FactionPlayer fp = factionplayermanager.getFactionPlayer(e.getPlayer());
						
						TextComponent menssage = new TextComponent(""+user.getPrefix());
						TextComponent menssage1 = new TextComponent("§7"+e.getPlayer().getName()+": ");
						menssage1.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("  §eDinheiro: §7"+fp.getMoney()+"€  \n  §eKDR: §7"+fp.getKDR()+"  \n  §ePoder: §7"+fp.getPower()+"/"+fp.getMaxPower()).create() ) );
						menssage1.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "/sussurrar "+e.getPlayer().getName()+" "));
						TextComponent menssage2 = new TextComponent("§f"+e.getMessage());
						menssage.addExtra(menssage1);
						menssage.addExtra(menssage2);
						
						for(Player p : Bukkit.getOnlinePlayers())p.spigot().sendMessage( menssage );
					}
				}
			}
		});

	}
	
}
