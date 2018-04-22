package Main.Factions.Utils.Others;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;

public class TabListManager {

	public static void loadTab(Player p) {
		
		String header="\n§7§l--[ §c§lBARBERIANS NETWORK  §7§l]--\n\n§eInformações:\n§7IP: mc.brevemente.net\nDiscord: discord.gg/Vq4mFwe\nLoja: www.brevemente.com\n";
		String footer="\n  §7A equipa do Barberians Network Agradece a tua companhia aqui no servidor.  \n  Compartilha o servidor para os teus Amigos e diverte-te!  \n";
		
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		
		IChatBaseComponent Title = ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent Foot = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
		
		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(Title);
		try {
			
			Field field = headerPacket.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(headerPacket, Foot);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			connection.sendPacket(headerPacket);
		}
		
	}
}
