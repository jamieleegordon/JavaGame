import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.SoundClip;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/** Ability to spawn 5 new balls */
public class BallFrenzy implements CollisionListener {

    private World world;

    private static SoundClip frenzyBall;

    public BallFrenzy(World w){
        this.world = w;
    }

    // COLLISION SOUND
    static {
        try {
            frenzyBall = new SoundClip("data/sounds/finalBall.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    /** Spawns 5 new balls in the middle of the world*/
    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Player1 || e.getOtherBody() instanceof Player2){
            frenzyBall.play();
            e.getReportingBody().destroy();
            FrenzyBall b1 = new FrenzyBall(world);
            b1.setPosition(new Vec2(0.1f, 0));
            FrenzyBall b2 = new FrenzyBall(world);
            b2.setPosition(new Vec2(2, 0));
            FrenzyBall b3 = new FrenzyBall(world);
            b3.setPosition(new Vec2(-1, 0));
            FrenzyBall b4 = new FrenzyBall(world);
            b4.setPosition(new Vec2(-3, 0));
            FrenzyBall b5 = new FrenzyBall(world);
            b5.setPosition(new Vec2(4, 0));
        }
    }

}
