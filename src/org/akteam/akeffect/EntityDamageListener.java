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
		this.players.add(player.getName());
	}
	
	public EntityDamageListener(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		//any other player will get damage
		if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
			if (!this.players.isEmpty()) {
				if (event.getEntityType() == EntityType.PLAYER ) {
					Player player = (Player) event.getEntity();
					if (this.players.contains(player.getName())) {
						event.setCancelled(true);
						this.players.remove(player.getName());
					}
				}else{
					event.setCancelled(true);
				}
			}
		}
	}
}
