public class GroceryTransaction extends Transaction implements Rewardable {
	private String storeName;

	public GroceryTransaction() {
		super();
		this.storeName = "none";
	}

	public GroceryTransaction(int transactionID, int month, int day, int year, double transactionAmount, String storeName) {
		super(transactionID, month, day, year, transactionAmount);
		this.storeName = storeName;
	}

	public GroceryTransaction(int transactionID, Date date, double transactionAmount, String storeName) {
		super(transactionID, date, transactionAmount);
		this.storeName = storeName;
	}

	public String getStoreName() { return storeName; } 

	public void setStoreName(String storeName) { this.storeName = storeName; }

	public String toString() {
		return "Grocery~" + super.toString() + "~" + storeName;
	}

	public void list() {
		System.out.println(date + "\tGrocery\t\t\t" + storeName + "\t" + transactionAmount);
	}

	public double earnPoints() {
		return transactionAmount * 5; 
	}
}

