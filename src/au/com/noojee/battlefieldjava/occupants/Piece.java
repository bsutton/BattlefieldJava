/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.occupants;


import au.com.noojee.battlefieldjava.engine.Direction;
import au.com.noojee.battlefieldjava.engine.Event;
import au.com.noojee.battlefieldjava.engine.GameSettings;
import au.com.noojee.battlefieldjava.engine.IllegalMoveError;
import au.com.noojee.battlefieldjava.engine.IllegalOperationError;
import au.com.noojee.battlefieldjava.engine.Location;
import au.com.noojee.battlefieldjava.engine.Ruler;
import au.com.noojee.battlefieldjava.engine.World;
import au.com.noojee.battlefieldjava.ruler.IPiece;
import au.com.noojee.battlefieldjava.ui.EventLogWindow;



/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
abstract public class Piece extends Occupant 
{

	private boolean				isAlive					= true;

	// If true then this object was attacked in opponents last turn
	boolean				wasAttacked				= false;

	// If the object was attacked by an opponent in the last turn
	// the this is the knight who attacked.
	Knight				attackedBy;

	// The guy who owns this object
	Ruler						ruler;

	// The strenght this object has remaining.
	private int							strength;
	
	// The piece created by the user defined ruler.
	final IPiece	userPiece;
	
	// tracks if the piece has moved during this round
	private boolean hasMoved;

	protected Piece(Ruler ruler, IPiece userPiece, int initialStrength)
	{
		super(userPiece);
		this.setRuler(ruler);
		this.strength = initialStrength;
		this.userPiece = userPiece;
	}

	
	/**
	 * @param string
	 * @return
	 */
	public String getTipText()
	{
		String tip = getLocation().toString() + " " + this.getClass().getSimpleName() + " ID=" + getId()
				+ " Strength=" + this.getStrength();
	
		if (wasAttacked)
		{
			tip += " AttackedBy=" + attackedBy.getId();
		}
	
		return tip;
	}


	
	public Ruler getRuler()
	{
		return ruler;
	}

	/* (non-Javadoc)
	 * @see openruler.ruler.IObject#isAlive()
	 */
	public boolean isAlive()
	{
		return isAlive;
	}

	public int getStrength()
	{
		return strength;
	}

	// returns true if the object is captured by the attack.
	public boolean attacked(int attackStrength, Knight attacker)
	{
		boolean captured = false;
		wasAttacked = true;
		attackedBy = attacker;
		strength -= attackStrength;
		if (strength <= 0)
		{
			captured = true;
			// Inform the ruler that he just lost a piece.
			((Ruler) this.getRuler()).pieceLost(this);
			
			if (this instanceof Castle)
			{
				// the attacker now rules this castle.
				this.setRuler(attacker.getRuler());
			}
			else
			{
				isAlive = false;
				location.clearOccupant();
			}
		}
		location.refresh();

		return captured;
	}

	public boolean wasAttacked()
	{
		return wasAttacked;
	}

	// Used to notify the object that a new turn has started.
	public void startRulerTurn()
	{
		boolean temp = wasAttacked;
		wasAttacked = false;
		attackedBy = null;

		if (temp)
			this.location.refresh();

	}

	/**
	 * When we capture a piece our strength increases.
	 * 
	 * @param capture_strengh_bonus
	 */
	protected void incrementStrength(int capture_strengh_bonus)
	{
		strength += capture_strengh_bonus;

	}

	public void setRuler(Ruler ruler)
	{
		this.ruler = ruler;
	}

	public IPiece getUserPiece()
	{
		return userPiece;
	}
	
	/**
	 * @param direction
	 */
	public void move(Direction direction)
	{
		// make certain that the ruler is moving one of their own pieces.
		if (this.getRuler() != World.getCurrentTurnRuler())
		{
			EventLogWindow.getInstance().addEvent(
					new Event(this, Event.ACTION_MOVE, "You may only move your own pieces"));
			if (GameSettings.getDebugMode())
				throw new IllegalOperationError("You may only move your own pieces");
			else
				return;
		}

		if (!this.isAlive())
		{
			EventLogWindow.getInstance().addEvent(
					new Event(this, Event.ACTION_MOVE, "You can't move subjects which are dead."));
			if (GameSettings.getDebugMode())
		throw new IllegalMoveError("You can't move subjects which are dead.");
			else
				return;
		}
		if (hasMoved())
		{
			EventLogWindow.getInstance().addEvent(
					new Event(this, Event.ACTION_MOVE,
							"You have already moved this piece during the current turn."));
			if (GameSettings.getDebugMode())
				throw new IllegalMoveError(
						"You have already moved this piece during the current turn.");
			else
				return;
		}

		setHasMoved(true);

		Location current = getLocation();
		Location newLocation = World.getLocationAfterMove(current, direction);
		assert current != newLocation;
		if (newLocation != null)
		{
			if (newLocation.getOccupant() == null)
			{
				EventLogWindow.getInstance().addEvent(
						new Event(this, Event.ACTION_MOVE, "Success: " + newLocation.toString()));

				World.getInstance().setOccupant(newLocation, this);
				current.clearOccupant();
				current.refresh();
				newLocation.refresh();
			}
			else
			{
				EventLogWindow.getInstance().addEvent(
						new Event(this, Event.ACTION_MOVE,
								"Invalid move as destination is already occupied"));
				if (GameSettings.getDebugMode())
					throw new IllegalMoveError("Invalid move as destination is already occupied");
				else
					return;
			}
		}
		else
		{
			EventLogWindow.getInstance().addEvent(
					new Event(this, Event.ACTION_MOVE,
							"Invalid direction ignored as piece would move off the Battle field"));
			if (GameSettings.getDebugMode())
				throw new IllegalMoveError(
						"Invalid direction ignored as piece would move off the Battle field");
			else
				return;
		}
	}


	abstract public boolean canMove();


	public void setHasMoved(boolean hasMoved)
	{
		this.hasMoved = hasMoved;
	}

	public boolean hasMoved()
	{
		return hasMoved;
	}

}