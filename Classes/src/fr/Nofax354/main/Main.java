package fr.Nofax354.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.Nofax354.Classes.Classe;
import fr.Nofax354.Dungeons.Dungeon;
import fr.Nofax354.Dungeons.DungeonCommand;
import fr.Nofax354.Dungeons.DungeonListeners;
import fr.Nofax354.Dungeons.DungeonState;
import fr.Nofax354.listeners.Listeners;

public class Main extends JavaPlugin{
	
	private Connection connection;
	private String host, database, username, password;
    private int port;
    private Statement statement;
    Classe classe;
    
    public HashMap<String, Integer> xp = new HashMap<String, Integer>();
    public HashMap<String, Integer> classes = new HashMap<String, Integer>();
    public HashMap<Player,Integer> dungeon = new HashMap<Player,Integer>();
    public ArrayList<Player> dungeonP = new ArrayList<Player>();
    public HashMap<Integer, Integer> dungeonXp = new HashMap<Integer, Integer>();
    public int nb = 0;
    
    public Dungeon dungeonT;
    
    @Override
	public void onEnable() {
    	dungeonT = new Dungeon();
    	
		host = "localhost";
        port = 3306;
        database = "Classes";
        username = "root";
        password = ""; 
        
        try {    
        	openConnection();
            statement = connection.createStatement();          
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        classe = new Classe(this,statement);
        getServer().getPluginManager().registerEvents(new Listeners(statement,this), this);
        getServer().getPluginManager().registerEvents(new DungeonListeners(this), this);
        
        dungeonT.setState(DungeonState.WAITING);
        this.getCommand("dungeon").setExecutor(new DungeonCommand(this));
	}
	
    @Override
	public void onDisable() {
		
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
