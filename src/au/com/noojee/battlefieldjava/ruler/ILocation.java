package au.com.noojee.battlefieldjava.ruler;

public interface ILocation
{

	public IRuler getOwner();

	public int getY();

	public int getX();
	
	public int getDistanceTo(int x, int y);

	public int getDistanceTo(IPiece piece);

}
