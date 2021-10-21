//-------------------------------------------------------------------------------------------------------------------------

//  File name:  Vehicle.java

//  Program purpose: This program defines the vehicle object used by the car dealer

//  Revision history:

//   Date                  Programmer          Foothill ID

//   07/08/20              Russell Chien       20393765                

// --------------------------------------------------------------------------------------------------------------------------------

public class Vehicle {
	private String make, model;
	private int year;
	private double price;

	public Vehicle() {
		make = "MFG";
		model = "MOD";
		year = 1970;
		price = 0.0;
	}

	public Vehicle(String make, String model, int year, double price) {
		this.make = make;
		this.model = model;
		this.year = year;
		this.price = price;
	}

	public String getMake() { return make; }

	public void setMake(String make) { this.make = make; }

	public String getModel() { return model; }

	public void setModel(String model) { this.model = model; }

	public int getYear() { return year; }

	public void setYear(int year) { this.year = year; }

	public double getPrice() { return price; }

	public void setPrice(double price) { this.price = price; }

	public String toString() {
		String vehicleInfo = make + " " + model + ";" + year + ";$" + price;
		return vehicleInfo;
	}

	public boolean equals(Object o) {
		Vehicle v = (Vehicle) o;
		return this.make.equalsIgnoreCase(v.make) && this.model.equalsIgnoreCase(v.model) && this.year == v.year;
	}

}