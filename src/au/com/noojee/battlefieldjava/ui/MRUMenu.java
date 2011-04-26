/*
 * MRUMenu.java
 * Use this class to add a list of the most recently accessed files
 * on the file menu.
 * The MRUMenu needs to be serialized to/from disk on application start/stop.
 *
 * The MRUMenu is append to a pre-existing menu (normally the file menu).
 * The menu which the MRUMenu is append to must be of a fixed size (menu item count).
 *
 * Created on 27 December 2001, 18:29
 */

package au.com.noojee.battlefieldjava.ui;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author  bsutton
 * @version
 */
public class MRUMenu implements Serializable
{
	private int m_nMaxEntries;
	private transient JMenu m_baseMenu;
	private transient listener m_listener;
	private transient int m_nBaseMenuItemCount = 0;		// The number of menu items on the File menu before the MRU are added.
	
	public static final long serialVersionUID = 2;
	
	/**
	 * The list of most recently used files. Sort based on the last time
	 * they where accessed.
	 */
	private java.util.TreeSet<RecentlyUsedFile> m_setMostRecentlyUsedFiles  = new TreeSet<RecentlyUsedFile>();
	
	
	/** Creates new MRUMenu
	 * @param baseMenu the menu to which the MRU list will be appended. The menu
	 * must be fully initialised when this function is called.
	 * @param nMaxEntries the maxumum number of entries to display on the MRU list.
	 */
	public MRUMenu(JMenu baseMenu, int nMaxEntries, MRUMenu.listener listener)
	{
		setMaxEntries(nMaxEntries);
		setMenu(baseMenu);
		setListener(listener);
		
		showMostRecentlyUsed();
	}
	
	public void setMenu(JMenu menuBase)
	{
		if (menuBase == null)
			throw new java.security.InvalidParameterException("menuBase may not be null");
		m_baseMenu = menuBase;
		m_nBaseMenuItemCount = m_baseMenu.getItemCount();
	}
		
	void setMaxEntries(int nMaxEntries)
	{
		m_nMaxEntries = nMaxEntries;
	}
	
	public void setListener(MRUMenu.listener listener)
	{
		if (listener == null)
			throw new java.security.InvalidParameterException("Listener may not be null");
		m_listener = listener;
	}

	/**
	 * Call this function to update the MRU list.
	 * This function should noramlly be called whenever a file is opened
	 * or saved using save as.
	 *
	 *@param filName the name of the file being accessed.
	 */
	public void update(File filName)
	{
		RecentlyUsedFile RUF = new RecentlyUsedFile(filName, Calendar.getInstance().getTime());
		
		RecentlyUsedFile rufDup = find(RUF);
		if (rufDup != null)
		{
			m_setMostRecentlyUsedFiles.remove(rufDup);
		}
		m_setMostRecentlyUsedFiles.add(RUF);
		
		if (m_setMostRecentlyUsedFiles.size() > m_nMaxEntries)
			m_setMostRecentlyUsedFiles.remove(m_setMostRecentlyUsedFiles.last());
		
		// Redisplay the MRU List on the menu
		showMostRecentlyUsed();
	}
	
	public void showMostRecentlyUsed()
	{
		
		// Removed the previously added MRU
		for (int i = m_baseMenu.getItemCount(); i > m_nBaseMenuItemCount; i--)
			m_baseMenu.remove(i - 1);
		
		java.util.Iterator<RecentlyUsedFile> Iter = m_setMostRecentlyUsedFiles.iterator();
		
		boolean bFirstPass = true;
		while (Iter.hasNext())
		{
			if (bFirstPass)
			{
				bFirstPass = false;
				m_baseMenu.addSeparator();
			}
			RecentlyUsedFile ruf = Iter.next();
			JMenuItem item = new JMenuItem(ruf.getPath());
			item.setActionCommand(ruf.getPath());
			
			item.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent evt)
				{
					onMRUSelected(evt);
				}
			});
			m_baseMenu.add(item);
		}
	}
	
	
	private void onMRUSelected(java.awt.event.ActionEvent evt)
	{
		// We store the Filename in the action command.
		String strFilename = evt.getActionCommand();
		File filSelected = new File(strFilename);
		m_listener.onMRUSelect(filSelected);
		update(filSelected);
	}
	
	private RecentlyUsedFile find(RecentlyUsedFile rhs)
	{
		Iterator<RecentlyUsedFile> iter = m_setMostRecentlyUsedFiles.iterator();
		RecentlyUsedFile rufDup = null;
		
		while (iter.hasNext())
		{
			RecentlyUsedFile RUF = iter.next();
			if (RUF.equals(rhs))
			{
				rufDup = RUF;
				break;
			}
		}
		return rufDup;
	}
	
	public interface listener
	{
		public void onMRUSelect(File filSelected);
	}
	
	
	class RecentlyUsedFile implements Comparable<RecentlyUsedFile>, Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 3346521153290292021L;
		public File m_filName;
		public Date m_dteTimeLastUsed;
		
		RecentlyUsedFile(File filName, Date dteTimeLastUsed)
		{
			m_filName = filName;
			m_dteTimeLastUsed = dteTimeLastUsed;
		}
		
		String getPath()
		{
			return m_filName.getPath();
		}

		boolean equals(RecentlyUsedFile rhs)
		{
			return (m_filName.equals(rhs.m_filName));
		}
		/**
		 * We sort by date/time
		 */
		public int compareTo(RecentlyUsedFile _rhs)
		{
			int nRet = 0;
			RecentlyUsedFile rhs = (RecentlyUsedFile)_rhs;
			nRet = rhs.m_dteTimeLastUsed.compareTo(m_dteTimeLastUsed);
			return nRet;
		}
		
		public String toString()
		{
			String str;
			str = m_filName + " " + m_dteTimeLastUsed;
			return str;
		}
	}
}
