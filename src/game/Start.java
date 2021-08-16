package game;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Start extends Application {


    @Override
    public void start(Stage stage) {
        new MainPage(stage);
        stage.getIcons().add(new Image("snake.png"));
        stage.setTitle("snake!!");
        stage.show();
    }

    public void begin(String[] args) {
        launch(args);
    }
}
