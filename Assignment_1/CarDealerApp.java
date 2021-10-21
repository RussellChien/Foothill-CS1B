//-------------------------------------------------------------------------------------------------------------------------

//  File name:  CarDealerApp.java

//  Program purpose: This program is to run the user interface for the car dealership

//  Revision history:

//   Date                  Programmer          Foothill ID

//   07/08/20              Russell Chien       20393765                    

// --------------------------------------------------------------------------------------------------------------------------------

public class CarDealerApp {
	public static void main(String [] args) {
		CarDealer cd = null;
		cd = new CarDealer("Cupertino");
		cd.init();
		cd.run();	
	}
}

