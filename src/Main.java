
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
import java.util.Random;
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
		while (!choice.equalsIgnoreCase("1")) {
			String id = C.login(input);
			if (id.contains("Manager")) {
				C.managerInterface(id);
				System.out.print("\nPress any key going back to login page or pass 1 to exit: ");
				choice = input.nextLine();
			} else if (id.contains("User")||id.equals("Guest")) {
				C.userInterface(id);
				System.out.print("\nPress any key going back to login page or pass 1 to exit: ");
				choice = input.nextLine();
			}
		}
		System.out.println("\nWelcome next time Good Bye!");
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
			brand = input.nextLine();			
			name = input.nextLine();			
			price = input.nextDouble();			
			input.nextLine();
			color = input.nextLine();			
			occNum = input.nextInt();			
			input.nextLine();
			occ = new ArrayList<>();
			for (int i = 0; i < occNum; i++) {
				occ.add(input.nextLine().toLowerCase());
			}			
			review = input.nextLine();			
			link = input.nextLine();
			if (input.hasNextLine()) {
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
			if (input.hasNextLine()) {
				input.nextLine();
			}

			u = new user(username, password);
			userHash.insert(u);
		}

	}

	public void populateManager(Scanner input) {
		user u;

		while (input.hasNextLine()) {
			String username = input.nextLine();
			String password = input.nextLine();
			if (input.hasNextLine()) {
				input.nextLine();
			}
			u = new user(username, password);
			managerHash.insert(u);
		}

	}

	

	public void managerInterface(String id) {
		Scanner input = new Scanner(System.in);
		String chooice = "";
		System.out.println("Cosmetics Deal Space Station Management system lgoin as "+ id);

		while (!chooice.equals("6")) {
			System.out.println(
					"\n1.Display product" + "\n2.Add product" + "\n3.delate product" + "\n4.Edit product" + "\n5.Add manager"+"\n6. Logout");
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
			} else if(chooice.equals("5")) {
				user u = registeredInterface(input, 2);
				managerHash.insert(u);
				System.out.println("Manager "+u.getUserName()+" has Added");
			}
			
			else if (chooice.equals("6")) {
				System.out.println("Thank you for your work!");
			} else {
				System.out.println("\nInvalid Selection!\n");
			}
		}
	}

	public void userInterface(String id) {
		Scanner input = new Scanner(System.in);
		String chooice = "";
		System.out.println("\nWelcome "+id+" to Cosmetics Deal Space Station!\n");
		while (!chooice.equals("4")) {
			System.out.println("\n1.Display product" + "\n2.Search" + "\n3.Get PromoCode" +"\n4.Logout");
			System.out.print("\nEnter you chooise:");
			chooice = input.nextLine();
			if (chooice.equals("1")) {
				displaying(input);
			} else if (chooice.equals("2")) {
				searchInterface(input);
			} else if (chooice.equals("3")) {
				getPromoCode();
			} else if (chooice.equals("4")) {
				System.out.println("\nThank you for using our program!\n");
			} else {
				System.out.println("\nWrong input! Only digit 1, 2, 3or 4 to exit\n");
			}
		}

	}

	public void searchInterface(Scanner input) {
		String chooice = "";
		System.out.println("\nWelcome to Cosmetics Search");

		while (!chooice.equals("3")) {
			System.out.println("\n1.Search by brand and name\n2.Smart Search\n3.Exit");
			System.out.print("\nEnter you chooise:");
			chooice = input.nextLine();
			if (chooice.equals("1")) {
				searching(input);
			} else if (chooice.equals("2")) {
				userSmartSearchInterface(input);
			} else if (chooice.equals("3")) {
				
			} else {
				System.out.println("\nWrong input! Only digit 1, 2 or 3 to exit\n");
			}
		}
	}

	public void userSmartSearchInterface(Scanner input) {
		String chooice = "";
		while (!chooice.equalsIgnoreCase("x")) {
			System.out.println("Welcome to our Smart Search Program\n" + "What are you looking for?\r\n"
					+ "You can enter category + brand or category + occasion\n"
					+ "It support multiple keywords search and match\n" + "keyword split by a \",\"\n" + "X for Exit");
			System.out.print("\nEnter you chooise:");
			chooice = input.nextLine();
			if(!chooice.equalsIgnoreCase("x")) {
				String[] chooiceArray = chooice.split(",");
				BST<Cosmetic> ss = bst1;
				for (int i = 0; i < chooiceArray.length; i++) {
					ss = ss.Contain(chooiceArray[i]);
				}
				if (ss.isEmpty()) {
					System.out.println("\nSorry, The product you want is not in our database\n");
				}
				ss.inOrderPrint();

				System.out.print("\nDo you want another search? or x for exit: ");
				chooice = input.nextLine();
			}
			
		}
	}

	public String login(Scanner input) {
		int pass = -1;
		String chooice = "";
		while (!chooice.equals("1")&&!chooice.equals("2")&&!chooice.equals("3")) {
			System.out.println("\nWelcome to Cosmetics Deal Space Station!\n");
			System.out.println("\n1.Login\n2.Registered\n3.Continue as the guest");
			System.out.print("\nEnter your chooice: ");
			chooice = input.nextLine();
			if(chooice.equals("1")) {
				while(pass<0) {
					System.out.println("\nlogin");
					System.out.print("\nPls enter your user name:");
					String username = input.nextLine();
					System.out.print("Pls enter your password:");
					String passwd = input.nextLine();
					user u = new user(username, passwd);
					pass = checkuser(u);
					if(pass == 1) {
						return "User "+u.getUserName();
					}
					else if(pass == 2) {
						return "Manager "+u.getUserName();
					}
					else {
						System.out.print("Username or Password is not correct,Please enter any key to try again or enter \"r\" to regitered: ");
						String tryagain = input.nextLine();
						if(tryagain.equalsIgnoreCase("r")) {
							user u1 = registeredInterface(input, 1);
							userHash.insert(u1);
							pass = 1;
							return "User "+u1.getUserName();
						}
					}
				}
				
			}
			else if(chooice.equals("2")) {
				user u = registeredInterface(input, 1);
				userHash.insert(u);
				return "User "+u.getUserName();
			}
			else if(chooice.equals("3")) {
				return "Guest";
			}
			else {
				System.out.println("Wrong input! Only digit 1, 2 or 3");
			}
			
		}
		return "";
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
	public user registeredInterface(Scanner input, int i) {
		boolean pass = false;
		String username = "";
		String password = "";
		String password2;
		if(i == 1) {
			System.out.println("\nWelcome become our menbership");
		}
		else {
			System.out.println("\nWelcome to add manager");
		}
		
		while(!pass) {	
			System.out.print("Enter new username: ");
			 username = input.nextLine();
			System.out.print("Enter new password: ");
			 password = input.nextLine();
			System.out.print("Repeat enter new password:");
			 password2 = input.nextLine();
			if(password.equals("")||username.equals("")) {
				System.out.println("\npasswoed can not be empty\n");
			}
			else if(password.equals(password2)) {
				pass = true;
			}
			else {
				System.out.println("\nThe two passwords are different, please try again\n");
			}
		}
		
		user u = new user(username,password);
		return u;
		
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
		Cosmetic c = ht.searchAndGet(edit);
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

		System.out.println("\nSearching for a Cosmetic by brand and name!\n");
		System.out.print("Enter the brand: ");
		brand = input.nextLine();
		System.out.print("Enter the name: ");
		name = input.nextLine();
		Cosmetic search = new Cosmetic(brand, name);
		if (ht.search(search) < 0) {
			System.out.println("\n" + brand + "\'s " + name + " is not in the database.");

		} else {
			System.out.println(ht.searchAndGet(search));

		}
	}

	public void getPromoCode() {
		String[] code = { "BEAUTY25", "SPRING20", "LUCKY15", "AWESOME10" };
		Random r = new Random();
		int i = r.nextInt(code.length);
		System.out.println("Congradulations! You got your personal offer Promotion code:\n" + code[i]
				+ "\nYou can use the code when you buying your love products on the official website and get "
				+ code[i].substring(code[i].length() - 2) + "percent off! Enjoy!");

	}

	public void wirteToFile() {
		File file = new File("cosmetics.txt");
		PrintWriter p;
		try {
			p = new PrintWriter(file);
			bst1.writeToFile(p);
			p.close();
			file = new File("user.txt");
			p = new PrintWriter(file);
			userHash.writeToFile(p);
			p.close();
			file = new File("manager.txt");
			p = new PrintWriter(file);
			managerHash.writeToFile(p);
			p.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

	}
}