package Main.Factions.Factions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

import Main.Factions.FactionPlayers.FactionPlayer;
import Main.Factions.FactionPlayers.FactionPlayerMySql;
import Main.Factions.Utils.APITitle;
import io.netty.util.internal.ThreadLocalRandom;

public class FactionManager 
{
	public static FactionMySql factionmysql = new FactionMySql();
	public static FactionPlayerMySql factionplayermysql = new FactionPlayerMySql();
	
	public static List<Faction> factions = new ArrayList<>();
	
    public static int generateInt(int min, int max)
	{
	   return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
    
	public Faction getFaction(int UUID)
	{
		for(Faction f: factions)if(f.getUUID()==UUID) return f;
		return null;
	}

	public boolean checkIfisLetter(String string)
	{
		boolean check = false;
		string = string.toUpperCase();
		for(char c :  string.toCharArray()){
			switch(c){
				case 'A':
				case 'B':
				case 'C':
				case '§':
				case 'D':
				case 'E':
				case 'F':
				case 'G':
				case 'H':
				case 'I':
				case 'J':
				case 'K':
				case 'L':
				case 'M':
				case 'N':
				case 'O':
				case 'P':
				case 'Q':
				case 'R':
				case 'S':
				case 'T':
				case 'U':
				case 'V':
				case 'W':
				case 'X':
				case 'Y':
				case 'Z':
					check=true;
					break;
				default:
					return false;
					
			}
		}
		
		return check;
	}
	
	public void createFaction(FactionPlayer factionplayer, String Name, String Tag){
		
		int UUID = generateInt(100000, 999999);
		while(factionmysql.isFactionExists(UUID))UUID = generateInt(100000, 999999);
		
		Faction f = new Faction(UUID,factionplayer.getPlayer().getName(), Tag, Name);
		factions.add(f);
		f.addPlayerToClan(factionplayer, f);
		f.setChest(Bukkit.createInventory(null, 54,"§aBau da Facção:")); 
		
        String date;
        
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        fmt.setTimeZone(TimeZone.getTimeZone("Western European Time"));
        date = fmt.format(new Date());
		f.setDate(date);
		
		
		factionmysql.createFaction(f);
		APITitle title = new APITitle("§fPARABENS", "§eAcabas-te de criar a facção "+Name, 1, 2, 1);
		title.send(factionplayer.getPlayer());
		factionplayer.getPlayer().sendMessage("§ePara adicionares uma descrição á tua facção usa este comando §a/faccao desc <descrição>§e.");
		factionplayer.getPlayer().playSound(factionplayer.getPlayer().getLocation(), Sound.LEVEL_UP, 2F, 1F);
		factionplayer.setFactionRank("§6[Dono]");
		
	}
	
	
}
