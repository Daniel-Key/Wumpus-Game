import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by dgk2 on 25/01/17.
 */
public class Wumpus extends Movable{

    private boolean alive;
    private int health;

    public boolean isAlive() {
        return alive;
    }

    public Wumpus(Room[][] board) {
        super(board);
        alive = true;
        health = Constants.WUMPUS_HEALTH;
    }

    public void move() {
    int a = ThreadLocalRandom.current().nextInt(0,3);
    char c = 'w';
    int oldXCoord = xCoord;
    int oldYCoord = yCoord;
    switch (a) {
        case 0:
            c = 'w';
            break;
        case 1:
            c = 'a';
            break;
        case 2:
            c = 's';
            break;
        case 3:
            c = 'd';
            break;
    }
    super.move(c);
    game.getBoard()[oldXCoord][oldYCoord].getGamePieces().remove(this);
    game.boardSpace(oldXCoord + 1, oldYCoord).setSmell(false);
    game.boardSpace(oldXCoord - 1, oldYCoord).setSmell(false);
    game.boardSpace(oldXCoord, oldYCoord + 1).setSmell(false);
    game.boardSpace(oldXCoord, oldYCoord - 1).setSmell(false);
    game.getBoard()[xCoord][yCoord].getGamePieces().add(this);
    }

    public void hit() {
        health--;
        if (health == 0) {
            alive = false;
        }
    }
}
