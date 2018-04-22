package Main.Factions.Factions.Menus.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import Main.Factions.FactionPlayers.FactionPlayersManager;
import Main.Factions.Factions.Menus.MenuAlly;
import Main.Factions.Factions.Menus.MenuEconomy;
import Main.Factions.Factions.Menus.MenuEconomySystem;
import Main.Factions.Factions.Menus.MenuFaction;
import Main.Factions.Factions.Menus.MenuMembersList;
import Main.Factions.Factions.Menus.MenuRanks;


public class InventoryClickListener implements Listener
{
	public static FactionPlayersManager factionplayermanager = new FactionPlayersManager();
	public static MenuMembersList menumembers = new MenuMembersList();
	public static MenuFaction menufaction = new MenuFaction();
	public static MenuEconomy menueconomy = new MenuEconomy();
	public static MenuEconomySystem menueconomysistem = new MenuEconomySystem();
	public static MenuRanks menuranks = new MenuRanks();
	public static MenuAlly menually = new MenuAlly();
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void InventoryClick(InventoryClickEvent e)
	{
		if(e.getInventory().getName().equals("§aMenu da Facção:"))
		{
			try
			{
				if(e.getCurrentItem() instanceof ItemStack && e.getCurrentItem().getType() != Material.AIR)
				{
					e.setCancelled(true);
					e.getWhoClicked().closeInventory();
				
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eBau da Facção"))
						e.getWhoClicked().openInventory(factionplayermanager.getFactionPlayer((Player)e.getWhoClicked()).getFaction().getChest());
					else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§a§l"+factionplayermanager.getFactionPlayer((Player)e.getWhoClicked()).getFaction().getName()))
						e.getWhoClicked().openInventory(menumembers.openMenuMembersList(factionplayermanager.getFactionPlayer((Player)e.getWhoClicked())));
					else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eDinheiro"))
						e.getWhoClicked().openInventory(menueconomy.openMenuEconomy(factionplayermanager.getFactionPlayer((Player)e.getWhoClicked())));
					else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eBase"))
						if(factionplayermanager.getFactionPlayer((Player)e.getWhoClicked()).getFaction().getHome()!=null)e.getWhoClicked().teleport(factionplayermanager.getFactionPlayer((Player)e.getWhoClicked()).getFaction().getHome());
						else e.getWhoClicked().sendMessage("§cA tua facção ainda não possui base.");
					else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eGerir Ranks e Permissões"))
						e.getWhoClicked().openInventory(menuranks.openMenuRanks(factionplayermanager.getFactionPlayer((Player)e.getWhoClicked()).getFaction()));
					else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eFacções Aliadas"))
						e.getWhoClicked().openInventory(menually.loadAlly(factionplayermanager.getFactionPlayer((Player)e.getWhoClicked()).getFaction()));
				}
			}
			catch(Exception ex){}
		}
		else if(e.getInventory().getName().equals("§aMembros da Facção:"))
		{
			try
			{
				if(e.getCurrentItem() instanceof ItemStack && e.getCurrentItem().getType() != Material.AIR)
				{
					e.setCancelled(true);
					e.getWhoClicked().closeInventory();
					
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eVoltar ao menu"))
						menufaction.openFactionInventory(factionplayermanager.getFactionPlayer((Player)e.getWhoClicked()));
				}
			}
			catch(Exception ex){}
		}
		else if(e.getInventory().getName().equals("§aBanco da Facção:"))
		{
			try
			{
				if(e.getCurrentItem() instanceof ItemStack && e.getCurrentItem().getType() != Material.AIR)
				{
					e.setCancelled(true);
					e.getWhoClicked().closeInventory();
					
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eVoltar ao menu"))
						menufaction.openFactionInventory(factionplayermanager.getFactionPlayer((Player)e.getWhoClicked()));
					else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eDepositar Dinheiro"))
						menueconomysistem.factiondepositMoney((Player)e.getWhoClicked());
					else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eLevantar Dinheiro"))
						menueconomysistem.factionupMoney((Player)e.getWhoClicked());
				}
			}
			catch(Exception ex){}
		}
		else if(e.getInventory().getName().equals("§aGerir Ranks da Facção:"))
		{
			try
			{
				if(e.getCurrentItem() instanceof ItemStack && e.getCurrentItem().getType() != Material.AIR)
				{
					e.setCancelled(true);
					e.getWhoClicked().closeInventory();
					
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§7[Recruta]"))
						e.getWhoClicked().openInventory(menuranks.openMenuRanks(null, "§7[Recruta]"));
					else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§8[Membro]"))
						e.getWhoClicked().openInventory(menuranks.openMenuRanks(null, "§8[Membro]"));
					else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§e[Capitão]"))
						e.getWhoClicked().openInventory(menuranks.openMenuRanks(null, "§e[Capitão]"));
					else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eVoltar ao menu"))
						menufaction.openFactionInventory(factionplayermanager.getFactionPlayer((Player)e.getWhoClicked()));
				}
			}
			catch(Exception ex){}
		}
		else if(e.getInventory().getName().equals("§aGerir Permissões:"))
		{
			try
			{
				if(e.getCurrentItem() instanceof ItemStack && e.getCurrentItem().getType() != Material.AIR)
				{
					e.setCancelled(true);
					e.getWhoClicked().closeInventory();
					
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§eVoltar ao menu"))
						e.getWhoClicked().openInventory(menuranks.openMenuRanks(factionplayermanager.getFactionPlayer((Player)e.getWhoClicked()).getFaction()));
				}
			}
			catch(Exception ex){}
		}else if (e.getInventory().getName().equals("§aFacções Aliadas:")) {
			try
			{
				if (((e.getCurrentItem() instanceof ItemStack)) && (e.getCurrentItem().getType() != Material.AIR))
				{
					e.setCancelled(true);
					e.getWhoClicked().closeInventory();
					if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§eVoltar ao menu")) {
						menufaction.openFactionInventory(FactionPlayersManager.getFactionPlayer((Player)e.getWhoClicked()));
					}
				}
			}
			catch (Exception localException5) {}
		}
	}
}
