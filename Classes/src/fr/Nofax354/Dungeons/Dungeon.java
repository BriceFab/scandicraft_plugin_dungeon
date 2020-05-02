package fr.Nofax354.Dungeons;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class Dungeon {
	
	DungeonState state;
	
	public void setState(DungeonState state) {
		this.state = state;
	}
	
	public boolean isState(DungeonState state) {
		return this.state == state;
	}
	
	public World getWorld(final String name) {
	    World world = Bukkit.getWorld(name);
	    return world == null ? Bukkit.createWorld(new WorldCreator(name)) : world;
	}
}
