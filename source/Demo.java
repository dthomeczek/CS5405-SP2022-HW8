package code;

// Imports used over the whole assignment
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;

/**
 * @author Devin Thomeczek
 */
public class Demo extends Application {
    
    Pane root;
    @Override // Override the start method in the Application class
    public void start(Stage stage) {
        // Create a pane to hold objects
        root = new Pane();
        buttons();
        // Create a scene for layout and place it in the stage
        Scene scene = new Scene(root, 750, 500);
        stage.setScene(scene); // Place the scene in the stage
        stage.show(); // Display the stage
        stage.setTitle("CS5405 HW8 - Demo of Button Panes and Circle Overlap"); // Set the stage title
    }

    // Creates panes and buttons for use on screen
    Pane front, problem, spatialrelations;
    Button button1, button2, button3;
    public void buttons() {
        button1 = new Button ("Front");

        button2 = new Button ("Problem");

        button3 = new Button ("SpatialRelations");

        Front f = new Front();
        front = f.displayfront();

        Problem p = new Problem();
        problem = p.displayproblem();

        SpatialRelations sr = new SpatialRelations();
        spatialrelations = sr.createCircle();

        button1.setTranslateX(150);
        button1.setTranslateY(10);

        button2.setTranslateX(300);
        button2.setTranslateY(10);

        button3.setTranslateX(450);
        button3.setTranslateY(10);

        root.getChildren().addAll(button1, button2, button3);
        button1.setOnAction(actionEvent-> {root.getChildren().clear(); root.getChildren().addAll(front, button1, button2, button3);});
        button2.setOnAction(actionEvent-> {root.getChildren().clear(); root.getChildren().addAll(problem, button1, button2, button3);});
        button3.setOnAction(actionEvent-> {root.getChildren().clear(); root.getChildren().addAll(spatialrelations, button1, button2, button3);});
    }

    public static void main(String[] args) {
        launch(args);
    }
}