/**
 * Created by Connor on 12/02/2017.
 */
public class Exit extends GamePiece {

    private boolean open;

    public Exit(Room[][] board) {
        super(board);
        open = false;
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = true;
        game.getBoard()[xCoord][yCoord].setDiscovered(true);
    }
}
