package fr.Nofax354.Dungeons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.Nofax354.main.Main;

public class DungeonManager {
	private List<Dungeon> dungeons = new ArrayList<>();
	
	//private Dungeon[] dungeons;
	
	Main main;
	public DungeonManager(Main main) {
		this.main = main;
	}

	public void addDungeon(Dungeon dungeon) {
		this.dungeons.add(dungeon);
	}
	
	public void joinDungeon(List<Player> players) {
		Dungeon nextDungeon = getNextDungeon(players);
		if(nextDungeon != null) {
			Location sp = new Location(Bukkit.getWorld("Donjon"),nextDungeon.getSpawn().getX(),nextDungeon.getSpawn().getY(),nextDungeon.getSpawn().getZ());
			//Bukkit.getWorld("Donjon").getBlockAt(sp).setType(Material.AIR);
			for(Player p : players) {
				nextDungeon.getPlayers().add(p);
				p.teleport(new Location(Dungeon.getWorld("Donjon"),sp.getX(),sp.getY(),sp.getZ() +(getSpawnLength(nextDungeon))));
			}
			nextDungeon.start.runTaskTimer(main, 0, 100);
			nextDungeon.start1.runTaskTimer(main, 0, 20);
			nextDungeon.setStarted(true);
		}else {
			for(Player p : players) {
				p.sendMessage("Aucun donjon disponible pour le moment !");
			}
		}
	}
	
	public boolean fit(int level,int bef,int aft) {
		
		int length = main.levels.get(level).getLength();
		double taille = 0;
		

		if(bef <= 0 && aft >= dungeons.size()-1) {
			return true;
		}else if(bef <= 0 && aft <= dungeons.size()-1) {
			Dungeon aft1 = dungeons.get(aft);
			Location nul = new Location(Dungeon.getWorld("Donjon"), 0, 0, 0);
			taille = nul.distance(aft1.getLoc());
		}else {
			Dungeon aft1 = dungeons.get(aft);
			Dungeon bef1 = dungeons.get(bef);
			taille = bef1.getLoc().distance(aft1.getLoc())-bef1.clipboard.getLength();
		}

		return length < taille;
	}
	
	public double getAllLength() {
		
		if(dungeons.size() <= 1) {
			return 0;
		}
		Dungeon d = dungeons.get(dungeons.size()-1);
		
		return d.getLoc().getZ()+d.clipboard.getLength();
	}
	
	public double getSpawnLength(Dungeon d) {
		return d.getLoc().getZ();
	}
	
	private Dungeon getNextDungeon(List<Player> players) {
		
		int xp = 0;
		
		for(Player pl : players) {
			xp+=main.xp.get(pl.getDisplayName());
		}
		
		xp = xp/1000;
		
		int level = 0;
		
		int i = 0;
		
		//if(dungeons.size())
		
		for(Dungeon d : dungeons) {
			System.out.println(d);
			if(d == null) {
				
				if(fit(level,i-1,i+1)) {
					System.out.println("id:"+i);
					Dungeon dn = new Dungeon(new Location(Dungeon.getWorld("Donjon"), 0, 0, i*(main.levels.get(level).getLength() + 5)), i, level,main,main.maxMobSpawn.get(level),main.timeMax.get(level));
					//addDungeon(dn);
					dungeons.set(i, dn);
					return dn;
				}
			}
			i++;
		}

		Dungeon dn = new Dungeon(new Location(Dungeon.getWorld("Donjon"), 0, 0, i*(main.levels.get(level).getLength() + 5)), i, level,main,main.maxMobSpawn.get(level),main.timeMax.get(level));
		addDungeon(dn);
		System.out.println("id:"+i);
		return dn;
	}
	
	public List<Dungeon> getDungeons(){
		return dungeons;
	}
	
	public Dungeon getDungeonByPlayer(Player p) {
		for(Dungeon dungeon : dungeons) {
			if(dungeon != null)
			if(dungeon.getPlayers().contains(p)) {
				return dungeon;
			}
		}
		return null;
	}

	public void removeDungeon(int i) {
		dungeons.set(i,null);
		
	}
}
