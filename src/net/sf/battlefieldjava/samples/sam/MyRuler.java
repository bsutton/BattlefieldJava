package net.sf.battlefieldjava.samples.sam;

import net.sf.battlefieldjava.samples.first.MyKnight;
import net.sf.battlefieldjava.samples.first.MyPeasant;
import au.com.noojee.battlefieldjava.defaultPieces.DefaultCastle;
import au.com.noojee.battlefieldjava.engine.Direction;
import au.com.noojee.battlefieldjava.engine.IllegalMoveError;
import au.com.noojee.battlefieldjava.engine.World;
import au.com.noojee.battlefieldjava.ruler.ICastle;
import au.com.noojee.battlefieldjava.ruler.IKnight;
import au.com.noojee.battlefieldjava.ruler.IPeasant;
import au.com.noojee.battlefieldjava.ruler.IPiece;
import au.com.noojee.battlefieldjava.ruler.IRuler;
import au.com.noojee.battlefieldjava.ruler.IWorld;

/**
 * This is the class that you must implement to enable your ruler within the
 * CodeRuler environment. Adding code to these methods will give your ruler its
 * personality and allow it to compete.
 */
public class MyRuler implements IRuler
{
	private final IWorld world;

	private int ThreatMap[][];

	private int CumulativeThreatMap[][];

	/**
	 * @param world
	 * @param color
	 */
	public MyRuler(IWorld world)
	{
		this.world = world;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.ruler.Ruler#getRulerName()
	 */
	public String getRulerName()
	{
		return "Robert V1";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.ruler.Ruler#getSchoolName()
	 */
	public String getSchoolName()
	{
		return "My School";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.ruler.Ruler#initialize()
	 */
	public void initialize()
	{
		// put implementation here

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.ruler.Ruler#orderSubjects(int)
	 */
	public void deployPieces(int lastMoveTime)
	{
		// put implementation here
		BuildThreatMaps();
		IPeasant enemyPeasants[] = world.getOtherPeasants(this);
		IPeasant myPeasants[] = world.getOwnedPeasants(this);
		ICastle myCastles[] = world.getCastles(this);
		ICastle enemyCastles[] = world.getOtherCastles(this);

		IKnight myKnights[] = world.getKnights(this);
		IKnight ememyKnights[] = world.getOtherKnights(this);
		if (myCastles.length > 0)
			if (myPeasants.length < 5)
				world.requestPeasants(myCastles[0]);
			else
				world.requestKnights(myCastles[0]);
		int range = 10000;
		int eKnight = -1;
		int d;
		Direction t;
		int s;
		for (int r = 0; r < 1 && r < myKnights.length && ememyKnights.length > 0; r++)
		{
			range = 10000;
			for (s = 0; s < ememyKnights.length; s++)
			{
				t = myKnights[r].getDirectionTo(ememyKnights[s]);
				d = myKnights[r].getDistanceTo(ememyKnights[s]);
				if (d < range)
				{
					range = d;
					eKnight = s;
				}
			}
			t = myKnights[r].getDirectionTo(ememyKnights[eKnight].getLocation());

			if (myCastles.length == 0)
			{
				try
				{
					if (myKnights[0].getDistanceTo(enemyCastles[0]) < 2)
						world.attack(myKnights[0], (myKnights[0].getDirectionTo(enemyCastles[0])));
					else
						world.move(myKnights[0], (myKnights[0].getDirectionTo(enemyCastles[0])));
				}
				catch (IllegalMoveError e)
				{
				}

			}
			else if (myKnights[r].getDistanceTo(myCastles[0]) < 10)
			{
				try
				{
					if (range < 2)
						world.attack(myKnights[r], t);
					else

						world.move(myKnights[r], t);
				}
				catch (IllegalMoveError e)
				{
				}
			}
			else
				try
				{
					world.move(myKnights[r], myKnights[r].getDirectionTo(myCastles[0]));
				}
				catch (IllegalMoveError e)
				{
				}
		}

		// Re-get the set of enemey knights as we may have killed one on the
		// first pass.
		ememyKnights = world.getOtherKnights(this);
		for (int r = 1; r < myKnights.length && ememyKnights.length > 0; r++)
		{
			range = 10000;

			for (s = 0; s < ememyKnights.length; s++)
			{
				if (ememyKnights[s].isAlive())
				{
					t = myKnights[r].getDirectionTo(ememyKnights[s]);
					d = myKnights[r].getDistanceTo(ememyKnights[s]);
					if (d < range)
					{
						range = d;
						eKnight = s;
					}
				}
			}
			t = myKnights[r].getDirectionTo(ememyKnights[eKnight]);

			// this usually means the knight is dead but we just checked for
			// that above?????
			if (t == null)
				continue;
			try
			{
				if (range < 2)
					world.attack(myKnights[r], t);
				else
					world.move(myKnights[r], t);
			}
			catch (IllegalMoveError e)
			{
			}
		}
		int ePeasant = -1;
		range = 10000;
		d = 1000;
		for (int r = 2; r < myKnights.length && enemyPeasants.length > 0; r++)
		{
			range = 10000;
			for (s = 0; s < enemyPeasants.length; s++)
			{
				if (enemyPeasants[s].isAlive())
				{
					t = myKnights[r].getDirectionTo(enemyPeasants[s]);
					d = myKnights[r].getDistanceTo(enemyPeasants[s]);
					if (d < range)
					{
						range = d;
						ePeasant = s;
					}
				}
			}
			if (eKnight != -1)
			{
				t = myKnights[r].getDirectionTo(enemyPeasants[ePeasant]);
				try
				{
					if (range < 2)
						world.attack(myKnights[r], t);
					else
						world.move(myKnights[r], t);

				}
				catch (IllegalMoveError e)
				{
				}
			}
		}

		for (int r = 0; r < myPeasants.length; r++)
		{
			t = DirectionToSafetyFrom(myPeasants[r].getX(), myPeasants[r].getY());
			try
			{
				if (t == Direction.MOVE_NONE)
				{
					int px = myPeasants[r].getX();
					int py = myPeasants[r].getY();
					int x;
					int y;
					for (x = -1; x < 2; x++)
						for (y = -1; y < 2; y++)
							try
							{
								if (world.getLandOwner(px + x, py + y) != this)
									t = myPeasants[r].getDirectionTo(px + x, py + y);
							}
							catch (Throwable e1)
							{
							}
					if (t == Direction.MOVE_NONE)
						world.move(myPeasants[r], Direction.random());
					else
						world.move(myPeasants[r], t);
				}
				else
					world.move(myPeasants[r], t);
			}
			catch (IllegalMoveError e)
			{
			}
		}
	}

	private Direction DirectionToSafetyFrom(int x, int y)
	{
		/*
		 * returns the direction to move from the given coordiates to stay safe
		 * first check cumulative threat map, then if there is a problem check
		 * the threat map
		 */
		Direction direction = Direction.MOVE_NONE;
		int cx = x / 5;
		int cy = y / 5;
		if (CumulativeThreatMap[cx][cy] == 0)
		{
			// no enemy here, check around on cumulative threat map
			x = x / 5;
			y = y / 5;
			if (x > 0 && x < (World.WIDTH / 5) - 1 && y > 0 && y < (World.HEIGHT / 5) - 1)
			{
				if (CumulativeThreatMap[x][y + 1] != 0)
					direction = Direction.MOVE_N;
				if (CumulativeThreatMap[x - 1][y + 1] != 0)
					direction = Direction.MOVE_NE;
				if (CumulativeThreatMap[x - 1][y] != 0)
					direction = Direction.MOVE_E;
				if (CumulativeThreatMap[x - 1][y - 1] != 0)
					direction = Direction.MOVE_SE;
				if (CumulativeThreatMap[x][y - 1] != 0)
					direction = Direction.MOVE_S;
				if (CumulativeThreatMap[x + 1][y - 1] != 0)
					direction = Direction.MOVE_SW;
				if (CumulativeThreatMap[x + 1][y] != 0)
					direction = Direction.MOVE_W;
				if (CumulativeThreatMap[x + 1][y + 1] != 0)
					direction = Direction.MOVE_NW;
			}

		}
		else
		{
			// enemy very near, check here.
			if (x > 0 && x < World.WIDTH - 1 && y > 0 && y < World.HEIGHT - 1)
			{
				if (ThreatMap[x][y + 1] != 0)
					direction = Direction.MOVE_N;
				if (ThreatMap[x - 1][y + 1] != 0)
					direction = Direction.MOVE_NE;
				if (ThreatMap[x - 1][y] != 0)
					direction = Direction.MOVE_E;
				if (ThreatMap[x - 1][y - 1] != 0)
					direction = Direction.MOVE_SE;
				if (ThreatMap[x][y - 1] != 0)
					direction = Direction.MOVE_S;
				if (ThreatMap[x + 1][y - 1] != 0)
					direction = Direction.MOVE_SW;
				if (ThreatMap[x + 1][y] != 0)
					direction = Direction.MOVE_W;
				if (ThreatMap[x + 1][y + 1] != 0)
					direction = Direction.MOVE_NW;
			}
		}
		return direction;

	}

	private void BuildThreatMaps()
	{
		/*
		 * Build the threat map, its simply a 1 for a knight or a 0 for nothing.
		 */

		ThreatMap = new int[World.WIDTH][World.HEIGHT];
		IKnight enemyKnights[] = world.getOtherKnights(this);
		for (int r = 0; r < enemyKnights.length; r++)
			ThreatMap[enemyKnights[r].getX()][enemyKnights[r].getY()] = 1;
		/*
		 * Build the cumulative threat map Survey the cumulative threat for an
		 * area of five squares the value stored is equal to the number of
		 * knights.
		 */
		int CumX = (World.WIDTH / 5) + 1;
		int CumY = (World.HEIGHT / 5) + 1;
		CumulativeThreatMap = new int[(World.WIDTH / 5) + 1][(World.HEIGHT / 5) + 1];
		for (int x = 0; x < CumX; x++)
		{
			for (int y = 0; y < CumY; y++)
			{
				for (int x1 = 0; x1 < 5 && x1 + (x * 5) < World.WIDTH; x1++)
					for (int y1 = 0; y1 < 5 && y1 + (y * 5) < World.HEIGHT; y1++)
					{
						CumulativeThreatMap[x][y] += ThreatMap[x1 + (x * 5)][y1 + (y * 5)];
					}
				// System.out.print(CumulativeThreatMap[x][y]);
			}
			// System.out.println("");
		}
	}

	public void notifyCapture(IPiece captor, IPiece captee)
	{
		// TODO Auto-generated method stub

	}

	public ICastle createCastle(IWorld world)
	{
		return new DefaultCastle(world);
	}

	public IKnight createKnight(IWorld world)
	{
		return new MyKnight(world);
	}

	public IPeasant createPeasant(IWorld world)
	{
		return new MyPeasant(world);
	}
}