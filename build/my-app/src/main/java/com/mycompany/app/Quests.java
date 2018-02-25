package com.mycompany.app;

import com.mycompany.app.view.GameView;
import com.mycompany.app.model.GameModel;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Quests class brings together major UI components and launches the application.
 *
 * to run:
 *   mvn package
 *   java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.Quests
 */

public class Quests extends Application{

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final String TITLE = "Quests of the Round Table";

    private GameModel gameModel = new GameModel();
    private GameView gameView = new GameView(gameModel);

    @Override
    public void start(Stage primaryStage) {
        LoggerMessageExample obj = new LoggerMessageExample();
        obj.runMe("new person");

        primaryStage.setTitle(TITLE);

        BorderPane root = new BorderPane();
        gameView.setAlignment(Pos.CENTER_LEFT);
        root.setCenter(gameView);

        Scene start = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setScene(start);
        primaryStage.setResizable(true);

        if(isWindows()) primaryStage.setMaximized(true);

        primaryStage.show();


    }

    private boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }

    public static void main(String[] args) {
        launch(args);
    }

}
