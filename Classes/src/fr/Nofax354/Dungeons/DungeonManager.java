package fr.Nofax354.Dungeons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
			for(Player p : players) {
				nextDungeon.getPlayers().add(p);
				p.sendMessage("tp");
				//p.teleport(nextDungeon.getLoc());
				p.teleport(new Location(Dungeon.getWorld("Donjon"),-8.5,2.3,12.5 * (nextDungeon.getId()+1)));
			}
			nextDungeon.setStarted(true);
			
		}else {
			for(Player p : players) {
				p.sendMessage("Aucun donjon disponible pour le moment !");
			}
		}
	}

	private Dungeon getNextDungeon(List<Player> players) {
		
		int xp = 0;
		
		for(Player pl : players) {
			xp+=main.xp.get(pl.getDisplayName());
		}
		
		xp = xp/1000;
		
		for(int i = 0; i < dungeons.size();i++) {
			if(dungeons.get(i) == null) {
				Dungeon dn = new Dungeon(new Location(Dungeon.getWorld("Donjon"), 0, 0, i*30), i, xp,main);
				addDungeon(dn);
				return dn;
			}
		}
		Dungeon dn = new Dungeon(new Location(Dungeon.getWorld("Donjon"), 0, 0, 30*dungeons.size()), dungeons.size(), xp,main);
		addDungeon(dn);
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
