package schedule;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.util.Scanner;
import java.io.IOException;
import site.java.*;

/**
	* TimeSlot --- class of objects that mark an ecompassing time with an ability to lock the computer.
	* @author Renggeng Zheng
*/
public class TimeSlot extends Scheduled {
	/** TimeSlot unlocked constructor.
		* @param startTime Start time of the period of activity.
		* @param endTime End time of the period of activity.
		* @param activity Activity you will be doing during this time.
	*/
	public TimeSlot(LocalTime startTime, LocalTime endTime, String activity) {
		super(startTime, endTime, activity);
	}

	public void isTime() {
		try {
			if (LocalTime.now().truncatedTo(ChronoUnit.SECONDS).equals(this.getStartTime())) startTime();
			else if (LocalTime.now().truncatedTo(ChronoUnit.SECONDS).equals(this.getEndTime())) endTime();
		} catch (IOException e) {
			System.out.println("Idk what an IOException is but apparently you have one.");
			e.printStackTrace();
		}
	}

	public void startTime() throws IOException {
		System.out.println(this.getMessage());
		tray();
		InSession.inSession++;
		if (InSession.inSession == 1) BlockAndUnblock.block();//if it is the first blocked session
	}

	public void endTime() throws IOException {
		System.out.println(this.getMessage() + " This activity has ended.");
		tray();
		InSession.inSession--;
		if (InSession.inSession == 0) BlockAndUnblock.unBlock();//if its the last blocked session
	}
}