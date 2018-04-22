package Main.Factions.Factions.Menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import Main.Factions.FactionPlayers.FactionPlayer;
import Main.Factions.Factions.Faction;


public class MenuFaction 
{
	public static Inventory inv, inventory;
	private Faction f;
	private List<String> lore = new ArrayList<>();
	
	public void openFactionInventory(FactionPlayer FactionPlayer){
		
		try{
		Thread t = new Thread(() -> {
			f = FactionPlayer.getFaction();
			inventory = Bukkit.createInventory(null, 54, inv.getTitle());
			int slot = 0;
			
			for(ItemStack item : inv.getContents())
			{
				if(item != null){
						ItemStack items = item.clone();
						ItemMeta itemmeta = items.getItemMeta();
						
		    			for (int i = 0; i < itemmeta.getLore().size(); i++){
		    		        String ss = itemmeta.getLore().get(i);    
		
		    		        lore.add(ss.replace("%faction%", f.getName())
		    		        		.replace("%faction_owner%", f.getOwner())
		    		        		.replace("%faction_date%", f.getDate())
		    		        		.replace("%faction_description%", f.getDescription())
		    		        		.replace("%faction_uuid%", ""+f.getUUID())
		    		        		.replace("%faction_tag%", f.getTag())
		    		        		.replace("%faction_ally%", ""+f.getAllies().size())
		    		        		.replace("%faction_enemeis%", ""+f.getEnemies().size())
		    		        		.replace("%faction_money%", ""+f.getMoney())
		    		        		);
		    			}
		    			
						if(itemmeta.getDisplayName().equals("§eFacções Aliadas") && f.getAllies().size()!=0) items.setAmount(f.getAllies().size());
						else if(itemmeta.getDisplayName().equals("§eFacções Enimigas") && f.getEnemies().size() != 0) items.setAmount(f.getEnemies().size());
						
		    			itemmeta.setLore(lore);
						items.setItemMeta(itemmeta);
	
					inventory.setItem(slot,items);
					lore.clear();
				}
				slot++;
				
			}
			
			inventory.setItem(34, f.getBanner());
			inventory.setItem(10, loadPlayerOwner(f));
			FactionPlayer.getPlayer().openInventory(inventory);
		});

		t.start();
		}catch(Exception  ex){
            ex.printStackTrace();

		}
	}

	public ItemStack loadPlayerOwner(Faction f)
	{
		ItemStack Owner = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta OwnerMeta = (SkullMeta)Owner.getItemMeta();
		List<String> OwnerLore = new ArrayList<>();
	
		OwnerMeta.setDisplayName("§a§l"+f.getName());
		OwnerMeta.setOwner(f.getOwner());
		
		OwnerLore.add("");
		OwnerLore.add("  §7Membros Online: §a"+f.getOnlineMembers()+"/"+f.getMembers().size()+"");
		OwnerLore.add("");
		OwnerLore.add("  §7Clica para veres todos os membros da tua");
		OwnerLore.add("  §7Facção e para ver as suas respetivas Stats.  ");
		OwnerLore.add("");
		OwnerMeta.setLore(OwnerLore);
		
		Owner.setItemMeta(OwnerMeta);
		Owner.setAmount(f.getMembersOnline().size());
		
		return Owner;
	}
	
	public void LoadFactionInventory()
	{

		inv = Bukkit.createInventory(null, 54, "§aMenu da Facção:");
		
		
		ItemStack Beacon = new ItemStack(Material.BEACON);
		ItemMeta BeaconMeta = Beacon.getItemMeta();
		List<String> BeaconLore = new ArrayList<>();
		
		BeaconMeta.setDisplayName("§eBeacons da Facção");
		
		BeaconLore.add("");
		BeaconLore.add("  §7Beacons da Facção: §cBrevemente");
		BeaconLore.add("");
		BeaconLore.add("  §7Clica para veres todos os Beacons que a  ");
		BeaconLore.add("  §7tua Facção possui.");
		BeaconLore.add("");
		BeaconMeta.setLore(BeaconLore);
		
		Beacon.setItemMeta(BeaconMeta);
		
		Beacon.setAmount(0);
		
		inv.setItem(13, Beacon);
		
		ItemStack Spawner = new ItemStack(Material.MOB_SPAWNER);
		ItemMeta SpawnerMeta = Spawner.getItemMeta();
		List<String> SpawnerLore = new ArrayList<>();
		
		SpawnerMeta.setDisplayName("§eSpawners da Facção");
		
		SpawnerLore.add("");
		SpawnerLore.add("  §7Spawners da Facção: §cBrevemente");
		SpawnerLore.add("");
		SpawnerLore.add("  §7Clica para veres todos os Spawners que a  ");
		SpawnerLore.add("  §7tua Facção possui.");
		SpawnerLore.add("");
		SpawnerMeta.setLore(SpawnerLore);
		
		Spawner.setItemMeta(SpawnerMeta);
		
		Spawner.setAmount(0);
		
		inv.setItem(14, Spawner);
		
		ItemStack BookQuill = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta BookQuillMeta = BookQuill.getItemMeta();
		List<String> BookQuillLore = new ArrayList<>();
		
		BookQuillMeta.setDisplayName("§eEditar Facção");
		
		BookQuillLore.add("");
		BookQuillLore.add("  §7Clica para poderes alterar todas as ");
		BookQuillLore.add("  §7configurações da tua Facção.");
		BookQuillLore.add("");
		BookQuillMeta.setLore(BookQuillLore);
		
		BookQuill.setItemMeta(BookQuillMeta);
		
		inv.setItem(16, BookQuill);
		
		ItemStack Book = new ItemStack(Material.BOOK);
		ItemMeta BookMeta = Book.getItemMeta();
		List<String> BookLore = new ArrayList<>();
		
		BookMeta.setDisplayName("§eInformações da Facção");
		
		BookLore.add("§7Facção ID: %faction_uuid%");
		BookLore.add("");
		BookLore.add("  §eInformações:");
		BookLore.add("    §7Nome: §a%faction%");
		BookLore.add("    §7TAG: §a[#%faction_tag%]");
		BookLore.add("    §7Dono: §a%faction_owner%");
		BookLore.add("    §7Criação: §a%faction_date%");
		BookLore.add("    §7Descrição: %faction_description%");
		BookLore.add("");
		BookMeta.setLore(BookLore);
		
		Book.setItemMeta(BookMeta);
		
		inv.setItem(28, Book);
		
		ItemStack Home = new ItemStack(Material.BED);
		ItemMeta HomeMeta = Home.getItemMeta();
		List<String> HomeLore = new ArrayList<>();
		
		HomeMeta.setDisplayName("§eBase");
		
		HomeLore.add("");
		HomeLore.add("  §7Clica para te teleportares para a ");
		HomeLore.add("  §7base da tua Facção.");
		HomeLore.add("");
		HomeMeta.setLore(HomeLore);
		
		Home.setItemMeta(HomeMeta);
		
		inv.setItem(29, Home);
		
		ItemStack Dirt = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta DirtMeta = (SkullMeta) Dirt.getItemMeta();
		List<String> DirtLore = new ArrayList<>();
		
		DirtMeta.setDisplayName("§eTerrenos");
		DirtMeta.setOwner("0qt");
		
		DirtLore.add("");
		DirtLore.add("  §7Terrenos Conquistados: §cBrevemente");
		DirtLore.add("");
		DirtLore.add("  §7Clica para veres e gerires os terrenos ");
		DirtLore.add("  §7da tua Facção.");
		DirtLore.add("");
		DirtMeta.setLore(DirtLore);
		
		Dirt.setItemMeta(DirtMeta);
		
		inv.setItem(30, Dirt);
		
		ItemStack Ally = new ItemStack(Material.BANNER);
		BannerMeta  AllyMeta = (BannerMeta) Ally.getItemMeta();
		List<String> AllyLore = new ArrayList<>();
		
		AllyMeta.setBaseColor(DyeColor.LIME);
		AllyMeta.setDisplayName("§eFacções Aliadas");
		
		AllyLore.add("");
		AllyLore.add("  §7Facções Aliadas: §a%faction_ally%/5");
		AllyLore.add("");
		AllyLore.add("  §7Clica para veres a lista de todas as ");
		AllyLore.add("  §7Facções que se aliaram á tua!  ");
		AllyLore.add("");
		AllyMeta.setLore(AllyLore);
		
		Ally.setItemMeta(AllyMeta);
		Ally.setAmount(0);
		
		inv.setItem(31, Ally);
		
		ItemStack Enemie = new ItemStack(Material.BANNER);
		BannerMeta EnemieMeta = (BannerMeta) Enemie.getItemMeta();
		List<String> EnemieLore = new ArrayList<>();
		
		EnemieMeta.setBaseColor(DyeColor.RED);
		EnemieMeta.setDisplayName("§eFacções Enimigas");
		
		EnemieLore.add("");
		EnemieLore.add("  §7Facções Enimigas: §a%faction_enemeis%/5");
		EnemieLore.add("");
		EnemieLore.add("  §7Clica para veres a lista de todas as ");
		EnemieLore.add("  §7Facções que se tornaram inimigas á tua!  ");
		EnemieLore.add("");
		EnemieMeta.setLore(EnemieLore);	
		
		Enemie.setItemMeta(EnemieMeta);
		Enemie.setAmount(0);
		
		inv.setItem(32, Enemie);
		
		ItemStack Leave = new ItemStack(Material.WOOD_DOOR);
		ItemMeta LeaveMeta = Leave.getItemMeta();
		List<String> LeaveLore = new ArrayList<>();
		
		LeaveMeta.setDisplayName("§eSair da Facção");
		
		LeaveLore.add("");
		LeaveLore.add("  §7Clica para saires da Facção. ");
		LeaveLore.add("");
		LeaveLore.add("  §c§lAtenção:");
		LeaveLore.add("  §cSe fores o Dono da facção e se saires dela,  ");
		LeaveLore.add("  §ca mesma será automaticamente apagada.");
		LeaveLore.add("");
		LeaveMeta.setLore(LeaveLore);
		
		Leave.setItemMeta(LeaveMeta);
		
		inv.setItem(43, Leave);
		
		ItemStack Ranks = new ItemStack(Material.PAPER);
		ItemMeta RanksMeta = Ranks.getItemMeta();
		List<String> RanksLore = new ArrayList<>();
		
		RanksMeta.setDisplayName("§eGerir Ranks e Permissões");
		
		RanksLore.add("");
		RanksLore.add("  §7Clica para gerires ranks e as suas  ");
		RanksLore.add("  §7respetivas permissões e aplica-as aos jogadores.  ");
		RanksLore.add("");
		RanksMeta.setLore(RanksLore);
		
		Ranks.setItemMeta(RanksMeta);
		
		inv.setItem(38, Ranks);
		
		ItemStack Chest = new ItemStack(Material.CHEST);
		ItemMeta ChestMeta = Chest.getItemMeta();
		List<String> ChestLore= new ArrayList<>();
		
		ChestMeta.setDisplayName("§eBau da Facção");
		
		ChestLore.add("");
		ChestLore.add("  §7Clica para teres acesso ao Bau da Facção.  ");
		ChestLore.add("");
		ChestMeta.setLore(ChestLore);
		
		Chest.setItemMeta(ChestMeta);
		
		inv.setItem(39, Chest);
		
		ItemStack Money = new ItemStack(Material.DOUBLE_PLANT);
		ItemMeta MoneyMeta = Money.getItemMeta();
		List<String> MoneyLore = new ArrayList<>();
		
		MoneyMeta.setDisplayName("§eDinheiro");
		
		MoneyLore.add("");
		MoneyLore.add("  §7Dinheiro: §a%faction_money%€  ");
		MoneyLore.add("");
		MoneyLore.add("  §7Clica para depositar ou levantar  ");
		MoneyLore.add("  §7dinheiro da Facção.");
		MoneyLore.add("");
		MoneyMeta.setLore(MoneyLore);
		
		Money.setItemMeta(MoneyMeta);
		
		inv.setItem(40, Money);

	}
}
