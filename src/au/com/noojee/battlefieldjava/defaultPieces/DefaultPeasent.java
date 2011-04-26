package au.com.noojee.battlefieldjava.defaultPieces;

import au.com.noojee.battlefieldjava.engine.Direction;
import au.com.noojee.battlefieldjava.engine.World;
import au.com.noojee.battlefieldjava.occupants.Piece;
import au.com.noojee.battlefieldjava.ruler.ILocation;
import au.com.noojee.battlefieldjava.ruler.IPeasant;
import au.com.noojee.battlefieldjava.ruler.IPiece;
import au.com.noojee.battlefieldjava.ruler.IWorld;


public class DefaultPeasent implements IPeasant
{
	private int id;
	
	private IWorld world;
	
	protected DefaultPeasent(IWorld world)
	{
		this.world = world;
	}

	public Direction getDirectionTo(int x, int y)
	{
		return Direction.getDirectionTo(getX(), getY(), x, y);
	}

	public Direction getDirectionTo(ILocation pnt)
	{
		return Direction.getDirectionTo(getX(), getY(), pnt.getX(), pnt.getY());
	}
	
	public Direction getDirectionTo(IPiece ipiece)
	{
		return getDirectionTo(ipiece.getLocation());
	}


	public int getDistanceTo(int x, int y)
	{
		return world.getLocation(this).getDistanceTo(x,y);
	}

	public int getDistanceTo(ILocation pnt)
	{
		return world.getLocation(this).getDistanceTo(pnt.getX(), pnt.getY());
	}
	
	public int getDistanceTo(IPiece ipiece)
	{
		return getDistanceTo(ipiece.getLocation());
	}


	public int getId()
	{
		return id;
	}


	public void move(Direction direction)
	{
		world.move(this, direction);
		
	}

	public ILocation getLocation()
	{
		return world.getLocation(this);
	}

	public boolean isAlive()
	{
		// If the piece is dead it won't have a location.
		return this.getLocation() != null && ((Piece)World.getOccupantAt(this.getLocation())).isAlive();
	}

	public int getX()
	{
		return getLocation().getX();
	}

	public int getY()
	{
		return getLocation().getY();
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public boolean hasMoved()
	{
		return world.hasMoved(this);
	}
}
