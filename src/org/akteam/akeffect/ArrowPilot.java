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

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ArrowPilot extends BukkitRunnable {
	private final Arrow arrow;
	private final Player player;
	private final double agc;
	public ArrowPilot(Arrow arrow, Player player, double agc) {
		this.arrow = arrow;
		this.player = player;
		this.agc = agc;
	}
	
	@Override
	public void run() {
		if(arrow.getPassengers().isEmpty()) { //check whether the player is off the rocket
			arrow.remove();
			this.cancel();
			return;
		}
		if(arrow.isDead()) { //check whether the arrow is killed
			arrow.remove();
			this.cancel();
			return;
		}
		if(arrow.isInBlock()) { //check whether the rocket has reached the destination
			arrow.removePassenger(player);
			arrow.remove();
			this.cancel();
		}else{
			Vector p =  player.getEyeLocation().getDirection();
			Vector v = arrow.getVelocity();
			double yaw = Math.atan2(p.getX(), p.getZ()) - Math.atan2(v.getX(), v.getZ());
			v.rotateAroundY(yaw);
			//let arrow rotate as the player's visual direction changes
			v.setY(v.getY()*agc); //anti-gravity constant
			arrow.setVelocity(v);
		}
	}
}
