package org.cis1200.wordle;

/*
 * CIS 120 HW09 - Wordle Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;

import java.io.IOException;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * <p>
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * <p>
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a Wordle object to serve as the game's model.
 */
public class RunWordlee implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Wordle");
        frame.setLocation(300, 300);
        frame.setResizable(false);
        frame.setFocusable(true);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final GameBoard board;
        try {
            board = new GameBoard(status);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        frame.add(board, BorderLayout.CENTER);


        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> {
            board.resetGrid();
            board.repaint();
            try {
                board.reset();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        control_panel.add(reset);

        final JButton save = new JButton("Save");
        save.addActionListener(e -> {
            try {
                board.saveGame();
                System.exit(0);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        control_panel.add(save);


        final JButton load = new JButton("Load");
        load.addActionListener(e -> {
            try {
                board.loadGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);

            }
        });
        control_panel.add(load);


        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game

        try {
          /*  if (!board.loadGame()){
                board.reset();
            } */
            board.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}