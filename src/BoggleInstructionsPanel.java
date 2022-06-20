package ICS4UBoggle.src;

/**
 * Names: Adarsh P, Larris X, Felix X, and Hubert X
 * Date: June 10, 2022
 * Description: A program that creates a GUI that displays the rules of Boggle
 */

import javax.swing.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

public class BoggleInstructionsPanel extends JFrame {
    static final String instructionsFilepath = "ICS4UBoggle/files/boggle_rules.txt";
    
    JPanel mainPanel;
    JScrollPane scrollableMainPanel;
    ArrayList<JTextArea> text;

    public BoggleInstructionsPanel() {
        setSize(500, 500);
        setTitle("Boggle Instructions");
        setLocation(500, 0);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        // Add a white border around the frame to make it look cleaner
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        text = new ArrayList<JTextArea>();

        Font headerFont = new Font(Font.DIALOG, Font.BOLD, 20);
        
        try {
            // Read the instructions from the boggle_rules.txt file
            File file = new File(instructionsFilepath);
            Scanner in = new Scanner(file);

            while (in.hasNextLine()) {
                String nextLine = in.nextLine();
                if (nextLine.equals("")) {
                    JTextArea currHeader = new JTextArea(in.nextLine());
                    currHeader.setLineWrap(true);
                    currHeader.setWrapStyleWord(true);
                    // Make headers larger and bolder than body text
                    currHeader.setFont(headerFont);
                    text.add(currHeader);
                } else {
                    JTextArea currBodyText = new JTextArea(nextLine);
                    currBodyText.setLineWrap(true);
                    currBodyText.setWrapStyleWord(true);
                    text.add(currBodyText);
                }
            }

            in.close();
        } catch (FileNotFoundException e) {e.printStackTrace();}

        for (int i = 0; i < text.size(); i++) {
            if (text.get(i).getFont().equals(headerFont)) {
                // Add a lot of empty white space before every header to give it a clean look
                mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            } else {
                // Add a little bit of empty white space before every paragraph to give it a clean look
                mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
            mainPanel.add(text.get(i));
        }
        
        // Add a scrollbar to the mainPanel so that the user does not need to resize the frame
        scrollableMainPanel = new JScrollPane(mainPanel, 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.setAutoscrolls(true);
        scrollableMainPanel.setPreferredSize(new Dimension(800, 300));

        add(scrollableMainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
