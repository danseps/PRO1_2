package pro1.swingComponents;

import pro1.drawingModel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private final DisplayPanel displayPanel;
    private int x;
    private int y;
    private String color = "#000000";
    private ArrayList<Ellipse> circles;
    private final ArrayList<Line> lines = new ArrayList<>();
    private int radius = 25; // defaultni radius
    private boolean linesVisible = true;

    public void setColor(String color) {
        this.color = color;
    }

    public void setRadius(int newRadius) {
        this.radius = newRadius;
        lines.clear();
        ArrayList<Ellipse> newCircles = new ArrayList<>();

        for (Ellipse oldCircle : circles) {
            // Stará pozice (levý horní roh): oldCircle.x, oldCircle.y
            int centerX = oldCircle.getX() + oldCircle.getWidth() / 2; // reverse operace k odecitani radiusu ve vytvareni noveho kolecka
            int centerY = oldCircle.getY() + oldCircle.getHeight() / 2;

            var newCircle = new Ellipse(centerX - radius, centerY - radius, radius * 2, radius * 2, oldCircle.getColor());
            newCircles.add(newCircle);

            // Pridani usecky k nejblizsimu kruhu
            if (newCircles.size() > 1) {
                circles = newCircles;
                addLineToClosestCircle();
            }
        }
        showExample();
    }

    public void setLinesVisible(boolean visible) {
        this.linesVisible = visible;
        showExample();
    }

    public void reset() {
        circles.clear();
        lines.clear();
        showExample();
    }

    public MainFrame() {
        this.setTitle("PRO1 Drawing");
        this.setVisible(true);
        this.setSize(800, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.circles = new ArrayList<>();

        this.displayPanel = new DisplayPanel();
        this.add(this.displayPanel, BorderLayout.CENTER);

        JPanel leftPanel = new OptionsPanel(this);
        this.add(leftPanel, BorderLayout.WEST);

        this.displayPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MainFrame.this.x = e.getX();
                MainFrame.this.y = e.getY();
                MainFrame.this.addCircle();
                MainFrame.this.showExample();
            }
        });
    }

    public void addCircle() {
        // střed je v (this.x, this.y)
        var c = new Ellipse(this.x - radius, this.y - radius, radius * 2, radius * 2, this.color);
        circles.add(c);

        // skip u prvniho kruhu
        if (circles.size() > 1) {
            addLineToClosestCircle();
        }
    }

    private void addLineToClosestCircle() {
        Ellipse newestCircle = circles.getLast();
        int newestCenterX = newestCircle.getX() + newestCircle.getWidth() / 2;
        int newestCenterY = newestCircle.getY() + newestCircle.getHeight() / 2;

        int closestIndex = 0;
        double closestDistance = Double.MAX_VALUE; // placeholder pro porovnani vzdalenosti

        // nejblizsi kruh
        for (int i = 0; i < circles.size() - 1; i++) {
            Ellipse circle = circles.get(i);
            int centerX = circle.getX() + circle.getWidth() / 2;
            int centerY = circle.getY() + circle.getHeight() / 2;

            double distance = Math.sqrt(
                    Math.pow(newestCenterX - centerX, 2) +
                            Math.pow(newestCenterY - centerY, 2)
            );
            if (distance < closestDistance) {
                closestDistance = distance;
                closestIndex = i;
            }
        }

        Ellipse closestCircle = circles.get(closestIndex);
        int closestCenterX = closestCircle.getX() + closestCircle.getWidth() / 2;
        int closestCenterY = closestCircle.getY() + closestCircle.getHeight() / 2;

        Line line = new Line(closestCenterX, closestCenterY, newestCenterX, newestCenterY, 2, "#000000");
        lines.add(line);
    }

    public void showExample() {
        MainFrame.this.displayPanel.setDrawable(this.example());
    }

    private Drawable example() {
        ArrayList<Drawable> allDrawables = new ArrayList<>(); // jedna skupina pro vsechny obj

        if (linesVisible) {
            allDrawables.addAll(lines);
        }

        allDrawables.addAll(circles);

        return new Group(allDrawables, 0, 0, 0, 1, 1);
    }
}