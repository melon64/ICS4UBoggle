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
    JLabel score;
    JLabel score2;
    JLabel winner;
    JButton restartButton;
    
    public BoggleGameScreen(String gameMode) {
        // ====================================
        // INITIAL SECTION
        // ====================================
        
        setTitle("Boggle Game Screen");
        setSize(1080, 720);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        timerPanel = new JPanel();
        timerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(5, 5));

        scorePanel = new JPanel();
        scorePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        outputPanel = new JPanel();
        outputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

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
        playerIndication.setPreferredSize(new Dimension(520, 50));
//        playerIndication.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        wordLabel = new JLabel("Enter Word: ", JLabel.RIGHT);
        wordLabel.setPreferredSize(new Dimension(200, 20));
        wordInput = new JTextField("", 8);
        submitButton = new JButton("GUESS");
        submitButton.addActionListener(submitWord);
        playerIndication2 = new JLabel("Player 2", JLabel.CENTER);
        playerIndication2.setPreferredSize(new Dimension(520, 50));
//        playerIndication2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        wordLabel2 = new JLabel("Enter Word: ", JLabel.RIGHT);
        wordLabel2.setPreferredSize(new Dimension(350, 20));
        wordInput2 = new JTextField("", 8);
        submitButton2 = new JButton("GUESS");
        submitButton2.addActionListener(submitWord);
        
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
        


        // ====================================
        // BOGGLE BOARD SECTION
        // ====================================
        
        JLabel[][] board = new JLabel[5][5];
        createGrid(board);
        boardPanel.setPreferredSize(new Dimension(1080, 400));

        // ====================================
        // SCORE SECTION
        // ====================================
        
        // ====================================
        // RESULTS SECTION
        // ====================================
        JLabel winner = new JLabel();
        restartButton = new JButton("RESTART");
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
        add(boardPanel);
        add(scorePanel);
        add(outputPanel);
        // Add empty white space at the bottom of the mainPanel to give it a clean look
        add(Box.createRigidArea(new Dimension(0, 20)));

        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private final char[][] dice = {{'A','A','A','F','R','S'}, {'A','A','E','E','E','E'}, {'A','A','F','I','R','S'}, {'A','D','E','N','N','N'}, {'A','E','E','E','E','M'}, {'A','E','E','G','M','U'}, 
    		{'A','E','G','M','N','N'}, {'A','F','I','R','S','Y'}, {'B','J','K','Q','X','Z'}, {'C','C','N','S','T','W'}, {'C','E','I','I','L','T'}, {'C','E','I','L','P','T'}, {'C','E','I','P','S','T'},
    		{'D','D','L','N','O','R'}, {'D','H','H','L','O','R'}, {'D','H','H','N','O','T'}, {'D','H','L','N','O','R'}, {'E','I','I','I','T','T'}, {'E','M','O','T','T','T'}, {'E','N','S','S','S','U'},
    		{'F','I','P','R','S','Y'}, {'G','O','R','R','V','W'}, {'H','I','P','R','R','Y'}, {'N','O','O','T','U','W'}, {'O','O','O','T','T','U'}};
    
    private void createGrid(JLabel[][] grid) {
    	boardPanel.removeAll();
    	Random rGen = new Random();
    	int diceCount = 0;
    	for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				int randNum = rGen.nextInt(6);
				grid[i][j] = new JLabel(""+dice[diceCount][randNum], JLabel.CENTER);
				grid[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				grid[i][j].setBackground(Color.WHITE);
				grid[i][j].setOpaque(true);
				boardPanel.add(grid[i][j]);
				diceCount++;
			}
		}
    	revalidate();
    }
}
