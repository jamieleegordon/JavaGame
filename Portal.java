import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.SoundClip;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/** Portal - teleports player to high platform in game in level 3 (ball can sometimes get stuck
 and this ability can be used as an advantage to reach the ball from above) **/
public class Portal implements CollisionListener {

    private final Player1 player1;
    private final Player2 player2;

    private static SoundClip portal;

    public Portal(Player1 p1, Player2 p2){
        this.player1 = p1;
        this.player2 = p2;
    }

    // SOUND
    static {
        try {
            portal = new SoundClip("data/sounds/portal.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Player1){
            portal.play();
            e.getReportingBody().destroy();
            player1.setPosition(new Vec2(10,7));
        } else if (e.getOtherBody() instanceof Player2){
            portal.play();
            e.getReportingBody().destroy();
            player2.setPosition(new Vec2(-10,7));
        }
    }
}
