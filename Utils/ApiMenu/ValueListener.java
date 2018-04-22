package Main.Factions.Utils.ApiMenu;

import org.bukkit.entity.Player;
public interface ValueListener<T> {
	void onChange(Player player, T oldValue, T newValue);

}
