package models;

    import java.security.MessageDigest;
    import java.security.NoSuchAlgorithmException;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.util.ArrayList;
    import java.util.List;

    import Dao.DBConnect;
    import Entity.Cargo;
    import Entity.User;

    public class AdminModel extends DBConnect {


    // Declare DB objects
    DBConnect conn = null;
    Statement stmt = null;


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

    public int addUser(User user) {
    String sql = null;
    int adduser=0;
    String passwordHash="";
    try {

    passwordHash= createHashes(user.getPassword());
    } catch (NoSuchAlgorithmException e1) {
    // TODO Auto-generated catch block
    e1.printStackTrace();
    }
    sql = "INSERT INTO warehouse_users(name, password, admin) " +
    "VALUES (?,?,?)";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
    statement.setString(1,user.getName());
    statement.setString(2,passwordHash);
    statement.setString(3,user.getAdmin());

    adduser= statement.executeUpdate();


    } catch (SQLException e) {
    System.out.println("Error add user: " + e);
    }
    return adduser; // return addcount
    }
    //search inventory by user name
    public List<Cargo> getInventorybyopt(Cargo cargo) {
Cargo cargo1 = new Cargo();
List<Cargo> cargoes = new ArrayList<>();
    //using name to get userid
    String query = "SELECT id,name FROM warehouse_users WHERE name=?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
    statement.setString(1, cargo.getName());
    ResultSet resultSet = statement.executeQuery();
    while (resultSet.next()) {
    cargo1.setName(resultSet.getString("name"));
    cargo1.setUserid(resultSet.getInt("id"));
    }

    } catch (SQLException e) {
    System.out.println("Error fetching inventory: " + e);
    }

    //using userid to get inventory
    String query1 = "SELECT orderNumber,name,color,weight FROM warehouse_inventory WHERE userid=?";
    try (PreparedStatement statement1 = connection.prepareStatement(query1)) {
    statement1.setInt(1, cargo1.getUserid());
    ResultSet resultSet1 = statement1.executeQuery();
    while (resultSet1.next()) {
    Cargo cargo2 = new Cargo();
    // grab record data by table field name into ClientModel account object
    cargo2.setName(resultSet1.getString("name"));
    cargo2.setOrderNumber(resultSet1.getString("orderNumber"));
    cargo2.setColor(resultSet1.getString("color"));
    cargo2.setWeight(resultSet1.getDouble("weight"));
    cargo2.setUserid(cargo1.getUserid());

    cargoes.add(cargo2); // add data to arraylist
    }
    } catch (SQLException e) {
    System.out.println("Error fetching inventory: " + e);
    }
    return cargoes; // return arraylist
    }

    // update inventory record by ordernumber
    public int updateCargo(Cargo cargo) {
    String sql = null;
    int updatecount=0;
    sql = "update warehouse_inventory set name=?,color=?,"+"weight=? where orderNumber=?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {

    statement.setString(1,cargo.getName());
    statement.setString(2,cargo.getColor());
    statement.setDouble(3,cargo.getWeight());
    statement.setString(4,cargo.getOrderNumber());

    updatecount= statement.executeUpdate();
    } catch (SQLException e) {
    System.out.println("Error add inventory: " + e);
    }


    return updatecount ; // return updatecount
    }
    // delete inventory record by ordernumber
    public int deleteCargo(Cargo cargo) {
    String sql = null;
    int deletecount=0;
    sql = "delete from warehouse_inventory where orderNumber=? and name=? and color=? and weight=?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {

    statement.setString(1,cargo.getOrderNumber());
    statement.setString(2,cargo.getName());
    statement.setString(3,cargo.getColor());
    statement.setDouble(4,cargo.getWeight());

    deletecount= statement.executeUpdate();
    } catch (SQLException e) {
    System.out.println("Error add inventory: " + e);
    }


    return deletecount ; // return deletecount
    }


    }