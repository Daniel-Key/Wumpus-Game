import java.util.ArrayList;

/**
 * Created by dgk2 on 25/01/17.
 */

public class Game {

    private int boardSize;
    private Room[][] board;
    private int batsNo;
    private int pitsNo;
    private int grapplingHooksNo;
    private Player player;
    private AI ai;
    private Wumpus wumpus;
    private Treasure treasure;
    private Exit exit;
    private SuperBat[] superBats;
    private Pit[] pits;
    private GrapplingHook[] grapplingHooks;
    private  int gameMode;
    private ArrayList<String> feedback = new ArrayList<String>();
    private int turnsCounter;

    public Game(int boardSize, int pitNumber, int batNumber, int gameMode) {
        this.boardSize = boardSize;
        GamePiece.game = this;
        pitsNo = pitNumber;
        batsNo = batNumber;
        this.gameMode = gameMode;
        grapplingHooksNo = pitsNo/Constants.PITS_PER_HOOK;
        board = new Room[boardSize][boardSize];
        initialSetUp();
        particles();
    }

    public int getBoardSize() {
        return boardSize;
    }

    public Room[][] getBoard() {
        return board;
    }

    public Adventurer getPlayer() {
        Adventurer a;
        switch (gameMode) {
            case 0:
                a = player;
                break;
            case 1:
                a = ai;
                break;
            default:
                a = player;
                break;
        }
        return a;
    }

    public int getBatsNo() {
        return batsNo;
    }

    public int getPitsNo() {
        return pitsNo;
    }

    public Wumpus getWumpus() {
        return wumpus;
    }

    public ArrayList<String> getFeedback() {
        return feedback;
    }

    public Exit getExit() {
        return exit;
    }

    public void initialSetUp() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = new Room(i, j, this);
            }
        }
        switch (gameMode) {
            case 0:
                player = new Player(board);
                break;
            case 1:
                ai = new AI(board);
                break;
            default:
                player = new Player(board);
                break;
        }
        wumpus = new Wumpus(board);
        treasure = new Treasure(board);
        exit = new Exit(board);
        superBats = new SuperBat[batsNo];
        for (int i = 0; i < batsNo; i++) {
            superBats[i] = new SuperBat(board);
        }
        pits = new Pit[pitsNo];
        for (int i = 0; i < pitsNo; i++) {
            pits[i] = new Pit(board);
        }
        grapplingHooks = new GrapplingHook[grapplingHooksNo];
        for (int i = 0; i < grapplingHooksNo; i++) {
            grapplingHooks[i] = new GrapplingHook(board);
        }
    }

    public Room boardSpace(int i, int j) {
        i = (i + boardSize) % boardSize;
        j = (j + boardSize) % boardSize;
        return board[i][j];
    }

    public void particles() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j].getGamePieces().contains(wumpus)) {
                    boardSpace(i - 1, j).setSmell(true);
                    boardSpace(i + 1, j).setSmell(true);
                    boardSpace(i, j - 1).setSmell(true);
                    boardSpace(i, j + 1).setSmell(true);
                }
                if (board[i][j].getGamePieces().contains(treasure)) {
                    boardSpace(i - 1, j).setGlitter(true);
                    boardSpace(i + 1, j).setGlitter(true);
                    boardSpace(i, j - 1).setGlitter(true);
                    boardSpace(i, j + 1).setGlitter(true);
                }
                if (findPits(i,j)) {
                    boardSpace(i - 1, j).setBreeze(true);
                    boardSpace(i + 1, j).setBreeze(true);
                    boardSpace(i, j - 1).setBreeze(true);
                    boardSpace(i, j + 1).setBreeze(true);
                }
            }
        }
    }

    private boolean findPits(int i, int j) {
        for (GamePiece g : board[i][j].getGamePieces()) {
            if (g instanceof Pit) {
                return true;
            }
        }
        return false;
    }

    public void checkHazard(Adventurer adventurer) {
        ArrayList<GamePiece> gamePieceRemove = new ArrayList<GamePiece>();
        for (GamePiece g: board[adventurer.getxCoord()][adventurer.getyCoord()].getGamePieces()) {
           if (g instanceof Wumpus && wumpus.isAlive()) {
               Main.gameOver(0);
           }
           else if (g instanceof Wumpus) {
               feedback.add("You see a dead wumpus");
           }
           else if (g instanceof Pit) {
               if (adventurer.grapplingHookNo == 0) {
                   Main.gameOver(1);
               }
               else {
                   feedback.add("You hook onto the edge of a pit");
                   adventurer.grapplingHookNo--;
                   if (adventurer instanceof AI) {
                       ((AI) adventurer).getDangerMap()[adventurer.getxCoord()][adventurer.getyCoord()] = Constants.PIT_SAFETY;
                   }
               }
           }
           else if (g instanceof SuperBat) {
               feedback.add("You are dropped in a different cave by a superbat");
               if (adventurer instanceof AI) {
                   ((AI) adventurer).getDangerMap()[adventurer.getxCoord()][adventurer.getyCoord()] = Constants.BAT_SAFETY;
               }
               adventurer.randomTeleport();
               if (adventurer instanceof AI) {
                   ((AI) adventurer).addDanger(board[adventurer.getxCoord()][adventurer.getyCoord()]);
               }
           }
           else if (g instanceof Treasure) {
               adventurer.setHasTreasure(true);
               exit.open();
               feedback.add("You pick up the treasure and find a map to the exit");
               if (adventurer instanceof AI) {
                   ((AI) adventurer).getDangerMap()[exit.getxCoord()][exit.getyCoord()] = Constants.EXIT_SAFETY;
               }
           }
           else if (g instanceof Exit && exit.isOpen()) {
               Main.gameOver(2);
           }
           else if (g instanceof Arrow) {
               adventurer.arrowNo++;
               gamePieceRemove.add(g);
               feedback.add("You pick up an arrow! Arrows remaining: " + adventurer.arrowNo);
           }
           else if (g instanceof GrapplingHook) {
               adventurer.grapplingHookNo++;
               gamePieceRemove.add(g);
               feedback.add("You pick up a grappling hook! Hooks remaining: " + adventurer.grapplingHookNo);
           }
        }
        board[adventurer.getxCoord()][adventurer.getyCoord()].getGamePieces().removeAll(gamePieceRemove);
        feedback.addAll(board[adventurer.getxCoord()][adventurer.getyCoord()].feedback());
    }

    public ArrayList<String> nextTurn(boolean mode, char c) {
        feedback = new ArrayList<String>();
        turnsCounter += 1;
        switch (gameMode) {
            case 0:
                player.move(mode, c);
                if (turnsCounter%Constants.TURNS_FOR_WUMPUS_MOVE == 0 && wumpus.isAlive()) {
                    wumpus.move();
                }
                checkHazard(player);
                break;
            case 1:
                ai.takeTurn(board);
                checkHazard(ai);
                break;
            default:
                player = new Player(board);
                break;
        }
        particles();
        return feedback;
    }
}