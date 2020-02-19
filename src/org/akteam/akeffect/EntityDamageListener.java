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

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public final class EntityDamageListener implements Listener {
	private final Set<String> players = new HashSet<>();
	
	public void invoke(Player player) {
		players.add(player.getName());
	}
	
	public EntityDamageListener(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		//any other player will get damage
		if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
			if (!players.isEmpty()) {
				if (event.getEntityType() == EntityType.PLAYER ) {
					Player player = (Player) event.getEntity();
					if (players.contains(player.getName())) {
						event.setCancelled(true);
						players.remove(player.getName());
					}
				}else{
					event.setCancelled(true);
				}
			}
		}
	}
}
