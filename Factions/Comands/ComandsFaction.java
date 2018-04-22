package Main.Factions.Factions.Comands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Main.Factions.FactionPlayers.FactionPlayersManager;
import Main.Factions.Factions.FactionManager;
import Main.Factions.Factions.FactionMySql;
import Main.Factions.Factions.Invites.FactionsInvites;
import Main.Factions.Factions.Invites.FactionsInvitesManager;
import Main.Factions.Factions.Menus.MenuFaction;
import Main.Factions.Listeners.PlayerChatListener;
import Main.Utils.Utils.Teleport.TeleportDelaySystem;

public class ComandsFaction implements CommandExecutor
{

	public static FactionPlayersManager factionplayermanager = new FactionPlayersManager();
	public static FactionManager factionmanager = new FactionManager();
	public static FactionMySql factionmysl = new FactionMySql();
	public static FactionsInvitesManager factionsinvites = new FactionsInvitesManager();
	public static MenuFaction menufaction = new MenuFaction();
	public static TeleportDelaySystem teleportdelaysystem = new TeleportDelaySystem();
	public static PlayerChatListener playerchatlistener = new PlayerChatListener();
	
	@SuppressWarnings("static-access")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) 
	{
		if(cmd.getName().equalsIgnoreCase("facçao")){
			
			if(sender instanceof Player){
				
				Player p = (Player)sender;
				if(args.length==0)
				{
					if(factionplayermanager.getFactionPlayer(p).getFaction() != null){
						menufaction.openFactionInventory(factionplayermanager.getFactionPlayer(p));
						p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2F, 1F);
					}
					else p.sendMessage("§cNao tens nenhuma facção!"); 
				}
				
				else if(args[0].equalsIgnoreCase("criar") || args[0].equalsIgnoreCase("create"))
				{
					if(args.length == 3){
						if(factionplayermanager.getFactionPlayer(p).getFaction() == null){
							if(args[1].length()<=16 && args[1].length()>=4){
								if(factionmanager.checkIfisLetter(args[1])){
									if(factionmysl.isFactionNameExists(args[1])==false){
										if(args[2].length()<=4 && args[2].length()>=2){
											if(factionmanager.checkIfisLetter(args[2])){
												factionmanager.createFaction(factionplayermanager.getFactionPlayer(p), args[1], args[2]);
											}
											else p.sendMessage("§cA tag da tua facção so pode conter letras.");
										}
										else p.sendMessage("§cNão podes colocar uma tag tão comprida/pequena para a tua facção.");
									}
									else p.sendMessage("§cJá existe uma facção com esse nome!");
								}
								else p.sendMessage("§cO nome da tua facção so pode conter letras.");
							}
							else  p.sendMessage("§cNão podes colocar um nome tão comprido/pequeno para a tua facção.");
						}
						else p.sendMessage("§cJá estás numa facção.");
					}
					else p.sendMessage("§cUsa /facçao criar <nome> <tag>");
				}
				else if(args[0].equalsIgnoreCase("desc") || args[0].equalsIgnoreCase("descricao") || args[0].equalsIgnoreCase("description"))
				{
					if(factionplayermanager.getFactionPlayer(p).getFaction() != null){
						String Description="";
						for(int x=1; x<args.length; x++)Description=Description+args[x]+" ";
						if(args.length >= 2){
							factionplayermanager.getFactionPlayer(p).getFaction().setDescription("§7"+Description.replace("&", "§"));
							
							factionplayermanager.getFactionPlayer(p).getFaction().sendNotifyMenssage("§eO "+factionplayermanager.getFactionPlayer(p).getFactionRank()+" §e"+p.getName() +" alterou a descrição da facção para:", "  §7"+Description.replace("&", "§"));
						}
						else p.sendMessage("§cUsa /faccao desc <descri§§o>");
					}
					else p.sendMessage("§cNão tens nenhuma facção!");
				}
				else if(args[0].equalsIgnoreCase("convidar") || args[0].equalsIgnoreCase("invite"))
				{
					if(args.length == 2)
					{

						if(factionplayermanager.getFactionPlayer(p).getFaction() != null){
							try{
								Player pl = Bukkit.getPlayer(args[1]);
								if(factionplayermanager.getFactionPlayer(pl).getFaction() !=null){
									p.sendMessage("§cEsse jogador ja tem uma facção!");
									return true; 
								}
								if(factionplayermanager.getFactionPlayer(p).getFaction().getMembers().size()>=16){
									p.sendMessage("§cA tua facção já atingiu o máximo de membros [16/16].");
									return true;
								}
								else if(pl == p){
									p.sendMessage("§cNão podes enviar convites a ti mesmo!");
									return true;
								}
								else if(factionsinvites.getFactionInvite(pl, p)== null){
									FactionsInvites fi = new FactionsInvites(p, pl, factionplayermanager.getFactionPlayer(p).getFaction());
									fi.sendMenssage();
									factionsinvites.invites.add(fi);
								}
								else p.sendMessage("§cJá envias-te uma convite a esse jogador.");
							}
							catch(Exception e){p.sendMessage("§cO jogador nao esta online");}
						}
						else p.sendMessage("§cNao tens nenhuma faccao!");
					}
					else p.sendMessage("§cUsa /facçao convidar <jogador>");
				}
				else if(args[0].equalsIgnoreCase("convite"))
				{
					if(args.length>1)
					{
						if(args[1].equalsIgnoreCase("aceitar"))
						{
							if(args.length==3)
							{
								if(factionsinvites.checkInvite(p) == true)
								{
									try{
										
										if(factionplayermanager.getFactionPlayer(p).getFaction() == null){
											FactionsInvites fi = factionsinvites.getFactionInvite(p, Bukkit.getPlayer(args[2]));
											if(factionplayermanager.getFactionPlayer(fi.getInviter()).getFaction().getMembers().size()>=16){
												p.sendMessage("§cA facção já esta cheia!");
												return true;
											}
											fi.sendAcceptMenssage();
											fi.getFaction().addPlayerToClan(factionplayermanager.getFactionPlayer(p), fi.getFaction());
											fi.stopTimer();
										}
										else p.sendMessage("§cJá estas numa facção!");
									}
									catch(Exception e){p.sendMessage("§cO jogador nao te enviou nenhum convite.");}
								}
								else p.sendMessage("§cO pedido ja foi expirado");
							}
							else p.sendMessage("§cUsa /facção convite aceitar <jogador>");
						}
						else if(args[1].equalsIgnoreCase("recusar"))
						{
							if(args.length==3)
							{
								if(factionsinvites.checkInvite(p) == true)
								{
									try{
										FactionsInvites fi = factionsinvites.getFactionInvite(p, Bukkit.getPlayer(args[2]));
										fi.sendRejectMenssage();
										fi.stopTimer();
									}
									catch(Exception e){p.sendMessage("§cO jogador nao te enviou nenhum convite.");}
								}
								else p.sendMessage("§cO pedido ja foi expirado");
							}
							else p.sendMessage("§cUsa /facçao convite recusar <jogador>");
						}
						else p.sendMessage("§cUsa /facçao convite aceitar/recusar <jogador>");
					}
					else p.sendMessage("§cUsa /facçao convite aceitar/recusar <jogador>");
				}
				else if(args[0].equalsIgnoreCase("eleminar") || args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("apagar"))
				{
					if(factionplayermanager.getFactionPlayer(p).getFaction() != null){
						factionmysl.deleteFaction(factionplayermanager.getFactionPlayer(p).getFaction());

					}
					else p.sendMessage("§cNao tens nenhuma facção!");
				}
				else if(args[0].equalsIgnoreCase("banner") || args[0].equalsIgnoreCase("bandeira"))
				{
					if(factionplayermanager.getFactionPlayer(p).getFaction() != null){
						if(p.getItemInHand().getType()==Material.BANNER)
						{
							factionplayermanager.getFactionPlayer(p).getFaction().setBanner(p.getItemInHand());
							factionplayermanager.getFactionPlayer(p).getFaction().sendNotifyJoinLeaveMenssage("§eO Banner da Facção foi alterado pelo "+factionplayermanager.getFactionPlayer(p).getFactionRank()+" §e"+p.getName()+"!");
						}
						else p.sendMessage("§cColoca um banner na mão para poderes alterar.");

					}
					else p.sendMessage("§cNao tens nenhuma facção!");
				}
				else if(args[0].equalsIgnoreCase("home") || args[0].equalsIgnoreCase("base"))
				{
					if(factionplayermanager.getFactionPlayer(p).getFaction() != null){
						if(factionplayermanager.getFactionPlayer(p).getFaction().getHome()!=null)
						{
							if(teleportdelaysystem.isInTeleportDelay(p))teleportdelaysystem.stopTeleportDelay(p);
								teleportdelaysystem.playerTeleportDelay(p, factionplayermanager.getFactionPlayer(p).getFaction().getHome(), 5, " a Base da Facção");
						}
						else p.sendMessage("§cA tua facção ainda não possui base.");
					}
					else p.sendMessage("§cNao tens nenhuma faccao!");
				}
				else if(args[0].equalsIgnoreCase("bau") || args[0].equalsIgnoreCase("chest"))
				{
					if(factionplayermanager.getFactionPlayer(p).getFaction() != null){
						p.openInventory(factionplayermanager.getFactionPlayer(p).getFaction().getChest());
					}
					else p.sendMessage("§cNao tens nenhuma faccao!");
				}
				else if(args[0].equalsIgnoreCase("chat"))
				{
					if(factionplayermanager.getFactionPlayer(p).getFaction() != null){
						if(args.length==2){
							playerchatlistener.changePlayerChat(args[1], p);
						}
						else p.sendMessage("§cUsa /facçao chat <Normal/Facçao/Aliado>");
					}
					else p.sendMessage("§cNao tens nenhuma faccao!");
				}
			}				
		}

		return false;
	}
}
