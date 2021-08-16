package utils;

import javafx.scene.input.KeyCode;

public class Snake extends GreenRectangle {

    private KeyCode direction;
    private Snake father;
    private int boardX, boardY;

    public Snake(KeyCode direction, int boardX, int boardY) {
        this.direction = direction;
        this.boardX = boardX;
        this.boardY = boardY;
    }

    public KeyCode getDirection() {
        return direction;
    }

    public void setDirection(KeyCode direction) {
        this.direction = direction;
    }

    public void setFather(Snake father) {
        this.father = father;
    }

    public Snake getFather() {
        return father;
    }

    public void setBoardX(int boardX) {
        this.boardX = boardX;
    }

    public int getBoardX() {
        return boardX;
    }

    public void setBoardY(int boardY) {
        this.boardY = boardY;
    }

    public int getBoardY() {
        return boardY;
    }
}
