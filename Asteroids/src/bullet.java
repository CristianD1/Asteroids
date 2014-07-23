import java.awt.*;

public class bullet extends VectorSprite {

    int bulletcounter;

    public bullet(int x, int y, double a,int xs , int ys, int size, int speed) {

        active = true;

        shape = new Polygon();
        shape.addPoint((size), 0);
        shape.addPoint(0, -(size));
        shape.addPoint(-(size), 0);
        shape.addPoint(0, (size));


        drawShape = new Polygon();
        drawShape.addPoint((size), 0);
        drawShape.addPoint(0, -(size));
        drawShape.addPoint(-(size), 0);
        drawShape.addPoint(0, (size));


        angle = a;
        xposition = x + 30 * Math.cos(angle);
        yposition = y + 30 * Math.sin(angle);
        yspeed = ys +(thrust +3+speed) * Math.sin(angle) ;
        xspeed = xs+ (thrust+3 +speed) * Math.cos(angle) ;

        rotation = Math.random() * 0.3;
        bulletcounter = 0;
    }

    public void updatePosition() {
        bulletcounter++;
        angle += rotation;
        super.updatePosition();
    }
}
