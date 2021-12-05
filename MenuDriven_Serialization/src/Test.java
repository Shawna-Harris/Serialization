//Aauthor Lashawna Harris Due 12=05-21   This program will stores data using Java Serialization feature and uses menu driven to 
// display and retrieve data stored.


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.EOFException;
import java.io.FileInputStream;
import java.util.*;
import java.time.LocalDate;
import java.util.ArrayList;


class Person implements Serializable{
private String name, phoneNumber, email;
LocalDate dob;

/**
 * @param name
 * @param phoneNumber
 * @param dob
 * @param email
 */
public Person(String name, String phoneNumber, LocalDate dob, String email) {
	this.name = name;
	this.phoneNumber = phoneNumber;
	this.dob = dob;
	this.email = email;
}
@Override
public String toString() {
	return "\t\t"+ name+ "\t\t"+ phoneNumber+"\t\t"+ dob+"\t\t"+ email;
}

}//end class Person

public class Test {
	
	//execute program
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		
			Scanner kb = new Scanner(System.in);
			
			//store user choice
			int userChoice = 0;
			
			ArrayList<Person> persons;
			Person person;
			try {
				ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream("Persons.bin"));

				do {
					//display choices to choose from 
					displayMainMenu();
					userChoice = kb.nextInt();
		
					//create switch statement to control program function based on user choice
					switch(userChoice) {
					
					case 1:
						objOut.writeObject(inputPerson(kb));
						System.out.println("A person added successfully.");
						break;
					case 2:
						persons = readFile();
						displayPersons(persons);
						System.out.println("Total objects read: " + persons.size());
						break;
					
					case 3:
						persons = readFile();
						displayPersons(persons);
						System.out.print("Choose an object to delete: ");
						person = persons.remove(kb.nextInt()-1);
						objOut = new ObjectOutputStream(new FileOutputStream("Persons.bin")); //clear the file
						for (Person p : persons) 
						{
							objOut.writeObject(p);
						}
						System.out.println("Successfully removed." + person.toString());
						break;
					
					case 4:
						persons = readFile();
						displayPersons(persons);
						System.out.print("Choose an object to edit: ");
						int objectNo = kb.nextInt();
						System.out.println("Please enter updated information.");
						persons.set(objectNo-1, inputPerson(kb));
						objOut = new ObjectOutputStream(new FileOutputStream("Persons.bin")); //clear the file
						for (Person p : persons) 
						{
							objOut.writeObject(p);
						}
						System.out.println("Successfully updated.");
						break;
					
					case 5:
						System.out.println("Thank you for using the program!");
						break;
					default:
						System.out.println("Invalid input. ");
			            
						}
							} while (userChoice != 5);
							objOut.close();
						} catch (IOException e) 
						{
							e.printStackTrace();
						}
						kb.close();
				}
	

	//display menu selection
	public static void displayMainMenu() {
		
		System.out.println("|===========MENU SELECTION==========================|" +
							"\n|  1 - ADD DATA TO FILE                           " +
		                    "\n|  2 - RETRIEVE, THEN DISPLAY DATA                " +
							"\n|  3 - DELETE DATA                                " +
		                    "\n|  4 - UPDATE DATA                                " +
							"\n|  5 - EXIT                                       " +
		                    "\n|  ENTER A CHOICE:                                " +
							"\n|===================================================|");
	}
	
		
	/**
	 * Return a person object. It creates person object through user input of person attributes.
	 * @param Scanner sc
	 * @return Person person
	 */
	public static Person inputPerson(Scanner sc) 
	{
		String name, phone, dob, email;

		System.out.print("Enter name: ");
		name = sc.next();
		sc.nextLine();
		System.out.println("Enter phone number (xxx-xxx-xxxx): ");
		phone = sc.next();
		sc.nextLine();
		System.out.println("Enter date of birth (yyyy-mm-dd): ");
		dob = sc.next();
		sc.nextLine();
		System.out.print("Enter email address: ");
		email = sc.next();
		Person person = new Person(name, phone, LocalDate.parse(dob), email);

		return person;
	}

	/**
	 * Deserialization
	 * Return person objects read from file in the form of ArrayList.
	 * @return ArrayList<Person> 
	 */
	public static ArrayList<Person> readFile() 
	{
		ArrayList<Person> persons = new ArrayList<Person>();
		try {
			ObjectInputStream objIn = new ObjectInputStream(new FileInputStream("Persons.bin"));
			boolean flagEOF = true;
			while (flagEOF) 
			{
				try {
					persons.add((Person) objIn.readObject());
				} catch (EOFException ex) 
				{
					flagEOF = false;
				}
			}
			objIn.close();
		} catch (IOException e) 
		{
			System.out.println("Nothing to read.");
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return persons;
	}

	/**
	 * Display given ArrayList of person objects.
	 * @param ArrayList<Person> persons
	 */
	private static void displayPersons(ArrayList<Person> persons) 
	{
		int count = 0;
		for (Person p : persons) 
		{
			count++;
			System.out.println(count + "-" + p.toString());
		}
	}
			
}//ends Test class


