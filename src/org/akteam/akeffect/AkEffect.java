package org.akteam.akeffect;

import org.bukkit.plugin.java.JavaPlugin;

public class AkEffect extends JavaPlugin {
	@Override
	public void onEnable() {
		this.getCommand("fuckme").setExecutor(new CommandFuckme(new EntityDamageListener(this)));
		this.getCommand("shazam").setExecutor(new CommandShazam(this));
	}
}
