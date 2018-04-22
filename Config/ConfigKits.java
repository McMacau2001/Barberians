package Main.Factions.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import Main.Factions.Main;
import Main.Utils.Utils.Kits.Kit;
import Main.Utils.Utils.Kits.KitsFuntions;


public class ConfigKits {
	
	public void loadKits(Plugin plugin){
		
		File file = new File(plugin.getDataFolder(), "Kits/");
		
		if(file.exists()==false)return;
    	if (file.listFiles().length>=1){
    		for(File f : file.listFiles()){
    			YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
    			
 
    				List<ItemStack> Items = new ArrayList<>();
    				
    				int x=1;
    				while(config.get("Kit.Items."+x) != null){
    					Items.add((ItemStack)config.get("Kit.Items."+x));
    					x++;
    				}
    				
    				Kit k = new Kit(config.getString("Kit.Name"),config.getString("Kit.Permission"), Items.toArray(new ItemStack[Items.size()]), config.getLong("Kit.Duration"));
    				KitsFuntions.Kits.add(k);
    		}
    	}
	}
	
	public void saveKits(Plugin plugin){
		for(Kit k: KitsFuntions.Kits){

			File file = new File(plugin.getDataFolder(), "Kits/"+k.getName()+".yml");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			config.set("Kit.Name", k.getName());
			config.set("Kit.Permission", k.getPermission());
			config.set("Kit.Duration", k.getDelay());
			
			int x=1;
			for(ItemStack i : k.getItems()){
				config.set("Kit.Items."+x, i);
				x++;
			}
			
			try{
				config.save(file);
			}catch(Exception e){}
		}
	}
	
	public static void removeKit(String Kit){
		
		File file = new File(Main.main.getDataFolder(), "Kits/"+Kit+".yml");		
		if(file.exists()) file.delete();
	}
	
	public static Long getDelayPlayer(String Kit, String Player){
		
		File file = new File(Main.main.getDataFolder(), "Kits/"+Kit+".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		return config.getLong("Delay."+Player);
	}
	
	public static boolean isPlayerRegistred(String Kit, String Player){
		File file = new File(Main.main.getDataFolder(), "Kits/"+Kit+".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		if(config.getString("Delay."+Player)==null)return false;
		else return true;
	}
	
	public static void registerPlayerPlayer(Kit Kit, String Player){
		
		File file = new File(Main.main.getDataFolder(), "Kits/"+Kit.getName()+".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		Calendar cal = Calendar.getInstance();
		Date d = new Date();
		cal.setTime(d);
		cal.add(Calendar.MINUTE,Kit.getDelay().intValue());
		
		config.set("Delay."+Player, cal.getTimeInMillis());

		
		try{
			config.save(file);
		}catch(Exception e){}
	}

	
}
