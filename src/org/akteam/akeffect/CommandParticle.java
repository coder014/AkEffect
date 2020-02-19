package org.akteam.akeffect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    public final HashMap<UUID, Particle> particle4Player = new HashMap<>();
    private static final Random rnd = new Random();
    private List<Particle> particleValues = new ArrayList<>();
    private JavaPlugin plugin;
    private BukkitRunnable task;

    CommandParticle(JavaPlugin plugin) {
        this.plugin = plugin;
        for (Particle p : Particle.values()) {
            if (p.getDataType() == Void.class) {
                particleValues.add(p);
            }
        }
        startParticleTask();
    }

    public void setParticleForPlayer(Player p) {
        particle4Player.put(p.getUniqueId(), particleValues.get(rnd.nextInt(particleValues.size())));
    }

    public Particle getParticleForPlayer(Player p) {
        if (!particle4Player.containsKey(p.getUniqueId())) {
            setParticleForPlayer(p);
        }

        return particle4Player.get(p.getUniqueId());
    }

    public boolean startParticleTask() {
        if (task == null) {
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        Vector d = p.getLocation().getDirection();
                        d.multiply(-1);
                        p.getWorld().spawnParticle(getParticleForPlayer(p), p.getLocation().clone().add(d), 2, 0.25, 0.0, 0.25);
                    }
                }
            };
            task.runTaskTimer(plugin, 10L, 2L);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!label.equals("particle4p")) return false;
        if (args.length <= 0) {
            if (startParticleTask()) {
                commandSender.sendMessage("[AkEffect] Particle4Player is now running.");
            } else {
                task.cancel();
                task = null;
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
                if (startParticleTask()) {
                    commandSender.sendMessage("[AkEffect] Particle4Player is now running.");
                } else commandSender.sendMessage(ChatColor.YELLOW + "[AkEffect] Particle4Player is already running!");
                break;
            case "stop":
                if (task != null) {
                    task.cancel();
                    task = null;
                    commandSender.sendMessage("[AkEffect] Particle4Player is now stopped.");
                } else commandSender.sendMessage(ChatColor.YELLOW + "[AkEffect] Particle4Player is already stopped!");
                break;
            default:
                commandSender.sendMessage("[AkEffect] unknown sub command.");
        }

        return true;
    }
}
