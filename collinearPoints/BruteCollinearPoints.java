
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	
	private int size = 0;
	private ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
	
	// finds all line segments containing 4 points
   public BruteCollinearPoints(Point[] points) {
	   if(points == null) {
		   throw new IllegalArgumentException();
	   }

	   int N = points.length;
	   double s1,s2,s3;
	   Point[] pointsSeg = new Point[4];
	   
	   for (int a = 0; a<N; a++) {
		   if (points[a] == null) {
			   throw new IllegalArgumentException();
		   }
	   }
	   
	   for (int a = 0; a<N; a++) {
		   
		   for (int b = a+1; b<N; b++) {
			   if (points[a].compareTo(points[b])==0) {
				   throw new IllegalArgumentException();
			   }
			   
			   for (int c = b+1; c<N; c++) {
				   
				   for (int d = c+1; d<N; d++) {
			
					   s1 = points[a].slopeTo(points[b]);
					   s2 = points[a].slopeTo(points[c]);
					   if (s1 == s2) {
						   s3 = points[a].slopeTo(points[d]);			
						   if (s2 == s3) {
							   pointsSeg[0] = points[a];
							   pointsSeg[1] = points[b];
							   pointsSeg[2] = points[c];
							   pointsSeg[3] = points[d];
							   Arrays.sort(pointsSeg);
							   ls.add(new LineSegment(pointsSeg[0],pointsSeg[3]));
							   size++;
						   }
					   }					    
				   }
			   }
		   }
	   }
   }
    
   // the number of line segments
   public int numberOfSegments() {
	   return size;
   }
   
   // the line segments
   public LineSegment[] segments() {
	   LineSegment [] lsa = ls.toArray(new LineSegment[ls.size()]);
	   return lsa;
   }
   
   /*
   public static void main(String[] args) {
	   
	   Point p0 = new Point(2,2);
	   Point p1 = new Point(2,2);
	   Point p2 = new Point(3,4);
	   Point p3 = new Point(7,8);
	   Point p4 = new Point(9,10);
	   Point p5 = new Point(2,4);
	   Point[] ps = {p0,p1,p2,p3,p4,p5};
	   BruteCollinearPoints btest = new BruteCollinearPoints(ps);
	   for (LineSegment i : btest.segments()) {
		   System.out.println(i);
	   }
	   System.out.println(btest.numberOfSegments());
	   
   }
   */
}
