
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int n;
	private int [][] grid;
	private WeightedQuickUnionUF UF;
	
	public Percolation(int n) {
		
		if (n<=0) {
			throw new java.lang.IllegalArgumentException();
		}
		
		this.n = n;
		grid = new int[n][n];	
		UF = new WeightedQuickUnionUF(n*n+2);
		
		//initialize virtual sites
		for (int i = 1; i < n+1; i++) {
			UF.union(0, i);
			UF.union(n*n+1, n*n+1-i);
		}
		
		
	}
	
	public void open(int row, int col) {

		if (row<1 || row >n || col<1 || col>n) {
			throw new java.lang.IllegalArgumentException();
		}
		
		row -= 1;
		col -= 1;
		
		grid[row][col] = 1;
		
		//these if statements are checking if the given row and col are at the edges
		if (row != 0){
			if (grid[row-1][col] == 1) {
				UF.union(rcToId(row,col), rcToId(row-1,col));
			}
		}
		
		if (row != n - 1) {
			if (grid[row+1][col] == 1) {
				UF.union(rcToId(row,col), rcToId(row+1,col));
			}
		}
		
		if (col != 0) {
			if (grid[row][col-1] == 1) {
				UF.union(rcToId(row,col), rcToId(row,col-1));
			}
		}
		
		if (col != n - 1) {
			if (grid[row][col+1] == 1) {
				UF.union(rcToId(row,col), rcToId(row,col+1));
			}
		}
		
	
		
	}
	
	public boolean isOpen(int row, int col) {
		
		if (row<1 || row >n || col<1 || col>n) {
			throw new java.lang.IllegalArgumentException();
		}
		
		return grid[row-1][col-1] == 1;
	}
	
	private int rcToId(int row0, int col0) { // indexing from 0
		int row = row0 + 1;
		int col = col0 + 1;
		return (row-1)*n + col;
	}
	
	public boolean isFull(int row, int col) {
		
		if (row<1 || row >n || col<1 || col>n) {
			throw new java.lang.IllegalArgumentException();
		}
		
		return isOpen(row,col) && UF.connected(0, rcToId(row-1, col-1));
	}
	
	public int numberOfOpenSites() {		
		int total = 0;
		for (int i = 0; i < n; i ++) {
			for (int j = 0; j < n; j ++) {
				if (isOpen(i+1,j+1)) {
					total += 1;
				}
			}
		}
		
		return total;
	}

	
	public boolean percolates() {
		if(n == 1) {
			return isFull(1,1);
		}else {
			return UF.connected(0, n*n+1);
		}
	}
	
	//for debug
	private void display() {
		for (int i = 0; i < n; i ++) {
			for (int j = 0; j < n; j ++) {
				if (isOpen(i+1,j+1)) {
					System.out.printf("1");
				} else {
					System.out.printf("0");
				}
			}
			System.out.println();
		}
		System.out.println("\n");
	}
	
	public static void main(String[] args) {
		Percolation p = new Percolation(6);
		p.open(1, 6);
		System.out.println("isFull?" + p.isFull(1, 6));
		p.display();
		/*
		p.open(1, 5);
		System.out.println("isFull?" + p.isFull(1, 5));
		p.display();
		p.open(2, 5);
		System.out.println("isFull?" + p.isFull(2, 5));
		p.display();
		*/
		System.out.println("Percolates?" + p.percolates());
		System.out.println("open " + p.numberOfOpenSites());
		
		
	}

}
