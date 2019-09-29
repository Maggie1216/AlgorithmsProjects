import java.util.ArrayList;
import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
	
	private TreeSet<Point2D> ts;
	// construct an empty set of points 
	public PointSET() {
		 this.ts = new TreeSet<Point2D>();
		
	}
	
	// is the set empty? 
	public boolean isEmpty() {
		return ts.isEmpty();
	}
	
	// number of points in the set 
	public int size() {
		return ts.size();
	}
	
	 // add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		assert !contains(p);
		ts.add(p);
	}
	
	// does the set contain point p? 
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		return ts.contains(p);
	}
	
	// draw all points to standard draw 
	public void draw() {
		for (Point2D pi : ts) {
			pi.draw();
		}
	}
	
	// all points that are inside the rectangle (or on the boundary) 
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new IllegalArgumentException();
		}
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		for (Point2D pi : ts) {
			if(rect.contains(pi)) {
				points.add(pi);
			}
		}
		return points;
	}
	
    // a nearest neighbor in the set to point p; null if the set is empty
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		if (isEmpty()) {
			return null;
		}
		double distmin = Double.POSITIVE_INFINITY;
		double dist;
		Point2D nearestPoint = p;
		for (Point2D pi : ts) {
			dist = p.distanceSquaredTo(pi);
			if(dist < distmin) {
				distmin = dist;
				nearestPoint = pi;
			}
		}
		return nearestPoint;
	}
	
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Point2D p1 = new Point2D(1,2);
		Point2D p2 = new Point2D(2,2);
		Point2D p3 = new Point2D(-1,3);
	    Point2D  A  = new Point2D(0.25,1.0);
	    Point2D  B  = new Point2D(0.75, 0.75);
	    Point2D  C  = new Point2D(1.0, 0.5);
	    Point2D  D  = new Point2D(0.25, 0.0);
	    Point2D  E  = new Point2D(0.25, 1.0);
	    Point2D  F  = new Point2D(0.0, 0.0);
	    Point2D  G  = new Point2D(0.5, 0.5);
	    Point2D  H  = new Point2D(1.0, 1.0);
	    Point2D  I  = new Point2D(0.25, 0.25);
	    Point2D  J  = new Point2D(1.0, 0.75);
		PointSET ps = new PointSET();
		RectHV rh = new RectHV(0,1,1.5,3);
		//ps.insert(p1);
		//ps.insert(p2);
		//ps.insert(p3);
		ps.insert(A);
		ps.insert(B);
		ps.insert(C);
		ps.insert(D);
		ps.insert(E);
		ps.insert(F);
		ps.insert(G);
		ps.insert(H);
		ps.insert(I);
		ps.insert(J);
		for (Point2D pi : ps.range(rh)) {
			System.out.println(pi);
		}
		for (Point2D pi: ps.ts) {
			System.out.println(pi);
		}

		System.out.println("nearest" + ps.nearest(new Point2D(0.5,0.5)));
		
	}
	*/

}
