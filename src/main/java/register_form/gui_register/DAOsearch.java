package register_form.gui_register;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOsearch {
	public List<String> findAll() throws SQLException {
		List<String> pass = new ArrayList<String>();
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/register_form", "",
				"");
		String query = "select * from account";
		PreparedStatement ps = connection.prepareStatement(query);
		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				String password = rs.getString("password");
				pass.add(password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return pass;

	}
}
