import java.util.ArrayList;
import java.util.Arrays;


public class FastCollinearPoints {
	
	private ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
	
	
	// finds all line segments containing 4 or more points
	public FastCollinearPoints(Point[] points) {
		
		if (points == null) {
			throw new IllegalArgumentException();
		}
		
		Point p;
		int N = points.length;
		Point[] pointsCopy = new Point[N];
		ArrayList<Double> slopes = new ArrayList<Double>();
		ArrayList<Double> sameSlope = new ArrayList<Double>();
		ArrayList<Point> samePoints = new ArrayList<Point>();
		Point[] nowPoint0 = new Point[1];
			
		//make a copy to ensure that every p get iterated
		for (int i = 0; i<N; i++) {
			if (points[i] == null) {
				throw new IllegalArgumentException();
			}
			pointsCopy[i] = points[i];
		}
		
		
		//now do the slope comparison
		//for each p
		for (int i = 0; i<N; i++) { 
			p = points[i];
			
			//sort it, the first element of points is also p
			Arrays.sort(pointsCopy, p.slopeOrder()); 
			
			//get the slopes value, slopes should be size N-i-1, and it should be ascending
			for (int j = 1; j<N; j++) {
				if (p.compareTo(pointsCopy[j]) == 0) {
					throw new IllegalArgumentException();
				}
				slopes.add(p.slopeTo(pointsCopy[j]));
			}
			
			
			
			//find consecutive slopes
			for (int k = 0; k<slopes.size(); k++) {

				/*//debug
				System.out.println("----------------------------------------------");
				System.out.println("points(slopeOrder): ");
				for (int m = 0; m<N; m++) {
					System.out.print(pointsCopy[m]);
				}
				System.out.println("\npoints(not slopeOrder): ");
				for (int m = 0; m<N; m++) {
					System.out.print(points[m]);
				}
				System.out.println();
				System.out.println("slope: "+slopes);
				System.out.printf("i = %d (point i in points(not slopeOrder)[i], k = %d (slope k in slope[k])\n", i,k);
				System.out.println("\nPoint: "+points[i]);
				 */
				
				//see if this slope is duplicate, if not then clear sameSlope
				double thisSlope = slopes.get(k);
				Point thisPoint = pointsCopy[k+1];

				if ( (sameSlope.isEmpty() || sameSlope.get(0) == thisSlope) && k!=slopes.size()-1) {
					//System.out.println("!!!!!!!SAME");
					sameSlope.add(thisSlope);
					samePoints.add(thisPoint);
					nowPoint0[0] = p;

				} else {
					//System.out.println("!!!!!!!NOT SAME");
					
					//last element
					if (k == slopes.size()-1) {
						if (sameSlope.get(0) == thisSlope) {
							//System.out.println("!!!!!!!SAME");
							sameSlope.add(thisSlope);
							samePoints.add(thisPoint);
							nowPoint0[0] = p;
						}
					}
					
					//all cases
					if (sameSlope.size() >=3) {	
						//System.out.println("!!!!!!!3 OR MORE");
						samePoints.add(nowPoint0[0]);
						Point[] pointsTemp =  samePoints.toArray(new Point[samePoints.size()]);

						Arrays.sort(pointsTemp);
						
						ls.add(new LineSegment(pointsTemp[0],pointsTemp[pointsTemp.length-1]));


					}
				
					sameSlope.clear();
					samePoints.clear();
					sameSlope.add(thisSlope);
					samePoints.add(thisPoint);
					nowPoint0[0] = p;

				}
	


			}
			
			slopes.clear();
		}
		
		if (N<4) {
			ls.clear();
			return;
		}
		
		removeDup(ls);	//REWRITE!!			
		
	}
	
	// the number of line segments
	public int numberOfSegments() {
		return ls.size();
	}
	
	// the line segments
	public LineSegment[] segments() {
		   LineSegment [] lsa = ls.toArray(new LineSegment[ls.size()]);
		   return lsa;
	}
	
	private void removeDup(ArrayList<LineSegment> lsIn) {
		
		   ArrayList<LineSegment> lsFinal = new ArrayList<LineSegment>();
		   ArrayList<String> lsNamesUnique = new ArrayList<String>();
		   String tempName;
		   
		   for (int i = 0; i<lsIn.size(); i++) {
			   tempName = lsIn.get(i).toString();
			   if (!lsNamesUnique.contains(tempName)) {
				   lsNamesUnique.add(tempName);
				   lsFinal.add(lsIn.get(i));
			   }
		   }

		   this.ls = lsFinal;

	}
	
	public static void main(String[] args) {
		Point p0 = new Point(9000, 9000);
		Point p1 = new Point(8000, 8000);
		Point p2 = new Point(7000, 7000);
		Point p3 = new Point(6000, 6000);
		Point p4 = new Point(5000, 5000);
		Point p5 = new Point(4000, 4000);
		Point p6 = new Point(3000, 3000);
		Point p7 = new Point(2000, 2000);
		Point p8 = new Point(1000, 1000);
		//Point p9 = new Point(4,4);
		//Point p10 = new Point(5,4);
		//Point p11 = new Point(77,4);
		Point[] ps = {p0,p1,p2,p3,p4,p5,p6,p7,p8};
		FastCollinearPoints ftest = new FastCollinearPoints(ps);
		for (LineSegment i : ftest.segments()) {
			System.out.println(i);
		}
		System.out.println(ftest.numberOfSegments());

	}
	

	

}
