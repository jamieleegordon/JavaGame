import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/** Platforms that move on their own left to right **/
public class MovingPlatform extends StaticBody implements StepListener {

    private static final Shape brickShape = new PolygonShape(4.89f,-0.184f, 4.87f,0.25f
            , -4.831f,0.23f, -4.811f,-0.184f);

    private static final BodyImage brickImage =
            new BodyImage("data/stadiums/brick.png", 0.5f);

    private final Vec2 startPosition;
    private final float right, left;
    private float delta;

    private final int yCoo;

    // increases x by 20
    public MovingPlatform(World w, int y, Vec2 v) {
        super(w, brickShape);
        addImage(brickImage);
        this.yCoo = y;
        startPosition = v;
        left = startPosition.x;
        right = startPosition.x + 20;
        delta = 0.08f;
        w.addStepListener(this);
    }

    @Override
    public void preStep(StepEvent stepEvent) {
        if (getPosition().x < left){
            this.setPosition(startPosition);
            delta *=-1;
        }
        if (getPosition().x > right){
            delta *=-1;
        }
        this.setPosition(new Vec2(this.getPosition().x + delta, yCoo));
    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }
}
