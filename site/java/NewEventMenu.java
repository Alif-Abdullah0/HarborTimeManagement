package site.java;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import schedule.*;

import java.awt.*;
import java.awt.TextArea;
import java.awt.desktop.AppForegroundListener;
import java.awt.event.PaintEvent;
import java.time.LocalTime;
import java.util.Locale;
import java.io.FileNotFoundException;

public class NewEventMenu extends Application {

    // This program creates Reminders and TimeSlots for the user.
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
       primaryStage.setTitle("Yeomer");
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        Button button = new Button("Reminder");

        button.setMinSize(Button.USE_PREF_SIZE,Button.USE_PREF_SIZE);
        button.setStyle("-fx-background-color: #FFF");
        button.setStyle("-fx-accent: #f293ff");
        button.setStyle("-fx-focus-color: #23930f");

        Button button1 = new Button("Timeslot");
        button1.setMinSize(Button.USE_PREF_SIZE,Button.USE_PREF_SIZE);
        button1.setStyle("-fx-background-color: #FFF");
        button1.setStyle("-fx-accent: #f293ff");
        button1.setStyle("-fx-focus-color: #23930f");

        grid.getChildren().add(button);

        TextField t0 = new TextField();
        t0.setPromptText("Message");
        TextField t1 = new TextField();
        t1.setPromptText("HourHour:MinuteMinute");
        TextField t2 = new TextField();
        t2.setPromptText("HourHour:MinuteMinute");
        t2.setPrefSize(t2.getPrefHeight(), t2.getPrefWidth() );
        TextField t3 = new TextField();
        t3.setPromptText("Title");

        Text txt = new Text("Start Time (in military time):");
        Text txt0 = new Text("(Fill Only When Creating Time Slot): End Time (in military time) :");
        Text txt1 = new Text("Confirm Message:");
        txt1.setVisible(false);

        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                StoreSchedule.schedule.put(t3.getText(), new Reminder(LocalTime.parse(t1.getText()), t0.getText()));
				StoreSchedule.writeSchedule(StoreSchedule.schedule);
                txt1.setText("Confirm Message: You set a reminder!");
                txt1.setVisible(true);
            }
        });

        button1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                StoreSchedule.schedule.put(t3.getText(), new TimeSlot(LocalTime.parse(t1.getText()), LocalTime.parse(t2.getText()), t0.getText()));
				StoreSchedule.writeSchedule(StoreSchedule.schedule);
                txt1.setText("Confirm Message: You set a time slot!");
                txt1.setVisible(true);
            }
        });

        // constraining elements
        GridPane.setConstraints(button,0,0);
        GridPane.setConstraints(button1,0,1);
        GridPane.setConstraints(txt, 0, 2);
        GridPane.setConstraints(txt0, 0, 3);
        GridPane.setConstraints(txt1, 0, 4);
        GridPane.setConstraints(t0, 1, 1);
        GridPane.setConstraints(t1, 1, 2);
        GridPane.setConstraints(t2, 1, 3);
        GridPane.setConstraints(t3, 1, 0);



        // adding elements
        grid.getChildren().add(button1);
        grid.getChildren().add(t0);
        grid.getChildren().add(t1);
        grid.getChildren().add(t2);
        grid.getChildren().add(t3);
        grid.getChildren().add(txt);
        grid.getChildren().add(txt0);
        grid.getChildren().add(txt1);
        Scene scene = new Scene(grid, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        System.out.println();
        Application.launch(args);
    }
}
