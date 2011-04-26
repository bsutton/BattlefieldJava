/*
 * Created on 14/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.ui;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JList;

/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class JListWithTips extends JList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2322348539313328109L;

	public String getToolTipText(MouseEvent e)
	{
		int row = locationToIndex(e.getPoint());
		return getModel().getElementAt(row).toString();
	}

	public Point getToolTipLocation(MouseEvent e)
	{
		int row = locationToIndex(e.getPoint());
		return indexToLocation(row);
	}
}