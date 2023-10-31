import city.cs.engine.*;

public class FireBall implements CollisionListener {

    private final Ball ball;

    // sets ball on fire and makes it more bouncy
    public FireBall(Ball b) {
        this.ball = b;
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Ball) {
            ball.removeAllImages();
            ball.addImage(new BodyImage("data/balls/fire.gif", 2f));
            ball.getBallFix().setRestitution(1.1f);
        }
    }
}


