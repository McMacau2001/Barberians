package Main.Factions.MySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Main.Factions.Config.ConfigMySQL;

public class MySqlConnection
{

	public static Connection Connection;
	public static ConfigMySQL configMySQL = new ConfigMySQL();
	
	@SuppressWarnings("static-access")
	public Connection MySQLConnection()
	{	
		if(Connection != null )return Connection;
		try{		
			synchronized (this)
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection =DriverManager.getConnection("jdbc:mysql://"+configMySQL.Host+":"+configMySQL.Port+"/"+configMySQL.DataBase, configMySQL.User, configMySQL.Password);				
				return Connection;
			}

		}
		catch(SQLException e){}
		catch(ClassNotFoundException e){}
		
		return null;
	}
	
	public void MySQLConnectionClose(){
		try{
			Connection.close();
		}
		catch(SQLException e){
		}
	}

	public Connection getConection() {
		return MySQLConnection();
	}
}
