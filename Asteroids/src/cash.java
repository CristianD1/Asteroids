import java.awt.*;

public class cash extends VectorSprite {

    int cashcounter;

    public cash(int x, int y) {

        active = true;

        shape = new Polygon();
        shape.addPoint(10, 0);
        shape.addPoint(0, -10);
        shape.addPoint(-10, 0);
        shape.addPoint(0, 10);

        drawShape = new Polygon();
        drawShape.addPoint(10, 0);
        drawShape.addPoint(0, -10);
        drawShape.addPoint(-10, 0);
        drawShape.addPoint(0, 10);

        angle = Math.random() * 2 * Math.PI;
        xposition = x + 30 * Math.cos(angle);
        yposition = y + 30 * Math.sin(angle);
        yspeed = thrust * Math.sin(angle) * 1;
        xspeed = thrust * Math.cos(angle) * 1;

        rotation = Math.random() * 0.3;

    }

    public void updatePosition() {
        cashcounter++;
        angle += rotation;
        super.updatePosition();
    }
}
