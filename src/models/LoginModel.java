package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Dao.DBConnect;

public class LoginModel extends DBConnect {
 
	private Boolean admin;
	private int id;
 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean isAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
		
	static String createHashes(String passwd) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(passwd.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sbHash = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sbHash.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sbHash.toString();
	}

	public Boolean getCredentials(String username, String password){
		 String passwordHash="";
		 try {
			
			 passwordHash= createHashes(password);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
           
        	String query = "SELECT * FROM warehouse_users WHERE name = ? and password = ?;";
            try(PreparedStatement stmt = connection.prepareStatement(query)) {
               stmt.setString(1, username);
               stmt.setString(2, passwordHash);
               ResultSet rs = stmt.executeQuery();
                if(rs.next()) { 
                 
                	setId(rs.getInt("id"));
                	setAdmin(rs.getBoolean("admin"));
                	return true;
               	}
             }catch (SQLException e) {
            	e.printStackTrace();   
             }
			return false;
    }

}//end class