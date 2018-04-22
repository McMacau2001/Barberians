package Main.Factions.Utils.ApiMenu.Inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public interface APIMenuListener {

	void onInteract(Player player, ClickType action, ItemStack item);

}
