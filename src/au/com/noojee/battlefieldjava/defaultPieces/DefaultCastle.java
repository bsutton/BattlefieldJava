package au.com.noojee.battlefieldjava.defaultPieces;

import java.awt.Image;

import au.com.noojee.battlefieldjava.engine.Direction;
import au.com.noojee.battlefieldjava.ruler.ICastle;
import au.com.noojee.battlefieldjava.ruler.ILocation;
import au.com.noojee.battlefieldjava.ruler.IPiece;
import au.com.noojee.battlefieldjava.ruler.IWorld;

public class DefaultCastle implements ICastle
{
	private int id;
	
	private IWorld world;

	public DefaultCastle(IWorld world)
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

	public Image getImage()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void startRulerTurn()
	{
		// TODO Auto-generated method stub

	}

	public String getTipText()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isAlive()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void createKnights()
	{
		// TODO Auto-generated method stub

	}

	public void createPeasants()
	{
		// TODO Auto-generated method stub

	}

	public int getX()
	{
		return getLocation().getX();
	}

	public int getY()
	{
		return getLocation().getY();
	}

	public ILocation getLocation()
	{
		return world.getLocation(this);
	}

	public void setId(int id)
	{
		this.id = id;
	}

}
