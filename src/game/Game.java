package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.LimeCircle;
import utils.Snake;

import java.util.Random;

import static javafx.scene.input.KeyCode.*;

public class Game {

    private static final Random RANDOM;

    static {
        RANDOM = new Random();
    }

    private final Stage STAGE;
    private final byte[][] PLACES;
    private final GridPane GRID_PANE;
    private final Timeline TIME_LINE;
    private final Circle FOOD;
    private Snake head, tail;

    public Game(Stage stage) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 36; j++) {
                Rectangle rectangle = new Rectangle(20, 20, Color.BLACK);
                gridPane.add(rectangle, i, j);
            }
        }
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        this.STAGE = stage;
        this.PLACES = new byte[64][36];
        int[] ints = getRandomLocation();
        Snake snake = new Snake(null, ints[0], ints[1]);
        snake.setFather(snake);
        this.head = this.tail = snake;
        gridPane.add(snake, ints[0], ints[1]);
        PLACES[ints[0]][ints[1]] += 0b1;
        FOOD = new LimeCircle();
        this.GRID_PANE = gridPane;
        changeFoodPosition();
        this.TIME_LINE = snakeMoveTimeLine(gridPane);
        direction(scene);
    }

    private void direction(Scene scene) {
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case UP:
                    if (head.getDirection() != DOWN)
                        head.setDirection(UP);
                    play();
                    break;
                case DOWN:
                    if (head.getDirection() != UP)
                        head.setDirection(DOWN);
                    play();
                    break;
                case LEFT:
                    if (head.getDirection() != RIGHT)
                        head.setDirection(LEFT);
                    play();
                    break;
                case RIGHT:
                    if (head.getDirection() != LEFT)
                        head.setDirection(RIGHT);
                    play();
                    break;
            }
        });
    }

    private Timeline snakeMoveTimeLine(GridPane gridPane) {
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), event -> {
            int x = head.getBoardX() + moveInDirectionHorizontally(head.getDirection());
            int y = head.getBoardY() + moveInDirectionVertically(head.getDirection());
            if (collisionWithSnakeOrBorder(x, y))
                stop();
            else {
                gridPane.getChildren().remove(tail);
                PLACES[tail.getBoardX()][tail.getBoardY()] -= 0b1;
                gridPane.add(tail, x, y);
                tail.setBoardX(x);
                tail.setBoardY(y);
                PLACES[x][y] += 0b1;
                tail.setDirection(head.getDirection());
                head.setFather(tail);
                head = tail;
                tail = tail.getFather();
                if (collisionWithFood(x, y)) {
                    PLACES[x][y] -= 0b10;
                    changeFoodPosition();
                    x = tail.getBoardX() - moveInDirectionHorizontally(tail.getDirection());
                    y = tail.getBoardY() - moveInDirectionVertically(tail.getDirection());
                    Snake newTail = new Snake(tail.getDirection(), x, y);
                    gridPane.add(newTail, x, y);
                    newTail.setFather(tail);
                    tail = newTail;
                    PLACES[x][y] += 0b1;
                }
            }
        });
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;
    }

    private void changeFoodPosition() {
        int[] ints;
        GRID_PANE.getChildren().remove(FOOD);
        do {
            ints = getRandomLocation();
        } while ((PLACES[ints[0]][ints[1]] & 0b1) != 0);
        GRID_PANE.add(FOOD, ints[0], ints[1]);
        PLACES[ints[0]][ints[1]] += 0b10;
    }

    private void play() {
        TIME_LINE.play();
    }

    private int[] getRandomLocation() {
        return new int[]{RANDOM.nextInt(64), RANDOM.nextInt(36)};
    }

    private int moveInDirectionVertically(KeyCode direction) {
        switch (direction) {
            case UP:
                return -1;
            case DOWN:
                return 1;
            default:
                return 0;
        }
    }

    private int moveInDirectionHorizontally(KeyCode direction) {
        switch (direction) {
            case RIGHT:
                return 1;
            case LEFT:
                return -1;
            default:
                return 0;
        }
    }

    private boolean collisionWithSnakeOrBorder(int x, int y) {
        if (x > 63 || x < 0 || y < 0 || y > 35)
            return true;
        return (PLACES[x][y] & 0b1) != 0;
    }

    private boolean collisionWithFood(int x, int y) {
        return (PLACES[x][y] & 0b10) != 0;
    }

    private void stop() {
        TIME_LINE.stop();
        new MainPage(STAGE);
    }
}
