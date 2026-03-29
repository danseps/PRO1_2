package pro1.swingComponents;

import pro1.utils.ColorUtils;

import javax.swing.*;
import java.awt.*;

public class OptionsPanel extends JPanel {
    private final MainFrame parent;
    private final JSlider rSlider;
    private final JSlider gSlider;
    private final JSlider bSlider;
    private final JSlider radiusSlider;
    private final JCheckBox linesCheckbox;

    public OptionsPanel(MainFrame parent) {
        this.parent = parent;
        this.setPreferredSize(new Dimension(200, 0));
        JButton newColorBtn = new JButton("Náhodná barva");
        this.add(newColorBtn);
        newColorBtn.addActionListener(
                (_) -> {
                    this.parent.setColor(ColorUtils.randomColor());
                    this.parent.showExample();
                }
        );

        // Color sliders
        this.add(new JLabel("Barva (RGB):"));
        this.rSlider = new JSlider(0, 255, 0);
        this.gSlider = new JSlider(0, 255, 0);
        this.bSlider = new JSlider(0, 255, 0);
        this.add(this.rSlider);
        this.add(this.gSlider);
        this.add(this.bSlider);
        this.rSlider.addChangeListener((_) -> this.sliderChanged());
        this.gSlider.addChangeListener((_) -> this.sliderChanged());
        this.bSlider.addChangeListener((_) -> this.sliderChanged());

        // Radius slider
        this.add(new JLabel("Poloměr:"));
        this.radiusSlider = new JSlider(5, 100, 25);
        this.add(this.radiusSlider);
        this.radiusSlider.addChangeListener((_) -> this.parent.setRadius(this.radiusSlider.getValue()));

        // Lines visibility checkbox
        this.add(new JLabel("Viditelnost:"));
        this.linesCheckbox = new JCheckBox("Zobrazit úsečky", true);
        this.add(this.linesCheckbox);
        this.linesCheckbox.addActionListener((_) -> this.parent.setLinesVisible(this.linesCheckbox.isSelected()));

        // Reset button
        JButton resetBtn = new JButton("Reset");
        this.add(resetBtn);
        resetBtn.addActionListener((_) -> this.parent.reset());
    }

    private void sliderChanged() {
        this.parent.setColor(ColorUtils.color(
                this.rSlider.getValue(),
                this.gSlider.getValue(),
                this.bSlider.getValue()
        ));
        this.parent.showExample();
    }
}
