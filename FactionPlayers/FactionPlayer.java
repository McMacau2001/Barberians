package Main.Factions.FactionPlayers;

import java.util.HashMap;

import org.bukkit.entity.Player;
import Main.Factions.Factions.Faction;
import Main.Factions.Factions.FactionManager;
import Main.Utils.Utils.Boxes.Box;

public class FactionPlayer 
{
	
	public static FactionManager factionmanager = new FactionManager();
	public static FactionPlayersManager factionplayermanager = new FactionPlayersManager();
	
	//Player Object
	
	private Player Player;
	private Faction Faction;
	private int Money;
	private float Kills;
	private float Deaths;
	private int Power;
	private int MaxPower;
	private int Cash;
	private String FactionRank;
	private Long LastLogin;
	private String Name;
	private Long Mute;
	private HashMap<Box, Integer> BoxKeys = new HashMap<>(); 
	private int Orbs;
	
	public FactionPlayer(Player Player, int Money, int Kills, int Deaths, int Cash, String FactionRank, int Orbs){
		this.Player=Player;
		this.Money = Money;
		this.Kills = Kills;
		this.Deaths = Deaths;
		this.setPower(5);
		this.setMaxPower(5);
		this.Cash = Cash;
		this.FactionRank = FactionRank;
		this.LastLogin = null;
		this.Name = Player.getName();
		this.Orbs=Orbs;
	}
	public Player getPlayer() 
	{
		return Player;
	}
	public void setPlayer(Player player)
	{
		Player = player;
	}
	public Faction getFaction() 
	{
		return Faction;
	}
	public void setFaction(Faction faction) 
	{
		Faction = faction;
	}
	public int getMoney() 
	{		
		return Money;
	}
	public void setMoney(int money) 
	{
		Money = money;
	}
	public float getKDR() 
	{
		try{
			return (int)Kills/(int)Deaths;
		}
		catch(Exception e){return 0;}
	}
	public int getKills()
	{
		return (int)Kills;
	}
	public void setKills(int kills)
    {
		Kills = kills;
	}
	public int getDeaths()
	{
		return (int)Deaths;
	}
	public void setDeaths(int deaths) 
	{
		Deaths = deaths;
	}
	public String showMoney(){
		return new Long((int)getMoney()).longValue()+"";
	}
	public String showCash(){
		return new Long((int)getCash()).longValue()+"";
	}
	public int getPower() {
		return Power;
	}
	public void setPower(int power) {
		Power = power;
	}
	public int getMaxPower() {
		return MaxPower;
	}
	public void setMaxPower(int maxPower) {
		MaxPower = maxPower;
	}
	public int getCash() {
		return Cash;
	}
	public void setCash(int cash) {
		Cash = cash;
	}
	public String getFactionRank() {
		return FactionRank;
	}
	public void setFactionRank(String factionRank) {
		FactionRank = factionRank;
	}
	public Long getLastLogin() {
		return LastLogin;
	}
	public void setLastLogin(Long lastLogin) {
		LastLogin = lastLogin;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Long getMute() {
		return Mute;
	}

	public void setMute(Long mute) {
		Mute = mute;
	}

	public HashMap<Box, Integer> getBoxKeys() {
		return BoxKeys;
	}

	public void setBoxKeys(HashMap<Box, Integer> boxKeys) {
		BoxKeys = boxKeys;
	}

	public int getOrbs() {
		return Orbs;
	}

	public void setOrbs(int orbs) {
		Orbs = orbs;
	}

}
