package Main.Factions.MySql;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Main.Factions.Config.ConfigMySQL;

public class MySqlTables 
{
	
	public static MySqlConnection mysqlconnection = new MySqlConnection();
	public static ConfigMySQL configmysql = new ConfigMySQL();

	public void MySqlTablesFactions()
	{
		try{
			DatabaseMetaData dbm = mysqlconnection.getConection().getMetaData();
			ResultSet tables = dbm.getTables(null, null, "Factions", null);
			if (!tables.next()) {	
				Statement statement = mysqlconnection.getConection().createStatement();
				statement.executeUpdate("CREATE TABLE Factions "+
				"(UUID INT(16), "
				+ "Name VARCHAR(16), "
				+ "TAG VARCHAR(4), "
				+ "Owner VARCHAR(16), "
				+ "Description VARCHAR(50), "
				+ "Home LONGTEXT,"
				+ "Banner LONGTEXT,"
				+ "Money INT(15), "
				+ "Power INT(4), "
				+ "TotalKills INT(5), "
				+ "TotalDeaths INT(5), "
				+ "Date VARCHAR(50) "
				+ ")");
				
				statement.close();
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
	public void MySqlTablesPlayers()
	{
		try{
			DatabaseMetaData dbm = mysqlconnection.getConection().getMetaData();
			ResultSet tables = dbm.getTables(null, null, "Players", null);
			if (!tables.next()) {	
				Statement statement = mysqlconnection.getConection().createStatement();
				statement.executeUpdate("CREATE TABLE Players "+
				"(UUID VARCHAR(50), "
				+ "Name VARCHAR(16), "
				+ "FactionUUID INT(16), "
				+ "Money INT(10), "
				+ "Kills INT(5), "
				+ "Deaths INT(5), "
				+ "Power INT(2), "
				+ "MaxPower INT(2), "
				+ "Cash INT(8), "
				+ "FactionRank VARCHAR(16), "
				+ "LastLogin FLOAT(50) "
				+ "Orbs INT(6) "
				+ ")");
				
				statement.close();
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void MySqlTablesBans()
	{
		try{
			DatabaseMetaData dbm = mysqlconnection.getConection().getMetaData();
			ResultSet tables = dbm.getTables(null, null, "Bans", null);
			if (!tables.next()) {	
				Statement statement = mysqlconnection.getConection().createStatement();
				statement.executeUpdate("CREATE TABLE Bans "+
				"(Punish VARCHAR(50), "
				+ "BannedUUID VARCHAR(50), "
				+ "BannedName VARCHAR(16), "
				+ "BannedIP VARCHAR(16), "
				+ "ExecutorName VARCHAR(16), "
				+ "Duration FLOAT(50), "
				+ "Reason VARCHAR(200) "
				+ ")");
				
				statement.close();
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void MySqlTableBoxes()
	{
		try{
			DatabaseMetaData dbm = mysqlconnection.getConection().getMetaData();
			ResultSet tables = dbm.getTables(null, null, "Boxes", null);
			if (!tables.next()) {	
				Statement statement = mysqlconnection.getConection().createStatement();
				statement.executeUpdate("CREATE TABLE Boxes "
				+ "(UUID VARCHAR(50), "
				+ "Name VARCHAR(16) "
				+ ")");
				
				statement.close();
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}


}
