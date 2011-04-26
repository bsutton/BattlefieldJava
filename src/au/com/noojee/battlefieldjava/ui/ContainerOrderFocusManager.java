/*
 * Created on 16/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.ui;

import java.awt.Component;
import java.awt.Container;

import javax.swing.DefaultFocusManager;

/** A Simple Swing FocusManager that walks through components in the
 *    order they were added to containers, the same way AWT works
 *  NOTE: This code provided "as is", and has only undergone about
 *        10 minutes of testing
 *  ALSO: I'm sure this could be made slightly more efficient
 *        somewhere...
 */
public class ContainerOrderFocusManager extends DefaultFocusManager
{

	/** Return order based on order in which
	 *    components were added to containers
	 */
	public boolean compareTabOrder(Component a, Component b)
	{
		// find a common container for the two components
		Container commonContainer;
		for (Component lookA = a; a != null; a = commonContainer)
		{
			commonContainer = lookA.getParent();
			for (; b != null; b = b.getParent())
				if (commonContainer.isAncestorOf(b))
					// determine which is found first
					return (depthFindFirst(commonContainer, a, b) == 1);
		}

		// if neither share a parent container,
		//   do the normal focus search
		return super.compareTabOrder(a, b);
	}

	/** Helper method that walks through containers, depth-first,
	 *  returning
	 *    0: container doesn't contain either a or b
	 *    1: found a first
	 *    2: found b first
	 */
	protected int depthFindFirst(Container c, Component a, Component b)
	{
		Component[] comps = c.getComponents();
		for (int i = 0; i < comps.length; i++)
			if (comps[i] == a)
				return 1;
			else if (comps[i] == b)
				return 2;
			else if (comps[i] instanceof Container)
			{
				int result = depthFindFirst((Container) comps[i], a, b);
				if (result > 0)
					return result;
			}
		return 0;
	}
}

