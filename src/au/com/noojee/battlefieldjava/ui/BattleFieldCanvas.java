/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;

import javax.swing.JComponent;

import au.com.noojee.battlefieldjava.engine.GameSettings;
import au.com.noojee.battlefieldjava.engine.Grid;
import au.com.noojee.battlefieldjava.engine.Location;
import au.com.noojee.battlefieldjava.engine.LocationIterator;
import au.com.noojee.battlefieldjava.engine.Ruler;
import au.com.noojee.battlefieldjava.engine.World;
import au.com.noojee.battlefieldjava.images.ImageLoader;
import au.com.noojee.battlefieldjava.occupants.Knight;
import au.com.noojee.battlefieldjava.occupants.Occupant;
import au.com.noojee.battlefieldjava.occupants.Piece;

/**
 * @author bsutton
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class BattleFieldCanvas extends JComponent
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3011800588006814619L;
	private static Color BORDER = new Color(125, 255, 250); // ligth blue border
	private static final Color BLACK = new Color(0, 0, 0);

	BattleField field;
	int xDelta;
	int yDelta;
	int leftMargin;
	int topMargin;
	boolean showIDs = false;

	/**
	 * @param field
	 */
	public BattleFieldCanvas(BattleField field)
	{
		this.field = field;

		this.setToolTipText("test");

	}

	public String getToolTipText(MouseEvent event)
	{
		String text = null;

		Location location = this.getLocationAtMouse(event);

		if (location != null)
		{
			Occupant piece = location.getOccupant();
			if (piece != null)
				text = piece.getTipText();
			else
				text = location.toString();
		}
		return text;

	}

	void setShowIDs(boolean showIDs)
	{
		if (this.showIDs != showIDs)
		{
			invalidate();
			paint(getGraphics());
		}

		this.showIDs = showIDs;

	}

	public synchronized void paint(Graphics graphics)
	{
		Dimension size = this.getSize();
		xDelta = size.width / field.getFieldWidth();
		yDelta = size.height / field.getFieldHeight();

		// Work out the margin area.
		leftMargin = (size.width - (field.getFieldWidth() * xDelta)) / 2;
		topMargin = (size.height - (field.getFieldHeight() * yDelta)) / 2;

		// Now draw the border.
		graphics.setColor(BORDER);
		// left border
		graphics.fillRect(0, 0, leftMargin, size.height);
		// bottom border
		graphics.fillRect(0, size.height - topMargin, size.width, topMargin);
		// top border
		graphics.fillRect(0, 0, size.width, topMargin);
		// right border
		graphics.fillRect(size.width - leftMargin, 0, leftMargin, size.height);

		graphics.setColor(BLACK);
		for (int x = 0; x < field.getFieldWidth() + 1; x++)
		{
			graphics.drawLine(xoffset(x * xDelta), yoffset(0), xoffset(x * xDelta),
					yoffset(yDelta * field.getFieldHeight()));
		}
		for (int y = 0; y < field.getFieldHeight() + 1; y++)
		{
			graphics.drawLine(xoffset(0), yoffset(y * yDelta), xoffset(xDelta * field.getFieldWidth()), yoffset(y
					* yDelta));
		}

		// Draw the pieces
		World world = World.getInstance();
		Grid grid = world.getGrid();

		graphics.setColor(Color.LIGHT_GRAY);
		LocationIterator iter = grid.getLocationIterator();
		while (iter.hasNext())
		{
			Location location = (Location) iter.next();
			clearLocation(graphics, location);
			Occupant occupant = location.getOccupant();
			draw(graphics, location, occupant);
		}
	}

	private void draw(Graphics graphics, Location location, Occupant occupant)
	{
		if (occupant != null)
		{
			drawOccupant(graphics, location, occupant);
		}
		else
			drawGrass(graphics, location);
	}
	

	private void drawGrass(Graphics graphics, Location location)
	{
		Image image;

		if (World.getLandOwner(location.getX(), location.getY()) != null)
			image = ImageLoader.getImage("unownedcrop3.png");
		else
			image = ImageLoader.getImage("grass.png");
		graphics.drawImage(image, xoffset(location.getX() * xDelta + 1), yoffset(location.getY() * yDelta + 1),
				xoffset((location.getX() + 1) * xDelta)// - (xDelta / 2)
				, yoffset((location.getY() + 1) * yDelta)// - (xDelta / 2)
				, 0, 0, image.getWidth(null), image.getHeight(null), null);

	}

	/**
	 * @param i
	 * @return
	 */
	private int xoffset(int xcoord)
	{
		return leftMargin + xcoord;
	}

	/**
	 * @param i
	 * @return
	 */
	private int yoffset(int ycoord)
	{
		return topMargin + ycoord;
	}

	/**
	 * @param graphics
	 * @param xDelta
	 * @param yDelta
	 * @param location
	 * @param occupant
	 */
	private void drawOccupant(Graphics graphics, Location location, Occupant occupant)
	{

		Image image = occupant.getImage();
		graphics.drawImage(image, xoffset(location.getX() * xDelta + 1), yoffset(location.getY() * yDelta + 1),
				xoffset((location.getX() + 1) * xDelta)// - (xDelta / 2)
				, yoffset((location.getY() + 1) * yDelta)// - (xDelta / 2)
				, 0, 0, image.getWidth(null), image.getHeight(null), null);

		if (occupant instanceof Piece && ((Piece)occupant).wasAttacked())
		{
			Image star = getStarImage();
			graphics.drawImage(star, xoffset(location.getX() * xDelta + 1), yoffset(location.getY() * yDelta + 1),
					xoffset((location.getX() + 1) * xDelta) - (int) (xDelta * .5), yoffset((location.getY() + 1)
							* yDelta)
							- (int) (yDelta * .5), 0, 0, star.getWidth(null), star.getHeight(null), null);

		}

		if (occupant instanceof Knight && ((Knight) occupant).didAttack())
		{
			Image blueStar = getBlueStarImage();
			graphics.drawImage(blueStar, xoffset(location.getX() * xDelta + 1), yoffset(location.getY() * yDelta + 1)
					+ yDelta / 2, xoffset((location.getX() + 1) * xDelta) - (int) (xDelta * .5),
					yoffset((location.getY() + 1) * yDelta) + (yDelta / 2) - (int) (yDelta * .5), 0, 0,
					blueStar.getWidth(null), blueStar.getHeight(null), null);

		}

		if (showIDs)
		{
			Color current = graphics.getColor();
			try
			{

				graphics.setColor(Color.RED);
				// TODO test that the font fits
				Font fontID = new Font("Courier", Font.PLAIN, 10);
				char[] id = Integer.toString(occupant.getId()).toCharArray();
				FontRenderContext context = ((Graphics2D) graphics).getFontRenderContext();
				Rectangle rect = fontID.getStringBounds(id, 0, id.length, context).getBounds();
				graphics.fillRect(xoffset(location.getX() * xDelta + 1) + (int) rect.getX(),
						yoffset((location.getY() + 1) * yDelta) + (int) rect.getY(), (int) rect.getWidth(),
						(int) rect.getHeight());
				graphics.setColor(Color.WHITE);

				graphics.drawChars(id, 0, id.length, xoffset(location.getX() * xDelta + 1),
						yoffset((location.getY() + 1) * yDelta));

			}
			finally
			{
				graphics.setColor(current);
			}
		}
	}

	/**
	 * @param location
	 */
	public void refresh(Graphics graphics, Location location)
	{
		Occupant occupant = location.getOccupant();
		clearLocation(graphics, location);
		draw(graphics, location, occupant);
	}

	/**
	 * @param graphics
	 * @param location
	 */
	private void clearLocation(Graphics graphics, Location location)
	{
		Color current = graphics.getColor();
		try
		{

			if (location.getOwner() == null)
			{
				graphics.setColor(Color.LIGHT_GRAY);
				graphics.fillRect(xoffset(location.getX() * xDelta + 1), yoffset(location.getY() * yDelta + 1),
						xDelta - 1, yDelta - 1);
			}
			else
			{
				graphics.setColor(((Ruler) location.getOwner()).getColor());
				graphics.fillRect(xoffset(location.getX() * xDelta + 1), yoffset(location.getY() * yDelta + 1),
						xDelta - 1, yDelta - 1);
			}
		}
		finally
		{
			graphics.setColor(current);
		}

	}

	/**
	 * 
	 */
	public void clear(Graphics graphics)
	{
		Dimension size = this.getSize();

		graphics.clearRect(0, 0, size.width, size.height);

	}

	/**
	 * @return
	 */
	public Location getLocationAtMouse(MouseEvent evt)
	{
		int x = evt.getX();
		int y = evt.getY();

		int xpos = (x - leftMargin) / xDelta;
		int ypos = (y - topMargin) / yDelta;
		World world = World.getInstance();
		Grid grid = world.getGrid();
		Location loc = null;
		if (xpos < GameSettings.getWIDTH() && ypos < GameSettings.getHEIGHT() && ypos >= 0 && xpos >= 0)
		{
			loc = grid.getLocations()[xpos][ypos];
		}
		return loc;

	}

	public Image getStarImage()
	{
		return ImageLoader.getImage("star.png");

	}

	/**
	 * @return
	 */
	private Image getBlueStarImage()
	{
		return ImageLoader.getImage("blue star.png");
	}

}