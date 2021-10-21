/* 
-------------------------------------------------------------------------------------------------------------------------

@version1.0 07-27-20

@author Russell Chien

File name: MLSListingsApp.java

Program purpose: This program is allows Customer Service representatives at a Wireless Phone Carrier 
				 to keep track of customers’ SmartPhone message usages/charges and messages erasing/deleting.
				 It can list all accounts and their messages, delete the first media message of each account,
				 and test if messages are equal to each other. 

Revision history:

Date     Programmer    Change ID Description

07-27-20 Russell Chien 0001      Initial implementation

 --------------------------------------------------------------------------------------------------------------------------------
*/

import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;

public class Main {
	public static void main (String[] args) {
		SmartCarrier smartCarrier = new SmartCarrier("Santa Clara");
		smartCarrier.init();
		smartCarrier.run();
	}
}

class Item {
	private int time; 
	private String from, to;
	private double charge;

	public Item() {
		this.time = 0;
		this.from = "";
		this.to = "";
		this.charge = 0.0;
	}

	public Item(int time, String from, String to, double charge) {
		this.time = time;
		this.from = from;
		this.to = to;
		this.charge = charge;
	}

	public int getTime() { return time; } 

	public void setTime(int time) { this.time = time; } 

	public String getFrom() { return from; } 

	public void setFrom(String from) { this.from = from; }

	public String getTo() { return to; } 

	public void setTo(String to) { this.to = to; }

	public double getCharge() { return charge; }

	public void setCharge(double charge) { this.charge = charge; }

	public String toString() {
		return String.format("Time: %d From: %s To: %s Charge: %.02f", time, from, to, charge); 
	}

	public boolean equals(Object other) {
		if (this == other) { return true; }
        if (other == null) { return false; } 
        if (!(other instanceof Item)) { return false; } 
        Item item = (Item) other;
        return time == item.getTime() && from.equals(item.getFrom()) && to.equals(item.getTo()); 
	}
}

class Message<T> extends Item {
	private T data; 

	public Message() {
		super();
		this.data = null;
	}

	public Message(int time, String from, String to, double charge, T data) {
		super(time, from, to, charge);
		this.data = data;
	}

	public T getMessage() { return data; } 

	public void setMessage(T data) { this.data = data; } 

	public String toString() {
		return super.toString() + " " + data;
	}

	public boolean equals(Object other) {
		Message<T> message = (Message<T>) other; 
		if (super.equals(message)) {
			if (message.getMessage() instanceof Text || message.getMessage() instanceof Media || message.getMessage() instanceof Voice) {
				return data.equals(message.getMessage()); 
			} 
		}
		return false; 
	}
}

interface Chargeable {
	public double charge();
} 

class Text extends Item implements Chargeable {
	private String content;

	public Text() { 
		content = ""; 
   	}

   	public Text(String content) { 
   		this.content = content; 
   	}

   	public String getText() { return content; } 

   	public void setText(String content) { this.content = content; }

   	public String toString() {
   		return "\tTEXT: " + content;
    }

    public boolean equals(Object other) {
    	if (this == other) { return true; }
        if (other == null) { return false; } 
        if (!(other instanceof Text)) { return false; } 
    	Text text = (Text) other;
    	return content.equals(text.getText()); 
    }

    public double charge() {
    	return 20;
    }
}

class Media extends Item implements Chargeable {
	private double size;
	private String format; 

	public Media() {
		this.size = 0;
		this.format = "";
	}

	public Media(double size, String format) { 
		this.size = size;
		this.format = format; 
	}

	public double getSize() { return size; }

	public void setSize(double size) { this.size = size; } 

	public String getFormat() { return format; }

	public void setFormat(String format) { this.format = format; }

	public String toString() {
		return new String ("\tMEDIA: Size: " + size + " MB, Format: " + format);
	}

	public boolean equals(Object other) {
		if (this == other) { return true; }
        if (other == null) { return false; } 
        if (!(other instanceof Media)) { return false; } 
		Media media = (Media) other;
		return size == media.getSize() && format.equals(media.getFormat());
	}

	public double charge() {
		return 50 * size; 
	}
}

class Voice extends Item implements Chargeable {
	private int duration; 
	private String format;

	public Voice() {
		this.duration = 0;
		this.format = "";
	}

	public Voice(int duration, String format) {
		this.duration = duration;
		this.format = format;
	}

	public int getDuration() { return duration; }

	public void setDuration(int duration) { this.duration = duration; } 

	public String getFormat() { return format; } 

	public void setFormat(String format) { this.format = format; }

	public String toString() { 
		return new String ("\tVOICE: Duration: " + duration + " (sec), Format: " + format);
	}

	public boolean equals(Object other) {
		if (this == other) { return true; }
        if (other == null) { return false; } 
        if (!(other instanceof Voice)) { return false; } 
		Voice voice = (Voice) other;
		return duration == voice.getDuration() && format.equals(voice.getFormat());
	}

	public double charge() {
		return 10 * duration;
	}
}

class SmartCarrier {
	private static final String INPUT_FILE_LOCATION = "/Users/Russell/Desktop/Code/Java/CS1B/assignment_4/messages.txt" ;

	private TreeMap<String, ArrayList<Message<? extends Item>>> messages;
	private String location;

	public SmartCarrier() {
		this.messages = null;
		this.location = "";
	}

	public SmartCarrier(String location) {
		this.messages = new TreeMap<String, ArrayList<Message<? extends Item>>>();
		this.location = location;
	}

	public void init() {
		Path inputFilePath = Paths.get(INPUT_FILE_LOCATION);          
        BufferedReader reader = null;
        String line = null;
        try {
        	reader = Files.newBufferedReader(inputFilePath, StandardCharsets.US_ASCII);
            	while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    switch(tokens[0].toLowerCase()) {
                    	case "t" : 
                    		Message<Text> textMessage = new Message<Text>(Integer.parseInt(tokens[1]), tokens[2], tokens[3], Double.parseDouble(tokens[5]), new Text(tokens[4])); 
                    		if (messages.containsKey(textMessage.getFrom())) { 
                    			messages.get(textMessage.getFrom()).add(textMessage);
                    		} else {
                    			messages.put(textMessage.getFrom(), new ArrayList<Message<? extends Item>>());
                    			messages.get(textMessage.getFrom()).add(textMessage);
                    		}
                    		break;
                    	case "v" :
                    		Message<Voice> voiceMessage = new Message<Voice>(Integer.parseInt(tokens[1]), tokens[2], tokens[3], Double.parseDouble(tokens[6]), new Voice(Integer.parseInt(tokens[4]), tokens[5]));
                    		if (messages.containsKey(voiceMessage.getFrom())) { 
                    			messages.get(voiceMessage.getFrom()).add(voiceMessage);
                    		} else {
                    			messages.put(voiceMessage.getFrom(), new ArrayList<Message<? extends Item>>());
                    			messages.get(voiceMessage.getFrom()).add(voiceMessage);
                    		}
                        	break;
                        case "m":
                        	Message<Media> mediaMessage = new Message<Media>(Integer.parseInt(tokens[1]), tokens[2], tokens[3], Double.parseDouble(tokens[6]), new Media(Double.parseDouble(tokens[4]), tokens[5]));
                        	if (messages.containsKey(mediaMessage.getFrom())) { 
                    			messages.get(mediaMessage.getFrom()).add(mediaMessage);
                    		} else {
                    			messages.put(mediaMessage.getFrom(), new ArrayList<Message<? extends Item>>());
                    			messages.get(mediaMessage.getFrom()).add(mediaMessage);
                    		}
                        	break;
                    	default : 
                    		break;
                    }
                }
                reader.close();
        } catch (IOException e )  {  
            e.printStackTrace ();
        }
	}

	public void run() {
		boolean running = true;
		do {
			Scanner sc = new Scanner(System.in);
			System.out.println("\t\tFOOTHILL WIRELESS at " + location + "\nMESSAGE UTILIZATION AND ACCOUNT ADMIN");
			System.out.println("1. List all accounts\n2. Erase the first media message\n3. equals Test\n4. Quit");
			System.out.println("Enter a choice (1-4): ");
			switch(sc.next()) {
				case "1":
					this.listAllAccounts();
					break;
				case "2":
					this.eraseFirstMediaMessage();
					break;
				case "3":
					Message<? extends Item> message1 = null;
					Message<? extends Item> message2 = null; 
					System.out.println("FIRST MESSAGE INFO");
					sc.nextLine();
					System.out.println("Enter message type: ");
					String type = "";
					try {
						type = sc.nextLine();
					} catch (Exception e) {
						System.out.println("Invalid input.");
						break;
					}
					System.out.println("Enter number of sender: ");
					String from = "";
					try {
						from = sc.nextLine();
					} catch (Exception e) {
						System.out.println("Invalid input.");
						break;
					}
					System.out.println("Enter number of recipient: ");
					String to = "";
					try {
						to = sc.nextLine();
					} catch (Exception e) {
						System.out.println("Invalid input.");
						break;
					}
					System.out.println("Enter in the time: ");
					int time = 0;
					try {
						time = sc.nextInt();
					} catch (Exception e) {
						System.out.println("Invalid input.");
						break;
					}
					if (type.toLowerCase().equals("text")) {
						sc.nextLine();
						System.out.println("Enter in the text content: ");
						String content = "";
						try {
							content = sc.nextLine();
						} catch (Exception e) {
							System.out.println("Invalid input.");
							break;
						}
						Text text = new Text(content);
						Message<Text> textMessage = new Message<Text>(time, from, to, 0, new Text(content));
						message1 = textMessage;
					} else if (type.toLowerCase().equals("voice")) {
						sc.nextLine();
						System.out.println("Enter in the duration: ");
						int duration = 0;
						try {
							duration = sc.nextInt();
						} catch (Exception e) {
							System.out.println("Invalid input.");
							break;
						}
						System.out.println("Enter in the format: ");
						String format = "";
						try {
							format = sc.nextLine();
						} catch (Exception e) {
							System.out.println("Invalid input.");
							break;
						}
						Message<Voice> voiceMessage = new Message<Voice>(time, from, to, 0, new Voice(duration, format));
						message1 = voiceMessage;
					} else if (type.toLowerCase().equals("media")) {
						sc.nextLine();
						System.out.println("Enter in the size: ");
						double size = 0; 
						try {
							size = sc.nextDouble();
						} catch (Exception e) {
							System.out.println("Invalid input.");
							break;
						}
						System.out.println("Enter in the format: ");
						String format = "";
						try {
							format = sc.nextLine();
						} catch (Exception e) {
							System.out.println("Invalid input.");
							break;
						}
						Message<Media> mediaMessage = new Message<Media>(time, from, to, 0, new Media(size, format));
						message1 = mediaMessage;
					}
					System.out.println("SECOND MESSAGE INFO");
					System.out.println("Enter message type: ");
					try {
						type = sc.nextLine();
					} catch (Exception e) {
						System.out.println("Invalid input.");
						break;
					}
					System.out.println("Enter number of sender: ");
					try {
						from = sc.nextLine();
					} catch (Exception e) {
						System.out.println("Invalid input.");
						break;
					}
					System.out.println("Enter number of recipient: ");
					try { 
						to = sc.nextLine();
					} catch (Exception e) {
						System.out.println("Invalid input.");
						break;
					}
					System.out.println("Enter in the time: ");
					try { 
						time = sc.nextInt();
					} catch (Exception e) {
						System.out.println("Invalid input.");
						break;
					}
					if (type.toLowerCase().equals("text")) {
						sc.nextLine();
						System.out.println("Enter in the text content: ");
						String content = "";
						try { 
							content = sc.nextLine();
						} catch (Exception e) {
							System.out.println("Invalid input.");
							break;
						}
						Text text = new Text(content);
						Message<Text> textMessage = new Message<Text>(time, from, to, 0, new Text(content));
						message2 = textMessage;
					} else if (type.toLowerCase().equals("voice")) {
						sc.nextLine();
						System.out.println("Enter in the duration: ");
						int duration = 0;
						try {
							duration = sc.nextInt();
						} catch (Exception e) {
							System.out.println("Invalid input.");
							break;
						}
						System.out.println("Enter in the format: ");
						String format = "";
						try {
							format = sc.nextLine();
						} catch (Exception e) {
							System.out.println("Invalid input.");
							break;
						}
						Message<Voice> voiceMessage = new Message<Voice>(time, from, to, 0, new Voice(duration, format));
						message2 = voiceMessage;
					} else if (type.toLowerCase().equals("media")) {
						sc.nextLine();
						System.out.println("Enter in the size: ");
						double size = 0;
						try {
							size = sc.nextDouble();
						} catch (Exception e) {
							System.out.println("Invalid input.");
							break;
						}
						System.out.println("Enter in the format: ");
						String format = "";
						try {
							format = sc.nextLine();
						} catch (Exception e) {
							System.out.println("Invalid input.");
							break;
						}
						Message<Media> mediaMessage = new Message<Media>(time, from, to, 0, new Media(size, format));
						message2 = mediaMessage;
					}
					if (message1.equals(message2)) {
						System.out.println("YES");
					} else {
						System.out.println("NO");
					}
					break;
				case "4": 
					running = false;
					break;
				default:
					System.out.println("Invalid option.");
					break;
			}
		} while(running);
	}

	private void listAllAccounts() {
		double charge = 0;
		for (Map.Entry<String, ArrayList<Message<? extends Item>>> entry : messages.entrySet()) {
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Account: " + entry.getKey());
			ListIterator<Message<? extends Item>> iterator = entry.getValue().listIterator();
			Message m = null;
			while (iterator.hasNext()) {
				m = iterator.next();
				charge += m.getCharge();
				System.out.println(m);
			}
			String totalCharges = String.format("Total Charges: %.02f", charge);
			System.out.println(totalCharges);
			charge = 0;
		}

	}

	private void eraseFirstMediaMessage() {
		for (Map.Entry<String, ArrayList<Message<? extends Item>>> entry : messages.entrySet()) {
			this.eraseHelper(entry.getValue());
		}
	}

	private void eraseHelper(List<? extends Item> messages) {
		ListIterator<? extends Item> iterator = messages.listIterator();
		while (iterator.hasNext()) {
			if (((Message<? extends Item>) iterator.next()).getMessage() instanceof Media) {
				iterator.remove();
				break;
			}
		}
	}
}