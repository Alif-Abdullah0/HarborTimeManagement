import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.time.temporal.ChronoUnit;
import java.awt.*;
import java.io.*;
import schedule.*;
import site.java.*;
// import scheduling.*;
//import com.google.gson.*;

public class HarborDesktopAppCLI {
	static HashMap<String, Scheduled> schedule = new HashMap<String, Scheduled>();
	static Scanner in = new Scanner(System.in);//scanner for getting user input
	static boolean DEBUG  = false;
	public static int inSession = 0;

	public static void main(String[] args) {
		try {
			schedule = StoreSchedule.readSchedule();
			System.out.println("HashMap successfully imported.");
		} catch (FileNotFoundException e) {
			System.out.println("Error reading file or file nonexistent.");
			schedule = new HashMap<String, Scheduled>();
		}
		// creates timer
        Timer timer = new Timer();
        // creates task
        TimerTask TimerTask = new TimerTask() {
            @Override
            public void run() {
				for (String message: schedule.keySet()) {
					schedule.get(message).isTime();
				}
            };


        };
        // schedules the task to be run every second. This prevents drift.
        timer.scheduleAtFixedRate(TimerTask,new Date(),1000);

		boolean exitState = false;
		do {
			if (in.hasNext()) {
				switch (in.next()) {
					case "exit":
						exitState = true;
						break;
					case "new":
						if (scheduleableCreation()) System.out.println("Object successfully created");
						break;
					case "delete":
						String alias = in.next();
						if (schedule.remove(alias) == null) {
							System.out.println("No item associated with alias \"" + alias + "\"");
						}
						break;
					case "url":
						if (url()) System.out.println("Url successfully inputted.");
					default:
						System.out.println("Not a valid operation.");
						break;
				}
			}

		} while (!exitState);

		StoreSchedule.writeSchedule(schedule);
		System.out.println("Exited program successfully.");
		System.exit(0);
	}

	public static boolean url() {
		boolean created = true;
		String check = in.next();
		switch(check) {
			case "Block":
				block();
				break;
			case "Unblock":
				unblock();
				break;
			case "return":
				return false;
			default:
				System.out.println("No object associated with " + check);
				break;
		}

		return created;
	}

	public static void block() {
		try {
			String url = in.next();
			String hostsFile = BlockAndUnblock.getHostsFile();
			String blockedUrls = BlockAndUnblock.getBlockedUrls();
			System.out.println(inSession);
			BlockAndUnblock.blockSite(hostsFile, blockedUrls, url, inSession == 1);
		} catch (IOException e) {
			System.out.println("Idk what an IOException is but apparently you have one.");
			e.printStackTrace();
		}
	}

	public static void unblock() {
		try {
			String url = in.next();
			String hostsFile = BlockAndUnblock.getHostsFile();
			String blockedUrls = BlockAndUnblock.getBlockedUrls();
			BlockAndUnblock.unBlockSite(hostsFile, blockedUrls, url, inSession == 1);
		} catch (IOException e) {
			System.out.println("Idk what an IOException is but apparently you have one.");
		}
	}

	private static boolean scheduleableCreation() {
		boolean created = true;
		String check = in.next();
		switch (check) {
			case "Alarm":
				createAlarm();
				break;
			case "Reminder":
				createReminder();
				break;
			case "TimeSlot":
				createTimeSlot();
				break;
			case "return":
				return false;
			default:
				System.out.println("No object associated with " + check);
				break;
		}

		return created;
	}

	private static boolean createAlarm() {
		boolean created = false;
		//gets name of Alarm
		String name = in.next();
		//gets time of reminder
		LocalTime time = getNextLocalTime();
		if (DEBUG) System.out.println(time);
		//extracts message of alarm
		String message = getNextQuoted();
		if (DEBUG) System.out.println(message);
		//puts object inside schedule
		schedule.put(name, new Alarm(time, message));

		created = true;
		return created;
	}

	private static boolean createReminder() {
		boolean created = false;
		//gets name of Reminder
		String name = in.next();
		//gets time of reminder
		LocalTime time = getNextLocalTime();
		if (DEBUG) System.out.println(time);
		//extracts message of reminder
		String message = getNextQuoted();
		if (DEBUG) System.out.println(message);
		//puts object inside schedule
		schedule.put(name, new Reminder(time, message));

		created = true;
		return created;
	}

	private static boolean createTimeSlot() {
		boolean created = false;
		//gets name of timeSlot
		String name = in.next();
		//gets start time of timeSlot
		LocalTime startTime = getNextLocalTime();
		if (DEBUG) System.out.println(startTime);
		//gets end time of timeSlot
		LocalTime endTime = getNextLocalTime();
		if (DEBUG) System.out.println(endTime);
		//gets message
		String message = getNextQuoted();
		//puts object inside schedule
		schedule.put(name, new TimeSlot(startTime, endTime, message));

		created = true;
		return created;
	}

	private static LocalDateTime getNextDateTime() {
		String timeInputted = in.next("\".*?\"");//regex for some reason extracts quotes with the string.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("\"yyyy-MM-dd|HH:mm\"");//quotes to account for regex extracting the quotes.
		LocalDateTime time = LocalDateTime.parse(timeInputted, formatter);
		return time;
	}

	private static LocalTime getNextLocalTime() {
		String timeInputted = in.next(".*?");
		LocalTime time = LocalTime.parse(timeInputted);
		return time;
	}

	private static String getNextQuoted() {
		return in.next("\"([^\"]*?)\"");//regex for some reason extracts quotes with the string.
	}
}
