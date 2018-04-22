package Main.Factions.Config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigMySQL
{

	//MySql Load
	public static String Host, DataBase, User, Password;
	public static int Port;
	
	public void loadConfigMYSQL(Plugin plugin)
	{
		
		File file = new File(plugin.getDataFolder(),"Config.yml");
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		
		//MySql Load
		
		Host = configuration.getString("Config.MySql.Host");
		DataBase = configuration.getString("Config.MySql.DataBase");
		User = configuration.getString("Config.MySql.User");
		Password = configuration.getString("Config.MySql.Password");
		
		Port = configuration.getInt("Config.MySql.Port");		
		
	}
	
}
