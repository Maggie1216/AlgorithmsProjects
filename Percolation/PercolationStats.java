
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
//import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
	private double [] thresholds;
	private int trials;
	private double thresMean = -1;
	private double thresStd = -1;
	
	// perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	
    	if(n<=0 || trials<=0) {
    		throw new java.lang.IllegalArgumentException();
    	}
    	
    	Percolation p;
    	this.trials = trials;
    	int luckyPickRow, luckyPickCol;
    	thresholds = new double[trials];
    	
    	for (int i = 0; i<trials; i++) {
    		p = new Percolation(n);
    		while (p.percolates() != true) {
    			do {
    				luckyPickRow = StdRandom.uniform(1, n+1);
    				luckyPickCol = StdRandom.uniform(1, n+1);
    			}while (p.isOpen(luckyPickRow, luckyPickCol));
    			p.open(luckyPickRow, luckyPickCol);
    		}
    		thresholds[i] = (double) p.numberOfOpenSites() / (n*n);
    		//System.out.println("trial number "+ trials + ", threshold is: " + (double) p.numberOfOpenSites() / (n*n));
    	}
    	
    }
    
    
    // sample mean of percolation threshold
    public double mean() {
    	this.thresMean = StdStats.mean(thresholds);
    	return thresMean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
    	if(thresMean == -1) { mean(); }
    	this.thresStd = StdStats.stddev(thresholds);
    	return thresStd;
    	
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
    	if(thresMean == -1) { mean(); }
    	if(thresStd == -1) { stddev(); }
    	return thresMean-1.96*thresStd/Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	if(thresMean == -1) { mean(); }
    	if(thresStd == -1) { stddev(); }
    	return thresMean+1.96*thresStd/Math.sqrt(trials);
    }
     
   // test client (see below)
	public static void main(String[] args) {
		//Stopwatch sw = new Stopwatch();//timing
		
		PercolationStats PS = new PercolationStats(200,200);
		System.out.println(PS.mean()+" "+
		PS.stddev()+" "+
		PS.confidenceLo()+" "+
		PS.confidenceHi());
		
		//System.out.println("elapsed time (in sec) : " + sw.elapsedTime());
		
	}
	

}
