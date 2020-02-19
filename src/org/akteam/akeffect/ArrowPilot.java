package org.akteam.akeffect;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ArrowPilot extends BukkitRunnable {
	private final Arrow arrow;
	private final Player player;
	public ArrowPilot(Arrow arrow, Player player) {
		this.arrow = arrow;
		this.player = player;
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
			v.setY(v.getY()*0.4); //anti-gravity constant
			arrow.setVelocity(v);
		}
	}
}
