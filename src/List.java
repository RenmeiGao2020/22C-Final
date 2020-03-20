/**
 /**
 * List.java
 * @author wen luo
 * @author xinyu zhang
 */

import java.util.NoSuchElementException;

public class List<T> {
	private class Node {
		private T data;
		private Node next;
		private Node prev;

		public Node(T data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}
	}

	private int length;
	private Node first;
	private Node last;
	private Node iterator;

	/**** CONSTRUCTOR ****/

	/**
	 * Instantiates a new List with default values
	 * 
	 * @postcondition a new empty list created
	 */
	public List() {
		first = null;
		last = null;
		length = 0;
		iterator = null;
	}

	/**
	 * Instantiates a new List by coping another List
	 * 
	 */
	public List(List<T> original) {
		if (original == null) {
			return;
		}
		if (original.length == 0) {
			length = 0;
			first = null;
			last = null;
			iterator = null;
		} else {
			Node temp = original.first;
			while (temp != null) {
				addLast(temp.data);
				temp = temp.next;
			}
			iterator = null;
		}
	}

	/**** ACCESSORS ****/

	/**
	 * return the data stored by the Node pointed to by the iterator
	 * 
	 * @precondition iterator is not null
	 * @return the value pointed at by the iterator
	 * @throws NullPointerException when the precondition is violated
	 */
	public T getIterator() throws NullPointerException { // accessor
		if (iterator == null) {
			throw new NullPointerException("getIterator: " + "iterator is off the end of the List.");

		}
		return iterator.data;
	}

	/**
	 * return whether the iterator is null
	 * 
	 * @return whether the iterator is null or not
	 */
	public boolean offEnd() {
		return iterator == null;
	}

	/**
	 * Returns the value stored in the first node
	 * 
	 * @precondition !isEmpty()
	 * @return the value stored at node first
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getFirst() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("getFirst(): List is empty");
		}
		return first.data;
	}

	/**
	 * Returns the value stored in the last node
	 * 
	 * @precondition !isEmpty()
	 * @return the value stored in the node last
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getLast() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("getLast(): List is empty");
		}
		return last.data;
	}

	/**
	 * Returns the current length of the list
	 * 
	 * @return the length of the list from 0 to n
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns whether the list is currently empty
	 * 
	 * @return whether the list is empty
	 */
	public boolean isEmpty() {
		return length == 0;
	}

	/**
	 * Determines whether two List have the same data in the same order
	 * 
	 * @param L the List to compare to this List
	 * @return whether the two Lists are equal
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof List)) {
			return false;
		} else {
			List<T> L = (List<T>) o;
			if (this.length != L.length) {
				return false;

			} else {
				Node temp1 = this.first;
				Node temp2 = L.first;
				while (temp1 != null) {// List are same length
					if (temp1.data != temp2.data) {
						return false;
					}
					temp1 = temp1.next;
					temp2 = temp2.next;
				}
				return true;
			}
		}
	}




	/**
	 * Returns the index of the iterator from 1 to n. Note that there is no index 0.
	 * Does not use recursion.
	 * @precondition 
	 * @return the index of the iterator
	 * @throws NullPointerException when the precondition is violated
	 */
	public int getIndex() throws NullPointerException {
		if(iterator == null) {
			throw new NullPointerException("getIndex(): Iterator is off end.");
		}
		else {
			int index =1;
			while(iterator!= first) {
				iterator = iterator.prev;
				index++;
			}
			advanceToIndex(index);
			return index;
		}
		

	}
	
	
	/**
     * Uses the iterative linear search
     * algorithm to locate a specific
     * element in the list
     * @param element the value to search for
     * @return the location of value in the
     * List or -1 to indicate not found
     * Note that if the List is empty we will
     * consider the element to be not found
     * @postcondition: position of the iterator remains
     * unchanged!
     */
    public int linearSearch(T element) {
    	Node n = first;
    	for(int i=1;i<=length;i++) {
    		if(n.data.equals(element)) {
    			return i;
    		}
    		n = n.next;
    	}
        return -1;
    }
    
    

   




	

	/**** MUTATORS ****/

	/**
	 * moves the iterator to the start of the list
	 */
	public void placeIterator() {
		iterator = first;
	}

	/**
	 * add an element after the node currently pointed by the iterator
	 * 
	 * @param data the data to insert
	 * @precondition !offEnd()
	 * @throws NullPointerException
	 */
	public void addIterator(T data) throws NullPointerException {
		if (offEnd()) { // precondition
			throw new NullPointerException("addIterator: " + "Iterator is off end. Cannot add.");
		} else if (iterator == last) { // edge case
			addLast(data);
		} else { // general case
			Node n = new Node(data);
			n.next = iterator.next;
			// add a couple of steps for a doubly linked list
			n.prev = iterator;
			iterator.next.prev = n;
			iterator.next = n;
			length++;
		}
	}

	/**
	 * removes the node referenced by the iterator
	 * 
	 * @precondition !offEnd()
	 * @postcondition iterator = null
	 * @throws NullPointerException when the precondition is violated
	 */
	public void removeIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("removeIterator(): Cannot remove when iterator is null.");
		} else if (iterator == first) {
			removeFirst();
		} else if (iterator == last) {
			removeLast();
		} else {
			iterator.prev.next = iterator.next;
			iterator.next.prev = iterator.prev;
			length--;
		}
		iterator = null;
	}

	/**
	 * Creates a new first element
	 * 
	 * @param data the data to insert at the front of the list
	 * @postcondition a new first node is created
	 */
	public void addFirst(T data) {
		Node N = new Node(data);
		if (length == 0) {
			first = last = N;
		} else {
			N.next = first;
			first.prev = N;
			first = N;
		}
		length++;
	}

	/**
	 * Creates a new last element
	 * 
	 * @param data the data to insert at the end of the list
	 * @postcondition a new last is created
	 */
	public void addLast(T data) {
		Node N = new Node(data);
		if (length == 0) {
			first = last = N;
		} else {
			last.next = N;
			N.prev = last;
			last = N;
		}
		length++;
	}

	/**
	 * removes the element at the front of the list
	 * 
	 * @precondition !isEmpty()
	 * @postcondition the first is removed
	 * @throws NoSuchElementException when precondition is violated
	 */
	public void removeFirst() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("removeFirst(): Cannot remove from the empty list");
		} else if (length == 1) {
			first = last = null;
		} else {
			first = first.next;
		}
		length--;
	}

	/**
	 * removes the element at the end of the list
	 * 
	 * @precondition !isEmpty
	 * @postcondition the last is removed, new last created
	 * @throws NoSuchElementException when precondition is violated
	 */
	public void removeLast() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("removeLast(): Cannot remove from the empty list");
		} else if (length == 1) {
			first = last = null;
		} else {
			last = last.prev;
			last.next = null;
		}
		length--;
	}
	
	/**
	 * Places the iterator at first and then iteratively advances it to the
	 * specified index no recursion
	 * 
	 * @param index the index where the iterator should be placed
	 * @precondition 1 <= index <= length
	 * @throws IndexOutOfBoundsException when precondition is violated
	 */
	public void advanceToIndex(int index) throws IndexOutOfBoundsException {
		if(index<1||index>length) {
			throw new IndexOutOfBoundsException("advanceToIndex(): index out of bound");
		}
		else {
			placeIterator();
			for(int i=1;i<index;i++) {
				advanceIterator();
			}
		}

	}

	/**** ADDITIONAL OPERATIONS ****/

	/**
	 * Advances the iterator by one node in the list
	 * 
	 * @precondition !offEnd()
	 * @throws NiullPointerException when the precondition is violated
	 */
	public void advanceIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("advanceIterator: " + "Iterator is null and cannot advance.");
		}
		iterator = iterator.next;
	}

	/**
	 * moves the iterator down by one node
	 */
	public void reverseIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("reverseIterator: Iterator is null and cannot reverse.");
		}
		iterator = iterator.prev;
	}

	/**
	 * List with each value on its own line At the end of the List a new line
	 * 
	 * @return the List as a String for display
	 */
	@Override
	public String toString() {
		String result = "";
		Node temp = first;
		while (temp != null) {
			result += temp.data + "\n";
			temp = temp.next;
			result += "\n";
		}
		

		return result;
	}

	/**
	 * Prints a linked list to the console in reverse by calling the private
	 * recursive helper method printInReverse
	 */
	public void printInReverse() {
		printInReverse(last);
	}

	/**
	 * Recursively prints a linked list to the console in reverse order from last to
	 * first (no loops) Each element separated by a space Should print a new line
	 * after all elements have been displayed
	 */

	private void printInReverse(Node node) {
		if (node == first) {
			System.out.println(first.data);
		} else {
			System.out.print(node.data + " ");
			printInReverse(node.prev);

		}

	}

}