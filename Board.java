import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

/**
 * Created by crdt on 25/01/17.
 */
public class Board extends JPanel implements KeyListener, ActionListener{

    //The required images
    private Image playerImg;
    private Image smell2Img;
    private Image breeze2Img;
    private Image glitter2Img;
    private Image smell3Img;
    private Image breeze3Img;
    private Image glitter3Img;
    private Image superBatImg;
    private Image wumpusAliveImg;
    private Image wumpusDeadImg;
    private Image treasureImg;
    private Image pitImg;
    private Image exitImg;
    private Image aiImg;

    //Variables required
    private int squareWidth;
    private int arcLength;
    private Game game;
    private GameInfo gameInfo;
    private ArrayList<String> feedback;
    private boolean mode;

    //Getter for the gameInfo
    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public Board(int boardSize, int pitNumber, int batNumber, int gameMode) {

        //Setting the background colour and preferred size of the board and allowing it to be in focus and listen for key presses
        setBackground(Color.black);
        setPreferredSize(new Dimension(Constants.BOARD_WIDTH,Constants.GAME_BOARD_HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        //Setting all variables required and initialising the game information panel
        squareWidth = Constants.BOARD_WIDTH/boardSize;
        arcLength = squareWidth/3;
        mode = true;
        feedback = new ArrayList<String>();
        gameInfo = new GameInfo(this);

        //Calling method to load images
        loadImages();

        //Initialising a new game and getting any information about the players default location
        game = new Game(boardSize, pitNumber, batNumber, gameMode);
        game.checkHazard(game.getPlayer());
        gameInfo.setInventory(game.getPlayer());
        gameInfo.showFeedback(game.getFeedback());
    }

    //Method that reacts to a key being released
    @Override
    public void keyReleased(KeyEvent e) {

        //Check to see if the player is in move mode or fire mode
        mode = gameInfo.isMode();

        //Getting which key was released
        char c = e.getKeyChar();

        //Checking if a movement key, the escape key or another key is pressed and giving the appropriate response
        if (c == 'w' || c == 'a' || c == 's' || c == 'd') {
            //Take the next turn and record any feedback
            feedback = game.nextTurn(mode, c);
            Main.repaint();
        }
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //Exit the game with the value for quitting the game
            Main.gameOver(3);
        }
        else {
            //Tell the user to press a valid key
            feedback = new ArrayList<String>();
            feedback.add("Only w, a, s, d and Esc are valid keys");
            Main.repaint();
        }
        //Display the user's inventory and any information about their location
        gameInfo.setInventory(game.getPlayer());
        gameInfo.showFeedback(feedback);

    }

    //Required methods for keyListener interface
    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    //If the mode change button is pressed, keep the board in focus and change the mode
    @Override
    public void actionPerformed(ActionEvent e) {
        this.requestFocusInWindow();
        gameInfo.changeMode();
    }

    //Method that displays any images and graphics
    @Override
    protected void paintComponent(Graphics g) {

        //Calling the super
        super.paintComponent(g);

        //Setting the pen colour to black and drawing grid lines
        g.setColor(Color.black);
        for (int i = 0; i <= Constants.BOARD_WIDTH; i += squareWidth) {
            g.drawLine(i, 0, i, Constants.GAME_BOARD_HEIGHT);
        }
        for (int i = 0; i <= Constants.BOARD_WIDTH; i += squareWidth) {
            g.drawLine(0, i, Constants.BOARD_WIDTH, i);
        }

        //Looping through each room in the board to display it
        for (Room[] rooms: game.getBoard()) {
            for (Room room: rooms) {

                //Getting the room's x and y coordinates
                int x = room.getXCoord();
                int y = room.getYCoord();

                //Filling the square black if it isn't discovered
                if (!game.getBoard()[x][y].isDiscovered()) {
                    fillSquare(Color.black, g, x, y);
                }
                //If the room has a particle, call a method to display it
                else if (room.isSmell() || room.isGlitter() || room.isBreeze()) {
                    showParticles(room, g, x, y);
                }
                //Fill empty rooms gray
                else {
                    fillSquare(Color.gray, g, x, y);
                }

                //Looping though gamepieces in the room if it is discovered
                if (game.getBoard()[x][y].isDiscovered()) {
                    for (GamePiece gamePiece: room.getGamePieces()) {

                        //Displaying the gamepieces in the room
                        if (gamePiece instanceof Player) {
                            drawImage(g, playerImg, x, y);
                        } else if (gamePiece instanceof AI) {
                            drawImage(g, aiImg, x, y);
                        } else if (gamePiece instanceof SuperBat) {
                            drawImage(g, superBatImg, x, y);
                        } else if (gamePiece instanceof Wumpus && game.getWumpus().isAlive()) {
                            drawImage(g, wumpusAliveImg, x, y);
                        } else if (gamePiece instanceof Wumpus && !game.getWumpus().isAlive()) {
                            drawImage(g, wumpusDeadImg, x, y);
                        } else if (gamePiece instanceof Pit) {
                            drawImage(g, pitImg, x, y);
                        } else if (gamePiece instanceof Treasure) {
                            drawImage(g, treasureImg, x, y);
                        } else if (gamePiece instanceof Exit && game.getExit().isOpen()) {
                            drawImage(g, exitImg, x, y);
                        }
                    }
                }
            }
        }
    }

    //Method to show any particles for a room
    private void showParticles(Room room, Graphics g, int x, int y) {

        //Checking if the room has a a combination of particles and displaying the correct colours and images
        if (room.isSmell()) {
            fillSquare(Constants.SMELL_COLOR, g, x, y);
            if (room.isBreeze()) {
                drawImage(g, breeze2Img, x, y);
                if (room.isGlitter()) {
                    drawImage(g, glitter3Img, x, y);
                }
            } else if (room.isGlitter()) {
                drawImage(g, glitter2Img, x, y);
            }
        } else if (room.isBreeze()) {
            fillSquare(Constants.BREEZE_COLOR, g, x, y);
            if (room.isGlitter()) {
                drawImage(g, glitter2Img, x, y);
            }
        } else {
            fillSquare(Constants.GLITTER_COLOR, g, x, y);
        }
    }

    //Method to display an image correctly within a square
    private void drawImage(Graphics g, Image img, int x, int y) {

        //Making a round rectangle the shape of the square to prevent images going over the edges
        Shape s = new RoundRectangle2D.Float(x * squareWidth + 1, y * squareWidth + 1, squareWidth - 1, squareWidth - 1, arcLength, arcLength);

        //Setting this round rectangle to the clip and drawing the image in the correct location before emptying the clip
        g.setClip(s);
        g.drawImage(img, x * squareWidth + 1, y * squareWidth + 1, squareWidth - 1, squareWidth - 1, this);
        g.setClip(null);
    }

    //Method to fill in a square a colour
    private void fillSquare(Color color, Graphics g, int x, int y) {

        //Set the colour and fill in the square with the appropriately sized round rectangle
        g.setColor(color);
        g.fillRoundRect(x * squareWidth + 1, y * squareWidth + 1, squareWidth - 1, squareWidth - 1, arcLength, arcLength);
    }

    //Method to load all required images
    private void loadImages() {

        ImageIcon playerIcon = new ImageIcon("resources/player.png");
        playerImg = playerIcon.getImage();

        ImageIcon smell2Icon = new ImageIcon("resources/smell2.png");
        smell2Img = smell2Icon.getImage();

        ImageIcon breeze2Icon = new ImageIcon("resources/breeze2.png");
        breeze2Img = breeze2Icon.getImage();

        ImageIcon glitter2Icon = new ImageIcon("resources/glitter2.png");
        glitter2Img = glitter2Icon.getImage();

        ImageIcon smell3Icon = new ImageIcon("resources/smell3.png");
        smell3Img = smell3Icon.getImage();

        ImageIcon breeze3Icon = new ImageIcon("resources/breeze3.png");
        breeze3Img = breeze3Icon.getImage();

        ImageIcon glitter3Icon = new ImageIcon("resources/glitter3.png");
        glitter3Img = glitter3Icon.getImage();

        ImageIcon superBatIcon = new ImageIcon("resources/bat.png");
        superBatImg = superBatIcon.getImage();

        ImageIcon wumpusAliveIcon = new ImageIcon("resources/wumpusAlive.png");
        wumpusAliveImg = wumpusAliveIcon.getImage();

        ImageIcon wumpusDeadIcon = new ImageIcon("resources/wumpusDead.png");
        wumpusDeadImg = wumpusDeadIcon.getImage();

        ImageIcon treasureIcon = new ImageIcon("resources/treasure.png");
        treasureImg = treasureIcon.getImage();

        ImageIcon pitIcon = new ImageIcon("resources/pit.png");
        pitImg = pitIcon.getImage();

        ImageIcon exitIcon = new ImageIcon("resources/exit.png");
        exitImg = exitIcon.getImage();

        ImageIcon aiIcon = new ImageIcon("resources/ai.png");
        aiImg = aiIcon.getImage();
    }
}