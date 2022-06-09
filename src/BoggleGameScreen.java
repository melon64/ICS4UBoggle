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
    
    public BoggleGameScreen() {
        // ====================================
        // INITIAL SECTION
        // ====================================
        
        setTitle("Boggle Game Screen");
        setSize(1080, 720);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

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

        playerIndication = new JLabel("Player 1", JLabel.LEFT);
        // check which mode
        playerIndication2 = new JLabel("Player 2", JLabel.LEFT);
        wordLabel = new JLabel("Enter word: ", JLabel.LEFT);
        // depends on which mode "Computer's word: "
        wordLabel2 = new JLabel("Enter word: ", JLabel.LEFT);
        wordInput = new JTextField("", 8);
        wordInput2 = new JTextField("", 8);
        submitButton = new JButton("GUESS");
        submitButton2 = new JButton("GUESS");

        submitButton.addActionListener(submitWord);
        submitButton2.addActionListener(submitWord);
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
        

        // ====================================
        // SCORE SECTION
        // ====================================
        
        // ====================================
        // RESULTS SECTION
        // ====================================

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
}
