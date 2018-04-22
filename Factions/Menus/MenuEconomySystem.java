package Main.Factions.Factions.Menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Main.Factions.FactionPlayers.FactionPlayersManager;
import Main.Factions.Utils.APIAnvil;
import Main.Factions.Utils.APIAnvil.AnvilClickEvent;
import Main.Factions.Utils.APIEconomy;

public class MenuEconomySystem{
	
	public static APIEconomy economy =  new APIEconomy();
	public static FactionPlayersManager factionplayermanager = new FactionPlayersManager();

	ItemStack Item;
	ItemMeta Meta;
	
	public void factiondepositMoney(final Player p)
	{
		APIAnvil anvil = new APIAnvil(p, new APIAnvil.AnvilClickEventHandler() {
			
			@SuppressWarnings("static-access")
			@Override
			public void onAnvilClick(AnvilClickEvent event) {
				if(event.getSlot() == APIAnvil.AnvilSlot.OUTPUT){
					event.setWillClose(true);
					event.setWillDestroy(true);
					
					try{
						int money = Integer.parseInt(event.getName());
						
						if(factionplayermanager.getFactionPlayer(p).getMoney()>=money && money>0)
						{
							economy.addFactionMoney(factionplayermanager.getFactionPlayer(p).getFaction(), money);
							economy.removePlayerMoney(factionplayermanager.getFactionPlayer(p), money);
							factionplayermanager.getFactionPlayer(p).getFaction().sendNotifyJoinLeaveMenssage("§eO "+factionplayermanager.getFactionPlayer(p).getFactionRank()+" §e"+p.getName()+" depositou §a"+event.getName()+"§.");
						}
						else p.sendMessage("§cNão tens esse dinheiro para depositar.");
						
					}catch(Exception e){
						p.sendMessage("§cO valor introduzido não é valido.");
					}

				}
				else{
					event.setWillClose(false);
					event.setWillDestroy(false);
				}
				
			}
		});
		Item = new ItemStack(Material.PAPER);
		Meta=Item.getItemMeta();
		
		Meta.setDisplayName("0");
		Item.setItemMeta(Meta);
		
		anvil.setSlot(APIAnvil.AnvilSlot.INPUT_LEFT, Item);
		anvil.open();
	}
	public void factionupMoney(Player p)
	{
			APIAnvil anvil = new APIAnvil(p, new APIAnvil.AnvilClickEventHandler() {
			
			@SuppressWarnings("static-access")
			@Override
			public void onAnvilClick(AnvilClickEvent event) {
				if(event.getSlot() == APIAnvil.AnvilSlot.OUTPUT){
					event.setWillClose(true);
					event.setWillDestroy(true);
					
					try{
						int money = Integer.parseInt(event.getName());
						
						if(factionplayermanager.getFactionPlayer(p).getFaction().getMoney()>=money && money>0)
						{
							economy.removeFactionMoney(factionplayermanager.getFactionPlayer(p).getFaction(), money);
							economy.addPlayerMoney(factionplayermanager.getFactionPlayer(p), money);
							factionplayermanager.getFactionPlayer(p).getFaction().sendNotifyJoinLeaveMenssage("§eO "+factionplayermanager.getFactionPlayer(p).getFactionRank()+" §e"+p.getName()+" levantou §a"+event.getName()+"€.");
						}
						else p.sendMessage("§cA Facção nao possui esse dinheiro para ser levantado.");
						
					}catch(Exception e){
						p.sendMessage("§cO valor introduzido não é valido.");
					}

				}
				else{
					event.setWillClose(false);
					event.setWillDestroy(false);
				}
				
			}
		});
		Item = new ItemStack(Material.PAPER);
		Meta=Item.getItemMeta();
		
		Meta.setDisplayName("0");
		Item.setItemMeta(Meta);
		
		anvil.setSlot(APIAnvil.AnvilSlot.INPUT_LEFT, Item);
		anvil.open();

	}

}
