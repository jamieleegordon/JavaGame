import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player2Controller implements KeyListener {

    private final float WALKING_SPEED;
    private final int jumpSpeed;

    private final Player2 player;

    public Player2Controller(Player2 player, int walkSpeed, int s) {
        this.player = player;
        this.WALKING_SPEED = walkSpeed;
        this.jumpSpeed = s;
    }

    // events occurring when certain keys are being pressed
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT) {
            player.startWalking(-WALKING_SPEED);
        } else if (code == KeyEvent.VK_RIGHT) {
            player.startWalking(WALKING_SPEED);
        } else if (code == KeyEvent.VK_UP){
            player.jump(jumpSpeed);
        }
    }

    // stops moving when key is released
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT) {
            player.stopWalking();
        } else if (code == KeyEvent.VK_RIGHT) {
            player.stopWalking();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
