package download;

import java.sql.*;

public class TestJDBC {
	public static void main(String[] args) {
		try {
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
			String url = "jdbc:odbc:myClass";
			String user = "";
			String password = "";
			Connection con = DriverManager.getConnection(url, user, password);
			Statement stmt = con.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.TYPE_FORWARD_ONLY);
			ResultSet res = stmt.executeQuery("select * from Exam");
			while (res.next()) {
				System.out.println(res.getString(1));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
