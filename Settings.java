import city.cs.engine.SoundClip;
import city.cs.engine.World;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Settings window
 * restart button - restarts the game
 * out button - ends the game (system exit)
 * exit button - applies the settings and closes settings window
 * */
public class Settings {

    private JFrame frame;

    private GameView view;
    private SoundClip clip;

    private JButton restart;
    private JButton out;
    private JButton exit;
    private JButton abilities;

    private World world;
    private JFrame gameFrame;

    private Player1 player1;
    private Player2 player2;

    /**
     * @param c - (clip) takes in the current background sound
     */
    public Settings(GameView v, SoundClip c, World w, JFrame f, Player1 p1, Player2 p2) {
        this.view = v;
        this.clip = c;
        this.world = w;
        this.gameFrame = f;
        this.player1 = p1;
        this.player2 = p2;

        frame = new JFrame("Settings");
        JPanel panel = new JPanel();

        frame.setSize(400,400);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JSlider slider = new JSlider(0,100,50);
        slider.setPreferredSize(new Dimension(200,100));
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(10);
        slider.setMajorTickSpacing(50);
        slider.setFont(new Font("MV Boli",Font.PLAIN,9));
        slider.setOrientation(SwingConstants.HORIZONTAL);

        // allows to adjust the volume of the background noise
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (slider.getValue() <= 5){
                    clip.stop();
                } else if (slider.getValue() <= 25) {
                    clip.play();
                    clip.setVolume(0.3);
                } else if (slider.getValue() <= 50){
                    clip.play();
                    clip.setVolume(0.6);
                } else if (slider.getValue() <= 50){
                    clip.play();
                    clip.setVolume(1);
                } else if (slider.getValue() <= 75){
                    clip.play();
                    clip.setVolume(1.4);
                } else if (slider.getValue() <= 100){
                    clip.play();
                    clip.setVolume(1.9);
                }
            }
        });

        // settings buttons
        exit = new JButton("APPLY AND EXIT");
        exit.setBounds(210,320,150,20);
        frame.add(exit);

        JLabel soundL = new JLabel("Sound");
        soundL.setBounds(30,30,50,50);
        frame.add(soundL);

        // abilities window
        abilities = new JButton("Abilities");
        abilities.setBounds(120,110,150,30);
        frame.add(abilities);

        // exit game
        out = new JButton("Exit game");
        out.setBounds(120,170,150,30);
        frame.add(out);

        // restart
        restart = new JButton("RESTART");
        restart.setBounds(120,230,150,30);
        frame.add(restart);

        buttonListeners();

        panel.add(slider);

        frame.add(panel);
        frame.setVisible(true);
    }

    public void buttonListeners() {

        ActionListener exitButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        };

        ActionListener restartButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                gameFrame.dispose();
                clip.stop();
                world.stop();
                new StartUp();
            }
        };

        ActionListener abilitiesButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Abilities();
            }
        };

        ActionListener outButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        };

        restart.addActionListener(restartButton);
        exit.addActionListener(exitButton);
        out.addActionListener(outButton);
        abilities.addActionListener(abilitiesButton);
    }

}

