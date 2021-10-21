/* 
-------------------------------------------------------------------------------------------------------------------------

@version1.0 07-27-20

@author Russell Chien

File name: MLSListingsApp.java

Program purpose: This program is a simplified Multiple Listing Service of properties on sale with a GUI interface. 
				 It allows user to search for properties based on price range or type of property or to view all listings.

Revision history:

Date     Programmer    Change ID Description

07-27-20 Russell Chien 0001      Initial implementation

 --------------------------------------------------------------------------------------------------------------------------------
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;

public class MLSListingsApp {
	public static void main (String[] args) {
		PropertyList propertyList = new PropertyList();
		propertyList.initialize();
		EventQueue.invokeLater(new Runnable() { 
			public void run() {
				MLSListingsView mlsView = new MLSListingsView();
				mlsView.setProperties(propertyList);
				mlsView.setVisible(true);
			}
		});
	}
}

class MLSListingsView extends JFrame {
	private final int WIDTH = 800;
	private final int HEIGHT = 600;

	private PropertyList propertyList;

	private JTextArea listings;

	private JButton showAllButton;
	private JButton showSFHButton;
	private JButton showCondoButton;
	private JButton clearButton;

	private JComboBox<String> priceSearch; 
	private JButton searchButton;
	private JLabel searchProperty; 

	public MLSListingsView() {
		setTitle("MLSListings");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);

		JPanel searchPanel = new JPanel();
		add(searchPanel, BorderLayout.NORTH);
		
		searchProperty = new JLabel("Search Property:");
		searchPanel.add(searchProperty);

		String[] options = {"Under 400k", "400k - < 600k", "600k - < 800k", "800k - < 1M", "1M or more"};
		priceSearch = new JComboBox<String>(options);
		searchPanel.add(priceSearch);

		searchButton = new JButton("Go");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String option = (String) priceSearch.getSelectedItem();
				switch(option) {
					case "Under 400k" :
						listings.setText(propertyList.searchByPriceRange(0, 400000)); 
						break; 
					case "400k - < 600k" :
						listings.setText(propertyList.searchByPriceRange(400000, 599999));
						break; 
					case "600k - < 800k" :
						listings.setText(propertyList.searchByPriceRange(600000, 799999));
						break;
					case "800k - < 1M" :
						listings.setText(propertyList.searchByPriceRange(800000, 999999));
						break;
					case "1M or more" :
						listings.setText(propertyList.searchByPriceRange(1000000, Double.MAX_VALUE));
						break;
				}
			}
		});
		searchPanel.add(searchButton);

		JPanel displayPanel = new JPanel();
		add(displayPanel, BorderLayout.CENTER);

		listings = new JTextArea(20, 60);
		listings.setBorder(BorderFactory.createEtchedBorder());
		listings.setEditable(false);
		displayPanel.add(listings);
		
		JPanel actionPanel = new JPanel();
		add(actionPanel, BorderLayout.SOUTH);

		showAllButton = new JButton("Show All");
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listings.setText(propertyList.getAllProperties());
			}
		});
		actionPanel.add(showAllButton);

		showSFHButton = new JButton("Show SFH");
		showSFHButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listings.setText(propertyList.getSingleFamilyHouse());
			}
		});
		actionPanel.add(showSFHButton);

		showCondoButton = new JButton("Show Condo");
		showCondoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listings.setText(propertyList.getCondo());
			}
		});
		actionPanel.add(showCondoButton);

		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listings.setText("");
			}
		});
		actionPanel.add(clearButton);

		addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                 System.exit(0);
            }            
         });
	}

	public void setProperties(PropertyList propertyList) {
		this.propertyList = propertyList; 
	}
}

class Property {
	private String address;
	private double offeredPrice;
	private int yearBuilt;
	private Property next;

	public Property() {
		this.address = "None";
		this.offeredPrice = 0.0;
		this.yearBuilt = 1970;
		this.next = null;
	}

	public Property(String address, double offeredPrice, int yearBuilt) {
		this.address = address;
		this.offeredPrice = offeredPrice;
		this.yearBuilt = yearBuilt;
		this.next = null;
	}

	public String getAddress() { return address; } 

	public void setAddress(String address) { this.address = address; } 

	public double getOfferedPrice() { return offeredPrice; } 

	public void setOfferedPrice(double offeredPrice) { this.offeredPrice = offeredPrice; }

	public int getYearBuilt() { return yearBuilt; } 

	public void setYearBuilt(int yearBuilt) { this.yearBuilt = yearBuilt; }

	public Property next() { return next; }

	public void setNext(Property next) { this.next = next; }

	public String toString() {
		return String.format("%s\t$%.02f\t%d", address, offeredPrice, yearBuilt);
	}

	public boolean equals(Property other) {
		return address.equals(other.getAddress()) && yearBuilt == other.getYearBuilt();
	}
}

class SingleFamilyHouse extends Property {
	private int backyardArea; 

	public SingleFamilyHouse() {
		super();
		this.backyardArea = 0;
	}

	public SingleFamilyHouse(String address, double offeredPrice, int yearBuilt, int backyardArea) {
		super(address, offeredPrice, yearBuilt);
		this.backyardArea = backyardArea;
	}

	public int getBackyardArea() { return backyardArea; }

	public void setBackyardArea(int backyardArea) { this.backyardArea = backyardArea; }

	public String toString() {
		return super.toString() + "\t" + backyardArea + " (sq ft)";
	}

	public boolean equals(SingleFamilyHouse other) {
		return super.equals(other) && this.backyardArea == other.getBackyardArea();
	}
}

class Condo extends Property {
	private double hoaFee; 

	public Condo() {
		super();
		this.hoaFee = 175.00;
	}

	public Condo(String address, double offeredPrice, int yearBuilt, double hoaFee) {
		super(address, offeredPrice, yearBuilt);
		this.hoaFee = hoaFee;
	}

	public double getHoaFee() { return hoaFee; } 

	public void setHoaFee(double hoaFee) { this.hoaFee = hoaFee; }

	public String toString() {
		return super.toString() + String.format("\tHOA Fee: $%.02f", hoaFee);
	}

	public boolean equals(Condo other) {
		return super.equals(other) && this.hoaFee == other.getHoaFee();
	}
}

class PropertyList {
	private static final String INPUT_FILE_LOCATION = "/Users/Russell/Desktop/Code/Java/CS1B/assignment_3/properties.txt" ;

	private Property head;

	public PropertyList() {
		this.head = null;
	}

	private void insert(Property property) {
		property.setNext(head);
        head = property; 
	}

	public void initialize() {
		 Path inputFilePath = Paths.get(INPUT_FILE_LOCATION);          
         BufferedReader reader = null;
         String line = null;
         try {
            reader = Files.newBufferedReader(inputFilePath, StandardCharsets.US_ASCII);
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(";");
                    switch(tokens[0].toLowerCase()) {
                    	case "sfh" : 
                    		this.insert(new SingleFamilyHouse(tokens[1], Double.parseDouble(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4])));
                    		break;
                    	case "condo" :
                    		this.insert(new Condo(tokens[1], Double.parseDouble(tokens[2]), Integer.parseInt(tokens[3]), Double.parseDouble(tokens[4])));
                    		break;
                    	default : 
                    		this.insert(new Property(tokens[1], Double.parseDouble(tokens[2]), Integer.parseInt(tokens[3])));
                    		break;
                    }
                }
                reader.close();
        } catch (IOException e )  {  
            e.printStackTrace ();
        }
	}

	public String getAllProperties() {
		Property temp = head;
		String properties = "Address\t\t\tPrice\tYear\tOther Info\n";
        while (temp != null) {
            properties += temp + "\n";
            temp = temp.next();
        }
        return properties;
	}

	public String getSingleFamilyHouse() {
		Property temp = head;
		String properties = "Address\t\t\tPrice\tYear\tOther Info\n";
        while (temp != null) {
        	if (temp instanceof SingleFamilyHouse) { properties += temp + "\n"; }
            temp = temp.next();
        }
        return properties;
	}

	public String getCondo() {
		Property temp = head;
		String properties = "Address\t\t\tPrice\tYear\tOther Info\n";
        while (temp != null) {
        	if (temp instanceof Condo) { properties += temp + "\n"; }
            temp = temp.next();
        }
        return properties;
	}

	public String searchByPriceRange(double min, double max) {
		Property temp = head;
		String properties = "Address\t\t\tPrice\tYear\tOther Info\n";
        while (temp != null) {
        	if (temp.getOfferedPrice() >= min && temp.getOfferedPrice() <= max) { properties += temp + "\n"; }
            temp = temp.next();
        }
        return properties;
	}
}