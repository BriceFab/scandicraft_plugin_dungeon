package fr.Nofax354.Dungeons;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import fr.Nofax354.main.Main;

public class DungeonListeners implements Listener {

	Main main;
	
	public DungeonListeners(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if(event.getEntity().getKiller() instanceof Player) {
			Player p = event.getEntity().getKiller();
			if(p != null) {
				p.sendMessage("killer");
				int nb = main.dungeon.get(p);
				int xp = main.dungeonXp.get(nb);
				//p.sendMessage("xp : "+xp+" + 10");
				main.dungeonXp.replace(nb, xp+10);
				for(int i = 0;i < main.dungeonP.size();i++) {
					if(main.dungeon.get(main.dungeonP.get(i)) == nb) {
						main.dungeonP.get(i).sendMessage("+10 xp");
					}
				}
			}
		}
	}
	
}
