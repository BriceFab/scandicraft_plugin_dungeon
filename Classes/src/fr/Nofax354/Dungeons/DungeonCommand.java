package fr.Nofax354.Dungeons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.Nofax354.main.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

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
			
			if(args.length == 0 && main.manager.getDungeonByPlayer(p) == null) {
				List<Player> pl = new ArrayList<>();
				pl.add(p);
				main.manager.joinDungeon(pl);
			}else if(args.length >= 1) {
				if(args[0].equalsIgnoreCase("create")) {
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
								if(!pl.equals(p)) {
									main.Pmanager.addWaiting(main.Pmanager.getPartyByPlayer(p), pl);
									p.sendMessage(pl.getDisplayName()+" a été invité dans votre partie");
									TextComponent msg = new TextComponent(ChatColor.GREEN+"accept");
									msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dungeon accept"));
									msg.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Cliquez pour rejoindre" ).create() ) );
									pl.sendMessage("vous avez été invité par "+p.getDisplayName()+" à rejoindre sa party");
									pl.sendMessage(msg);
								}else {
									p.sendMessage("Vous ne pouvez pas vous auto inviter !");
								}
							}else {
								p.sendMessage("--> joueur non existant");
							}
						}
					}else {
						p.sendMessage("tu n'est pas dans une partie");
					}
				}else if(args[0].equalsIgnoreCase("accept")) {
					if(pt == null) {
						if(main.Pmanager.waiting.containsKey(p)) {
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
				}else if(args[0].equalsIgnoreCase("help")) {
					p.sendMessage("[Dungeon Help]");
					TextComponent msg = new TextComponent("-->"+ChatColor.RED+"/dungeon");
					msg.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "rejoindre un donjon." ).create() ) );
					p.sendMessage(msg);
					msg = new TextComponent("-->"+ChatColor.RED+"/dungeon create");
					msg.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "créer une partie." ).create() ) );
					p.sendMessage(msg);
					msg = new TextComponent("-->"+ChatColor.RED+"/dungeon add <Player>");
					msg.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "ajouter un joueur à votre partie." ).create() ) );
					p.sendMessage(msg);
					msg = new TextComponent("-->"+ChatColor.RED+"/dungeon accept");
					msg.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "accepter l'invitation à une partie." ).create() ) );
					p.sendMessage(msg);
					msg = new TextComponent("-->"+ChatColor.RED+"/dungeon start");
					msg.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "démarrer la partie." ).create() ) );
					p.sendMessage(msg);
					if(p.hasPermission("dungeon.dungeons")) {
						msg = new TextComponent("-->"+ChatColor.RED+"/dungeons");
						msg.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "se téléporter à la map de donjons." ).create() ) );
						p.sendMessage(msg);
					}
				}else if(args[0].equalsIgnoreCase("getSpawn")) {
					Dungeon d = main.manager.getDungeonByPlayer(p);
					p.sendMessage(d.getLoc().getX()+","+d.getLoc().getY()+","+d.getLoc().getZ());
					if(d.getSpawn() != null) {
						
						p.sendMessage(d.getSpawn().getX()+","+d.getSpawn().getY()+","+d.getSpawn().getZ());
					}else {
						p.sendMessage("no spawn");
					}
				}
			}
		}
		return true;
	}

}
