package fr.Nofax354.Dungeons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Nofax354.main.Main;

public class DungeonAttenteTask extends BukkitRunnable {
	
	private Main main;
	List<Player> pl = new ArrayList<>();
	
	public DungeonAttenteTask(Main main) {
		this.main = main;
	}
	
	@Override
	public void run() {
		int size = main.attente.size();
		if(size >= 6) {
			for(int i = 0; i < 6;i++) {
				pl.add(main.attente.get(0));
				main.attente.remove(0);
			}
		}else {
			for(int i = 0; i < size;i++) {
				pl.add(main.attente.get(0));
				main.attente.remove(0);
			}
		}
		if(pl.size() > 0)
			main.manager.joinDungeon(pl);
		pl.clear();
		
	}

}
