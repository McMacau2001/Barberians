package Main.Factions.Factions.Invites;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import Main.Factions.Main;
import Main.Factions.Factions.Faction;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class FactionsInvites 
{

	public static FactionsInvitesManager factionsinvitesmanager = new FactionsInvitesManager();
	
	private Player Inviter;
	private Player Receiver;
	private Faction Faction;
	private int Task;
	private int Time=0;
	
	public FactionsInvites(Player Inviter, Player Receiver, Faction Faction)
	{
		this.Inviter = Inviter;
		this.Receiver = Receiver;
		this.Faction = Faction;
		
		Task=Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.main,  new Runnable() {
			@Override
			public void run() {
				
				if(Time==1){
					factionsinvitesmanager.removeCancelFactionInvite(factionsinvitesmanager.getFactionInvite(Receiver, Inviter));
					stopTimer();
				}
				Time++;
			}
		}, 0L, 600L);	
	}
	
	public Player getInviter() 
	{
		return Inviter;
	}
	public void setInviter(Player Inviter) 
	{
		this.Inviter = Inviter;
	}
	public Player getReceiver() 
	{
		return Receiver;
	}
	public void setReceiver(Player receiver) 
	{
		Receiver = receiver;
	}
	public Faction getFaction() 
	{
		return Faction;
	}
	public void setFaction(Faction Faction) 
	{
		this.Faction = Faction;
	}
	public void stopTimer()
	{
		Bukkit.getScheduler().cancelTask(Task);
		factionsinvitesmanager.removeCancelFactionInvite(factionsinvitesmanager.getFactionInvite(Receiver, Inviter));
	}
	public void sendMenssage()
	{
		
		PermissionUser user = PermissionsEx.getUser(Inviter);
		Receiver.sendMessage("");
		Receiver.sendMessage("§eO jogador "+user.getPrefix()+"§7[#"+Faction.getTag()+"] §7"+Inviter.getName()+" §equer convidar-te para a facção dele. Clica para aceitares ou rejeitares o pedido.");		
		
		TextComponent message = new TextComponent("");
		TextComponent accept = new TextComponent( "                 §a§l[ACEITAR]" );
		accept.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/f convite aceitar "+Inviter.getName() ) );
		accept.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aAceitar").create() ) );

		TextComponent reject = new TextComponent( "§c§l[RECUSAR]\n" );
		reject.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cRecusar").create() ) );
		reject.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/f convite recusar "+Inviter.getName() ) );
		
		message.addExtra( accept );
		message.addExtra( "             " );
		message.addExtra( reject );
		Receiver.sendMessage("");
		
		Inviter.sendMessage("§aEnvias-te um convite para entrar na tua facção ao jogador "+Receiver.getName()+".");
		Receiver.spigot().sendMessage( message );
		Receiver.playSound(Receiver.getLocation(), Sound.NOTE_PLING, 2F, 1F);
	}
	public void sendRejectMenssage()
	{
		Inviter.sendMessage("§cO jogador "+Receiver.getName()+" recusou o teu convite.");
		Receiver.sendMessage("§cRecusaste o convite do jogador "+Inviter.getName()+".");
	}
	public void sendAcceptMenssage()
	{
		Receiver.playSound(Receiver.getLocation(), Sound.LEVEL_UP, 2F, 1F);
		Receiver.sendMessage("§aJuntaste-te á facção "+Faction.getName()+".");
	}
	public void sendExpiresMenssage()
	{
		Inviter.sendMessage("§cO convite do jogador "+Receiver.getName()+" expirou.");
	}

	public int getTask() {
		return Task;
	}

	public void setTask(int task) {
		Task = task;
	}
}
