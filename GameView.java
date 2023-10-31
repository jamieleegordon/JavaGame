import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**timer, stats and foreground graphics implemented
 * - ball appears next to the player scores to represent how many goals have been scored by each player
 * - countdown timer of 60 seconds to show the players how long they have left
 * - hourglass image appears when there is only 15 seconds left of the level
 * - the winner of the level is displayed at the end in the foreground */
public class GameView extends UserView {

    private final Image background;

    private final Player1 player1;
    private final Player2 player2;

    private int timeRemaining = 60;
    private String timeRemainingText = "";
    private Timer timer;
    private final World world;

    private final String ballFile = "data/balls/goalBall.gif";

    private static SoundClip whistleSound;

    public GameView(World w, int width, int height, String backgroundImg, Player1 player1, Player2 player2) {
        super(w, width, height);
        background = new ImageIcon(backgroundImg).getImage();
        this.player1 = player1;
        this.player2 = player2;
        this.world = w;
        whistleSound.setVolume(2);

        // start the timer
        timerMethod();
        timer.start();
    }

    // SOUND
    static {
        try {
            whistleSound = new SoundClip("data/sounds/whistle.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public void setTimeRemainingText(String timeRemainingText) {
        this.timeRemainingText = timeRemainingText;
    }

    public void timerMethod(){
        timer = new Timer(1000, new ActionListener() { // timer decreases with a 1000-millisecond delay (seconds)

            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining --; // decrement time left
                setTimeRemainingText(String.valueOf(timeRemaining)); // change the text to show new time left
                if (timeRemaining <= 0) {
                    timer.stop(); // stop the timer when the time is up
                }
            }
        });
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, 0, -60, this);
    }

    @Override
    protected void paintForeground(Graphics2D g) {
        // Scoreboard
        g.setFont(new Font("MV Boli", Font.BOLD, 40));
        g.drawString(player1.getPlayer1Score() + " - " + player2.getPlayer2Score(), 460, 40);
        g.setFont(new Font("Dialog", Font.BOLD, 30));
        g.setColor(Color.GREEN);
        g.drawString(timeRemainingText,500, 80);
        if (timeRemaining <= 15){
            // time changes to red as not long left
            g.setColor(Color.red);
            g.drawString(timeRemainingText,500, 80);

            // draws a hour glass when there is only 15 seconds left
            Shape hourGlassShape = new BoxShape(0.5f,0.5f);
            StaticBody hourGlass = new StaticBody(world);
            GhostlyFixture goal1Ghost = new GhostlyFixture(hourGlass,hourGlassShape);
            hourGlass.setPosition(new Vec2(2.4f,8.7f));
            hourGlass.addImage(new BodyImage("data/buttonIcons/hourGlass.png",3f));
        }

        // displays the winner of the round
        if (timeRemaining == 0){
            whistleSound.play();
            // stops the game
            world.stop();
            // displays who won or if there was a draw
            g.setFont(new Font("MV Boli", Font.BOLD, 80));
            g.setColor(Color.YELLOW);
            if (player1.getPlayer1Score() > player2.getPlayer2Score()){
                g.drawString("PLAYER 1 WINS", 200,250);
            } else if (player2.getPlayer2Score() > player1.getPlayer1Score()){
                g.drawString("PLAYER 2 WINS", 200,250);
            } else {
                g.drawString("DRAW", 390,250);
            }
        }

        player1Goals();
        player2Goals();
    }

    // draws a ball gif when a goal is scored for player 1
    public void player1Goals () {
        if (player1.getPlayer1Score() == 1){
            Shape goal1Shape = new BoxShape(0.5f,0.5f);
            StaticBody goal1 = new StaticBody(world);
            GhostlyFixture goal1Ghost = new GhostlyFixture(goal1,goal1Shape);
            goal1.setPosition(new Vec2(-3.7f,11.2f));
            goal1.addImage(new BodyImage(ballFile,1.3f));
        } else if (player1.getPlayer1Score() == 2){
            Shape goal2Shape = new BoxShape(0.5f,0.5f);
            StaticBody goal2 = new StaticBody(world);
            GhostlyFixture goal2Ghost = new GhostlyFixture(goal2,goal2Shape);
            goal2.setPosition(new Vec2(-5.2f,11.2f));
            goal2.addImage(new BodyImage(ballFile,1.3f));
        } else if (player1.getPlayer1Score() == 3){
            Shape goal3Shape = new BoxShape(0.5f,0.5f);
            StaticBody goal3 = new StaticBody(world);
            GhostlyFixture goal3Ghost = new GhostlyFixture(goal3,goal3Shape);
            goal3.setPosition(new Vec2(-6.7f,11.2f));
            goal3.addImage(new BodyImage(ballFile,1.3f));
        }else if (player1.getPlayer1Score() == 4) {
            Shape goal4Shape = new BoxShape(0.5f, 0.5f);
            StaticBody goal4 = new StaticBody(world);
            GhostlyFixture goal4Ghost = new GhostlyFixture(goal4, goal4Shape);
            goal4.setPosition(new Vec2(-8.2f, 11.2f));
            goal4.addImage(new BodyImage(ballFile, 1.3f));
        }else if (player1.getPlayer1Score() == 5) {
            Shape goal5Shape = new BoxShape(0.5f, 0.5f);
            StaticBody goal5 = new StaticBody(world);
            GhostlyFixture goal5Ghost = new GhostlyFixture(goal5, goal5Shape);
            goal5.setPosition(new Vec2(-9.7f, 11.2f));
            goal5.addImage(new BodyImage(ballFile, 1.3f));
        }else if (player1.getPlayer1Score() == 6) {
            Shape goal6Shape = new BoxShape(0.5f, 0.5f);
            StaticBody goal6 = new StaticBody(world);
            GhostlyFixture goal6Ghost = new GhostlyFixture(goal6, goal6Shape);
            goal6.setPosition(new Vec2(-11.2f, 11.2f));
            goal6.addImage(new BodyImage(ballFile, 1.3f));
        }else if (player1.getPlayer1Score() == 7) {
            Shape goal7Shape = new BoxShape(0.5f, 0.5f);
            StaticBody goal7 = new StaticBody(world);
            GhostlyFixture goal7Ghost = new GhostlyFixture(goal7, goal7Shape);
            goal7.setPosition(new Vec2(-12.7f, 11.2f));
            goal7.addImage(new BodyImage(ballFile, 1.3f));
        }else if (player1.getPlayer1Score() == 8) {
            Shape goal8Shape = new BoxShape(0.5f, 0.5f);
            StaticBody goal8 = new StaticBody(world);
            GhostlyFixture goal8Ghost = new GhostlyFixture(goal8, goal8Shape);
            goal8.setPosition(new Vec2(-11.2f, 11.2f));
            goal8.addImage(new BodyImage(ballFile, 1.3f));
        }
    }

    // draws a ball gif when a goal is scored for player 2
    public void player2Goals () {
        if (player2.getPlayer2Score() == 1){
            Shape goal1Shape = new BoxShape(0.5f,0.5f);
            StaticBody goal1 = new StaticBody(world);
            GhostlyFixture goal1Ghost = new GhostlyFixture(goal1,goal1Shape);
            goal1.setPosition(new Vec2(4.1f,11.2f));
            goal1.addImage(new BodyImage(ballFile,1.3f));
        } else if (player2.getPlayer2Score() == 2){
            Shape goal2Shape = new BoxShape(0.5f,0.5f);
            StaticBody goal2 = new StaticBody(world);
            GhostlyFixture goal2Ghost = new GhostlyFixture(goal2,goal2Shape);
            goal2.setPosition(new Vec2(5.6f,11.2f));
            goal2.addImage(new BodyImage(ballFile,1.3f));
        } else if (player2.getPlayer2Score() == 3){
            Shape goal3Shape = new BoxShape(0.5f,0.5f);
            StaticBody goal3 = new StaticBody(world);
            GhostlyFixture goal3Ghost = new GhostlyFixture(goal3,goal3Shape);
            goal3.setPosition(new Vec2(7.1f,11.2f));
            goal3.addImage(new BodyImage(ballFile,1.3f));
        }else if (player2.getPlayer2Score() == 4) {
            Shape goal4Shape = new BoxShape(0.5f, 0.5f);
            StaticBody goal4 = new StaticBody(world);
            GhostlyFixture goal4Ghost = new GhostlyFixture(goal4, goal4Shape);
            goal4.setPosition(new Vec2(8.6f, 11.2f));
            goal4.addImage(new BodyImage(ballFile, 1.3f));
        }else if (player2.getPlayer2Score() == 5) {
            Shape goal5Shape = new BoxShape(0.5f, 0.5f);
            StaticBody goal5 = new StaticBody(world);
            GhostlyFixture goal5Ghost = new GhostlyFixture(goal5, goal5Shape);
            goal5.setPosition(new Vec2(10.1f, 11.2f));
            goal5.addImage(new BodyImage(ballFile, 1.3f));
        }else if (player2.getPlayer2Score() == 6) {
            Shape goal6Shape = new BoxShape(0.5f, 0.5f);
            StaticBody goal6 = new StaticBody(world);
            GhostlyFixture goal6Ghost = new GhostlyFixture(goal6, goal6Shape);
            goal6.setPosition(new Vec2(11.6f, 11.2f));
            goal6.addImage(new BodyImage(ballFile, 1.3f));
        }else if (player2.getPlayer2Score() == 7) {
            Shape goal7Shape = new BoxShape(0.5f, 0.5f);
            StaticBody goal7 = new StaticBody(world);
            GhostlyFixture goal7Ghost = new GhostlyFixture(goal7, goal7Shape);
            goal7.setPosition(new Vec2(13.1f, 11.2f));
            goal7.addImage(new BodyImage(ballFile, 1.3f));
        }else if (player2.getPlayer2Score() == 8) {
            Shape goal8Shape = new BoxShape(0.5f, 0.5f);
            StaticBody goal8 = new StaticBody(world);
            GhostlyFixture goal8Ghost = new GhostlyFixture(goal8, goal8Shape);
            goal8.setPosition(new Vec2(11.6f, 11.2f));
            goal8.addImage(new BodyImage(ballFile, 1.3f));
        }
    }

    public Timer getTimer() {
        return timer;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
}
