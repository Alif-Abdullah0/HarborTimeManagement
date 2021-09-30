package site.java;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.util.*;
import java.io.*;
import schedule.*;
import java.nio.file.*;

public class BlockUrlsMenu extends Application {
	private HashMap<String, Scheduled> schedule;
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
		System.out.print("Url gui successfully started.");
		final HBox hBox = new HBox();
		hBox.setSpacing(5);

		final TextField urlTextField = new TextField();
		urlTextField.setPromptText("Type Url Here");
		Button unblock = new Button("Unblock");
		Button block = new Button("Block");

		String hostsFile = BlockAndUnblock.getHostsFile();
		String blockedUrls = BlockAndUnblock.getBlockedUrls();
		boolean inSession = InSession.inSession > 0;

		ListView listView = new ListView();
		setListView(listView);
		listView.setId("Listview");
		
		unblock.setOnAction(event -> {
			try {
				BlockAndUnblock.unBlockSite(hostsFile,blockedUrls,urlTextField.getText(), inSession);
				setListView(listView);
				urlTextField.setText("");
			} 
			catch (IOException e) {
				e.printStackTrace();
				System.out.println("You encountered an IOException when trying to initiate the block and unblock buttons.");
			}
		});
		block.setOnAction(event -> {
			try {
				BlockAndUnblock.blockSite(hostsFile,blockedUrls,urlTextField.getText(), inSession);
				setListView(listView);
				urlTextField.setText("");
			} 
			catch (IOException e) {
				e.printStackTrace();
				System.out.println("You encountered an IOException when trying to initiate the block and unblock buttons.");
			}
		});
		

		hBox.getChildren().add(listView);
		hBox.getChildren().add(urlTextField);
		hBox.getChildren().add(block);
		hBox.getChildren().add(unblock);
		hBox.setAlignment(Pos.CENTER_LEFT);
		hBox.setId("Urls");

		Scene scene = new Scene(hBox, 750, 500);
		scene.getStylesheets().addAll(this.getClass().getResource("../style.css").toExternalForm());
		primaryStage.setTitle("Urls");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	public static ArrayList<String> readUrls() throws FileNotFoundException {
		try {
			ArrayList<String> file = (ArrayList<String>) Files.readAllLines(Paths.get(BlockAndUnblock.getBlockedUrls()));
			ArrayList<String> urls = new ArrayList<String>();
			for (String line: file) {
				if (line.contains("0.0.0.0")) {
					urls.add(line.replace("0.0.0.0 ","").replace("\n",""));
				}
			}
			return urls;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error when reading Urls");
			return null;
		}
	}
	public void setListView(ListView listView) throws FileNotFoundException {
		listView.getItems().clear();
		listView.getItems().addAll(readUrls());
	}

	public void setSchedule(HashMap<String, Scheduled> sched) {
		this.schedule = sched;
	}

	public HashMap<String, Scheduled> getSchedule() {
		return schedule;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
