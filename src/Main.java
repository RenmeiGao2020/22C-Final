
/**
 * main.java
 * Just for Testing
 * CIS 22C, Final Project
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private final int NUM_COSETICS = 25;
	Hash<Cosmetic> ht = new Hash<>(NUM_COSETICS * 2);
	BST<Cosmetic> bst1 = new BST<>();
	BST<Cosmetic> bst2 = new BST<>();
	Hash<user> userHash = new Hash<>(100);
	Hash<user> managerHash = new Hash<>(100);

	public static void main(String[] args) throws IOException {
		Main C = new Main();

		File file = new File("Cosmetics.txt");
		Scanner input = new Scanner(file);
		C.populateData(input);
		file = new File("user.txt");
		input = new Scanner(file);
		C.populateUser(input);
		file = new File("manager.txt");
		input = new Scanner(file);
		C.populateManager(input);

		input = new Scanner(System.in);
		String choice = "";
		while(!choice.equalsIgnoreCase("1")) {
			int id = C.login();
			if(id==2) {
				C.managerInterface();
				System.out.print("Will go back to login page or pass 1 to exit");
				choice = input.nextLine();
			}
			else if(id == 1) {
				C.userInterface();
				System.out.print("Will go back to login page or pass 1 to exit");
				choice = input.nextLine();
			}
		}
		System.out.println("Welcome next time Good Bye!");
		C.wirteToFile();
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
		int occNum = 0;
		ArrayList<String> occ;
		Cosmetic c;
		while (input.hasNextLine()) {

			category = input.nextLine();
			System.out.println(category);
			brand = input.nextLine();
			System.out.println(brand);
			name = input.nextLine();
			System.out.println(name);
			price = input.nextDouble();
			System.out.println(price);
			input.nextLine();
			color = input.nextLine();
			System.out.println(color);
			occNum = input.nextInt();
			System.out.println(occNum);
			input.nextLine();
			occ = new ArrayList<>();
			for(int i=0;i<occNum;i++) {
				occ.add(input.nextLine());
			}
			System.out.println(occ);
			review = input.nextLine();
			System.out.println(review);
			link = input.nextLine();
			System.out.println(link);
			if(input.hasNextLine()) {
				input.nextLine();
			}
			
			
			c = new Cosmetic(category, brand, name, price, color, link, occ, review);
			ht.insert(c);
			bst1.insert(c);
			bst2.insertByPrice(c);
		}
	}

	public void populateUser(Scanner input) {
		user u;

		while (input.hasNextLine()) {
			String username = input.nextLine();
			String password = input.nextLine();
			if(input.hasNextLine()) {
				input.nextLine();
			}
			
			u = new user(username, password);
			userHash.insert(u);
		}
		System.out.println("UserHash:\n"+userHash);

	}

	public void populateManager(Scanner input) {
		user u;

		while (input.hasNextLine()) {
			String username = input.nextLine();
			String password = input.nextLine();
			if(input.hasNextLine()) {
				input.nextLine();
			}
			u = new user(username, password);
			managerHash.insert(u);
		}
		System.out.println("ManagerHash:\n"+managerHash);

	}

	public int checkuser(user u) {
		if (userHash.check(u)) {
			return 1;
		} else if (managerHash.check(u)) {
			return 2;
		} else {
			return -1;
		}
	}

	public void managerInterface() {
		Scanner input = new Scanner(System.in);
		String chooice ="";
		
		while (!chooice.equals("5")) {
			System.out.println(
					"\n1.Display product" + "\n2.Add product" + "\n3.delate product" + "\n4.Edit product" + "\n5.Exit");
			System.out.print("\nEnter you chooise:");
			chooice = input.nextLine();
			if (chooice.equals("1")) {
				displaying(input);
			} else if (chooice.equals("2")) {
				adding(input);
			} else if (chooice.equals("3")) {
				delate(input);
			} else if (chooice.equals("4")) {
				editing(input);
			} else if(chooice.equals("5")){
				System.out.println("Tank you for chooice our program!");
			}
			else {
				System.out.println("\nInvalid Selection!");
			}
		}
	}

	public void userInterface() {
		Scanner input = new Scanner(System.in);
		String chooice = "";
		System.out.println("\nWelcome to Cosmetics Deal Space Station!\n");
		while (!chooice.equals("3")) {
			System.out.println(
					"\n1.Display product" + "\n2.SmartSearch"+ "\n3.Exit");
			System.out.print("\nEnter you chooise:");
			chooice = input.nextLine();
			if(chooice.equals("1")) {
				displaying(input);
			}
			else if(chooice.equals("2")) {
				userSmartSearchInterface(input);
			}
			else {
				System.out.println("Wrong input! Only digit 1 or 2");
			}
		}
		

	}
	
	public void userSmartSearchInterface(Scanner input) {
		String chooice ="";
		while(!chooice.equalsIgnoreCase("x")) {
			System.out.println("Welcome to our Smart Search Program\n"+"What are you looking for?\r\n" + 
					"You can enter category + brand or category + occasion\n"+
					"It support multiple keywords search and match\n"+
					"keyword split by a \",\"\n"+
					"X for Exit");
			System.out.print("\nEnter you chooise:");
			chooice = input.nextLine();
			String [] chooiceArray = chooice.split(" ");
			BST<Cosmetic> ss = bst1;
			for(int i=0; i<chooiceArray.length;i++) {
				ss= ss.CosmeticContain(chooiceArray[i]);
			}
			if(ss.isEmpty()) {
				System.out.println("Sorry, The product you want is not in our database");
			}
			ss.inOrderPrint();
			
			System.out.print("\nDo you want another search? or x for exit");
		}
	}

	public int login() {
		Scanner input = new Scanner(System.in);
		int pass =-1;
		while(pass<0) {
			System.out.println("\nWelcome to Cosmetics Deal Space Station!");
			System.out.println("\nlogin");
			System.out.print("\nPls enter your user name:");
			String username = input.nextLine();
			System.out.print("Pls enter your password:");
			String passwd = input.nextLine();
			user u = new user(username, passwd);
			pass = checkuser(u);
			System.out.println(pass);
		}
		return pass;
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
		input.nextLine();
		System.out.print("Enter the color");
		String color = input.nextLine();
		ArrayList<String> occ = new ArrayList<>();
		Cosmetic add = new Cosmetic(category, brand, name, price, color, link, occ, review);
		bst1.insert(add);
		bst2.insert(add);
		ht.insert(add);
		System.out.println("\n" + brand + "\'s " + name + " was added!");

	}

	public void displaying(Scanner input) {
		System.out.print("\n" + "Please select one of the following options:\n" + "\nS. Sorted by brands\n"
				+ "P. Sorted by price\n" + "U. Unsorted\n");
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
		String brand = "";
		String name = "";

		System.out.println("\nRemoving a Cosmetic!\n");
		System.out.print("Enter the brand: ");
		brand = input.nextLine();
		System.out.print("Enter the name: ");
		name = input.nextLine();
		Cosmetic remove = new Cosmetic(brand, name);
		if (bst1.search(remove) == true) {
			bst1.remove(remove);
			bst2.remove(remove);
			ht.remove(remove);
			System.out.println("\n" + brand + "\'s " + name + " was removed!");
		} else {
			System.out.println("\nI cannot find " + brand + "\'s " + name + " in the database.");

		}
	}

	public void editing(Scanner input) {
		String category = "";
		String brand = "";
		String name = "";
		double price = 0.0;
		String color = "";
		String link = "";
		String review = "";
		String chooice = "";

		System.out.println("\nEditing for a Cosmetic!\n");
		System.out.print("Enter the brand: ");
		brand = input.nextLine();
		System.out.print("Enter the name: ");
		name = input.nextLine();
		Cosmetic edit = new Cosmetic(brand, name);
		Cosmetic c = bst1.searchAndGet(edit);
		if (c != null) {
			bst1.remove(c);
			bst2.remove(c);
			ht.remove(c);
			System.out.println(c);
			
			while (!chooice.equalsIgnoreCase("x")) {
				System.out.print("Which part want edit(Example:name;x to Exit):");
				chooice = input.nextLine();
				if (chooice.equalsIgnoreCase("category")) {
					System.out.print("Enter new category:");
					category = input.nextLine();
					System.out.print(
							"Confirm change category from " + c.getCategory() + " to " + category + ". (yes or no)");
					chooice = input.nextLine();
					if (chooice.equalsIgnoreCase("yes")) {
						c.setCategory(category);
					}
				} else if (chooice.equalsIgnoreCase("brand")) {
					System.out.print("Enter new brand:");
					brand = input.nextLine();
					System.out.print("Confirm change brand from " + c.getBrand() + " to " + brand + ". (yes or no)");
					chooice = input.nextLine();
					if (chooice.equalsIgnoreCase("yes")) {
						c.setBrand(brand);
					}
				} else if (chooice.equalsIgnoreCase("name")) {
					System.out.print("Enter new name:");
					name = input.nextLine();
					System.out.print("Confirm change name from " + c.getName() + " to " + name + ". (yes or no)");
					chooice = input.nextLine();
					if (chooice.equalsIgnoreCase("yes")) {
						c.setName(name);
					}
				} else if (chooice.equalsIgnoreCase("price")) {
					System.out.print("Enter new price:");
					price = input.nextDouble();
					input.nextLine();
					System.out.print("Confirm change price from " + c.getPrice() + " to " + price + ". (yes or no)");
					chooice = input.nextLine();
					if (chooice.equalsIgnoreCase("yes")) {
						c.setPrice(price);
					}
				} else if (chooice.equalsIgnoreCase("color")) {
					System.out.print("Enter new color:");
					color = input.nextLine();
					System.out.print("Confirm change color from " + c.getColor() + " to " + color + ". (yes or no)");
					color = input.nextLine();
					if (chooice.equalsIgnoreCase("yes")) {
						c.setColor(color);
					}
				} else if (chooice.equalsIgnoreCase("link")) {
					System.out.print("Enter new link:");
					link = input.nextLine();
					System.out.print("Confirm change link from " + c.getLink() + "\nto\n" + link + ". (yes or no)");
					link = input.nextLine();
					if (chooice.equalsIgnoreCase("yes")) {
						c.setLink(link);
					}
				} else if (chooice.equalsIgnoreCase("review")) {
					System.out.print("Enter new link:");
					review = input.nextLine();
					System.out
							.print("Confirm change review from " + c.getReview() + "\nto\n" + review + ". (yes or no)");
					review = input.nextLine();
					if (chooice.equalsIgnoreCase("yes")) {
						c.setReview(review);
					}
				} else {
					System.out.println("Enter Error Pls enter again or x to exit");
				}
			}
			bst1.insert(c);
			bst2.insert(c);
			ht.insert(c);
		} else {
			System.out.println("\n" + brand + "\'s " + name + " is not in the database.");
		}

	}

	public void delate(Scanner input) {
		String brand = "";
		String name = "";

		System.out.println("\nSearching for a Cosmetic!\n");
		System.out.print("Enter the brand: ");
		brand = input.nextLine();
		System.out.print("Enter the name: ");
		name = input.nextLine();
		Cosmetic search = new Cosmetic(brand, name);
		if (bst1.search(search) == false) {
			System.out.println("\n" + brand + "\'s " + name + " is not in the database.");

		} else {
			bst1.remove(search);
			bst2.remove(search);
			ht.remove(search);
			System.out.println("\n" + brand + "\'s " + name + " has removed from the database.");
		}

	}

	public void searching(Scanner input) {
		String brand = "";
		String name = "";


		System.out.println("\nSearching for a Cosmetic!\n");
		System.out.print("Enter the brand: ");
		brand = input.nextLine();
		System.out.print("Enter the name: ");
		name = input.nextLine();
		Cosmetic search = new Cosmetic(brand, name);
		if (bst1.search(search) == false) {
			System.out.println("\n" + brand + "\'s " + name + " is not in the database.");

		} else {
			System.out.println("\n" + brand + "\'s " + name + " is in the database.");

		}
	}
	
	public void wirteToFile() {
		File file = new File("cosmetics.txt");
		PrintWriter p;
		try {
			p = new PrintWriter(file);
			bst1.writeToFile(p);
			file = new File("user.txt");
			p = new PrintWriter(file);
			userHash.writeToFile(p);
			file = new File("manager.txt");
			p = new PrintWriter(file);
			managerHash.writeToFile(p);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}