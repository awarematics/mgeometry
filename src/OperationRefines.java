import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

//@WebServlet(name = "GetAndPostExample", urlPatterns = {"/GetAndPostExample"})
public class OperationRefines {
	/*public static void main(String[] args) throws SQLException{
		String name = Postgresql("select m_astext(mt) from usertrajs where id=1;");
		System.out.println(name);
	}*/
	public static String Postgresql(String sql) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		String name = "";
		String type = "";
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://202.31.147.196/experiment", "postgres",
					"mcalab3408");
		} catch (SQLException e) { 
			e.printStackTrace();  
		} 
		
		
		String temptable =  "CREATE TEMPORARY table t AS "+sql; 
		statement = connection.createStatement();	
		int update = statement.executeUpdate(temptable);
		 		
		String selecttemp = "select * from t;";
		ResultSet resultSet = statement.executeQuery(selecttemp);
		
		ResultSetMetaData rsmd = resultSet.getMetaData();
		while (resultSet.next()) {
			String columns = null;
			for (int i = 1; i <= rsmd.getColumnCount(); i++) { 
				if (i == 1)
					columns = resultSet.getString(i);
				else
					columns = columns + "@" + resultSet.getString(i);
			}
			if (columns != null)
				name = name + columns + "#"; 
		}		
		
		String typename = "select column_name from information_schema.columns where table_name='t'";
		ResultSet resultSet1 = statement.executeQuery(typename);
		
		ResultSetMetaData rsmd1 = resultSet1.getMetaData();
		while (resultSet1.next()) {
			String column = null;
			for (int i = 1; i <= rsmd1.getColumnCount(); i++) { 
				if (i == 1)
					column = resultSet1.getString(i);
				else
					column = column + "@" + resultSet1.getString(i);
			}
			if (column != null)
				type = type + column + "#"; 
		}
		 statement.close();
		 connection.close();
		return type+"*"+name;
	}
}