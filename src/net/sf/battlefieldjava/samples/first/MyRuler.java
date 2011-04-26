package net.sf.battlefieldjava.samples.first;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;

import au.com.noojee.battlefieldjava.defaultPieces.DefaultCastle;
import au.com.noojee.battlefieldjava.engine.Direction;
import au.com.noojee.battlefieldjava.engine.GameSettings;
import au.com.noojee.battlefieldjava.engine.IllegalMoveError;
import au.com.noojee.battlefieldjava.engine.World;
import au.com.noojee.battlefieldjava.ruler.ICastle;
import au.com.noojee.battlefieldjava.ruler.IKnight;
import au.com.noojee.battlefieldjava.ruler.ILocation;
import au.com.noojee.battlefieldjava.ruler.IMobilePiece;
import au.com.noojee.battlefieldjava.ruler.IOccupant;
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

	Random random = new Random();
	// Map of strengths for each knight at the start of the prior round
	HashMap<IKnight, Integer> strengths = new HashMap<IKnight, Integer>();
	private IWorld world;
	private static final int ATTACK_RATIO = 2;
	private static final int CALVERY_STRENGTH = 20;

	private boolean attacked = false;

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
		return "Brett";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.ruler.Ruler#getSchoolName()
	 */
	public String getSchoolName()
	{
		return "Slayer";
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
		this.attacked = false;

		// Check if we have been under attack
		// If so start creating knights
		ICastle[] castles = world.getCastles(this);
		for (int i = 0; i < castles.length; i++)
		{
			ICastle castle = castles[i];

			IKnight[] knights = world.getKnights(this);
			if (this.attacked || knights.length < CALVERY_STRENGTH)
				world.requestKnights(castle);
			else
				world.requestPeasants(castle);
		}

		boolean peasantMovesLeft = true;
		boolean knightMovesLeft = true;
		// Sometimes a Piece can be block by one of our own pieces
		// as such we need to try to make a move in cycles
		// as once the first guys have moved out of the way the others can
		// follow
		while (peasantMovesLeft || knightMovesLeft)
		{
			peasantMovesLeft = deployPeasants();

			knightMovesLeft = deployKnights();
		}

	}

	private boolean deployKnights()
	{
		boolean knightMovesLeft = false;
		boolean moved = false;

		IKnight[] knights = world.getKnights(this);

		for (int i = 0; i < knights.length; i++)
		{
			IKnight knight = knights[i];

			if (knight.hasMoved())
				continue;

			Integer strength = strengths.get(knight);

			if (knight != null && strength != null && knight.getStrength() < strength.intValue())
				this.attacked = true;

			strengths.put(knight, new Integer(knight.getStrength()));
			Direction dir = Direction.random();
			ILocation loc = world.getLocation(knight);
			Point np = World.getPositionAfterMove(loc.getX(), loc.getY(), dir);
			// Make certain we are bumping into the edge of the map;
			if (np == null || np.getX() < 0 || np.getY() < 0 || np.getX() >= GameSettings.getWIDTH()
					|| np.getY() >= GameSettings.getHEIGHT())
				dir = dir.rotate180();

			// check for the weakest enemy in an adjacent square
			IPiece enemy = findWeakestAdjacentEnemy(knight);
			if (enemy != null)
			{
				dir = getBestDirectionTo(knight, world.getLocation(enemy));

				// Check we can actually move
				if (dir == Direction.MOVE_NONE)
				{
					knightMovesLeft = true;
					break;
				}

				if (this.isAdjacent(knight, enemy))
				{
					try
					{
						world.attack(knight, dir);
					}
					catch (IllegalMoveError e)
					{
						e.printStackTrace();
					}

				}
				else if (enemy instanceof IKnight && this.isWithinStrikingDistance(knight, enemy))
				{
					// Check if we have company
					// if (this.areComradesWithinStrikingDistance(knight,
					// enemy))
					try
					{
						world.move(knight, dir);
						moved = true;
					}
					catch (IllegalMoveError e)
					{
						e.printStackTrace();
					}
					// else
					// flee(knight, enemy);
				}
				else
				{
					try
					{
						world.move(knight, dir);
						moved = true;
					}
					catch (IllegalMoveError e)
					{
						e.printStackTrace();
					}

				}
			}
			else
			{
				ICastle castle = null;
				// Now check if any enemy knights left
				if (World.getOtherKnights().length == 0)
				{
					// No so attack the castle
					castle = getNearestEnemyCastle(knight);
					if (castle != null)
						dir = getBestDirectionTo(knight, world.getLocation(castle));
				}

				if (castle != null && isAdjacent(knight, castle))
				{
					try
					{
						world.attack(knight, dir);
						moved = true;
					}
					catch (IllegalMoveError e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					if (world.getLocationAfterMove(world.getLocation(knight), dir) == null)
						dir = dir.rotate180();
					try
					{
						world.move(knight, dir);
						moved = true;
					}
					catch (IllegalMoveError e)
					{
						e.printStackTrace();
					}

				}
			}
		}
		return moved && knightMovesLeft;
	}

	private boolean deployPeasants()
	{
		boolean peasantMovesLeft = false;
		boolean moved = false;

		/**
		 * Track the locations that have been targeted so we don't send two
		 * peasants to the same location.
		 * 
		 */
		HashSet<Integer> targets = new HashSet<Integer>();

		IPeasant[] peasants = world.getOwnedPeasants(this);
		for (int i = 0; i < peasants.length; i++)
		{
			IPeasant peasant = peasants[i];
			if (peasant.hasMoved())
				continue;

			ILocation location = findNearestUnownedLand(peasant.getLocation(), targets);
			if (location != null)
			{
				Direction dir = getBestDirectionTo(peasant, location);
				if (dir != Direction.MOVE_NONE)
				{
					try
					{
						world.move(peasant, dir);
					}
					catch (IllegalMoveError e)
					{
						e.printStackTrace();
					}
					moved = true;
				}
				else
					peasantMovesLeft = true;
			}
			else
				peasantMovesLeft = true;
		}

		return moved && peasantMovesLeft;
	}

	/**
	 * Finds the nearest piece of unowned land.
	 * 
	 * @param exclusions
	 *            the set of locations to exclude from the search.
	 * 
	 * @param peasant
	 * @return
	 */
	private ILocation findNearestUnownedLand(ILocation iLocation, HashSet<Integer> exclusions)
	{
		boolean found = false;
		ILocation target = null;
		ILocation temp = null;

		// work out in a spiral looking from the unowned laned.

		boolean unownedRemain = checkForUnowned(exclusions);

		if (unownedRemain)
		{
			int width = 3;
			int height = 3;

			// int maxSpiralRadius = Math.max(GameSettings.getWIDTH(),
			// GameSettings.getHEIGHT()) * 3;
			// Search every square.
			int maxSpiralRadius = GameSettings.getWIDTH() * GameSettings.getHEIGHT();

			// Origin of grid is top/left corner (0,0).
			int x = 0;
			int y = 0;

			for (int i = 0; i < maxSpiralRadius; i++)
			{
				width++;
				height++;

				int xoffset = (width / 2);
				int yoffset = (height / 2);

				// scan the top of the square
				y = -yoffset;
				for (x = -xoffset; x <= xoffset; x++)
				{
					if (isValid(iLocation.getX() + x, iLocation.getY() + y))
					{
						temp = world.getLocationAt(iLocation.getX() + x, iLocation.getY() + y);

						if (!isOccupied(temp) && temp.getOwner() != this && !exclusions.contains(temp))
						{
							found = true;
							break;
						}
					}
				}

				if (found)
					break;

				// scan the right side of the square.
				x = xoffset;
				for (y = 0; y < height; y++)
				{
					if (isValid(iLocation.getX() + x, iLocation.getY() + y))
					{
						temp = world.getLocationAt(iLocation.getX() + x, iLocation.getY() + y);
						if (!isOccupied(temp) && temp.getOwner() != this && !exclusions.contains(temp))
						{
							found = true;
							break;
						}
					}
				}

				if (found)
					break;

				// scan the bottom of the square
				y = yoffset;
				for (x = -xoffset; x <= xoffset; x++)
				{
					if (isValid(iLocation.getX() + x, iLocation.getY() + y))
					{
						temp = world.getLocationAt(iLocation.getX() + x, iLocation.getY() + y);
						if (!isOccupied(temp) && temp.getOwner() != this && !exclusions.contains(temp))
						{
							found = true;
							break;
						}
					}
				}

				if (found)
					break;

				// scan the left side of the cube
				x = -xoffset;
				for (y = -yoffset; y <= yoffset; y++)
				{
					if (isValid(xoffset + x, yoffset + y))
					{
						temp = world.getLocationAt(xoffset + x, yoffset + y);
						if (!isOccupied(temp) && temp.getOwner() != this && !exclusions.contains(temp))
						{
							found = true;
							break;
						}
					}
				}
				if (found)
					break;

			}

		}
		if (found)
			target = temp;

		return target;
	}

	private boolean checkForUnowned(HashSet<Integer> exclusions)
	{
		boolean found= false;
		
		// First work out if there is actually any unowned land
		for (int i = 0; i < GameSettings.getWIDTH(); i++)
		{
			for (int j = 0; j < GameSettings.getHEIGHT(); j++)
			{
				ILocation temp = world.getLocationAt(i,j);
				if (!isOccupied(temp) && temp.getOwner() != this && !exclusions.contains(temp))
				{
					found = true;
					break;
				}
			}
			if (found)
				break;
		}
		return found;
	}

	/**
	 * @param i
	 * @param j
	 * @return
	 */
	private boolean isValid(int x, int y)
	{
		boolean valid = false;

		if (x >= 0 && x < GameSettings.getWIDTH() && y >= 0 && y < GameSettings.getHEIGHT())
			valid = true;
		return valid;
	}

	/**
	 * @param i
	 * @return
	 */
	protected int clipY(int y)
	{
		return Math.min(GameSettings.getWIDTH() - 1, Math.max(0, y));
	}

	/**
	 * @param i
	 * @return
	 */
	protected int clipX(int x)
	{
		return Math.min(GameSettings.getHEIGHT() - 1, Math.max(0, x));
	}

	/**
	 * Determines the best direction to the target. Basically tries the most
	 * direct path and if block will try and go around.
	 * 
	 * @param knight
	 * @param enemy
	 * @return
	 */
	private Direction getBestDirectionTo(IMobilePiece subject, ILocation target)
	{
		Direction dir = subject.getDirectionTo(target);
		ILocation location = world.getLocationAfterMove(world.getLocation(subject), dir);
		int count = 8;
		while (location != target && isOccupied(location) && count > 0)
		{
			dir = dir.rotate45();
			location = world.getLocationAfterMove(world.getLocation(subject), dir);
			count--;
		}

		// Final check to see if we found a path (we may be completely block)
		// If we are block then don't bother moving.
		location = world.getLocationAfterMove(world.getLocation(subject), dir);
		if (isOccupied(location))
			dir = Direction.MOVE_NONE;

		return dir;
	}

	/**
	 * @param knight
	 * @param enemy
	 * @return Check if the enemy is within striking distance i.e. no more than
	 *         one clear space away.
	 */
	private boolean isWithinStrikingDistance(IKnight subject, IPiece enemy)
	{
		boolean adjacent = false;
		if (subject != null && enemy != null)
		{
			ILocation loc = world.getLocation(subject);
			Point pntSubject = new Point(loc.getX(), loc.getY());

			ILocation locEnemy = world.getLocation(enemy);
			Point pntEnemy = new Point(locEnemy.getX(), locEnemy.getY());

			if (Math.abs(pntSubject.x - pntEnemy.x) <= 2 && Math.abs(pntSubject.y - pntEnemy.y) <= 2)
				adjacent = true;
		}
		return adjacent;
	}

	/**
	 * @param knight
	 */
	public void flee(IKnight knight, IPiece enemy)
	{
		ILocation locEnemy = world.getLocation(enemy);
		Direction dir = knight.getDirectionTo(locEnemy.getX(), locEnemy.getY());

		world.move(knight, dir.rotate180());
	}

	/**
	 * Returns true if the location is occupied or null. If the location is
	 * empty then false is returned.
	 * 
	 * @param temp
	 * @return
	 */
	private boolean isOccupied(ILocation temp)
	{
		boolean occupied = false;
		if (temp != null)
		{
			IOccupant object = world.getOccupant(temp);
			if (object != null)
				occupied = true;
		}
		else
			occupied = true;
		return occupied;
	}

	/**
	 * @param knight
	 * @param enemy
	 * @return
	 */
	public boolean areComradesWithinStrikingDistance(IKnight knight, IPiece enemy)
	{
		boolean bComradesAdjacent = false;
		IKnight[] comrades = world.getKnights(this);

		int adjacentComrades = 0;
		for (int i = 0; i < comrades.length; i++)
		{
			IKnight comrade = comrades[i];
			if (isWithinStrikingDistance(comrade, enemy))
				adjacentComrades++;
		}

		if (adjacentComrades > ATTACK_RATIO)
			bComradesAdjacent = true;
		return bComradesAdjacent;
	}

	/**
	 * @param enemy
	 *            Check if any other knights are adjacent to the enemy.
	 * @return
	 */
	public boolean areComradesAdjacent(IKnight knight, IPiece enemy)
	{
		boolean bComradesAdjacent = false;
		IKnight[] comrades = world.getKnights(this);

		int adjacentComrades = 0;
		for (int i = 0; i < comrades.length; i++)
		{
			IKnight comrade = comrades[i];
			if (isAdjacent(comrade, enemy))
				adjacentComrades++;
		}

		if (adjacentComrades > ATTACK_RATIO)
			bComradesAdjacent = true;
		return bComradesAdjacent;
	}

	/**
	 * @return
	 */
	private ICastle getNearestEnemyCastle(IKnight knight)
	{
		ICastle castle = null;
		ICastle[] castles = world.getOtherCastles(this);

		for (int i = 0; i < castles.length; i++)
		{
			if (castles[i].isAlive() && this.getDistanceTo(knight, castles[i]) < this.getDistanceTo(knight, castle))
				castle = castles[i];
		}
		return castle;
	}

	/**
	 * @param knights
	 */
	public void dump(IKnight[] knights)
	{
		for (int i = 0; i < knights.length; i++)
		{
			IKnight knight = knights[i];
			System.out.println(knight.toString());
		}

	}

	/**
	 * @param knight
	 * @param enemy
	 * @return
	 */
	private boolean isAdjacent(IPiece subject, IPiece enemy)
	{
		boolean adjacent = false;
		if (subject != null && enemy != null)
		{
			ILocation loc = world.getLocation(subject);
			Point pntSubject = new Point(loc.getX(), loc.getY());

			ILocation locEnemy = world.getLocation(enemy);
			Point pntEnemy = new Point(locEnemy.getX(), locEnemy.getY());

			if (Math.abs(pntSubject.x - pntEnemy.x) <= 1 && Math.abs(pntSubject.y - pntEnemy.y) <= 1)
				adjacent = true;
		}
		return adjacent;

	}

	/**
	 * @param knight
	 * @return
	 */
	private IPiece findWeakestAdjacentEnemy(IKnight knight)
	{
		IPiece target = null;

		IKnight[] knights = world.getOtherKnights(this);

		IKnight targetKnight = null;
		for (int i = 0; i < knights.length; i++)
		{
			IKnight enemyKnight = knights[i];
			// Check for adjacent knights first
			if (!isAdjacent(knight, targetKnight) && isAdjacent(knight, enemyKnight))
			{
				// choose an adjacent knight over a remote one.
				targetKnight = enemyKnight;
			}
			else if (isAdjacent(knight, targetKnight) && isAdjacent(knight, enemyKnight)
					&& enemyKnight.getStrength() < targetKnight.getStrength())
			{
				// choose the weakest adjacent knight
				targetKnight = enemyKnight;
			}
			else
			{
				if (targetKnight == null || enemyKnight.getStrength() < targetKnight.getStrength())
				{
					targetKnight = enemyKnight;
				}
				else if (enemyKnight.getStrength() == targetKnight.getStrength()
						&& getDistanceTo(knight, enemyKnight) < getDistanceTo(knight, targetKnight))
				{
					targetKnight = enemyKnight;
				}
			}
		}

		if (targetKnight == null)
		{
			// then search for a peasent
			IPeasant[] peasants = world.getOtherPeasants(this);
			for (int i = 0; i < peasants.length; i++)
			{
				IPeasant enemyPeasant = peasants[i];
				if (this.getDistanceTo(knight, enemyPeasant) < this.getDistanceTo(knight, target))
				{
					target = enemyPeasant;
				}
			}
		}
		else
			target = targetKnight;

		return target;
	}

	/**
	 * @param knight
	 * @param enemyKnight
	 * @return
	 */
	private int getDistanceTo(IPiece lhs, IPiece rhs)
	{
		if (lhs == null || rhs == null)
			return GameSettings.getWIDTH() * GameSettings.getHEIGHT();
		else
		{
			ILocation loc = world.getLocation(rhs);
			return lhs.getDistanceTo(loc.getX(), loc.getY());
		}
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

	public void notifyCapture(IPiece captor, IPiece captee)
	{
		// TODO Auto-generated method stub

	}

}