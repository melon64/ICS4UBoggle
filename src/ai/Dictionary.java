package ICS4UBoggle.src.ai;

import java.util.*;
import java.io.*;

public class Dictionary {
    ArrayList<String> dictionary = new ArrayList<>();

    public Dictionary() {
        readDictionaryFromFile();
    }

    private void readDictionaryFromFile() {
        try {
            File file = new File("ICS4UBoggle/files/dictionary.txt");
            Scanner in = new Scanner(file);

            while (in.hasNextLine()) {
                String nextLine = in.nextLine();
                nextLine = nextLine.trim();
                dictionary.add(nextLine);
            }
            in.close();
        } catch (FileNotFoundException exception) {
            System.out.println(exception);
        }
    }
}