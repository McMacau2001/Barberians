package Main.Factions.Utils.ApiMenu;

import org.bukkit.entity.HumanEntity;

public abstract class MenuBuilder<T> {

	public MenuBuilder() {
	}

	@SuppressWarnings("rawtypes")
	public abstract MenuBuilder show(HumanEntity... viewers);
	@SuppressWarnings("rawtypes")
	public abstract MenuBuilder refreshContent();
	@SuppressWarnings("hiding")
	public abstract <T> T build();
	public abstract void dispose();

}
