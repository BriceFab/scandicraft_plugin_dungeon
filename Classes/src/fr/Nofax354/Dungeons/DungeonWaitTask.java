package fr.Nofax354.Dungeons;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Nofax354.main.Main;

public class DungeonWaitTask extends BukkitRunnable{
	
	int timer = 30;
	Main main;
	
	public DungeonWaitTask(Main main) {
		this.main = main;
	}

	@Override
	public void run() {
		
		if(timer == 10 || timer == 5 || timer == 4 || timer == 3 || timer == 2 || timer == 1) {
			for(int i = 0;i < main.dungeon.size();i++) {
				main.dungeon.get(i).sendMessage("Lancement du donjon dans "+timer+"s");
			}
		}
		
		if(timer == 0) {
			for(int i = 0;i < main.dungeon.size();i++) {
				main.dungeon.get(i).sendMessage("Le jeu va démmarrer");
			}
			
			main.dungeonT.setState(DungeonState.PVP);
			
			for(int i = 0;i < main.dungeon.size();i++) {
				Player p = main.dungeon.get(i);
				p.getInventory().clear();
				p.setGameMode(GameMode.SURVIVAL);
				p.teleport(new Location(main.dungeonT.getWorld("Donjon"),-0.5D,2.5D,-0.5D));
			}
			
			DungeonTask dungeonTask = new DungeonTask(main);
			dungeonTask.runTaskTimer(main, 0, 20);
			
			cancel();
		}
		
		timer--;
	}

}
