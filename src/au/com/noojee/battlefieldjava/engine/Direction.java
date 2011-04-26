package au.com.noojee.battlefieldjava.engine;

import java.util.Random;

import au.com.noojee.battlefieldjava.ruler.ILocation;

public enum Direction
{
	MOVE_N(1), // north
	MOVE_NE(2), // northeast
	MOVE_E(3), // east
	MOVE_SE(4), // south east
	MOVE_S(5), // south
	MOVE_SW(6), // south west
	MOVE_W(7), // west
	MOVE_NW(8), // north west
	MOVE_NONE(0); // do nothing (used to cancel a move)

	static Random rdm = new Random();

	int direction;

	Direction(int direction)
	{
		this.direction = direction;
	}

	// Generate a random direction
	public static Direction random()
	{
		return valueOf(rdm.nextInt(8));
	}

	public static Direction valueOf(int direction)
	{
		Direction result = MOVE_NONE;
		for (Direction current : values())
		{
			if (current.ordinal() == direction)
			{
				result = current;
				break;
			}
		}
		return result;
	}

	public Direction rotate180()
	{
		int direct180;

		if (direction > 4)
			direct180 = ((direction + 4) % 9) + 1;
		else
			direct180 = ((direction + 4) % 9);

		return valueOf(direct180);
	}

	/**
	 * @param dir
	 * @return
	 */
	public Direction rotate45()
	{
		int direct45;

		if (direction > 7)
			direct45 = ((direction + 1) % 9) + 1;
		else
			direct45 = ((direction + 1) % 9);

		return valueOf(direct45);
	}

	public static Direction getDirectionTo(int x1, int y1, int x2, int y2)
	{
		Direction direction = MOVE_NONE;

		if (x1 < 0 || x1 > GameSettings.getWIDTH() - 1 || x2 < 0 || x2 > GameSettings.getWIDTH() - 1 || y1 < 0
				|| y1 > GameSettings.getHEIGHT() - 1 || y2 < 0 || y2 > GameSettings.getHEIGHT() - 1)
			throw new IllegalArgumentException("Invalid co-ordinate must be on the grid");

		// Determine quadrant
		double a = x2 - x1;
		double b = y2 - y1;
		// double c = Math.sqrt(Math.pow(a, 2.0) + Math.pow(b, 2.0));

		Compass quadrant;
		if (a > 0)
		{
			if (b > 0)
				quadrant = Compass.QUADRANT_BOTTOM_RIGHT;
			else if (b < 0)
				quadrant = Compass.QUADRANT_TOP_RIGHT;
			else
			{
				quadrant = Compass.QUADRANT_POINT;
				direction = MOVE_E;
			}
		}
		else if (a < 0)
		{
			if (b > 0)
				quadrant = Compass.QUADRANT_BOTTOM_LEFT;
			else if (b < 0)
				quadrant = Compass.QUADRANT_TOP_LEFT;
			else
			{
				quadrant = Compass.QUADRANT_POINT;
				direction = MOVE_W;
			}
		}
		else
		{
			if (b > 0)
			{
				quadrant = Compass.QUADRANT_POINT;
				direction = MOVE_S;
			}
			else if (b < 0)
			{
				quadrant = Compass.QUADRANT_POINT;
				direction = MOVE_N;
			}
			else
			{
				quadrant = Compass.QUADRANT_POINT;
				direction = MOVE_NONE;
			}
		}

		if (quadrant != Compass.QUADRANT_POINT)
		{
			double angle = Math.toDegrees(Math.atan(Math.abs(b) / Math.abs(a)));
			if (angle > 45 + 11.25)
				direction = valueOf(quadrant.intValue() + 2);
			else if (angle < 45 - 11.25)
				direction = valueOf(quadrant.intValue());
			else
				direction = valueOf(quadrant.intValue() + 1);
			//
			// // Check for rollover in the top left quadrant.
			// if (direction == 9)
			// direction = Direction.MOVE_N;

		}
		// assert direction > 0 && direction < 9;

		return direction;
	}

	public static Direction getDirectionTo(ILocation location, Location pnt)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
