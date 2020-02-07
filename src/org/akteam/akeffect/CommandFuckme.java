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
