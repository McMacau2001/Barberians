package Main.Factions.Utils.ApiMenu.Inventory;

import org.bukkit.inventory.Inventory;

import Main.Factions.Utils.ApiMenu.Util.AccessUtil;
import Main.Factions.Utils.ApiMenu.Util.Reflection;

public class InventoryHelper {

	static Class<?> obcCraftInventory;
	static Class<?> obcCraftInventoryCustom;
	static Class<?> obcMinecraftInventory;

	static {
		try {
			obcCraftInventory = Reflection.getOBCClass("inventory.CraftInventory");
			obcCraftInventoryCustom = Reflection.getOBCClass("inventory.CraftInventoryCustom");
			for (Class<?> c : obcCraftInventoryCustom.getDeclaredClasses()) {
				if (c.getSimpleName().equals("MinecraftInventory")) {
					obcMinecraftInventory = c;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void changeTitle(Inventory inv, String title) {
		try {
			Object minecrafInventory = AccessUtil.setAccessible(obcCraftInventory.getDeclaredField("inventory")).get(inv);
			AccessUtil.setAccessible(obcMinecraftInventory.getDeclaredField("title")).set(minecrafInventory, title);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
