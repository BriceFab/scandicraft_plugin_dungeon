package fr.Nofax354.Classes;

import java.sql.SQLException;
import java.sql.Statement;

import fr.Nofax354.main.Main;

public class Classe {
	
	Main main;
	static Statement statement;
	
	@SuppressWarnings("static-access")
	public Classe(Main main, Statement statement) {
		this.main = main;
		this.statement = statement;
	}

	public static void setClasse(String Username,int Class) throws SQLException {
		statement.executeUpdate("INSERT INTO class (Username, Classe) VALUES ('"+Username+"', "+Class+");");
	}
	
	public static void setXp(String Username,int Xp) throws SQLException {
		statement.executeUpdate("UPDATE `class` SET xp="+Xp+" WHERE Username = '"+Username+"';");
	}
}
