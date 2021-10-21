public abstract class Transaction implements Comparable<Transaction> {
	protected int transactionID;
	protected Date date;
	protected double transactionAmount;

	public Transaction() {
		this.transactionID = 0;
		this.date = new Date();
		this.transactionAmount = 0.0;
	}

	public Transaction(int transactionID, int month, int day, int year, double transactionAmount) {
		this.transactionID = transactionID;
		this.date = new Date(month, day, year);
		this.transactionAmount = transactionAmount;
	}

	public Transaction(int transactionID, Date date, double transactionAmount) {
		this.transactionID = transactionID;
		try {
			this.date = date.clone();
		} catch (CloneNotSupportedException e) {
			this.date = new Date();
		}
		this.transactionAmount = transactionAmount;
	}

	public abstract void list();

	public String toString() {
		return date + "~" + transactionID + "~" + transactionAmount;
	}

	public int getTransactionID() { return transactionID; }

	public void setTransactionID(int transactionID) { this.transactionID = transactionID; }

	public Date getDate() { return date; }

	public void setDate(Date date) throws CloneNotSupportedException { this.date = date.clone(); }

	public double getTransactionAmount() { return transactionAmount; }

	public void setTransactionAmount(double transactionAmount) { this.transactionAmount = transactionAmount; }

	public boolean equals(Object other) {
		if (other == null || !(other instanceof Transaction)) { return false; }
		Transaction transaction = (Transaction) other;
		return transaction.transactionID == this.transactionID;
	}

	public int compareTo(Transaction other) {
		if (this == null && other == null) {
			return 0;
		} else if (this == null) {
        	return 1;
      	} else if (other == null) {
        	return -1;
      	}
		if (other.equals(this)) {
			return 0;
		} else if (this.transactionID > other.transactionID) {
			return 1;
		} else {
			return -1;
		}
	}
}