package au.com.noojee.battlefieldjava.ruler;

import au.com.noojee.battlefieldjava.engine.Direction;

public interface IWorld
{

	/** 
	 * returns the set of knights owned by the given ruler
	 * 
	 * @param ruler
	 * @return
	 */
	IKnight[] getKnights(IRuler ruler);


	/** 
	 * returns the set of peasants owned by the given ruler
	 * 
	 * @param ruler
	 * @return
	 */
	IPeasant[] getOwnedPeasants(IRuler ruler);

	// returns the location of the given piece
	ILocation getLocation(IPiece piece);


	/**
	 * Attempt an attack in the given direction using the 'attacker'.
	 * @param knight
	 * @param dir
	 */
	void attack(IKnight attacker, Direction dir);


	/**
	 * Attempt to move the given piece in the given direction.
	 * 
	 * @param piece
	 * @param dir
	 */
	void move(IPiece piece, Direction dir);


	/**
	 * returns the set of castles owened by the given ruler.
	 * 
	 * @param ruler
	 * @return
	 */
	ICastle[] getCastles(IRuler ruler);


	/*
	 * returns the set of knights owned by other rulers.
	 */
	IKnight[] getOtherKnights(IRuler ruler);

	/*
	 * returns the set of peasants owned by other rulers.
	 */
	IPeasant[] getOtherPeasants(IRuler ruler);

	/*
	 * returns the set of castles owned by other rulers.
	 */

	ICastle[] getOtherCastles(IRuler ruler);


	/**
	 * Allows a IRuler to request the creation of a Peasant at the given castle.
	 * The request will be honoured providing the Ruler hasn't exceed their allotment of
	 * peasants for this turn.
	 * @param iCastle
	 */
	void requestPeasants(ICastle iCastle);

	/**
	 * Allows a IRuler to request the creation of a Knight at the given castle.
	 * The request will be honoured providing the Ruler hasn't exceed their allotment of
	 * Knights for this turn.
	 * @param iCastle
	 */
	void requestKnights(ICastle iCastle);


	IOccupant getOccupant(ILocation temp);


	ILocation getLocationAt(int X, int Y);


	ILocation getLocationAfterMove(ILocation location, Direction dir);


	IRuler getLandOwner(int X, int Y);


	boolean hasMoved(IMobilePiece imobilePiece);
}
