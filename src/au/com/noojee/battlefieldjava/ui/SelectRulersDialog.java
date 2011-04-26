/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.ui;

import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import au.com.noojee.battlefieldjava.config.Loader;
import au.com.noojee.battlefieldjava.engine.RulerType;


/**
 * @author bsutton
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SelectRulersDialog extends JDialog
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4728670981696811385L;

	private javax.swing.JPanel	jContentPane		= null;

	private JScrollPane			jScrollPane			= null;

	private JScrollPane			jScrollPane1		= null;

	private JList				jSelectedRulers		= null;

	private JList				jAvailableRules		= null;

	private JButton				jButton				= null;

	private JButton				jButton1			= null;

	private JLabel				jLabel				= null;

	private JLabel				jLabel1				= null;

	private JButton				runButton			= null;

	private JButton				jImport				= null;

	private RulerType[]			selectedRulerTypes	= new RulerType[0];

	private DefaultListModel	defaultListModel	= null;				// @jve:decl-index=0:

	private DefaultListModel	defaultListModel1	= null;				// @jve:decl-index=0:

	/**
	 */
	public SelectRulersDialog(JFrame parent)
	{
		super(parent);
		initialize();
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane()
	{
		if (jScrollPane == null)
		{
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(12, 32, 224, 274);
			jScrollPane.setViewportView(getJAvailableRules());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1()
	{
		if (jScrollPane1 == null)
		{
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(382, 32, 224, 279);
			jScrollPane1.setViewportView(getJSelectedRulers());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jList
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJSelectedRulers()
	{
		if (jSelectedRulers == null)
		{
			jSelectedRulers = new JList();
			jSelectedRulers.setModel(getDefaultListModel1());
			jSelectedRulers.addMouseListener(new java.awt.event.MouseAdapter()
			{
				public void mouseClicked(java.awt.event.MouseEvent e)
				{
					if (e.getClickCount() == 2)
						doRemove();
				}
			});
		}
		return jSelectedRulers;
	}

	/**
	 * This method initializes jList1
	 * 
	 * @return javax.swing.JList
	 */
	private JList getJAvailableRules()
	{
		if (jAvailableRules == null)
		{
			jAvailableRules = new JList();
			jAvailableRules.setModel(getDefaultListModel());
			jAvailableRules.addMouseListener(new java.awt.event.MouseAdapter()
			{
				public void mouseClicked(java.awt.event.MouseEvent e)
				{
					if (e.getClickCount() == 2)
					{
						doAdd();
					}
				}
			});
		}
		return jAvailableRules;
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
			jButton.setBounds(255, 63, 106, 22);
			jButton.setText("Select >>");
			jButton.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					doAdd();
				}
			});
		}
		return jButton;
	}

	/**
	 * 
	 */
	protected void doAdd()
	{
		Object[] rulerTypes = jAvailableRules.getSelectedValues();
		DefaultListModel modelTo = (DefaultListModel) jSelectedRulers
				.getModel();
		for (int i = 0; i < rulerTypes.length; i++)
		{
			modelTo.addElement(rulerTypes[i]);
		}
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
			jButton1.setBounds(255, 96, 106, 22);
			jButton1.setText("Remove <<");
			jButton1.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					doRemove();
				}
			});
		}
		return jButton1;
	}

	/**
	 * 
	 */
	protected void doRemove()
	{
		Object[] rulerTypes = jSelectedRulers.getSelectedValues();
		DefaultListModel modelFrom = (DefaultListModel) jSelectedRulers
				.getModel();
		for (int i = 0; i < rulerTypes.length; i++)
		{
			// modelTo.addElement(rulerTypes[i]);
			modelFrom.removeElement(rulerTypes[i]);
		}

	}

	/**
	 * This method initializes jButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getRunButton()
	{
		if (runButton == null)
		{
			runButton = new JButton();
			runButton.setBounds(255, 270, 106, 22);
			runButton.setText("Run");
			runButton.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					doClose();
				}
			});
		}
		return runButton;
	}

	/**
	 * 
	 */
	protected void doClose()
	{
		DefaultListModel modelFrom = (DefaultListModel) jSelectedRulers
				.getModel();
		selectedRulerTypes = new RulerType[modelFrom.getSize()];
		modelFrom.copyInto(selectedRulerTypes);

		this.setVisible(false);
		this.dispose();
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJImport()
	{
		if (jImport == null)
		{
			jImport = new JButton();
			jImport.setBounds(12, 312, 106, 22);
			jImport.setText("Import...");
			jImport.addActionListener(new java.awt.event.ActionListener()
			{

				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					doImport();
				}
			});
		}
		return jImport;
	}

	/**
	 * 
	 */
	protected void doImport()
	{
		RulerImportDialog dialog = new RulerImportDialog(this);
		dialog.setVisible(true);
		if (dialog.getResult() == JOptionPane.OK_OPTION)
		{
			RulerType rulerType = new RulerType(dialog.getDescription(), dialog
					.getClassName());
			try
			{
				Loader.getInstance().addRulerType(rulerType);
				DefaultListModel model = (DefaultListModel) jAvailableRules
						.getModel();
				model.addElement(rulerType);
			}
			catch (Exception e)
			{
				Utilities.showException(e);
			}
		}
	}

	/**
	 * This method initializes defaultListModel
	 * 
	 * @return javax.swing.DefaultListModel
	 */
	private DefaultListModel getDefaultListModel()
	{
		if (defaultListModel == null)
		{
			defaultListModel = new DefaultListModel();
		}
		return defaultListModel;
	}

	/**
	 * This method initializes defaultListModel1
	 * 
	 * @return javax.swing.DefaultListModel
	 */
	private DefaultListModel getDefaultListModel1()
	{
		if (defaultListModel1 == null)
		{
			defaultListModel1 = new DefaultListModel();
		}
		return defaultListModel1;
	}

	public static void main(String[] args)
	{
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Select Combatant Rules");
		this.setSize(627, 417);
		this.setModal(true);
		this.setContentPane(getJContentPane());
		this.getRootPane().setDefaultButton(getRunButton());
		Utilities.centerComponentOnParent(getParent(), this);

		Iterator<RulerType> iter;
		try
		{
			DefaultListModel model = (DefaultListModel) jAvailableRules
					.getModel();

			iter = Loader.getInstance().iterator();
			while (iter.hasNext())
			{
				RulerType rulerType = (RulerType) iter.next();
				model.addElement(rulerType);
			}
		}
		catch (Exception e)
		{
			Utilities.showException(e);
		}
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
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(12, 12, 213, 17);
			jLabel.setText("Available:");
			jLabel1.setBounds(382, 12, 219, 17);
			jLabel1.setText("Selected:");
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getJScrollPane1(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getRunButton(), null);
			jContentPane.add(getJImport(), null);
		}
		return jContentPane;
	}

	/**
	 * @return
	 */
	public RulerType[] getSelectedRulerTypes()
	{
		return selectedRulerTypes;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
