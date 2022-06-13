package ICS4UBoggle.src;

/**
 * Names: Adarsh P, Larris X, Felix X, and Hubert X
 * Date: June 8, 2022
 * Description: A program that creates the main game GUI for Boggle 
 */

import javax.swing.*;
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

    int score = 0;
    int score2 = 0;

    private final ArrayList<char[]> dice = readDiceDistribution();
    
    public BoggleGameScreen(String gameMode, int tournamentScore) {
        // ====================================
        // INITIAL SECTION
        // ====================================

        setTitle("Boggle Game Screen");
        setSize(1080, 960);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        inputPanel.setPreferredSize(new Dimension(1080, 50));
        // inputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        timerPanel = new JPanel();
        timerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        // timerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        wordResultPanel = new JPanel();
        wordResultPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        wordResultPanel.setMaximumSize(new Dimension(1080, 50));
        // wordResultPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(5, 5));
        // boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        boardPanel.setPreferredSize(new Dimension(1080, 300));

        scorePanel = new JPanel();
        scorePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        scorePanel.setPreferredSize(new Dimension(1080, 50));
        scorePanel.setMaximumSize(new Dimension(1080, 50));

        outputPanel = new JPanel();
        outputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));

        // ====================================
        // USER INPUT SECTION
        // ====================================

        ActionListener submitWord = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // search for the word inputed on the board
                
            }
        };
        
        // declare components for input
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

        timerLabel = new JLabel("INSERT TIMER HERE", JLabel.CENTER);

        timerPanel.add(timerLabel);

        // ====================================
        // WORD RESULT SECTION
        // ====================================

        wordResult = new JLabel("ex. AND is worth 6 points", JLabel.CENTER);
        wordResult.setFont(new Font("Dialog", Font.PLAIN, 20));
        wordResult.setPreferredSize(new Dimension(1080, 50));

        wordResultPanel.add(wordResult);

        // ====================================
        // BOGGLE BOARD SECTION
        // ====================================

        JLabel[][] board = new JLabel[5][5];
        createGrid(board);

        // ====================================
        // SCORE SECTION
        // ====================================

        scoreLabel = new JLabel("Player 1 Score: " + score, JLabel.CENTER);
        String playerScore = gameMode.equals("Single Player") ? "Computer Player Score: " : "Player 2 Score: ";
        scoreLabel2 = new JLabel(playerScore + score, JLabel.CENTER);
        tournamentScoreLabel = new JLabel("Score to Reach: " + tournamentScore, JLabel.CENTER);

        scoreLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        scoreLabel2.setFont(new Font("Dialog", Font.PLAIN, 18));
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
        winner.setPreferredSize(new Dimension(1080, 50));
        // winner.setVisible(false);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // reset all componenets
                createGrid(board);            
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
    
    private void createGrid(JLabel[][] grid) {
        boardPanel.removeAll();
        Random rGen = new Random();
        ArrayList<char[]> diceCopy = (ArrayList<char[]>)dice.clone();

    	for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
                int randDie = rGen.nextInt(diceCopy.size());
                int randFace = rGen.nextInt(6);
                
                grid[i][j] = new JLabel("" + diceCopy.get(randDie)[randFace], JLabel.CENTER);
                grid[i][j].setFont(new Font("Dialog", Font.BOLD, 24));
				grid[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				grid[i][j].setBackground(Color.WHITE);
				grid[i][j].setOpaque(true);
                
                boardPanel.add(grid[i][j]);
                diceCopy.remove(randDie);
			}
		}
    	revalidate();
    }

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
        } catch (FileNotFoundException exception) {};
        return diceList;
    }
}
