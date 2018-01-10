import javax.swing.*;
import java.awt.*;

/**
 * Created by crdt on 25/01/17.
 */
public class Main{

    private static JFrame gui;

    public static void main(String[] args) {
        //Start the event dispatch thread and initialise the GUI
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                setUpGUI();
            }
        });
    }

    //Method to set the look and feel of the gui, initialise the JFrame and give it the required properties
    public static void setUpGUI() {

        try {
            //Get the preinstalled "Nimbus" look and feel
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (("Nimbus").equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Initialise a JFrame and make it visible
        gui = new JFrame();
        gui.setVisible(true);

        //Call a method to initialise the menu
        menu();

        //Make the window not resizable and resize the window to fit its components
        gui.setResizable(false);
        gui.pack();

        //Set the title for the window, make the program quit when the close button is pressed and centre the window
        gui.setTitle("Hunt the Wumpus");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setLocationRelativeTo(null);
    }

    //Method to start a game
    public static void newGame(int boardSize, int pitNumber, int batNumber, int gameMode) {

        //Empty the GUI
        gui.getContentPane().removeAll();

        //Make the game board and add it to a split pane with information displayed above the game
        Board board = new Board(boardSize, pitNumber, batNumber, gameMode);
        JSplitPane gameSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,board.getGameInfo(), board);

        //Fix the dimensions of the two components and add them to the GUI
        gameSplitPane.setEnabled(false);
        gui.add(gameSplitPane);

        //Bring the board into focus to detect key presses and display it
        gameSplitPane.getBottomComponent().requestFocusInWindow();
        repaint();
    }

    //Method to redisplay the GUI
    public static void repaint(){
        gui.getContentPane().revalidate();
        gui.getContentPane().repaint();
    }

    //Method to end the game and bring up a game over screen
    public static void gameOver(int gameEnding) {
        /*Int gameEnding has four settings: 0: Killed by Wumpus, 1: Killed by pit
        2: Found treasure, 3: Game quit using escape key*/

        //Empty the GUI and make the appropriate game over screen
        gui.getContentPane().removeAll();
        GameOver gameOver = new GameOver(gameEnding);

        //Add the game over screen to the GUI
        gui.add(gameOver);
        repaint();
    }

    //Method to show the game menu
    public static void menu() {

        //Empty the GUI
        gui.getContentPane().removeAll();

        //Make the menu which consists of an image above the menu options
        JSplitPane menuSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,new TitleScreen(), new Menu());

        //Fix the dimensions of the two components
        menuSplitPane.setEnabled(false);

        //Add the menu to the GUI
        gui.add(menuSplitPane);
        repaint();
    }
}