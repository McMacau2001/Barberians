package Main.Factions.Utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public abstract class APIScoreBoard {

	private Scoreboard scoreboard;
	private Objective objective;
	@SuppressWarnings("unused")
	private Player player;

	protected int currentLine = 16;
	protected Map<Integer, String> last = new HashMap<>();
	protected Map<Integer, String> cache = new LinkedHashMap<>();

	public APIScoreBoard(Player p, String title) {
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		this.objective = scoreboard.registerNewObjective("sbapi", "dummy");
		this.objective.setDisplayName(title.replace("&", "§"));
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.player = p;
		p.setScoreboard(this.scoreboard);
	}

	public boolean add(String text) {
		if (currentLine <= 0)
			return false;
		cache.put(currentLine--, text.replace("&", "§"));
		return true;
	}

	public boolean blank() {
		return add("");
	}

	public void updateScoreboard() {
		currentLine = 16;
		for (String text : cache.values())
			update(text);
	}
	
	protected boolean update(String text) {
		text = placeHolders(text);
		if (text.length() > 32) {
			if (last.containsKey(currentLine))
				if (!last.get(currentLine).equals(text.substring(16, 32)))
					scoreboard.resetScores(last.get(currentLine));
		}
		String prefix = text.length() > 32 ? text.substring(0, 16) : "";
		String entry = text.length() > 32 ? text.substring(16, 32) : ChatColor.values()[currentLine - 1] + "§r";
		String suffix = text.length() > 32 ? text.substring(32, text.length() > 48 ? 48 : text.length()) : "";
		if (!text.isEmpty() && prefix == "")
			prefix = text.substring(0, text.length() > 16 ? 16 : text.length());
		if (text.length() > 16 && suffix == "") {
			if (prefix.substring(prefix.length() - 1).equals("§")) {
				prefix = prefix.substring(0, prefix.length() - 1);
				text = text.replace(prefix, "");
				suffix = text.substring(0, text.length() > 16 ? 16 : text.length());
			} else {
				text = ChatColor.getLastColors(prefix) + text.replace(prefix, "");
				suffix = text.substring(0, text.length() > 16 ? 16 : text.length());
			}
		}
		Team t = scoreboard.getTeam("[Team:" + currentLine + "]");
		if (t == null)
			t = scoreboard.registerNewTeam("[Team:" + currentLine + "]");
		t.setPrefix(prefix);
		t.setSuffix(suffix);
		if (!t.hasEntry(entry))
			t.addEntry(entry);
		objective.getScore(entry).setScore(currentLine);
		last.put(currentLine--, entry);
		return true;
	}

	public abstract String placeHolders(String str);
}
