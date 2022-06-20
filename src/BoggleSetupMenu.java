package ICS4UBoggle.src;

/**
 * Names: Adarsh P, Larris X, Felix X, and Hubert X
 * Date: June 8, 2022
 * Description: A program that creates a GUI that allows the user to choose settings
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class BoggleSetupMenu extends JFrame {
    JPanel mainPanel;
    JPanel settingsPanel;
    JLabel modeLabel;
    JComboBox<String> modeChoiceBox;
    JLabel wordLengthLabel;
    JSlider wordLengthSlider;
    JLabel timerLabel;
    JSpinner timerSpinner;
    JLabel scoreLabel;
    JSpinner scoreSpinner;
    JLabel musicLabel;
    JComboBox<String> musicChoiceBox;
    JLabel computerModeLabel;
    JComboBox<String> computerModeChoiceBox;
    JLabel computerDifficultyLabel;
    JSlider computerDifficultySlider;
    JButton startButton;
    
    public BoggleSetupMenu() {
        // ====================================
        // INITIAL SECTION
        // ====================================
        
        setTitle("Boggle Setup Menu");
        setSize(400, 375);
        getContentPane().setBackground(BoggleGameScreen.LIGHT_BLUE);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BoggleGameScreen.LIGHT_BLUE);
        
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridBagLayout());
        settingsPanel.setBackground(BoggleGameScreen.LIGHT_BLUE);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        // Create the instruction panel
        BoggleInstructionsPanel instructionPanel = new BoggleInstructionsPanel();

        // ====================================
        // MODE SELECTION SECTION
        // ====================================
        
        modeLabel = new JLabel("Mode Choice:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.EAST;
        settingsPanel.add(modeLabel, constraints);

        modeChoiceBox = new JComboBox<String>(new String[]{"Single Player", "Two Player"});
        modeChoiceBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modeChoiceBox.getSelectedItem().equals("Two Player")) {
                    computerModeChoiceBox.setEnabled(false);
                    computerDifficultySlider.setEnabled(false);
                } else {
                    computerModeChoiceBox.setEnabled(true);
                    if (computerModeChoiceBox.getSelectedItem().equals("Adjustable")) {
                        computerDifficultySlider.setEnabled(true);
                    }
                }
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        settingsPanel.add(modeChoiceBox, constraints);

        // ====================================
        // TIMER SETTING SECTION
        // ====================================
        
        timerLabel = new JLabel("Length of Timer (Seconds):");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.EAST;
        settingsPanel.add(timerLabel, constraints);
        
        timerSpinner = new JSpinner(new SpinnerNumberModel(15, 5, null, 1));
        JFormattedTextField timerTextField = ((JSpinner.DefaultEditor)timerSpinner.getEditor()).getTextField();
        // Set the number of columns of scoreSpinner to 3 - this will allow you to fit 4-digit numbers
        timerTextField.setColumns(3);
        // Center the text in scoreSpinner
        timerTextField.setHorizontalAlignment(JTextField.CENTER);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        settingsPanel.add(timerSpinner, constraints);

        // ====================================
        // WORD LENGTH SETTING SECTION
        // ====================================
        
        wordLengthLabel = new JLabel("Minimum Word Length:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.EAST;
        settingsPanel.add(wordLengthLabel, constraints);
        
        // Create a slider which ranges from 3 to 6 and starts at 3
        wordLengthSlider = new JSlider(JSlider.HORIZONTAL, 3, 6, 3);
        wordLengthSlider.setBackground(BoggleGameScreen.LIGHT_BLUE);
        wordLengthSlider.setPreferredSize(new Dimension(100, 45));
        wordLengthSlider.setMajorTickSpacing(1);
        wordLengthSlider.setPaintTicks(true);
        wordLengthSlider.setPaintLabels(true);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.WEST;
        settingsPanel.add(wordLengthSlider, constraints);

        // ====================================
        // TOURNAMENT SCORE SETTING SECTION
        // ====================================
        
        scoreLabel = new JLabel("Tournament Score:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.EAST;
        settingsPanel.add(scoreLabel, constraints);

        scoreSpinner = new JSpinner(new SpinnerNumberModel(5, 1, null, 1));
        JFormattedTextField scoreTextField = ((JSpinner.DefaultEditor)scoreSpinner.getEditor()).getTextField();
        // Set the number of columns of scoreSpinner to 3 - this will allow you to fit 4-digit numbers
        scoreTextField.setColumns(3);
        // Center the text in scoreSpinner
        scoreTextField.setHorizontalAlignment(JTextField.CENTER);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.WEST;
        settingsPanel.add(scoreSpinner, constraints);

        // ====================================
        // MUSIC SELECTION SECTION
        // ====================================
        
        musicLabel = new JLabel("Music Choice:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.EAST;
        settingsPanel.add(musicLabel, constraints);

        musicChoiceBox = new JComboBox<String>(getMusicFileNames());
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.WEST;
        settingsPanel.add(musicChoiceBox, constraints);

        // ====================================
        // COMPUTER MODE SELECTION SECTION
        // ====================================
        
        computerModeLabel = new JLabel("Computer Mode:");
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.EAST;
        settingsPanel.add(computerModeLabel, constraints);

        computerModeChoiceBox = new JComboBox<String>(new String[]{"Adjustable", "Basic"});
        computerModeChoiceBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (computerModeChoiceBox.getSelectedItem().equals("Basic")) {
                    computerDifficultySlider.setEnabled(false);
                } else {
                    computerDifficultySlider.setEnabled(true);
                }
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.anchor = GridBagConstraints.WEST;
        settingsPanel.add(computerModeChoiceBox, constraints);
        
        // ====================================
        // COMPUTER DIFFICULTY SETTING SECTION
        // ====================================
        
        computerDifficultyLabel = new JLabel("Computer Difficulty:");
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.EAST;
        settingsPanel.add(computerDifficultyLabel, constraints);
        
        // Create a slider which ranges from 1 to 10 and starts at 5
        computerDifficultySlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
        computerDifficultySlider.setPreferredSize(new Dimension(150, 45));
        computerDifficultySlider.setMajorTickSpacing(1);
        computerDifficultySlider.setPaintTicks(true);
        computerDifficultySlider.setPaintLabels(true);
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.WEST;
        settingsPanel.add(computerDifficultySlider, constraints);

        // ====================================
        // START BUTTON SECTION
        // ====================================

        startButton = new JButton("Start Game!");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create the main game screen GUI when this button is clicked
                String gameMode = getGameMode();
                int tournamentScore = getTournamentScore();
                int duration = getTimerDuration();
                int minLength = getMinLength();
                String track = getTrack();
                String computerMode = getComputerMode();
                int computerDifficulty = getComputerDifficulty();
                dispose();
                instructionPanel.dispose();
                new BoggleGameScreen(gameMode, tournamentScore, duration, minLength, track, computerMode, computerDifficulty);
            }
        });

        // ====================================
        // FINALIZATION SECTION
        // ====================================
        
        mainPanel.add(settingsPanel);
        mainPanel.add(startButton);
        // Add empty white space at the bottom of the mainPanel to give it a clean look
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        add(mainPanel);

        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * This function gets the game mode
     * 
     * @return A string indicating the game mode
     */
    public String getGameMode() {
        return modeChoiceBox.getSelectedItem().toString();
    }

    /**
     * This function gets the tournament score
     * 
     * @return An integer representing the tournament score
     */
    public int getTournamentScore() {
        return (int) scoreSpinner.getValue();
    }

    /**
     * This function gets the timer duration
     * 
     * @return An integer representing the timer duration value
     */
    public int getTimerDuration() {
        return (int) timerSpinner.getValue();
    }

    /**
     * This function gets the minimum length of the word
     * 
     * @return An integer indicating the minimum length valid words must be
     */
    public int getMinLength() {
        return wordLengthSlider.getValue();
    }

    /**
     * This function gets the specified music track
     * 
     * @return A string of the track name
     */
    public String getTrack() {
        return musicChoiceBox.getSelectedItem().toString();
    }

    /**
     * This function gets the computer mode that was chosen
     * 
     * @return A string of the computer mode that was chosen
     */
    public String getComputerMode() {
        return computerModeChoiceBox.getSelectedItem().toString();
    }

    /**
     * This function gets the computer difficulty that was chosen
     * 
     * @return The computer difficulty integer that was chosen
     */
    public int getComputerDifficulty() {
        return computerDifficultySlider.getValue();
    }

    /**
     * This function gets a list of all the music files in the music directory
     * 
     * @return A list of all the files in the music directory
     */
    public static String[] getMusicFileNames() {
        File file = new File("ICS4UBoggle/audio");
        String[] fileArr = file.list();
        // The following is the extension that the program is looking for, so it 
        // will ignore all files that do not have this extension
        String expectedExtension = ".wav";

        ArrayList<String> musicList = new ArrayList<String>();
        // Add an option to not play music at all
        musicList.add("NONE");
        
        for (int i = 0; i < fileArr.length; i++) {
            // Only add the file to te list if it is a music file
            if (fileArr[i].substring(fileArr[i].length() - 4, fileArr[i].length()).equals(expectedExtension)) {
                musicList.add(fileArr[i].substring(0, fileArr[i].length() - expectedExtension.length()));
            }
        }
        
        // Convert the array list back into a string array before returning it
        return musicList.toArray(new String[musicList.size()]);
    }
}
