package fr.Nofax354.Dungeons.mobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Nofax354.Dungeons.Dungeon;

public class DungeonSpawnTask extends BukkitRunnable {

	List<Location> spawns = new ArrayList<>();
	Random r;
	Dungeon d;
	int maxMobs;
	int mobs;
	
	public DungeonSpawnTask(List<Location> spawns,Dungeon d, int maxMobs) {
		this.spawns = spawns;
		this.maxMobs = maxMobs;
		this.d = d;
		r = new Random();
	}
	
	@Override
	public void run() {
		if(mobs < maxMobs) {
			Zombie z = (Zombie) Bukkit.getWorld("Donjon").spawnEntity(spawns.get(r.nextInt(spawns.size())), EntityType.ZOMBIE);
			z.setHealth(z.getHealth()*(d.getLevel()+1));
			d.addEntity(z);
			mobs++;
		}else {
			cancel();
		}
	}

}
