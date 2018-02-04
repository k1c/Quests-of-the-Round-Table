package com.mycompany.app;

import com.mycompany.app.view.AdventureDeckView;
import com.mycompany.app.view.StoryDeckView;

import javafx.application.Application;
import javafx.geometry.Pos;
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
    private static final int MARGIN_OUTER = 5;
    private static final String TITLE = "Quests of the Round Table";

    private AdventureDeckView adventureDeckView = new AdventureDeckView();
    private StoryDeckView storyDeckView = new StoryDeckView();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(TITLE);

        GridPane root = new GridPane();
        root.setAlignment(Pos.TOP_RIGHT);
        root.setStyle("-fx-background-color: d3d3d3;");
        root.setHgap(MARGIN_OUTER);
        root.setVgap(MARGIN_OUTER);
        root.setPadding(new Insets(MARGIN_OUTER));

        root.add(adventureDeckView,0,0);
        root.add(storyDeckView, 1, 0);
        root.setGridLinesVisible(true); //used for debugging

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}