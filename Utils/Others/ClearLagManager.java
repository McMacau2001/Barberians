package Main.Factions.Utils.Others;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;


public class ClearLagManager implements Listener{
	
	public static int taskid=0;

	public static void clearLag()
	{
		try{
			Thread t = new Thread(() -> {
				int x = 0;
		        for(World world :  Bukkit.getWorlds()){
		        	List<Entity> enList = world.getEntities();
		        	for (Entity current : enList)
		        	{
		        		if ((current instanceof Item))
		        		{
		        			x++;
		        			current.remove();
		        		}
		        	}
		        }
		        Bukkit.broadcastMessage("§a[Info] §7Foram removidos §a" + x + " §7items.");
			});
		t.start();
		}catch(Exception  ex){}
	}
	
	@EventHandler
	public void items(ItemDespawnEvent e)
	{
		e.setCancelled(true);
	}
}
