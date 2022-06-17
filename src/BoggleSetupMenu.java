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
    JButton startButton;
    
    public BoggleSetupMenu() {
        // ====================================
        // INITIAL SECTION
        // ====================================
        
        setTitle("Boggle Setup Menu");
        setSize(325, 300);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);


        // ====================================
        // MODE SELECTION SECTION
        // ====================================
        
        modeLabel = new JLabel("Mode Choice:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.EAST;
        settingsPanel.add(modeLabel, constraints);

        modeChoiceBox = new JComboBox<String>(new String[]{"Single Player", "Two Player"});
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

        scoreSpinner = new JSpinner(new SpinnerNumberModel(20, 1, null, 1));
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
        // SUBMIT BUTTON SECTION
        // ====================================

        startButton = new JButton("Start Game!");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create the main game screen GUI when this button is clicked
                setVisible(false);
                String gameMode = getGameMode();
                int tournamentScore = getTournamentScore();
                int duration = getTimerDuration();
                int minLength = getMinLength();
                String track = getTrack();
                new BoggleGameScreen(gameMode, tournamentScore, duration, minLength, track);
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
     * This function gets a list of all the music files in the music directory
     * 
     * @return A list of all the files in the music directory
     */
    public static String[] getMusicFileNames() {
        File file = new File("ICS4UBoggle/audio");
        String[] fileArr = file.list();
        ArrayList<String> musicList = new ArrayList<String>();
        // The following is the extension that the program is looking for, so it 
        // will ignore all files that do not have this extension
        String expectedExtension = ".wav";

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
