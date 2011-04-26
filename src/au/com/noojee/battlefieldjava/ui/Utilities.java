/*
 * 01:55:41 07/07/01
 *
 * Utilities.java - Some static utilities for swing gui
 * Copyright (C) 2001 Jurgen Prokein
 */

package au.com.noojee.battlefieldjava.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;

public class Utilities
{ 
     /**
     * Used to 'beep' the user.
     */
      public static void beep()
      {
        Toolkit.getDefaultToolkit().beep();
      }
      
      /**
      * Center window on screen.
      * @param comp The <code>Component</code> to center on screen
      */
      public static void centerComponentOnScreen(Component comp)
      {
        comp.setLocation(new Point((getScreenDimension().width - comp.getSize().width) / 2,
                          (getScreenDimension().height - comp.getSize().height) / 2));
      }
      
      /**
      * Center window on screen.
      * @param comp The <code>Component</code> to center on screen
      * @param size An integer indicating the size (in % of ScreenSize) to size the component
      */
      public static void centerAndSizeComponentOnScreen(Component comp, int size)
      {
          if (size < 1 || size > 100)
              throw new RuntimeException("Attempting to resize component " + comp.getClass().getName() + " to an invalid size: " + size);
          comp.setSize(getScreenDimension().width * size / 100, 
                     getScreenDimension().height * size / 100);
          comp.setLocation(new Point((getScreenDimension().width - comp.getSize().width) / 2,
                          (getScreenDimension().height - comp.getSize().height) / 2));          
      }

     /**
      * Center dialog within their parent.
      * @param parent The parent <code>Component</code>
      * @param child The <code>Component</code> to center
      */
      public static void centerComponentOnParent(Component parent, Component child)
      {
        Rectangle par = parent.getBounds();
        Rectangle chi = child.getBounds();
        child.setLocation(new Point(par.x + (par.width - chi.width) / 2,
                                    par.y + (par.height - chi.height) / 2));
      }
      
      /**
       * Load a File from somewhere on classpath.
       * @param file The path to the file (incl. extension)        
       * @return An <code>Image</code>
       */
      public static java.io.File getFile(String file)
      {
        java.io.File f = null;
        URL iconURL = ClassLoader.getSystemResource(file);
        if (iconURL != null) 
            f = new java.io.File(iconURL.getPath());
            //f = Toolkit.getDefaultToolkit().getF .getImage(iconURL);           
        if (f == null)
            throw new RuntimeException("Could not find File '" 
                + file + "' anywhere on the classpath.");
        return f;    
      }
      
      /**
       * Returns user directory.
       */
      public static String getUserDirectory()
      {
        return System.getProperty("user.dir");
      }

      /**
       * Returns user's home directory.
       */
      public static String getHomeDirectory()
      {
        return System.getProperty("user.home");
      }
    
      /**
       * Determine the screen's dimensions.
       * @return A <code>Dimension</code> object containing screen's resolution
       */
      public static Dimension getScreenDimension()
      {
        return Toolkit.getDefaultToolkit().getScreenSize();
      }

  /**
   * Long operations need to display an hourglass.
   * @param comp The <code>JComponent</code> on which to apply the hour glass cursor
   * @param on If true, we set the cursor on the hourglass   
   
  public static void setCursorOnWait(Component comp, boolean on)
  {
    if (on)
    {
      if (comp instanceof JextFrame)
        ((JextFrame) comp).showWaitCursor();
      else
        comp.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    } else {
      if (comp instanceof JextFrame)
        ((JextFrame) comp).hideWaitCursor();
      else
        comp.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
  }
  */

  /*
  private static JFileChooser getFileChooser(Component owner, int mode)
  {
    JFileChooser chooser = null;

    String last = Jext.getProperty("lastdir." + mode);
    if (last == null)
      last = Jext.getHomeDirectory();

    if (owner instanceof JextFrame)
    {
      chooser = ((JextFrame) owner).getFileChooser(mode);
      chooser.setCurrentDirectory(new File(last));
      if (mode == SAVE)
      {
        String ff = ((JextFrame) owner).getTextArea().getCurrentFile();
        if (ff != null)
          chooser.setSelectedFile(new File(ff));
      } else
        chooser.setSelectedFile(new File(""));
    } else {
      chooser = new JFileChooser(last);
      if (mode == SAVE)
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
      else
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
    }

    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setFileHidingEnabled(true);

    return chooser;
  }
   */


  /**
   * Quick sort an array of Strings.
   * @param string Strings to be sorted
   */

  public static void sortStrings(String[] strings)
  {
    sortStrings(strings, 0, strings.length - 1);
  }

  /**
   * Quick sort an array of Strings.
   * @param a Strings to be sorted
   * @param lo0 Lower bound
   * @param hi0 Higher bound
   */

  public static void sortStrings(String a[], int lo0, int hi0)
  {
    int lo = lo0;
    int hi = hi0;
    String mid;

    if (hi0 > lo0)
    {
      mid = a[(lo0 + hi0) / 2];

      while (lo <= hi)
      {
        while (lo < hi0 && a[lo].compareTo(mid) < 0)
          ++lo;

        while (hi > lo0 && a[hi].compareTo(mid) > 0)
          --hi;

        if (lo <= hi)
        {
          swap(a, lo, hi);
          ++lo;
          --hi;
        }
      }

      if (lo0 < hi)
        sortStrings(a, lo0, hi);

      if (lo < hi0)
        sortStrings(a, lo, hi0);
    }
  }

  /**
   * Swaps two Strings.
   * @param a The array to be swapped
   * @param i First String index
   * @param j Second String index
   */

  public static void swap(String a[], int i, int j)
  {
    String T;
    T = a[i];
    a[i] = a[j];
    a[j] = T;
  }

	/**
	 * @param e
	 * @param b
	 */
	public static void showException(JFrame parent, Throwable e, boolean centreOnScreen)
	{
		e.printStackTrace();
		ThrowableDialog dialog = new ThrowableDialog(parent, e, centreOnScreen);
		dialog.setVisible(true);

	}

	public static void showException(Throwable e)
	{
		showException(null, e, true);
	}

// End of Utilities.java
}
