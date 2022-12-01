package models;

public class Cargo {

	
	
	private int id;
	private int userid;
  
	private String name;
	private String color;
	private double weight;
	
	//private ArrayList<Transaction> transactions;
	
	public Cargo() {
		
	}
    public Cargo(int userid, String color, String name,String orderNumber,double weight) {
        this.name=name;
        this.orderNumber=orderNumber;
		this.userid = userid;
		this.color=color;
 		this.weight = weight;
	}
    public String getOrderNumber() {
  		return orderNumber;
  	}
  	public void setOrderNumber(String orderNumber) {
  		this.orderNumber = orderNumber;
  	}
  	private String orderNumber;
	
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
  
    /*
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}
	*/
}