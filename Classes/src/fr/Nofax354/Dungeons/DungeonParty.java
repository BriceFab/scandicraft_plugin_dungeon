package fr.Nofax354.Dungeons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import fr.Nofax354.main.Main;

public class DungeonParty {
	
	private List<Player> players;
	private Player owner;
	
	private boolean started = false;
	
	Main main;
	
	public DungeonParty(Player owner,Main main) {
		this.players = new ArrayList<Player>();
		this.owner = owner;
		this.main = main;
		players.add(owner);
	}
	
	public boolean started() {
		return started;
	}
	
	public void start() {
		started = true;
		main.manager.joinDungeon(players);
		main.Pmanager.deleteParty(this);
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	public List<Player> getPlayers() {
		return players;
	}

}
