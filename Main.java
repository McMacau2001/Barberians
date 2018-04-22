package Main.Factions;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import Main.Factions.Config.ConfigBoxes;
import Main.Factions.Config.ConfigKits;
import Main.Factions.Config.ConfigMySQL;
import Main.Factions.Config.ConfigWarps;
import Main.Factions.Config.ConfigWhitelist;
import Main.Factions.FactionPlayers.FactionPlayerMySql;
import Main.Factions.Factions.FactionMySql;
import Main.Factions.Factions.Comands.ComandsFaction;
import Main.Factions.Factions.Menus.MenuFaction;
import Main.Factions.Factions.Menus.Listeners.InventoryClickListener;
import Main.Factions.Listeners.PlayerChatListener;
import Main.Factions.Listeners.PlayerCommandProcessListener;
import Main.Factions.Listeners.PlayerJoinListener;
import Main.Factions.Listeners.PlayerQuitListener;
import Main.Factions.MySql.MySqlConnection;
import Main.Factions.MySql.MySqlTables;
import Main.Factions.Utils.APIScoreBoard;
import Main.Factions.Utils.ApiMenu.Inventory.InventoryListener;
import Main.Factions.Utils.Others.ClearLagManager;
import Main.Factions.Utils.Others.ScoreBoardManager;
import Main.Utils.Utils.Essentials.Vanish;

public class Main extends JavaPlugin
{
	public static Main instance;
	public static Main main;
	public InventoryListener inventoryListener;
	
    public static Main getInstance() {
    	return instance;
    }
    
	//File 
	File file = new File(this.getDataFolder(),"Config.yml");
	
	public static ConfigMySQL configmysql = new ConfigMySQL();
	public static MySqlConnection mysqlconnection = new MySqlConnection();
	public static MySqlTables mysqltables = new MySqlTables();
	public static FactionPlayerMySql factionplayermysql = new FactionPlayerMySql();
	public static ConfigWarps configwarps = new ConfigWarps();
	public static FactionMySql factionmysql = new FactionMySql();
	public static MenuFaction menufaction = new MenuFaction();
	public static ScoreBoardManager scoreboardmanager = new ScoreBoardManager();
	public static Vanish vanish = new Vanish();
	public static ConfigWhitelist configwhitelist = new ConfigWhitelist();
	public static ConfigKits configKits = new ConfigKits();
	public static ConfigBoxes configboxes = new ConfigBoxes();
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() 
	{
		instance = this;
		main=this;
		
		//Load Config
		if(!file.exists()) this.saveResource("Config.yml", false);
		configmysql.loadConfigMYSQL(this);
		configwarps.loadConfigWarps(this);
		configwhitelist.loadWhitelist(this);
		configKits.loadKits(this);
		configboxes.loadBoxes(this);
		
		//MysqlConncetion
		String Status="";
		if(mysqlconnection.MySQLConnection()!=null)Status="Enabled";
		else Status="Disabled";
		
		//Load PlayerFaction
		factionplayermysql.loadAllPlayersFaction();
		
		//LoadMenus 
		menufaction.LoadFactionInventory();
		
		//Eventos
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerCommandProcessListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
		getServer().getPluginManager().registerEvents(new ClearLagManager() , this);
		Bukkit.getPluginManager().registerEvents(this.inventoryListener = new InventoryListener(this), this);
		
		//Coamndos
		getCommand("facçao").setExecutor(new ComandsFaction());
		
		//LoadMysqlTables
		mysqltables.MySqlTablesFactions();
		mysqltables.MySqlTablesPlayers();
		mysqltables.MySqlTablesBans();
		mysqltables.MySqlTableBoxes();
		
		//Log the Plugin
		this.getServer().getLogger().info("###################################");
		this.getServer().getLogger().info("   Factions Pluginv1.1(beta)   ");
		this.getServer().getLogger().info("   Status: Enabled");
		this.getServer().getLogger().info("   DataBase: "+Status);
		this.getServer().getLogger().info("###################################");
	
		//ScoreBoard
		Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
	        @SuppressWarnings("static-access")
			public void run() {
				for (APIScoreBoard boards : scoreboardmanager.scoreboards.values())boards.updateScoreboard();
	        }
	    }, 0L, 20*5);

	}
	
	@Override
	public void onDisable() 
	{
		//Save PlayerFaction
		factionplayermysql.saveAllPlayersFaction();
		factionmysql.saveAllFaction();
		configwhitelist.saveWhitelist(this);
		configKits.saveKits(this);
		configboxes.saveBoxes(this);
		
		//Log the Plugin
		this.getServer().getLogger().info("###################################");
		this.getServer().getLogger().info("   Factions Pluginv1.1(beta)   ");
		this.getServer().getLogger().info("   Status: Disabled");
		this.getServer().getLogger().info("###################################");
		
	}

}
