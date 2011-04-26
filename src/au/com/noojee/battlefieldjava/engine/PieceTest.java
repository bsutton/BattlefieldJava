/*
 * Created on 27/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.engine;

import junit.framework.TestCase;

/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PieceTest extends TestCase
{

	public void testDirectionTo()
	{
		Direction direction;
		
		// Test no move
		direction = Direction.getDirectionTo(10, 10, 10, 10);
		assertTrue(direction == Direction.MOVE_NONE);

		// Test the four points of the compass
		direction = Direction.getDirectionTo(10, 10, 11, 10);
		assertTrue(direction == Direction.MOVE_E);
		direction = Direction.getDirectionTo(10, 10, 10, 11);
		assertTrue(direction == Direction.MOVE_S);
		direction = Direction.getDirectionTo(10, 10, 9, 10);
		assertTrue(direction == Direction.MOVE_W);
		direction = Direction.getDirectionTo(10, 10, 10, 9);
		assertTrue(direction == Direction.MOVE_N);
		
		// Test the for intermediate points of the compass
		direction = Direction.getDirectionTo(10, 10, 11, 11);
		assertTrue(direction == Direction.MOVE_SE);
		direction = Direction.getDirectionTo(10, 10, 11, 9);
		assertTrue(direction == Direction.MOVE_NE);
		direction = Direction.getDirectionTo(10, 10, 9, 9);
		assertTrue(direction == Direction.MOVE_NW);
		direction = Direction.getDirectionTo(10, 10, 9, 11);
		assertTrue(direction == Direction.MOVE_SW);
		
		// TODO Test the boundary cases
		// 
	}

	public static void main(String[] args)
	{
		junit.swingui.TestRunner.run(PieceTest.class);
	}

}