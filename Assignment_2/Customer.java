import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.Arrays;
import java.util.Comparator;

public class Customer {
	private static final int MAX_SIZE = 1024;
	private static final String INPUT_FILE_LOCATION = "/Users/Russell/Desktop/Code/Java/CS1B/assignment_2/transactions.txt" ;

	private String name;
	private String creditCardNumber;
	private double balance;
	private double pointBalance;
	private Transaction[] transactions;
	private Rewardable[] rewardables; 

	public Customer() {
		this.name = "John Doe";
		this.creditCardNumber = "1111222233334444";
		this.balance = 0.0;
		this.pointBalance = 1000.0; 
		transactions = new Transaction[MAX_SIZE];
		rewardables = new Rewardable[MAX_SIZE];
	}

	public Customer(String name, String creditCardNumber, double balance, double pointBalance) {
		this.name = name;
		this.creditCardNumber = creditCardNumber;
		this.balance = balance;
		this.pointBalance = pointBalance;
		transactions = new Transaction[MAX_SIZE];
		rewardables = new Rewardable[MAX_SIZE];
	}

	public void readTransactions() {
		 Path inputFilePath = Paths.get(INPUT_FILE_LOCATION);          
         BufferedReader reader = null;
         String line = null;
         int index = 0;
         System.out.println("\t\t\tTRANSACTION LISTING REPORT");
         try {
            reader = Files.newBufferedReader(inputFilePath, StandardCharsets.US_ASCII);
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split("~");
                    String dateString = tokens[1];
                    String[] dateTokens = dateString.split("/");
                    int month = Integer.parseInt(dateTokens[0]);
                    int day = Integer.parseInt(dateTokens[1]);
                    int year = Integer.parseInt(dateTokens[2]);
                    Date date = new Date(month, day, year);
                    if (tokens[0].equals("BK")) {
                    	BankingTransaction bankingTransaction = new BankingTransaction(Integer.parseInt(tokens[2]), date, Double.parseDouble(tokens[3]), tokens[4], Double.parseDouble(tokens[5]));
                    	transactions[index] = bankingTransaction;
                    } else if (tokens[0].equals("GR")) {
                    	GroceryTransaction groceryTransaction = new GroceryTransaction(Integer.parseInt(tokens[2]), date, Double.parseDouble(tokens[3]), tokens[4]);
                    	transactions[index] = groceryTransaction;
                    } else if (tokens[0].equals("DS")) {
                    	DepartmentStoreTransaction departmentStoreTransaction = new DepartmentStoreTransaction(Integer.parseInt(tokens[2]), date, Double.parseDouble(tokens[3]), tokens[4], Integer.parseInt(tokens[5]));
                    	transactions[index] = departmentStoreTransaction;
                    }
                    index++;
                }
                reader.close();
        } catch (IOException e )  {  
            e.printStackTrace ();
        }
    	Arrays.sort(transactions, Customer.TransactionComparator);
        index = 0;
        for (Transaction transaction : transactions) {
            if (transaction instanceof Rewardable) {
            	rewardables[index] = (Rewardable) transaction;
            }
            index++;
        }
    }

	public void reportTransactions() {
		for (Transaction transaction : transactions) {
			if (transaction == null) { break; }
			else { transaction.list(); }
		}
	}

	public void reportCharges() {
		double charge = 0;
		for (Transaction transaction : transactions) {
			if (transaction instanceof BankingTransaction) {
				BankingTransaction bankTransaction = (BankingTransaction) transaction; 
				charge += bankTransaction.getCharge();
			}
		}
		System.out.println("Total Charges: " + charge);
	}

	public void reportRewardSummary() {
		System.out.println("Reward Summary for " + name + " " + creditCardNumber.substring(12));
		double departmentStorePoints = 0;
		double groceryStorePoints = 0;
		for (Rewardable transaction : rewardables) {
			if (transaction instanceof GroceryTransaction) {
				GroceryTransaction groceryTransaction = (GroceryTransaction) transaction;
				groceryStorePoints += groceryTransaction.earnPoints();
			} else if (transaction instanceof DepartmentStoreTransaction) {
				DepartmentStoreTransaction departmentStoreTransaction = (DepartmentStoreTransaction) transaction;
				departmentStorePoints += departmentStoreTransaction.earnPoints();
			}
		}
		System.out.println("Previous points balance\t\t\t\t" + pointBalance); 
		pointBalance += groceryStorePoints;
		pointBalance += departmentStorePoints; 
		System.out.println("+ Points earned on Department store purchases:\t" + departmentStorePoints);
		System.out.println("+ Points earned on Grocery Store purchases:\t" + groceryStorePoints); 
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("=  Total points available for redemption\t" + pointBalance);
	}

	public static Comparator<Transaction> TransactionComparator = new Comparator<Transaction>() {
    public int compare(Transaction transaction, Transaction otherTransaction) {
      if (transaction == null && otherTransaction == null) {
        return 0;
      } else if (transaction == null) {
        return 1;
      } else if (otherTransaction == null) {
        return -1;
      } else {
        return (int) transaction.getTransactionAmount() - (int) otherTransaction.getTransactionAmount();
      }
    }
  };
}