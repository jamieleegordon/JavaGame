import city.cs.engine.SoundClip;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**Shows the results on a separate window
 * - breaks down all the results from each level and an aggregate score
 * - saving and loading feature implemented that saves stats such as how many matches have been played
 * and a record showing player 1 wins, draws and player 2 wins.
 * - other stats such as how many matches have been played and also the average amount of goals scored by each player per match
 * */
public class Results {

    private final JButton playAgain;
    private final JPanel rPanel;
    private final JFrame frame;

    private String level1;
    private String level2;
    private String level3;
    private String level4;
    private int player1Score;
    private int player2Score;
    private String finalR;

    private static SoundClip kids;

    public Results(String l1, String l2, String l3, String l4, int p1, int p2, String f) throws IOException {

        this.level1 = l1;
        this.level2 = l2;
        this.level3 = l3;
        this.level4 = l4;
        this.player1Score = p1;
        this.player2Score = p2;
        this.finalR = f;

        // updates player1 vs player 2 record
        RecordWriter rWriter = new RecordWriter("data/files/record.txt");

        RecordReader rReader = new RecordReader("data/files/record.txt");
        rReader.readRecord();

        // if player 1 wins ...
        if (player1Score > player2Score){
            rWriter.writeRecord(
                    rReader.updateP1Wins(),
                    rReader.getDraws(),
                    rReader.getP2Wins());
        // if player 2 wins ...
        } else if (player2Score > player1Score){
            rWriter.writeRecord(
                    rReader.getP1Wins(),
                    rReader.getDraws(),
                    rReader.updateP2Wins());
        // if there is a draw ...
        } else {
            rWriter.writeRecord(
                    rReader.getP1Wins(),
                    rReader.updateDraws(),
                    rReader.getP2Wins());
        }
        rReader.readRecord();

        // updates the amount of matches that have been players
        // and generates an average amount of goals each player scores per match
        StatsWriter sWriter = new StatsWriter("data/files/stats.txt");

        StatsReader sReader = new StatsReader("data/files/stats.txt");
        sReader.readStats();

        sWriter.writeStats(sReader.updateMatches(), sReader.getP1Goals() + player1Score,
                sReader.getP2Goals() + player2Score);

        sReader.readStats();

        // average goals scored by each player
        int avgP1 = sReader.getP1Goals() / sReader.getMatches();
        int avgP2 = sReader.getP2Goals() / sReader.getMatches();

        // background music plays
        playBackgroundSound();

        frame = new JFrame("Results");
        rPanel = new JPanel();
        frame.setSize(600, 600);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(rPanel);
        frame.setLocationByPlatform(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        rPanel.setLayout(null);

        // Shows the results from all levels and who wins
        JLabel results = new JLabel("Results");
        results.setFont(new Font("Dialog",Font.PLAIN,40));
        results.setBounds(70,150,200,50);
        results.setForeground(Color.yellow);
        rPanel.add(results);

        JLabel level1R = new JLabel("Level 1: " + level1);
        level1R.setFont(new Font("Dialog",Font.PLAIN,25));
        level1R.setBounds(50,200,200,50);
        level1R.setForeground(Color.yellow);
        rPanel.add(level1R);

        JLabel level2R = new JLabel("Level 2: " + level2);
        level2R.setFont(new Font("Dialog",Font.PLAIN,25));
        level2R.setBounds(50,240,200,50);
        level2R.setForeground(Color.yellow);
        rPanel.add(level2R);

        JLabel level3R = new JLabel("Level 3: " + level3);
        level3R.setFont(new Font("Dialog",Font.PLAIN,25));
        level3R.setBounds(50,280,200,50);
        level3R.setForeground(Color.yellow);
        rPanel.add(level3R);

        JLabel level4R = new JLabel("Level 4: " + level4);
        level4R.setFont(new Font("Dialog",Font.PLAIN,25));
        level4R.setBounds(50,320,200,50);
        level4R.setForeground(Color.yellow);
        rPanel.add(level4R);

        JLabel agg = new JLabel("Aggregate: " + finalR);
        agg.setFont(new Font("Dialog",Font.PLAIN,25));
        agg.setBounds(50,360,200,50);
        agg.setForeground(Color.yellow);
        rPanel.add(agg);

        JLabel record = new JLabel("Record");
        record.setFont(new Font("Dialog",Font.PLAIN,40));
        record.setBounds(370,150,200,50);
        record.setForeground(Color.yellow);
        rPanel.add(record);

        // Shows player vs player record
        JLabel player1Wins = new JLabel("Player 1 wins: " + rReader.getP1Wins());
        player1Wins.setFont(new Font("Dialog",Font.PLAIN,25));
        player1Wins.setBounds(300,200,200,50);
        player1Wins.setForeground(Color.yellow);
        rPanel.add(player1Wins);

        JLabel draws = new JLabel("Draws: " + rReader.getDraws());
        draws.setFont(new Font("Dialog",Font.PLAIN,25));
        draws.setBounds(300,240,200,50);
        draws.setForeground(Color.yellow);
        rPanel.add(draws);

        JLabel player2WIns = new JLabel("Player 2 wins: " + rReader.getP2Wins());
        player2WIns.setFont(new Font("Dialog",Font.PLAIN,25));
        player2WIns.setBounds(300,280,200,50);
        player2WIns.setForeground(Color.yellow);
        rPanel.add(player2WIns);

        JLabel matches = new JLabel("Matches: " + sReader.getMatches());
        matches.setFont(new Font("Dialog",Font.PLAIN,25));
        matches.setBounds(300,320,200,50);
        matches.setForeground(Color.yellow);
        rPanel.add(matches);

        JLabel p1Avg = new JLabel("P1 avg goals: " + avgP1);
        p1Avg.setFont(new Font("Dialog",Font.PLAIN,25));
        p1Avg.setBounds(300,360,200,50);
        p1Avg.setForeground(Color.yellow);
        rPanel.add(p1Avg);

        JLabel p2Avg = new JLabel("P2 avg goals: " + avgP2);
        p2Avg.setFont(new Font("Dialog",Font.PLAIN,25));
        p2Avg.setBounds(300,400,200,50);
        p2Avg.setForeground(Color.yellow);
        rPanel.add(p2Avg);

        JLabel winner = new JLabel();
        winner.setFont(new Font("Dialog",Font.PLAIN,50));
        winner.setBounds(120,20,400,100);
        winner.setForeground(Color.blue);
        rPanel.add(winner);

        // Displays label showing who won depending on who scored more on aggregate
        if (player1Score > player2Score) {
            winner.setText("Player 1 wins");
        } else if (player2Score > player1Score){
            winner.setText("Player 2 wins");
        } else {
            winner.setText("Draw");
        }

        //background
        ImageIcon img = new ImageIcon("data/stadiums/grass.png");
        JLabel background = new JLabel("",img,JLabel.CENTER);
        background.setBounds(0,0,600,600);
        rPanel.add(background);

        // play again button
        playAgain = new JButton("Play again");
        playAgain.setBounds(200,500,200,50);
        rPanel.add(playAgain);

        frame.setVisible(true);

        buttonListeners();
    }

    public void buttonListeners() {
        ActionListener playAgainButton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                kids.stop();
                new StartUp();
            }
        };
        playAgain.addActionListener(playAgainButton);
    }

    static {
        try {
            kids = new SoundClip("data/sounds/kids.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public void playBackgroundSound(){
        kids.play();
        kids.setVolume(0.6f);
        kids.loop();
    }

}

