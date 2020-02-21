/*
 * AkEffect
 * Copyright (C) 2020 coder014
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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

public final class CommandRocket implements CommandExecutor {
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
			long height = plugin.getConfig().getLong("rocket.height");
			float speed = (float)plugin.getConfig().getDouble("rocket.speed");
			float spread = (float)plugin.getConfig().getDouble("rocket.spread");
			Arrow arrow = player.getWorld().spawnArrow(location.add(0L, height, 0L), vector.setY(0), speed, spread);
			arrow.setFireTicks(Integer.MAX_VALUE);
			arrow.addPassenger(player);
			player.sendMessage(ChatColor.RED + "Launch!");
			//shoot a fired arrow from above the player and make the player ride on it
			double agcm = plugin.getConfig().getDouble("rocket.agcm"); //anti-gravity constant
			double agcp = plugin.getConfig().getDouble("rocket.agcp"); //anti-gravity constant
			new ArrowPilot(arrow, player, agcm, agcp).runTaskTimer(plugin, 0L, 1L);
			return true;
		}
		return false;
	}
}
