import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by dgk2 on 25/01/17.
 */
public abstract class Adventurer extends Movable {
    protected boolean alive;
    protected int arrowNo;
    protected int grapplingHookNo;
    protected boolean hasTreasure;

    public Adventurer(Room[][] board) {
        super(board);
        alive = true;
        game.getBoard()[xCoord][yCoord].setDiscovered(true);
        arrowNo = 3;
        grapplingHookNo = 0;
        hasTreasure = false;
    }

    public boolean isHasTreasure() {
        return hasTreasure;
    }

    public void setHasTreasure(boolean hasTreasure) {
        this.hasTreasure = hasTreasure;
    }

    public int getArrowNo() {
        return arrowNo;
    }

    public int getGrapplingHookNo() {
        return grapplingHookNo;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void move(boolean mode, char c) {
        int oldXCoord = xCoord;
        int oldYCoord = yCoord;
        if (mode) {
            super.move(c);
        }
        else if (arrowNo > 0){
            switch (c) {
                case 'w':
                    game.getBoard()[validCoord(xCoord)][validCoord(yCoord-1)].fireArrow();
                    arrowNo--;
                    break;
                case 'a':
                    game.getBoard()[validCoord(xCoord-1)][validCoord(yCoord)].fireArrow();
                    arrowNo--;
                    break;
                case 's':
                    game.getBoard()[validCoord(xCoord)][validCoord(yCoord+1)].fireArrow();
                    arrowNo--;
                    break;
                case 'd':
                    game.getBoard()[validCoord(xCoord+1)][validCoord(yCoord)].fireArrow();
                    arrowNo--;
                    break;
            }
        }
        game.getBoard()[oldXCoord][oldYCoord].getGamePieces().remove(this);
        game.getBoard()[xCoord][yCoord].getGamePieces().add(this);
        game.getBoard()[xCoord][yCoord].setDiscovered(true);
    }

    public char randomBestMove(ArrayList<String> bestOptions) {
        if (bestOptions.size()>0) {
            int a = ThreadLocalRandom.current().nextInt(0, bestOptions.size());
            char c = bestOptions.get(a).charAt(0);
            return c;
        }
        return 'w';

    }

    public void randomTeleport() {
        game.getBoard()[xCoord][yCoord].getGamePieces().remove(this);
        generateEmptyCoords(game.getBoard());
        game.getBoard()[xCoord][yCoord].setDiscovered(true);
    }
}
