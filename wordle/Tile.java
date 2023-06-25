package org.cis1200.wordle;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Tile extends JLabel {

    private java.awt.Color colorBackground;
    private java.awt.Color colorBorder;

    public Tile() {
        this.setBackground(Color.lightGray);
        colorBackground = Color.lightGray;
        this.setPreferredSize(new Dimension(300 / 6, 300 / 6));
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        colorBorder = Color.black;
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
        this.setText(" ");
        this.setOpaque(true);
    }


    public void setTile(String option, Character c) {
        this.setText(c.toString());
        this.colorChange(option);
    }


    public void colorChange(String option) {
        if (Objects.equals(option, "incorrect")) {
            this.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
            this.setBackground(Color.darkGray);
            colorBackground = Color.darkGray;
            colorBorder = Color.gray;
        } else if (Objects.equals(option, "correct")) {
            this.setBorder(BorderFactory.createLineBorder(Color.green, 2));
            this.setBackground(Color.green);
            colorBackground = Color.green;
            colorBorder = Color.green;
        } else if (Objects.equals(option, "diffSpot")) {
            this.setBorder(BorderFactory.createLineBorder(Color.yellow, 2));
            this.setBackground(Color.yellow);
            colorBackground = Color.yellow;
            colorBorder = Color.yellow;
        }
    }


    public java.awt.Color getColorBackground() {
        return colorBackground;
    }

    public java.awt.Color getColorBorder() {
        return colorBorder;
    }


}
