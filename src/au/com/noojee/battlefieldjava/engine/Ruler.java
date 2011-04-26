/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.engine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import au.com.noojee.battlefieldjava.images.ImageLoader;
import au.com.noojee.battlefieldjava.occupants.Castle;
import au.com.noojee.battlefieldjava.occupants.Knight;
import au.com.noojee.battlefieldjava.occupants.Occupant;
import au.com.noojee.battlefieldjava.occupants.Peasant;
import au.com.noojee.battlefieldjava.occupants.Piece;
import au.com.noojee.battlefieldjava.ruler.ICastle;
import au.com.noojee.battlefieldjava.ruler.IRuler;
import au.com.noojee.battlefieldjava.ui.EventLogWindow;


/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Ruler 
{

	World					world;
	private Score					score;
	private ArrayList<Knight>				knights				= new ArrayList<Knight>();
	private ArrayList<Peasant>				peasants			= new ArrayList<Peasant>();
	private ArrayList<Piece>				castles				= new ArrayList<Piece>();
	private Color					color;

	// Fertility rate table
	// The rate of population growth is linked to the number of locations owned by 
	// a ruler. The more land owned the few turns it takes to gestate a new subject.
	// The following table defines the number of turns it requires to gestate
	// one subject given the ruler holds less than or equal to the associated number of 
	// pieces of land.
	// The type of piece generated is controlled by the individual castles creationMode
	// as it is set during the turn in which the birth takes place.
	// If the ruler transitions between fertility rates during a count down
	// then the current gestation period will not be affected.

	private int						fertilityTable[][]	= {{0, 0}, {125, 14} // 0 - 125 locations == 14 turns
												, {250, 12} // 126 - 250 locations == 12 turns
												, {500, 10}, {1000, 8}, {2000, 6}, {4000, 4}};

	// The period remaining until the next birth
	private int						gestation;

	// Over-ride for the gestation period. If true
	// then no new subjects are born.
	private boolean					infertile			= true;
	
	
	/** The user's ruler object 
	 * 
	 */
	private IRuler userRuler;

	Ruler(IRuler userRuler, World world, Color color)
	{
		this.userRuler = userRuler;
		this.world = world;
		this.color = color;
		this.score = new Score(this);
	}
	
	/**
	 * Called at the end of each turn to allow the ruler to birth subjects.
	 * 
	 * The ruler may generate 1 subjects of the currently set type per 'n' squares
	 * of owned land for each owned castle held.
	 * 
	 */
	public final void populate()
	{
		int gestationPeriod = getGestationPeriod();
		if (!infertile)
		{
			if (gestation > 0)
				gestation--;
			else
			{
				Castle[] castles = this.getCastles();
				for (int i = 0; i < castles.length; i++)
				{
					Castle castle = castles[i];
					castle.populate(1);
				}
				gestation = gestationPeriod;
			}
		}

		// Check fertility 
		{
			if (gestationPeriod == 0)
				infertile = true;
			else
			{
				if (infertile == true)
					gestation = gestationPeriod;
				infertile = false;
			}
		}
	}

	int getGestationPeriod()
	{
		int gestationPeriod = fertilityTable[fertilityTable.length - 1][1];
		int land = getOwnedLandCount();

		for (int i = fertilityTable.length - 1; i >= 0; i--)
		{
			if (land > fertilityTable[i][0])
			{
				gestationPeriod = fertilityTable[i][1];
				break;
			}
		}
		return gestationPeriod;
	}

	/* (non-Javadoc)
	 * @see openruler.ruler.IRuler#getPoints()
	 */
	public final int getPoints()
	{
		return score.getPoints();
	}

	/* (non-Javadoc)
	 * @see openruler.ruler.IRuler#getCastles()
	 */
	public final Castle[] getCastles()
	{
		return castles.toArray(new Castle[0]);
	}

	/* (non-Javadoc)
	 * @see openruler.ruler.IRuler#getPeasants()
	 */
	public final Peasant[] getPeasants()
	{
		return peasants.toArray(new Peasant[0]);
	}

	/* (non-Javadoc)
	 * @see openruler.ruler.IRuler#getKnights()
	 */
	public final Knight[] getKnights()
	{
		return knights.toArray(new Knight[0]);
	}

	/* Return both peasants and knights in a single list.
	 * (non-Javadoc)
	 * @see openruler.ruler.IRuler#getSubjects()
	 */
	public Occupant[] getSubjects()
	{
		Knight[] knights = getKnights();
		Peasant[] peasants = getPeasants();
		Occupant[] subjects = new Occupant[knights.length + peasants.length];

		System.arraycopy(knights, 0, subjects, 0, knights.length);
		System.arraycopy(peasants, 0, subjects, knights.length, peasants.length);

		return subjects;
	}

	/* Return both peasants and knights in a single list.
	 * (non-Javadoc)
	 * @see openruler.ruler.IRuler#getSubjects()
	 */
	public Piece[] getPieces()
	{
		Occupant[] subjects = getSubjects();
		Castle[] castles = getCastles();
		Piece[] pieces = new Piece[subjects.length + castles.length];

		System.arraycopy(subjects, 0, pieces, 0, subjects.length);
		System.arraycopy(castles, 0, pieces, subjects.length, castles.length);

		return pieces;
	}


	/* (non-Javadoc)
	 * @see openruler.ruler.IRuler#getOwnedLandCount()
	 */
	public final int getOwnedLandCount()
	{
		return world.getOwnedLandCount(this);
	}

	/**
	 * Order the specified piece to move in the given direction. If the square is empty
	 * (does not contain a peasant, knight, or castle), the piece will move to the
	 * adjacent square. If the square that it moves into is not already claimed
	 * by your ruler, the piece will claim the land.
	 * 
	 * @param piece - The peasant to give the order to
	 * @param direction - The direction that the peasant should move
	 */
	public final void move(Piece piece, Direction direction)
	{
		if (piece.canMove())
			piece.move(direction);
	}

	/**
	 * Order the specified knight to attack in the given direction. If there is a peasant, knight
	 * or castle from an opposing ruler in the square in the specified direction, the knight will
	 * attempt to capture it. If the square is empty, the knight will not do anything. The knight's
	 * location will not change in either case.
	 * 
	 * @param knight - The knight to give the order to
	 * @param direction - The direction the knight should capture in
	 */
	public final void attack(Knight knight, Direction direction)
	{
		knight.attack(direction);
	}

	/**
	 * Order the specified castle to start producing new peasants. The castle will
	 * produce peasants faster when the ruler has more land.
	 * 
	 * @param castle - The castle to give the order to
	 */
	public final void createPeasants(ICastle castle)
	{
		castle.createPeasants();
	}

	/**
	 * @param captee
	 */
	void registerCastle(Castle castle)
	{
		if (castle.getRuler() != this)
			castle.changeOwner(this);

		castles.add(castle);
		world.setOccupant(castle.getLocation(), castle);
		castle.getLocation().refresh();

	}

	/**
	 * Order the specified castle to start producing new knights. The castle will
	 * produce knights faster when the ruler has more land.
	 * 
	 * @param castle - The castle to give the order to
	 */
	public final void createKnights(ICastle castle)
	{
		castle.createKnights();
	}

	public void registerKnight(Knight knight, Location birthPoint)
	{
		knights.add(knight);
		Location location = world.getLocationNear(birthPoint);
		world.setOccupant(location, knight);
		location.refresh();
		EventLogWindow.getInstance().addEvent(new Event(knight, Event.ACTION_BIRTH));
	}

	public void registerPeasant(Peasant peasant, Location birthPoint)
	{
		peasants.add(peasant);
		Location location = world.getLocationNear(birthPoint);
		world.setOccupant(location, peasant);
		location.refresh();
		EventLogWindow.getInstance().addEvent(new Event(peasant, Event.ACTION_BIRTH));
	}



	/**
	 * 
	 */
	public void dumpFinalPositions()
	{
		Occupant[] subjects = this.getSubjects();

		for (int i = 0; i < subjects.length; i++)
		{
			Occupant subject = subjects[i];
			subject.dump(System.out);
		}

	}

	public Color getColor()
	{
		return color;
	}

	/**
	 * Informs the ruler that we captured an object.
	 * @param occupant
	 * @param occupant2
	 */
	public void notifyCapture(Occupant captor, Piece captee)
	{
		if (captee instanceof Peasant)
		{
			score.capturedPeasant();
		}
		else if (captee instanceof Knight)
		{
			score.capturedKnight();
		}
		else if (captee instanceof Castle)
		{
			score.capturedCastle();
			// the castle is now ours.
			((Ruler) captee.getRuler()).pieceLost((Object) captee);
			registerCastle((Castle) captee);

			castles.add(captee);
		}
		else
			throw new IllegalArgumentException(
					"Unknown captee type. must be Peasant, Knight or castle");

	}

	/**
	 * Informs the ruler that one of his objects was captured
	 * @param object
	 */
	public void pieceLost(Object object)
	{
		// just try to remove it form each array util we succeed.
		knights.remove(object);
		peasants.remove(object);
		castles.remove(object);
	}

	/**
	 * @return
	 */
	public Score getScore()
	{
		return score;
	}

	/**
	 * @return
	 */
	public ImageIcon getImageIcon(Component target)
	{
		ImageIcon rulerIcon = null;
		Image intermediateImage = null;
		if (intermediateImage == null)
		{
			int width = 20;
			int height = 20;
			ImageIcon castle = ImageLoader.getIcon(Castle.IMAGE_PATH);

			BufferedImage bImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D)bImg.getGraphics();
			g.setComposite(AlphaComposite.Src);
			g.setBackground(this.getColor());
			g.clearRect(0, 0, width, height);
			g.setComposite(AlphaComposite.SrcOver);
			g.drawImage(castle.getImage(), 0, 0, null);

			rulerIcon = new ImageIcon(bImg);
			g.dispose();
		}
		return rulerIcon;
	}

	/**
	 * Gives each Ruler a chance to do some initialisation as the work is created.
	 */
	public void initialize()
	{
		// Give the user a chance to initialise their ruler.
		this.userRuler.initialize();
	}

	public void deployPieces(int lastMoveTime)
	{
		this.userRuler.deployPieces(lastMoveTime);
	}

	public String getRulerName()
	{
		return userRuler.getRulerName();
	}

	public String getSchoolName()
	{
		return userRuler.getSchoolName();
		
	}

	public Knight createKnight()
	{
		return new Knight(this, userRuler.createKnight(world.getUserWorld()));
	}

	public Peasant createPeasant()
	{
		return new Peasant(this, userRuler.createPeasant(world.getUserWorld()));
	}

	public IRuler getUserRuler()
	{
		return userRuler;
	}
}
