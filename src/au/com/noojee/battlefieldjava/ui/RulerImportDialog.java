/*
 * Created on 26/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.ui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RulerImportDialog extends JDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5661247144322105079L;

	int result = JOptionPane.CANCEL_OPTION;
	
	private javax.swing.JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JButton jOKButton = null;
	private JTextField jClassName = null;
	private JLabel jLabel1 = null;
	private JTextField jDescription = null;
	private JButton jButton1 = null;
	/**
	 */
	public RulerImportDialog(JDialog parent) 
	{
		super(parent);
		initialize();
	}


	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJOKButton() {
		if (jOKButton == null) {
			jOKButton = new JButton();
			jOKButton.setBounds(66, 130, 122, 28);
			jOKButton.setText("OK");
			jOKButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					doOK();
				}
			});
		}
		return jOKButton;
	}
	/**
	 * 
	 */
	protected void doOK()
	{
		result = JOptionPane.OK_OPTION;
		this.setVisible(false);
		this.dispose();
		
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJClassName() {
		if (jClassName == null) {
			jClassName = new JTextField();
			jClassName.setBounds(12, 96, 373, 25);
			jClassName.setText("");
		}
		return jClassName;
	}
	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getJDescription() {
		if (jDescription == null) {
			jDescription = new JTextField();
			jDescription.setBounds(12, 37, 373, 25);
			jDescription.setText("");
		}
		return jDescription;
	}
	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(200, 130, 128, 27);
			jButton1.setText("Cancel");
			jButton1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					doCancel();
				}
			});
		}
		return jButton1;
	}
    	/**
	 * 
	 */
	protected void doCancel()
	{
		this.setVisible(false);
		this.dispose();
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setTitle("Ruler Import Dialog");
		this.setSize(401, 203);
		this.setModal(true);
		this.setContentPane(getJContentPane());
		this.getRootPane().setDefaultButton(getJOKButton());
		Utilities.centerComponentOnParent(getParent(), this);
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jLabel.setBounds(12, 67, 185, 22);
			jLabel.setText("Fully Qualified Ruler Class:");
			jLabel1.setBounds(12, 10, 192, 20);
			jLabel1.setText("Description:");
			jContentPane.add(jLabel, null);
			jContentPane.add(getJOKButton(), null);
			jContentPane.add(getJClassName(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(getJDescription(), null);
			jContentPane.add(getJButton1(), null);
		}
		return jContentPane;
	}

	/**
	 * @return
	 */
	public int getResult()
	{
		return result;
	}

	/**
	 * @return
	 */
	public String getDescription()
	{
		return jDescription.getText();
	}

	/**
	 * @return
	 */
	public String getClassName()
	{
		return jClassName.getText();
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
