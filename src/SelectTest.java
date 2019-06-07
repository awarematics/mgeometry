import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

//@WebServlet(name = "GetAndPostExample", urlPatterns = {"/GetAndPostExample"})
public class SelectTest {
	
	public static String Postgresql(String sql) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		String name = "";
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://169.254.52.252:6000/postgres", "postgres",
					"postgres");
		} catch (SQLException e) { 
			e.printStackTrace();  
		} 
		statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
	//	String column = "select column_name from information_schema.columns where table_schema='public' and table_name='uservideos'";
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
		return name;
	}
}