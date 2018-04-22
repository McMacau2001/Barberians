package Main.Factions.Factions.Invites;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class FactionsInvitesManager 
{
	
	public static List<FactionsInvites> invites = new ArrayList<>();
	
	public boolean checkInvite(Player p)
	{
		for(FactionsInvites e : invites)
		{
			if(e.getReceiver().getName()==p.getName()) return true;
		}
		return false;
	}
	
	public FactionsInvites getFactionInvite(Player receiver, Player inviter)
	{
		for(FactionsInvites e : invites)
		{
			if(e.getReceiver().getName()==receiver.getName() && e.getInviter().getName()==inviter.getName()) return e;
		}
		return null;
	}
	
	public void removeCancelFactionInvite(FactionsInvites e)
	{
		invites.remove(e);
		e=null;
	}
	
	
	

}
