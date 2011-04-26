/*
 * Created on 25/04/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package au.com.noojee.battlefieldjava.images;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;


/**
 * @author bsutton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ImageLoader
{
    /**
     * Load an ImageIcon from somewhere on classpath.
     * @param picture The name of the image (incl. extension)    
     * @return An <code>ImageIcon</code>
     */
     public static ImageIcon getIcon(String picture)
     {
       ImageIcon icon = null;        
       URL iconURL = ImageLoader.class.getResource(picture);
       if (iconURL != null) 
           icon = new ImageIcon(iconURL);
       if (icon == null)
           throw new RuntimeException("Could not find ImageIcon '" 
               + picture + "' anywhere on the classpath.");
       return icon;
     }
     
     /**
      * Load an Image from somewhere on classpath.
      * @param picture The path to the image (incl. extension)        
      * @return An <code>Image</code>
      */
     public static Image getImage(String picture)
     {
       Image icon = null;
       URL iconURL = ImageLoader.class.getResource(picture);
       if (iconURL != null) 
           icon = Toolkit.getDefaultToolkit().getImage(iconURL);           
       if (icon == null)
           throw new RuntimeException("Could not find Image '" 
               + picture + "' anywhere on the classpath.");
       return icon;    
     }

}
