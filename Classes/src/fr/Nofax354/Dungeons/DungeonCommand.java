package fr.Nofax354.Dungeons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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
			DungeonParty pt = main.Pmanager.getPartyByPlayer(p);
			DungeonParty wait = main.Pmanager.waiting.get(p);
			if(args.length == 0 && main.manager.getDungeonByPlayer(p) == null) {
				List<Player> pl = new ArrayList<>();
				pl.add(p);
				main.manager.joinDungeon(pl);
			}else if(args.length >= 1) {
				if(args[0].equalsIgnoreCase("party")) {
					if(pt == null) {
						main.Pmanager.createParty(p);
						p.sendMessage("Partie créée");
					}else {
						p.sendMessage("tu est deja dans une partie");
					}
				}else if(args[0].equalsIgnoreCase("add")) {
					if(pt != null) {
						if(args.length == 1) {
							p.sendMessage("--> /dungeon add <Player>");
						}else {
							Player pl = Bukkit.getPlayer(args[1]);
							if(pl != null) {
								main.Pmanager.addWaiting(main.Pmanager.getPartyByPlayer(p), pl);
								p.sendMessage(pl.getDisplayName()+" a été invité dans votre partie");
								pl.sendMessage("vous avez été invité par "+p.getDisplayName()+" à rejoindre sa party\nfaites /dungeon accept pour la rejoindre");
							}else {
								p.sendMessage("--> joueur non existant");
							}
						}
					}else {
						p.sendMessage("tu n'est pas dans une partie");
					}
				}else if(args[0].equalsIgnoreCase("accept")) {
					if(pt == null) {
						if(wait != null) {
							if(!main.Pmanager.waiting.get(p).started()) {
								main.Pmanager.addPlayer(main.Pmanager.waiting.get(p), p);
								main.Pmanager.removeWaiting(p);
								p.sendMessage("Vous avez rejoin la party de "+main.Pmanager.getPartyByPlayer(p).getOwner().getDisplayName()+" !");
							}else {
								p.sendMessage("cette partie a déja débuté");
							}
						}else {
							p.sendMessage("tu n'as été invité dans aucune partie.");
						}
					}else {
						p.sendMessage("tu est deja dans une partie");
					}
				}else if(args[0].equalsIgnoreCase("start")) {
					if(pt != null) {
						Player owner = pt.getOwner();
						if(owner.equals(p)) {
							pt.start();
						}else {
							p.sendMessage("tu n'est pas le owner de la partie  !");
						}
					}else {
						p.sendMessage("tu n'est pas dans une partie !");
					}
				}
			}
		}
		return true;
	}

}
