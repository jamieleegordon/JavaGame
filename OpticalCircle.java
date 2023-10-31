import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class OpticalCircle extends StaticBody implements StepListener {

    private static final Shape circleShape = new CircleShape(1);

    private static final BodyImage circleImage =
            new BodyImage("data/stadiums/optical.gif", 2);

    private final Vec2 startPosition;
    private final float right, left;
    private float delta;

    private final int yCoo;

    // circle shape that moves left and right by increasing x by 5
    public OpticalCircle(World w, int y, Vec2 v) {
        super(w, circleShape);
        addImage(circleImage);
        this.yCoo = y;
        startPosition = v;
        left = startPosition.x;
        right = startPosition.x + 5;
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
