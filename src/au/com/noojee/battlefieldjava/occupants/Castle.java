/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.occupants;

import java.awt.Image;

import au.com.noojee.battlefieldjava.engine.GameSettings;
import au.com.noojee.battlefieldjava.engine.IllegalOperationError;
import au.com.noojee.battlefieldjava.engine.Ruler;
import au.com.noojee.battlefieldjava.engine.World;
import au.com.noojee.battlefieldjava.images.ImageLoader;
import au.com.noojee.battlefieldjava.ruler.ICastle;
import au.com.noojee.battlefieldjava.ruler.IPiece;



/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Castle extends Piece
{

	private static final int	CREATION_MODE_PEASANT	= 0;
	private static final int	CREATION_MODE_KNIGHT	= 1;

	int							creationMode			= CREATION_MODE_PEASANT;

	public static final int		INITIAL_STRENGTH		= 1000;
	public static final String	IMAGE_PATH				= "castle3.png";
	
	final private ICastle userCastle;

	public Castle(Ruler owner, ICastle userCastle)
	{
		super(owner, userCastle, GameSettings.getStengthCastles());
		userCastle.setId(getId());
		
		this.userCastle = userCastle;
	}

	/**
	 * Order the specified castle to start producing new knights. The castle will
	 * produce knights faster when the ruler has more land.
	 * 
	 * @param castle - The castle to give the order to
	 */
	public final void createKnights()
	{
		if (getRuler() != World.getCurrentTurnRuler())
			throw new IllegalOperationError("You can't control castles which arn't yours");
		creationMode = CREATION_MODE_KNIGHT;
	}

	/**
	 * Order the specified castle to start producing new peasants. The castle will
	 * produce peasants faster when the ruler has more land.
	 * 
	 * @param castle - The castle to give the order to
	 */
	public final void createPeasants()
	{
		if (getRuler() != World.getCurrentTurnRuler())
			throw new IllegalOperationError("You can't control castles which arn't yours");
		creationMode = CREATION_MODE_PEASANT;
	}

	public Image getImage()
	{
		return ImageLoader.getImage(IMAGE_PATH);
	}

	/* (non-Javadoc)
	 * @see openruler.ruler.IObject#getTipText()
	 */
	public String getTipText()
	{
		return getLocation().toString() + " Castle Strength=" + this.getStrength();

	}

	/* (non-Javadoc)
	 * @see openruler.ruler.IObject#getType()
	 */
	public String getType()
	{
		return "Castle";
	}

	/**
	 * @param conceptionRate
	 */
	public final void populate(int conceptionRate)
	{
		for (int i = 0; i < conceptionRate; i++)
		{
			switch (creationMode)
			{
				case CREATION_MODE_KNIGHT :
					Knight knight =  getRuler().createKnight();
					getRuler().registerKnight(knight, getLocation());
					break;
				case CREATION_MODE_PEASANT :
					Peasant peasant = getRuler().createPeasant();
					getRuler().registerPeasant(peasant, getLocation());
					break;
			}
		}
	}

	/**
	 * Castles can (by way of being captured) change owner. This method
	 * can be used to notify the castle that its owner has changed.
	 * 
	 * @param owner the new owner of the castle.
	 */
	public void changeOwner(Ruler owner)
	{
		this.setRuler(owner);
	}

	
	// Returns true if the piece is captured by the attack
	// but of course castles cannot be captured in this game.
	public boolean attacked(int attackValue, IPiece attackingPiece)
	{
		return false;
	}

	public ICastle getUserCastle()
	{
		return userCastle;
	}

	@Override
	public boolean canMove()
	{
		return false;
	}
}