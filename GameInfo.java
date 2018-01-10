import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import static javax.swing.SwingConstants.CENTER;

/**
 * Created by Connor on 12/02/2017.
 */
public class GameInfo extends JPanel {

    //Initialising the button, labels and variables required
    private JButton changeMode = new JButton("Fire Mode");
    private JLabel inventory = new JLabel();
    private JLabel feedback = new JLabel();
    private boolean mode;

    public GameInfo(Board board) {

        //Setting the background colour, preferred size and layout
        Dimension minSize = new Dimension(Constants.BOARD_WIDTH, Constants.GAME_INFO_HEIGHT);
        setBackground(Color.lightGray);
        setMinimumSize(minSize);
        setLayout(new GridBagLayout());

        //Adding a listener to the button
        changeMode.addActionListener(board);

        //Setting the default mode to move mode
        mode = true;

        //Aligning the text in the labels
        inventory.setHorizontalAlignment(CENTER);
        inventory.setVerticalAlignment(CENTER);
        feedback.setHorizontalAlignment(CENTER);
        feedback.setVerticalAlignment(CENTER);

        //Setting the components font
        changeMode.setFont(Constants.FONT);
        inventory.setFont(Constants.FONT);
        feedback.setFont(Constants.FONT);

        //Calling a method to add the components
        addComponents();
    }

    //Getter to return the currrent mode
    public boolean isMode() {
        return mode;
    }

    //Method to display the player's inventory
    public void setInventory(Adventurer adventurer) {

        //Checking if the player has the treasure and converting the answer to a String
        String treasure;
        if (adventurer.hasTreasure) {
            treasure = "Yes";
        }
        else {
            treasure = "No";
        }

        //Setting the labels text to the player's inventory
        inventory.setText("<html>Arrows: " + adventurer.getArrowNo() +
                "<br>Grappling Hooks: " + adventurer.getGrapplingHookNo() +
                "<br>Treasure: " + treasure);
    }

    //Method to change the current mode
    public void changeMode() {

        //Check if the game is in one mode and switch it and change the button's text
        if (changeMode.getText().equals("Fire Mode")) {
            changeMode.setText("Move Mode");
            mode = false;
        }
        else {
            changeMode.setText("Fire Mode");
            mode = true;
        }
    }

    //Method to show information about the player's current location
    public void showFeedback(ArrayList<String> feedback) {

        /*Checking if there is inputted feedback and displaying it if there is or else
        show a String of spaces to prevent the label moving as much*/
        if (feedback.size() > 0) {
            this.feedback.setText("<html>");
            for (String s: feedback) {
                this.feedback.setText(this.feedback.getText() + "<br>" + s);
            }
        }
        else {
            this.feedback.setText("                                      ");
        }
    }

    //Method to add components to the panel
    public void addComponents() {

        //Initialising a new set of constraints that fills horizontally and provides the correct padding between components
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,20,0,20);

        //Setting the weight for the components
        c.weightx = 0.5;
        c.weighty = 0.5;

        //Placing the components
        c.gridx = 0;
        add(changeMode, c);
        c.gridx = 1;
        add(inventory, c);
        c.gridx = 2;
        add(feedback, c);
    }
}
