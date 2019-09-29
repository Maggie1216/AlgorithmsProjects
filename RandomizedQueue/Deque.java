import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private Node first;
	private Node last;
	private int size = 0;
	
	private class Node {
		Item item;
		Node next;
		Node prev;
	}
	
    // construct an empty deque
    public Deque() {}

    // is the deque empty?
    public boolean isEmpty() {
    	return first == null;
    }

    // return the number of items on the deque
    public int size() {
    	return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
    	if(item == null) {
    		throw new IllegalArgumentException();
    	}
    	Node oldfirst = first;
    	first = new Node();
    	first.item = item;
    	first.next = oldfirst;
    	first.prev = null;
    	if (size == 0) {
    		last = first;
    	}else {
    		oldfirst.prev = first;
    	}
    	size ++;
    }

    // add the item to the back
    public void addLast(Item item) {
    	if(item == null) {
    		throw new IllegalArgumentException();
    	}
    	Node oldlast = last;
    	last = new Node();
    	last.item = item;
    	last.next = null;
		last.prev = oldlast;
		if(size == 0) {
			first = last;
		}else {
			oldlast.next = last;
		}
		
		size ++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
    	if(size == 0) {
    		throw new NoSuchElementException();
    	}
    	Item firstItem = first.item;
    	if (size == 1) {
    		first = null;
    		last = first;
    	} else {
	    	first = first.next;
	    	first.prev = null;
    	}
    	size --;
    	return firstItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
    	if(size == 0) {
    		throw new NoSuchElementException();
    	}
    	Item lastItem = last.item;
    	if (size == 1) {
    		last = null;
    		first = last;
    	} else {
	    	last = last.prev;
	    	last.next = null;
    	}
    	size --;
    	return lastItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
    	return new ListIterator();
    }
    
	private class ListIterator implements Iterator<Item> {
		private Node current = first;
		public boolean hasNext() {
			return current != null;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
		public Item next() {
			if (current == null) {
				throw new NoSuchElementException();
			}
			Item item = current.item;
			current = current.next;
			return item;
		}
	}

    // unit testing (required)
    public static void main(String[] args) {
    		Deque<String> dequeTest = new Deque<String>();
    		dequeTest.addFirst("good morning");
    		dequeTest.addLast("good afternoon");
    		dequeTest.addLast("good evening");
    		Iterator <String> i = dequeTest.iterator();
    		while (i.hasNext()) {
    			System.out.printf("The next item is: %s\n", i.next());
    		}
    		System.out.println(dequeTest.removeFirst());
    		System.out.println(dequeTest.removeLast());
    		System.out.println(dequeTest.size());

    }

}
