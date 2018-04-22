package Main.Factions.Factions.Menus;

import Main.Factions.Factions.Faction;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuAlly{
	
	ItemStack Item;
	ItemMeta Meta;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	List<String> Lore = new ArrayList();
  
	public Inventory loadAlly(Faction f)
  	{
  		Inventory inv = Bukkit.createInventory(null, 27, "§aFacções Aliadas:");
	    
	    this.Item = new ItemStack(Material.PAPER);
	    this.Meta = this.Item.getItemMeta();
	    
	    this.Meta.setDisplayName("§aConvites de outras Facções");
	    
	    this.Lore.add("");
	    this.Lore.add("  §7Lista de convites de todas as Facções  ");
	    this.Lore.add("  §7que se querem aliar à tua!  ");
	    this.Lore.add("");
	    
	    this.Meta.setLore(this.Lore);
	    this.Item.setItemMeta(this.Meta);
	    
	    inv.setItem(4, this.Item);
	    this.Lore.clear();
	    
	    this.Item = new ItemStack(Material.BANNER, f.getAllies().size());
	    BannerMeta BannerMeta = (BannerMeta)this.Item.getItemMeta();
	    
	    BannerMeta.setBaseColor(DyeColor.LIME);
	    BannerMeta.setDisplayName("§aFacções Aliadas");
	    
	    this.Lore.add("");
	    for (Faction factions : f.getAllies()) {
	    	this.Lore.add("  " + factions.getTag() + " " + factions.getName() + ": " + factions.getUUID());
	    }
	    if (f.getAllies().size() == 0) {
	    	this.Lore.add("  §cNenhuma Facção Aliada.  ");
	    }
	    this.Lore.add("");
	    
	    BannerMeta.setLore(this.Lore);
	    
	    this.Item.setItemMeta(BannerMeta);
	    
	    inv.setItem(9, this.Item);
	    this.Lore.clear();
	    
	    this.Lore.clear();
	    
	    this.Item = new ItemStack(Material.ARROW);
	    ItemMeta ItemMeta = this.Item.getItemMeta();
	    
	    ItemMeta.setDisplayName("§eVoltar ao menu");
	    
	    this.Lore.add("");
	    this.Lore.add("  §7Clica para voltares ao menu principal  ");
	    this.Lore.add("  §7da tua Facção.");
	    this.Lore.add("");
	    
	    ItemMeta.setLore(this.Lore);
	    this.Item.setItemMeta(ItemMeta);
	    
	    inv.setItem(22, this.Item);
	    this.Lore.clear();
	    
	    return inv;
  	}
}
