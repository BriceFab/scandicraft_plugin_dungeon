package fr.Nofax354.Dungeons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DungeonManager {
	private List<Dungeon> dungeons = new ArrayList<>();
	
	public void addDungeon(Dungeon dungeon) {
		this.dungeons.add(dungeon);
	}
	
	public void joinDungeon(List<Player> players) {
		Dungeon nextDungeon = getNextDungeon();
		if(nextDungeon != null) {
			
			for(Player p : players) {
				nextDungeon.getPlayers().add(p);
				p.teleport(nextDungeon.getLoc());
			}
			nextDungeon.setStarted(true);
		}else {
			for(Player p : players) {
				p.sendMessage("Aucun donjon disponible pour le moment !");
			}
		}
	}

	private Dungeon getNextDungeon() {
		for(Dungeon dungeon : dungeons) {
			//Bukkit.broadcastMessage("Donjon,"+dungeon.isStarted());
			if(!dungeon.isStarted()) {
				return dungeon;
			}
		}
		return null;
	}
	
	public List<Dungeon> getDungeons(){
		return dungeons;
	}
	
	public Dungeon getDungeonByPlayer(Player p) {
		for(Dungeon dungeon : dungeons) {
			if(dungeon.getPlayers().contains(p)) {
				return dungeon;
			}
		}
		return null;
	}
}
