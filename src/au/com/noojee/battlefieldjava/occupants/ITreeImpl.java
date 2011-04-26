package au.com.noojee.battlefieldjava.occupants;

import au.com.noojee.battlefieldjava.ruler.IOccupant;

public class ITreeImpl implements IOccupant
{
	int id;
	
	public void setId(int id)
	{
		this.id = id;
	}
	public int getId()
	{
		return id;
	}

}
