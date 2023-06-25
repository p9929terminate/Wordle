package org.cis1200.wordle;

/*
 * CIS 120 HW09 - Wordle Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


import static java.lang.Character.isLetter;
import static java.lang.Character.toUpperCase;

/**
 * This class instantiates a Wordle object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * <p>
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * <p>
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private static final int GAP = 1;
    private JLabel status; // current status text

    private Tiles grid;

    private FileWriter lw;

    private FileWriter cw;




    // Game constants
    public static final int BOARD_WIDTH = 300;
    public static final int BOARD_HEIGHT = 500;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) throws IOException {

        // creates border around the court area, JCom   hg iponent method

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        status = statusInit; // initializes the status JLabel

        setFocusable(true);
        requestFocus(true);

        grid = new Tiles();

        add(grid);

        /* for(int i = 0; i < wordle.getGrid().length; i++){
            JPanel fin = new JPanel();
            for(int j = 0; j < wordle.getGrid()[0].length; j++){
                fin.add(wordle.getCell(i,j));
            }
            add(fin);
        } */


        //add(wordle.createPanel(), BorderLayout.CENTER);
        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                requestFocusInWindow();
                System.out.println("hello");
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        grid.enterGuess();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (grid.checkWin()) {
                        JOptionPane.showMessageDialog(null, "You've Guessed it!!!");
                        setFocusable(false);
                    } else if (grid.currRow > 5) {
                        JOptionPane.showMessageDialog(null, "You've Lost");
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (grid.currCol > 0) {
                        grid.currCol--;
                    }
                    grid.tiles[grid.getRow()][grid.getCol()].setText(" ");
                } else {
                    setFocusable(true);
                    requestFocusInWindow();
                    if (grid.getCol() < 5) {
                        Character newC = toUpperCase(e.getKeyChar());
                        if (isLetter(newC)) {
                            grid.tiles[grid.getRow()][grid.getCol()].setText(String.valueOf(newC));
                            grid.currCol++;
                        }
                    }

                    // updates the model given the coordinates of the mouseclick

                    //updateStatus(); // updates the status JLabel
                    repaint();  // repaints the game board
                }

            }

            public void keyReleased(KeyEvent e) {
            }
        });

//        requestFocus(true);

    }


    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() throws IOException {
        requestFocusInWindow();
        setFocusable(true);
        //   wordle.reset();
        repaint();

        // Makes sure this component has keyboard/mouse focus
    }

    public void saveGame() throws IOException {
        grid.saveGame();
    }


    public boolean loadGame() throws IOException {
        requestFocusInWindow();
        setFocusable(true);
        boolean re = grid.loadGame();
        repaint();
        return re;
    }


    public void resetGrid() {
        for (int i = 0; i < grid.tiles.length; i++) {
            for (int j = 0; j < grid.tiles[0].length; j++) {
                grid.tiles[i][j].setText(" ");
                grid.tiles[i][j].setBackground(Color.lightGray);
                grid.tiles[i][j].setBackground(Color.lightGray);
                grid.tiles[i][j].setBorder(BorderFactory.createLineBorder(Color.lightGray));
            }
        }
        grid.changeWordToGuess();
        setFocusable(true);
        grid.currCol = 0;
        grid.currRow = 0;

        // Makes sure this component has keyboard/mouse focus
    }


    public void paint() {
    }


    /**
     * Updates the JLabel to reflect the current state of the game.
     */
  /*  private void updateStatus() {
        if (wordle.getCurrentPlayer()) {
            status.setText("Player 1's Turn");
        } else {
            status.setText("Player 2's Turn");
        }

        int winner = wordle.checkWinner();
        if (winner == 1) {
            status.setText("Player 1 wins!!!");
        } else if (winner == 2) {
            status.setText("Player 2 wins!!!");
        } else if (winner == 3) {
            status.setText("It's a tie.");
        }
    } */

    /**
     * Draws the game board.
     * <p>
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        // Draws board grid
        // g.drawLine(100, 0, 100, 300);
        //  g.drawLine(200, 0, 200, 300);
        //g.drawLine(0, 100, 300, 100);
        //g.drawLine(0, 200, 300, 200);

        // Draws X's and O's


    }


    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

}
