/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.occupants;

import au.com.noojee.battlefieldjava.engine.Ruler;
import au.com.noojee.battlefieldjava.ruler.IPiece;

/**
 * @author bsutton
 *
 * A MobilePiece is any piece that can move about the board.
 */
abstract public class MobilePiece extends Piece 
{

	protected MobilePiece(Ruler owner, IPiece userPiece, int initialStrength)
	{
		super(owner, userPiece, initialStrength);
	}

	// Used to notify the object that a new turn has started.
	public void startRulerTurn()
	{
		super.startRulerTurn();
		setHasMoved(false);
	}


}