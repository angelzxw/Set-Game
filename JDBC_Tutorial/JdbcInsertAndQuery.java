import java.sql.*;

public class JdbcInsertAndQuery {
	public static void main(String[] args){
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://199.98.20.118/TestDatabase","TDguest", "TDpass");
			if (!con.isClosed()){
				System.out.println("Successfully connceted to MySQL Server...");
			}
			Statement s = con.createStatement();
			int count;
			
			//create a table and insert data
			s.executeUpdate("DROP TABLE IF EXISTS animal");
			s.executeUpdate("CREATE TABLE animal (" + "id INT UNSIGNED NOT NULL AUTO_INCREMENT," +"PRIMARY KEY (id)," +"name CHAR (40), category CHAR (40))");
			count = s.executeUpdate("INSERT INTO animal (name, category)" + " VALUES" + "('snake', 'reptile'," + "('frog', 'amphibian')," + "('tuna', 'fish')," + "('racoon', 'mammal')");
			System.out.println(count + "rows were inserted");
			
			//Query the database and iterate through the result set
			s.executeQuery("SELECT id, name, category FROM animal");
			ResultSet rs = s.getResultSet();
			count = 0;
			while (rs.next()) {
				int idVal = rs.getInt("id");
				String nameVal = rs.getString("name");
				String catVal = rs.getString("category");
				System.out.println("id = " + idVal + ", name = " + nameVal + ", category = " + catVal);
				++count;
			}
			System.out.println(count + " rows were retrieved");
			
			rs.close();
			s.close();
		} catch (Exception e){
			System.out.println("Exception: " + e.getMessage());
		} finally{
			try {
				if (con != null)
					con.close();
			} catch (SQLException e){}
		}
	}
}
