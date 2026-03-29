package pro1.drawingModel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Group extends XY {

    ArrayList<Drawable> items;
    double rotationDegrees;
    double scaleX;
    double scaleY;

    public Group(ArrayList<Drawable> items, int x, int y, double rotationDegrees, double scaleX, double scaleY) {
        super(x, y);
        this.items = items;
        this.rotationDegrees = rotationDegrees;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform transform = g.getTransform();

        g.translate(this.x, this.y);
        g.rotate(Math.toRadians(this.rotationDegrees));
        g.scale(this.scaleX, this.scaleY);
        for (var item : this.items)
            item.draw(g);

        g.setTransform(transform);
    }
}
