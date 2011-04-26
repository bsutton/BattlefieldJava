package au.com.noojee.battlefieldjava.engine;

public enum Compass
{
	// QUADRANTS of the compass used when calculating the direction
	// to another object
	QUADRANT_POINT(0),	// One of the four major points of the compass
	QUADRANT_TOP_RIGHT(1),
	QUADRANT_BOTTOM_RIGHT(3),
	QUADRANT_TOP_LEFT(7),
	QUADRANT_BOTTOM_LEFT(5);
	
	int quadrant;
	
	Compass(int quadrant)
	{
		this.quadrant = quadrant;
	}

	public int intValue()
	{
		return this.quadrant;
	}

}
