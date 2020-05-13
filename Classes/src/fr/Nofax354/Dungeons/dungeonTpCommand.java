package fr.Nofax354.Dungeons;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.Nofax354.main.Main;

public class dungeonTpCommand implements CommandExecutor {
	
	Main main;
	
	public dungeonTpCommand(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("dungeon.dungeons")) {
				if(!p.getWorld().equals(Dungeon.getWorld("Donjon"))) {
					p.teleport(new Location(Dungeon.getWorld("Donjon"),0,0,0));
				}else {
					p.teleport(main.spawn);
				}
			}
		}else {
			sender.sendMessage("La commande n'est executable que par un joueur");
		}
		
		return true;
	}

}
