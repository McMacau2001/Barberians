package Main.Factions.Utils;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

public class APIItem {
	
	private ItemStack Item;
	private Material Material;
	private ItemMeta ItemMeta;
	private int Amount;
	private List<String> Lore = new ArrayList<>();
	private ItemType Type;
	private SkullMeta SkullMeta;
	private BannerMeta BannerMeta;
	
	public enum ItemType {
		BANNER, PLAYERHEAD, ITEM
	}
	
	public APIItem(Material Material, int Amount){
		Item = new ItemStack(Material, Amount);
		ItemMeta = Item.getItemMeta();
		setType(ItemType.ITEM);
	}
	
	@SuppressWarnings("static-access")
	public APIItem(ItemType ItemType, int amount){
		if(ItemType.equals(ItemType.PLAYERHEAD)){
			Item = new ItemStack(Material.SKULL_ITEM, amount, (short) SkullType.PLAYER.ordinal());
			SkullMeta = (SkullMeta)Item.getItemMeta();
			setType(ItemType.PLAYERHEAD);
		}
		else if(ItemType.equals(ItemType.BANNER)){
			Item = new ItemStack(Material.BANNER, amount);
			BannerMeta = (BannerMeta)Item.getItemMeta();
			setType(ItemType.BANNER);
		}
	}
	
	public APIItem setPlayerHead(String Name){
		if(Type.equals(ItemType.PLAYERHEAD))this.SkullMeta.setOwner("McMacau");	
		return this;
	}
	
	public APIItem setBannerColor(DyeColor Color){
		if(Type.equals(ItemType.BANNER))BannerMeta.setBaseColor(Color);	
		return this;
	}
	
	public APIItem setData(MaterialData Data){
		if(Type.equals(ItemType.ITEM))this.Item.setData(Data);
		return this;
	}
	
	public APIItem setDurability(short Durability){
		if(Type.equals(ItemType.ITEM))this.Item.setDurability(Durability);
		return this;
	}
	
	public APIItem setDisplaName(String DisplayName){
		if(Type.equals(ItemType.ITEM))this.getItemMeta().setDisplayName(DisplayName);
		else if(Type.equals(ItemType.PLAYERHEAD))this.getSkullMeta().setDisplayName(DisplayName);
		else this.getBannerMeta().setDisplayName(DisplayName);
		
		return this;
	}
	
	public APIItem setLore(List<String> Lore){
		this.Lore=Lore;
		return this;
	}
	
	public APIItem addItemFlag(ItemFlag ItemFlag){
		if(Type.equals(ItemType.ITEM))this.getItemMeta().addItemFlags(ItemFlag);
		return this;
	}
	
	public APIItem addEnchantment(Enchantment Enchantment, int Level){
		if(Type.equals(ItemType.ITEM))this.getItemMeta().addEnchant(Enchantment, Level, true);
		else if(Type.equals(ItemType.PLAYERHEAD))this.getSkullMeta().addEnchant(Enchantment, Level, true);
		else this.getBannerMeta().addEnchant(Enchantment, Level, true);
			
		return this;
	}
	
	public APIItem addLoreLine(String Lore){
		this.Lore.add(Lore);
		return this;
	}
	
	public ItemStack create(){
		if(Type.equals(ItemType.ITEM)){
			this.ItemMeta.setLore(Lore);
			this.getItem().setItemMeta(ItemMeta);
		}
		else if(Type.equals(ItemType.PLAYERHEAD)){
			this.SkullMeta.setLore(Lore);
			this.getItem().setItemMeta(SkullMeta);
		}
		else{
			this.BannerMeta.setLore(Lore);
			this.getItem().setItemMeta(BannerMeta);
		}
		
		return this.Item;
	}

	public ItemStack getItem() {
		return Item;
	}

	public void setItem(ItemStack item) {
		Item = item;
	}

	public Material getMaterial() {
		return Material;
	}

	public void setMaterial(Material material) {
		Material = material;
	}

	public ItemMeta getItemMeta() {
		return ItemMeta;
	}

	public void setItemMeta(ItemMeta itemMeta) {
		ItemMeta = itemMeta;
	}

	public int getAmount() {
		return Amount;
	}

	public void setAmount(int amount) {
		Amount = amount;
	}

	public ItemType getType() {
		return Type;
	}

	public void setType(ItemType type) {
		Type = type;
	}

	public SkullMeta getSkullMeta() {
		return SkullMeta;
	}

	public void setSkullMeta(SkullMeta skullMeta) {
		SkullMeta = skullMeta;
	}

	public BannerMeta getBannerMeta() {
		return BannerMeta;
	}

	public void setBannerMeta(BannerMeta bannerMeta) {
		BannerMeta = bannerMeta;
	}
}