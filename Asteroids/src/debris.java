import java.awt.*;

public class debris extends VectorSprite {

    int debriscounter = 0;

    public debris(int x, int y) {
        shape = new Polygon();
        shape.addPoint(1, 0);
        shape.addPoint(0, -1);
        shape.addPoint(-1, 0);
        shape.addPoint(0, 1);


        drawShape = new Polygon();
        drawShape.addPoint(1, 0);
        drawShape.addPoint(0, -1);
        drawShape.addPoint(-1, 0);
        drawShape.addPoint(0, 1);


        angle = Math.random() * 2 * Math.PI;
        xposition = x +  Math.cos(angle);
        yposition = y +  Math.sin(angle);
        yspeed = thrust * Math.sin(angle) * 5;
        xspeed = thrust * Math.cos(angle) * 5;

        rotation = Math.random() * 0.3;
    }
        public void updatePosition() {
        debriscounter++;
        angle += rotation;
        super.updatePosition();
    }
}
