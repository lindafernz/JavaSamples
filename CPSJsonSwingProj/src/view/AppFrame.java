package view;

import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class AppFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 102030405060L;
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 300;
	
	public AppFrame()
	{		
		GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		DisplayMode dim = genv.getDefaultScreenDevice().getDisplayMode();
		
		setSize(dim.getWidth()/2, dim.getHeight()/2);
		setLocationByPlatform(true);
		
		setIconImage(new ImageIcon("images/icon.png").getImage());
	}
}
