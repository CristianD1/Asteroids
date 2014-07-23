import java.awt.*;

public class heart extends VectorSprite{

    public heart(){
      shape = new Polygon();
        shape.addPoint(50,10);
        shape.addPoint(60,20);
        shape.addPoint(70,10);
        shape.addPoint(80,20);
        shape.addPoint(60,40);
        shape.addPoint(40,20);

        drawShape = new Polygon();
        drawShape.addPoint(50,10);
        drawShape.addPoint(60,20);
        drawShape.addPoint(70,10);
        drawShape.addPoint(80,20);
        drawShape.addPoint(60,40);
        drawShape.addPoint(40,20);
    }
}
