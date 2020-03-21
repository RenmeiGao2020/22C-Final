import java.util.ArrayList;

public class Cosmetic implements Comparable<Cosmetic>, Contain,FileOutPutFormat {
	private String category;
	private String brand;
	private String name;
	private double price;
	private String color;
	private String link;
	private ArrayList<String> occasion;
	private String review;

	public Cosmetic(String category, String brand, String name, double price, String color, String link,ArrayList<String> occ,
			String review) {
		this.category = category;
		this.brand = brand;
		this.name = name;
		this.price = price;
		this.color = color;
		this.link = link;
		this.occasion = occ;
		this.review = review;
	}
	
	public Cosmetic(String brand, String name) {
		this.name = name;
		this.brand = brand;
	}

	/** mutator **/
	public void setCategory(String category) {
		this.category = category;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void addOccasion(String occasion) {
		this.occasion.add(occasion);
	}

	public void setReview(String review) {
		this.review = review;
	}
	
	@Override
	public boolean contain(String s) {
		return category.equalsIgnoreCase(s)||brand.equalsIgnoreCase(s)||name.contains(s)||color.equalsIgnoreCase(s)||occasion.contains(s.toLowerCase());
	}
	

	/** accessor **/
	public String getCategory() {
		return category;
	}

	public String getBrand() {
		return brand;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getColor() {
		return color;
	}

	public String getLink() {
		return link;
	}

	public String getOccasion(int index) {
		return occasion.get(index);
	}

	public String getReview() {
		return review;
	}

	/** additional operation **/
	@Override
	public String toString() {
		return "\nCategory: " + category + "\nBrand: " + brand + "\nName " + name + "\nPrice: $"
				+ String.format("%,.2f", price) + "\nColor: " + color + "\nReview: " + review + "\nLink: " + link;

	}

	/**
	 * Returns a consistent hash code for each Cosmetic by summing the Unicode
	 * values of each character in the key Key = brand + name
	 * 
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		int sum = 0;
		String key = brand + name;
		for (int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}

	/**
	 * Compares two Cosmetic objects to determine ordering Returns 0 if the two
	 * items are equal Return -1 if this Cosmetic's brand comes alphabetically
	 * before the C's brand. Returns 1 if the C's name comes alphabetically before
	 * this cosmetic's name If the two cosmetics' brands are the same, will
	 * differentiate by names (alphabetical comparison)
	 * 
	 * @param C Cosmetic object to compare to this
	 * @return 0 (same cosmetic), -1 (this cosmetic order first) or 1 (C order
	 *         first)
	 */
	@Override
	public int compareTo(Cosmetic C) {
		if (this.equals(C)) {
			return 0;
		} else {
			if (brand.compareTo(C.brand) < 0) {
				return -1;
			} else if (brand.compareTo(C.brand) > 0) {
				return 1;
			} else {
				if (name.compareTo(C.name) < 0) {
					return -1;
				} else if (name.compareTo(C.name) > 0) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	}
	
	@Override
	public int CompareSecondaryKey(Cosmetic C) {
		if (this.equals(C)) {
			return 0;
		} else {
			if (price < C.price) {
				return -1;
			} else if (price > C.price) {
				return 1;
			} else {
				return 0;
			}
		}

	}

	/**
	 * Determines whether two Cosmetic objects are equal by comparing brands and
	 * names
	 * 
	 * @param o the object to compare
	 * @return whether the Cosmetics are equal
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (!(o instanceof Cosmetic)) {
			return false;
		} else {
			Cosmetic C = (Cosmetic) o;
			return brand.equals(C.brand) && name.equals(C.name);
		}
	}
	@Override
	public String fileOutPutFormat() {
		int occNum = occasion.size();
		String occ = "";
		for(int i=0;i<occNum;i++) {
			occ += occasion.get(i)+"\n";
		}
		return category + "\n" + brand + "\n" + name + "\n"
				+ String.format("%,.2f", price) + "\n" + color + "\n" + occNum +"\n" + occ + review + "\n" + link+"\n";
	}

}