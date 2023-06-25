package org.cis1200;

import org.cis1200.wordle.Tiles;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class WordleTests {


    @Test
    public void checkIfChangeWordToGuessChanges() throws IOException {
        Tiles t = new Tiles();
        String s = t.getAnswer();
        t.changeWordToGuess();
        String p = t.getAnswer();
        assertTrue(Objects.equals(s, p), String.valueOf(false));
    }

    @Test
    public void correctLettersTest() throws IOException {
        Tiles t = new Tiles();
        String s = t.getAnswer();
        char[] c = s.toCharArray();
        t.currRow = 0;
        for (int j = 0; j < t.tiles[0].length; j++) {
            t.tiles[0][j].setText(String.valueOf(c[j]));
        }
        t.enterGuess();
        boolean flag = true;
        for (int j = 0; j < t.tiles[0].length; j++) {
            System.out.println(t.tiles[0][j].getText() + "," + String.valueOf(c[j]));
            if (!(Objects.equals(t.tiles[0][j].getText(), String.valueOf(c[j])))) {
                flag = false;
                break;
            }
        }
        assertTrue(flag, String.valueOf(true));


    }


    @Test
    public void correctColorsTest() throws IOException {
        Tiles t = new Tiles();
        String s = t.getAnswer();
        char[] c = s.toCharArray();
        for (int j = 0; j < t.tiles[0].length; j++) {
            t.tiles[0][j].setText(String.valueOf(c[j]));
        }
        t.currCol = 5;
        t.currRow = 0;
        t.enterGuess();
        boolean flag = true;
        for (int j = 0; j < t.tiles[0].length; j++) {
            System.out.println(t.tiles[0][j].getColorBackground() + "," + Color.green);
            if (!(t.tiles[0][j].getColorBackground() == Color.green)) {
                flag = false;
                break;
            }
        }
        assertTrue(flag, String.valueOf(true));


    }


    @Test
    public void notViableWord() throws IOException {
        Tiles t = new Tiles();
        String s = t.getAnswer();
        char[] c = s.toCharArray();
        t.currRow = 0;
        for (int j = 0; j < t.tiles[0].length; j++) {
            t.tiles[0][j].setText(";");
        }
        t.currCol = 5;
        t.currRow = 0;
        boolean flag = t.enterGuess();
        System.out.println(flag);
        assertFalse(flag, String.valueOf(true));

    }

    @Test
    public void viableWord() throws IOException {
        Tiles t = new Tiles();
        String s = t.getAnswer();
        char[] c = s.toCharArray();
        t.currRow = 0;
        t.tiles[0][0].setText("H");
        t.tiles[0][1].setText("E");
        t.tiles[0][2].setText("L");
        t.tiles[0][3].setText("L");
        t.tiles[0][4].setText("O");
        t.currCol = 5;
        t.currRow = 0;
        boolean flag = t.enterGuess();
        for (int j = 0; j < t.tiles[0].length; j++) {
            System.out.println(
                    t.tiles[0][j].getText());
        }

        System.out.println(flag);
        assertTrue(flag, String.valueOf(true));

    }








  /*  boolean flag=true;
        for (Tile j: t.tiles[0]){
        if (!(j.getColorBackground() == Color.green)) {
            flag = false;
            break;
        }
    } */


}
