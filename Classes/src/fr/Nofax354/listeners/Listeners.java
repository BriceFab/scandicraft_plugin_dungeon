package fr.Nofax354.listeners;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Nofax354.Capacites.Capacite;
import fr.Nofax354.Classes.Classe;
import fr.Nofax354.Utils.Functions;
import fr.Nofax354.main.Main;


public class Listeners implements Listener {
	private Statement statement;
	private Inventory classSelect;
	private Main main;
	public int nb = 0;
	ResultSet result;
	
	public Listeners(Statement statement, Main main) {
		this.statement = statement;
		this.main = main;
		classSelect = Bukkit.createInventory(null, 9,"Select class");
		
		ItemStack Guerrier = new ItemStack(Material.DIAMOND_BLOCK,1);
		ItemStack Archer = new ItemStack(Material.DIAMOND_BLOCK,1);
		ItemStack Magicien = new ItemStack(Material.DIAMOND_BLOCK,1);
		
		ItemMeta GuerrierM = Guerrier.getItemMeta();
		ItemMeta ArcherM = Archer.getItemMeta();
		ItemMeta MagicienM = Magicien.getItemMeta();
		
		GuerrierM.setDisplayName("Classe Guerrier");
		ArcherM.setDisplayName("Classe Archer");
		MagicienM.setDisplayName("Classe Magicien");
		
		Guerrier.setItemMeta(GuerrierM);
		Archer.setItemMeta(ArcherM);
		Magicien.setItemMeta(MagicienM);
		
		classSelect.setItem(2, Guerrier);
		classSelect.setItem(4, Archer);
		classSelect.setItem(6, Magicien);
	}

	@EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException
    {
		Player p = event.getPlayer();
		nb = 0;
		
		result = statement.executeQuery("SELECT * FROM class WHERE Username = '"+p.getDisplayName()+"'");

		result.last();
		nb = result.getRow();
		result.beforeFirst();

	    new BukkitRunnable() {
	        @Override
	         public void run() {
	        	if(nb == 0) {
	        		p.openInventory(classSelect);
	        	}else {
		        	try {
						result.first();
						main.xp.put(p.getDisplayName(), result.getInt("xp"));
						main.classes.put(p.getDisplayName(), result.getInt("Classe"));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	         }
	     }.runTaskLater(main, 20L);
    }
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event) throws SQLException {
		
		Classe.setXp(event.getPlayer().getDisplayName(), main.xp.get(event.getPlayer().getDisplayName()));
		
		main.xp.remove(event.getPlayer().getDisplayName());
		main.classes.remove(event.getPlayer().getDisplayName());
	}
	
   @EventHandler
   public void onInventoryClick(InventoryClickEvent event) throws SQLException {
	   Player player = (Player) event.getWhoClicked();
	   //ItemStack clicked = event.getCurrentItem();
	   Inventory inventory = event.getInventory();
	   if (inventory.getName().equals(classSelect.getName())) {
		   if(event.getCurrentItem() != null) {
			   event.setCancelled(true);
			   if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Classe Guerrier")) {
				   player.sendMessage("Classe Guerrier choisie !");
				   Classe.setClasse(player.getDisplayName(), 1);
				   player.closeInventory();
			   }
			   if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Classe Archer")) {
				   player.sendMessage("Classe Archer choisie !");
				   Classe.setClasse(player.getDisplayName(), 2);
				   player.closeInventory();
			   }
			   if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Classe Magicien")) {
				   player.sendMessage("Classe Magicien choisie !");
				   Classe.setClasse(player.getDisplayName(), 3);
				   player.closeInventory();
			   }
		   }
	   }
   }
   

   @EventHandler
   public void onInteract(PlayerInteractEvent event) {
	   Player p = event.getPlayer();
	   //Action c = event.getAction();
	   
	   if(main.classes.containsKey(p.getDisplayName())) {
		   int classe = main.classes.get(p.getDisplayName());
		   if(classe == 1) {
			   if (!(event.getAction() == Action.RIGHT_CLICK_AIR)) return;
			   if(event.getMaterial() == Material.DIAMOND_SWORD) {
				   Entity ent = Functions.getNearestEntityInSight(p, 100);
				   Capacite.Guerrier2(p,ent);
			   }
		   }else if(classe == 3) {
			   if (!(event.getAction() == Action.RIGHT_CLICK_AIR)) return;
			   if(event.getMaterial() == Material.BLAZE_ROD) {
				   Entity ent = Functions.getNearestEntityInSight(p, 100);
				   if(ent != null)
					   Capacite.Magicien1(ent);
			   }
		   }
	   }
   }
   
   
}
