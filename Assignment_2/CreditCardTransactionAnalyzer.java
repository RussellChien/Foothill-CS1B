/* 
-------------------------------------------------------------------------------------------------------------------------

@version1.0 07-18-20

@author Russell Chien

File name: CreditCardTransactionAnalyzer.java

Program purpose: This program analyzes a customer's credit card transactions by reading them in from a file. 
				 It provides info on each type of transaction, the date, the amount, and more specific 
				 information depending on the type of transaction. It also calculates reward points from
				 department store and grocery transactions and charges from bank transacions. 

Revision history:

Date     Programmer    Change ID Description

07-18-20 Russell Chien 0001      Initial implementation

 --------------------------------------------------------------------------------------------------------------------------------
*/
public class CreditCardTransactionAnalyzer {
	public static void main (String [] args) {
		Customer customer = new Customer("Bob Jones", "4321777789654081", 2000.50, 200); 
		customer.readTransactions();
		customer.reportTransactions();
		customer.reportCharges();
		customer.reportRewardSummary();
	}
}