package fr.Nofax354.Dungeons;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;

import fr.Nofax354.main.Main;

public class Dungeon {

	private Location loc;
	private List<Player> players;
	private int level;
	private int xp = 0;
	private boolean isStarted = false;
	private int id;
	Main main;
	File level0 ;
	CuboidClipboard clipboard;
	EditSession session;
	
	public Dungeon(Location loc,int id,int level,Main main) {
		this.main = main;
		this.level = level;
		this.id = id;
		this.setLoc(loc);
		this.players = new ArrayList<Player>();
		this.setStarted(false);
		generateMap();
	}

	@SuppressWarnings("deprecation")
	public void generateMap() {
		try {
			level0 = new File(main.getDataFolder()+File.separator+"portal-nether.schematic");
			clipboard = CuboidClipboard.loadSchematic(level0);
			
			session = main.we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(Dungeon.getWorld("Donjon")), 999999999);
			System.out.println(clipboard.getWidth()+","+clipboard.getHeight()+","+clipboard.getLength());
			clipboard.paste(session, new Vector(loc.getX(),loc.getY(),loc.getZ()), false);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static World getWorld(final String name) {
	    World world = Bukkit.getWorld(name);
	    return world == null ? Bukkit.createWorld(new WorldCreator(name)) : world;
	}
	
	public void restart() {
		for(Player p : players) {
			p.teleport(new Location(Bukkit.getWorld("world"),-29.5,92.5,-27.5));
		}
		
		session.undo(session);
		
		main.manager.removeDungeon(id);
		
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
