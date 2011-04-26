/*
 * Created on 17/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.ui;

import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import au.com.noojee.battlefieldjava.engine.Ruler;
import au.com.noojee.battlefieldjava.engine.World;


/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VictoryDialog extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7638847592263130708L;
	private javax.swing.JPanel	jContentPane	= null;
	private JTable				jTable			= null;
	private JScrollPane			jScrollPane		= null;
	private JLabel				jLabel			= null;

	private DefaultTableModel defaultTableModel = null;   //  @jve:decl-index=0:
	private JButton jCloseButton = null;
	/**
	 * @throws java.awt.HeadlessException
	 */
	public VictoryDialog(JFrame parent)
	{
		super(parent);
		initialize();
		this.getRootPane().setDefaultButton(getJCloseButton());
		Utilities.centerComponentOnParent(getParent(), this);

		Ruler[] rulers = World.getRulers();
		Arrays.sort(rulers, new ScoreComparator());
		for (int i = 0; i < rulers.length; i++)
		{
			Ruler ruler = rulers[i];
			String[] row  = 
			{
					ruler.getRulerName() + ":"
					+ ruler.getSchoolName()
					, Integer.toString(ruler.getPoints())
			};
			getDefaultTableModel().addRow(row);
		}
		
	}

	public static void main(String[] args)
	{
		//new VictoryDialog().setVisible(true);
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setTitle("Battle Results");
		this.setSize(445, 302);
		this.setContentPane(getJContentPane());
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
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new java.awt.BorderLayout());
			jLabel.setText(" ");
			jContentPane.add(this.getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(jLabel, java.awt.BorderLayout.NORTH);
			jContentPane.add(getJCloseButton(), java.awt.BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable()
	{
		if (jTable == null)
		{
			jTable = new JTable();
			jTable.setModel(getDefaultTableModel());
		}
		return jTable;
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
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}
	/**
	 * This method initializes defaultTableModel	
	 * 	
	 * @return javax.swing.table.DefaultTableModel	
	 */    
	private DefaultTableModel getDefaultTableModel() {
		if (defaultTableModel == null) {
			defaultTableModel = new DefaultTableModel();
		}
		return defaultTableModel;
	}
	
	class ScoreComparator implements Comparator<Ruler>
	{

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Ruler lhs, Ruler rhs)
		{
			return rhs.getPoints() - lhs.getPoints();
		}
		
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJCloseButton() {
		if (jCloseButton == null) {
			jCloseButton = new JButton();
			jCloseButton.setText("Close");
			jCloseButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					VictoryDialog.this.setVisible(false);
					VictoryDialog.this.dispose();
				}
			});
		}
		return jCloseButton;
	}
  } //  @jve:decl-index=0:visual-constraint="10,10"
