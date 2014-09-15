import java.util.Random;

public class TestDesign {
	
	private static final double DEGREE = 360;
	private static final double MAX_DISTANCE = 100;
	
	//measures the time it takes to instantiate a point with Cartesian coordinates
	public static long testInstantiationCartesian (int n) {
		Random rand = new Random();
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i ++) {
			new PointCP ('C', rand.nextDouble()*MAX_DISTANCE, rand.nextDouble()*MAX_DISTANCE);
		}
		return System.currentTimeMillis() - start;
	}
	
	//measures the time it takes to instantiate a point with polar coordinates
	public static long testInstantiationPolar (int n) {
		Random rand = new Random();
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i ++) {
			new PointCP ('P', rand.nextDouble()*MAX_DISTANCE, rand.nextDouble()*DEGREE);
		}
		return System.currentTimeMillis() - start;
	}
	
	//measures the time it takes to return Cartesian coordinates
	//creates a new instance with Cartesian coordinates every time
	public static long testCartesianCartesian (int n) {
		Random rand = new Random();
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i ++) {
			PointCP pt = new PointCP ('C', rand.nextDouble()*MAX_DISTANCE, rand.nextDouble()*MAX_DISTANCE);
			pt.getX();
			pt.getY();
		}
		return System.currentTimeMillis() - start;
	}
	
	//measures the time it takes to return Cartesian coordinates
	//creates a new instance with polar coordinates every time
	public static long testCartesianPolar (int n) {
		Random rand = new Random();
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i ++) {
			PointCP pt = new PointCP ('P', rand.nextDouble()*MAX_DISTANCE, rand.nextDouble()*DEGREE);
			pt.getX();
			pt.getY();
		}
		return System.currentTimeMillis() - start;
	}
	
	//measures the time it takes to return polar coordinates
	//creates a new instance with Cartesian coordinates every time
	public static long testPolarCartesian (int n) {
		Random rand = new Random();
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i ++) {
			PointCP pt = new PointCP ('C', rand.nextDouble()*MAX_DISTANCE, rand.nextDouble()*MAX_DISTANCE);
			pt.getRho();
			pt.getTheta();
		}
		return System.currentTimeMillis() - start;
	}
	
	//measures the time it takes to return polar coordinates
	//creates a new instance with polar coordinates every time
	public static long testPolarPolar (int n) {
		Random rand = new Random();
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i ++) {
			PointCP pt = new PointCP ('P', rand.nextDouble()*MAX_DISTANCE, rand.nextDouble()*DEGREE);
			pt.getRho();
			pt.getTheta();
		}
		return System.currentTimeMillis() - start;
	}
	
	//measures time to calculate the distance between points
	//creates two new instances with Cartesian coordinates every time
	public static long testDistanceCartesian (int n) {
		Random rand = new Random();
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i ++) {
			PointCP pt = new PointCP ('C', rand.nextDouble()*MAX_DISTANCE, rand.nextDouble()*MAX_DISTANCE);
			PointCP pt2 = new PointCP ('C', rand.nextDouble()*MAX_DISTANCE, rand.nextDouble()*MAX_DISTANCE);
			pt.getDistance(pt2);
		}
		return System.currentTimeMillis() - start;
	}
	
	//measures time to calculate the distance between points
	//creates two new instances with polar coordinates every time
	public static long testDistancePolar (int n) {
		Random rand = new Random();
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i ++) {
			PointCP pt = new PointCP ('P', rand.nextDouble()*MAX_DISTANCE, rand.nextDouble()*DEGREE);
			PointCP pt2 = new PointCP ('P', rand.nextDouble()*MAX_DISTANCE, rand.nextDouble()*DEGREE);
			pt.getDistance(pt2);
		}
		return System.currentTimeMillis() - start;
	}
	
	//measures the time to return the rotation
	//creates a new instance with Cartesian coordinates every time
	public static long testRotationCartesian (int n) {
		Random rand = new Random();
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i ++) {
			PointCP pt = new PointCP ('C', rand.nextDouble()*MAX_DISTANCE, rand.nextDouble()*MAX_DISTANCE);
			pt.rotatePoint(rand.nextDouble()*DEGREE);
		}
		return System.currentTimeMillis() - start;
	}
	
	//measures the time to return the rotation
	//creates a new instance with polar coordinates every time
	public static long testRotationPolar (int n) {
		Random rand = new Random();
		long start = System.currentTimeMillis();
		for (int i = 0; i < n; i ++) {
			PointCP pt = new PointCP ('P', rand.nextDouble()*MAX_DISTANCE, rand.nextDouble()*DEGREE);
			pt.rotatePoint(rand.nextDouble()*DEGREE);
		}
		return System.currentTimeMillis() - start;
	}

	//prints the results
	public static void main (String[] args) {
		final int ITER = 1000000;
		
		System.out.println("Testing instantiations-cartesian : " + testInstantiationCartesian(ITER) + "ms");
		System.out.println("Testing instantiations-polar: " + testInstantiationPolar(ITER) + "ms");
		System.out.println("Testing cartesian-cartesian: " + testCartesianCartesian(ITER) + "ms");
		System.out.println("Testing cartesian-polar: " + testCartesianPolar(ITER) + "ms");
		System.out.println("Testing polar-cartesian: " + testPolarCartesian(ITER) + "ms");
		System.out.println("Testing polar-polar: " + testPolarPolar(ITER) + "ms");
		System.out.println("Testing distance-cartesian: " + testDistanceCartesian(ITER) + "ms");
		System.out.println("Testing distance-polar: " + testDistancePolar(ITER) + "ms");
		System.out.println("Testing rotation-cartesian: " + testRotationCartesian(ITER) + "ms");
		System.out.println("Testing rotation-polar: " + testRotationPolar(ITER) + "ms");
	}
}
