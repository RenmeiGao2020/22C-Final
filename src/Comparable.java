  

/**
 * Comparable.java
 * 
 * @author Renmei Gao
 * 
 *         CIS 22C Final Project Interface CompareKeys contains two compare
 *         methods to compare two objects with two different subjects
 */

public interface Comparable<T> {

	/**
	 * Compares this object with the specified object for order.
	 * 
	 * @param t to compare with
	 * @return Returns a negative integer, zero, or a positive integer as this
	 *         object is less than, equal to, or greater than t he specified object.
	 */
	public int compareTo(T t);

	/**
	 * Compares this object with the specified object for order.
	 * 
	 * @param t to compare with
	 * @return Returns a negative integer, zero, or a positive integer as this
	 *         object is less than, equal to, or greater than t he specified object.
	 */
	public int CompareSecondaryKey(T t);

}