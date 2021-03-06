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

import org.bukkit.plugin.java.JavaPlugin;

public final class AkEffect extends JavaPlugin {
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		
		this.getCommand("akeffect").setExecutor(new PluginController(this));
		this.getCommand("fuckme").setExecutor(new CommandFuckme(this));
		this.getCommand("shazam").setExecutor(new CommandShazam(this));
		this.getCommand("rocket").setExecutor(new CommandRocket(this));
		this.getCommand("particle4p").setExecutor(new CommandParticle(this));
	}
}
