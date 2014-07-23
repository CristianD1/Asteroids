import java.awt.*;

public class SpaceCraft extends VectorSprite {

    int firedelay;
    int heart = 3;
    int cash = 0;

    public SpaceCraft() {

        active = true;
        shape = new Polygon();
        shape.addPoint(20, -30);
        shape.addPoint(10, -20);
        shape.addPoint(0, -30);
        shape.addPoint(-10, -20);
        shape.addPoint(-20, -30);
        shape.addPoint(-30, -30);
        shape.addPoint(-20, 0);
        shape.addPoint(-10, 10);
        shape.addPoint(-10, 20);
        shape.addPoint(0, 30);
        shape.addPoint(10, 20);
        shape.addPoint(10, 10);
        shape.addPoint(20, 0);
        shape.addPoint(30, -30);

        drawShape = new Polygon();
        drawShape.addPoint(20, -30);
        drawShape.addPoint(10, -20);
        drawShape.addPoint(0, -30);
        drawShape.addPoint(-10, -20);
        drawShape.addPoint(-20, -30);
        drawShape.addPoint(-30, -30);
        drawShape.addPoint(-20, 0);
        drawShape.addPoint(-10, 10);
        drawShape.addPoint(-10, 20);
        drawShape.addPoint(0, 30);
        drawShape.addPoint(10, 20);
        drawShape.addPoint(10, 10);
        drawShape.addPoint(20, 0);
        drawShape.addPoint(30, -30);

        xposition = 450;
        yposition = 300;
        firedelay = 100;
    }
    int upgrades[][] = new int[9][2];

    public void accelerate() {
        if (Math.sqrt(yspeed * yspeed + xspeed * xspeed) < 10) {
            xspeed -= (thrust + upgrades[0][0] / 10) * Math.cos(angle - Math.PI / 2);
            yspeed -= (thrust + upgrades[0][0] / 10) * Math.sin(angle - Math.PI / 2);
        }
        if (xspeed > 0) {
            xspeed -= 0.1;
        } else if (xspeed < 0) {
            xspeed += 0.1;
        }

        if (yspeed > 0) {
            yspeed -= 0.1;
        } else if (yspeed < 0) {
            yspeed += 0.1;
        }
    }

    public void deccelerate() {
        xspeed /= (1 + (double) upgrades[2][0] / 50);
        yspeed /= (1 + (double) upgrades[2][0] / 50);
    }

    public void rotateLeft() {
        angle -= (rotation + upgrades[1][0] / 100);
    }

    public void rotateRight() {
        angle += (rotation + upgrades[1][0] / 100);
    }

    public void reset() {
        xposition = 450;
        yposition = 300;
        xspeed = 0;
        yspeed = 0;
        angle = 0;
        active = true;
    }

    public void hit() {
        if (active == true) {
            active = false;
            counter = 0;
            heart -= 1;
        }
    }

    public void updatePosition() {
        firedelay += 1 + upgrades[3][0];

        super.updatePosition();
    }
}
