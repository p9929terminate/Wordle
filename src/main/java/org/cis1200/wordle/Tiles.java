package org.cis1200.wordle;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Character.toUpperCase;

public class Tiles extends JPanel {

    public Tile[][] tiles = new Tile[6][5];

    public int currRow = 0;

    public int currCol = 0;

    public static final int BOARD_WIDTH = 300;
    public static final int BOARD_HEIGHT = 500;

    public ArrayList<Character> wordToGuess;

    private Random random;

    private BufferedReader br;
    private String wGu;

    public static final String LETTER_FILE = "/Users/pvpalleti/Downloads/hw09_local_temp/" +
            "src/main/java/org/cis1200/wordle/saveLetterGuesses.txt";


    public static final String COLOR_FILE = "/Users/pvpalleti/Downloads/hw09_local_temp/src/main" +
            "/java/org/cis1200/wordle/saveColorGrid.txt";

    private ArrayList<String> possWords;

    private final Map<Integer, java.awt.Color> intToColor = Stream.of(new Object[][]{
            {1, java.awt.Color.green},
            {2, Color.darkGray},
            {3, Color.yellow}
    }).collect(Collectors.toMap(p -> (Integer) p[0], p -> (java.awt.Color) p[1]));

    private final List<Color> intToColorOne = Arrays.asList(
            Color.lightGray, Color.green,
            Color.darkGray, Color.yellow);


    private BufferedWriter lw;
    private BufferedWriter cw;

    private BufferedReader lr;

    private BufferedReader cr;


    public Tiles() throws IOException {
        this.setLayout(new GridLayout(6, 5, 1, 1));
        for (int i = 0; i < tiles.length; i++) { //initialize the array
            for (int j = 0; j < tiles[i].length; j++) {
                tiles[i][j] = new Tile();
                this.add(tiles[i][j]);
            }
        }
        this.setSize(300, 500);
        possWords = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(
                    "/Users/pvpalleti/Downloads/hw09_local_temp/" +
                            "src/main/java/org/cis1200/wordle/sgb-words.txt"));
            String w = br.readLine();
            while (w != null) {
                possWords.add(w.toUpperCase());
                w = br.readLine();
            }
            br.close();


        } catch (FileNotFoundException e) {
            System.out.println("possible words file not found");
        } catch (IOException e) {
            System.out.println("IOException found");
        }

        random = new Random();

        wGu = possWords.get(random.nextInt(possWords.size()));

        wordToGuess = new ArrayList<Character>();
        for (Character c : wGu.toCharArray()) {
            wordToGuess.add(toUpperCase(c));
        }

        System.out.print(wordToGuess);





     /*   setFocusable(true);
        requestFocus(true);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
               if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    wordle.enterGuess();
                } else{
                System.out.println("hello");
                setFocusable(true);
                requestFocusInWindow();
                if (currCol < 5) {
                    Character newC = toUpperCase(e.getKeyChar());
                    tiles[currRow][currCol].setText(String.valueOf(newC));
                }

            }
        });
        requestFocus(true);
*/
    }

    public Tile getCell(int i, int j) {
        return tiles[i][j];
    }


    public String getAnswer() {
        return wGu;
    }


    public void changeWordToGuess() {
        random = new Random();
        String wordToFindString = possWords.get(random.nextInt(possWords.size()));
        wordToGuess = new ArrayList<Character>();
        for (Character c : wordToFindString.toCharArray()) {
            wordToGuess.add(toUpperCase(c));
        }
    }


    public int getRow() {
        return currRow;
    }

    public int getCol() {
        return currCol;
    }

    public void incrRow() {
        currRow++;
    }

    public void incrCol() {
        currCol++;
    }


    public boolean isValidWord(String s) {
        return possWords.contains(s);
    }


    private String[] accessCurrGuess(int i) {
        String[] currGuess = new String[5];
        for (int jp = 0; jp < tiles[i].length; jp++) {
            currGuess[jp] = tiles[i][jp].getText();

        }
        return currGuess;
    }

    public boolean enterGuess() throws IOException {
        if (currCol != 5 || currRow > 5) {
            return false;
        }

        String[] currGuess = accessCurrGuess(currRow);
        String ppp = String.join("", currGuess);
        int[] colorsToSave = new int[tiles[0].length];


        if (!isValidWord(ppp)) {
            JOptionPane.showMessageDialog(null, "Invalid Word");
            return false;
        } else {
            for (int j = 0; j < tiles[0].length; j++) {
                if (Objects.equals(currGuess[j], wordToGuess.get(j).toString())) {
                    tiles[currRow][j].colorChange("correct");
                    colorsToSave[j] = 1;
                } else if (wordToGuess.contains(currGuess[j].charAt(0))) {
                    tiles[currRow][j].colorChange("diffSpot");
                    colorsToSave[j] = 2;
                } else {
                    tiles[currRow][j].colorChange("incorrect");
                    colorsToSave[j] = 3;
                }
            }

            currRow++;
            currCol = 0;
        }
        return true;
    }


    public void saveGame(String i, int[] colorsToSave) throws IOException {
        try {
            lw.write(i + "\n");
            for (int colo : colorsToSave) {
                cw.write((colo) + ",");
            }
            cw.write("\n");
            lw.close();
            cw.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }

    }


    public void saveGame() throws IOException {
        try {
            new PrintWriter(LETTER_FILE).close();
            new PrintWriter(COLOR_FILE).close();
            System.out.println("File found");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        try {
            lw = new BufferedWriter(new FileWriter((LETTER_FILE), true));
            cw = new BufferedWriter(new FileWriter(COLOR_FILE, true));
            System.out.println("ppp1");

            for (Tile[] tile : tiles) {
                for (int c = 0; c < tiles[0].length; c++) {
                    System.out.println("ppp2");
                    String letter = tile[c].getText();
                    Color color = tile[c].getColorBackground();
                    int imp = intToColorOne.indexOf(color);
                    if (imp == 0) {
                        if (c == tiles[0].length - 1) {
                            System.out.println("ppp2");
                            lw.write(letter + "\n");
                            cw.write(0 + "\n");
                        } else {
                            System.out.println("ppp2");
                            lw.write(letter + ",");
                            cw.write(0 + ",");
                        }
                    } else {
                        if (c == tiles[0].length - 1) {
                            System.out.println("ppp2");
                            lw.write(letter + "\n");
                            cw.write(imp + "\n");
                        } else {
                            System.out.println("ppp2");
                            lw.write(letter + ",");
                            cw.write(imp + ",");
                        }
                    }
                }
                if (intToColorOne.indexOf(tile[0].getColorBackground()) == 0) {
                    for (int c = 0; c < tiles[0].length; c++) {
                        if (c == tiles[0].length - 1) {
                            System.out.println("wham");
                            cw.write(0 + "\n");
                        } else {
                            System.out.println("wham");
                            cw.write(0 + ",");
                        }
                    }
                }
            }

            System.out.print("ppp3");
            lw.write(getAnswer() + "\n");
            cw.close();
            lw.close();
        } catch (IOException e) {
            System.out.println("Failed saving game");
        }
    }


    public boolean loadGame() throws IOException {
        BufferedReader lrTemp = new BufferedReader(new FileReader(LETTER_FILE));
        if (lrTemp.readLine() == null) {
            return false;
        } else {
            lr = new BufferedReader(new FileReader(LETTER_FILE));
            cr = new BufferedReader(new FileReader(COLOR_FILE));
            for (int r = 0; r < tiles.length; r++) {
                String[] cv = cr.readLine().split(",");
                String[] bv = lr.readLine().split(",");
                for (int i = 0; i < cv.length; i++) {
                    if (Objects.equals(cv[i], "-1")) {
                        cv[i] = "0";
                    }
                }
                for (int c = 0; c < tiles[0].length; c++) {
                    tiles[r][c].setText(bv[c]);
                    tiles[r][c].setBackground(intToColorOne.
                            get(Integer.parseInt(cv[c])));
                    System.out.println("yep" + c);
                    if (!tiles[r][c].getText().equals(" ")) {
                        if (r < 5 && c >= 4) {
                            currCol = 0;
                            currRow++;
                        } else if (c >= 4) {
                            currCol = 4;
                        } else {
                            currCol = c + 1;
                        }
                    }
                }
            }
            wGu = lr.readLine();
            System.out.println("New Word:" + wGu);
            wordToGuess = new ArrayList<Character>();
            for (Character c : wGu.toCharArray()) {
                wordToGuess.add(toUpperCase(c));
            }
            lr.close();
            cr.close();
            for (int r = 0; r < tiles.length; r++) {
                for (int i = 0; i < tiles[0].length; i++) {
                    if (Objects.equals(tiles[r][i].getText(), "") ||
                            Objects.equals(tiles[r][i].getText(), "")) {
                        tiles[r][i].setBackground(intToColorOne.
                                get(0));
                    }
                }
            }


        }
        return true;
    }


    public boolean checkWin() {
        int v = currRow - 1;
        String[] currGuess = accessCurrGuess(v);

        for (int j = 0; j < currGuess.length; j++) {
            if (!Objects.equals(currGuess[j], wordToGuess.get(j).toString())) {
                return false;
            }
        }
        return true;

    }


    public void reset() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j] = new Tile();
            }
        }
        currCol = 0;
        currRow = 0;
    }


}

