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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class CommandParticle implements CommandExecutor {
	private final Map<String, Integer> particleForPlayer = new HashMap<>();
	private final Random rnd = new Random();
	private final List<Particle> particles = new ArrayList<>();
	private final JavaPlugin plugin;
	private BukkitRunnable task;
	
	public CommandParticle(JavaPlugin plugin) {
		this.plugin = plugin;
		for (Particle particle : Particle.values()) {
			if (particle.getDataType() == Void.class) {
				particles.add(particle);
			}
		}
		startParticleTask();
	}
	
	public void setParticleForPlayer(Player player) {
		particleForPlayer.put(player.getName(), rnd.nextInt(particles.size()));
	}
		
	public Particle getParticleForPlayer(Player player) {
		if (!particleForPlayer.containsKey(player.getName())) {
			setParticleForPlayer(player);
		}
		return particles.get(particleForPlayer.get(player.getName()));
	}
	
	public void startParticleTask() {
		task = new BukkitRunnable() {
			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					Location l = player.getLocation();
					Vector d = l.getDirection().multiply(-1);
					l.add(d);
					//spawn particles behind the player
					player.getWorld().spawnParticle(getParticleForPlayer(player), l, 2, 0.25, 0.25, 0.25);
				}
			}
		};
		task.runTaskTimer(plugin, 10L, 2L);
	}
	
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (args.length <= 0) {  //toggle particles
			if (task.isCancelled()) {
				startParticleTask();
				commandSender.sendMessage("[AkEffect] Particle4Player is now running.");
			} else {
				task.cancel();
				commandSender.sendMessage("[AkEffect] Particle4Player is now stopped.");
			}
		} else switch (args[0]) {
			case "change":
				if (commandSender instanceof Player) {
					setParticleForPlayer((Player) commandSender);
					commandSender.sendMessage("[AkEffect] Particle for you has changed.");
				} else {
					commandSender.sendMessage("[AkEffect] This command must be executed as a player");
				}
				break;
			case "start":
				if (task.isCancelled()) {
					startParticleTask();
					commandSender.sendMessage("[AkEffect] Particle4Player is now running.");
				} else commandSender.sendMessage(ChatColor.YELLOW + "[AkEffect] Particle4Player is already running!");
				break;
			case "stop":
				if (!task.isCancelled()) {
					task.cancel();
					commandSender.sendMessage("[AkEffect] Particle4Player is now stopped.");
				} else commandSender.sendMessage(ChatColor.YELLOW + "[AkEffect] Particle4Player is already stopped!");
				break;
			default:
				return false;
		}
		return true;
	}
}
