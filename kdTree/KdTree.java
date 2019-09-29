
import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;


public class KdTree {
	private Node root;
	private int size;
	private Point2D champ = null;
	private double distMin = Double.POSITIVE_INFINITY;
	public KdTree() {
	}
	
	private class Node implements Comparable<Node>{
		private Point2D p;
		private boolean compareWithX; 
		private Node left;
		private Node right;
		private RectHV rect;

		
		
		Node(Point2D p, boolean compareWithX, RectHV rect) {
			this.p = p;
			this.compareWithX = compareWithX;
			this.rect = rect;
		}
		
		
		Node(Point2D p) {
			this.p = p;
		}
		
		public String toString() {
			return "Node [p=" + p + ", compareWithX=" + compareWithX + ", rect[xmin,xmax]*[ymin,ymax]: " + rect.toString() +"]";
		}
		
		

		public double[] getXY() {
			double [] xy = {p.x(),p.y()};
			return xy;
		}
		
		public int compareTo(Node o) {
			if (o.getXY()[0] == p.x() && o.getXY()[1] == p.y()) {
				return 0;
			}
			if (o.compareWithX) {
				if (o.getXY()[0] <= p.x()) {//modified to <=
					return 1;
				} else if (o.getXY()[0] > p.x()){
					return -1;
				}
			} else {
				if (o.getXY()[1] <= p.y()) {//modified to >=
					return 1;
				} else if (o.getXY()[1] > p.y()){
					return -1;
				}
			}
			return 0; // never happen
			
		}
	}
	
	public boolean isEmpty() {
		return root==null;
	}
	
	public int size() {
		return size;
	}
		
	public void draw() {
		
	}
		
	private Iterable<Node> nodesList() {
		ArrayList<Node> al = new ArrayList<Node>();
		root = nodesList(root,al);
		return al;
	}
	
	private Node nodesList(Node node,ArrayList<Node> al) {
		if (node == null) {
			return node;
		}
		al.add(node);
		node.left = nodesList(node.left,al);
		node.right = nodesList(node.right,al);
		return node;
	}
	
	private void drawMine() {
		drawMine(root,"center");
	}
	
	private void drawMine(Node node, String location) {
		if (node == null) {
			return;
		}
		System.out.println(node + location);
		drawMine(node.left,"left");
		drawMine(node.right, "right");
		return;
		
	}
	
	public void insert(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		root = insert(root, root, p, 0);
		size++;
	}
	
	
	private Node insert(Node x, Node y, Point2D p, int d) {
		Node nodeNew = new Node(p);
		if (x == null) {
			RectHV rect;
			if (y == null) {
				rect = new RectHV(0,0,1,1);
				return new Node(p,true,rect);
			}
			if (d%2==0) {
				if (p.y() < y.p.y()) {
					rect = new RectHV(y.rect.xmin(),y.rect.ymin(),y.rect.xmax(),y.p.y());	
				} else {
					rect = new RectHV(y.rect.xmin(),y.p.y(),y.rect.xmax(),y.rect.ymax());
				}
				return new Node(p,true,rect);
				
			} else {
				if (p.x() < y.p.x()) {
					rect = new RectHV(y.rect.xmin(),y.rect.ymin(),y.p.x(),y.rect.ymax());	
				} else {
					rect = new RectHV(y.p.x(),y.rect.ymin(),y.rect.xmax(),y.rect.ymax());
				}
				return new Node(p,false,rect);
			}
		}
		int cmp = nodeNew.compareTo(x);
		if (cmp > 0) {
			x.right = insert(x.right,x,p,++d);
		} else if (cmp < 0) {
			x.left = insert(x.left,x,p,++d);
		} else {
			x.p = p;
			size--;
		}
		return x;


	}
	
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		Node newNode = new Node(p);
		return contains(root,newNode);
	}
	
	private boolean contains(Node x, Node newNode) {
		if (x == null) {
			return false;
		}
		int cmp = newNode.compareTo(x);
		if (cmp>0) {
			return contains(x.right,newNode);
		} else if (cmp<0) {
			return contains(x.left,newNode);
		} else {
			return true;
		}
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new IllegalArgumentException();
		}
		ArrayList<Point2D> al = new ArrayList<Point2D>();
		range(rect, root,al);
		return al;
	}
	
	private void range(RectHV rect, Node node, ArrayList<Point2D> al) {
		if (node == null) {
			return;
		}
		if (intersectPos(rect, node)<0) {
			range(rect,node.right,al);
		} else if (intersectPos(rect, node)>0) {
			range(rect,node.left,al);
		} else {
			if (rect.contains(node.p)) {
				al.add(node.p);
			}
			range(rect,node.left,al);
			range(rect,node.right,al);
		}
		return;
	}
	
	private int intersectPos(RectHV rect, Node node) {
		if (node.compareWithX) {
			if (rect.xmin()<=node.p.x() && rect.xmax()>=node.p.x()) {
				return 0;
			} else if (rect.xmin()>node.p.x()) {
				return -1;
			} else if (rect.xmax()<node.p.x()) {
				return 1;
			}
		} else {
			if (rect.ymin()<=node.p.y() && rect.ymax()>=node.p.y()) {
				return 0;
			} else if (rect.ymin()>node.p.y()) {
				return -1;
			} else if (rect.ymax()<node.p.y()) {
				return 1;
			}
		}
		return 0;//this is never gonna happen
	}
	
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		champ = null;
		distMin = Double.POSITIVE_INFINITY;
		nearest(root,p);

		return champ;
	}
	
	
	/* ARCHIVE
	private Point2D nearest(Node node, Point2D point, double distMin) {

		if (node == null) {
			return champ;
		}

		double distNow = node.p.distanceSquaredTo(point);
		if (distNow<distMin) {
			distMin = distNow;
			champ = node.p;
			if (pointPos(point,node)>0) {
				nearest(node.left,point,distMin);
				if (pointPosPos(point,node,champ)==0) {
					nearest(node.right,point,distMin);
				}
			} else {
				nearest(node.right,point,distNow);
				if (pointPosPos(point,node,champ)==0) {
					nearest(node.left,point,distMin);
				}
			}
		} else {
			if (pointPosPos(point,node,champ)==0) {
				nearest(node.left,point,distMin);
			}
			if (pointPosPos(point,node,champ)==0) {
				nearest(node.right,point,distMin);
			}
		}
		
		return champ;
	}
		
	private int pointPosPos(Point2D point, Node node, Point2D champ) {
		
		if (node.compareWithX) {
			if (champ.x()>=point.x() && champ.x()<node.p.x()) {
				return 1;
			} else if (champ.x()<=point.x() && champ.x()>node.p.x()) {
				return -1;
			} 
		} else {
			if (champ.y()>=point.y() && champ.y()<node.p.y()) {
				return 1;
			} else if (champ.y()<=point.y() && champ.y()>node.p.y()) {
				return -1;
			} 
		}
		//System.out.println("we should continue, since: goal: " + point + " now: " + node.p + " champ: " +champ);
		return 0; //this can happen!
	}
	*/
	
	/* new version
	private Point2D nearest(Node node, Point2D point) {

		if (node == null) {
			return champ;
		}

		double distNow = node.p.distanceSquaredTo(point);
		
		System.out.println("searching goal: " + point + " now: " + node.p + " champ: " +champ+" mindist: "+distMin+" newdist: "+distNow);

		if (distNow<distMin) {
			distMin = distNow;
			champ = node.p;
			System.out.println("NEW champ goal: " + point + " now: " + node.p + " champ: " +champ);
			
			if (pointPos(point,node)>0) {
				if (node.left != null) {
					System.out.println("turn left goal: " + point + " now: " + node.left.p + " champ: " +champ);
					
				}
				if (prune(point,node,champ) == 1) {
					nearest(node.left,point);
				} else if (prune(point,node,champ) == -1) {
					nearest(node.right,point);
				} else {
					nearest(node.left,point);
					nearest(node.right,point);
				}
			} else {
				System.out.println("turn right goal: " + point + " now: " + node.right.p + " champ: " +champ);
				if (prune(point,node,champ) == -1) {
					nearest(node.right,point);
				} else if (prune(point,node,champ) == 1) {
					nearest(node.left,point);
				} else {
					nearest(node.left,point);
					nearest(node.right,point);
				}
			}
		} else {
			if (prune(point,node,champ) == 1) {
				nearest(node.left,point);
			} else if (prune(point,node,champ) == -1) {
				nearest(node.right,point);
			} else {
				nearest(node.left,point);
				nearest(node.right,point);
			}
		}
		
		return champ;
	}
	*/
		
	//new version simplyfied
	private void nearest(Node node, Point2D point) {

		if (node == null) {
			return;
		}

		double distNow = node.p.distanceSquaredTo(point);
		
		//System.out.println("searching goal: " + point + " now: " + node.p + " champ: " +champ+" mindist: "+distMin+" newdist: "+distNow);

		if (distNow<distMin) {
			distMin = distNow;
			champ = node.p;
			//System.out.println("NEW champ goal: " + point + " now: " + node.p + " champ: " +champ);
		}
		
		if (pointPos(point,node)>0) {

			if (prune(point,node,champ)) {
				//System.out.println("turn left goal: " + point + " now: " + node.p + " champ: " +champ );	
				nearest(node.left,point);
				nearest(node.right,point);
			} 
			
		} else {
			
			if (prune(point,node,champ)) {
				//System.out.println("turn right goal: " + point + " now: " + node.p + " champ: " +champ );
				nearest(node.right,point);
				nearest(node.left,point);
			} 
		}
			

		return;
	}
	
	/*old version prune
	private int prune(Point2D point, Node node, Point2D champ) {
		
			
		if (node.compareWithX) {
			if (champ.distanceSquaredTo(point) < Math.pow((node.p.x() - point.x()), 2)) {
				if (node.p.x() > point.x()) {
					return 1;//no need to look at right tree
				} else if (node.p.x() < point.x()) {
					return -1;//no need to look at left tree
				} else {
					return 0;//==
				}
			} else {
				return 0; //need to look at both subtrees
			}
		} else {
			if (champ.distanceSquaredTo(point) < Math.pow((node.p.x() - point.x()), 2)) {
				if (node.p.y() > point.y()) {
					return 1;//no need to look at right tree
				} else if (node.p.y() <= point.y()) {
					return -1;//no need to look at left tree
				} else {
					return 0;//==
				}
			} else {
				return 0; //need to look at both subtrees
			}
		}
		//System.out.println("we should continue, since: goal: " + point + " now: " + node.p + " champ: " +champ);

	}
	*/
	
	private boolean prune(Point2D point, Node node, Point2D champ) {
		if (point.distanceSquaredTo(champ) < node.rect.distanceSquaredTo(point)) {
			return false;
		} else {
			return true;
		}
	}
	
	private int pointPos(Point2D query, Node node) {
		if (node.compareWithX) {
			if (query.x()<=node.p.x()) {
				return 1;
			} else if (query.x()>node.p.x()) {
				return -1;
			}
		} else {
			if (query.y()<=node.p.y()) {
				return 1;
			} else if (query.y()>node.p.y()) {
				return -1;
			} 
		}
		return 0;//this never happens
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KdTree kdt = new KdTree();
		Point2D p1 = new Point2D(0.7,0.2);
		Point2D p2 = new Point2D(0.5,0.4);
		Point2D p3 = new Point2D(0.2,0.3);
		Point2D p4 = new Point2D(0.4,0.7);
		Point2D p5 = new Point2D(0.9,0.6);
		Point2D  A  = new Point2D(0.25,0);
	    Point2D  B  = new Point2D(0.5, 1);
	    Point2D  C  = new Point2D(0.75, 1);
	    Point2D  D  = new Point2D(0.75, 0.75);
	    Point2D  E  = new Point2D(0.5, 0.25);
	    Point2D  F  = new Point2D(0.75, 0.25);
	    Point2D  G  = new Point2D(0, 1);
	    Point2D  H  = new Point2D(0, 0);
	    Point2D  I  = new Point2D(0.25, 0.25);
	    Point2D  J  = new Point2D(0.25, 1);
		RectHV rh = new RectHV(0,0,0.75,0.75);
		/*
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
		ps.drawMine();
		*/
		//System.out.println(ps.nearest(new Point2D(0,0.5)));

		kdt.insert(p1);
		kdt.insert(p2);
		kdt.insert(p3);
		kdt.insert(p4);
		//System.out.println("size " + kdt.size());
		kdt.insert(p5);
		kdt.drawMine();
		//System.out.println(kdt.range(rh));
		//System.out.println(kdt.nodesList());
		//System.out.println(kdt.contains(new Point2D(0.5,0.26)));
		System.out.println(kdt.nearest(new Point2D(0.379, 0.802)));
	
		
	}
	 

	/*
    public static void main(String[] args) {

        // initialize the two data structures with point from file
    	
    	String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }
        Point2D query = new Point2D(0.046, 0.075);
        System.out.println(kdtree.nearest(query));
        
        // process nearest neighbor queries
        //StdDraw.enableDoubleBuffering();
        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenRadius(0.01);
            StdDraw.text(x, y, x+", "+y);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            brute.nearest(query).draw();
            StdDraw.setPenRadius(0.02);

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenColor(StdDraw.BLUE);
            kdtree.nearest(query).draw();
            StdDraw.show();
            StdDraw.pause(40);
        }
        
    }
	*/




}
