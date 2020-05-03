package fr.Nofax354.Dungeons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

public class Dungeon {

	private Location loc;
	private List<Player> players;
	private int level;
	private int xp = 0;
	private boolean isStarted = false;
	
	public Dungeon(Location loc) {
		this.setLoc(loc);
		this.players = new ArrayList<Player>();
		this.setStarted(false);
	}
	
	
	public static World getWorld(final String name) {
	    World world = Bukkit.getWorld(name);
	    return world == null ? Bukkit.createWorld(new WorldCreator(name)) : world;
	}
	
	public void restart() {
		for(Player p : players) {
			p.teleport(new Location(Bukkit.getWorld("world"),-29.5,92.5,-27.5));
		}
		
		this.xp = 0;
		this.players = new ArrayList<Player>();
		this.setStarted(false);
	}

	public Location getLoc() {
		return loc;
	}


	public void setLoc(Location loc) {
		this.loc = loc;
	}


	public List<Player> getPlayers() {
		return players;
	}


	public boolean isStarted() {
		return isStarted;
	}


	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public int getXp() {
		return xp;
	}


	public void setXp(int xp) {
		this.xp = xp;
	}
}
