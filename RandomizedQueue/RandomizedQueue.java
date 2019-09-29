import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] arry;
	private int N = 0;
	
    // construct an empty randomized queue
    public RandomizedQueue() {
    	arry = (Item []) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
    	return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
    	return N;
    }

    // add the item
    public void enqueue(Item item) {
    	if (item == null) {
    		throw new IllegalArgumentException();
    	}
    	if (N == arry.length) {
    		resize(N * 2);
    	}
    	arry[N] = item;
    	N ++;
    	
    }

    private void resize(int n) {
    	Item [] arry_copy =   (Item []) new Object[n];
    	for (int i = 0; i<arry.length;i++) {
    		arry_copy[i] = arry[i];
    	}
    	arry = arry_copy;
    }
    
    // remove and return a random item
    public Item dequeue() {
    	if (N == 0) {
    		throw new NoSuchElementException();
    	}
		if(N==(1/4) * arry.length) {
			resize(N/2);
		}
		int luckyPick = StdRandom.uniform(0, N);
    	Item luckyItem = arry[luckyPick];
    	arry[luckyPick] = arry[N-1];
    	arry[N-1] = null;
    	N --;
    	return luckyItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
    	if (N == 0) {
    		throw new NoSuchElementException();
    	}
    	int luckyPick = StdRandom.uniform(0, N);
    	Item luckyItem = arry[luckyPick];
    	return luckyItem;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
    	return new arrayIterator();
    }
    
    private class arrayIterator implements Iterator<Item>    {        
    	private int curr = 0;
    	public boolean hasNext() {  
    		return curr < N ;        
    	}        
    	public void remove() {  
    		throw new UnsupportedOperationException();
    	}        
    	public Item next() {  
    		if (curr >= N ) {
    			throw new NoSuchElementException();
    		}
    		return arry[curr++];       
    	}    
    } 

    // unit testing (required)
    public static void main(String[] args) {
    	RandomizedQueue<String> randQue = new RandomizedQueue<String>();
    	randQue.enqueue("a");
    	randQue.enqueue("b");
    	randQue.enqueue("c");
    	randQue.enqueue("d");
    	System.out.println(randQue.size());
    	Iterator <String>randQueIter = randQue.iterator();
    	while (randQueIter.hasNext()) {
    		System.out.printf("the next item is: %s\n", randQueIter.next());
    	}
    	System.out.printf("sample randomly: %s\n", randQue.sample());
    	System.out.printf("deque randomly: %s\n", randQue.dequeue());
    	System.out.println(randQue.size());
    	
    }

}
