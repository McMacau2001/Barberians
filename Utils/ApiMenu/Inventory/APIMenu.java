package Main.Factions.Utils.ApiMenu.Inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import Main.Factions.Main;
import Main.Factions.Utils.ApiMenu.MenuBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.event.inventory.ClickType.*;

public class APIMenu extends MenuBuilder<Inventory> {

	public static final ClickType[] ALL_CLICK_TYPES = new ClickType[] {
			LEFT,
			SHIFT_LEFT,
			RIGHT,
			SHIFT_RIGHT,
			WINDOW_BORDER_LEFT,
			WINDOW_BORDER_RIGHT,
			MIDDLE,
			NUMBER_KEY,
			DOUBLE_CLICK,
			DROP,
			CONTROL_DROP };

	private Inventory inventory;

	private List<ItemCallback> callbackItems = new ArrayList<>();

	public APIMenu() {
	}

	public APIMenu(int size) {
		this();
		withSize(size);
	}

	public APIMenu(int size, String title) {
		this(size);
		withTitle(title);
	}

	public APIMenu(InventoryType type) {
		this();
		withType(type);
	}

	public APIMenu(InventoryType type, String title) {
		this(type);
		withTitle(title);
	}

	protected void initInventory(Inventory inventory) {
		if (this.inventory != null) { throw new IllegalStateException("Inventory already initialized"); }
		this.inventory = inventory;
	}

	protected void validateInit() {
		if (this.inventory == null) { throw new IllegalStateException("inventory not yet initialized"); }
	}

	public Inventory getInventory() {
		return inventory;
	}

	public APIMenu withSize(int size) {
		initInventory(Bukkit.createInventory(null, size));
		return this;
	}

	public APIMenu withType(InventoryType type) {
		initInventory(Bukkit.createInventory(null, type));
		return this;
	}

	public APIMenu withTitle(String title) {
		return withTitle(title, true);
	}

	public APIMenu withTitle(String title, boolean refresh) {
		validateInit();
		InventoryHelper.changeTitle(this.inventory, title);

		if (refresh) {
			for (HumanEntity viewer : this.inventory.getViewers()) {
				viewer.closeInventory();
				viewer.openInventory(this.inventory);
			}
		}
		return this;
	}

	public APIMenu withEventHandler(InventoryEventHandler eventHandler) {
		try {
			Main.instance.inventoryListener.registerEventHandler(this, eventHandler);
		} catch (IllegalArgumentException e) {
			throw e;
		}
		return this;
	}

	public APIMenu onInteract(InventoryMenuListener listener, ClickType... actions) {
		if (actions == null || (actions != null && actions.length == 0)) {
			throw new IllegalArgumentException("must specify at least one action");
		}
		try {
			Main.instance.inventoryListener.registerListener(this, listener, actions);
		} catch (IllegalArgumentException e) {
			throw e;
		}
		return this;
	}

	public APIMenu withItem(int slot, ItemStack item) {
		this.inventory.setItem(slot, item);
		return this;
	}

	public APIMenu withItem(final int slot, final ItemStack item, final APIMenuListener listener, ClickType... actions) {
		withItem(slot, item);
		onInteract(new InventoryMenuListener() {
			@Override
			public void interact(Player player, ClickType action, int slot_) {
				if (slot_ == slot) { listener.onInteract(player, action, item); }
			}
		}, actions);
		return this;
	}

	public APIMenu withItem(ItemCallback callback) {
		callbackItems.add(callback);
		return this;
	}

	@SuppressWarnings("unchecked")
	public Inventory build() {
		return this.inventory;
	}

	public APIMenu show(HumanEntity... viewers) {
		refreshContent();
		for (HumanEntity viewer : viewers) {
			viewer.openInventory(this.build());
		}
		return this;
	}

	public APIMenu refreshContent() {
		for (ItemCallback callback : callbackItems) {
			int slot = callback.getSlot();
			ItemStack item = callback.getItem();

			withItem(slot, item);
		}
		return this;
	}

	@Override
	public void dispose() {
		Main.instance.inventoryListener.unregisterAllListeners(getInventory());
	}

	public void unregisterListener(InventoryMenuListener listener) {
		try {
			Main.instance.inventoryListener.registerListener(this, listener, ALL_CLICK_TYPES);
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}
}
