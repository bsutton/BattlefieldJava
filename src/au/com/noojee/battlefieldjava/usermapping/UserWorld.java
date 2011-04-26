/**
 * Provides a mapping between the World and the IWorld interface.
 * 
 * We need to give the user the ability to access certain world attributes
 * withouth allowing them to modify the world. 
 * The IWorld interface creates the isolation and this class provides mappings
 * to the real World object.
 * 
 */
package au.com.noojee.battlefieldjava.usermapping;

import java.util.Vector;

import au.com.noojee.battlefieldjava.engine.Direction;
import au.com.noojee.battlefieldjava.engine.Location;
import au.com.noojee.battlefieldjava.engine.Ruler;
import au.com.noojee.battlefieldjava.engine.World;
import au.com.noojee.battlefieldjava.occupants.Castle;
import au.com.noojee.battlefieldjava.occupants.Knight;
import au.com.noojee.battlefieldjava.occupants.MobilePiece;
import au.com.noojee.battlefieldjava.occupants.Occupant;
import au.com.noojee.battlefieldjava.occupants.Peasant;
import au.com.noojee.battlefieldjava.occupants.Piece;
import au.com.noojee.battlefieldjava.ruler.ICastle;
import au.com.noojee.battlefieldjava.ruler.IKnight;
import au.com.noojee.battlefieldjava.ruler.ILocation;
import au.com.noojee.battlefieldjava.ruler.IMobilePiece;
import au.com.noojee.battlefieldjava.ruler.IOccupant;
import au.com.noojee.battlefieldjava.ruler.IPeasant;
import au.com.noojee.battlefieldjava.ruler.IPiece;
import au.com.noojee.battlefieldjava.ruler.IRuler;
import au.com.noojee.battlefieldjava.ruler.IWorld;

public class UserWorld implements IWorld
{
	private World world;

	public UserWorld(World world)
	{
		this.world = world;
	}

	public IKnight[] getKnights(IRuler iruler)
	{
		Knight[] knights = findRuler(iruler).getKnights();

		IKnight[] iknights = new IKnight[knights.length];

		int i = 0;
		for (Knight knight : knights)
		{
			iknights[i] = knight.getUserKnight();
			i++;
		}
		return iknights;
	}

	private Ruler findRuler(IRuler iruler)
	{
		Ruler ruler = null;

		Ruler[] rulers = World.getRulers();
		for (Ruler current : rulers)
		{
			if (current.getUserRuler() == iruler)
			{
				ruler = current;
				break;
			}
		}

		return ruler;

	}

	public IPeasant[] getOwnedPeasants(IRuler iruler)
	{
		Peasant[] peasants = findRuler(iruler).getPeasants();

		IPeasant[] ipeasants = new IPeasant[peasants.length];

		int i = 0;
		for (Peasant peasant : peasants)
		{
			ipeasants[i] = peasant.getUserPeasant();
			i++;
		}
		return ipeasants;
	}

	public IPiece[] getPieces(IRuler iruler)
	{
		Piece[] pieces = findRuler(iruler).getPieces();

		IPiece[] ipieces = new IPiece[pieces.length];

		int i = 0;
		for (Piece piece : pieces)
		{
			ipieces[i] = piece.getUserPiece();
			i++;
		}
		return ipieces;
	}

	public ILocation getLocation(IPiece piece)
	{
		ILocation location = null;

		Piece[] pieces = World.getAllPieces();

		for (Piece current : pieces)
		{
			if (current.getUserPiece() == piece)
				location = current.getLocation().getUserLocation();
		}

		return location;
	}

	public ICastle[] getCastles(IRuler iruler)
	{
		Castle[] castles = findRuler(iruler).getCastles();

		ICastle[] icastles = new ICastle[castles.length];

		int i = 0;
		for (Castle castle : castles)
		{
			icastles[i] = castle.getUserCastle();
			i++;
		}
		return icastles;
	}

	public IKnight[] getOtherKnights(IRuler iruler)
	{
		Piece[] pieces = World.getAllPieces();
		Vector<IKnight> knights = new Vector<IKnight>();

		for (Piece piece : pieces)
		{
			if (piece.getRuler().getUserRuler() != iruler)
			{
				if (piece instanceof Knight)
					knights.add(((Knight) piece).getUserKnight());
			}
		}
		IKnight[] result = new IKnight[knights.size()];
		return knights.toArray(result);
	}

	public IPeasant[] getOtherPeasants(IRuler iruler)
	{
		Piece[] pieces = World.getAllPieces();
		Vector<IPeasant> peasants = new Vector<IPeasant>();

		for (Piece piece : pieces)
		{
			if (piece.getRuler().getUserRuler() != iruler)
			{
				if (piece instanceof Peasant)
					peasants.add(((Peasant) piece).getUserPeasant());
			}
		}
		IPeasant[] result = new IPeasant[peasants.size()];
		return peasants.toArray(result);
	}

	public ICastle[] getOtherCastles(IRuler iruler)
	{
		Piece[] pieces = World.getAllPieces();
		Vector<ICastle> peasants = new Vector<ICastle>();

		for (Piece piece : pieces)
		{
			if (piece.getRuler().getUserRuler() != iruler)
			{
				if (piece instanceof Castle)
					peasants.add(((Castle) piece).getUserCastle());
			}
		}
		ICastle[] result = new ICastle[peasants.size()];
		return peasants.toArray(result);
	}

	public void requestPeasants(ICastle iCastle)
	{
		// TODO Auto-generated method stub

	}

	public void requestKnights(ICastle iCastle)
	{
		// TODO Auto-generated method stub

	}

	public IOccupant getOccupant(ILocation temp)
	{
		Occupant occupant = World.getLocationAt(temp.getX(), temp.getY()).getOccupant();

		return (occupant == null ? null : occupant.getUserOccupant());
	}

	public ILocation getLocationAt(int X, int Y)
	{
		Location location = World.getLocationAt(X, Y);

		return location.getUserLocation();
	}

	public ILocation getLocationAfterMove(ILocation ilocation, Direction dir)
	{
		Location location = World.getLocationAt(ilocation.getX(), ilocation.getY());
		Location after = World.getLocationAfterMove(location, dir);
		return (after == null ? null : after.getUserLocation());
	}

	public IRuler getLandOwner(int X, int Y)
	{
		Ruler owner = World.getLandOwner(X, Y);
		return (owner == null ? null : owner.getUserRuler());
	}

	public void attack(IKnight attacker, Direction dir)
	{
		Occupant occupant = World.getOccupantAt(attacker.getLocation());
		if (occupant != null)
		{
			if (occupant instanceof Piece)
			{
				Piece piece = (Piece) occupant;
				piece.getRuler().attack((Knight) piece, dir);
			}
		}
		else
			world.getPiece(attacker).move(dir);

	}

	public void move(IPiece ipiece, Direction dir)
	{
		Occupant occupant = World.getOccupantAt(ipiece.getLocation());

		if (occupant instanceof Piece)
		{
			Piece piece = (Piece) occupant;
			piece.getRuler().move(piece, dir);
		}
	}

	public boolean hasMoved(IMobilePiece imobilePiece)
	{
		boolean hasMoved = false;
		Occupant occupant = World.getOccupantAt(imobilePiece.getLocation());
		
		if (occupant != null && occupant instanceof MobilePiece)
			hasMoved = ((MobilePiece)occupant).hasMoved();
		return hasMoved;
	}
}
