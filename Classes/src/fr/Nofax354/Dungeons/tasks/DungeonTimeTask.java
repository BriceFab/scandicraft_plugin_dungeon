package fr.Nofax354.Dungeons.tasks;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Nofax354.Dungeons.Dungeon;

public class DungeonTimeTask extends BukkitRunnable {

	
	Dungeon d;
	int maxTime;
	int time;
	
	public DungeonTimeTask(Dungeon dungeon, int maxTime) {
		this.maxTime = maxTime;
		this.d = dungeon;
		time = maxTime;
		
	}

	@Override
	public void run() {
		if(time == maxTime) {
			List<Player> pls = d.getPlayers();
			d.Broadcast("Vous avez "+maxTime+"s pour terminer le Donjon !");
		}
		if(time == 0) {
			List<Player> pls = d.getPlayers();
			d.Broadcast("Vous n'avez pas términé le donjon à temps");
			d.restart();
			d.start.cancel();
			cancel();
		}
		
		if(time == 30 || time == 10 || time == 5 || time == 4 || time == 3 || time == 2 || time == 1) {
			List<Player> pls = d.getPlayers();
			d.Broadcast("Il vous reste "+time+"s pour finir le donjon !");
		}
		
		time--;
	}

}
