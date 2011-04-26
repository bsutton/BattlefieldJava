/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.ruler;



/**
 * @author bsutton
 *
 * A mobile piece is any piece that can move (e.g. a peasant but not a castle)
 */
public interface IMobilePiece extends IPiece
{
	
	/**
	 * 
	 * @return true if the subject has moved during the current turn.
	 */
	boolean hasMoved();


}
