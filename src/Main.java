/**
 * main.java
 * Just for Testing
 * CIS 22C, Final Project
 */

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {
	private final int NUM_COSETICS = 25;
	Hash<Cosmetic> ht = new Hash<>(NUM_COSETICS * 2);
	BST<Cosmetic> bst1 = new BST<>();
	BST<Cosmetic> bst2 = new BST<>();

	public static void main(String[] args) throws IOException {
		Main C = new Main();

		File file = new File("cosmetics.txt");
		Scanner input = new Scanner(file);
		C.populateData(input);

		input = new Scanner(System.in);
		String choice;
		System.out.println("Welcome to Cosmetics Deal Space Station!");
		System.out.println("\nPlease select from one of the following options:\n" + "\n" + "A. Add a Cosmetic\n"
				+ "D. Display all Cosmetics\n" + "R. Remove a Cosmetic\n" + "S. Search for a Cosmetic\n" + "X. Exit");
		System.out.print("\nEnter your choice: ");
		choice = input.nextLine();
		while (!choice.equalsIgnoreCase("X")) {

			if (choice.equalsIgnoreCase("A")) {
				C.adding(input);

			} else if (choice.equalsIgnoreCase("D")) {
				C.displaying(input);

			} else if (choice.equalsIgnoreCase("R")) {
				C.removing(input);

			} else if (choice.equalsIgnoreCase("S")) {
				C.searching(input);

			} else {
				System.out.println("\nInvalid Selection!");
			}

			System.out.println("\nPlease select from one of the following options:\n" + "\n" + "A. Add a Cosmetic\n"
					+ "D. Display all Cosmetics\n" + "R. Remove a Cosmetic\n" + "S. Search for a Cosmetic\n"
					+ "X. Exit");
			System.out.print("\nEnter your choice: ");
			choice = input.nextLine();
		}
		System.out.println("\nGoodbye!");
		input.close();
	}

	public void populateData(Scanner input) throws IOException {
		String category = "";
		String brand = "";
		String name = "";
		double price = 0.0;
		String color = "";
		String link = "";
		String review = "";
		Cosmetic c;
		while (input.hasNextLine()) {

			category = input.nextLine();
			brand = input.nextLine();
			name = input.nextLine();
			price = input.nextDouble();
			input.nextLine();
			color = input.nextLine();

			c = new Cosmetic(category, brand, name, price, color,link,review);
			ht.insert(c);
			bst1.insert(c);
			bst2.insertByPrice(c);
		}
	}
	
	public Hash<user> populateUser(Scanner input)  {
		user u;
		Hash<user> h = new Hash<>(100);
		while(input.hasNextLine()) {
			String username = input.nextLine();
			String password = input.nextLine();
			input.nextLine();
			u = new user(username,password);
			h.insert(u);
		}
		return h;
	}
	
	

	public void adding(Scanner input) {
		String link = "";
		String review = "";
		System.out.println("\nAdding a cosmetic!\n");
		System.out.print("Enter the category: ");
		String category = input.nextLine();
		System.out.print("Enter the brand: ");
		String brand = input.nextLine();
		System.out.print("Enter the name: ");
		String name = input.nextLine();
		System.out.print("Enter the price: $");
		double price = input.nextDouble();
		System.out.print("Enter the color");
		String color = input.nextLine();
		Cosmetic add = new Cosmetic(category, brand, name, price, color,link, review);
		bst1.insert(add);
		ht.insert(add);
		System.out.println("\n" + brand + "\'s " + name + " was added!");

	}

	public void displaying(Scanner input) {
		System.out
				.print("\n" + "Please select one of the following options:\n" + "\nS. Sorted by brands\n" + "P. Sorted by price\n"+"U. Unsorted\n");
		System.out.print("\nEnter your choice: ");
		String choice = input.nextLine();
		System.out.println("\nDisplaying Cosmetics list:");
		if (choice.equalsIgnoreCase("S")) {
			bst1.inOrderPrint();
			System.out.println();
		}
		
		if (choice.equalsIgnoreCase("P")) {
			bst2.inOrderPrint();
		}
		if (choice.equalsIgnoreCase("U")) {
			System.out.println(ht);

		}
	}

	public void removing(Scanner input) {
		String category = "";
		String brand = "";
		String name = "";
		double price = 0.0;
		String color = "";
		String link = "";
		String review = "";

		System.out.println("\nRemoving a Cosmetic!\n");
		System.out.print("Enter the brand: ");
		brand = input.nextLine();
		System.out.print("Enter the name: ");
		name = input.nextLine();
		Cosmetic remove = new Cosmetic(category, brand, name, price, color,link, review);
		if (bst1.search(remove) == true) {
			bst1.remove(remove);
			ht.remove(remove);
			System.out.println("\n" + brand + "\'s " + name + " was removed!");
		} else {
			System.out.println("\nI cannot find " + brand + "\'s " + name + " in the database.");

		}
	}

	public void searching(Scanner input) {
		String category = "";
		String brand = "";
		String name = "";
		double price = 0.0;
		String color = "";
		String link = "";
		String review = "";

		System.out.println("\nSearching for a Cosmetic!\n");
		System.out.print("Enter the brand: ");
		brand = input.nextLine();
		System.out.print("Enter the name: ");
		name = input.nextLine();
		Cosmetic search = new Cosmetic(category, brand, name, price, color,link, review);
		if (bst1.search(search) == false) {
			System.out.println("\n" + brand + "\'s " + name + " is not in the database.");

		} else {
			System.out.println("\n" + brand + "\'s " + name + " is in the database.");

		}
	}
}