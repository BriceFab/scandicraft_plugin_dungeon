package fr.Nofax354.Dungeons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import fr.Nofax354.main.Main;

public class PartyManager {
	private List<DungeonParty> parties = new ArrayList<>();
	
	public HashMap<Player, DungeonParty> waiting = new HashMap<Player, DungeonParty>();
	
	Main main;
	public PartyManager(Main main) {
		this.main = main;
	}
	
	public void createParty(Player p) {
		parties.add(new DungeonParty(p,main));
	}
	
	public void deleteParty(DungeonParty party) {
		parties.remove(party);
	}
	
	public void addPlayer(DungeonParty party,Player p) {
		party.addPlayer(p);
	}
	
	public void addWaiting(DungeonParty party,Player p) {
		waiting.put(p, party);
	}
	
	public void removeWaiting(Player p) {
		waiting.remove(p);
	}
	
	public DungeonParty getPartyByPlayer(Player p) {
		for(DungeonParty party : parties) {
			if(party != null)
			if(party.getPlayers().contains(p)) {
				return party;
			}
		}
		return null;
	}
}
