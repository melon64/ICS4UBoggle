package ICS4UBoggle.src;

/**
 * Names: Adarsh P, Larris X, Felix X, and Hubert X
 * Date: June 8, 2022
 * Description: A program that creates the main game GUI for Boggle 
 */

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class BoggleGameScreen extends JFrame {
    JPanel inputPanel;
    JPanel timerPanel;
    JPanel wordResultPanel;
    JPanel boardPanel;
    JPanel scorePanel;
    JPanel outputPanel;
    JLabel playerIndication;
    JLabel playerIndication2;
    JLabel wordLabel;
    JLabel wordLabel2;
    JTextField wordInput;
    JTextField wordInput2;
    JButton submitButton;
    JButton submitButton2;
    JLabel timerLabel;
    JLabel wordResult;
    JLabel scoreLabel;
    JLabel scoreLabel2;
    JLabel tournamentScoreLabel;
    JLabel winner;
    JButton restartButton;

    private char[][] grid = new char[5][5];
    private JLabel[][] board = new JLabel[5][5];

    private int score = 0;
    private int score2 = 0;
    private int duration;
    private int minLength;
    
    private final ArrayList<String> usedWords = new ArrayList<String>();
    private final ArrayList<String> dictionary = getDictionaryFromFile();
    private final ArrayList<char[]> dice = readDiceDistribution();

    public BoggleGameScreen(String gameMode, int tournamentScore, int duration, int minLength) {
        // ====================================
        // INITIAL SECTION
        // ====================================

        int width = 1080, height = 960;
        setTitle("Boggle Game Screen");
        setSize(width, height);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        inputPanel.setPreferredSize(new Dimension(width, 50));

        timerPanel = new JPanel();
        timerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        timerPanel.setMaximumSize(new Dimension(width, 50));

        wordResultPanel = new JPanel();
        wordResultPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        wordResultPanel.setMaximumSize(new Dimension(width, 50));

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(5, 5));
        boardPanel.setPreferredSize(new Dimension(width, 300));

        scorePanel = new JPanel();
        scorePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        scorePanel.setPreferredSize(new Dimension(width, 50));
        scorePanel.setMaximumSize(new Dimension(width, 50));

        outputPanel = new JPanel();
        outputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));

        Font defaultFont = new Font("Dialog", Font.PLAIN, 18);

        // ====================================
        // USER INPUT SECTION
        // ====================================

        playerIndication = new JLabel("Player 1", JLabel.CENTER);
        wordLabel = new JLabel("Enter Word: ", JLabel.RIGHT);
        wordInput = new JTextField("", 8);
        submitButton = new JButton("GUESS");
        submitButton.addActionListener(submitWord);
        playerIndication2 = new JLabel("Player 2", JLabel.CENTER);
        wordLabel2 = new JLabel("Enter Word: ", JLabel.RIGHT);
        wordInput2 = new JTextField("", 8);
        submitButton2 = new JButton("GUESS");
        submitButton2.addActionListener(submitWord);
        this.minLength = minLength;

        wordLabel.setPreferredSize(new Dimension(200, 20));
        playerIndication.setPreferredSize(new Dimension(520, 50));
        wordLabel2.setPreferredSize(new Dimension(350, 20));
        playerIndication2.setPreferredSize(new Dimension(520, 50));

        // changes depending on mode
        if (gameMode.equals("Single Player")) {
            playerIndication2.setText("Computer Player");
            wordLabel2.setText("Computer's Word: ");
            wordInput2.setColumns(10);
            wordInput2.setEnabled(false);
            submitButton2.setVisible(false);
        }

        inputPanel.add(playerIndication);
        inputPanel.add(playerIndication2);
        inputPanel.add(wordLabel);
        inputPanel.add(wordInput);
        inputPanel.add(submitButton);
        inputPanel.add(wordLabel2);
        inputPanel.add(wordInput2);
        inputPanel.add(submitButton2);

        // ====================================
        // TIMER SECTION
        // ====================================

        timerLabel = new JLabel("Time Left to Guess: " + duration, JLabel.CENTER);
        timerLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        this.duration = duration * 1000; // convert seconds to milliseconds
        startTimer();

        timerPanel.add(timerLabel);

        // ====================================
        // WORD RESULT SECTION
        // ====================================

        wordResult = new JLabel("ex. AND is worth 6 points", JLabel.CENTER);
        wordResult.setFont(new Font("Dialog", Font.PLAIN, 20));
        wordResult.setPreferredSize(new Dimension(width, 50));
        wordResult.setVisible(false);

        wordResultPanel.add(wordResult);

        // ====================================
        // BOGGLE BOARD SECTION
        // ====================================

        createGrid(grid, board);

        // ====================================
        // SCORE SECTION
        // ====================================

        scoreLabel = new JLabel("Player 1 Score: " + score, JLabel.CENTER);
        String playerScore = gameMode.equals("Single Player") ? "Computer Player Score: " : "Player 2 Score: ";
        scoreLabel2 = new JLabel(playerScore + score2, JLabel.CENTER);
        tournamentScoreLabel = new JLabel("Score to Reach: " + tournamentScore, JLabel.CENTER);

        scoreLabel.setFont(defaultFont);
        scoreLabel2.setFont(defaultFont);
        tournamentScoreLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        scoreLabel.setPreferredSize(new Dimension(420, 50));
        scoreLabel2.setPreferredSize(new Dimension(400, 50));
        tournamentScoreLabel.setPreferredSize(new Dimension(200, 50));

        scorePanel.add(scoreLabel);
        scorePanel.add(tournamentScoreLabel);
        scorePanel.add(scoreLabel2);

        // ====================================
        // RESULTS SECTION
        // ====================================

        winner = new JLabel("The winner is ", JLabel.CENTER);
        restartButton = new JButton("RESTART");

        winner.setFont(new Font("Dialog", Font.BOLD, 24));
        winner.setPreferredSize(new Dimension(width, 50));
        winner.setVisible(false);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // reset all componenets
                createGrid(grid, board);
                score = 0;
                score2 = 0;
                scoreLabel.setText("Player 1 Score: " + score);
                scoreLabel2.setText(playerScore + score2);
                wordResult.setText("");
            }
        });

        outputPanel.add(winner);
        outputPanel.add(restartButton);

        // ====================================
        // FINALIZATION SECTION
        // ====================================

        add(inputPanel);
        add(timerPanel);
        add(wordResultPanel);
        add(boardPanel);
        add(scorePanel);
        add(outputPanel);
        // Add empty white space at the bottom of the mainPanel to give it a clean look
        add(Box.createRigidArea(new Dimension(0, 20)));

        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private ActionListener submitWord = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            wordResult.setVisible(true);

            // check which player has guessed
            boolean isPlayerOne = e.getSource() == submitButton;

            // get the word inputed through the text field
            String wordGuessed = isPlayerOne ? wordInput.getText().toUpperCase() : wordInput2.getText().toUpperCase();
            
            // check whether word has already been used
            boolean usedWord = usedWords.size() > 0 ? BoggleAlgorithms.getIdxOfWord(usedWords, wordGuessed) != -1 : false;

            if (wordGuessed.length() >= minLength && !usedWord) {
                // use getWordPath to check if the word exists in the maze
                ArrayList<Integer[]> path = BoggleAlgorithms.getWordPath(grid, wordGuessed);

                // use getIdxOfWord to check if the word is in the dictionary
                boolean isValidWord = BoggleAlgorithms.getIdxOfWord(dictionary, wordGuessed.toLowerCase()) != -1;
                usedWords.add(wordGuessed); // add the word to the list of used words

                if (!path.isEmpty() && isValidWord) {
                    int points = BoggleAlgorithms.getScore(wordGuessed.length());
                    wordResult.setText(wordGuessed + " is worth " + points + " points");
                    if (isPlayerOne) {
                        score += points;
                        scoreLabel.setText("Player 1 Score: " + score);
                    } 
                    else {
                        score2 += points;
                        scoreLabel2.setText("Player 2 Score: " + score);
                    }
                }
                else if (isValidWord) {
                    wordResult.setText(wordGuessed + " is not on the board");
                }
                else {
                    wordResult.setText(wordGuessed + " is not a valid word");
                }
            }
            else {
                if (usedWord) {
                    wordResult.setText(wordGuessed + " has already been used");
                }
                else {
                    wordResult.setText(wordGuessed + " is less than the minimum length of " + minLength);
                }
            }
            wordInput.setText("");
            wordInput2.setText("");

            // TODO - implement timer logic
            // try {
            // Thread.sleep(2000);
            // } catch (InterruptedException ex) {
            // System.out.println(ex);
            // }
            // startTimer();
        }
    };

    private long startTime = -1;

    private Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if (startTime < 0) {
                startTime = System.currentTimeMillis();
            }
            long now = System.currentTimeMillis();
            long clockTime = now - startTime;
            if (clockTime >= duration) {
                clockTime = duration;
                timer.stop();
            }
            long elapsed = now - startTime;
            int secondsLeft = (int) ((duration - elapsed + 500) / 1000);
            timerLabel.setText("Time Left to Guess: " + secondsLeft);
        }
    });

    /**
     * This method starts a timer that indicates the amount of time left a player has to guess
     */
    private void startTimer() {
        if (!timer.isRunning()) {
            startTime = -1;
            timer.setInitialDelay(0);
            timer.start();
        }
    }

    /**
     * This method creates a grid of labels by choosing random dice and a random
     * face of
     * that die to place in every one of the 25 positions on the grid
     * 
     * @param grid A grid filled with characters that will contain the values of the board
     * @param board A grid filled with JLabels that will be edited by this method
     */
    private void createGrid(char[][] grid, JLabel[][] board) {
        boardPanel.removeAll();
        Random rGen = new Random();
        ArrayList<char[]> diceCopy = (ArrayList<char[]>) dice.clone();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int randDie = rGen.nextInt(diceCopy.size());
                int randFace = rGen.nextInt(6);
                char letter = diceCopy.get(randDie)[randFace];

                grid[i][j] = letter;
                board[i][j] = new JLabel("" + letter, JLabel.CENTER);
                board[i][j].setFont(new Font("Dialog", Font.BOLD, 24));
                board[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                board[i][j].setBackground(Color.WHITE);
                board[i][j].setOpaque(true);

                boardPanel.add(board[i][j]);
                diceCopy.remove(randDie);
            }
        }
        revalidate();
    }

    /**
     * A method that reads in the dice distributions from a text file
     * 
     * @return An array list containing character arrays with the faces of every die
     */
    private ArrayList<char[]> readDiceDistribution() {
        ArrayList<char[]> diceList = new ArrayList<char[]>();
        try {
            File file = new File("ICS4UBoggle/files/dice_distribution.txt");
            Scanner in = new Scanner(file);

            while (in.hasNextLine()) {
                String nextLine = in.nextLine();
                char[] dieFaces = new char[6];
                for (int i = 0; i < dieFaces.length; i++) {
                    dieFaces[i] = nextLine.charAt(i);
                }
                diceList.add(dieFaces);
            }
            in.close();
        } catch (FileNotFoundException exception) {
            System.out.println(exception);
        }
        return diceList;
    }

    /**
     * A method that reads in the dictionary from a text file
     * 
     * @return An array list containing strings of all the words in the dictionary
     */
    private ArrayList<String> getDictionaryFromFile() {
        ArrayList<String> dictionary = new ArrayList<String>();
        try {
            File file = new File("ICS4UBoggle/files/dictionary.txt");
            Scanner in = new Scanner(file);

            while (in.hasNextLine()) {
                String nextLine = in.nextLine();
                dictionary.add(nextLine);
            }
            in.close();
        } catch (FileNotFoundException exception) {
            System.out.println(exception);
        }
        return dictionary;
    }
}
