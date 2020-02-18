package org.akteam.akeffect;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class CommandRocket implements CommandExecutor {
	private final JavaPlugin plugin;
	public CommandRocket(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			Location location = player.getLocation();
			Vector vector = location.getDirection();
			Arrow arrow = player.getWorld().spawnArrow(location.add(0L, 20L, 0L), vector.setY(0), 2.0F, 12F);
			arrow.setFireTicks(Integer.MAX_VALUE);
			arrow.addPassenger(player);
			player.sendMessage(ChatColor.RED + "Launch!");
			//shoot a fired arrow from above the player and make the player ride on it
			new ArrowPilot(arrow, player).runTaskTimer(this.plugin, 0L, 10L);
			return true;
		}
		return false;
	}
}