package au.com.noojee.battlefieldjava.ruler;




/**
 * @author bsutton
 *
 * Inteface to the Ruler.
 */
public interface IRuler
{
	/**
	 * Called to give you a chance to do initialization. This method
	 * will be called at the beginning of each match, and you will
	 * have a limited amount of time to do initialization. All of your
	 * objects and your opponents' objects will already exist.
	 */
	public abstract void initialize();

	/**
	 * Returns the name of the ruler.
	 * 
	 * @return java.lang.String
	 */
	public String getRulerName();

	/**
	 * Returns the name of the school or organization
	 * responsible for developing the ruler.
	 * 
	 * @return java.lang.String
	 */
	public String getSchoolName();

	/** Called each time the game needs a new castle created
	 * 
	 * @return
	 */
	public ICastle createCastle(IWorld world);

	/**
	 * Called each time the game needs to have a new Knight owned by the ruler
	 */
	public IKnight createKnight(IWorld world);


	/**
	 * Called each time the game needs to have a new Peasant owned by the ruler
	 */
	public IPeasant createPeasant(IWorld world);

	/**
	 * Called to notify the ruler that a piece (the captee) was captured
	 * by the another piece the 'captor'.
	 * 
	 * @param captor
	 * @param captee
	 */
	public void notifyCapture(IPiece captor, IPiece captee);

	/**
	 * This method is called each turn to allow you to give orders to your peasants,
	 * knights, and castles. The parameter specifies the length of time (in ms) that
	 * the last call to orderSubjects() took.
	 * (The first time that this method is called, lastMoveTime is -1)
	 *
	 * @param int lastMoveTime
	 */
	public abstract void deployPieces(int lastMoveTime);

}
