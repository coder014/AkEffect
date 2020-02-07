package org.akteam.akeffect;

import java.util.HashSet;
import com.sun.istack.internal.NotNull;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import static org.bukkit.Bukkit.*;

public final class EntityDamageListener implements Listener {
	private HashSet<Player> players = new HashSet<>();
	
	public void invoke(@NotNull Player player) {
		this.players.add(player);
	}
	
	public EntityDamageListener(@NotNull Plugin plugin) {
		getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
			if (!this.players.isEmpty()) {
				if (event.getEntityType() == EntityType.PLAYER ) {
					Player player = (Player) event.getEntity();
					if (this.players.contains(player)) {
						event.setCancelled(true);
						this.players.remove(player);
					}
				}else{
					event.setCancelled(true);
				}
			}
		}
	}
}
