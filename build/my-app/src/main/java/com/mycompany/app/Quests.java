package com.mycompany.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

/**
 * Quests class brings together major UI compoements and launches the application.
 *
 * to run:
 *   mvn package
 *   java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.Quests
 */

public class Quests extends Application {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int MARGING_OUTER = 5;
    private static final String TITLE = "Quests of the Roundtable";

    //private AdventureDeckView adventureDeckView = new AdventureDeckView();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(TITLE);

        GridPane root = new GridPane();
        root.setStyle("-fx-background-color: d3d3d3;");
        root.setHgap(MARGING_OUTER);
        root.setVgap(MARGING_OUTER);
        root.setPadding(new Insets(MARGING_OUTER));

        //root.add(adventureDeckView,0,0);

        root.setGridLinesVisible(false); //used for debugging

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}