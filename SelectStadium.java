import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**Allows the players to select a stadium by pressing a corresponding button
 * - these buttons will change the background image of the game*/
public class SelectStadium implements ActionListener {

    // button used to select a stadium
    JButton oldTraffordButton;
    JButton stamfordBridgeButton;
    JButton campNouButton;
    JButton santiagoBernabeuButton;

    // paths to each stadium image, that will be used to pass through as a parameter
    public static String oldTrafford = "data/stadiums/OldTrafford.png";
    public static String stamfordBridge = "data/stadiums/StamfordBridge.png";
    public static String CampNou = "data/stadiums/CampNou.png";
    public static String santiagoBernabeu = "data/stadiums/SantiagoBernabeu.png";

    private Game oldTraffordGame;
    private Game stamfordBridgeGame;
    private Game santiagoGame;
    private Game campNouGame;

    // CONSTRUCTOR
    public SelectStadium(){

        final JFrame frame = new JFrame("SelectStadium");
        JPanel panel = new JPanel();
        frame.setSize(500, 500);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        panel.setLayout(null);

        // creating and adding the buttons to panel
        Icon oT = new ImageIcon("data/buttonIcons/OldTrafford.png");
        oldTraffordButton = new JButton(oT);
        panel.add(oldTraffordButton);

        Icon sB = new ImageIcon("data/buttonIcons/StamfordBridge.png");
        stamfordBridgeButton = new JButton(sB);
        panel.add(stamfordBridgeButton);

        Icon cN = new ImageIcon("data/buttonIcons/CampNou.png");
        campNouButton = new JButton(cN);
        panel.add(campNouButton);

        Icon sBer = new ImageIcon("data/buttonIcons/SantiagoBernabeu.png");
        santiagoBernabeuButton = new JButton(sBer);
        panel.add(santiagoBernabeuButton);

        // set location of the buttons
        oldTraffordButton.setBounds(25,110,200,100);
        stamfordBridgeButton.setBounds(250,110,200,100);
        campNouButton.setBounds(25,250,200,100);
        santiagoBernabeuButton.setBounds(250,250,200,100);

        // SELECT STADIUM header
        ImageIcon textImg = new ImageIcon("data/text/selectStadium.png");
        JLabel text = new JLabel("",textImg,JLabel.CENTER);
        text.setBounds(0,0,500,100);
        panel.add(text);

        // background
        ImageIcon img = new ImageIcon("data/stadiums/grass.png");
        JLabel background = new JLabel("",img,JLabel.CENTER);
        background.setBounds(0,0,500,500);
        panel.add(background);

        frame.setVisible(true);

        buttonListeners();
    }

    // events that occur when the buttons are pressed
    public void buttonListeners() {
        ActionListener oldTButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oldTraffordGame = new Game(oldTrafford); // starts a new game with Old Trafford as the background
            }
        };

        ActionListener stamBridgeButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stamfordBridgeGame = new Game(stamfordBridge);
            }
        };

        ActionListener campButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campNouGame = new Game(CampNou);
            }
        };

        ActionListener santiagoBernButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                santiagoGame = new Game(santiagoBernabeu);
            }
        };

        // adds appropriate action listeners to the buttons
        oldTraffordButton.addActionListener(oldTButton);
        stamfordBridgeButton.addActionListener(stamBridgeButton);
        campNouButton.addActionListener(campButton);
        santiagoBernabeuButton.addActionListener(santiagoBernButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
