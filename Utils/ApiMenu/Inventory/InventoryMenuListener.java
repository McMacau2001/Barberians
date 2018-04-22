package Main.Factions.Utils.ApiMenu.Inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface InventoryMenuListener {

	void interact(Player player, ClickType action, int slot);

}
