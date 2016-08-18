package Practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;

public class DB 
{
	static Statement st = null;
	static Connection con = null;
	
	public static void main(String[] args) 
	{
		try
		{
			Statement st = getStatement();
			String query = "create table student(id number(10), name varchar(25))";
			st.execute(query);
		}
		catch(NoSuchElementException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(st!=null)
			{
				try
				{
					st.close();
					boolean status = st.isClosed();
					if(status == true)
					{
						st = null;
					}
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
			if(con!=null)
			{
				try
				{
					con.close();
					boolean status = st.isClosed();
					if(status == true)
					{
						con = null;
					}
				}
				catch(SQLException  e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	public static Statement getStatement() throws ClassNotFoundException, SQLException
	{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		con = DriverManager.getConnection("jdbc:odbc:mydsn", "system", "123");
		st = con.createStatement();
		return st;
	}
}
