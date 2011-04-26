/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.engine;

import au.com.noojee.battlefieldjava.occupants.Occupant;
import au.com.noojee.battlefieldjava.occupants.Piece;



/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Grid
{

	private Location[][]	locations;
	private int				rows;
	private int				columns;

	/**
	 * @param width
	 * @param height
	 */
	public Grid(int width, int height)
	{
		this.columns = width;
		this.rows = height;

		locations = new Location[width][height];
		// Now initialise each location

		for (int column = 0; column < width; column++)
		{
			for (int row = 0; row < height; row++)
			{
				locations[column][row] = new Location(column, row);
			}
		}
	}

	/**
	 * @param ruler
	 * @return
	 */
	public int getOwnedLandCount(Ruler ruler)
	{
		int count = 0;
		for (int column = 0; column < locations.length; column++)
		{
			for (int row = 0; row < locations[column].length; row++)
			{
				if (locations[column][row].getOwner() == ruler)
					count++;
			}
		}
		return count;
	}

	/**
	 * @return
	 */
	public int getHeight()
	{
		return rows;
	}

	/**
	 * @return
	 */
	public int getWidth()
	{
		return columns;
	}

	/**
	 * @return
	 */
	public Location[][] getLocations()
	{
		return locations;
	}

	/**
	 * @return
	 */
	public LocationIterator getLocationIterator()
	{
		return new LocationIterator(this);
	}

	public void setOccupant(int x, int y, Piece occupant)
	{
		locations[x][y].setOccupant(occupant);
	}

	public void setOccupant(Location location, Occupant occupant)
	{
		location.setOccupant(occupant);
	}
}