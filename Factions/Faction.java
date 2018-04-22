package Main.Factions.Factions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import Main.Factions.FactionPlayers.FactionPlayer;
import Main.Factions.FactionPlayers.FactionPlayerMySql;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Faction {
	
	public static FactionPlayerMySql factioplayernmysql = new FactionPlayerMySql();
	//Faction object
	
	private String Name; //
	private String Description="§7Esta facção nao possui descrição"; //
	private String Tag; //
	private Location Home=null;
	private String Owner; //
	private List<FactionPlayer> MembersOnline = new ArrayList<>(); //
	private List<String> Members = new ArrayList<>(); //
	private List<Faction> Allies = new ArrayList<>();
	private List<Faction> Enemies = new ArrayList<>();
	private ItemStack Banner; //
	private int Money; 
	private int Power; //
	private int UUID; //
	private float TotalKills;
	private float TotalDeaths;
	private String Date;
	private Inventory Chest;
	
	
	public Faction(int UUID, String Owner , String Tag, String Name)
	{
		this.Name = Name; 
		this.Tag = Tag.toUpperCase(); 
		this.Owner = Owner; 
		this.Banner = this.createClanBanner(Banner, Tag, DyeColor.WHITE, DyeColor.BLACK);
		this.setUUID(UUID);
	}
	
	public String getName() 
	{
		return Name;
	}
	public void setName(String name) 
	{
		Name = name;
	}
	public String getDescription() 
	{
		return Description;
	}
	public void setDescription(String description) 
	{
		Description = description;
	}
	public Location getHome() 
	{
		return Home;
	}
	public void setHome(Location home) 
	{
		Home = home;
	}
	public String getOwner() 
	{
		return Owner;
	}
	public void setOwner(String owner) 
	{
		Owner = owner;
	}
	public int getTotalMembers() 
	{
		return Members.size();
	}
	public List<String> getMembers() 
	{
		return Members;
	}
	public void setMembers(List<String> Member) 
	{
		Members = Member;
	}
	public List<Faction> getAllies() 
	{
		return Allies;
	}
	public void setAllies(List<Faction> allies) 
	{
		Allies = allies;
	}
	public List<Faction> getEnemies() 
	{
		return Enemies;
	}
	public void setEnemies(List<Faction> enemies)
	{
		Enemies = enemies;
	}
	public ItemStack getBanner() 
	{
		ItemMeta m = this.Banner.getItemMeta();
		List<String> BannerLore = new ArrayList<>();
		m.setDisplayName("§eBanner da Facção");
		    
	    BannerLore.add("");
		BannerLore.add("  §eFacção:");
		BannerLore.add("    §7Membros: §a"+this.getMembers().size()+"/16");
		BannerLore.add("    §7Kills: §a"+(int)this.getTotalKills());
		BannerLore.add("    §7Mortes: §a"+(int)this.getTotalDeaths());
		BannerLore.add("    §7KDR: §a"+this.getKDR());
		BannerLore.add("    §7Poder: §a"+this.getPower());
		BannerLore.add("    §7Dinheiro: §a"+this.getMoney()+"€");
		BannerLore.add("");
		
		m.setLore(BannerLore);
		m.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		
		this.Banner.setItemMeta(m);
		
		return Banner;
	}
	public void setBanner(ItemStack banner) 
	{
		Banner = banner;
	}
	public int getMoney() 
	{
		return Money;
	}
	public void setMoney(int money) 
	{
		Money = money;
	}
	public String showMoney(){
		return new Long((int)getMoney()).longValue()+"";
	}
	public float getKDR() 
	{
		if(TotalKills !=0 || TotalDeaths !=0)return TotalKills/TotalDeaths;
		return 0;
	}
	public int getPower() 
	{
		return Power;
	}
	public void setPower(int power) 
	{
		Power = power;
	}

	public String getTag() {
		return Tag;
	}

	public void setTag(String tag) {
		Tag = tag;
	}
	public void removePowerClan(int Power){
		this.Power = this.Power - Power;
	}
	public void sendAlert(String Menssage){
		for(FactionPlayer id : MembersOnline){
			try{
				Player p = id.getPlayer();
				p.sendMessage("");
				p.sendMessage("§c§lALERTA:");
				p.sendMessage("  "+Menssage);
				p.sendMessage("");
				p.playSound(p.getLocation(), Sound.GLASS, 2F, 1F);
			}catch(Exception e){}
		}
	}
	public void sendPlayerFactionMenssage(String Faction, String Rank, String Name , String Menssage)
	{
		for(FactionPlayer id : MembersOnline){
			try{
				Player p = id.getPlayer();
				TextComponent notify1 = new TextComponent("§a[Facção] "+Rank + " ");
				TextComponent notify2 = new TextComponent(Menssage);
				TextComponent notify = new TextComponent("§7"+Name+": ");
				notify.setClickEvent( new ClickEvent( ClickEvent.Action.SUGGEST_COMMAND, "/tell "+Name +" "));
				notify1.addExtra(notify);
				notify1.addExtra(notify2);
				p.spigot().sendMessage(notify1);
			}catch(Exception e){}
		}
	}
	public void sendNotifyJoinLeaveMenssage(String Menssage){
		for(FactionPlayer id : MembersOnline){
			try{
				Player p = id.getPlayer();
				TextComponent notify = new TextComponent("\n§a§lNOTICIA:");
				notify.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eClica para abrires o menu da tua facção.").create()));
				notify.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/f" ));
				p.spigot().sendMessage(notify);
				p.sendMessage("  "+Menssage);
				p.sendMessage("");
				p.playSound(p.getLocation(), Sound.EXPLODE, 2F, 1F);
			}catch(Exception e){}
		}
	}
	public void sendNotifyMenssage(String MenssageType, String Menssage){
		for(FactionPlayer id : MembersOnline){
			try{
				Player p = id.getPlayer();
				TextComponent notify = new TextComponent("\n§a§lNOTICIA:");
				notify.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eClica para abrires o menu da tua facção.").create()));
				notify.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/f" ));
				p.spigot().sendMessage(notify);
				p.sendMessage("  "+MenssageType);
				p.sendMessage("  "+Menssage);
				p.sendMessage("");
				p.playSound(p.getLocation(), Sound.EXPLODE, 2F, 1F);
			}catch(Exception e){}
		}
	}
	public void addPlayerToClan(FactionPlayer FactionPlayer, Faction Faction){
		
		Faction.sendNotifyJoinLeaveMenssage("§eO jogador §e"+FactionPlayer.getPlayer().getName()+"§e juntou-se á tua facção.");
		FactionPlayer.setFaction(Faction);
		FactionPlayer.setFactionRank("§7[Recruta]");
		
		Faction.getMembersOnline().add(FactionPlayer);
		Faction.getMembers().add(FactionPlayer.getName());
		Faction.setTotalKills(Faction.getTotalKills()+FactionPlayer.getKills());
		Faction.setTotalDeaths(Faction.getTotalDeaths()+FactionPlayer.getDeaths());
		Faction.setPower(Faction.getPower()+FactionPlayer.getPower());
		
		factioplayernmysql.savePlayerFaction(FactionPlayer,false);

	}
	public void removePlayerToClan(FactionPlayer FactionPlayer, Faction Faction){
		
		Faction.sendNotifyJoinLeaveMenssage("§cO jogador §7"+FactionPlayer.getPlayer().getName()+"§c saiu da tua facção.");
		
		FactionPlayer.setFaction(null);
		FactionPlayer.setFactionRank("null");
		Faction.getMembers().remove(FactionPlayer.getPlayer().getName());
		
		Faction.setTotalKills(Faction.getTotalKills()-FactionPlayer.getKills());
		Faction.setTotalDeaths(Faction.getTotalDeaths()-FactionPlayer.getDeaths());
		Faction.setPower(Faction.getPower()-FactionPlayer.getPower());
		
		factioplayernmysql.savePlayerFaction(FactionPlayer,false);
		
	}
	public int getOnlineMembers(){
		return this.getMembersOnline().size();
	}
	public int getMemberId(FactionPlayer f){
		int x=0;
		for(String fp : this.getMembers()){
			if(f.getName().equals(fp))return x;
			x++;
		}
		return 0;
	}
	public ItemStack createClanBanner(ItemStack banner, String Tag, DyeColor baseColor, DyeColor dyeColor) {
		Tag= ChatColor.stripColor(Tag.toUpperCase()).substring(0, 1);
		
		banner = new ItemStack(Material.BANNER);
		BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();
		bannerMeta.setBaseColor(baseColor);
		
		switch (Tag) {
		case "A":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_TOP));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_MIDDLE));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "B":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_TOP));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_MIDDLE));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "C":
		case "Ç":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_TOP));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "D":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_TOP));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.CURLY_BORDER));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "E":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_MIDDLE));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.STRIPE_RIGHT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_TOP));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "F":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_MIDDLE));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.STRIPE_RIGHT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_TOP));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "G":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.HALF_HORIZONTAL));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_TOP));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "H":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_MIDDLE));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "I":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_TOP));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_CENTER));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "J":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.HALF_HORIZONTAL));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "K":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_MIDDLE));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.HALF_VERTICAL_MIRROR));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.CROSS));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "L":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "M":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.TRIANGLE_TOP));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.TRIANGLES_TOP));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "N":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.DIAGONAL_RIGHT_MIRROR));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_DOWNRIGHT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "O":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_TOP));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "P":
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.HALF_HORIZONTAL));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.STRIPE_BOTTOM));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_TOP));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "Q":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_TOP));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.SQUARE_BOTTOM_RIGHT));
			break;
		case "R":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_TOP));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.HALF_HORIZONTAL_MIRROR));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_DOWNRIGHT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "S":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_TOP));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.RHOMBUS_MIDDLE));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_DOWNRIGHT));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.CURLY_BORDER));
			break;
		case "T":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_TOP));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_CENTER));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "U":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "V":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.TRIANGLES_BOTTOM));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_DOWNLEFT));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "W":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.TRIANGLE_BOTTOM));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.TRIANGLES_BOTTOM));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_LEFT));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_RIGHT));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "X":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_TOP));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.STRIPE_CENTER));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.CROSS));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "Y":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.CROSS));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.HALF_VERTICAL_MIRROR));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_DOWNLEFT));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		case "Z":
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_TOP));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_BOTTOM));
			bannerMeta.addPattern(new Pattern(dyeColor, PatternType.STRIPE_DOWNLEFT));
			bannerMeta.addPattern(new Pattern(baseColor, PatternType.BORDER));
			break;
		}
		banner.setItemMeta(bannerMeta);
		return banner;
	}

	public int getUUID() {
		return UUID;
	}

	public void setUUID(int uUID) {
		UUID = uUID;
	}

	public float getTotalKills() {
		return TotalKills;
	}

	public void setTotalKills(float totalKills) {
		TotalKills = totalKills;
	}

	public float getTotalDeaths() {
		return TotalDeaths;
	}

	public void setTotalDeaths(float totalDeaths) {
		TotalDeaths = totalDeaths;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public Inventory getChest() {
		return Chest;
	}

	public void setChest(Inventory chest) {
		Chest = chest;
	}

	public List<FactionPlayer> getMembersOnline() {
		return MembersOnline;
	}

	public void setMembersOnline(List<FactionPlayer> membersOnline) {
		MembersOnline = membersOnline;
	}

}
