/*
 * Created on 26/02/2005
 *
 * Describes the world of OpenRulers.
 * 
 * The World is made up of a grid of locations which is n columns wide and m rows deep.
 * The co-ordinate plain of the world is an x, y (cartesian) coordinate plain
 * with its origin in the top left of the grid. X increases across the page
 * and Y increases down the page.
 * 
 * The top of the page is considered North with the bottom being South.
 * The left of the page is considered West with the right hand side being East. 
 */
package au.com.noojee.battlefieldjava.engine;

import java.awt.Container;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import au.com.noojee.battlefieldjava.occupants.Castle;
import au.com.noojee.battlefieldjava.occupants.ITreeImpl;
import au.com.noojee.battlefieldjava.occupants.Knight;
import au.com.noojee.battlefieldjava.occupants.Occupant;
import au.com.noojee.battlefieldjava.occupants.Peasant;
import au.com.noojee.battlefieldjava.occupants.Piece;
import au.com.noojee.battlefieldjava.occupants.Tree;
import au.com.noojee.battlefieldjava.ruler.ILocation;
import au.com.noojee.battlefieldjava.ruler.IPiece;
import au.com.noojee.battlefieldjava.ruler.IWorld;
import au.com.noojee.battlefieldjava.ui.BattleField;
import au.com.noojee.battlefieldjava.ui.Utilities;
import au.com.noojee.battlefieldjava.ui.VictoryDialog;
import au.com.noojee.battlefieldjava.usermapping.UserWorld;

/**
 * @author bsutton
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class World implements Runnable
{

	/**
	 * The set of rulers loaded into the game
	 * 
	 */
	static ArrayList<Ruler> rulers = new ArrayList<Ruler>();

	private static Grid grid;
	private static World self = null;
	private static UserWorld userWorld = null;

	// The ruler hows currently having his turn.
	private static Ruler currentTurnRuler = null;

	private static int currentRound = 0;

	private static TurnListener turnListener;

	private static BattleField battleField;

	public static int WIDTH = 0;

	public static int HEIGHT = 0;

	public synchronized static World getInstance()
	{
		return self;
	}

	public static void init(BattleField battleField)
	{
		if (self == null)
		{
			self = new World(battleField);
			userWorld = new UserWorld(self);
		}
		else
			throw new IllegalStateException("The World already exists.");

	}

	static Random rand = new Random();

	private World(BattleField battleField)
	{
		World.battleField = battleField;
		World.grid = new Grid(GameSettings.getWIDTH(), GameSettings.getHEIGHT());
	}

	public static void setTurnListener(TurnListener listener)
	{
		turnListener = listener;
	}

	/**
	 * @param bPauseAfterInit
	 * 
	 */
	public void run()
	{
		Ruler[] rulers = null;
		boolean cancelled = false;
		try
		{
			rulers = getRulers();

			if (turnListener != null)
				turnListener.startBattle();

			for (currentRound = 0; currentRound < GameSettings.getRounds(); currentRound++)
			{
				if (turnListener != null)
					turnListener.startRound(currentRound);

				for (int i = 0; i < rulers.length; i++)
				{
					if (turnListener != null)
						turnListener.startTurn(currentRound, rulers[i]);
					for (int j = 0; j < rulers.length; j++)
						startTurn(rulers[j]);
					try
					{
						World.getInstance().setCurrentRuler(rulers[i]);
						rulers[i].deployPieces(0);
						// battleField.refresh();
					}
					catch (Throwable e)
					{
						e.printStackTrace();
					}
					if (turnListener != null)
						turnListener.endTurn(currentRound, rulers[i]);

				}
				if (turnListener != null)
					turnListener.endRound(currentRound);

				// now give birth to a new generation of subjects
				for (int i = 0; i < rulers.length; i++)
				{
					rulers[i].populate();
				}
			}
		}
		catch (UserCancelledBattle e1)
		{
			// User has cancelled the battle so just silently shutdown
			cancelled = true;
		}
		catch (Exception e)
		{
			Utilities.showException(e);
		}
		finally
		{
			if (turnListener != null)
				turnListener.endBattle(cancelled);

		}

		if (!cancelled)
		{
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					Container parent = World.battleField.getParent();
					while (parent != null && !(parent instanceof JFrame))
						parent = parent.getParent();
					new VictoryDialog((JFrame) parent).setVisible(true);
				}
			});

		}
		// dumpFinalPositions(rulers);
	}

	/**
	 * clears the wasAttacked flag for all of the rulers pieces.
	 * 
	 * @param ruler
	 */
	private void startTurn(Ruler ruler)
	{
		Piece[] piece = ruler.getPieces();
		for (int i = 0; i < piece.length; i++)
			(piece[i]).startRulerTurn();
	}

	/**
	 * @param rulers2
	 */
	public static void dumpFinalPositions(Ruler[] rulers)
	{
		for (int i = 0; i < rulers.length; i++)
		{
			rulers[i].dumpFinalPositions();
		}

	}

	public void reset()
	{
		World.grid = new Grid(GameSettings.getWIDTH(), GameSettings.getHEIGHT());
		rulers = new ArrayList<Ruler>();
		World.battleField.reset(GameSettings.getWIDTH(), GameSettings.getHEIGHT());
	}

	/**
	 * Calculates the number of squares the given ruler owns.
	 * 
	 * @param ruler
	 * @return
	 */
	public int getOwnedLandCount(Ruler ruler)
	{
		return grid.getOwnedLandCount(ruler);
	}

	public void addRuler(Ruler ruler)
	{
		rulers.add(ruler);
		ruler.initialize();
	}

	/**
	 * @return
	 */
	public Grid getGrid()
	{
		return grid;
	}

	/**
	 * @return
	 */
	public static Ruler[] getRulers()
	{
		return rulers.toArray(new Ruler[0]);
	}

	/**
	 * @return
	 */
	public static Ruler[] getOtherRulers()
	{
		Ruler[] temp = getRulers();
		Ruler[] rulers = new Ruler[temp.length - 1];

		int index = 0;
		for (int i = 0; i < rulers.length; i++)
		{
			Ruler ruler = temp[i];
			// Ignore the ruler whose current turn it is
			if (ruler != currentTurnRuler)
			{
				rulers[index++] = ruler;
			}
		}

		return rulers;
	}

	/**
	 * @return
	 */
	public static Castle[] getOtherCastles()
	{
		Ruler[] rulers = getRulers();
		Castle[][] temp = new Castle[rulers.length - 1][];
		int count = 0;
		int index = 0;
		for (int i = 0; i < rulers.length; i++)
		{
			Ruler ruler = rulers[i];
			// Ignore the ruler whose current turn it is
			if (ruler != currentTurnRuler)
			{
				temp[index] = ruler.getCastles();
				count += temp[index].length;
				index++;
			}
		}

		// copy all nights into a single dimension array
		int offset = 0;
		Castle[] castles = new Castle[count];
		for (int j = 0; j < rulers.length - 1; j++)
		{
			System.arraycopy(temp[j], 0, castles, offset, temp[j].length);
			offset += temp[j].length;
		}

		return castles;
	}

	/**
	 * @return
	 */
	public static Knight[] getOtherKnights()
	{
		Ruler[] rulers = getRulers();
		Knight[][] temp = new Knight[rulers.length - 1][];
		int count = 0;
		int index = 0;
		for (int i = 0; i < rulers.length; i++)
		{
			Ruler ruler = rulers[i];
			// Ignore the ruler whose current turn it is
			if (ruler != currentTurnRuler)
			{
				temp[index] = ruler.getKnights();
				count += temp[index].length;
				index++;
			}
		}

		// copy all nights into a single dimension array
		int offset = 0;
		Knight[] knights = new Knight[count];
		for (int j = 0; j < rulers.length - 1; j++)
		{
			System.arraycopy(temp[j], 0, knights, offset, temp[j].length);
			offset += temp[j].length;
		}

		return knights;
	}

	/**
	 * @return
	 */
	public static Peasant[] getOtherPeasants()
	{
		Ruler[] rulers = getRulers();
		Peasant[][] temp = new Peasant[rulers.length - 1][];
		int count = 0;
		int index = 0;

		for (int i = 0; i < rulers.length; i++)
		{
			Ruler ruler = rulers[i];
			// Ignore the ruler whose current turn it is
			if (ruler != currentTurnRuler)
			{
				temp[index] = ruler.getPeasants();
				count += temp[index].length;
				index++;
			}
		}

		// copy all nights into a single dimension array
		int offset = 0;
		Peasant[] peasant = new Peasant[count];
		for (int j = 0; j < rulers.length - 1; j++)
		{
			System.arraycopy(temp[j], 0, peasant, offset, temp[j].length);
			offset += temp[j].length;
		}

		return peasant;
	}

	/*
	 * Return both peasants and knights in a single list. (non-Javadoc)
	 * 
	 * @see openruler.ruler.IRuler#getSubjects()
	 */
	public Occupant[] getOtherSubjects()
	{
		Knight[] knights = getOtherKnights();
		Peasant[] peasants = getOtherPeasants();
		Occupant[] subjects = new Occupant[knights.length + peasants.length];

		System.arraycopy(knights, 0, subjects, 0, knights.length);
		System.arraycopy(peasants, 0, subjects, knights.length, peasants.length);

		return subjects;
	}

	/*
	 * Return both peasants and knights in a single list. (non-Javadoc)
	 * 
	 * @see openruler.ruler.IRuler#getSubjects()
	 */
	public Occupant[] getOtherPieces()
	{
		Occupant[] subjects = getOtherSubjects();
		Castle[] castles = getOtherCastles();
		Occupant[] pieces = new Occupant[subjects.length + castles.length];

		System.arraycopy(subjects, 0, pieces, 0, subjects.length);
		System.arraycopy(castles, 0, pieces, subjects.length, castles.length);

		return pieces;
	}

	/**
	 * @return
	 */
	public static Piece[] getAllPieces()
	{
		Ruler[] rulers = getRulers();
		Vector<Piece> pieces = new Vector<Piece>();

		for (Ruler ruler : rulers)
		{
			for (Knight knight : ruler.getKnights())
				pieces.add(knight);
			for (Peasant peasant : ruler.getPeasants())
				pieces.add(peasant);
			for (Castle castle : ruler.getCastles())
				pieces.add(castle);
		}

		Piece[] aPieces = new Piece[0];
		return pieces.toArray(aPieces);
	}

	public Occupant[] getOtherObjects()
	{
		return getOtherPieces();
	}

	/**
	 * @param ruler
	 */
	public void setCurrentRuler(Ruler ruler)
	{
		currentTurnRuler = ruler;
	}

	public int getCurrentRound()
	{
		return currentRound;
	}

	public void setOccupant(int x, int y, Piece occupant)
	{
		grid.setOccupant(x, y, occupant);
	}

	public void setOccupant(Location location, Occupant occupant)
	{
		grid.setOccupant(location, occupant);
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public static Occupant getPieceAt(int x, int y)
	{
		return grid.getLocations()[x][y].getOccupant();
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public static Occupant getOccupantAt(Location location)
	{
		return grid.getLocations()[location.getX()][location.getY()].getOccupant();
	}

	public static Occupant getOccupantAt(ILocation location)
	{
		return grid.getLocations()[location.getX()][location.getY()].getOccupant();
	}

	public static Location getLocationAt(int x, int y)
	{
		return grid.getLocations()[x][y];
	}

	public static Ruler getLandOwner(int row, int column)
	{
		return grid.getLocations()[row][column].getOwner();
	}

	public static Location getLocationAfterMove(Location location, Direction dir)
	{
		return getLocationAfterMove(location.getX(), location.getY(), dir);
	}

	public static Point getPositionAfterMove(int x, int y, Direction dir)
	{
		Point point = null;
		Location location = getLocationAfterMove(x, y, dir);

		if (location != null)
			point = new Point(location.getX(), location.getY());
		return point;

	}

	/**
	 * Returns the location based on a position at location x,y after a
	 * theoretical move in the direction dir has occured. If the new location is
	 * off the grid then null is returned;
	 * 
	 * @param x
	 *            across the page left to right (west to east)
	 * @param y
	 *            down the page top to bottom (north to south)
	 * @param dir
	 * @return
	 */
	public static Location getLocationAfterMove(int x, int y, Direction dir)
	{
		Location location = null;
		Location[][] locations = grid.getLocations();

		switch (dir)
		{
			case MOVE_NONE:
				break;
			case MOVE_N:
				y--;
				break;
			case MOVE_NE:
				x++;
				y--;
				break;
			case MOVE_E:
				x++;
				break;
			case MOVE_SE:
				x++;
				y++;
				break;
			case MOVE_S:
				y++;
				break;
			case MOVE_SW:
				x--;
				y++;
				break;
			case MOVE_W:
				x--;
				break;
			case MOVE_NW:
				x--;
				y--;
				break;
			default:
				throw new IllegalArgumentException("The direction value of " + dir + " is illegal use 1-8");
		}

		// If the location isn't off the grid then return it.
		if (!(x < 0 || y < 0 || x > GameSettings.getWIDTH() - 1 || y > GameSettings.getHEIGHT() - 1))
			location = locations[x][y];

		return location;
	}

	/**
	 * @param location
	 */
	public void refresh(Location location)
	{
		battleField.refresh(location);
	}

	/**
	 * @param b
	 */
	public void setShowIDs(boolean showIDs)
	{
		battleField.setShowIDs(showIDs);

	}

	public static Ruler getCurrentTurnRuler()
	{
		return currentTurnRuler;
	}

	/**
	 * @param i
	 */
	public void setCurrentRound(int currentRound)
	{
		World.currentRound = currentRound;

	}

	/**
	 * @param i
	 */
	public void setCurrentTurnRuler(int i)
	{
		// TODO Auto-generated method stub

	}

	public IWorld getUserWorld()
	{
		return userWorld;
	}

	public Piece getPiece(IPiece ipiece)
	{
		Piece result = null;
		Piece[] pieces = getAllPieces();

		for (Piece piece : pieces)
		{
			if (ipiece == piece.getUserPiece())
				result = piece;
		}

		return result;
	}

	public void populate()
	{
		createTrees();

		for (Ruler ruler : getRulers())
		{
			populate(ruler);
		}
	}

	private void createTrees()
	{
		int numTrees = getRandom(150);

		for (int i = 0; i < numTrees; i++)
		{
			int x,y;
			// Start by laying down the forests

			// Search for a free location to place each tree
			// TODO: do some clustering to create forests.
			Tree tree = new Tree(new ITreeImpl());
			do
			{
				y = getRandomY(rand);
				x = getRandomX(rand);
			} while (World.getPieceAt(x, y) != null);

			tree.setLocation(World.getLocationAt(x, y));
			setOccupant(tree.getLocation(), tree);
			tree.getLocation().refresh();
		}
	}

	void populate(Ruler ruler)
	{
		int y;
		int x;

		// Create the initial population
		for (int i = 0; i < GameSettings.getPopulationCastles(); i++)
		{
			// Search for a free location to place the castle at.
			// TODO: attempt to ensure that we have reasonable separation
			// between
			// castles.
			Castle castle = new Castle(ruler, ruler.getUserRuler().createCastle(getUserWorld()));
			do
			{
				y = getRandomY(rand);
				x = getRandomX(rand);
			} while (World.getPieceAt(x, y) != null);

			castle.setLocation(World.getLocationAt(x, y));
			ruler.registerCastle((Castle) castle);

			// register peasants
			for (int j = 0; j < GameSettings.getPopulationPeasants(); j++)
			{
				Peasant peasant = new Peasant(ruler, ruler.getUserRuler().createPeasant(getUserWorld()));
				ruler.registerPeasant(peasant, castle.getLocation());
			}

			// register knights
			for (int j = 0; j < GameSettings.getPopulationKnights(); j++)
			{
				Knight knight = new Knight(ruler, ruler.getUserRuler().createKnight(getUserWorld()));
				ruler.registerKnight(knight, castle.getLocation());
			}
		}

	}

	/**
	 * @param rand
	 * @param range
	 *            TODO
	 * @return
	 */
	int getRandom(int range)
	{
		return Math.abs(World.rand.nextInt() % range);
	}

	/**
	 * @param rand
	 * @return
	 */
	int getRandomY(Random rand)
	{
		return Math.abs(rand.nextInt()) % GameSettings.getHEIGHT();
	}

	/**
	 * @param rand
	 * @return
	 */
	int getRandomX(Random rand)
	{
		return Math.abs(rand.nextInt()) % GameSettings.getWIDTH();
	}

	/**
	 * Finds a location near the given location with some randomness applied.
	 * Used during the population phase.
	 * 
	 * @param location
	 * @param centre
	 *            TODO
	 * @return
	 */
	Location getLocationNear(Location centre)
	{
		Location location = null;

		int distance = 5;
		int x;
		int y;
		do
		{
			int attempts = 0;
			boolean skip = false;
			do
			{
				skip = false;
				y = centre.getY() + getRandom(distance);
				x = centre.getX() + getRandom(distance);

				// Check for a valid location
				if (y < 0 || y > GameSettings.getHEIGHT() - 1 || x < 0 || x > GameSettings.getWIDTH() - 1)
					skip = true;

				attempts++;
			} while (skip || (World.getPieceAt(x, y) != null && attempts < 100));

			distance++;
		} while (World.getPieceAt(x, y) != null && distance < GameSettings.getWIDTH());

		if (World.getPieceAt(x, y) != null)
			throw new IllegalStateException(
					"Unable to place all pieces, try increasing the board size or reducing the number of pieces.");

		location = World.getLocationAt(x, y);
		return location;

	}

}