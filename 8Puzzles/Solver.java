import java.util.ArrayList;
//import java.util.Iterator;

//import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
//import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private MinPQ<Node> pq = new MinPQ<Node>();
	private MinPQ<Node> pqtwin = new MinPQ<Node>();
	private Node solutionNode;
	private boolean isSolvableBool;
	
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	if (initial == null) {
    		throw new IllegalArgumentException();
    	}
    	
    	Node iniNode = new Node(initial,0,null);
    	pq.insert(iniNode);
    	Node iniNodeTwin = new Node(initial.twin(),0,null);
    	pqtwin.insert(iniNodeTwin);
    	//System.out.println(iniNode.B);
    	//System.out.println(iniNodeTwin.B);

    	
    	while ((! pq.min().B.isGoal()) && (! pqtwin.min().B.isGoal())){
    		
    		//delete minimum
    		Node currNode = pq.delMin();    
    		
    		//enqueue new neighbors
    		for (Board Bi : currNode.B.neighbors()) {
    			//check if this neighbor exists in path
        		if (sameBoardsCheck(Bi,currNode)) {
        			pq.insert(new Node(Bi,currNode.moves+1,currNode));
        		}

    		}
    		
 
    		////do the same thing for twin borad
    		//delete minimum
    		Node currNodeTwin = pqtwin.delMin();    
    		
    		//enqueue new neighbors
    		for (Board Bi : currNodeTwin.B.neighbors()) {
    			//check if this neighbor exists in path
        		if (sameBoardsCheck(Bi,currNodeTwin)) {
        			pqtwin.insert(new Node(Bi,currNodeTwin.moves+1,currNodeTwin));
        		}

    		}

    		/*
    		Iterator iterPQ = pq.iterator();
    		System.out.println("----------------------------pq: \n");
    		while (iterPQ.hasNext()) {
        		System.out.print(iterPQ.next() + "\n\n");
    		}
    		
    		Iterator iterPQt = pq.iterator();
    		System.out.println("----------------------------pqTWIN: \n");
    		while (iterPQt.hasNext()) {
        		System.out.print(iterPQt.next() + "\n\n");
    		}
    		*/
    		
    	} 
    	

    	if (pq.min().B.isGoal()) {
    		solutionNode = pq.min();
    		isSolvableBool = true;
    	} else if (pqtwin.min().B.isGoal()){
    		solutionNode = null;
    		isSolvableBool = false;
    	}
    	/*
    	System.out.println("Minimum number of moves = " + moves());
    	for (Board i : solution()) {
    		System.out.println(i);
    	}
    	*/
    	

    }
    
    private ArrayList<Board> solutionSequence(Node solutionNode) {
    	ArrayList<Board> sequenceFinal = new ArrayList<Board>();
    	while (solutionNode != null) {
    		sequenceFinal.add(solutionNode.B);
    		solutionNode = solutionNode.preN;
    	}
    	sequenceFinal = reverseArray(sequenceFinal);
    	return sequenceFinal;
    }
    
    private ArrayList<Board> reverseArray(ArrayList<Board> sequenceFinal) {
    	ArrayList<Board> sequenceFinalFinal = new ArrayList<Board>();
    	for (int i = sequenceFinal.size()-1; i>=0; i--) {
    		sequenceFinalFinal.add(sequenceFinal.get(i));
    	}
    	return sequenceFinalFinal;
    }
    
    private boolean sameBoardsCheck(Board b, Node currNodeIn) {
    	Node preNode = currNodeIn.preN;
		if (preNode == null ) {
			return true;
		}
		if ( b.equals(preNode.B)) {
			return false;
		}
    	return true;
    }
    
    private class Node implements Comparable<Node>{
    	private Board B;
    	private int moves;
    	private int dist;
    	private Node preN;
    	private Integer priorityNum;
    	
    	public Node(Board B, int moves, Node preN) {
    		this.B = B;
    		this.moves = moves;
    		this.preN = preN;
    		this.dist = B.manhattan();
    		this.priorityNum = moves + dist;
    	}
    	
		@Override
		public int compareTo(Node o) {
			return priorityNum.compareTo(o.priorityNum);
		}


    }

    // is the initial board solvable?
    public boolean isSolvable() {
    	return isSolvableBool;
    }

    // min number of moves to solve initial board
    public int moves() {
    	if (solutionNode == null) {
    		return -1;
    	} else {
    		return solutionSequence(solutionNode).size()-1;
    	}
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
    	if (solutionNode == null) {
    		return null;
    	} else {
    		return solutionSequence(solutionNode);
    	}
    }

    // test client (see below) 
	public static void main(String[] args) {
		/*
    	int [][] b = {{0,3,1},{4,2,5},{7,8,6}};
    	int [][] c = {{0,1,3},{4,2,5},{7,8,6}};
    	int [][] a = {{3,1},{2,0}};
    	int [][] d = {{1,2,3},{4,5,6},{8,7,0}};
    	Board ab = new Board(c);
    	Solver solver = new Solver(ab);
    	System.out.println(solver.isSolvable());
    	System.out.println(solver.solution());
    	
		
		
		/*
    	int[][] board = {
                {6, 2, 3},
                {4, 5, 1},
                {8, 7, 0}
        };
    	Solver s = new Solver(new Board(board));
    	System.out.println(s.solution());
    	s.moves();
    	s.isSolvable();
    	*/
		
		/*
	    // create initial board from file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    int[][] tiles = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            tiles[i][j] = in.readInt();

	    Board initial = new Board(tiles);
		System.out.println(initial);
	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	*/
   
	}

}
