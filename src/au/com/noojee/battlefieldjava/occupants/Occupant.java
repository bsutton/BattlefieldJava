package au.com.noojee.battlefieldjava.occupants;

import java.awt.Image;
import java.io.PrintStream;

import au.com.noojee.battlefieldjava.engine.Direction;
import au.com.noojee.battlefieldjava.engine.Location;
import au.com.noojee.battlefieldjava.ruler.IOccupant;

/**
 * Anything that can occupy a postion on the battlefield.
 * 
 * @author bsutton
 * 
 */
public abstract class Occupant
{
	/** 
	 * Used to generate a unique id for each Occupant of the battlefield.
	 */
	static int idGenerator = 0;
	
	// This Occupants unique id.
	int id;
	
	// The location of the Occupant on the world grid.
	Location location;

	// The userOccupant interface used to control what access custom Ruler have to the engine. 
	private final IOccupant userOccupant;

	Occupant(IOccupant userOccupant)
	{
		this.userOccupant = userOccupant;
		this.id = idGenerator++;

	}
	
	public int getId()
	{
		return id;
	}

	public int getDistanceTo(int x, int y)
	{
		return location.getDistanceTo(x, y);
	}

	public int getDistanceTo(Location pnt)
	{
		return getDistanceTo(getX(), getY());
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see openruler.ruler.IObject#getDirectionTo(int, int)
	 */
	public Direction getDirectionTo(int x, int y)
	{
		return Direction.getDirectionTo(location.getX(), location.getY(), x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see openruler.ruler.IObject#getDirectionTo(Location)
	 */
	public Direction getDirectionTo(Location pnt)
	{
		return Direction.getDirectionTo(location.getX(), location.getY(), pnt.getX(), pnt.getY());
	}

	public static Direction getDirectionTo(int x1, int y1, int x2, int y2)
	{
		return Direction.getDirectionTo(x1, y1, x2, y2);
	}

	/**
	 * @param location
	 */
	public void setLocation(Location location)
	{
		this.location = location;
	}

	public Location getLocation()
	{
		return location;
	}

	public int getX()
	{
		return location.getX();
	}

	public int getY()
	{
		return location.getY();
	}

	abstract public Image getImage();

	public void dump(PrintStream out)
	{
		out.print(this.getClass().getSimpleName() + ": ID=" + getId() + " ");
		getLocation().dump(out);
		out.println();

	}

	abstract public String getTipText();

	public IOccupant getUserOccupant()
	{
		return userOccupant;
	}
}
