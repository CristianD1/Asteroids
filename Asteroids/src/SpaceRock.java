import java.awt.*;

public class SpaceRock extends VectorSprite {
    int size;
    public SpaceRock() {

        active = true;


        drawShape = new Polygon();
        shape = new Polygon();
        for (int angle = 0; angle < 360; angle += 15) {
            int r = (int) (30 + Math.random() * 30);
            shape.addPoint((int) (r * Math.cos(angle * Math.PI / 180)), (int) (r * Math.sin(angle * Math.PI / 180)));
            drawShape.addPoint((int) (r * Math.cos(angle * Math.PI / 180)), (int) (r * Math.sin(angle * Math.PI / 180)));

        }



        double randomSpeed, randomAngle, randomDistance;
        randomAngle = Math.random() * 360;

        randomSpeed = Math.random() + 1;
        xspeed -= randomSpeed * Math.cos(randomAngle + Math.PI / 2);
        yspeed -= randomSpeed * Math.sin(randomAngle + Math.PI / 2);

        randomAngle = Math.random() * 360;
        randomDistance = Math.random() * 400 + 200;
        xposition = randomDistance * Math.cos(randomAngle + Math.PI / 2) + 450;
        yposition = randomDistance * Math.sin(randomAngle + Math.PI / 2) + 300;

        rotation = Math.random() * 0.1;
        size = 3;
    }

    public SpaceRock(int x, int y, int size) {
     this.size = size;
        active = true;
        drawShape = new Polygon();
        shape = new Polygon();
        for (int angle = 0; angle < 360; angle += 15) {
            int r = (int) (10*size + Math.random() * 10*size);
            shape.addPoint((int) (r * Math.cos(angle * Math.PI / 180)), (int) (r * Math.sin(angle * Math.PI / 180)));
            drawShape.addPoint((int) (r * Math.cos(angle * Math.PI / 180)), (int) (r * Math.sin(angle * Math.PI / 180)));

        }
        double randomSpeed, randomAngle, randomDistance;
        randomAngle = Math.random() * 360;

        randomSpeed = Math.random() + 1;
        xspeed -= randomSpeed * Math.cos(randomAngle + Math.PI / 2);
        yspeed -= randomSpeed * Math.sin(randomAngle + Math.PI / 2);

        randomAngle = Math.random() * 360;
        randomDistance = Math.random() * 400 + 200;
        xposition = x;
        yposition = y;

        rotation = Math.random() * 0.1;
    }

    public void updatePosition() {

        angle += rotation;
        super.updatePosition();
    }

    public void hit() {}
}