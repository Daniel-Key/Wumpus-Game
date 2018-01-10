/**
 * Created by dgk2 on 25/01/17.
 */
import java.util.ArrayList;
public class Room {

    private boolean smell;
    private boolean breeze;
    private boolean glitter;
    private boolean discovered;
    private int xCoord;
    private int yCoord;
    private Game game;
    private ArrayList<GamePiece> gamePieces = new ArrayList<GamePiece>();

    public ArrayList<GamePiece> getGamePieces() {
        return gamePieces;
    }


    public boolean isSmell() {
        return smell;
    }
    public boolean isBreeze() {
        return breeze;
    }
    public boolean isGlitter() {
        return glitter;
    }

    public void setSmell(boolean smell) {
        this.smell = smell;
    }
    public void setBreeze(boolean breeze) {
        this.breeze = breeze;
    }
    public void setGlitter(boolean glitter) {
        this.glitter = glitter;
    }

    public int getXCoord() {
        return xCoord;
    }
    public int getYCoord() {
        return yCoord;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public void fireArrow() {
        if (gamePieces.contains(game.getWumpus()) && game.getWumpus().isAlive()) {
            game.getWumpus().hit();
        }
        else {
            gamePieces.add(new Arrow(xCoord, yCoord));
        }
    }

    public ArrayList<String> feedback() {
        ArrayList<String> feedback = new ArrayList<String>();
        if (smell) {
            feedback.add("You smell a wumpus");
        }
        if (breeze) {
            feedback.add("You feel a breeze");
        }
        if (glitter) {
            feedback.add("You see glitter from some nearby treasure");
        }
        return feedback;
    }

    public Room(int x, int y, Game game) {
        discovered = false;
        xCoord = x;
        yCoord = y;
        this.game = game;
    }
}

