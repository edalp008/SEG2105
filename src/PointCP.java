// This file contains material supporting section 2.9 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

/**
 * This class contains instances of coordinates in either polar or
 * cartesian format.  It also provides the utilities to convert
 * them into the other type. It is not an optimal design, it is used
 * only to illustrate some design issues.
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge
 * @version July 2000
 */
public class PointCP
{
	private static final double DEGREE = 360;
	
  //Instance variables ************************************************

  /**
   * Contains P(olar) to identify the type of
   * coordinates that are being dealt with.
   */
  private char typeCoord;
  
  /**
   * Contains the current value of RHO
   */
  private double Rho;
  
  /**
   * Contains the current value of THETA
   */
  private double Theta;
	
  
  //Constructors ******************************************************

  /**
   * Constructs a coordinate object, with a type identifier.
   */
  public PointCP(char type, double xOrRho, double yOrTheta)
  {
    if(type != 'C' && type != 'P')
      throw new IllegalArgumentException();
    if (type == 'P') {
      Rho = xOrRho;
      Theta = yOrTheta;
    }
    else {
      Theta = Math.toDegrees(Math.atan2(yOrTheta, xOrRho));
      Rho = Math.sqrt(Math.pow(xOrRho, 2) + Math.pow(yOrTheta, 2));
    }
    typeCoord = type;
  }
	
  
  //Instance methods **************************************************
 
 
  public double getX()
  {
      return (Math.cos(Math.toRadians(Theta)) * Rho);
  }
  
  public double getY()
  {
      return (Math.sin(Math.toRadians(Theta)) * Rho);
  }
  
  public double getRho()
  {
      return Rho;
  }
  
  public double getTheta()
  {
      return Theta;
  }

  /**
   * Calculates the distance in between two points using the Pythagorean
   * theorem  (C ^ 2 = A ^ 2 + B ^ 2). Not needed until E2.30.
   *
   * @param pointA The first point.
   * @param pointB The second point.
   * @return The distance between the two points.
   */
  public double getDistance(PointCP pointB)
  {
    // Obtain differences in X and Y, sign is not important as these values
    // will be squared later.
    double deltaX = getX() - pointB.getX();
    double deltaY = getY() - pointB.getY();
    
    return Math.sqrt((Math.pow(deltaX, 2) + Math.pow(deltaY, 2)));
  }

  /**
   * Rotates the specified point by the specified number of degrees.
   * Not required until E2.30
   *
   * @param point The point to rotate
   * @param rotation The number of degrees to rotate the point.
   * @return The rotated image of the original point.
   */
  public PointCP rotatePoint(double rotation)
  {
    return new PointCP('P', Rho, (Theta + rotation)%DEGREE);
  }

  /**
   * Returns information about the coordinates.
   *
   * @return A String containing information about the coordinates.
   */
  public String toString()
  {
	  String str = "";
	  if (typeCoord == 'C') {
		  str = str + "Cartesian ";
	  }
	  else {
		  str = str + "Polar ";
	  }
	  str = str + "coordinates.\n";
	  str = str + "Stored as Polar [" + getRho() + "," + getTheta() + "]";
	  str = str + " with Cartesian calculated as (" + getX() + "," + getY() + ").\n";
      return str;
  }
}
