/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import au.com.noojee.battlefieldjava.engine.Location;





/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BattleField extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1841319656207857669L;
	private  int height;
	private  int width;
	
	private final BattleFieldCanvas canvas;
	
	BattleField(int width, int height)
	{
		this.height = height;
		this.width = width;
		
		this.setLayout(new BorderLayout());
		canvas = new BattleFieldCanvas(this);
		canvas.setSize(this.getSize());
		this.add(canvas, BorderLayout.CENTER);
	}
	/**
	 * 
	 */
	void initialise()
	{
	}
	/**
	 * @return
	 */
	public int getFieldWidth()
	{
		return width;
	}
	/**
	 * @return
	 */
	public int getFieldHeight()
	{
		return height;
	}
	
	public void setShowIDs(boolean showIDs)
	{
		canvas.setShowIDs(showIDs);
	}
	
	/**
	 * 
	 */
	public void refresh()
	{
		canvas.invalidate();
		canvas.paint(canvas.getGraphics());
		
	}
	/**
	 * @param location
	 */
	public void refresh(Location location)
	{
		canvas.refresh(canvas.getGraphics(), location);
	}
	/**
	 * @param height
	 * @param width
	 */
	public void reset(int width, int heigth)
	{
		this.height = heigth;
		this.width = width;
		canvas.clear(canvas.getGraphics());
		refresh();

	}
	
	
}
