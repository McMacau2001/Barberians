package Main.Factions.Config;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import Main.Factions.Main;
import Main.Utils.Utils.Boxes.Box;
import Main.Utils.Utils.Boxes.BoxesFunctions;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class ConfigBoxes {
	
	public void loadBoxes(Plugin Plugin){
		
		File file = new File(Plugin.getDataFolder(),"Boxes.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		int x=1, i=1;
		while(config.getString("Boxes."+x+".Name") !=null){
			Box b = new Box(config.getString("Boxes."+x+".Name"), Material.valueOf(config.getString("Boxes."+x+".Block")), config.getString("Boxes."+x+".Permission"), EnumParticle.valueOf(config.getString("Boxes."+x+".Particle")));
			b.setColor(config.getString("Boxes."+x+".Color"));
			if(config.getString("Boxes."+x+".Location.World")!=null)b.setLocation(new Location(Bukkit.getWorld(config.getString("Boxes."+x+".Location.World")), config.getDouble("Boxes."+x+".Location.X"), config.getDouble("Boxes."+x+".Location.Y"), config.getDouble("Boxes."+x+".Location.Z")));
			
			while(config.getString("Boxes."+x+".Items."+i)!=null){
				b.getItems().add((ItemStack)config.get("Boxes."+x+".Items."+i));
				i++;
			}
			i=1;
			
			BoxesFunctions.Boxes.add(b);
			x++; 
		}
		
	}
	
	public void saveBoxes(Plugin Plugin){
		
		File file = new File(Plugin.getDataFolder(),"Boxes.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		int x=1, i=1;
		for(Box b: BoxesFunctions.Boxes){
			
			config.set("Boxes."+x+".Name",b.getName());//
			config.set("Boxes."+x+".Permission",b.getPermission());//
			config.set("Boxes."+x+".Particle",b.getParticle().name());//
			config.set("Boxes."+x+".Block",b.getBlock().name());//
			config.set("Boxes."+x+".Color",b.getColor());//
			config.set("Boxes."+x+".Location.World",b.getLocation().getWorld().getName());
			config.set("Boxes."+x+".Location.X",b.getLocation().getBlockX());
			config.set("Boxes."+x+".Location.Y",b.getLocation().getBlockY());
			config.set("Boxes."+x+".Location.Z",b.getLocation().getBlockZ());
			
			for(ItemStack item : b.getItems()){
				config.set("Boxes."+x+".Items."+i,item);
				i++;
			}

			i=1;
			x++;
		}
		
		try{
			config.save(file);
		}catch(Exception e){}
	}
	
	public static void remove(Box b){
		
		File file = new File(Main.main.getDataFolder(),"Boxes.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		int x=1;
		while(config.getString("Boxes."+x+".Name") !=null){
			if(config.getString("Boxes."+x+".Name").equals(b.getName()))config.set("Boxes."+x+"",null);
		}
		
		try{
			config.save(file);
		}catch(Exception e){}
	}

}
