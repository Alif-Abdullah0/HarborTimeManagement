package site.java;
import javafx.stage.Stage;
import java.io.*;
import schedule.*;

public class JavascriptUpcalls {
	public void exit() {
		StoreSchedule.writeSchedule(StoreSchedule.schedule);
		try {
			BlockAndUnblock.unBlock();
		} catch (IOException e) {
			System.out.println("IO Error");
		}
		System.out.println("Exited program successfully.");
		System.exit(0);
	}

	public void newEventMenu() {
		System.out.println("new event menu should trigger");
		NewEventMenu newEventMenu = new NewEventMenu();
		Stage s = new Stage();

		try {
			newEventMenu.start(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void deleteMenu() {
		System.out.println("delete menu should trigger");
		DeleteMenu deleteMenu = new DeleteMenu();
		Stage s = new Stage();
		try {
			deleteMenu.start(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void blockUrlsMenu() {
		System.out.println("block urls menu should trigger");
		BlockUrlsMenu blockUrlsMenu = new BlockUrlsMenu();
		blockUrlsMenu.setSchedule(StoreSchedule.schedule);

		Stage s = new Stage();
		try {
			blockUrlsMenu.start(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
