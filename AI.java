import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created bx Connor on 11/02/2017.
 */
public class AI extends Adventurer {

    private int[][] dangerMap;
    private ArrayList<String> bestOptions;

    public AI(Room[][] board) {
        super(board);
        alive=true;
        dangerMap = new int[game.getBoardSize()][game.getBoardSize()];
        bestOptions = new ArrayList<String>();
        for (int[] ints: dangerMap) {
            for (int i: ints) {
                i = Constants.UNDISCOVERED_SAFETY;
            }
        }
    }

    public int[][] getDangerMap() {
        return dangerMap;
    }


    public void takeTurn(Room[][] board) {
        addDanger(board[xCoord][yCoord]);
        pickMove();
    }

    public void addDanger(Room room) {
        int roomDanger = 0;
        if (!room.isSmell() && !room.isBreeze() && !room.isGlitter()) {
            if (!game.boardSpace(xCoord + 1, yCoord).isDiscovered()) {
                dangerMap[game.boardSpace(xCoord + 1, yCoord).getXCoord()][yCoord] = 0;
            }
            if (!game.boardSpace(xCoord - 1, yCoord).isDiscovered()) {
                dangerMap[game.boardSpace(xCoord - 1, yCoord).getXCoord()][yCoord] = 0;
            }
            if (!game.boardSpace(xCoord, yCoord + 1).isDiscovered()) {
                dangerMap[xCoord][game.boardSpace(xCoord, yCoord + 1).getYCoord()] = 0;
            }
            if (!game.boardSpace(xCoord, yCoord - 1).isDiscovered()) {
                dangerMap[xCoord][game.boardSpace(xCoord, yCoord - 1).getYCoord()] = 0;
            }
        } else {
            if (room.isSmell()) {
                roomDanger += Constants.SMELL_SAFETY;
            }
            if (room.isBreeze()) {
                roomDanger += Constants.SMELL_SAFETY;
            }
            if (room.isGlitter()) {
                roomDanger -= Constants.SMELL_SAFETY;
            }

            if (!game.boardSpace(xCoord + 1, yCoord).isDiscovered()) {
                dangerMap[game.boardSpace(xCoord + 1, yCoord).getXCoord()][yCoord] += roomDanger;
            }
            if (!game.boardSpace(xCoord - 1, yCoord).isDiscovered()) {
                dangerMap[game.boardSpace(xCoord - 1, yCoord).getXCoord()][yCoord] += roomDanger;
            }
            if (!game.boardSpace(xCoord, yCoord + 1).isDiscovered()) {
                dangerMap[xCoord][game.boardSpace(xCoord, yCoord + 1).getYCoord()] += roomDanger;
            }
            if (!game.boardSpace(xCoord, yCoord - 1).isDiscovered()) {
                dangerMap[xCoord][game.boardSpace(xCoord, yCoord - 1).getYCoord()] += roomDanger;
            }
            if (dangerMap[xCoord][yCoord] == Constants.SMELL_SAFETY) {
                dangerMap[xCoord][yCoord] = 0;
            }
        }

        dangerMap[xCoord][yCoord] += 3;
    }

    public void pickMove() {
        int bestOptionValue = Constants.SMELL_SAFETY;
        int a = ThreadLocalRandom.current().nextInt(0,3);
        char bestOption;

        if (dangerMap[xCoord][game.boardSpace(xCoord, yCoord - 1).getYCoord()] == bestOptionValue){
            bestOptions.add("w");
        }
        else if (dangerMap[xCoord][game.boardSpace(xCoord, yCoord - 1).getYCoord()] < bestOptionValue) {
            bestOptionValue = dangerMap[xCoord][game.boardSpace(xCoord, yCoord - 1).getYCoord()];
            bestOptions.clear();
            bestOptions.add("w");
        }

        if (dangerMap[game.boardSpace(xCoord - 1, yCoord).getXCoord()][yCoord] == bestOptionValue) {
            bestOptions.add("a");
        }
        else if (dangerMap[game.boardSpace(xCoord - 1, yCoord).getXCoord()][yCoord] < bestOptionValue) {
            bestOptionValue = dangerMap[game.boardSpace(xCoord - 1, yCoord).getXCoord()][yCoord];
            bestOptions.clear();
            bestOptions.add("a");
        }

        if (dangerMap[xCoord][game.boardSpace(xCoord, yCoord + 1).getYCoord()] == bestOptionValue) {
            bestOptions.add("s");
        }
        else if (dangerMap[xCoord][game.boardSpace(xCoord, yCoord + 1).getYCoord()] < bestOptionValue) {
            bestOptionValue = dangerMap[xCoord][game.boardSpace(xCoord, yCoord + 1).getYCoord()];
            bestOptions.clear();
            bestOptions.add("s");
        }

        if (dangerMap[xCoord][game.boardSpace(xCoord, yCoord + 1).getYCoord()] == bestOptionValue) {
            bestOptions.add("d");
        }
        else if (dangerMap[game.boardSpace(xCoord + 1, yCoord).getXCoord()][yCoord] < bestOptionValue) {
            bestOptionValue = dangerMap[game.boardSpace(xCoord + 1, yCoord).getXCoord()][yCoord];
            bestOptions.clear();
            bestOptions.add("d");
        }


        bestOption = randomBestMove(bestOptions);
        move(true, bestOption);
        bestOptions.clear();
    }
}