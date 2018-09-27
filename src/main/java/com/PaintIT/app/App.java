package com.PaintIT.app;


import Game.GameSession;
import Game.Player;
import Game.Team;
import Views.MainMenuView;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
        launch(args);

    }

    @Override
    public void start (Stage primaryStage) throws Exception{
        primaryStage.setTitle("PainIT");

        // TopController.getInstance();
        GameSession gameSession = new GameSession();

        Scene scene = new Scene(TopController.getCurrentView());
        TopController.show(MainMenuView.class.getSimpleName());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}