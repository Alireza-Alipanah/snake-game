package game;

import javafx.application.Application;
import javafx.stage.Stage;

public class Start extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setResizable(false);
        MainPage mainPage = new MainPage();
        stage.setScene(mainPage.getScene());
        stage.show();
    }

    public void begin(String[] args) {
        launch(args);
    }
}
