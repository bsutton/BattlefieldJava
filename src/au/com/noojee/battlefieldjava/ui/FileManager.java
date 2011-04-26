/*
 * FileManager.java
 *
 * The FileManager is designed to aid in implementing the standard
 * File menu which contains New, Open, Save and Save As options. The
 * FileManager also support a Most Recently Used (MRU) menu (normally
 * just an extention to the File menu).
 * By implementing a FileManager all you have to worry about
 * is the actual saving and loading of the file. All other
 * operations including user interaction are handled by the 
 * File Manager.
 *
 * Created on 20 September 2002, 16:52
 */

package au.com.noojee.battlefieldjava.ui;

import java.awt.Component;
import java.awt.Cursor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


/**
 *
 * @author  bsutton
 */
public class FileManager
{
	private final String m_strExt;
	private final String m_strExtDescription;
	private final String m_strDefaultDocument;
	private final Component m_comParent;
	private final MRUMenu m_mruMenu;
	private Listener m_listener;
	
	/** Tracks if the document has changed since it was last saved.
	 */
	private boolean m_bChanged = false;
	
	private File m_file;			// The currently open file.
	
	
	/** Creates a new instance of FileManager
	 * @param comParent the parent window to hang any dialogs off. May be null
	 * @param mruMenu The menu where the most recently used filenames will be displayed.
	 *	If you pass this in the FileManager will update your MRU automatically.
	 *	May be null. Note: The FileManager will not save/restore the MRU between
	 *  sessions.
	 * @param strDefaultDocument the name given to new unnamed documents.
	 * @param strExtDescription the description associated with the file extention. 
	 * @param strExt the file extention (just the three letter suffix) to use when
	 *	filtering files in the open file dialog.
	 */
	public FileManager(java.awt.Component comParent, MRUMenu mruMenu, String strDefaultDocument, String strExtDescription, String strExt)
	{
		m_comParent = comParent;
		m_strDefaultDocument = strDefaultDocument;
		m_strExtDescription = strExtDescription;
		m_strExt = "." + strExt;
		m_mruMenu = mruMenu;
	}
	
	/**
	 * Allows the caller to register to recieve
	 * the set of events defined by FileManager.Listener.
	 * Only one listener may be registered at a time.
	 */
	public void register(Listener listener)
	{
		m_listener = listener;
	}
	
	/**
	 * Allows the caller to deregister from recieving
	 * the set of events defined by FileManager.Listener.
	 * Calling this function is not usually required.
	 */
	public void deregister()
	{
		m_listener = null;
	}
	
	public void changed()
	{
		m_bChanged = true;
	}
	
	
	public void doOpen()
	throws FileNotFoundException, IOException, FileManager.Exception
	{
		if (userSave("Open"))
		{
			
			File fileDefault = m_file;
			if (fileDefault == null)
			{
				// set a default.
				String strDir = System.getProperty("user.home");
				fileDefault = new File(strDir, m_strDefaultDocument);
			}
			
			JFileChooser  dlg = new JFileChooser();
			dlg.setCurrentDirectory(fileDefault);
			dlg.setFileFilter(new ExtFilter(m_strExtDescription, m_strExt));
			if (dlg.showDialog(m_comParent, "Open") == JFileChooser.APPROVE_OPTION)
			{
				File fileSelected = dlg.getSelectedFile();
				if (m_mruMenu != null)
					m_mruMenu.update(fileSelected);
				openFile(fileSelected);
			}
			m_bChanged = false;
		}
	}
	
	public void doNew()
	throws FileNotFoundException, IOException, FileManager.Exception
	{
		if (userSave("New"))
		{
			m_file = null;
			if (m_listener != null)
			{
				m_comParent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				
				try
				{
					m_listener.clear();
				}
				finally
				{
					m_comParent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
			
		}
		m_bChanged = false;
	}
	
	/**
	 * @return true if the user completed the save action.
	 *			false if the user cancelled the save action.
	 */
	public boolean doSave()
	throws FileNotFoundException, IOException, FileManager.Exception
	{
		boolean bSaved = false;
		if (m_file != null)
		{
			save(m_file);
			bSaved = true;
			m_bChanged = false;
		}
		else
			bSaved = doSaveAs();
		return bSaved;
	}
	/**
	 * @return true if the user completed the save action.
	 *			false if the user cancelled the save action.
	 */
	public boolean doSaveAs()
	throws FileNotFoundException, IOException, FileManager.Exception
	{
		boolean bSave = false;
		File fileDefault = m_file;
		if (fileDefault == null)
		{
			// set a default.
			String strDir = System.getProperty("user.home");
			fileDefault = new File(strDir, m_strDefaultDocument);
		}
		
		JFileChooser  dlg = new JFileChooser();
		dlg.setFileFilter(new ExtFilter(m_strExtDescription, m_strExt));
		
		dlg.setCurrentDirectory(fileDefault);
		
		// Allows the user to retry in the event that they
		// select a filename that already exists.
		boolean bUserRetry = true;
		while (bUserRetry)
		{
			int nResult =  dlg.showDialog(m_comParent, "Save As");
			if (nResult == JFileChooser.APPROVE_OPTION)
			{
				m_file = dlg.getSelectedFile();
				// Check for an extention
				if (m_file.getName().endsWith(m_strExt) == false)
					m_file = new File(m_file.getAbsolutePath() + m_strExt);
				
				// Check if the file already exists.
				if (m_file.exists())
				{
					int nResponse = JOptionPane.showConfirmDialog(m_comParent, "The file "
					+ m_file.getName()
					+ " already exists. Do you want to over-write it?"
					, "DbCoder"
					, JOptionPane.YES_NO_CANCEL_OPTION
					, JOptionPane.QUESTION_MESSAGE);
					switch (nResponse)
					{
						case JOptionPane.YES_OPTION:
							bSave = true;
							bUserRetry = false;
							break;
						case JOptionPane.NO_OPTION:
							// Loop around and give the user another try.
							break;
							
						case JOptionPane.CANCEL_OPTION:
							bUserRetry = false;
							// fall out doing nothing.
							break;
					}
				}
				else
				{
					bSave = true;	// File doesn't already exists so we can save it.
					bUserRetry = false;
				}
				
				if (bSave)
				{
					if (m_mruMenu != null)
						m_mruMenu.update(m_file);
					save(m_file);
					bSave = true;
					m_bChanged = false;
				}
			}
			else
				bUserRetry = false;	// User cancelled out of the dialog.
		}
		
		return bSave;
	}
	
	/**
	 * Prompts the user to save the current settings.
	 * @param strAction the action the user is executing which will be displayed as
	 *		the dialogs title.
	 * @return true if the setting where succuessfully saved or the user
	 *	choose not to save the settings.
	 *	false if the save failed.
	 */
	public boolean userSave(String strAction)
	throws FileNotFoundException, IOException, FileManager.Exception
	{
		boolean bSaved = false;
		
		if (m_bChanged)
		{
			int nOption = JOptionPane.showConfirmDialog(m_comParent
			, "Do you want to save your changes", strAction
			, JOptionPane.YES_NO_OPTION
			, JOptionPane.QUESTION_MESSAGE);
			switch (nOption)
			{
				case JOptionPane.YES_OPTION:
					bSaved = doSave();
					break;
				case JOptionPane.NO_OPTION:
					// User chose not to save, so thats as good as having
					// saved it.
					bSaved = true;
					break;
				default:	// probably the user cancelled the action.
					break;
			}
		}
		else
			bSaved = true;
		return bSaved;
	}
	
	public void openFile(File file)
	throws FileNotFoundException, IOException, FileManager.Exception
	{
		// Reset everything..
		doNew();
		
		m_file = file;
		load(m_file);
		m_bChanged = false;
	}
	
	
	/**
	 * Checks if the given fully qualified directory exits.
	 * If it doesn't then the user is asked if they would like it to be created.
	 * If they respond yes then necessary directories are created.
	 * @return true if
	 *		the directory already exists
	 *		the user said no to creating the directory.
	 *		the user said yes to creating the directory and it was successfully created.
	 *	false if
	 *		the user said yes and the directory could not be created.
	 *		the user canceled the operation.
	 */
	public boolean userMakeDir(String strDirectory)
	{
		boolean bSuccess = false;
		
		File newDir = new File(strDirectory);
		
		if (!newDir.exists())
		{
			// Ask the user if we can go ahead and create it.
			int nOption = JOptionPane.showConfirmDialog(m_comParent
			, "The output directory " + strDirectory + " does not exists."
			+ " Do you want it to be created?"
			, "Create Directory"
			, JOptionPane.YES_NO_CANCEL_OPTION
			, JOptionPane.QUESTION_MESSAGE);
			
			switch (nOption)
			{
				case JOptionPane.YES_OPTION:
					bSuccess = newDir.mkdirs();
					break;
				case JOptionPane.NO_OPTION:
					bSuccess = true;
					break;
				case JOptionPane.CANCEL_OPTION:
					break;
			}
		}
		else
			bSuccess = true;
		
		return bSuccess;
	}
	
	private class ExtFilter extends javax.swing.filechooser.FileFilter
	{
		private String m_strExtDescription;
		private String m_strExt;
		
		ExtFilter(String strDescription, String strExt)
		{
			m_strExtDescription = strDescription;
			m_strExt = strExt;
		}
		
		public boolean accept(File file)
		{
			return file.getName().endsWith(m_strExt) || file.isDirectory();
		}
		
		public String getDescription()
		{
			return m_strExtDescription + " (*" + m_strExt + ")";
		}
	}
	
	void load(File file)
	throws FileNotFoundException,  IOException, FileManager.Exception
	{
		if (m_listener != null)
		{
			m_comParent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			try
			{
				
				m_listener.load(file);
			}
			finally
			{
				m_comParent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}
	
	
	void save(File file)
	throws FileNotFoundException, IOException, FileManager.Exception
	{
		if (m_listener != null)
		{
			m_comParent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			try
			{
				m_listener.save(file);
			}
			finally
			{
				m_comParent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}
	
	public static class Exception extends java.lang.Exception
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 8227786360925565547L;

		public Exception(String strMessage, java.lang.Exception cause)
		{
			super(strMessage, cause);
		}
	}
	
	
	/**
	 * Defines the set of events that the FileManager will pass to 
	 * a registered listener for processing.
	 * The FileManager nevers reads or writes to a file, instead
	 * it calls the registered listener at the appropriate points
	 * in time to do the actual work.
	 */
	public interface Listener
	{
		/** The listener should read the files contents.
		 * This event is triggered during File|Open processing.
		 */
		void load(File file)
		throws FileNotFoundException,  IOException, FileManager.Exception;
		
		/** The listener should write the files contents.
		 * This event is triggered during File|Save and File|Save As 
		 * processing.
		 */
		void save(File file)
		throws FileNotFoundException, IOException, FileManager.Exception;
		
		/** The listener should clear any file data from memory. This event
		 * is triggered as part of the FileManager being requested to create
		 * a new file (i.e. File|New selected from menu). 
		 * It is not normally possible to create the physical 
		 * file at this point as the file name will not be known until
		 * the user selects to save the document and the user is then
		 * prompted for the filename.
		 */
		void clear();
	}
	
	
}
