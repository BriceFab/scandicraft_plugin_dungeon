package fr.Nofax354.Dungeons;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.Nofax354.main.Main;

public class dungeonCommand implements CommandExecutor {

	Main main;
	
	public dungeonCommand(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length == 0) {
				if(main.attente.contains(p)) {
					main.attente.remove(p);
					p.sendMessage("vous n'êtes plus dans la file d'attente");
				}else {
					main.attente.add(p);
					p.sendMessage("vous êtes numéro "+main.attente.size()+" dans la file d'attente");
				}
			}else if(args.length == 1 && args[0].equalsIgnoreCase("status")) {
				if(main.attente.contains(p)) {
					p.sendMessage("vous êtes numéro "+(main.attente.lastIndexOf(p)+1)+" dans la file d'attente");
				}else {
					p.sendMessage("vous n'êtes pas dans la file d'attente");
				}
			}
		}
		return true;
	}

}
