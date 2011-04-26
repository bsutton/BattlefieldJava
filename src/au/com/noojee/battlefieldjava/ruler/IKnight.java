/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.ruler;

import au.com.noojee.battlefieldjava.engine.Direction;


/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IKnight extends IMobilePiece
{
	/*
	 * Returns the nights strength
	 */
	int getStrength();

	/**
	 * @param direction
	 */
	//void attack(int direction);

	/**
	 * @param direction
	 */
	void move(Direction direction);

	int getX();

	int getY();

}
