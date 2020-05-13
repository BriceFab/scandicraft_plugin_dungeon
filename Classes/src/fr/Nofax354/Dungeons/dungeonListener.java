package fr.Nofax354.Dungeons;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.Nofax354.main.Main;

public class dungeonListener implements Listener {

	Main main;
	
	public dungeonListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if(event.getEntity().getKiller() instanceof Player) {
			
			Player p = event.getEntity().getKiller();
			Dungeon d = main.manager.getDungeonByPlayer(p);
			if(!event.getEntity().getName().equalsIgnoreCase("Boss")) {
				if(d.getEntities().contains(event.getEntity())) {
					d.removeEntity(event.getEntity());
					d.setXp(d.getXp()+10);
					for(Player pl : d.getPlayers()) {
						pl.sendMessage("+10xp ("+d.getXp()+")");
					}
				}
			}else {
				d.setXp(d.getXp()+100);
				for(Player pl : d.getPlayers()) {
					pl.sendMessage("+100xp ("+d.getXp()+")");
				}
				
				for(Player pl : d.getPlayers()) {
					int xpAdd = d.getXp() / d.getPlayers().size();
					int xpCurr = main.xp.get(pl.getDisplayName());
					xpAdd+=xpCurr;
					main.xp.replace(pl.getDisplayName(), xpAdd);
					
					pl.sendMessage("xp: "+main.xp.get(pl.getDisplayName()));
				}
				d.restart();
				
			}
		}else if(event.getEntity() instanceof Player) {
			Player p = (Player) event.getEntity();
			Dungeon d = main.manager.getDungeonByPlayer(p); 
			if(d != null) {
				d.removePlayer(p);
				p.sendMessage("tu n'a pas r�ussi a aboutir le donjon");
			}
			if(d.getPlayers().size() == 0) {
				d.restart();
			}
		}
	}
	
	@EventHandler
	public void onDisconnect(PlayerQuitEvent e) {
		if(e.getPlayer() instanceof Player) {
			Player p = e.getPlayer();
			if(p != null) {
				Dungeon d = main.manager.getDungeonByPlayer(p);
				if(d != null) {
					d.removePlayer(p);
					if(d.getPlayers().size() == 0) {
						d.restart();
					}
				}
			}
		}
	}

}
