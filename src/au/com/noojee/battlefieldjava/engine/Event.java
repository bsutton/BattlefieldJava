/*
 * Created on 10/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.engine;

import au.com.noojee.battlefieldjava.occupants.Occupant;


/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Event
{

	public static final int			ACTION_MOVE		= 0;
	public static final int			ACTION_ATTACK	= 1;
	public static final int			ACTION_ATTACKED	= 2;
	public static final int			ACTION_KILLED	= 3;
	public static final int			ACTION_CAPTURED	= 4;
	public static final int			ACTION_BIRTH	= 5;

	private static final String[]	actions			= new String[]{"Move", "Attack", "Attacked",
			"Killed", "Captured", "Birth"			};

	private final Occupant			source;
	private final int				action;
	private final String			description;
	private final Occupant			target;
	private final int				turn			= World.getInstance().getCurrentRound();

	private String					display;

	public Event(Occupant source, int action, String description, Occupant target)
	{
		this.source = source;
		this.action = action;
		this.description = description;
		this.target = target;
		this.display = toString();
	}

	/**
	 * @param subject
	 * @param action_move2
	 */
	public Event(Occupant source, int action)
	{
		this.source = source;
		this.action = action;
		this.description = "";
		this.target = null;
		this.display = toString();
	}

	/**
	 * @param subject
	 * @param action_move2
	 * @param string
	 */
	public Event(Occupant source, int action, String description)
	{
		this.source = source;
		this.action = action;
		this.description = description;
		this.target = null;
		this.display = toString();
	}

	public String toString()
	{
		if (display == null)
		{
			StringBuffer sb = new StringBuffer();
			sb.append("[" + (turn) + "]");
			sb.append(" " + actions[action]);
			sb.append(" ID=" + source.getId());
			sb.append(" " + (target != null ? target.getClass().getSimpleName() : ""));
			sb.append(" " + source.getLocation().toString());
			sb.append(" " + description);
			if (target != null)
			{
				sb.append(" Target=" + target.getId());
				sb.append(" " + target.getClass().getSimpleName());
				sb.append(" " + target.getLocation().toString());

			}

			display = sb.toString();
		}
		return display;
	}

}