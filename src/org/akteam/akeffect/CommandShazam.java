package org.akteam.akeffect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public final class CommandShazam implements CommandExecutor {
	private final JavaPlugin plugin;
	private final List<PotionEffect> l = new ArrayList<>(); //potion effects list
	public CommandShazam(JavaPlugin plugin) {
		this.plugin = plugin;
		
		//initialize potion effects
		l.add(new PotionEffect(PotionEffectType.SPEED, effectTime*20, 3));
		l.add(new PotionEffect(PotionEffectType.REGENERATION, effectTime*20, 4));
		l.add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, effectTime*20, 3));
		l.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, effectTime*20, 3));
		l.add(new PotionEffect(PotionEffectType.WATER_BREATHING, effectTime*20, 1));
		l.add(new PotionEffect(PotionEffectType.JUMP, effectTime*20, 2));
		l.add(new PotionEffect(PotionEffectType.GLOWING, effectTime*20, 1));
		l.add(new PotionEffect(PotionEffectType.SLOW_FALLING, effectTime*20, 1));
		l.add(new PotionEffect(PotionEffectType.NIGHT_VISION, effectTime*20, 1));
		l.add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, effectTime*20, 3));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if (sender instanceof Player) {
			this.shazalize((Player) sender);
			return true;
		}
		return false;
	}
	
	private final Set<String> shazams = new HashSet<>();  //Shazams list
	private final int effectTime = 60;
	
	private void shazalize(Player player) {
		if (this.canShazalize(player)) {
			this.shazams.add(player.getName());
			player.setFoodLevel(player.getFoodLevel() - 12);
			player.setSaturation(0);
			player.addPotionEffects(l);
			this.strike(player);
			//get the powers,pay the price
			player.sendMessage(ChatColor.BLUE + "You are a Shazam now.");
			player.chat(ChatColor.RED + "Shazam!");
			new BukkitRunnable() {
				@Override
				public void run() {
					unshazalize(player);
				}
			}.runTaskLater(this.plugin, effectTime*20L);
		}
	}
	
	private void unshazalize(Player player) {
		this.shazams.remove(player.getName());
		if (plugin.getServer().getPlayer(player.getName()) == player) { //else player is offline
			player.setSaturation(0);
			player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 2*effectTime * 20, 4));
			//pay more price
			player.sendMessage(ChatColor.GRAY + "You are not a Shazam anymore.");
		}
	}
	
	private boolean canShazalize(Player player) {
		if (this.shazams.contains(player.getName())) {
			player.sendMessage("You have already been a Shazam!");
			return false;
		} else if (player.getFoodLevel() < 16) {
			player.sendMessage("You don't have enough food level!");
			return false;
		}
		return true;
	}
	
	private void strike(Player player) {
		player.getWorld().strikeLightningEffect(player.getLocation());
		new BukkitRunnable() {
			@Override
			public void run() {
				player.getWorld().strikeLightningEffect(player.getLocation().add(5, 0, 0));
				player.getWorld().strikeLightningEffect(player.getLocation().add(0, 0, -5));
			}
		}.runTaskLater(this.plugin, 10L);
		new BukkitRunnable() {
			@Override
			public void run() {
				player.getWorld().strikeLightningEffect(player.getLocation().add(0, 0, 5));
				player.getWorld().strikeLightningEffect(player.getLocation().add(-5, 0, 0));
			}
		}.runTaskLater(this.plugin, 20L);
	}
}
