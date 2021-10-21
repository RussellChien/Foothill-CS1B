public class BankingTransaction extends Transaction {
	private String type;
	private double charge;

	public BankingTransaction() {
		super();
		this.type = "none";
		this.charge = 0;
	}

	public BankingTransaction(int transactionID, int month, int day, int year, double transactionAmount, String type, double charge) {
		super(transactionID, month, day, year, transactionAmount);
		this.type = type;
		this.charge = charge; 
	}

	public BankingTransaction(int transactionID, Date date, double transactionAmount, String type, double charge) {
		super(transactionID, date, transactionAmount);
		this.type = type;
		this.charge = charge; 
	}

	public String getType() { return type; }

	public void setType(String type) { this.type = type; }	

	public double getCharge() { return charge; } 

	public void setCharge(double charge) { this.charge = charge; }
	
	public String toString() {
		double totalAmount = charge + transactionAmount;
		return "Bank~" + super.toString() + "~" + type + "~" + totalAmount; 
	}

	public void list() {
		double totalAmount = charge + transactionAmount;
		System.out.println(date + "\tBank\t\t\t" + type + "\t" + totalAmount);
	}
}