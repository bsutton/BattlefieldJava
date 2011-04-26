/*
 * Created on 3/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.engine;


/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class IllegalMoveError extends Error
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param string
	 */
	public IllegalMoveError(String string)
	{
		super(string);
	}

}
