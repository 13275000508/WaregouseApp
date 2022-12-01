package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
 
import Dao.DBConnect;

public class ClientModel extends DBConnect {

	
	// Declare DB objects
	DBConnect conn = null;
	Statement stmt = null;
	

	public List<Cargo> getInventory(Cargo cargo) {
		List<Cargo> cargoes = new ArrayList<>();
		String query = "SELECT name,color,weight FROM inventory WHERE orderNumber = ?;";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, cargo.getOrderNumber());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Cargo cargo1 = new Cargo();
				// grab record data by table field name into ClientModel account object
				cargo1.setName(resultSet.getString("name"));
				cargo1.setColor(resultSet.getString("color"));
				cargo1.setWeight(resultSet.getDouble("weight"));
				cargoes.add(cargo1); // add account data to arraylist
			}
		} catch (SQLException e) {
			System.out.println("Error fetching inventory: " + e);
		}
		return cargoes; // return arraylist
	}
	public int addInbound(Cargo cargo) {
		 String sql = null;
		 int addcount=0;
		 sql = "INSERT INTO inventory(orderNumber,name, color, weight,userid) " +
				  "VALUES (?,?,?,?,?)";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1,cargo.getOrderNumber());
			statement.setString(2,cargo.getName());
			statement.setString(3,cargo.getColor());
			statement.setDouble(4,cargo.getWeight());
			statement.setInt(5,cargo.getUserid());
		
			addcount= statement.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println("Error add inventory: " + e);
		}
		return addcount; // return addcount
	}
	public int outbound(Cargo cargo) {
		 String sql = null,sql1=null;
		 int outboundcount=0,addcount=0;
		 sql = "DELETE FROM inventory where orderNumber = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1,cargo.getOrderNumber());
		
	
		
			outboundcount= statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error add inventory: " + e);
		}
		sql = "INSERT INTO outbound(orderNumber,name, color, weight,userid) " +
				  "VALUES (?,?,?,?,?)";
		try (PreparedStatement statement1 = connection.prepareStatement(sql)) {
			statement1.setString(1,cargo.getOrderNumber());
			statement1.setString(2,cargo.getName());
			statement1.setString(3,cargo.getColor());
			statement1.setDouble(4,cargo.getWeight());
			statement1.setInt(5,cargo.getUserid());
			
				addcount= statement1.executeUpdate();

			} catch (SQLException e) {
				System.out.println("Error add inventory: " + e);
			}
		
		return outboundcount ; // return Outboundcount
	}

	
}