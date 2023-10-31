import city.cs.engine.BodyImage;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;

public class DefaultBall implements CollisionListener {

    private final Ball ball;

    // turns ball back to normal if it is on fire
    public DefaultBall(Ball b) {
        this.ball = b;
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Player1 || e.getOtherBody() instanceof Player2){
            ball.removeAllImages();
            ball.addImage(new BodyImage("data/balls.ball.png", 1.5f));
            ball.getBallFix().setRestitution(0.9f);
        }
    }
}
