package schedule;
import java.awt.*;
import java.awt.TrayIcon.MessageType;

public class TrayDisplay {
	public static void displayTray(String title, String message) throws AWTException {
		//Obtain only one instance of the SystemTray object
		SystemTray tray = SystemTray.getSystemTray();

		//If the icon is a file
		Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
		//Alternative (if the icon is on the classpath):
		//Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

		TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
		//Let the system resize the image if needed
		trayIcon.setImageAutoSize(true);

		//Set tooltip text for the tray icon
		trayIcon.setToolTip("System tray icon demo");
		tray.add(trayIcon);

		/*
		TODO: Assignment Time Reminder
		Sample Code:
		if (assignmentTimeReminder) {
		trayIcon.displayMessage("Assignment To Do","You have a " + assignmentName + " to do.", MessageType.WARNING);
		} */

		trayIcon.displayMessage(title, message, MessageType.INFO);
		tray.remove(trayIcon);
	}
}
