package au.com.noojee.battlefieldjava.engine;

import au.com.noojee.battlefieldjava.ruler.ILocation;
import au.com.noojee.battlefieldjava.ruler.IPiece;
import au.com.noojee.battlefieldjava.ruler.IRuler;

public class ILocationImpl implements ILocation
{
	private Location location;
	

	public ILocationImpl(Location location)
	{
		this.location = location;
	}

	public IRuler getOwner()
	{
		Ruler owner = location.getOwner();
		return (owner == null ? null : location.getOwner().getUserRuler());
	}

	public int getY()
	{
		return location.getY();
	}

	public int getX()
	{
		return location.getX();
	}

	public int getDistanceTo(int x, int y)
	{
		return location.getDistanceTo(x, y);
	}
	
	
	public int getDistanceTo(ILocation pnt)
	{
		return getDistanceTo(pnt.getX(), pnt.getY());
	}

	public int getDistanceTo(IPiece ipiece)
	{
		return getDistanceTo(ipiece.getLocation());
	}


}
