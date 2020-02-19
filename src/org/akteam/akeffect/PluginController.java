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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginController implements CommandExecutor {
	private final JavaPlugin plugin;
	public PluginController(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if(args.length<1) return false;
		switch(args[0]) {
			case "reload": {
				plugin.saveDefaultConfig();
				plugin.reloadConfig();
				plugin.getCommand("shazam").setExecutor(new CommandShazam(plugin));
				plugin.getServer().broadcastMessage(ChatColor.RED + "Plugin [AkEffect] has been self-reloaded!");
				return true;
			}
			default: return false;
		}
	}
}
