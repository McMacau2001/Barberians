package Main.Factions.Config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import Main.Utils.Utils.Essentials.Whitelist;


public class ConfigWhitelist {
	
	public void loadWhitelist(Plugin plugin){
		
		File file = new File(plugin.getDataFolder(), "Config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		Whitelist.setReason(config.getString("Config.Whitelist.Reason").replace("&", "§"));
		Whitelist.setState(config.getBoolean("Config.Whitelist.State"));
	}
	
	public void saveWhitelist(Plugin plugin){
		
		File file = new File(plugin.getDataFolder(), "Config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		
		config.set("Config.Whitelist.Reason", Whitelist.getReason());
		config.set("Config.Whitelist.State", Whitelist.getState());
		
		try{
			config.save(file);
		}catch(Exception e){}
	}

}
