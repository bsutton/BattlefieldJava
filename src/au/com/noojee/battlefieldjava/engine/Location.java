/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.engine;

import java.io.PrintStream;

import au.com.noojee.battlefieldjava.occupants.Occupant;
import au.com.noojee.battlefieldjava.occupants.Piece;
import au.com.noojee.battlefieldjava.ruler.ILocation;

/**
 * @author bsutton
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class Location
{
	// The Location x,y co-ordinates on the grid.
	private int y;
	private int x;

	// The ruler that currently owns this location, null if its not currently
	// owned.
	//
	private Ruler owner;

	// The current occupant of this location. If no occupent then null
	private Occupant occupant = null;

	/**
	 * @param i
	 * @param j
	 */
	public Location(int x, int y)
	{
		this.y = y;
		this.x = x;
	}

	void setOwner(Ruler ruler)
	{
		owner = ruler;
	}

	public void setOccupant(Occupant occupant2)
	{
		occupant = occupant2;

		if (occupant instanceof Piece)
		{
			Piece piece = (Piece) occupant;
			// If someone occupies a location the implication is that they own
			// it.
			owner = piece.getRuler();
		}

		// And tell the occupant where they are
		occupant2.setLocation(this);

	}

	public void clearOccupant()
	{
		occupant = null;
	}

	/**
	 * @return
	 */
	public Ruler getOwner()
	{
		return owner;
	}

	/**
	 * @return
	 */
	public Occupant getOccupant()
	{
		return occupant;
	}

	/**
	 * @return
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @return
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * 
	 */
	public void refresh()
	{
		World.getInstance().refresh(this);
	}

	public String toString()
	{
		String result = "[" + getX() + ", " + getY();

		if (owner != null)
			result += "," + owner.getRulerName();

		result += "]";

		return result;
	}

	/**
	 * @return
	 */
	public void dump(PrintStream out)
	{
		out.print(toString());
	}

	public ILocation getUserLocation()
	{
		return new ILocationImpl(this);
	}

	public int getDistanceTo(int x2, int y2)
	{
		double a = Math.abs(x2 - getX());
		double b = Math.abs(y2 - getY());
		double c = Math.sqrt(Math.pow(a, 2.0) + Math.pow(b, 2.0));
		return (int) Math.round(c);
	}
}
