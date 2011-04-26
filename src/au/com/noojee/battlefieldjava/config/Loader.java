/*
 * Created on 29/12/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.config;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import au.com.noojee.battlefieldjava.engine.GameSettings;
import au.com.noojee.battlefieldjava.engine.RulerType;

/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Loader
{

	static final String	ROOT_NODE							= "OpenRuler";
	static final String	NODE_RULER_TYPES					= "RulerTypes";
	static final String	NODE_RULER_TYPE						= "RulerType";
	static final String	NODE_RULER_TYPE_CLASS				= ".class";
	static final String	NODE_RULER_TYPE_DESCRIPTION			= ".description";

	static final String	NODE_SETTINGS_WIDTH					= "Settings.width";
	static final String	NODE_SETTINGS_HEIGHT				= "Settings.height";
	static final String	NODE_SETTINGS_ROUNDS				= "Settings.rounds";
	static final String	NODE_SETTINGS_TURN_DURATION			= "Settings.duration";
	static final String	NODE_SETTINGS_DEBUG_MODE			= "Settings.debugMode";
	static final String	NODE_SETTINGS_STENGTH_CASTLES		= "Settings.strength.castles";
	static final String	NODE_SETTINGS_STENGTH_KNIGHTS		= "Settings.strength.knights";
	static final String	NODE_SETTINGS_STENGTH_PEASANTS		= "Settings.strength.peasants";
	static final String	NODE_SETTINGS_POPULATION_CASTLES	= "Settings.population.castles";
	static final String	NODE_SETTINGS_POPULATION_KNIGHTS	= "Settings.population.knights";
	static final String	NODE_SETTINGS_POPULATION_PEASANTS	= "Settings.population.peasants";

	private XMLConfiguration	conf;

	private Vector<RulerType>				rulers								= new Vector<RulerType>();

	private GameSettings		settings;

	private static Loader		s_self;

	private Loader() throws ConfigurationException, IOException
	{
		String userDir = System.getProperty("user.dir");
		File fileRepositories = new File(userDir, "rulers.xml");
		conf = new XMLConfiguration(fileRepositories);
		// If we are creating the config the set the root.
		if (conf.getDocument() == null)
			conf.setRootElementName(ROOT_NODE);

		conf.setAutoSave(true);

		load();
	}

	public static synchronized Loader getInstance() throws ConfigurationException, IOException
	{
		if (s_self == null)
		{
			s_self = new Loader();
		}
		return s_self;
	}

	public Iterator iterator()
	{
		return new Iterator();
	}

	public void addRulerType(RulerType ruler) throws ConfigurationException, IOException
	{
		int nIndex = find(ruler);
		if (nIndex == -1)
		{
			rulers.add(ruler);
			_add(ruler);
		}
		else
		{
			rulers.remove(nIndex);
			rulers.add(ruler);
			_update(ruler);
		}
	}

	public void removeRulerType(RulerType ruler) throws ConfigurationException, IOException
	{
		int nIndex = find(ruler);
		if (nIndex != -1)
		{
			rulers.remove(nIndex);
			_remove(nIndex);
		}
	}

	int find(RulerType ruler)
	{
		int nIndex = -1;

		Iterator iter = iterator();
		int i = 0;
		while (iter.hasNext())
		{
			RulerType item = (RulerType) iter.next();
			if (item.equals(ruler))
			{
				nIndex = i;
				break;
			}
			i++;
		}
		return nIndex;
	}

	/**
	 * 
	 */
	public void saveSettings()
	{

		addIntProperty(NODE_SETTINGS_WIDTH, GameSettings.getWIDTH());
		addIntProperty(NODE_SETTINGS_HEIGHT, GameSettings.getHEIGHT());
		addIntProperty(NODE_SETTINGS_ROUNDS, GameSettings.getRounds());
		addIntProperty(NODE_SETTINGS_TURN_DURATION, GameSettings.getTurnDuration());
		addIntProperty(NODE_SETTINGS_STENGTH_CASTLES, GameSettings.getStengthCastles());
		addIntProperty(NODE_SETTINGS_STENGTH_KNIGHTS, GameSettings.getStengthKnights());
		addIntProperty(NODE_SETTINGS_STENGTH_PEASANTS, GameSettings.getStrengthPeasants());
		addIntProperty(NODE_SETTINGS_POPULATION_CASTLES, GameSettings.getPopulationCastles());
		addIntProperty(NODE_SETTINGS_POPULATION_KNIGHTS, GameSettings.getPopulationKnights());
		addIntProperty(NODE_SETTINGS_POPULATION_PEASANTS, GameSettings.getPopulationPeasants());
		addBooleanProperty(NODE_SETTINGS_DEBUG_MODE, GameSettings.getDebugMode());

	}

	/**
	 * @param node_settings_debug_mode2
	 * @param debugMode
	 */
	private void addBooleanProperty(String key, boolean value)
	{
		conf.setProperty(key, new Boolean(value));

	}

	/**
	 * @param node_settings_width2
	 * @param width
	 */
	private void addIntProperty(String key, int value)
	{
		conf.setProperty(key, new Integer(value));

	}

	private void load() throws ConfigurationException
	{
		GameSettings
				.setWIDTH(conf.getInt(NODE_SETTINGS_WIDTH, GameSettings.DEFAULT_SETTINGS_WIDTH));
		GameSettings.setHEIGHT(conf.getInt(NODE_SETTINGS_HEIGHT,
				GameSettings.DEFAULT_SETTINGS_HEIGHT));
		GameSettings.setRounds(conf
				.getInt(NODE_SETTINGS_ROUNDS, GameSettings.DEFAULT_SETTINGS_TURNS));
		GameSettings.setTurnDuration(conf.getInt(NODE_SETTINGS_TURN_DURATION,
				GameSettings.DEFAULT_SETTINGS_TURN_DURATION));
		GameSettings.setStengthCastles(conf.getInt(NODE_SETTINGS_STENGTH_CASTLES,
				GameSettings.DEFAULT_SETTINGS_STENGTH_CASTLES));
		GameSettings.setStengthKnights(conf.getInt(NODE_SETTINGS_STENGTH_KNIGHTS,
				GameSettings.DEFAULT_SETTINGS_STENGTH_KNIGHTS));
		GameSettings.setStrengthPeasants(conf.getInt(NODE_SETTINGS_STENGTH_PEASANTS,
				GameSettings.DEFAULT_SETTINGS_STENGTH_PEASANTS));
		GameSettings.setPopulationCastles(conf.getInt(NODE_SETTINGS_POPULATION_CASTLES,
				GameSettings.DEFAULT_SETTINGS_POPULATION_CASTLES));
		GameSettings.setPopulationKnights(conf.getInt(NODE_SETTINGS_POPULATION_KNIGHTS,
				GameSettings.DEFAULT_SETTINGS_POPULATION_KNIGHTS));
		GameSettings.setPopulationPeasants(conf.getInt(NODE_SETTINGS_POPULATION_PEASANTS,
				GameSettings.DEFAULT_SETTINGS_POPULATION_PEASANTS));
		GameSettings.setDebugMode(conf.getBoolean(NODE_SETTINGS_DEBUG_MODE,
				GameSettings.DEFAULT_SETTINGS_DEBUG_MODE));

		int nRulerTypes = conf.getMaxIndex(NODE_RULER_TYPES);

		for (int i = 0; i <= nRulerTypes; i++)
		{
			String instance = getRulerTypeInstance(i);
			String className = conf.getString(instance + NODE_RULER_TYPE_CLASS);
			if (className == null)
			{
				conf.clearTree(instance);
				JOptionPane.showMessageDialog(null, "RulerTypes.xml contains a ruler (instance="
						+ i + ") with an invalid class (null). It will be removed.", "Error",
						JOptionPane.ERROR_MESSAGE);
				continue;
			}

			String description = conf.getString(instance + NODE_RULER_TYPE_DESCRIPTION);
			if (description == null)
				description = "";

			rulers.add(new RulerType(description, className));
		}
	}

	/**
	 * @param i
	 * @return
	 */
	private static String getRulerTypeInstance(int i)
	{
		return NODE_RULER_TYPES + "(" + i + ")";
	}

	protected int findRulerType(String className)
	{
		int index = -1;
		int nRulerTypes = conf.getMaxIndex(NODE_RULER_TYPES);
		for (int i = 0; i <= nRulerTypes; i++)
		{
			String instance = getRulerTypeInstance(i);
			String tmp = conf.getString(instance + NODE_RULER_TYPE_CLASS);
			if (tmp.compareTo(className) == 0)
			{
				index = i;
				break;
			}
		}
		return index;
	}

	private void _add(RulerType ruler) throws ConfigurationException
	{
		int nRulerTypes = conf.getMaxIndex(NODE_RULER_TYPES) + 1;
		String instance = getRulerTypeInstance(nRulerTypes);
		conf.addProperty(instance + NODE_RULER_TYPE_CLASS, ruler.getClassName());
		conf.addProperty(instance + NODE_RULER_TYPE_DESCRIPTION, ruler.getDescription());
	}

	/**
	 * @param ruler
	 */
	private void _update(RulerType ruler)
	{
		int nIndex = find(ruler);
		if (nIndex != -1)
		{
			String instance = getRulerTypeInstance(nIndex);
			conf.setProperty(instance + NODE_RULER_TYPE_CLASS, ruler.getClass());
			conf.setProperty(instance + NODE_RULER_TYPE_DESCRIPTION, ruler.getDescription());

		}
	}

	private void _remove(int instance) throws ConfigurationException
	{
		String path = getRulerTypeInstance(instance);
		conf.clearTree(path);
		conf.save();
	}

	public GameSettings getSettings()
	{
		return settings;
	}

	public class Iterator implements java.util.Iterator<RulerType>
	{

		int	nIndex	= 0;

		/* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		public void remove()
		{
			try
			{
				Loader.this.removeRulerType(rulers.elementAt(nIndex - 1));
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}

		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext()
		{
			return nIndex < rulers.size();
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public RulerType next()
		{
			return rulers.elementAt(nIndex++);
		}

	}

}