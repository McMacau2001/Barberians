package Main.Factions.Config;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import Main.Factions.Main;

public class ConfigWarps 
{
	
	public static HashMap<String,Location> Warps = new HashMap<>();
	
	public void loadConfigWarps(Plugin plugin)
	{
		File file = new File(plugin.getDataFolder(), "Config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		for(String key : config.getKeys(true))
		{
			if(key.contains("Warps"))
				if(!key.contains(".World") && !key.contains(".X") && !key.contains(".Y") && !key.contains(".Z") && !key.contains(".Yaw") && !key.contains(".Pitch") && key.length()>12){
					Location location = new Location(Bukkit.getWorld(config.getString(key+".World")), config.getDouble((key+".X")),config.getDouble((key+".Y")) ,config.getDouble((key+".Z")));
					location.setYaw((float)config.getDouble((key+".Yaw")));
					location.setPitch((float)config.getDouble((key+".Pitch")));
					
					Warps.put(key.replace("Config.Warps.", ""), location);
				}
		}
	}
	
	public void saveConfigWarp(String name, Location loc)
	{
		File file = new File(Main.main.getDataFolder(), "Config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		config.set("Config.Warps."+name+".World", loc.getWorld().getName());
		config.set("Config.Warps."+name+".X", loc.getX());
		config.set("Config.Warps."+name+".Y", loc.getY());
		config.set("Config.Warps."+name+".Z", loc.getZ());
		config.set("Config.Warps."+name+".Yaw", loc.getYaw());
		config.set("Config.Warps."+name+".Pitch", loc.getPitch());
		
		try{
			config.save(file);
		}catch(Exception e){}
		
		
	}
	
	public void removeWarp(String name)
	{
		File file = new File(Main.main.getDataFolder(), "Config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		config.set("Config.Warps."+name+"", null);
	}

}
