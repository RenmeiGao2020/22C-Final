/**
 * BST.java
 * @author Renmei Gao
 * 
 * 
 * The first Binary Search Tree which extends Interface Comparable.
 * Manipulates data include insertion, searching, deletion and sorting 
 * according method compareTo which is defined by comparing brand+name 
 * inside the Object class.
 * 
 * CIS 22C Final Project
 * 
 */

import java.io.PrintWriter;
import java.util.NoSuchElementException;

public class BST<T extends Comparable<T>& Contain&FileOutPutFormat> {
	protected class Node {
		protected T data;
		protected Node left;
		protected Node right;

		public Node(T data) {
			this.data = data;
			left = null;
			right = null;
		}

		public Node getLeft() {
			return left;
		}

		public Node getRight() {
			return right;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public void setRight(Node right) {
			this.right = right;
		}
	}

	protected Node root;

	/*** CONSTRUCTORS ***/

	/**
	 * Default constructor for BST sets root to null
	 */
	public BST() {
		root = null;
	}

	/**
	 * Copy constructor for BST
	 * 
	 * @param BST the BST to make a copy of
	 */
	public BST(BST<T> BST) {
		if (BST.root == null) {
			root = null;
		} else {
			copyHelper(BST.root);
		}
	}

	/**
	 * Helper method for copy constructor
	 * 
	 * @param node the node containing data to copy
	 */
	private void copyHelper(Node node) {
		if (node != null) {
			insert(node.data);
			copyHelper(node.left);
			copyHelper(node.right);
		}
	}

	/*** ACCESSORS ***/

	/**
	 * Returns the data stored in the root
	 * 
	 * @precondition !isEmpty()
	 * @return the data stored in the root
	 * @throws NoSuchElementException when precondition is violated
	 */
	public Node getRoot() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("Error for getRoot. Tree is empty.");
		}
		return root;
	}

	/**
	 * Determines whether the tree is empty
	 * 
	 * @return whether the tree is empty
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Returns the current size of the tree (number of nodes)
	 * 
	 * @return the size of the tree
	 */
	public int getSize() {
		return getSize(root);
	}

	/**
	 * Helper method for the getSize method
	 * 
	 * @param node the current node to count
	 * @return the size of the tree
	 */
	private int getSize(Node node) {
		if (node == null) {
			return 0;
		} else {
			return 1 + getSize(node.left) + getSize(node.right);
		}
	}

	/**
	 * Returns the height of tree by counting edges.
	 * 
	 * @return the height of the tree
	 */
	public int getHeight() {
		return getHeight(root);
	}

	/**
	 * Helper method for getHeight method
	 * 
	 * @param node the current node whose height to count
	 * @return the height of the tree
	 */
	private int getHeight(Node node) {
		if (node == null) {
			return -1;
		}
		return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
	}

	/**
	 * Returns the smallest value in the tree
	 * 
	 * @precondition !isEmpty()
	 * @return the smallest value in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T findMin() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("Error for findMin. Tree is empty.");
		}
		return findMin(root);
	}

	/**
	 * Helper method to findMin method
	 * 
	 * @param node the current node to check if it is the smallest
	 * @return the smallest value in the tree
	 */
	private T findMin(Node node) {
		if (node.left != null) {
			return findMin(node.left);
		}
		return node.data;
	}

	/**
	 * Returns the largest value in the tree
	 * 
	 * @precondition !isEmpty()
	 * @return the largest value in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T findMax() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("Error for findMax. Tree is empty.");
		}
		return findMax(root);
	}

	/**
	 * Helper method to findMax method
	 * 
	 * @param node the current node to check if it is the largest
	 * @return the largest value in the tree
	 */
	private T findMax(Node node) {
		if (node.right != null) {
			return findMax(node.right);
		}
		return node.data;
	}

	/**
	 * Searches for a specified value in the tree
	 * 
	 * @param data the value to search for
	 * @return whether the value is stored in the tree
	 */
	public boolean search(T data) {
		if (root == null) {
			return false;
		} else {
			return search(data, root);
		}
	}

	/**
	 * Helper method for the search method
	 * 
	 * @param data the data to search for
	 * @param node the current node to check
	 * @return whether the data is stored in the tree
	 */
	private boolean search(T data, Node node) {
		if (data.equals(node.data)) {
			return true;
		} else if (data.compareTo(node.data) < 0) {
			if (node.left == null) {
				return false;
			}
			return search(data, node.left);
		} else {
			if (node.right == null) {
				return false;
			}
			return search(data, node.right);
		}
	}
	
	

	/**
	 * Determines whether two trees store identical data in the same structural
	 * position in the tree
	 * 
	 * @param o another Object
	 * @return whether the two trees are equal
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof BST)) {
			return false;
		} else {
			BST<T> b = (BST<T>) o;
			return equals(root, b.root);
		}
	}

	/**
	 * Helper method for the equals method
	 * 
	 * @param node1 the node of the first BST
	 * @param node2 the node of the second BST
	 * @return whether the two trees contain identical data stored in the same
	 *         structural position inside the trees
	 */
	private boolean equals(Node node1, Node node2) {
		if (node1 == null && node2 == null) {
			return true;

		} else if (node1 == null || node2 == null) {
			return false;

		} else {
			return node1.data.equals(node2.data) && equals(node1.left, node2.left) && equals(node1.right, node2.right);
		}
	}

	/*** MUTATORS ***/

	public void serRoot(Node root) {
		this.root = root;
	}

	/**
	 * Inserts a new node in the tree
	 * 
	 * @param data the data to insert
	 */
	public void insert(T data) {
		if (root == null) {
			root = new Node(data);
		} else {
			if(!search(data)) {
				insert(data, root);
			}
			
		}
	}

	/**
	 * Helper method to insert Inserts a new value in the tree
	 * 
	 * @param data the data to insert
	 * @param node the current node in the search for the correct location in which
	 *             to insert
	 */
	private void insert(T data, Node node) {
		if (data.compareTo(node.data) <= 0) {
			if (node.left == null) {
				node.left = new Node(data);
			} else {
				insert(data, node.left);
			}
		} else {
			if (node.right == null) {
				node.right = new Node(data);
			} else {
				insert(data, node.right);
			}
		}
	}
	
	public void insertByPrice(T data) {
		if (root == null) {
			root = new Node(data);
		} else {
			insertByPrice(data, root);
		}
	}
	private void insertByPrice(T data, Node node) {
		if (data.CompareSecondaryKey(node.data) <= 0) {
			if (node.left == null) {
				node.left = new Node(data);
			} else {
				insertByPrice(data, node.left);
			}
		} else {
			if (node.right == null) {
				node.right = new Node(data);
			} else {
				insertByPrice(data, node.right);
			}
		}
	}
	
	public void insertAll(BST<T> bst) {
		if(bst.isEmpty()||bst==null) {
			return;
		}
		else {
			insertAll(bst.root);
		}
	}
	private void insertAll(Node node) {
		if(node!=null) {
			insertAll(node.left);
			this.insert(node.data);
			insertAll(node.right);
		}
	}

	/**
	 * Removes a value from the BST
	 * 
	 * @param data the value to remove
	 * @precondition !isEmpty()
	 * @precondition the data is located in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public void remove(T data) throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("Error for remove. Tree is empty.");
		} else if (!search(data, root)) {
			throw new NoSuchElementException("Error for remove. Element not found.");
		} else {
			root = remove(data, root);
		}
	}

	/**
	 * Helper method to the remove method
	 * 
	 * @param data the data to remove
	 * @param node the current node
	 * @return an updated reference variable
	 */
	private Node remove(T data, Node node) {
		if (node == null) {
			return null;
		} else if (data.compareTo(node.data) < 0) {
			node.left = remove(data, node.left);
		} else if (data.compareTo(node.data) > 0) {
			node.right = remove(data, node.right);
		} else {
			if (node.left == null && node.right == null) {
				node = null;
			} else if (node.left != null && node.right == null) {
				node = node.left;
			} else if (node.left == null && node.right != null) {
				node = node.right;
			} else {
				node.data = findMin(node.right);
				node.right = remove(findMin(node.right), node.right);
			}
		}
		return node;
	}

	/*** ADDITIONAL OPERATIONS ***/

	/**
	 * Prints the data in pre order to the console
	 */
	public void preOrderPrint() {
		if (root == null) {
			return;
		} else {
			preOrderPrint(root);
		}
	}

	/**
	 * Helper method to preOrderPrint method Prints the data in pre order to the
	 * console
	 */
	private void preOrderPrint(Node node) {
		if (node != null) {
			System.out.print(node.data);
			preOrderPrint(node.left);
			preOrderPrint(node.right);
		}
	}

	/**
	 * Prints the data in sorted order to the console
	 */
	public void inOrderPrint() {
		if (root == null) {
			return;
		} else {
			inOrderPrint(root);
		}
	}

	/**
	 * Helper method to inOrderPrint method Prints the data in sorted order to the
	 * console
	 */
	private void inOrderPrint(Node node) {
		if (node != null) {
			inOrderPrint(node.left);
			System.out.print(node.data);
			inOrderPrint(node.right);
		}
	}

	/**
	 * Prints the data in post order to the console
	 */
	public void postOrderPrint() {
		if (root == null) {
			return;
		} else {
			postOrderPrint(root);
		}
	}

	/**
	 * Helper method to postOrderPrint method Prints the data in post order to the
	 * console
	 */
	private void postOrderPrint(Node node) {
		if (node != null) {
			postOrderPrint(node.left);
			postOrderPrint(node.right);
			System.out.print(node.data);
		}
	}
	
	public BST<T> Contain(String s){
		if(root == null) {
			return null;
		}
		else {
			BST<T> al= new BST<>();
			al = Contain(root, s);
			return al;
		}
	}
	
	private BST<T> Contain(Node node, String s) {
		BST<T> al = new BST<>();
		if(node != null) {
			
			al.insertAll(Contain(node.left, s));
			if(node.data.contain(s)) {
				al.insert((T)node.data);
			}
			al.insertAll(Contain(node.right, s));
			
		}
		return al;
	}
	
	public void writeToFile(PrintWriter p) {
		if(root == null) {
			return;
		}
		writeToFile(p,root);
	}
	
	private void writeToFile(PrintWriter p, Node node) {
		if (node != null) {
			writeToFile(p,node.left);
			p.println(node.data.fileOutPutFormat());
			writeToFile(p,node.right);
		}
	}

}