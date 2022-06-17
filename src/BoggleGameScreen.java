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
    JPanel wordsPanel;
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
    JLabel turnLabel;
    JLabel timerLabel;
    JLabel totalWordsLabel;
    JLabel wordResult;
    JLabel[] wordLengthsLabels;
    JLabel scoreLabel;
    JLabel scoreLabel2;
    JLabel tournamentScoreLabel;
    JLabel winner;
    JButton shakeUpBoard;
    JButton restartButton;

    private char[][] grid = new char[5][5];
    private JButton[][] board = new JButton[5][5];

    private int width = 1080, height = 960;
    private int score = 0;
    private int score2 = 0;
    private int passedTurns = 0;
    private int duration;
    private int minLength;
    
    private final ArrayList<String> usedWords = new ArrayList<String>();
    private final ArrayList<String> dictionary = getDictionaryFromFile();
    private final ArrayList<char[]> dice = readDiceDistribution();

    public BoggleGameScreen(String gameMode, int tournamentScore, int duration, int minLength, String track) {
        // ====================================
        // INITIAL SECTION
        // ====================================

        setTitle("Boggle Game Screen");
        setSize(width, height);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        new BoggleMusicPlayer(track);

        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        inputPanel.setPreferredSize(new Dimension(width, 100));
        inputPanel.setMaximumSize(new Dimension(width, 100));
        
        timerPanel = new JPanel();
        timerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        timerPanel.setMaximumSize(new Dimension(width, 50));

        wordResultPanel = new JPanel();
        wordResultPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        wordResultPanel.setPreferredSize(new Dimension(width, 140));
        wordResultPanel.setMaximumSize(new Dimension(width, 140));

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
        // MUSIC SECTION
        // ====================================

        // BoggleMusicPlayer musicPlayer = new BoggleMusicPlayer(track);

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
        submitButton2.setEnabled(false);
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

        turnLabel = new JLabel(playerIndication.getText() + "'s Turn", JLabel.CENTER);
        timerLabel = new JLabel("Time Left to Guess: " + duration, JLabel.CENTER);
        timerLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        turnLabel.setFont(defaultFont);
        turnLabel.setPreferredSize(new Dimension(width, 20));
        turnLabel.setMaximumSize(new Dimension(width, 20));
        // turnLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        timerLabel.setPreferredSize(new Dimension(width, 100));
        timerLabel.setMaximumSize(new Dimension(width, 100));
        // timerLabel.setBorder(BorderFactory.createLineBorder(Color.black, 0));
        this.duration = duration * 1000; // convert seconds to milliseconds
        startTimer();

        timerPanel.add(turnLabel);
        timerPanel.add(timerLabel);

        // ====================================
        // BOGGLE BOARD SECTION
        // ====================================

        createGrid(grid, board);

        // ====================================
        // WORD RESULT SECTION
        // ====================================

        int[] wordLengths = new int[maxDisplayedLength-minLength+1];
        wordLengthsLabels = new JLabel[maxDisplayedLength-minLength+1];
        ArrayList<String> possibleWords = BoggleAlgorithms.getAllWords(grid, dictionary, minLength);
        System.out.println(possibleWords.toString());
        int totalWords = possibleWords.size();
        
        wordResult = new JLabel("Click on the letters to form a word", JLabel.CENTER);
        totalWordsLabel = new JLabel("0/"+totalWords+" WORDS", JLabel.CENTER);
        wordResult.setFont(new Font("Dialog", Font.PLAIN, 20));
        wordResult.setPreferredSize(new Dimension(width, 50));
        totalWordsLabel.setPreferredSize(new Dimension(width, 50));
        totalWordsLabel.setFont(defaultFont);
        // totalWordsLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        wordResultPanel.add(wordResult);
        wordResultPanel.add(totalWordsLabel);
        assignWordLengths(possibleWords, wordLengths);

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
        shakeUpBoard = new JButton("Shake-up the board?");
        restartButton = new JButton("RESTART");

        winner.setFont(new Font("Dialog", Font.BOLD, 24));
        winner.setPreferredSize(new Dimension(width, 50));
        winner.setVisible(false);
        shakeUpBoard.setEnabled(false);
        
        shakeUpBoard.addActionListener(new ActionListener() {
            @Override
                public void actionPerformed(ActionEvent e) {
                // TODO - add sfx
                // reset the board
                createGrid(grid, board);
                shakeUpBoard.setEnabled(false);
            }
        });
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create warning with sfx
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
        outputPanel.add(shakeUpBoard);
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

            // mark whether the word has been accepted to decide if the turn should be changed
            boolean wordAccepted = false;

            if (wordGuessed.length() >= minLength && !usedWord) {
                // use getWordPath to check if the word exists in the maze
                ArrayList<Integer[]> path = BoggleAlgorithms.getWordPath(grid, wordGuessed);

                // use getIdxOfWord to check if the word is in the dictionary
                boolean isValidWord = BoggleAlgorithms.getIdxOfWord(dictionary, wordGuessed) != -1;

                if (!path.isEmpty() && isValidWord) {
                    wordAccepted = true;
                    usedWords.add(wordGuessed); // add the word to the list of used words
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

            // implement timer logic
            if (wordAccepted) {
                try {
                    Thread.sleep(1000);
                } 
                catch (InterruptedException ex) {
                    System.out.println(ex);
                }
                startTimer();
            }
        }
    };

    private int maxDisplayedLength = 8; // Boggle treats the words of length 8+ the same
    
    /**
     * This method assigns the amount of words on the board at each possible length
     * from minLength to maxDisplayedLength, inclusive
     *
     * @param words A list containing all the possible words
     * @param wordLengths An array that will contain how many words exist at each possible length
     */
    private void assignWordLengths(ArrayList<String> words, int[] wordLengths) {
        for (String word : words) {
            int index = word.length() - (maxDisplayedLength-wordLengths.length+1);
            wordLengths[index]++;
        }
        System.out.println(Arrays.toString(wordLengths));
        for (int i = 0; i < wordLengths.length; i++) {
            int currLength = maxDisplayedLength-wordLengths.length+i+1;
            wordLengthsLabels[i] = new JLabel(currLength + ": " + wordLengths[i], JLabel.CENTER);
            wordLengthsLabels[i].setFont(new Font("Dialog", Font.PLAIN, 16));
            wordLengthsLabels[i].setPreferredSize(new Dimension(width/wordLengths.length-20, 20));
            wordResultPanel.add(wordLengthsLabels[i]);
        }
    }

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
                if (passedTurns == 4) { // Both players have passed twice
                    passedTurns = 0;
                    // shake up the board
                    shakeUpBoard.setEnabled(true);
                }
                else {
                    passedTurns++;
        
                }
                changeTurn();
            }
            long elapsed = now - startTime;
            int secondsLeft = (int) ((duration - elapsed + 500) / 1000);
            timerLabel.setText("Time Left to Guess: " + secondsLeft);
        }
    });

    /**
     * This method alternates turns between the two players
     */
    private void changeTurn() {
        String currTurn = turnLabel.getText();
        String currPlayer = currTurn.substring(0, currTurn.length()-7);
        if (currPlayer.equals(playerIndication.getText())) {
            turnLabel.setText(playerIndication2.getText() + "'s Turn");
            submitButton.setEnabled(false);
            submitButton2.setEnabled(true);
        }
        else {
            turnLabel.setText(playerIndication.getText() + "'s Turn");
            submitButton.setEnabled(true);
            submitButton2.setEnabled(false);
        }
    }

    /**
     * This method starts a timer that indicates the amount of time left a player has to guess
     */
    private void startTimer() {
        if (!timer.isRunning()) {
            startTime = -1;
            timer.setInitialDelay(0);
            timer.start();
        }
        else {
            timer.stop();
            changeTurn();
        }
    }

    private ArrayList<int[]> path = new ArrayList<int[]>(); 

    /**
     * This method creates a grid of labels by choosing random dice and a random
     * face of
     * that die to place in every one of the 25 positions on the grid
     * 
     * @param grid A grid filled with characters that will contain the values of the board
     * @param board A grid filled with JButtons that will be edited by this method
     */
    private void createGrid(char[][] grid, JButton[][] board) {
        boardPanel.removeAll();
        Random rGen = new Random();
        ArrayList<char[]> diceCopy = (ArrayList<char[]>) dice.clone();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int randDie = rGen.nextInt(diceCopy.size());
                int randFace = rGen.nextInt(6);
                char letter = diceCopy.get(randDie)[randFace];

                grid[i][j] = letter;
                board[i][j] = new JButton("" + letter);
                board[i][j].setFont(new Font("Dialog", Font.BOLD, 24));
                board[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                board[i][j].setBackground(Color.WHITE);

                final int x = i, y = j;
                board[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int[] lastCoords = path.isEmpty() ? new int[]{0, 0} : path.get(path.size()-1);
                        if (board[x][y].getBackground() == Color.GREEN && x == lastCoords[0] && y == lastCoords[1]) {
                            board[x][y].setBackground(Color.WHITE);
                            path.remove(path.size()-1);
                        }
                        else if (board[x][y].getBackground() == Color.WHITE) {
                            if (path.isEmpty() || isAdjacent(x, y, lastCoords[0], lastCoords[1])) {
                                board[x][y].setBackground(Color.GREEN);
                                int[] coords = new int[]{x, y};
                                if (!path.contains(coords)) {
                                    path.add(coords);
                                }
                            }
                        }
                        // for (int[] p : path) {
                        //     System.out.print("[" + p[0] + ", " + p[1] + "], ");
                        // }
                        // System.out.println();
                    }
                });

                boardPanel.add(board[i][j]);
                diceCopy.remove(randDie);
            }
        }
        revalidate();
    }

    /**
     * This method checks if a cell is one of the 8 adjacent cells 
     * 
     * @param x The x coordinate of the current cell
     * @param y The y coordinate of the current cell
     * @param prevX The x coordinate of the previous cell
     * @param prevY The y coordinate of the previous cell
     * @return A boolean indicating if the coordinates are adjacent
     */
    public boolean isAdjacent(int x, int y, int prevX, int prevY) {
        boolean adjacents = 1 == ((x-prevX)*(x-prevX) + (y-prevY)*(y-prevY));
        boolean diagonals = Math.abs(x-prevX) == 1 && Math.abs(y-prevY) == 1;
        if (adjacents || diagonals) {
            return true;
        }
        return false;
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
