
import edu.princeton.cs.algs4.StdIn;

public class Permutation {

	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> queTest = new RandomizedQueue<String>();
		String str;
		while ( !StdIn.isEmpty() ) {
			str = StdIn.readString();
			queTest.enqueue(str);
		}
		for (int i = 0; i<k; i++) {
			System.out.println(queTest.dequeue());
		}

		
	}

}
	
