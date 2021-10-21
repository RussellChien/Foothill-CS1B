public class DepartmentStoreTransaction extends Transaction implements Rewardable {
	private String departmentName;
	private int returnPolicy;

	public DepartmentStoreTransaction() {
		super();
		this.departmentName = "none";
		this.returnPolicy = 0;
	}

	public DepartmentStoreTransaction(int transactionID, int month, int day, int year, double transactionAmount, String departmentName, int returnPolicy) {
		super(transactionID, month, day, year, transactionAmount);
		this.departmentName = departmentName;
		this.returnPolicy = returnPolicy; 
	}

	public DepartmentStoreTransaction(int transactionID, Date date, double transactionAmount, String departmentName, int returnPolicy) {
		super(transactionID, date, transactionAmount);
		this.departmentName = departmentName;
		this.returnPolicy = returnPolicy;
	}	

	public String getDepartmentName() { return departmentName; }

	public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

	public int getReturnPolicy() { return returnPolicy; }

	public void setReturnPolicy(int returnPolicy) { this.returnPolicy = returnPolicy; }

	public String toString() {
		return "Department Store~" + super.toString() + "~" + departmentName + "~" + returnPolicy;
	}

	public void list() {
		System.out.println(date + "\tDepartment Store\t" + departmentName + "\t" + transactionAmount + "\tReturn in " + returnPolicy + " days");
	}

	public double earnPoints() {
		return transactionAmount * 3;
	}
}