/*
 * Created on 17/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.ui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextPane;
/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AboutDialog extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2995031328348874242L;
	private javax.swing.JPanel jContentPane = null;
	private JButton jClose = null;
	private JTextPane jTextPane = null;
	/**
	 * @throws java.awt.HeadlessException
	 */
	public AboutDialog(JFrame parent) 
	{
		super(parent);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("About Battlefield Java");
		this.setSize(361, 237);
		this.setContentPane(getJContentPane());
		this.getRootPane().setDefaultButton(getJClose());
		Utilities.centerComponentOnParent(getParent(), this);
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJClose(), null);
			jContentPane.add(getJTextPane(), null);
		}
		return jContentPane;
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getJClose() {
		if (jClose == null) {
			jClose = new JButton();
			jClose.setBounds(268, 167, 76, 25);
			jClose.setText("Close");
			jClose.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					AboutDialog.this.setVisible(false);
					AboutDialog.this.dispose();
				}
			});
		}
		return jClose;
	}
	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */    
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			jTextPane.setBounds(6, 7, 337, 156);
			jTextPane.setContentType("text/html");
			jTextPane.setText("<html><body>" +
					"<p><b>Battlefield Java<br>" +
					"by S. Brett Sutton</b><br>" +
					"Version 1.0 beta 1</p>" +
					"<p>Based on the original idea CodeRuler by Tim deBoer.<br><br>" +
					"<i>Let the coding begin!</i>" +
					"</body></html>");
			jTextPane.setBackground(new java.awt.Color(238,238,238));
			jTextPane.setEditable(false);
		}
		return jTextPane;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
