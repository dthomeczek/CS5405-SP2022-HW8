package code;

import javafx.scene.layout.*;
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
import java.io.InputStreamReader;
import java.lang.ClassLoader;
import java.io.BufferedReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Slider;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;

public class SpatialRelations {
    Pane pane; // The pane
    Label prompt, label, mousecoords; // Variables for labels
    TextField tField; // TextField for user input
    Circle circle, circle2; // The circles
    Button changeCircle; // Button to change selected circle
    boolean circle_check = true; // A boolean to check true for red circle or false for green circle
    Rectangle backdrop; // Set to allow for mouse event on screen (wouldn't click without an object to click on)
    int circle_val1, circle_val2, circle2_val1, circle2_val2; // x and y values for both circles
    double circle_val3, circle2_val3; // Radius for both circles
    double center_distance, radii_sum; // Calculated values based on circle dimensions and locations
    String c1xtoint, c1ytoint, c2xtoint, c2ytoint = ""; // String placeholders for double to string conversion
    double c1x, c1y, c2x, c2y = 0; // Int placeholders for string to int convertion
    Slider slider; // The slider
    
    public Pane createCircle() {
        pane = new Pane();
        changeCircle = new Button ("Red Circle Selected");
        changeCircle.relocate(0, 105);

        slider = new Slider();
        slider.setMin(0);
        slider.setMax(150);
        slider.setValue(100);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);

        backdrop = new Rectangle();
        backdrop.setWidth(750);
        backdrop.setHeight(500);
        backdrop.setOpacity(0);

        Scanner scanner = null;
        try
        {
            BufferedReader file = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/data/infile.txt")));
            scanner = new Scanner(file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        circle_val1 = scanner.nextInt();
        circle_val2 = scanner.nextInt();
        circle_val3 = scanner.nextDouble();
    
        circle2_val1 = scanner.nextInt();
        circle2_val2 = scanner.nextInt();
        circle2_val3 = scanner.nextDouble();

        scanner.close();

        // Prompt & Name
        prompt = new Label();
        prompt.setText("By: Devin Thomeczek - Determine if the two circles overlap.\n");
        prompt.relocate(0, 40);

        // First Circle
        circle = new Circle();
        circle.setCenterX(circle_val1);
        circle.setCenterY(circle_val2);
        circle.setRadius(circle_val3);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.RED);
        circle.setOpacity(0.5);

        // Second Circle
        circle2 = new Circle();
        circle2.setCenterX(circle2_val1);
        circle2.setCenterY(circle2_val2);
        circle2.setRadius(circle2_val3);
        circle2.setStroke(Color.BLACK);
        circle2.setFill(Color.LIGHTGREEN);
        circle2.setOpacity(0.5);

        // TextField for manual inputs
        tField = new TextField(circle_val1 + " " + circle_val2 + " " + circle_val3 + " " + circle2_val1 + " " + circle2_val2 + " " + circle2_val3);
        tField.setOnAction(new TextFieldHandler());
        tField.setPrefWidth(300);
        tField.relocate(0, 80);

        // Calls the button and mouse event for usage
        button();
        pane.setOnMousePressed(new MousePressEventHandler());

        // A label to hold the updated values based on mouse recentering or slider usage
        mousecoords = new Label("Current circle coords after given change: " + circle_val1 + " " + circle_val2 + " " + circle_val3 + " " + circle2_val1 + " " + circle2_val2 + " " + circle2_val3);
        mousecoords.relocate(0, 130);

        // Gets values from slider and updates them as necessary based on the initial values
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                // Checks if the button is set to circle 1 and updates circle 1 values if so
                if (circle_check == true) {
                    circle_val3 = new_val.doubleValue();
                    circle.setRadius(circle_val3);
                    mousecoords.setText("Current circle coords after given change: " + circle_val1 + " " + circle_val2 + " " + circle_val3 + " " + circle2_val1 + " " + circle2_val2 + " " + circle2_val3);
                
                    center_distance = Math.sqrt(((circle2_val1 - circle_val1) * (circle2_val1 - circle_val1)) + ((circle2_val2 - circle_val2) * (circle2_val2 - circle_val2)));
                    radii_sum = circle_val3 + circle2_val3;

                    // Used to output whether the circles are overlapping or not
                    if (center_distance > radii_sum)
                    {
                        label.setText("These Circles Don\'t Overlap And Are Disjoint.");
                    }
                    else if (center_distance == radii_sum)
                    {
                        label.setText("These Circles Touch Each Other From The Outside.");
                    }
                    else if (center_distance == 0 && circle_val3 == circle2_val3)
                    {
                        label.setText("Circle 1 And Circle 2 Are Equal.");
                    }
                    else if (center_distance == circle_val3 - circle2_val3)
                    {
                        label.setText("Circle 2 Touches And Is Inside Circle 1.");
                    }
                    else if (center_distance == circle2_val3 - circle_val3)
                    {
                        label.setText("Circle 1 Touches And Is Inside Circle 2.");
                    }
                    else if (center_distance < circle_val3 - circle2_val3)
                    {
                        label.setText("Circle 2 Is Inside But Does Not Touch Circle 1.");
                    }
                    else if (center_distance < circle2_val3 - circle_val3)
                    {
                        label.setText("Circle 1 Is Inside But Does Not Touch Circle 2.");
                    }
                    else
                    {
                        label.setText("Circle 1 And Circle 2 Have Proper Overlap.");
                    }
                
                }

                // Checks if the button is set to circle 2 and updates circle 2 values if so
                else if (circle_check == false) {
                    circle2_val3 = new_val.doubleValue();
                    circle2.setRadius(circle2_val3);
                    mousecoords.setText("Current circle coords after given change: " + circle_val1 + " " + circle_val2 + " " + circle_val3 + " " + circle2_val1 + " " + circle2_val2 + " " + circle2_val3);
                
                    center_distance = Math.sqrt(((circle2_val1 - circle_val1) * (circle2_val1 - circle_val1)) + ((circle2_val2 - circle_val2) * (circle2_val2 - circle_val2)));
                    radii_sum = circle_val3 + circle2_val3;

                    // Used to output whether the circles are overlapping or not
                    if (center_distance > radii_sum)
                    {
                        label.setText("These Circles Don\'t Overlap And Are Disjoint.");
                    }
                    else if (center_distance == radii_sum)
                    {
                        label.setText("These Circles Touch Each Other From The Outside.");
                    }
                    else if (center_distance == 0 && circle_val3 == circle2_val3)
                    {
                        label.setText("Circle 1 And Circle 2 Are Equal.");
                    }
                    else if (center_distance == circle_val3 - circle2_val3)
                    {
                        label.setText("Circle 2 Touches And Is Inside Circle 1.");
                    }
                    else if (center_distance == circle2_val3 - circle_val3)
                    {
                        label.setText("Circle 1 Touches And Is Inside Circle 2.");
                    }
                    else if (center_distance < circle_val3 - circle2_val3)
                    {
                        label.setText("Circle 2 Is Inside But Does Not Touch Circle 1.");
                    }
                    else if (center_distance < circle2_val3 - circle_val3)
                    {
                        label.setText("Circle 1 Is Inside But Does Not Touch Circle 2.");
                    }
                    else
                    {
                        label.setText("Circle 1 And Circle 2 Have Proper Overlap.");
                    }
                
                }
            }
        });

        // Used to determine if the values of the circles are overlapping
        center_distance = Math.sqrt(((circle2_val1 - circle_val1) * (circle2_val1 - circle_val1)) + ((circle2_val2 - circle_val2) * (circle2_val2 - circle_val2)));
        radii_sum = circle_val3 + circle2_val3;

        // Used to output whether the circles are overlapping or not
        label = new Label();
        if (center_distance > radii_sum)
        {
            label.setText("These Circles Don\'t Overlap And Are Disjoint.");
        }
        else if (center_distance == radii_sum)
        {
            label.setText("These Circles Touch Each Other From The Outside.");
        }
        else if (center_distance == 0 && circle_val3 == circle2_val3)
        {
            label.setText("Circle 1 And Circle 2 Are Equal.");
        }
        else if (center_distance == circle_val3 - circle2_val3)
        {
            label.setText("Circle 2 Touches And Is Inside Circle 1.");
        }
        else if (center_distance == circle2_val3 - circle_val3)
        {
            label.setText("Circle 1 Touches And Is Inside Circle 2.");
        }
        else if (center_distance < circle_val3 - circle2_val3)
        {
            label.setText("Circle 2 Is Inside But Does Not Touch Circle 1.");
        }
        else if (center_distance < circle2_val3 - circle_val3)
        {
            label.setText("Circle 1 Is Inside But Does Not Touch Circle 2.");
        }
        else
        {
            label.setText("Circle 1 And Circle 2 Have Proper Overlap.");
        }
        
        label.relocate(0, 60);
        
        // Apply updates to the pane
        pane.getChildren().addAll(backdrop,circle,circle2,prompt,label,tField,mousecoords,changeCircle, slider);
        return pane;
    }

    // Used for button functionality to switch between circles
    public void button() {
        changeCircle.setOnAction(actionEvent-> {
        if (circle_check == true)
        {
            changeCircle.setText("Green Circle Selected");
            circle_check = false;
        }
        else if (circle_check == false)
        {
            changeCircle.setText("Red Circle Selected");
            circle_check = true;
        }
        }); 
    }

    // Handles placement of the currently selected circle via a single click within the scene
    public class MousePressEventHandler implements EventHandler<MouseEvent> {
        public void handle(MouseEvent e) {
            // Checks if the button is set to circle 1 and updates circle 1 values if so for mouse repositioning
            if (circle_check == true)
            {
                if (e.getEventType() == MouseEvent.MOUSE_PRESSED)
                {
                    // To maintain values as ints, gets the value from getScene, applies to a double, then converts to string, cuts decimals, and converts back to int
                    c1x = e.getSceneX();
                    c1y = e.getSceneY();
                    c1xtoint = String.format("%.0f", c1x);
                    c1ytoint = String.format("%.0f", c1y);
                    circle_val1 = Integer.parseInt(c1xtoint);
                    circle_val2 = Integer.parseInt(c1ytoint);

                    circle.setCenterX(circle_val1);
                    circle.setCenterY(circle_val2);

                    mousecoords.setText("Current circle coords after given change: " + circle_val1 + " " + circle_val2 + " " + circle_val3 + " " + circle2_val1 + " " + circle2_val2 + " " + circle2_val3);
                    
                    center_distance = Math.sqrt(((circle2_val1 - circle_val1) * (circle2_val1 - circle_val1)) + ((circle2_val2 - circle_val2) * (circle2_val2 - circle_val2)));
                    radii_sum = circle_val3 + circle2_val3;

                    // Used to output whether the circles are overlapping or not
                    if (center_distance > radii_sum)
                    {
                        label.setText("These Circles Don\'t Overlap And Are Disjoint.");
                    }
                    else if (center_distance == radii_sum)
                    {
                        label.setText("These Circles Touch Each Other From The Outside.");
                    }
                    else if (center_distance == 0 && circle_val3 == circle2_val3)
                    {
                        label.setText("Circle 1 And Circle 2 Are Equal.");
                    }
                    else if (center_distance == circle_val3 - circle2_val3)
                    {
                        label.setText("Circle 2 Touches And Is Inside Circle 1.");
                    }
                    else if (center_distance == circle2_val3 - circle_val3)
                    {
                        label.setText("Circle 1 Touches And Is Inside Circle 2.");
                    }
                    else if (center_distance < circle_val3 - circle2_val3)
                    {
                        label.setText("Circle 2 Is Inside But Does Not Touch Circle 1.");
                    }
                    else if (center_distance < circle2_val3 - circle_val3)
                    {
                        label.setText("Circle 1 Is Inside But Does Not Touch Circle 2.");
                    }
                    else
                    {
                        label.setText("Circle 1 And Circle 2 Have Proper Overlap.");
                    }
                }
            }

            // Checks if the button is set to circle 2 and updates circle 2 values if so for mouse repositioning
            else if (circle_check == false)
            {
                if (e.getEventType() == MouseEvent.MOUSE_PRESSED)
                {
                    // To maintain values as ints, gets the value from getScene, applies to a double, then converts to string, cuts decimals, and converts back to int
                    c2x = e.getSceneX();
                    c2y = e.getSceneY();
                    c2xtoint = String.format("%.0f", c2x);
                    c2ytoint = String.format("%.0f", c2y);
                    circle2_val1 = Integer.parseInt(c2xtoint);
                    circle2_val2 = Integer.parseInt(c2ytoint);

                    circle2.setCenterX(circle2_val1);
                    circle2.setCenterY(circle2_val2);

                    mousecoords.setText("Current circle coords after given change: " + circle_val1 + " " + circle_val2 + " " + circle_val3 + " " + circle2_val1 + " " + circle2_val2 + " " + circle2_val3);
                    
                    center_distance = Math.sqrt(((circle2_val1 - circle_val1) * (circle2_val1 - circle_val1)) + ((circle2_val2 - circle_val2) * (circle2_val2 - circle_val2)));
                    radii_sum = circle_val3 + circle2_val3;

                    // Used to output whether the circles are overlapping or not
                    if (center_distance > radii_sum)
                    {
                        label.setText("These Circles Don\'t Overlap And Are Disjoint.");
                    }
                    else if (center_distance == radii_sum)
                    {
                        label.setText("These Circles Touch Each Other From The Outside.");
                    }
                    else if (center_distance == 0 && circle_val3 == circle2_val3)
                    {
                        label.setText("Circle 1 And Circle 2 Are Equal.");
                    }
                    else if (center_distance == circle_val3 - circle2_val3)
                    {
                        label.setText("Circle 2 Touches And Is Inside Circle 1.");
                    }
                    else if (center_distance == circle2_val3 - circle_val3)
                    {
                        label.setText("Circle 1 Touches And Is Inside Circle 2.");
                    }
                    else if (center_distance < circle_val3 - circle2_val3)
                    {
                        label.setText("Circle 2 Is Inside But Does Not Touch Circle 1.");
                    }
                    else if (center_distance < circle2_val3 - circle_val3)
                    {
                        label.setText("Circle 1 Is Inside But Does Not Touch Circle 2.");
                    }
                    else
                    {
                        label.setText("Circle 1 And Circle 2 Have Proper Overlap.");
                    }
                }
            }

            // For dealing with the slider post-click placement
            slider.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    // Checks if the button is set to circle 1 and updates circle 1 values if so
                    if (circle_check == true) {
                        circle_val3 = new_val.doubleValue();
                        circle.setRadius(circle_val3);
                        mousecoords.setText("Current circle coords after given change: " + circle_val1 + " " + circle_val2 + " " + circle_val3 + " " + circle2_val1 + " " + circle2_val2 + " " + circle2_val3);
                    
                        center_distance = Math.sqrt(((circle2_val1 - circle_val1) * (circle2_val1 - circle_val1)) + ((circle2_val2 - circle_val2) * (circle2_val2 - circle_val2)));
                        radii_sum = circle_val3 + circle2_val3;

                        // Used to output whether the circles are overlapping or not
                        if (center_distance > radii_sum)
                        {
                            label.setText("These Circles Don\'t Overlap And Are Disjoint.");
                        }
                        else if (center_distance == radii_sum)
                        {
                            label.setText("These Circles Touch Each Other From The Outside.");
                        }
                        else if (center_distance == 0 && circle_val3 == circle2_val3)
                        {
                            label.setText("Circle 1 And Circle 2 Are Equal.");
                        }
                        else if (center_distance == circle_val3 - circle2_val3)
                        {
                            label.setText("Circle 2 Touches And Is Inside Circle 1.");
                        }
                        else if (center_distance == circle2_val3 - circle_val3)
                        {
                            label.setText("Circle 1 Touches And Is Inside Circle 2.");
                        }
                        else if (center_distance < circle_val3 - circle2_val3)
                        {
                            label.setText("Circle 2 Is Inside But Does Not Touch Circle 1.");
                        }
                        else if (center_distance < circle2_val3 - circle_val3)
                        {
                            label.setText("Circle 1 Is Inside But Does Not Touch Circle 2.");
                        }
                        else
                        {
                            label.setText("Circle 1 And Circle 2 Have Proper Overlap.");
                        }
                    }

                    // Checks if the button is set to circle 2 and updates circle 2 values if so
                    else if (circle_check == false) {
                        circle2_val3 = new_val.doubleValue();
                        circle2.setRadius(circle2_val3);
                        mousecoords.setText("Current circle coords after given change: " + circle_val1 + " " + circle_val2 + " " + circle_val3 + " " + circle2_val1 + " " + circle2_val2 + " " + circle2_val3);
                    
                        center_distance = Math.sqrt(((circle2_val1 - circle_val1) * (circle2_val1 - circle_val1)) + ((circle2_val2 - circle_val2) * (circle2_val2 - circle_val2)));
                        radii_sum = circle_val3 + circle2_val3;

                        // Used to output whether the circles are overlapping or not
                        if (center_distance > radii_sum)
                        {
                            label.setText("These Circles Don\'t Overlap And Are Disjoint.");
                        }
                        else if (center_distance == radii_sum)
                        {
                            label.setText("These Circles Touch Each Other From The Outside.");
                        }
                        else if (center_distance == 0 && circle_val3 == circle2_val3)
                        {
                            label.setText("Circle 1 And Circle 2 Are Equal.");
                        }
                        else if (center_distance == circle_val3 - circle2_val3)
                        {
                            label.setText("Circle 2 Touches And Is Inside Circle 1.");
                        }
                        else if (center_distance == circle2_val3 - circle_val3)
                        {
                            label.setText("Circle 1 Touches And Is Inside Circle 2.");
                        }
                        else if (center_distance < circle_val3 - circle2_val3)
                        {
                            label.setText("Circle 2 Is Inside But Does Not Touch Circle 1.");
                        }
                        else if (center_distance < circle2_val3 - circle_val3)
                        {
                            label.setText("Circle 1 Is Inside But Does Not Touch Circle 2.");
                        }
                        else
                        {
                            label.setText("Circle 1 And Circle 2 Have Proper Overlap.");
                        }
                    }
                }
            });
        }
    }

    // Handles the enter button press for changing values
    public class TextFieldHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e)
        {
            String str = tField.getText();
            String delimiter = " ";
            String[] nums_str;
            nums_str= str.split(delimiter);

            circle_val1 = Integer.parseInt(nums_str[0]);
            circle_val2 = Integer.parseInt(nums_str[1]);
            circle_val3 = Double.parseDouble(nums_str[2]);

            circle2_val1 = Integer.parseInt(nums_str[3]);
            circle2_val2 = Integer.parseInt(nums_str[4]);
            circle2_val3 = Double.parseDouble(nums_str[5]);

            slider.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    // Checks if the button is set to circle 1 and updates circle 1 values if so
                    if (circle_check == true) {
                        circle_val3 = new_val.doubleValue();
                        circle.setRadius(circle_val3);
                        mousecoords.setText("Current circle coords after given change: " + circle_val1 + " " + circle_val2 + " " + circle_val3 + " " + circle2_val1 + " " + circle2_val2 + " " + circle2_val3);
                
                        center_distance = Math.sqrt(((circle2_val1 - circle_val1) * (circle2_val1 - circle_val1)) + ((circle2_val2 - circle_val2) * (circle2_val2 - circle_val2)));
                        radii_sum = circle_val3 + circle2_val3;

                        // Used to output whether the circles are overlapping or not
                        if (center_distance > radii_sum)
                        {
                            label.setText("These Circles Don\'t Overlap And Are Disjoint.");
                        }
                        else if (center_distance == radii_sum)
                        {
                            label.setText("These Circles Touch Each Other From The Outside.");
                        }
                        else if (center_distance == 0 && circle_val3 == circle2_val3)
                        {
                            label.setText("Circle 1 And Circle 2 Are Equal.");
                        }
                        else if (center_distance == circle_val3 - circle2_val3)
                        {
                            label.setText("Circle 2 Touches And Is Inside Circle 1.");
                        }
                        else if (center_distance == circle2_val3 - circle_val3)
                        {
                            label.setText("Circle 1 Touches And Is Inside Circle 2.");
                        }
                        else if (center_distance < circle_val3 - circle2_val3)
                        {
                            label.setText("Circle 2 Is Inside But Does Not Touch Circle 1.");
                        }
                        else if (center_distance < circle2_val3 - circle_val3)
                        {
                            label.setText("Circle 1 Is Inside But Does Not Touch Circle 2.");
                        }
                        else
                        {
                            label.setText("Circle 1 And Circle 2 Have Proper Overlap.");
                        }
                    }

                    // Checks if the button is set to circle 1 and updates circle 1 values if so
                    else if (circle_check == false) {
                        circle2_val3 = new_val.doubleValue();
                        circle2.setRadius(circle2_val3);
                        mousecoords.setText("Current circle coords after given change: " + circle_val1 + " " + circle_val2 + " " + circle_val3 + " " + circle2_val1 + " " + circle2_val2 + " " + circle2_val3);
                
                        center_distance = Math.sqrt(((circle2_val1 - circle_val1) * (circle2_val1 - circle_val1)) + ((circle2_val2 - circle_val2) * (circle2_val2 - circle_val2)));
                        radii_sum = circle_val3 + circle2_val3;

                        // Used to output whether the circles are overlapping or not
                        if (center_distance > radii_sum)
                        {
                            label.setText("These Circles Don\'t Overlap And Are Disjoint.");
                        }
                        else if (center_distance == radii_sum)
                        {
                            label.setText("These Circles Touch Each Other From The Outside.");
                        }
                        else if (center_distance == 0 && circle_val3 == circle2_val3)
                        {
                            label.setText("Circle 1 And Circle 2 Are Equal.");
                        }
                        else if (center_distance == circle_val3 - circle2_val3)
                        {
                            label.setText("Circle 2 Touches And Is Inside Circle 1.");
                        }
                        else if (center_distance == circle2_val3 - circle_val3)
                        {
                            label.setText("Circle 1 Touches And Is Inside Circle 2.");
                        }
                        else if (center_distance < circle_val3 - circle2_val3)
                        {
                            label.setText("Circle 2 Is Inside But Does Not Touch Circle 1.");
                        }
                        else if (center_distance < circle2_val3 - circle_val3)
                        {
                            label.setText("Circle 1 Is Inside But Does Not Touch Circle 2.");
                        }
                        else
                        {
                            label.setText("Circle 1 And Circle 2 Have Proper Overlap.");
                        }
                    }
                }
            });

            circle.setCenterX(circle_val1);
            circle.setCenterY(circle_val2);
            circle.setRadius(circle_val3);

            circle2.setCenterX(circle2_val1);
            circle2.setCenterY(circle2_val2);
            circle2.setRadius(circle2_val3);

            center_distance = Math.sqrt(((circle2_val1 - circle_val1) * (circle2_val1 - circle_val1)) + ((circle2_val2 - circle_val2) * (circle2_val2 - circle_val2)));
            radii_sum = circle_val3 + circle2_val3;

            // Used to output whether the circles are overlapping or not
            if (center_distance > radii_sum)
            {
                label.setText("These Circles Don\'t Overlap And Are Disjoint.");
            }
            else if (center_distance == radii_sum)
            {
                label.setText("These Circles Touch Each Other From The Outside.");
            }
            else if (center_distance == 0 && circle_val3 == circle2_val3)
            {
                label.setText("Circle 1 And Circle 2 Are Equal.");
            }
            else if (center_distance == circle_val3 - circle2_val3)
            {
                label.setText("Circle 2 Touches And Is Inside Circle 1.");
            }
            else if (center_distance == circle2_val3 - circle_val3)
            {
                label.setText("Circle 1 Touches And Is Inside Circle 2.");
            }
            else if (center_distance < circle_val3 - circle2_val3)
            {
                label.setText("Circle 2 Is Inside But Does Not Touch Circle 1.");
            }
            else if (center_distance < circle2_val3 - circle_val3)
            {
                label.setText("Circle 1 Is Inside But Does Not Touch Circle 2.");
            }
            else
            {
                label.setText("Circle 1 And Circle 2 Have Proper Overlap.");
            }
        }
    }
}