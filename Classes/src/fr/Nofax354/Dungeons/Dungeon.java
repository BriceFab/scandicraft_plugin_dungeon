package fr.Nofax354.Dungeons;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;

import fr.Nofax354.Dungeons.mobs.DungeonSpawnTask;
import fr.Nofax354.Dungeons.tasks.DungeonTimeTask;
import fr.Nofax354.main.Main;

public class Dungeon {

	private Location loc;
	private List<Player> players;
	private List<Entity> entities;
	private int level;
	private int xp = 0;
	private boolean isStarted = false;
	private int id;
	Main main;
	File level0 ;
	CuboidClipboard clipboard;
	EditSession session;
	public static DungeonSpawnTask start;
	public static DungeonTimeTask start1;
	
	public Dungeon(Location loc,int id,int level,Main main,int maxMobs,int maxTime) {
		this.main = main;
		this.level = level;
		this.id = id;
		this.setLoc(loc);
		this.players = new ArrayList<Player>();
		this.entities = new ArrayList<Entity>();
		this.setStarted(false);
		generateMap();
		
		start = new DungeonSpawnTask(getSpawns(),this,maxMobs);
		
		
		start1 = new DungeonTimeTask(this,maxTime);
		
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public void removeEntity(Entity e) {
		entities.remove(e);
	}
	
	public List<Entity> getEntities() {
		return entities;
	}

	@SuppressWarnings("deprecation")
	public void generateMap() {
		try {
				clipboard = main.levels.get(level);
				session = main.we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(Dungeon.getWorld("Donjon")), 999999999);
				clipboard.paste(session, new Vector(loc.getX(),loc.getY(),loc.getZ()), false);
				clipboard.pasteEntities(new Vector(loc.getX(),loc.getY(),loc.getZ()));
			} catch (MaxChangedBlocksException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}
	
	public void Broadcast(String message) {
		for(Player p : players) {
			p.sendMessage(message);
		}
	}
	
	public List<Location> getSpawns(){
		List<Location> spawns = new ArrayList<>();
		
		for(double x = loc.getX(); x > -(loc.getX()+clipboard.getWidth()+2);x--) {
			for(double y = loc.getY(); y < loc.getY()+clipboard.getHeight()+1;y++) {
				for(double z = loc.getZ(); z < loc.getZ()+clipboard.getLength()+1;z++) {
					Block b = Bukkit.getWorld("Donjon").getBlockAt(new Location(Bukkit.getWorld("Donjon"),x,y,z));
					if(b.getType().equals(Material.GLOWSTONE)) {
						spawns.add(new Location(Bukkit.getWorld("Donjon"),x+05,y+1.3,z+0.5));
					}
					//Bukkit.getWorld("Donjon").getBlockAt(new Location(Bukkit.getWorld("Donjon"),x,y,z)).setType(Material.REDSTONE_BLOCK);
				}
			}
		}
		System.out.println(spawns.size());
		return spawns;
	}
	
	public Location getSpawn(){

		for(double x = loc.getX(); x > -(loc.getX()+clipboard.getWidth());x--) {
			for(double y = loc.getY(); y < loc.getY()+clipboard.getHeight();y++) {
				for(double z = loc.getZ(); z < loc.getZ()+clipboard.getLength();z++) {
					Block b = Bukkit.getWorld("Donjon").getBlockAt(new Location(Bukkit.getWorld("Donjon"),x,y,z));
					if(b.getType().equals(Material.GOLD_BLOCK)) {
						return new Location(Bukkit.getWorld("Donjon"),x+0.5,y+1.3,z+0.5);
					}
				}
			}
		}
		return new Location(Bukkit.getWorld("Donjon"),0,0,0);
	}
	
	public static World getWorld(final String name) {
	    World world = Bukkit.getWorld(name);
	    return world == null ? Bukkit.createWorld(new WorldCreator(name)) : world;
	}
	
	public void restart() {
		for(Player p : players) {
			p.teleport(new Location(Bukkit.getWorld("world"),-29.5,92.5,-27.5));
		}
		
		//start.cancel();
		//start1.cancel();
		
		session.undo(session);
		
		main.manager.removeDungeon(id);
		
		this.xp = 0;
		this.players.clear();
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
	
	
	public void removePlayer(Player p) {
		players.remove(p);
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

	public int getId() {
		return id;
	}
}
