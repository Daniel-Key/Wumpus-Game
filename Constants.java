import java.awt.*;

/**
 * Created by Connor on 10/02/2017.
 */
public class Constants {

    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 850;
    public static final int GAME_INFO_HEIGHT = 50;
    public static final int GAME_BOARD_HEIGHT =BOARD_HEIGHT - GAME_INFO_HEIGHT;
    public static final int MENU_HEIGHT = 100;
    public static final int TITLE_IMAGE_HEIGHT = BOARD_HEIGHT-MENU_HEIGHT;

    public static final int MIN_PIT_BAT_PERCENTAGE = 5;
    public static final int MAX_PIT_BAT_PERCENTAGE = 20;
    public static final int INIT_PIT_BAT = 10;
    public static final int MIN_BOARD_SIZE = 5;
    public static final int MAX_BOARD_SIZE = 20;
    public static final int INIT_BOARD_SIZE = 10;

    public static final Color SMELL_COLOR = new Color(115,160,0);
    public static final Color GLITTER_COLOR = new Color(214,195,72);
    public static final Color BREEZE_COLOR = new Color(127,255,255);

    public static final Font FONT = new Font("Arial", Font.BOLD, 14);

    public static final int WUMPUS_HEALTH = 1;
    public static final int TURNS_FOR_WUMPUS_MOVE = 4;

    public static final int PITS_PER_HOOK = 5;

    public static final int UNDISCOVERED_SAFETY = 0;
    public static final int BAT_SAFETY = 5;
    public static final int PIT_SAFETY = 100;
    public static final int EXIT_SAFETY = -100;
    public static final int SMELL_SAFETY = 10;
}
