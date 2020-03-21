/**
* Hash.java
* @author
* @author
* CIS 22C, Lab 7
*/
import java.io.PrintWriter;
import java.util.ArrayList;

public class Hash<T extends FileOutPutFormat> {

private int numElements;
private ArrayList<List<T> > Table;

/**
* Constructor for the Hash.java
* class. Initializes the Table to
* be sized according to value passed
* in as a parameter
* Inserts size empty Lists into the
* table. Sets numElements to 0
* @param size the table size
*/
public Hash(int size) {
	Table = new ArrayList<>(size);
	for(int i=0;i<size;i++) {
		Table.add(new List<T>());
	}
	this.numElements = 0;
}

/**Accessors*/

/**
* Returns the hash value in the Table
* for a given key by taking modulus
* of the hashCode value for that key
* and the size of the table
* @param t the key
* @return the index in the Table
*/
private int hash(T t) {
	int code = t.hashCode();
    return code%Table.size();
}

/**
* Counts the number of keys at this index
* @param index the index in the Table
* @precondition 0 <= index < Table.length
* @return the count of keys at this index
* @throws IndexOutOfBoundsException
*/
public int countBucket(int index) throws IndexOutOfBoundsException{
	if(index<0||index>Table.size()) {
		throw new IndexOutOfBoundsException("countBucket(): " + index+" out of index.");
	}
    return Table.get(index).getLength();
}

/**
* Returns total number of keys in the Table
* @return total number of keys
*/
public int getNumElements() {
    return numElements;
}

/**
* Searches for a specified key in the Table
* @param t the key to search for
* @return the index in the Table
* at which the element is located
* or -1 if it is not found
*/
public int search(T t) {
	int index = hash(t);
	if(Table.get(index).linearSearch(t)>=0) {
		return index;
	}
	else {
		return -1;
	}
    
}

public T searchAndGet(T t) {
	int index = search(t);
	if(index>0) {
		Table.get(index).advanceToIndex(Table.get(index).linearSearch(t));
		return Table.get(index).getIterator();
	}
	return null;
}

public boolean check(T t) {
	int index = search(t);
	if(index > 0) {
		Table.get(index).advanceToIndex(Table.get(index).linearSearch(t));
		return t.equals(Table.get(index).getIterator());
	}
	else {
		return false;
	}
	
}


/**Mutators*/

/**
* Inserts a new key in the Table
* calls the hash method to determine placement
* @param t the key to insert
*/
public void insert(T t) {
	int bucket = hash(t);
	Table.get(bucket).addLast(t);
	numElements++;
}


/**
* removes the key t from the Table
* calls the hash method on the key to
* determine correct placement
* has no effect if t is not in
* the Table
* @param t the key to remove
*/
public void remove(T t) {
	if(search(t)>0) {
		int bucket = hash(t);
		int index = Table.get(bucket).linearSearch(t);
		if(index==1) {
			Table.get(bucket).removeFirst();
		}
		else if(index==Table.get(index).getLength()) {
			Table.get(bucket).removeLast();
		}
		else {
			Table.get(bucket).advanceToIndex(index);
			Table.get(bucket).removeIterator();
		}
	}
	
	
}

/**Additional Methods*/

/**
* Prints all the keys at a specified
* bucket in the Table. Each key displayed
* on its own line, with a blank line
* separating each key
* Above the keys, prints the message
* "Printing bucket #<bucket>:"
* Note that there is no <> in the output
* @param bucket the index in the Table
*/
public void printBucket(int bucket) {
	System.out.println("Printing bucket #"+bucket+":\n");
	System.out.println(Table.get(bucket));
}

/**
* Starting at the first bucket, and continuing
* in order until the last bucket, concatenates
* all elements at all buckets into one String
*/
 @Override public String toString() {
     String s = "";
     for(int i=0;i<Table.size();i++) {
    	 if(countBucket(i)!=0) {
    		 s +=Table.get(i);
    	 }
    	 
     }
       
     return s;
 }
 
 public void writeToFile(PrintWriter p) {
	 for(int i=0;i<Table.size();i++) {
		 for(int j=1;j<=Table.get(i).getLength();j++) {
			 Table.get(i).advanceToIndex(j);
			 p.println(Table.get(i).getIterator().fileOutPutFormat());
		 }
	 }
 }
}