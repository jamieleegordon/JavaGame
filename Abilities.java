import javax.swing.*;
import java.awt.*;

/** GUI window showing all possible abilities and what they are */
public class Abilities {

    public Abilities() {

        JFrame frame = new JFrame("Abilities");
        JPanel panel = new JPanel();

        frame.setSize(600,400);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        panel.setLayout(null);

        // Speed ability icon
        ImageIcon speedImg = new ImageIcon("data/abilities/speed.png");
        JLabel speed = new JLabel("",speedImg,JLabel.CENTER);
        speed.setBounds(0,0,100,100);
        panel.add(speed);

        // Speed ability title
        JLabel speedTitle = new JLabel("Speed boost");
        speedTitle.setFont(new Font("MV Boli", Font.BOLD,25));
        speedTitle.setBounds(100,25,150,50);
        speedTitle.setForeground(Color.yellow);
        panel.add(speedTitle);

        // Jump ability icon
        ImageIcon jumpImg = new ImageIcon("data/abilities/jump.png");
        JLabel jump = new JLabel("",jumpImg,JLabel.CENTER);
        jump.setBounds(0,0,100,280);
        panel.add(jump);

        // Jump ability title
        JLabel jumpTitle = new JLabel("Jump boost");
        jumpTitle.setFont(new Font("MV Boli", Font.BOLD,25));
        jumpTitle.setBounds(100,125,150,50);
        jumpTitle.setForeground(Color.red);
        panel.add(jumpTitle);

        // Goal enlargement ability icon
        ImageIcon goalImg = new ImageIcon("data/abilities/goal.png");
        JLabel goal = new JLabel("",goalImg,JLabel.CENTER);
        goal.setBounds(0,0,100,460);
        panel.add(goal);

        // Goal enlargement ability title
        JLabel goalTitle = new JLabel("Big net");
        goalTitle.setFont(new Font("MV Boli", Font.BOLD,25));
        goalTitle.setBounds(100,200,150,50);
        goalTitle.setForeground(Color.BLACK);
        panel.add(goalTitle);

        // Basketball ability icon
        ImageIcon basketBallImg = new ImageIcon("data/abilities/basketball.png");
        JLabel basketBall = new JLabel("",basketBallImg,JLabel.CENTER);
        basketBall.setBounds(0,0,100,640);
        panel.add(basketBall);

        // Basketball ability title
        JLabel basketballTitle = new JLabel("Basketball");
        basketballTitle.setFont(new Font("MV Boli", Font.BOLD,25));
        basketballTitle.setBounds(100,275,150,50);
        basketballTitle.setForeground(Color.orange);
        panel.add(basketballTitle);

        // Freeze ability icon
        ImageIcon iceImg = new ImageIcon("data/abilities/ice.png");
        JLabel ice = new JLabel("",iceImg,JLabel.CENTER);
        ice.setBounds(0,0,700,100);
        panel.add(ice);

        // Freeze ability title
        JLabel iceTitle = new JLabel("Freeze");
        iceTitle.setFont(new Font("MV Boli", Font.BOLD,25));
        iceTitle.setBounds(425,25,150,50);
        iceTitle.setForeground(Color.CYAN);
        panel.add(iceTitle);

        // Portal ability icon
        ImageIcon portalImg = new ImageIcon("data/abilities/portal.png");
        JLabel portal = new JLabel("",portalImg,JLabel.CENTER);
        portal.setBounds(0,0,700,350);
        panel.add(portal);

        // Portal ability title
        JLabel portalTitle = new JLabel("Portal");
        portalTitle.setFont(new Font("MV Boli", Font.BOLD,25));
        portalTitle.setBounds(425,150,150,50);
        portalTitle.setForeground(Color.DARK_GRAY);
        panel.add(portalTitle);

        // Ball frenzy ability icon
        ImageIcon frenzyImg = new ImageIcon("data/abilities/frenzy.png");
        JLabel frenzy = new JLabel("",frenzyImg,JLabel.CENTER);
        frenzy.setBounds(0,0,700,600);
        panel.add(frenzy);

        // Ball frenzy ability title
        JLabel frenzyTitle = new JLabel("Ball frenzy");
        frenzyTitle.setFont(new Font("MV Boli", Font.BOLD,25));
        frenzyTitle.setBounds(425,275,150,50);
        frenzyTitle.setForeground(Color.MAGENTA);
        panel.add(frenzyTitle);

        frame.add(panel);
        frame.setVisible(true);
    }

}


