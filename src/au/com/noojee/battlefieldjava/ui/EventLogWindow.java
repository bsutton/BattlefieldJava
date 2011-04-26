/*
 * Created on 10/03/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.ui;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import au.com.noojee.battlefieldjava.engine.Event;



/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EventLogWindow extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8015147643724011316L;
	private JScrollPane				jScrollPane			= null;
	private JListWithTips			jList				= null;
	private DefaultListModel		defaultListModel	= null; //  @jve:decl-index=0:
	private static EventLogWindow	self;

	/**
	 * @throws java.awt.HeadlessException
	 */
	public EventLogWindow()
	{
		super();
		self = this;
		initialize();
		if (isVisible())
			getJList().ensureIndexIsVisible(0);

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		//	this.setSize(465, 352);
		this.setLayout(new java.awt.BorderLayout());
		this.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
	}

	public void addEvent(Event event) // IObject source, String action, IObject target)
	{
		final Event tmpEvent = event;
		SwingUtilities.invokeLater(new Runnable()
		{

			public void run()
			{
				((DefaultListModel) jList.getModel()).add(0, tmpEvent);
			}
		});

	}

	/**
	 * @return
	 */
	public static EventLogWindow getInstance()
	{
		if (self == null)
		{
			self = new EventLogWindow();
		}
		return self;
	}

	public void reset()
	{
		getDefaultListModel().removeAllElements();
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
			jScrollPane.setViewportView(getJList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JListWithTips getJList()
	{
		if (jList == null)
		{
			jList = new JListWithTips();
			jList.setModel(getDefaultListModel());
		}
		return jList;
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

	public static void main(String[] args)
	{
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
