package fr.Nofax354.Dungeons;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.Nofax354.main.Main;

public class DungeonCommand implements CommandExecutor {
	
	Main main;
	
	public DungeonCommand(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
		
			if(main.dungeonP.contains(p)) {
				main.dungeon.remove(p);
				main.dungeonP.remove(p);
				p.sendMessage("Vous avez été enlevé de la file d'attente pour le donjon");
			}else {
				if(main.dungeonT.isState(DungeonState.WAITING) && main.dungeon.size() >= 1) {
					DungeonWaitTask waitTask = new DungeonWaitTask(main);
					waitTask.runTaskTimer(main, 0, 20);
					main.dungeonT.setState(DungeonState.STARTING);
					for(int i = 0;i < main.dungeonP.size();i++) {
						main.dungeonP.get(i).sendMessage("Le donjon débutera dans 30s");
					}
				}
				main.dungeonP.add(p);
				main.dungeon.put(p, main.nb);
				p.sendMessage("Vous avez été ajouté à la file d'attente pour le donjon ("+main.dungeon.size()+"/2)");
			}
		}
		return true;
	}

}
