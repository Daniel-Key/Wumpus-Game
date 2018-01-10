/**
 * Created by dgk2 on 25/01/17.
 */
import java.util.concurrent.ThreadLocalRandom;

public abstract class GamePiece {

    protected int xCoord;
    protected int yCoord;
    protected static Game game;

    public GamePiece(Room[][] board) {
        generateEmptyCoords(board);
    }

    public GamePiece() {
    }

    public static int generateNumber() {
        return ThreadLocalRandom.current().nextInt(0,game.getBoardSize());
    }

    public int getxCoord() {
        return xCoord;
    }
    public int getyCoord() {
        return yCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }
    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public void generateEmptyCoords(Room[][] board) {
        generatePosition();
        do {
            if (board[xCoord][yCoord].getGamePieces().size() > 0) {
                generatePosition();
            }
            else {
                board[xCoord][yCoord].getGamePieces().add(this);
            }
        }
        while (!board[xCoord][yCoord].getGamePieces().contains(this));
    }

    public void generatePosition() {
        xCoord = generateNumber();
        yCoord = generateNumber();
    }

    protected int validCoord (int i) {
        i = (i + game.getBoardSize()) % game.getBoardSize();
        return i;
    }
}