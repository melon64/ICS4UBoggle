package ICS4UBoggle.src;

/**
 * Names: Adarsh P, Larris X, Felix X, and Hubert X
 * Date: June 10, 2022
 * Description: A program that creates a GUI that displays the rules of Boggle and other help
 */

import javax.swing.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

public class BoggleHelpPanel extends JFrame {
    public BoggleHelpPanel() {
        setSize(500, 500);
        setTitle("Boggle Instructions");
        setLocation(500, 0);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Boggle Rules", getScrollPane("ICS4UBoggle/files/help/boggle_rules.txt"));
        tabs.add("Interface Guide", getScrollPane("ICS4UBoggle/files/help/interface_guide.txt"));
        tabs.add("Options List", getScrollPane("ICS4UBoggle/files/help/options_list.txt"));
        
        add(tabs);
        setVisible(true);
    }

    public JScrollPane getScrollPane(String filePath) {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        // Add a white border around the frame to make it look cleaner
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        ArrayList<JTextArea> text = new ArrayList<JTextArea>();

        Font headerFont = new Font(Font.DIALOG, Font.BOLD, 20);
        
        try {
            // Read the instructions from a file
            File file = new File(filePath);
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
                infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            } else {
                // Add a little bit of empty white space before every paragraph to give it a clean look
                infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
            infoPanel.add(text.get(i));
        }
        
        // Add a scrollbar to the infoPanel so that the user does not need to resize the frame
        JScrollPane scrollableMainPanel = new JScrollPane(infoPanel, 
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        infoPanel.setAutoscrolls(true);
        scrollableMainPanel.setPreferredSize(new Dimension(800, 300));
        return scrollableMainPanel;
    }
}
