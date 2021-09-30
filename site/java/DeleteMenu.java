package site.java;

import javafx.application.Application;
import javafx.scene.control.ScrollPane;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.util.Callback;
import java.io.*;
import java.util.HashMap;
import java.time.*;
import schedule.*;

public class DeleteMenu extends Application{
	private TableView table = new TableView();
	public static void main(String[] args){
		System.out.println("main triggered");
		launch();
	}

	@Override
	public void start(Stage stage) throws FileNotFoundException{;
		// Setting up application
		Scene scene = new Scene(new Group());
		stage.setTitle("Delete Menu");
		stage.setWidth(425);
		stage.setHeight(500);
		// Getting data from schedule and converting to ObservableList
		HashMap<String, Scheduled> data = StoreSchedule.schedule;
		ObservableList<TableScheduled> schedule = FXCollections.observableArrayList();

		data.forEach((key, value) -> {
			schedule.add(new TableScheduled(key, value));
		});
		// Seting schedule as table items
		table.setItems(schedule);
		// Creating the columns for the table
		TableColumn<TableScheduled, String> nameCol = new TableColumn<>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<TableScheduled, LocalTime> startTimeCol = new TableColumn<>("Start Time");
		startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));

		TableColumn<TableScheduled, LocalTime> endTimeCol = new TableColumn<>("End Time");
		endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));

		TableColumn<TableScheduled, LocalTime> messageCol = new TableColumn<>("Message");
		messageCol.setCellValueFactory(new PropertyValueFactory<>("message"));

		TableColumn<TableScheduled, Void> deleteCol = new TableColumn<>("Delete?");
		// Creating delete button for deleteCol
		Callback<TableColumn<TableScheduled, Void>, TableCell<TableScheduled, Void>> cellFactory = new Callback<TableColumn<TableScheduled, Void>, TableCell<TableScheduled, Void>>() {
			@Override
			public TableCell<TableScheduled, Void> call(final TableColumn<TableScheduled, Void> param) {
				final TableCell<TableScheduled, Void> cell = new TableCell<TableScheduled, Void>() {

					private final Button deleteBtn = new Button("X");
					{
						deleteBtn.setOnAction((ActionEvent event) -> {
							TableScheduled scheduled = getTableView().getItems().get(getIndex());
							getTableView().getItems().remove(getIndex());
							data.remove(scheduled.getName());
							StoreSchedule.writeSchedule(data);
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(deleteBtn);
						}
					}
				};
				return cell;
			}
		};

		deleteCol.setCellFactory(cellFactory);
		table.getColumns().addAll(nameCol, startTimeCol, endTimeCol, messageCol, deleteCol);

		ScrollPane sp = new ScrollPane();
		sp.setContent(table);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.getChildren().addAll(sp);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage.setScene(scene);
		stage.show();
	}
}
