import java.awt.*;

public class VectorSprite {

    double thrust = 0.2, rotation = 0.05;
    double xposition;
    double yposition;
    double xspeed;
    double yspeed;
    double angle;
    Polygon shape, drawShape;
    boolean active;
    int counter;

    public VectorSprite() {

        shape = new Polygon();
        shape.addPoint(0, -30);
        shape.addPoint(10, 0);
        shape.addPoint(20, 10);
        shape.addPoint(25, 20);
        shape.addPoint(5, 25);
        shape.addPoint(-5, 25);
        shape.addPoint(-25, 20);
        shape.addPoint(-20, 10);
        shape.addPoint(-10, 0);

        drawShape = new Polygon();
        drawShape.addPoint(0, -30);
        drawShape.addPoint(10, 0);
        drawShape.addPoint(20, 10);
        drawShape.addPoint(25, 20);
        drawShape.addPoint(5, 25);
        drawShape.addPoint(-5, 25);
        drawShape.addPoint(-25, 20);
        drawShape.addPoint(-20, 10);
        drawShape.addPoint(-10, 0);

        xposition = 450;
        yposition = 300;
    }

    public void paint(Graphics g) {
        g.drawPolygon(drawShape);
        g.fillPolygon(drawShape);
    }

    public void updatePosition() {

        counter ++;
        xposition += xspeed;
        yposition += yspeed;
        wrapAround();
        int x, y;
        for (int i = 0; i < shape.npoints; i++) {
            x = (int) Math.round(shape.xpoints[i] * Math.cos(angle) - shape.ypoints[i] * Math.sin(angle));
            y = (int) Math.round(shape.xpoints[i] * Math.sin(angle) + shape.ypoints[i] * Math.cos(angle));
            drawShape.xpoints[i] = x;
            drawShape.ypoints[i] = y;
        }
        drawShape.invalidate();
        drawShape.translate((int) Math.round(xposition), (int) Math.round(yposition));
    }

    public void wrapAround() {
        if (xposition > 910) {
            xposition = -10;
        }

        if (xposition < -10) {
            xposition = 910;
        }

        if (yposition > 610) {
            yposition = -10;
        }

        if (yposition < -10) {
            yposition = 610;
        }
    }
}


