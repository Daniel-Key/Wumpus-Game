import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Created by crdt on 30/01/17.
 */
public class GameOver extends JPanel implements ActionListener{

    //Initialising the button, images and variable required
    private JButton menu = new JButton("Back to Menu");
    private Image gameOverWumpusImg;
    private Image gameOverPitImg;
    private Image gameOverTreasureImg;
    private Image gameOverQuitImg;
    private int gameEnding;

    public GameOver(int gameEnding)  {

        //Setting the background colour and preferred size of the board
        setBackground(Color.lightGray);
        setPreferredSize(new Dimension(Constants.BOARD_WIDTH,Constants.BOARD_HEIGHT));

        //Calling method to load images
        loadImages();

        //Setting how the game ended
        this.gameEnding = gameEnding;

        //Setting the font for the button, adding a listener and then adding it ot the GUI
        menu.setFont(Constants.FONT);
        menu.addActionListener(this);
        add(menu);
    }

    //Method to detect the button being pressed and return to the menu
    @Override
    public void actionPerformed(ActionEvent e) {
        Main.menu();
    }

    //Method that displays any images and graphics
    @Override
    protected void paintComponent(Graphics g) {

        //Calls the super
        super.paintComponent(g);

        //Check how the game ended and show the appropriate game over image
        switch (gameEnding) {
            case 0:
                g.drawImage(gameOverWumpusImg, 1, 1, this);
                break;
            case 1:
                g.drawImage(gameOverPitImg, 1, 1, this);
                break;
            case 2:
                g.drawImage(gameOverTreasureImg, 1, 1, this);
                break;
            case 3:
                g.drawImage(gameOverQuitImg, 1, 1, this);
                break;
        }
    }

    //Method to load all required images
    private void loadImages() {

        ImageIcon gameOverTreasureIcon = new ImageIcon("resources/gameOverTreasure.png");
        gameOverTreasureImg = gameOverTreasureIcon.getImage();

        ImageIcon gameOverPitIcon = new ImageIcon("resources/gameOverPit.png");
        gameOverPitImg = gameOverPitIcon.getImage();

        ImageIcon gameOverWumpusIcon = new ImageIcon("resources/gameOverWumpus.png");
        gameOverWumpusImg = gameOverWumpusIcon.getImage();

        ImageIcon gameOverQuitIcon = new ImageIcon("resources/gameOverQuit.png");
        gameOverQuitImg = gameOverQuitIcon.getImage();
    }
}
