/**
 * Created by Connor on 13/02/2017.
 */
public abstract class Movable extends GamePiece {

    public Movable(Room[][] board) {
        super(board);
    }

    public void move(char c) {
        switch(c) {
            case 'w':
                yCoord = validCoord(yCoord - 1);
                break;
            case 'a':
                xCoord = validCoord(xCoord - 1);
                break;
            case 's':
                yCoord = validCoord(yCoord + 1);
                break;
            case 'd':
                xCoord = validCoord(xCoord + 1);
                break;
        }
    }
}
