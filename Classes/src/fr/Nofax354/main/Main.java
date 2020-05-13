package fr.Nofax354.main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.world.DataException;
import com.sk89q.worldedit.world.World;

import fr.Nofax354.Classes.Classe;
import fr.Nofax354.Dungeons.Dungeon;
import fr.Nofax354.Dungeons.DungeonAttenteTask;
import fr.Nofax354.Dungeons.DungeonManager;
import fr.Nofax354.Dungeons.PartyManager;
import fr.Nofax354.Dungeons.dungeonCommand;
import fr.Nofax354.Dungeons.dungeonListener;
import fr.Nofax354.Dungeons.dungeonTpCommand;
import fr.Nofax354.listeners.Listeners;

public class Main extends JavaPlugin{
	
	private Connection connection;
	private String host, database, username, password;
    private int port;
    private Statement statement;
    Classe classe;
    
    public HashMap<String, Integer> xp = new HashMap<String, Integer>();
    public HashMap<String, Integer> classes = new HashMap<String, Integer>();
    
    public List<Integer> timeMax = new ArrayList<>();
    public List<Integer> maxMobSpawn = new ArrayList<>();
    public List<CuboidClipboard> levels = new ArrayList<>();
    
    public List<Player> attente = new ArrayList<>();
    public DungeonManager manager = new DungeonManager(this);
    public PartyManager Pmanager = new PartyManager(this);
    private File dungeonFile;
    private YamlConfiguration dungeonConfig;
    public WorldEditPlugin we;
    
    public Location spawn;
    
    @Override
	public void onEnable() {
    	
		host = "localhost";
        port = 3306;
        database = "Classes";
        username = "root";
        password = ""; 
        
        

        //creation fichier config donjons
        if(!getDataFolder().exists()) {
        	getDataFolder().mkdir();
        }
        
        File file = new File("plugins/Classes", "bdd.yml");
        if(!file.exists()) {
	        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	        cfg.set("bdd.host", "localhost");
	        cfg.set("bdd.user", "root");
	        cfg.set("bdd.psw", "");
	        try {
	            cfg.save(file);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
        }
        
        dungeonConfig = YamlConfiguration.loadConfiguration(file);
        
        ConfigurationSection dungeonSection = dungeonConfig.getConfigurationSection("bdd");
        host = dungeonSection.getString("host");
        username = dungeonSection.getString("user");
        password = dungeonSection.getString("psw");
        
        try {    
        	openConnection();
            statement = connection.createStatement();          
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        dungeonFile = new File(getDataFolder() + File.separator + "Donjons.yml");
        if(!dungeonFile.exists()) {
        	try {
				dungeonFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        dungeonConfig = YamlConfiguration.loadConfiguration(dungeonFile);
        //charger donjons
        
        dungeonSection = dungeonConfig.getConfigurationSection("Donjons");
        
        for(String string : dungeonSection.getKeys(false)) {
        	int spawn = dungeonSection.getInt(string+".maxTime");
        	timeMax.add(spawn);
        	spawn = dungeonSection.getInt(string+".maxMobSpawn");
        	maxMobSpawn.add(spawn);
        	System.out.println("loaded : "+string);
        	
        }
        
        
        
        dungeonSection = dungeonConfig.getConfigurationSection("spawn");
        
        String str = dungeonSection.getString("loc");
        
        spawn = parseStringToLoc2(str);
        
        classe = new Classe(this,statement);
        getServer().getPluginManager().registerEvents(new Listeners(statement,this), this);
        getServer().getPluginManager().registerEvents(new dungeonListener(this), this);
        getCommand("dungeon").setExecutor(new dungeonCommand(this));
        getCommand("dungeons").setExecutor(new dungeonTpCommand(this));
        
        we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        
        loadLevels();
	}
	
    @Override
	public void onDisable() {
		for(Dungeon d :manager.getDungeons()) {
			if(d!=null)
				d.restart();
		}
	}
    
    public void loadLevels() {
    	for(int i = 0;i < timeMax.size();i++) {
    		File f = new File(getDataFolder()+File.separator+"schematics/level"+i+".schematic");
    		
    		try {
				levels.add(CuboidClipboard.loadSchematic(f));
				System.out.println("level"+i+" loaded.");
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    public Location parseStringToLoc(String str) {
    	String[] parsedLoc = str.split(",");
    	double x = Double.parseDouble(parsedLoc[0]);
    	double y = Double.parseDouble(parsedLoc[1]);
    	double z = Double.parseDouble(parsedLoc[2]);
    	
    	return new Location(Dungeon.getWorld("Donjon"), x, y, z);
    }
    
    public Location parseStringToLoc2(String str) {
    	String[] parsedLoc = str.split(",");
    	double x = Double.parseDouble(parsedLoc[0]);
    	double y = Double.parseDouble(parsedLoc[1]);
    	double z = Double.parseDouble(parsedLoc[2]);
    	return new Location(Bukkit.getWorld("world"), x, y, z);
    }
    
    public String parseLocToString(Location loc) {
    	return loc.getX()+","+loc.getY()+","+loc.getZ();
    }
    
    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }
     
        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
        }
    }
}
