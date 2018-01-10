import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Created by crdt on 30/01/17.
 */
public class Menu extends JPanel implements ActionListener, ChangeListener, SwingConstants{

    //Initialising the button, sliders, labels and radio buttons required
    private JButton startButton = new JButton("Start");
    private JSlider batSlider = newSlider(Constants.MIN_PIT_BAT_PERCENTAGE, Constants.MAX_PIT_BAT_PERCENTAGE, Constants.INIT_PIT_BAT);
    private JSlider pitSlider = newSlider(Constants.MIN_PIT_BAT_PERCENTAGE, Constants.MAX_PIT_BAT_PERCENTAGE, Constants.INIT_PIT_BAT);
    private JSlider boardWidthSlider = newSlider(Constants.MIN_BOARD_SIZE, Constants.MAX_BOARD_SIZE, Constants.INIT_BOARD_SIZE);
    private JLabel batLabel = new JLabel("Percentage of rooms with bats", CENTER);
    private JLabel pitLabel = new JLabel("Percentage of rooms with pits", CENTER);
    private JLabel boardWidthLabel = new JLabel("Width of the board", CENTER);
    private JRadioButton playerButton = new JRadioButton("Player", true);
    private JRadioButton aIButton = new JRadioButton("AI", false);
    private ButtonGroup gameModeButtons = new ButtonGroup();

    //Variables for the sliders and radio buttons
    private int pitPercentage;
    private int batPercentage;
    private int boardSize;
    private int gameMode;

    public Menu() {
        //Setting the background colour, preferred size and layout for the menu
        setBackground(Color.white);
        setPreferredSize(new Dimension(Constants.BOARD_WIDTH,Constants.BOARD_HEIGHT));
        setLayout(new GridBagLayout());

        //Adding listeners to the button, sliders and radio buttons
        startButton.addActionListener(this);
        batSlider.addChangeListener(this);
        pitSlider.addChangeListener(this);
        boardWidthSlider.addChangeListener(this);
        playerButton.addActionListener(this);
        aIButton.addActionListener(this);

        //Adding the radio buttons to a button group
        gameModeButtons.add(playerButton);
        gameModeButtons.add(aIButton);

        //Setting the fonts for the button, labels and radio buttons
        batLabel.setFont(Constants.FONT);
        pitLabel.setFont(Constants.FONT);
        boardWidthLabel.setFont(Constants.FONT);
        startButton.setFont(Constants.FONT);
        playerButton.setFont(Constants.FONT);
        aIButton.setFont(Constants.FONT);

        //Setting variables to their default values
        gameMode = 0;
        boardSize = Constants.INIT_BOARD_SIZE;
        batPercentage = Constants.INIT_PIT_BAT;
        pitPercentage = Constants.INIT_PIT_BAT;

        //Calling a method to add the components to the menu
        addComponents();
    }

    //Method to listen for events in the button and radio buttons
    @Override
    public void actionPerformed(ActionEvent e) {

        //Finding which component caused the event and giving the required response
        Object s = e.getSource();
        if (s.equals(startButton)) {
            //Generating a new game and passing the variables required to it
            int roomNumber = boardSize*boardSize;
            int pitNumber = pitPercentage*roomNumber/100;
            int batNumber = batPercentage*roomNumber/100;
            Main.newGame(boardSize, pitNumber, batNumber, gameMode);
        }
        else if (s.equals(playerButton)) {
            //Set the game mode for a human player
            gameMode = 0;
        }
        else if (s.equals(aIButton)) {
            //Set the game mode for an AI player
            gameMode = 1;
        }
    }

    //Method to listen for the state of sliders changing
    @Override
    public void stateChanged(ChangeEvent e) {

        //Finding which slider was moved and casting the object to a JSlider
        Object s = e.getSource();
        JSlider source = (JSlider)s;

        //Changing the appropriate variable to the value of the slider that was moved
        if (s.equals(pitSlider)) {
            if (!source.getValueIsAdjusting()) {
                pitPercentage = source.getValue();
            }
        }
        else if (s.equals(batSlider)) {
            if (!source.getValueIsAdjusting()) {
                batPercentage = source.getValue();
            }
        }
        else {
            if (!source.getValueIsAdjusting()) {
                boardSize = source.getValue();
            }
        }
    }

    //Method to initialise a slider with the settings provided and show the correct labels and ticks
    private JSlider newSlider(int minValue, int maxValue, int initValue) {
        JSlider jSlider = new JSlider(minValue, maxValue, initValue);
        jSlider.setMajorTickSpacing(5);
        jSlider.setMinorTickSpacing(1);
        jSlider.setPaintTicks(true);
        jSlider.setPaintLabels(true);
        return jSlider;
    }

    //Method to add the components to the menu in the correct layout
    private void addComponents() {

        //Initialising a new set of constraints that fills horizontally and provides the correct padding between components
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,10,0,10);

        //Setting the weight for the first row of components
        c.weightx = 1;
        c.weighty = 1;

        //Placing row 1
        c.gridx = 0;
        c.gridy = 0;
        add(startButton, c);
        c.gridx = 1;
        add(playerButton, c);
        c.gridx = 2;
        add(aIButton, c);

        //Setting the weight for the rest of the components
        c.weighty = 0.1;

        //Placing row 2
        c.gridy = 1;
        c.gridx = 0;
        add(boardWidthLabel, c);
        c.gridx = 1;
        add(batLabel, c);
        c.gridx = 2;
        add(pitLabel, c);

        //Placing row 3
        c.gridy = 2;
        c.gridx = 0;
        add(boardWidthSlider, c);
        c.gridx = 1;
        add(batSlider, c);
        c.gridx = 2;
        add(pitSlider, c);

    }
}