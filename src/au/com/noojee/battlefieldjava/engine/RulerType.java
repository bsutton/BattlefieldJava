/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.engine;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import au.com.noojee.battlefieldjava.ruler.IRuler;
import au.com.noojee.battlefieldjava.ruler.IWorld;




/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RulerType
{
	static Color PALE_GREEN = new Color(128,255,128);
	static Color ORANGE = new Color(255,128,0);
	static Color LILAC = new Color(128,128,255);
	static Color PALE_PINK = new Color(255,128,192);
	static Color GREEN = new Color(0,0xFF,0);
	static Color LIGHT_BLUE = new Color(128,255,255);
	static Color LIGHT_YELLOW = new Color(255,255,128);
	
	private String description;
	private String className;
	
	// Colour coding for rulers
	static int nextColor = 0;
	static Color[] colors = {LILAC, PALE_GREEN, ORANGE, PALE_PINK, GREEN, LIGHT_BLUE, LIGHT_YELLOW};
	

	/**
	 * @param description2
	 * @param className2
	 */
	public RulerType(String description, String className)
	{
		this.description = description;
		this.className = className;
	}
	
	public String toString()
	{
		return description;
	}

	/**
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 */
	public Ruler newInstance() throws ClassNotFoundException, NoSuchMethodException
	,  InstantiationException, IllegalAccessException, InvocationTargetException
	{
		Class<?> clazz = Class.forName(className);
		Class<?>[] clazzArgs = new Class[] { IWorld.class };
		Constructor<?> ctor = clazz.getConstructor(clazzArgs);
		java.lang.Object[] args = new java.lang.Object[] { World.getInstance().getUserWorld()};
		IRuler ruler = (IRuler)ctor.newInstance(args);
		return new Ruler(ruler, World.getInstance()
				, colors[nextColor++ % colors.length]);
	}
	public String getDescription()
	{
		return description;
	}
	public String getClassName()
	{
		return className;
	}
}
