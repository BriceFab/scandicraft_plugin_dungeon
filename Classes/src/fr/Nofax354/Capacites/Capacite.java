package fr.Nofax354.Capacites;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.minecraft.server.v1_8_R3.EntityLightning;

public class Capacite {
	public static void Guerrier1(Player p) {
		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 2));
	}
	public static void Guerrier2(Player p,Entity target) {
		target.teleport(p);
	}
	public static void Guerrier3(Player p) {
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3, 3));
		p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 1, 5));
	}
	
	public static void Magicien1(Entity p) {
		Bukkit.getWorld("world").strikeLightning(p.getLocation());
	}
	
	public static void Magicien1(Location loc) {
		Bukkit.getWorld("world").strikeLightning(loc);
	}
	
	public static void Magicien2(Player p,Player r) {
		p.setHealth(20);
		r.setHealth(20);
	}
	
	public static void Archer3(Player r) {
		r.setHealth(r.getHealth()/2);
	}
}
