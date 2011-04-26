/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.ruler;

import au.com.noojee.battlefieldjava.engine.Direction;

/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IPiece extends IOccupant
{

	/**
	 * Returns the  direction to the given position from this IPiece.
	 * Note: the direction is an approximation and the closest match of the 
	 * eight valid directions is returned.
	 *
	 * @param int row
	 * @param int column
	 * @return int
	 */
	public Direction getDirectionTo(int x, int y);


	/**
	 * Returns the  direction to the given position from this IObject.
	 * Note: the direction is an approximation and the closest match of the 
	 * eight valid directions is returned.
	 *
	 * @param Point pnt
	 * @return Direction
	 */
	public Direction getDirectionTo(ILocation iLocation);
	
	public Direction getDirectionTo(IPiece ipiece);

	/**
	 * Returns the number of squares between this Object and the indicated position.
	 * i.e. the number of moves it would take to reach the object.
	 * 
	 * @param int row
	 * @param int column
	 * @return int
	 */
	public int getDistanceTo(int x, int y);

	/**
	 * Returns the number of squares between this Object and the indicated position.
	 * i.e. the number of moves it would take to reach the object.
	 * 
	 * @param int x
	 * @param int y
	 * @return int
	 */
	public int getDistanceTo(ILocation pnt);
	
	public int getDistanceTo(IPiece ipiece);

	
	/**
	 * @return if the piece is still alive (independant of who now owns it.
	 */
	boolean isAlive();
	
	ILocation getLocation();

	int getX();

	int getY();


}
