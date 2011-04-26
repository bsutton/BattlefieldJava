/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.engine;

import java.util.Iterator;



/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LocationIterator implements Iterator<Location>
{
	Grid grid;
	
	int y = 0;
	int x = 0;
	
	LocationIterator(Grid grid)
	{
		this.grid = grid;
	}
	
	
	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() 
	{
		throw new UnsupportedOperationException();
		
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext()
	{
		boolean hasNext = false;
		if (y < grid.getHeight() - 1 
				|| (y == grid.getHeight() - 1 &&  x <= grid.getWidth()))
			hasNext = true;
		
		return hasNext;
	}


	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public Location next()
	{
		Location location = grid.getLocations()[x][y];
		x++;
		if (x >= grid.getWidth())
		{
			x = 0;
			y++;
		}
		return location;
	}

}
