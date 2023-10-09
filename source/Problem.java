package code;

import javafx.scene.layout.*;
import javafx.scene.control.Label;
import java.io.FileNotFoundException;
import javafx.scene.text.TextAlignment;

public class Problem {
    Pane pane;
    Label problem;

    // Used to display the problem to the screen
    public Pane displayproblem() {
        pane = new Pane();
        problem = new Label("Create a program that has two circles as input: the input parameters are x, y, r for each circle.");
        problem.relocate(120, 50);
        pane.getChildren().add(problem);
        return pane;
    }
}