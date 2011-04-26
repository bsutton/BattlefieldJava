/*
 * Created on 13/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import au.com.noojee.battlefieldjava.engine.GameSettings;
import au.com.noojee.battlefieldjava.engine.World;



/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Test extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7984121684528401561L;
	private javax.swing.JPanel	jContentPane	= null;
	private BattleField				battleField			= null;
	private JTabbedPane			jTabbedPane		= null;
	private JTabbedPane			jTabbedPane1	= null;
	private JPanel				jPanel1			= null;

	/**
	 * @throws java.awt.HeadlessException
	 */
	public Test()
	{
		super();
		initialize();
		World.init(battleField);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		//		this.setSize(300,200);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jContentPane.add(getLeftPanel(), java.awt.BorderLayout.WEST);
			jContentPane.add(getBattleField(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private BattleField getBattleField()
	{
		if (battleField == null)
		{
			battleField = new BattleField(GameSettings.getWIDTH(), GameSettings.getHEIGHT());
		}
		return battleField;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getLeftPanel()
	{
		if (jTabbedPane == null)
		{
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setMaximumSize(new java.awt.Dimension(150, 1000));
			jTabbedPane.addTab("Controls", null, getJTabbedPane1(), null);
			jTabbedPane.addTab("Event Log", null, getJPanel1(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jTabbedPane1	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane1()
	{
		if (jTabbedPane1 == null)
		{
			jTabbedPane1 = new JTabbedPane();
			jTabbedPane1.setPreferredSize(new java.awt.Dimension(150, 600));
		}
		return jTabbedPane1;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1()
	{
		if (jPanel1 == null)
		{
			jPanel1 = new JPanel();
			jPanel1.add(new EventLogWindow());
			jPanel1.setPreferredSize(new java.awt.Dimension(150, 32000));
		}
		return jPanel1;
	}

	public static void main(String[] args)
	{
		Test a = new Test();
		a.setVisible(true);

	}


}