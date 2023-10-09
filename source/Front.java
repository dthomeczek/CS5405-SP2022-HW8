package code;

import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Front {
    Pane pane1;
    ImageView imageView;
    Label name, date, description;
    Image me;

    // Used to display the front page and its info/picture to the screen
    public Pane displayfront() {
        pane1 = new Pane();
         
        try {
            me = new Image(getClass().getResourceAsStream("/images/me.png"));
        } catch(Exception e){ e.printStackTrace();}

        imageView = new ImageView();
        imageView.setImage(me);
        imageView.relocate(255, 100);
        imageView.setFitWidth(150);
        imageView.setFitHeight(300);

        name = new Label("Devin Thomeczek");
        name.relocate(280, 50);

        date = new Label("4/28/2022");
        date.relocate(300, 65);

        description = new Label("This is my assignment #8 for CS5405 and this is a description to show I worked alone");
        description.relocate(125, 80);

        pane1.getChildren().addAll(name, date, description, imageView);

        return pane1;
    }
}