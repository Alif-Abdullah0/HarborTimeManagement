package schedule;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
/**
* Alarm --- a reminder that has an auditory Notification
* @author Renggeng Zheng
*/
public class Alarm extends Reminder {
	private String ringtone = "assets/alarms/default.mp3";//stores what ringtone plays as a private string
	/** Creates an alarm object with all parameters settable.
		* @param alarmTime The time the alarm should occur.
		* @param message The message that accompanies the alarm.
		* @param ringtone The ringtone that plays for the user.
	*/
	public Alarm(LocalTime alarmTime, String message, String ringtone) {
		super(alarmTime, message);
		this.ringtone = ringtone;
	}
	/** Creates an alarm object at the given time, no message, all default settings.
	* @param alarmTime The time the alarm should occur.
	* @param message Message assocaited with alarm.
	*/
	public Alarm(LocalTime alarmTime, String message) {
		super(alarmTime, message);
	}

	public void isTime() {
		if (LocalTime.now().truncatedTo(ChronoUnit.SECONDS).equals(this.getStartTime())) {
			tray();
			System.out.println(this.getMessage());
		} else if (DEBUG) System.out.println(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
	}
}
