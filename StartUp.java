import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Start menu GUI
 * - Play button
 * - How to play - showing user controls */
public class StartUp implements ActionListener {

    JButton play;
    JButton howToPlay;

    private HowToPlay howToPlayWindow;
    private SelectStadium selectStadium;

    private Clip clip;

    // CONSTRUCTOR
    public StartUp() {

        // MUSIC
        try {
            music();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        final JFrame frame = new JFrame("Soccer Head");
        JPanel panel = new JPanel();
        frame.setSize(500, 500);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        panel.setLayout(null);

        // PLAY button
        play = new JButton("PLAY");
        play.setBounds(0, 150, 200, 100);

        // HOW TO PLAY button
        howToPlay = new JButton("How to play");
        howToPlay.setBounds(0, 250, 200, 100);

        panel.add(play);
        panel.add(howToPlay);

        // ball gif
        ImageIcon ballImg = new ImageIcon("data/balls/bouncingBall.gif");
        JLabel ball = new JLabel("",ballImg,JLabel.CENTER);
        ball.setBounds(0,0,700,575);
        panel.add(ball);

        // header
        ImageIcon textImg = new ImageIcon("data/text/header.png");
        JLabel text = new JLabel("",textImg,JLabel.CENTER);
        text.setBounds(0,0,500,150);
        panel.add(text);

        //background
        ImageIcon img = new ImageIcon("data/stadiums/grass.png");
        JLabel background = new JLabel("",img,JLabel.CENTER);
        background.setBounds(0,0,500,500);
        panel.add(background);

        frame.setVisible(true);

        // allow button usability
        buttonListeners();
    }

    public void buttonListeners() {
        ActionListener playButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if player selects PLAY, they then get to select a stadium
                selectStadium = new SelectStadium();
                clip.stop();
            }
        };

        ActionListener howToPlayButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // opens new window showing users how to play
                howToPlayWindow = new HowToPlay();
            }
        };

        play.addActionListener(playButton);
        howToPlay.addActionListener(howToPlayButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File music = new File("data/sounds/music.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(music);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    }

}
