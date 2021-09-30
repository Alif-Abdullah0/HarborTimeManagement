import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.concurrent.Worker.State;
import netscape.javascript.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import site.java.*;
import schedule.*;
import java.io.*;

public class HarborDesktopApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		//creates object that will handle JS, HTML, and CSS of local and internet stuff.
		WebView webView = new WebView();
		//loads html
		WebEngine webEngine = webView.getEngine();
		webEngine.load(getClass().getResource("/site/index.html").toString());
		//loads JS
		webEngine.getLoadWorker().stateProperty().addListener(//checks if the engine has finished loading in
			new ChangeListener<State>() {
				@Override//indicates its overriding a previously existing changed function. This is presumably in the class ChangeListener.
				public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {//overrides an existing changed method that does nothing with this one that does something if newStte is that it succeeded loading.
					if (newState == State.SUCCEEDED) {//checks if java has successfully loaded the site
						JSObject window = (JSObject)webEngine.executeScript("window");//it imports the javascript of the site into java as the variable window.
						JavascriptUpcalls jsu = new JavascriptUpcalls();//this maintains the jsu reference in java, in case we need it later. It apparently also prevents java garbage collection from deleting it.
						//creates an object that will handle the majority of the JS upcalls in our program.
						window.setMember("program", jsu);//it maps the object named "jsu" to "program" in the javascript.
					}
				}
			}
		);
		//displays scene
		Scene scene = new Scene(webView,600,600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Harbor Application");
		primaryStage.show();
	}

	public static void main(String[] args) {
		checkTime();
		checkIfInSession();
		launch(args);

	}

	public static void checkTime() {
		//imports schedule
		try {
			StoreSchedule.schedule = StoreSchedule.readSchedule();
		} catch (FileNotFoundException e) {
			StoreSchedule.writeSchedule(StoreSchedule.schedule);
		}
		// creates timer
        Timer timer = new Timer();
        // creates task
        TimerTask TimerTask = new TimerTask() {
            @Override
            public void run() {
				for (String message: StoreSchedule.schedule.keySet()) {
					StoreSchedule.schedule.get(message).isTime();
				}
            };



        };
        // schedules the task to be run every second. This prevents drift.
        timer.scheduleAtFixedRate(TimerTask,new Date(),1000);
	}

	public static void checkIfInSession() {
		for (String message: StoreSchedule.schedule.keySet()) {
			LocalTime time = LocalTime.now();
			Scheduled object = StoreSchedule.schedule.get(message);
			if (time.isAfter(object.getStartTime()) && time.isBefore(object.getEndTime())) InSession.inSession++;
		}
		try {
			if (InSession.inSession > 0) BlockAndUnblock.block();
		} catch (IOException e) {
			System.out.println("IO Exception");
		}
	}
}
