package schedule;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
/**
* Scheduled --- An abstract class coordinating functions for schedulable items.
* @author Renggeng Zheng
*/
public abstract class Scheduled implements java.io.Serializable {
	//LocalTime variables stores when the time slot opens and closes today.
	private LocalTime startTime;
	private LocalTime endTime;
	private String message;
	public static boolean DEBUG = false;
	private static final long serialVersionUID = 42l;
	/** constructor which sets when the time slot begins and ends.
		* @param start Start time of the event
		* @param end End time of the event
	*/
	public Scheduled(LocalTime start, LocalTime end) {
		this(start, end, "");
	}
	public Scheduled(LocalTime start, LocalTime end, String message) {
		startTime = start;
		endTime = end;
		this.message = message;
	}
	/** retrieves the start of the time slot.
		* @return the current startTime
	*/
	public LocalTime getStartTime() {
		return startTime;
	}
	/**retrieves the end of the time slot.
		*@return the current endTime
	*/
	public LocalTime getEndTime() {
		return endTime;
	}
	/** changes the start time of the slot and returns the old start time.
		* @param newStartTime the new start time.
		* @return oldStartTime the old start time.
	*/
	public LocalTime modifyStartTime(LocalTime newStartTime) {
		LocalTime oldStartTime = startTime;
		startTime = newStartTime;
		return oldStartTime;
	}
	/*changes the end time of the the time slot and rturns the old end time.
		* @param newEndTime the new end time.
		* @return oldEndTime the old end time.
	*/
	public LocalTime modifyEndTime(LocalTime newEndTime) {
		LocalTime oldEndTime = endTime;
		endTime = newEndTime;
		return oldEndTime;
	}
	/**
		*@return the message
	 */
	public String getMessage() {
		return message;
	}
	/** mutator of message.
		* @param newMessage the new reminder message.
		* @return the old reminder message.
	*/
	public String editMessage(String newMessage) {
		String oldMessage = message;
		message = newMessage;
		return oldMessage;
	}

	public void isTime() {
		if (LocalTime.now().truncatedTo(ChronoUnit.SECONDS).equals(startTime)) {
			System.out.println("Start of timeblock.");
		} else if (LocalTime.now().truncatedTo(ChronoUnit.SECONDS).equals(endTime)) {
			System.out.println("End of timeblock.");
		}
	}

	public void tray() {
		if (SystemTray.isSupported()) {
			/* Variable title will take in data from outside class
			at a later date. */
			String title = "Harbor Notification";
			/* Variable message will take in data from outside class
			at a later date. */
			try {
				TrayDisplay.displayTray(title, message);
			}
			catch (AWTException ae) {
				ae.printStackTrace();
			}


		} else {
			System.err.println("System tray not supported!");
		}
	}
}
