/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.occupants;

import java.awt.Image;

import au.com.noojee.battlefieldjava.engine.GameSettings;
import au.com.noojee.battlefieldjava.engine.Ruler;
import au.com.noojee.battlefieldjava.images.ImageLoader;
import au.com.noojee.battlefieldjava.ruler.IPeasant;



/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Peasant extends MobilePiece 
{
	final private IPeasant userPeasant;

	public Peasant(Ruler owner, IPeasant userPeasant)
	{
		super(owner, userPeasant, GameSettings.getStrengthPeasants());
		this.userPeasant = userPeasant;
		this.userPeasant.setId(this.id);
	}

	public Image getImage()
	{
		return ImageLoader.getImage("peasant.png");

	}

	public IPeasant getUserPeasant()
	{
		return userPeasant;
	}

	@Override
	public boolean canMove()
	{
		return true;
	}

}