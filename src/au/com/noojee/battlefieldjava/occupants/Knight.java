/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.occupants;

import java.awt.Image;
import java.io.PrintStream;
import java.util.Random;

import au.com.noojee.battlefieldjava.engine.Direction;
import au.com.noojee.battlefieldjava.engine.Event;
import au.com.noojee.battlefieldjava.engine.GameSettings;
import au.com.noojee.battlefieldjava.engine.IllegalMoveError;
import au.com.noojee.battlefieldjava.engine.IllegalOperationError;
import au.com.noojee.battlefieldjava.engine.Location;
import au.com.noojee.battlefieldjava.engine.Ruler;
import au.com.noojee.battlefieldjava.engine.World;
import au.com.noojee.battlefieldjava.images.ImageLoader;
import au.com.noojee.battlefieldjava.ruler.IKnight;
import au.com.noojee.battlefieldjava.ui.EventLogWindow;



/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Knight extends MobilePiece 
{

	Random						rand					= new Random();

	public static final int		MAX_ATTACK_STRENGTH		= 30;

	public static final int		MIN_ATTACK_STRENGTH		= 15;

	// the strenght bonus a knight gets for capturing
	// an enemy.
	private static final int	CAPTURE_STRENGH_BONUS	= 20;

	//If true this knight attacked another object in the last turn
	private boolean				didAttack				= false;

	// if didAttack is true then this hold the object which was attacked.
	private Occupant				attackTarget;

	final private IKnight 			userKnight;
	
	public Knight(Ruler owner, IKnight userKnight)
	{
		super(owner, userKnight, GameSettings.getStengthKnights());
		this.userKnight = userKnight;
		this.userKnight.setId(getId());
	}

	/**
	 * Attempts an attack manuveour in the direction specified.
	 * @param direction
	 */
	public void attack(Direction direction)
	{
		if (!this.isAlive())
		{
			EventLogWindow.getInstance().addEvent(
					new Event(this, Event.ACTION_ATTACK, "You can't use subjects which are dead."));
			if (GameSettings.getDebugMode())
				throw new IllegalMoveError("You can't use subjects which are dead.");
			else
				return;
		}

		if (getRuler() != World.getCurrentTurnRuler())
		{
			EventLogWindow.getInstance().addEvent(
					new Event(this, Event.ACTION_ATTACK,
							"You can't control knights which arn't yours."));
			if (GameSettings.getDebugMode())
				throw new IllegalOperationError("You can't control knights which arn't yours");
			else
				return;
		}

		if (hasMoved())
		{
			EventLogWindow.getInstance().addEvent(
					new Event(this, Event.ACTION_ATTACK,
							"You have already moved this piece during the current turn."));
			if (GameSettings.getDebugMode())
				throw new IllegalMoveError(
						"You have already moved this piece during the current turn.");
			else
				return;
		}

		setHasMoved(true);

		Location newLocation = World.getLocationAfterMove(this.getLocation(), direction);

		Occupant occupant = newLocation.getOccupant();
		if (occupant != null && occupant instanceof Piece)
		{
			Piece piece = (Piece) occupant;
			if (piece.getRuler() == getRuler())
			{
				EventLogWindow.getInstance().addEvent(
						new Event(this, Event.ACTION_ATTACK, "You can't capture your own pieces."));
				if (GameSettings.getDebugMode())
					throw new IllegalOperationError("You can't capture your own pieces. Attacker="
							+ this.getId() + " attacked=" + piece.getId());
				else
					return;

			}
			else
			{
				// roll the dice to get our attack success
				int attackValue = getAttackValue();
				EventLogWindow.getInstance().addEvent(
						new Event(this, Event.ACTION_ATTACK, "Attack Value=" + attackValue,
								occupant));

				if (piece.attacked(attackValue, this))
				{
					this.captured(piece);
					EventLogWindow.getInstance().addEvent(
							new Event(this, Event.ACTION_KILLED, "Success", occupant));
				}
				didAttack = true;
				attackTarget = piece;
				piece.getLocation().refresh();
				this.getLocation().refresh();

				// no action required if the capture fails.
			}
		}

	}

	/**
	 * Called when the knight has captured the given object. 
	 * @param occupant
	 */
	private void captured(Piece captee)
	{
		incrementStrength(CAPTURE_STRENGH_BONUS);
		getRuler().notifyCapture(this, captee);
	}

	/**
	 * @return
	 */
	int getAttackValue()
	{
		return (Math.max(MIN_ATTACK_STRENGTH, Math.abs(rand.nextInt()) % MAX_ATTACK_STRENGTH));
	}

	public Image getImage()
	{
		return ImageLoader.getImage("knight.png");

	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Knight: ID=" + getId() + " ");
		sb.append(getLocation().toString());
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see openruler.ruler.ISubject#dump()
	 */
	public void dump(PrintStream out)
	{
		out.println(toString());
	}


	// Used to notify the object that a new turn has started.
	public void startRulerTurn()
	{
		super.startRulerTurn();
		attackTarget = null;
		didAttack = false;

		this.getLocation().refresh();
	}

	public boolean didAttack()
	{
		return didAttack;
	}


	public Occupant getAttackTarget()
	{
		return attackTarget;
	}

	public IKnight getUserKnight()
	{
		return userKnight;
	}

	@Override
	public boolean canMove()
	{
		return true;
	}


}