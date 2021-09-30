package schedule;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
/**
	* Reminder --- a scheduled item that only occurs at one point in time.
	* @author Renggeng Zheng
*/
public class Reminder extends Scheduled {
	/** constructor of the Reminder class.
		* @param reminderTime Time of occurance of the reminder.
		* @param reminder Reminder message.
	*/
	public Reminder (LocalTime reminderTime, String reminder) {
		super(reminderTime, reminderTime, reminder);
	}
	/** mutator of time of occurance.
		* @param time new time of reminder occurance.
		* @return the old time of occurance.
	*/
	public LocalTime modifyTimeOfOccurance(LocalTime time) {
		modifyStartTime(time);
		return modifyEndTime(time);
	}

	//~~~~ THE FOLLOWING METHODS ALIAS PREVIOUS METHODS FROM SCHEDULED TO KEEP EXPECTED BEHAVIOR ~~

	/** mutator of time of occurance.
		* @param time new time of reminder occurance.
		* @return the old time of occurance.
	*/
	public LocalTime modifyStartTime(LocalTime time) {
		return modifyTimeOfOccurance(time);
	}
	/** mutator of time of occurance.
		* @param time new time of reminder occurance.
		* @return the old time of occurance.
	*/
	public LocalTime modifyEndTime(LocalTime time) {
		return modifyTimeOfOccurance(time);
	}
	public void isTime() {
		if (LocalTime.now().truncatedTo(ChronoUnit.SECONDS).equals(this.getStartTime())) {
			tray();
			System.out.println(this.getMessage());
		} else if (DEBUG) System.out.println(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
	}
}
