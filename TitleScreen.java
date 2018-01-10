import javax.swing.*;
import java.awt.*;

/**
 * Created by Connor on 04/02/2017.
 */
public class TitleScreen extends JPanel{
    
    private Image titleScreenImg;

    //constructor to set the correct dimensions of the component and load images
    public TitleScreen() {
        Dimension minSize = new Dimension(Constants.BOARD_WIDTH, Constants.TITLE_IMAGE_HEIGHT);
        setMinimumSize(minSize);
        loadImages();
    }

    //Override method to paint the image to the JPanel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(titleScreenImg, 1, 1, this);
    }

    //Method to load all required images
    public void loadImages() {

        ImageIcon titleScreenIcon = new ImageIcon("resources/titleScreen.png");
        titleScreenImg = titleScreenIcon.getImage();
    }
}
