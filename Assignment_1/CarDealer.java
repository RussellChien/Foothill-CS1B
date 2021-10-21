//-------------------------------------------------------------------------------------------------------------------------

//  File name:  CarDealer.java

//  Program purpose: This program processes the car dealership 

//  Revision history:

//   Date                  Programmer          Foothill ID

//   07/08/20              Russell Chien       20393765                      

// --------------------------------------------------------------------------------------------------------------------------------


import java.util.Scanner;

public class CarDealer {
	private Vehicle[] vehicleList;
	private String location;
	private int vehicleCount;
	private static String DEALERSHIP_BRAND = "Foothill Car Dealership";
	private static int MAX_CARS = 1024;
	private Scanner sc = new Scanner(System.in);
	private boolean active = true;

	public CarDealer() {
		vehicleList = new Vehicle[MAX_CARS];
		vehicleCount = 0;
		location = "The Lost City";
	}

	public CarDealer(String location) {
		vehicleList = new Vehicle[MAX_CARS];
		vehicleCount = 0;
		this.location = location;
	}

	private void menu() {
		System.out.println("\t\tMENU\n1. View Vehicle Inventory\n2. Search by make and model\n3. Search by Price\n4. Quit");
	}

	private void viewInventory() {
		for(int i = 0; i < vehicleList.length; i++) { 
			if (vehicleList[i] == null);
			else {
				System.out.println(vehicleList[i]); 
			}
		}
	}

	private void searchMakeModel() {
		boolean none = true;
		String make = "";
		String model = "";
		sc.nextLine();
		System.out.print("\n\tEnter vehicle make: ");
		try { 
			make = sc.nextLine();
		} catch (Exception e) {
			System.out.println("Invalid input.");
		}
		System.out.print("\tEnter vehicle model: ");
		try {
			model = sc.nextLine();
		} catch (Exception e) {
			System.out.println("Invalid input.");
		}
		for(int i = 0; i < vehicleList.length; i++) {
			if (vehicleList[i] == null);
			else if (vehicleList[i].getMake().equals(make) && vehicleList[i].getModel().equals(model)) {
				System.out.println("\t" + vehicleList[i]);
				none = false;
			}
		}
		if (none) { System.out.println("\tNo such vehicle is found."); }
	}

	private void searchPriceRange() {
		boolean none = true;
		double min = 0;
		double max = 0;
		String input = "";
		sc.nextLine();
		System.out.print("\tEnter min price: ");
		try {
			input = sc.nextLine();
		} catch (Exception e) {
			System.out.println("Invalid input.");
		}
		try {
            min = Double.parseDouble(input);
		} catch (NumberFormatException e) {
			System.out.println("Invalid input.");
		}
		System.out.print("\tEnter max price: ");
		try {
			input = sc.nextLine();
		} catch (Exception e) {
			System.out.println("Invalid input.");
		}
		try {
            max = Double.parseDouble(input);
		} catch (NumberFormatException e) {
			System.out.println("Invalid input.");
		}
		for(int i = 0; i < vehicleList.length; i++) {
			if (vehicleList[i] == null);
			else if (vehicleList[i].getPrice() > min && vehicleList[i].getPrice() < max) {
				System.out.println("\t" + vehicleList[i]);
				none = false;
			}
		}
		if (none) { System.out.println("\tNo vehicles found within price range."); }
	}

	private void searchSimilarVehicles() {
		boolean none = true;
		String make = "";
		String model = "";
		String input = "";
		int year = 0;
		sc.nextLine();
		System.out.print("\n\tEnter vehicle make: ");
		try { 
			make = sc.nextLine();
		} catch (Exception e) {
			System.out.println("Invalid input.");
		}
		System.out.print("\tEnter vehicle model: ");
		try { 
			model = sc.nextLine();
		} catch (Exception e) {
			System.out.println("Invalid input.");
		}
		System.out.print("\tEnter vehicle year: ");
		try { 
			input = sc.nextLine();
		} catch (Exception e) {
			System.out.println("Invalid input.");
		}
		try {
            year = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			System.out.println("Invalid input.");
		}
		Vehicle similar = new Vehicle(make, model, year, 0.0);
		for(int i = 0; i < vehicleList.length; i++) {
			if (vehicleList[i] == null);
			else if (vehicleList[i].getYear() > year) {
				if (none) { System.out.println("\tNo such vehicle is found."); }
				return;
			} else if (vehicleList[i].equals(similar)) {
				System.out.println("\t" + vehicleList[i]);
				none = false;
			}
		}
		if (none) { System.out.println("\tNo such vehicle is found."); }
	}

	private void quit() {
		System.out.println("\tThanks for shopping at " + DEALERSHIP_BRAND + " at " + location + ".");
		active = false;
	}

	public static String GET_DEALERSHIP_BRAND() { return DEALERSHIP_BRAND; }

	public static int GET_MAX_CARS() { return MAX_CARS; }

	public String getLocation() { return location; }

	public void setLocation(String location) { this.location = location; }

	public int getVehicleCount() { return vehicleCount; }

	public void setVehicleCount(int vehicleCount) { this.vehicleCount = vehicleCount; }

	public void init() {
		vehicleCount = 10;

		String[] vehicleMakes = new String[10];
		vehicleMakes[1] = "Toyota";
		vehicleMakes[3] = "Mercedes-Benz";
		vehicleMakes[5] = "Audi";
		vehicleMakes[7] = "Honda";
		vehicleMakes[9] = "Honda";

		String[] vehicleModels = new String[10];
		vehicleModels[1] = "Camry";
		vehicleModels[3] = "AMG GT";
		vehicleModels[5] = "S3";
		vehicleModels[7] = "Accord";
		vehicleModels[9] = "Accord";

		int[] vehicleYears = new int[10];
		vehicleYears[1] = 2016;
		vehicleYears[3] = 2015;
		vehicleYears[5] = 2018;
		vehicleYears[7] = 2003;
		vehicleYears[9] = 2008;

		double[] vehiclePrices = new double[10];
		vehiclePrices[1] = 13999.49;
		vehiclePrices[3] = 23499.25;
		vehiclePrices[5] = 21999.00;
		vehiclePrices[7] = 10000.50;
		vehiclePrices[9] = 5000.00;

		for (int i = 0; i < vehicleCount; i++) {
			if (i%2 == 0) {
				vehicleList[i] = new Vehicle();
			} else {
				vehicleList[i] = new Vehicle(vehicleMakes[i], vehicleModels[i], vehicleYears[i], vehiclePrices[i]);
			}	
		}

		vehicleList[0].setMake("Tesla");
		vehicleList[0].setModel("Model 3");
		vehicleList[0].setYear(2020);
		vehicleList[0].setPrice(45000.00);

		vehicleList[2].setMake("Honda");
		vehicleList[2].setModel("Civic");
		vehicleList[2].setYear(2001);
		vehicleList[2].setPrice(8999.99);

		vehicleList[4].setMake("Subaru");
		vehicleList[4].setModel("Outback");
		vehicleList[4].setYear(2012);
		vehicleList[4].setPrice(12000.50);

		vehicleList[6].setMake("Mercedes-Benz");
		vehicleList[6].setModel("S-Class");
		vehicleList[6].setYear(2020);
		vehicleList[6].setPrice(39999.99);

		vehicleList[8].setMake("Toyota");
		vehicleList[8].setModel("Camry");
		vehicleList[8].setYear(2016);
		vehicleList[8].setPrice(14999.99);
		
		for (int i = 0; i < vehicleList.length - 1; i++) {
			for (int j = 0; j < vehicleList.length - i - 1; j++) {
				if (vehicleList[i] == null || vehicleList[j] == null);
				else if (vehicleList[i].getYear() < vehicleList[j].getYear()) {
					Vehicle temp = vehicleList[i];
					vehicleList[i] = vehicleList[j];
					vehicleList[j] = temp;
				}
			}
		}
	}

	public void run() {
		System.out.println("Welcome to " + DEALERSHIP_BRAND + " @ " + location + "\nLoading vehicles from Database. Please wait ..." + "\nDone.");
		while (active) {
			System.out.println("\n\tMENU\n1. View vehicle inventory\n2. Search by make and model\n3. Search by price\n4. Search similar vehicles\n5. Quit\n");
			System.out.print("Enter your choice: ");
			String input = "";
			try {
				input = sc.next();
			} catch (Exception e) {
				System.out.println("Invalid input.");
			}
			switch (input) {
				case "1" :
					System.out.println("--------------------------------------------\n|           VEHICLE INVENTORY       |\n--------------------------------------------\n");
					System.out.println("MAKE & MODEL                  YEAR      PRICE\n----------------------------------------------------");
					viewInventory();
					break;
				case "2" : 
					searchMakeModel();
					break;
				case "3" :
					searchPriceRange();
					break;
				case "4" :
					searchSimilarVehicles();
					break;
				case "5" :
					quit();
					break;
				default :
					System.out.println("Invalid input.");
					break;
			}
		}
		sc.close();
	}
}