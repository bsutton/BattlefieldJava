package au.com.noojee.battlefieldjava.occupants;

import java.awt.Image;

import au.com.noojee.battlefieldjava.images.ImageLoader;
import au.com.noojee.battlefieldjava.ruler.IOccupant;

public class Tree extends Occupant
{

	public Tree(IOccupant userOccupant)
	{
		super(userOccupant);
	}

	public boolean canMove()
	{
		return false;
	}

	@Override
	public Image getImage()
	{
		return ImageLoader.getImage("tree.png");
	}

	@Override
	public String getTipText()
	{
		return "I'm are tree, I'm are tree, I'm are tree....";
	}

}
