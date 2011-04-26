/*
 * Created on 5/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.ui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import au.com.noojee.battlefieldjava.engine.GameSettings;


/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SettingsDialog extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JPanel	jContentPane		= null;
	private JLabel				jLabel				= null;
	private JLabel				jLabel1				= null;
	private JLabel				jLabel2				= null;
	private JLabel				jLabel3				= null;
	private JButton				jButton				= null;
	private JButton				jButton1			= null;
	protected static final int	OK					= 1;
	protected static final int	CANCEL				= 0;
	protected int				result;
	private JTextField			jWidth				= null;
	private JTextField			jHeight				= null;
	private JTextField			jTurns				= null;
	private JTextField			jTurnDuration		= null;
	private JLabel				jLabel10			= null;
	private JLabel				jLabel11			= null;
	private JLabel				jLabel12			= null;
	private JLabel				jLabel13			= null;
	private JLabel				jLabel14			= null;
	private JLabel				jLabel15			= null;
	private JLabel				jLabel16			= null;
	private JLabel				jLabel17			= null;
	private JTextField			JStrengthCastles	= null;
	private JTextField			jStrengthKnights	= null;
	private JTextField			jStrengthPeasants	= null;
	private JTextField			jPopulationCastles	= null;
	private JTextField			jPopulationKnights	= null;
	private JTextField			jPopulationPeasants	= null;
	private JCheckBox			jDebugMode			= null;

	/**
	 * @throws java.awt.HeadlessException
	 */
	public SettingsDialog(JFrame parent)
	{
		super(parent);
		initialize();
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton()
	{
		if (jButton == null)
		{
			jButton = new JButton();
			jButton.setBounds(327, 18, 87, 30);
			jButton.setText("OK");
			jButton.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent evt)
				{
					SettingsDialog.this.setVisible(false);
					SettingsDialog.this.dispose();
					try
					{
						GameSettings.setWIDTH(new Integer(jWidth.getText()).intValue());
						GameSettings.setHEIGHT(new Integer(jHeight.getText()).intValue());
						GameSettings.setRounds(new Integer(jTurns.getText()).intValue());
						GameSettings.setTurnDuration(new Integer(jTurnDuration.getText())
								.intValue());
						GameSettings.setStengthCastles(new Integer(JStrengthCastles.getText())
								.intValue());
						GameSettings.setStengthKnights(new Integer(jStrengthKnights.getText())
								.intValue());
						GameSettings.setStrengthPeasants(new Integer(jStrengthPeasants.getText())
								.intValue());
						GameSettings.setPopulationCastles(new Integer(jPopulationCastles.getText())
								.intValue());
						GameSettings.setPopulationKnights(new Integer(jPopulationKnights.getText())
								.intValue());
						GameSettings.setPopulationPeasants(new Integer(jPopulationPeasants
								.getText()).intValue());
						GameSettings.setDebugMode(new Boolean(jDebugMode.isSelected())
								.booleanValue());

						GameSettings.saveSettings();
					}
					catch (Exception e)
					{
						Utilities.showException(e);
					}

					result = OK;
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1()
	{
		if (jButton1 == null)
		{
			jButton1 = new JButton();
			jButton1.setBounds(326, 57, 89, 33);
			jButton1.setText("Cancel");
			jButton1.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					SettingsDialog.this.setVisible(false);
					SettingsDialog.this.dispose();
					result = CANCEL;

				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jTextField6	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJWidth()
	{
		if (jWidth == null)
		{
			jWidth = new JTextField();
			jWidth.setBounds(125, 15, 40, 20);
			jWidth.setText(Integer.toString(GameSettings.getWIDTH()));
			jWidth.setToolTipText("The width of the board in columns.");
		}
		return jWidth;
	}

	/**
	 * This method initializes jTextField6	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJHeigth()
	{
		if (jHeight == null)
		{
			jHeight = new JTextField();
			jHeight.setBounds(125, 40, 40, 20);
			jHeight.setText(Integer.toString(GameSettings.getHEIGHT()));
			jHeight.setToolTipText("The height of the board in rows");
		}
		return jHeight;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextField getJTurns()
	{
		if (jTurns == null)
		{
			jTurns = new JTextField();
			jTurns.setBounds(125, 65, 60, 20);
			jTurns.setText(Integer.toString(GameSettings.getRounds()));
			jTurns.setToolTipText("The number of turns for the game.");
		}
		return jTurns;
	}

	/**
	 * This method initializes jTextField6	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTurnDuration()
	{
		if (jTurnDuration == null)
		{
			jTurnDuration = new JTextField();
			jTurnDuration.setBounds(125, 90, 60, 20);
			jTurnDuration.setText(Integer.toString(GameSettings.getTurnDuration()));
			jTurnDuration
					.setToolTipText("The time (in milliseconds) for each time (-1 for no time limit).");

		}
		return jTurnDuration;
	}

	/**
	 * This method initializes jTextField6	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJStrengthCastles()
	{
		if (JStrengthCastles == null)
		{
			JStrengthCastles = new JTextField();
			JStrengthCastles.setBounds(125, 165, 60, 20);
			JStrengthCastles.setText(Integer.toString(GameSettings.getStengthCastles()));
			JStrengthCastles.setToolTipText("The initial strenght of a castle.");
		}
		return JStrengthCastles;
	}

	/**
	 * This method initializes jTextField6	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJStrengthKnights()
	{
		if (jStrengthKnights == null)
		{
			jStrengthKnights = new JTextField();
			jStrengthKnights.setBounds(125, 190, 60, 20);
			jStrengthKnights.setText(Integer.toString(GameSettings.getStengthKnights()));
			jStrengthKnights.setToolTipText("The initial strength of each knight.");
		}
		return jStrengthKnights;
	}

	/**
	 * This method initializes jTextField6	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJStrengthPeasants()
	{
		if (jStrengthPeasants == null)
		{
			jStrengthPeasants = new JTextField();
			jStrengthPeasants.setBounds(125, 215, 60, 20);
			jStrengthPeasants.setText(Integer.toString(GameSettings.getStrengthPeasants()));
			jStrengthPeasants.setToolTipText("The initial strength of each Peasant.");
		}
		return jStrengthPeasants;
	}

	/**
	 * This method initializes jTextField6	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJPopulationCastles()
	{
		if (jPopulationCastles == null)
		{
			jPopulationCastles = new JTextField();
			jPopulationCastles.setBounds(320, 165, 40, 20);
			jPopulationCastles.setText(Integer.toString(GameSettings.getPopulationCastles()));
			jPopulationCastles.setToolTipText("In initial number of castles for each ruler.");
		}
		return jPopulationCastles;
	}

	/**
	 * This method initializes jTextField6	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJPopulationKnights()
	{
		if (jPopulationKnights == null)
		{
			jPopulationKnights = new JTextField();
			jPopulationKnights.setBounds(320, 190, 40, 20);
			jPopulationKnights.setText(Integer.toString(GameSettings.getPopulationKnights()));
			jPopulationKnights.setToolTipText("The initial number of knights for each ruler.");
		}
		return jPopulationKnights;
	}

	/**
	 * This method initializes jTextField6	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJPopulationPeasants()
	{
		if (jPopulationPeasants == null)
		{
			jPopulationPeasants = new JTextField();
			jPopulationPeasants.setBounds(320, 215, 40, 20);
			jPopulationPeasants.setText(Integer.toString(GameSettings.getPopulationPeasants()));
			jPopulationPeasants.setToolTipText("The initial number of peasants for each ruler.");
		}
		return jPopulationPeasants;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJDebugMode()
	{
		if (jDebugMode == null)
		{
			jDebugMode = new JCheckBox();
			jDebugMode.setBounds(326, 104, 95, 17);
			jDebugMode.setText("Debug Mode");
			jDebugMode.setSelected(GameSettings.getDebugMode());
			jDebugMode.setToolTipText("Generate Exceptions on Illegal Moves");
		}
		return jDebugMode;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setTitle("Game Settings");
		this.setSize(439, 282);
		this.setContentPane(getJContentPane());
		this.setModal(true);
		this.getRootPane().setDefaultButton(getJButton());
		Utilities.centerComponentOnParent(getParent(), this);
		jWidth.setText(Integer.toString(GameSettings.getWIDTH()));
		//this.setFocusTraversalPolicy(new DefaultFocusTraversalPolicy ());
		//FocusManager.setCurrentManager(new ContainerOrderFocusManager());
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
			jContentPane.setLayout(null);
			jLabel17 = new JLabel();
			jLabel16 = new JLabel();
			jLabel15 = new JLabel();
			jLabel14 = new JLabel();
			jLabel13 = new JLabel();
			jLabel12 = new JLabel();
			jLabel11 = new JLabel();
			jLabel10 = new JLabel();
			jLabel3 = new JLabel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jLabel.setBounds(18, 15, 90, 20);
			jLabel.setText("Width:");
			jLabel1.setBounds(18, 40, 90, 20);
			jLabel1.setText("Height:");
			jLabel2.setBounds(18, 65, 90, 20);
			jLabel2.setText("Rounds:");
			jLabel3.setBounds(18, 90, 90, 20);
			jLabel3.setText("Turn Duration:");
			jLabel10.setBounds(18, 140, 137, 20);
			jLabel10.setText("Strength:");
			jLabel11.setBounds(208, 140, 207, 20);
			jLabel11.setText("Population:");
			jLabel12.setBounds(25, 165, 90, 20);
			jLabel12.setText("Castles:");
			jLabel13.setBounds(25, 190, 90, 20);
			jLabel13.setText("Knights:");
			jLabel14.setBounds(25, 215, 90, 20);
			jLabel14.setText("Peasants:");
			jLabel15.setBounds(215, 165, 90, 20);
			jLabel15.setText("Castles:");
			jLabel16.setBounds(215, 190, 90, 20);
			jLabel16.setText("Knights:");
			jLabel17.setBounds(215, 215, 90, 20);
			jLabel17.setText("Peasants:");
			jContentPane.add(getJWidth(), null);
			jContentPane.add(getJHeigth(), null);
			jContentPane.add(getJTurns(), null);
			jContentPane.add(getJTurnDuration(), null);
			jContentPane.add(getJDebugMode(), null);
			jContentPane.add(getJStrengthCastles(), null);
			jContentPane.add(getJStrengthKnights(), null);
			jContentPane.add(getJStrengthPeasants(), null);
			jContentPane.add(getJPopulationCastles(), null);
			jContentPane.add(getJPopulationKnights(), null);
			jContentPane.add(getJPopulationPeasants(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(jLabel3, null);
			jContentPane.add(jLabel10, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(jLabel12, null);
			jContentPane.add(jLabel13, null);
			jContentPane.add(jLabel14, null);
			jContentPane.add(jLabel15, null);
			jContentPane.add(jLabel16, null);
			jContentPane.add(jLabel17, null);
		}
		return jContentPane;
	}

	public int getResult()
	{
		return result;
	}
} //  @jve:decl-index=0:visual-constraint="10,10"
