/*
 * Created on 6/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.engine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.configuration.ConfigurationException;

import au.com.noojee.battlefieldjava.config.Loader;

/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GameSettings
{

	public static final int		DEFAULT_SETTINGS_WIDTH					= 40;
	public static final int		DEFAULT_SETTINGS_HEIGHT					= 40;
	public static final int		DEFAULT_SETTINGS_TURNS					= 500;
	public static final int		DEFAULT_SETTINGS_TURN_DURATION			= -1;
	public static final int		DEFAULT_SETTINGS_STENGTH_CASTLES		= 1000;
	public static final int		DEFAULT_SETTINGS_STENGTH_KNIGHTS		= 100;
	public static final int		DEFAULT_SETTINGS_STENGTH_PEASANTS		= 0;
	public static final int		DEFAULT_SETTINGS_POPULATION_CASTLES		= 1;
	public static final int		DEFAULT_SETTINGS_POPULATION_KNIGHTS		= 10;
	public static final int		DEFAULT_SETTINGS_POPULATION_PEASANTS	= 10;
	public static final boolean	DEFAULT_SETTINGS_DEBUG_MODE				= true;

	private static int			WIDTH									= DEFAULT_SETTINGS_WIDTH;
	private static int			HEIGHT									= DEFAULT_SETTINGS_HEIGHT;
	private static int			ROUNDS									= DEFAULT_SETTINGS_TURNS;
	private static int			TURN_DURATION							= DEFAULT_SETTINGS_TURN_DURATION;
	private static int			STENGTH_CASTLES							= DEFAULT_SETTINGS_STENGTH_CASTLES;
	private static int			STENGTH_KNIGHTS							= DEFAULT_SETTINGS_STENGTH_KNIGHTS;
	private static int			STENGTH_PEASANTS						= DEFAULT_SETTINGS_STENGTH_PEASANTS;
	private static int			POPULATION_CASTLES						= DEFAULT_SETTINGS_POPULATION_CASTLES;
	private static int			POPULATION_KNIGHTS						= DEFAULT_SETTINGS_POPULATION_KNIGHTS;
	private static int			POPULATION_PEASANTS						= DEFAULT_SETTINGS_POPULATION_PEASANTS;
	private static boolean		DEBUG_MODE								= DEFAULT_SETTINGS_DEBUG_MODE;

	/**
	 * @param wIDTH The wIDTH to set.
	 */
	public static void setWIDTH(int wIDTH)
	{
		WIDTH = wIDTH;
		World.WIDTH = wIDTH;
	}

	/**
	 * @return Returns the wIDTH.
	 */
	public static int getWIDTH()
	{
		return WIDTH;
	}

	/**
	 * @param hEIGHT The hEIGHT to set.
	 */
	public static void setHEIGHT(int hEIGHT)
	{
		HEIGHT = hEIGHT;
		World.HEIGHT = hEIGHT;
	}

	/**
	 * @return Returns the hEIGHT.
	 */
	public static int getHEIGHT()
	{
		return HEIGHT;
	}

	/**
	 * @param rounds The ROUNDS to set.
	 */
	public static void setRounds(int rounds)
	{
		ROUNDS = rounds;
	}

	/**
	 * @return Returns the ROUNDS.
	 */
	public static int getRounds()
	{
		return ROUNDS;
	}

	/**
	 * @param tURN_DURATION The tURN_DURATION to set.
	 */
	public static void setTurnDuration(int tURN_DURATION)
	{
		TURN_DURATION = tURN_DURATION;
	}

	/**
	 * @return Returns the tURN_DURATION.
	 */
	public static int getTurnDuration()
	{
		return TURN_DURATION;
	}

	/**
	 * @param sTENGTH_CASTLES The sTENGTH_CASTLES to set.
	 */
	public static void setStengthCastles(int sTENGTH_CASTLES)
	{
		STENGTH_CASTLES = sTENGTH_CASTLES;
	}

	/**
	 * @return Returns the sTENGTH_CASTLES.
	 */
	public static int getStengthCastles()
	{
		return STENGTH_CASTLES;
	}

	/**
	 * @param sTENGTH_KNIGHTS The sTENGTH_KNIGHTS to set.
	 */
	public static void setStengthKnights(int sTENGTH_KNIGHTS)
	{
		STENGTH_KNIGHTS = sTENGTH_KNIGHTS;
	}

	/**
	 * @return Returns the sTENGTH_KNIGHTS.
	 */
	public static int getStengthKnights()
	{
		return STENGTH_KNIGHTS;
	}

	/**
	 * @param sTENGTH_PEASANTS The sTENGTH_PEASANTS to set.
	 */
	public static void setStrengthPeasants(int sTENGTH_PEASANTS)
	{
		STENGTH_PEASANTS = sTENGTH_PEASANTS;
	}

	/**
	 * @return Returns the sTENGTH_PEASANTS.
	 */
	public static int getStrengthPeasants()
	{
		return STENGTH_PEASANTS;
	}

	/**
	 * @param pOPULATION_CASTLES The pOPULATION_CASTLES to set.
	 */
	public static void setPopulationCastles(int pOPULATION_CASTLES)
	{
		POPULATION_CASTLES = pOPULATION_CASTLES;
	}

	/**
	 * @return Returns the pOPULATION_CASTLES.
	 */
	public static int getPopulationCastles()
	{
		return POPULATION_CASTLES;
	}

	/**
	 * @param pOPULATION_KNIGHTS The pOPULATION_KNIGHTS to set.
	 */
	public static void setPopulationKnights(int pOPULATION_KNIGHTS)
	{
		POPULATION_KNIGHTS = pOPULATION_KNIGHTS;
	}

	/**
	 * @return Returns the pOPULATION_KNIGHTS.
	 */
	public static int getPopulationKnights()
	{
		return POPULATION_KNIGHTS;
	}

	/**
	 * @param pOPULATION_PEASANTS The pOPULATION_PEASANTS to set.
	 */
	public static void setPopulationPeasants(int pOPULATION_PEASANTS)
	{
		POPULATION_PEASANTS = pOPULATION_PEASANTS;
	}

	/**
	 * @return Returns the pOPULATION_PEASANTS.
	 */
	public static int getPopulationPeasants()
	{
		return POPULATION_PEASANTS;
	}

	public static void saveSettings() throws ConfigurationException, IOException
	{
		Loader.getInstance().saveSettings();
	}

	/**
	 * @param b
	 */
	public static void setDebugMode(boolean debugMode)
	{
		DEBUG_MODE = debugMode;

	}
	
	public static boolean getDebugMode()
	{
		return DEBUG_MODE;
	}

	/**
	 * @param s
	 * @throws IOException
	 */
	public static void writeObject(ObjectOutputStream s) throws IOException
	{
		s.write(WIDTH);	
		s.write(HEIGHT);
		s.write(ROUNDS);
		s.write(TURN_DURATION);
		s.write(STENGTH_CASTLES);
		s.write( STENGTH_KNIGHTS);
		s.write(STENGTH_PEASANTS);
		s.write(POPULATION_CASTLES);
		s.write(POPULATION_KNIGHTS);
		s.write(POPULATION_PEASANTS);
		s.writeBoolean(DEBUG_MODE);
	}
	/**
	 * @param s
	 * @throws IOException
	 */
	public static void readObject(ObjectInputStream s) throws IOException
	{
		WIDTH = s.read();	
		HEIGHT = s.read();
		ROUNDS = s.read();
		TURN_DURATION = s.read();
		STENGTH_CASTLES = s.read();
		STENGTH_KNIGHTS = s.read( );
		STENGTH_PEASANTS = s.read();
		POPULATION_CASTLES = s.read();
		POPULATION_KNIGHTS = s.read();
		POPULATION_PEASANTS = s.read();
		DEBUG_MODE = s.readBoolean();
	}

}