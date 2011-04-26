/*
 * Created on 29/12/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.ui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ThrowableDialog extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1270974907293166506L;
	private javax.swing.JPanel jContentPane = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel lbException = null;
	private JTextArea lbMessage = null;
	private JTextArea txtStackTrace = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JButton jButton = null;
	private JScrollPane jScrollPane = null;
	private JPanel jPanel3 = null;
	private JPanel jPanel4 = null;
	/**
	 * This is the default constructor
	 */
	public ThrowableDialog(Frame parent, Throwable e) 
	{
		this(parent, e, false);
	}
	/**
	 * @param main
	 * @param e
	 * @param centreOnScreen
	 */
	public ThrowableDialog(Frame parent, Throwable e, boolean centreOnScreen)
	{
		super();
		initialize();
		
		if (centreOnScreen)
			Utilities.centerComponentOnScreen(this);
		else
			Utilities.centerComponentOnParent(parent, this);
		Throwable cause = e.getCause();
		if (cause == null)
			cause = e;
		
		lbException.setText(cause.getClass().getName());
		
		// add paragraph tags so that it word wraps.
		lbMessage.setText(cause.getMessage());
		StringWriter sr = new StringWriter();
		
		cause.printStackTrace(new PrintWriter(sr));
		txtStackTrace.setText(sr.toString());
		txtStackTrace.setCaretPosition( 0 );

	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("Exception");
		this.setSize(544, 335);
		this.setContentPane(getJContentPane());
		this.setModal(true);
		this.getRootPane().setDefaultButton(getCloseButton());
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BoxLayout(jContentPane, BoxLayout.Y_AXIS));
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
			jContentPane.add(getJPanel2(), null);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */    
	private JTextArea getTxtStackTrace() {
		if (txtStackTrace == null) {
			txtStackTrace = new JTextArea();
			txtStackTrace.setEnabled(true);
			txtStackTrace.setBackground(new java.awt.Color(204,204,204));
			txtStackTrace.setEditable(false);
		}
		return txtStackTrace;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setPreferredSize(new java.awt.Dimension(90,95));
			jPanel.setMaximumSize(new java.awt.Dimension(2147483647,95));
			jPanel.setMinimumSize(new java.awt.Dimension(415,95));
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.ipadx = 84;
			gridBagConstraints1.ipady = 90;
			gridBagConstraints1.anchor = java.awt.GridBagConstraints.SOUTH;
			gridBagConstraints1.gridheight = 2;
			gridBagConstraints1.gridwidth = 1;
			gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints1.weightx = 0.0D;
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.ipadx = 440;
			gridBagConstraints2.ipady = 90;
			gridBagConstraints2.insets = new java.awt.Insets(0,0,0,1);
			gridBagConstraints2.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints2.gridheight = 3;
			gridBagConstraints2.weightx = 1.0D;
			gridBagConstraints2.anchor = java.awt.GridBagConstraints.EAST;
			jPanel.add(getJPanel3(), gridBagConstraints1);
			jPanel.add(getJPanel4(), gridBagConstraints2);
			jPanel.setPreferredSize(new java.awt.Dimension(0,95));
			
		}
		return jPanel;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setPreferredSize(new java.awt.Dimension(10,50));
			jPanel2.add(getCloseButton(), null);
		}
		return jPanel2;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getCloseButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Close");
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					dispose();
				}
			});
		}
		return jButton;
	}
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */    
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTxtStackTrace());
			jScrollPane.setAutoscrolls(true);
			jScrollPane.setMinimumSize(new java.awt.Dimension(0,0));
			jScrollPane.setPreferredSize(new java.awt.Dimension(0,0));
		}
		return jScrollPane;
	}
	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jLabel2 = new JLabel();
			jLabel1 = new JLabel();
			jLabel = new JLabel();
			jPanel3.setLayout(null);
			jPanel3.setPreferredSize(new java.awt.Dimension(40,45));
			jLabel.setBounds(11, 5, 72, 16);
			jLabel.setText("Exception:");
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel1.setBounds(11, 26, 72, 16);
			jLabel1.setText("Message:");
			jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel2.setText("Stack Trace:");
			jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jLabel2.setSize(72, 16);
			jLabel2.setLocation(11, 70);
			jPanel3.add(jLabel, null);
			jPanel3.add(jLabel1, null);
			jPanel3.add(jLabel2, null);
			
		}
		return jPanel3;
	}
	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setLayout(new GridBagLayout());
			jPanel4.setBounds(6, 4, 443, 86);
			lbMessage = new JTextArea();
			lbException = new JLabel();
			lbException.setText("JLabel");
			lbException.setSize(306, 16);
			lbException.setLocation(6, 5);
			lbMessage.setBounds(6, 26, 314, 59);
			lbMessage.setText("JLabel");
			lbMessage.setAutoscrolls(true);
			lbMessage.setLineWrap(true);
			lbMessage.setWrapStyleWord(true);
			lbMessage.setBackground(new java.awt.Color(204,204,204));
			lbMessage.setEditable(false);
			jPanel4.setPreferredSize(new java.awt.Dimension(1,45));
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints1.insets = new Insets(5,5,5,5);
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.anchor = java.awt.GridBagConstraints.NORTHWEST;
			gridBagConstraints2.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints2.weightx = 1.0D;
			gridBagConstraints2.weighty = 1.0D;
			gridBagConstraints2.gridwidth = 0;
			lbException.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			jPanel4.add(lbException, gridBagConstraints1);
			jPanel4.add(lbMessage, gridBagConstraints2);
		}
		return jPanel4;
	}

       }  //  @jve:decl-index=0:visual-constraint="10,10"
