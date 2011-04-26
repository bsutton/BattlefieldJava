/*
 * Created on 26/02/2005
 * Tracks the Score of each Ruler
 * 
 * Ruler scores if he captures an object as follows:
 * Capture a peasant	4 points
 * Capture a knight	6 points
 * Capture a castle	15 points
 * 
 * At the end of the match the rulers remaining pieces, and captured land
 * are added to the score as follows:
 * 
 * Piece remaining	Score
 * Peasant	1 point
 * Knight	2 points
 * Castle	25 points
 * Land	1 point per 10 squares
 */
package au.com.noojee.battlefieldjava.engine;



/**
 * @author bsutton
 *
 */
public class Score
{
	// The points awarded for capturing an object.
	static final int CAPTURE_PEASANT = 4;
	static final int CAPTURE_KNIGHT = 6;
	static final int CAPTURE_CASTLE = 15;
	
	// the points awarded for having an object remaining at the end
	// of the battle.
	static final int REMAINING_PEASANT = 1;
	static final int REMAINING_KNIGHT = 2;
	static final int REMAINING_CASTLE = 25;
	
	// Land is one point per ten squares captured
	static final int REMAINING_LAND = 1;
	
	// The ruler we are keeping score for.
	private final Ruler ruler;
	
	int runningScore = 0;
	
	private int knightsKilled = 0;
	private int peasantsKilled = 0;
	private int castlesCaptured = 0;
	private int knightsLost = 0;
	private int peasantsLost = 0;
	
	
	
	public Score(Ruler ruler)
	{
		this.ruler = ruler;
	}
	
	/**
	 * @return
	 */
	public int getPoints()
	{
		return runningScore;
	}
	
	public int getFinalScore()
	{
		return runningScore 
			+ (ruler.getOwnedLandCount() / 10) * REMAINING_LAND
			+ ruler.getCastles().length * REMAINING_CASTLE
			+ ruler.getKnights().length * REMAINING_KNIGHT
			+ ruler.getPeasants().length * REMAINING_PEASANT;
	}
	
	public void capturedPeasant()
	{
		runningScore += CAPTURE_PEASANT;
		peasantsKilled++;
	}

	public void capturedKnight()
	{
		runningScore += CAPTURE_KNIGHT;
		knightsKilled++;
	}
	
	public void capturedCastle()
	{
		runningScore += CAPTURE_CASTLE;
		castlesCaptured++;
	}
	public int getCastlesCaptured()
	{
		return castlesCaptured;
	}
	public int getKnightsKilled()
	{
		return knightsKilled;
	}
	public int getKnightsLost()
	{
		return knightsLost;
	}
	public int getPeasantsKilled()
	{
		return peasantsKilled;
	}
	public int getPeasantsLost()
	{
		return peasantsLost;
	}
	public int getRunningScore()
	{
		return runningScore;
	}
}
