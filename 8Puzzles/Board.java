import java.util.ArrayList;

public class Board {

	private int [] tilesArray;
	private int N;
	
	// create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
    	N = tiles.length;
    	tilesArray=to1dArray(tiles);
    }
                                           
    // string representation of this board
    public String toString() {
    	String s;
    	s = N + "\n";

    	for (int r = 0; r<N; r++) {
    		for (int c = 0; c<N; c++) {
    			s += " " +tilesArray[r*N+c] ;
    			if (c == N-1) {
    				s +="\n";
    			}
    		}
    	}
    	return s;
    }

    // board dimension n
    public int dimension() {
    	return N;
    }

    // number of tiles out of place
    public int hamming() {
       	int num = 0;
       	
    	for (int i = 0; i<N*N; i++) {
    		if (tilesArray[i]!=i+1 && tilesArray[i]!=0) {
    			num++;
    		}
    	}
    	
    	return num;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

    	int num = 0;
    	
    	for (int i = 0; i<N*N; i++) {
    		if (tilesArray[i]!=i+1 && tilesArray[i]!=0) {
    			num+=Math.abs((tilesArray[i]-1)/N-Pos1To2(i)[0]);
    			num+=Math.abs(tilesArray[i]-1-(tilesArray[i]-1)/N *N - Pos1To2(i)[1]);
    		}
  	  		

    	}
    	
    	return num;
    	
    }
    
    private int[] Pos1To2(int i) {
    	int r = Math.floorDiv(i, N);
    	int c = i - r*N; 
    	int[] ans = {r,c};
    	return ans;
    }
    

    // is this board the goal board?
    public boolean isGoal() {
    	if (tilesArray[N*N-1] == 0) {

	    	for (int i = 0; i<N*N-1; i++) {
	    		if (tilesArray[i] != i+1) {
	    			return false;
	    		}
	    	}
	    	
	    	return true;
	    	
    	} else {
    		return false;
    	}
    }
    
    private int[] to1dArray(int[][] tilesIn) {
    	int [] tilesArrayIn = new int[N*N];
    	for (int r = 0; r<N; r++) {
    		for (int c = 0; c<N; c++) {
    			tilesArrayIn[r*N+c] = tilesIn[r][c];
    		}
    	}

    	return tilesArrayIn;
    }
        

    // does this board equal y?
    public boolean equals(Object y) {
    	if (y!=null && y.getClass().getName().equals("Board")) {
        	Board objy = (Board) y;
        	return objy.toString().equals(toString());
    	}
    	return false;

    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
    	ArrayList<Board> boardNeighbors = new ArrayList<Board>();
    	
    	//find 0 position 
    	int r0 = 0;
    	int c0 = 0;
    	for (int i = 0; i<N*N; i++) {
    		if (tilesArray[i] == 0) {
    			r0 = Pos1To2(i)[0];
    			c0 = Pos1To2(i)[1];
    			break;
    		}
    	}

    	
    	//find neighbors
    	if (r0!=0) {
    		Board Bup = new Board(copyBoard(tilesArray));
    		Bup.exchPos(Bup.tilesArray,r0, c0, r0-1, c0);
    		boardNeighbors.add(Bup);
    	}
    	
    	if (r0!=N-1) {
    		Board Bdown = new Board(copyBoard(tilesArray));
    		Bdown.exchPos(Bdown.tilesArray,r0, c0, r0+1, c0);
    		boardNeighbors.add(Bdown);
    	}
    	
    	if (c0!=0) {
    		Board Bleft = new Board(copyBoard(tilesArray));
    		Bleft.exchPos(Bleft.tilesArray,r0, c0, r0, c0-1);
    		boardNeighbors.add(Bleft);
    	}
    	
    	if (c0!=N-1) {
    		Board Bright = new Board(copyBoard(tilesArray));
    		Bright.exchPos(Bright.tilesArray,r0, c0, r0, c0+1);
    		boardNeighbors.add(Bright);
    	}

		return boardNeighbors;
    	
    }
    
    private int[][] copyBoard (int[] tilesArrayIn) {

    	int [][] BoardCopy = new int[N][N];;
    	for (int r = 0; r<N; r++) {
    		for (int c = 0; c<N; c++) {
    			BoardCopy[r][c] = tilesArrayIn[r*N+c];
    		}
    	}
    	return BoardCopy;
    	
    }




    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
    	int [] twinTilesArray = new int[N*N];
    	for (int i = 0; i<N*N; i++) {
    		twinTilesArray[i] = tilesArray[i];
    	}
    	int [] twinPos = checkTwinsPos(twinTilesArray);
    	exchPos(twinTilesArray, twinPos[0],twinPos[1],twinPos[2],twinPos[3]);
    	int [][] twinTiles = copyBoard(twinTilesArray);
    	return new Board(twinTiles);
    }
    
    private int[] checkTwinsPos(int[] tilesIn) {
    	int[] result = {0,0,0,0};
    	
    	if (tilesIn[result[2] *N + result[3]]==0) {
    		result[2] = 1;
    	}

    	for (int r = 0; r<N; r++) {
    		for (int c = 0; c<N; c++) {
    			if (!(r== result[2] && c== result[3]) && tilesIn[r*N +c] != 0) {
    				result[0] = r;
    				result[1] = c;
    				return result;
    			}
    		}
    	}
    	return result;
    	
    }
    
        
    private void exchPos(int[] tilesArrayIn, int r1, int c1, int r2, int c2) {
    	int item = tilesArrayIn[r1*N+c1];
    	tilesArrayIn[r1*N+c1] = tilesArrayIn[r2*N+c2];
    	tilesArrayIn[r2*N+c2] = item;
    }

    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
    	int [][] d = {{1,6,3},{2,7,8},{4,5,0}};
    	int [][] c = {{1,2,3},{4,5,6},{7,8,0}};
    	int [][] a = {{8,1,3},{4,0,2},{7,6,5}};
    	int [][] a0 = {{0,1,3},{4,2,5},{7,8,6}};
    	int [][] b = {{8,1,3},{4,0,2},{7,6,5}};
    	Board ab = new Board(a0);
    	Board ab0 = new Board(b);
    	//System.out.println("equal?: " + ab.equals(ab0));
    	System.out.println(ab.hamming());
    	System.out.println(ab.manhattan());
    	//System.out.println(ab.twin());
    	//for (Board i : ab.neighbors()) {
    	//	System.out.println(i);
    	//}

    	
    	System.out.println("is goal?: " + ab.isGoal());
    	System.out.println(ab.getClass().getName());
    	*/
	}

}
