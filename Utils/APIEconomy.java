package Main.Factions.Utils;

import Main.Factions.FactionPlayers.FactionPlayer;
import Main.Factions.Factions.Faction;

public class APIEconomy 
{
	
	//FactionPlayer Functions
	
	public void addPlayerMoney(FactionPlayer factionplayer, int amount)
	{
		factionplayer.setMoney(factionplayer.getMoney()+amount);
	}
	public void removePlayerMoney(FactionPlayer factionplayer, int amount)
	{
		factionplayer.setMoney(factionplayer.getMoney()-amount);
	}
	public void setPlayerMoney(FactionPlayer factionplayer, int amount)
	{
		factionplayer.setMoney(amount);
	}
	public void resetPlayerMoney(FactionPlayer factionplayer)
	{
		factionplayer.setMoney(0);
	}
	
	//Cash Functions
	
	public void addPlayerCash(FactionPlayer factionplayer, int amount)
	{
		factionplayer.setCash(factionplayer.getCash()+amount);
	}
	public void removePlayerCash(FactionPlayer factionplayer, int amount)
	{
		factionplayer.setCash(factionplayer.getCash()-amount);
	}
	public void setPlayerCash(FactionPlayer factionplayer, int amount)
	{
		factionplayer.setCash(amount);
	}
	public void resetPlayerCash(FactionPlayer factionplayer)
	{
		factionplayer.setCash(0);
	}
		
	//Faction Functions
	
	public void addFactionMoney(Faction faction, int amount)
	{
		faction.setMoney(faction.getMoney()+amount);
	}
	public void removeFactionMoney(Faction faction, int amount)
	{
		faction.setMoney(faction.getMoney()-amount);
	}
	public void setFactionMoney(Faction faction, int amount)
	{
		faction.setMoney(amount);
	}
	public void resetFactionMoney(Faction faction)
	{
		faction.setMoney(0);
	}

}
