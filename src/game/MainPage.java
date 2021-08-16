package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainPage {

    public MainPage(Stage stage) {
        Label label = new Label();
        label.setAlignment(Pos.CENTER);
        label.setText("SNAKE!!");
        label.setTextFill(Color.GREEN);
        label.setFont(new Font(80));
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1),
                event -> label.setTextFill(label.getTextFill() == Color.GREEN ? Color.LIME : Color.GREEN));
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        Button button = new Button();
        Background greenBackground = new Background(new BackgroundFill(Color.GREEN, null, null));
        Background limeBackground = new Background(new BackgroundFill(Color.LIME, null, null));
        button.setBackground(greenBackground);
        button.setText("Play");
        button.setAlignment(Pos.CENTER);
        button.setFont(new Font(24));
        button.setTextFill(Color.BLACK);
        button.setOnMouseEntered(e -> button.setBackground(limeBackground));
        button.setOnMouseExited(e -> button.setBackground(greenBackground));
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, button);
        vBox.setPrefWidth(1280);
        vBox.setPrefHeight(720);
        Paint paint = Color.BLACK;
        BackgroundFill backgroundFill = new BackgroundFill(paint, null, null);
        Background background = new Background(backgroundFill);
        vBox.setBackground(background);
        button.setOnMouseClicked(e -> {
            timeline.stop();
            new Game(stage);
        });
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
    }
}
