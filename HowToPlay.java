import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HowToPlay {

    private static JButton back;
    private static JFrame frame;

    // CONSTRUCTOR
    public HowToPlay(){
        frame = new JFrame("How to play");
        JPanel panel = new JPanel();
        frame.setSize(500, 500);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        panel.setLayout(null);

        // back button
        back = new JButton("Back");
        back.setBounds(320, 385, 100, 50);
        panel.add(back);
        buttonListeners();

        // WASD icon
        ImageIcon wasdImg = new ImageIcon("data/text/wasd.png");
        JLabel wasd = new JLabel("", wasdImg, JLabel.CENTER);
        wasd.setBounds(0, 125, 250, 150);
        panel.add(wasd);

        // arrows icon
        ImageIcon arrowsImg = new ImageIcon("data/text/arrows.png");
        JLabel arrows = new JLabel("", arrowsImg, JLabel.CENTER);
        arrows.setBounds(0, 260, 250, 150);
        panel.add(arrows);

        // header
        ImageIcon textImg = new ImageIcon("data//text/howToPlay.png");
        JLabel text = new JLabel("", textImg, JLabel.CENTER);
        text.setBounds(0, 0, 500, 100);
        panel.add(text);

        //background
        ImageIcon img = new ImageIcon("data/stadiums/grass.png");
        JLabel background = new JLabel("", img, JLabel.CENTER);
        background.setBounds(0, 0, 500, 500);
        panel.add(background);

        frame.setVisible(true);
    }

    public static void buttonListeners() {
        ActionListener backButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        };
        back.addActionListener(backButton);
    }
}

