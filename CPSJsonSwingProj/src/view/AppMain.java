package view;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class AppMain {

	public static void main(String[] args) {

		EventQueue.invokeLater(
			new Runnable() {
				
				@Override
				public void run() {

		            try {
		                UIManager.setLookAndFeel(
		                    UIManager.getSystemLookAndFeelClassName());
		            } catch (Exception e) {
		                System.err.println("AppMain::main - Couldn't use system look and feel.");
		            }
		            
		            
					JFrame appFrame = new AppFrame();
					appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					
					appFrame.setTitle("Chicago Elementary School Progress Report Card Application(Json+Swing)");
					
					appFrame.add(new AppPanel());
					
					// display the window
					appFrame.setVisible(true);
				}
			});
	}

}
