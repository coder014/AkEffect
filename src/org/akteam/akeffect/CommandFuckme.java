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

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CommandFuckme implements CommandExecutor {
	private final EntityDamageListener listener;
	public CommandFuckme(EntityDamageListener listener) {
		this.listener = listener;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			listener.invoke(player);
			player.getWorld().createExplosion(player.getLocation(), 4F, false, false);
			player.chat("Come and fuck me!");
			return true;
		}
		return false;
	}
}
