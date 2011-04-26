/*
 * Created on 2/03/2005
 *
 * Used to notify an interested party regarding specific turn events.
 */
package au.com.noojee.battlefieldjava.engine;



/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface TurnListener
{
	// Called just before the first turn and before the 
	// call to startTurn
	void startBattle()throws UserCancelledBattle;
	
	// Called at the start of each turn
	void startRound(int turn) throws UserCancelledBattle;
	
	// Called before each ruler is given a turn
	void startTurn(int turn, Ruler ruler) throws UserCancelledBattle;
	
	void endTurn(int turn, Ruler ruler) throws UserCancelledBattle;
	
	// Called at the end of each turn.
	void endRound(int turn) throws UserCancelledBattle;
	
	// Called at the completion of the battle
	void endBattle(boolean cancelled);
}
