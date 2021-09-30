package schedule;

import java.io.*;
import java.util.HashMap;

public class StoreSchedule {

	public static HashMap<String, Scheduled> schedule = new HashMap<String, Scheduled>();

	public static void writeSchedule(HashMap<String, Scheduled> schedule) {
		try {
			FileOutputStream fileOut = new FileOutputStream("schedule.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(schedule);
			out.close();
			fileOut.close();
			System.out.println("HashMap save successfully");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public static HashMap<String, Scheduled> readSchedule() throws FileNotFoundException {
		HashMap<String, Scheduled> s = null;
		try {
			FileInputStream fileIn = new FileInputStream("schedule.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			s = (HashMap<String, Scheduled>) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			//i.printStackTrace();
			HashMap<String, Scheduled> nu = new HashMap<String, Scheduled>();
			writeSchedule(nu);
			System.out.println("HashMap created successfully");
			return nu;
		} catch (ClassNotFoundException c) {
			System.out.println("Scheduled not found.");
			c.printStackTrace();
			return new HashMap<String, Scheduled>();
		}

		return s;
	}
}
