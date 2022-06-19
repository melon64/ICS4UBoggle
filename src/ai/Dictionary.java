package ai;

import java.util.TreeSet;

public class Dictionary {
    TreeSet<String> dictionary = new TreeSet<>();

    public Dictionary() {
        dictionary.add("apple");
        dictionary.add("123450");
        dictionary.add("127bcdie0543");
        dictionary.add("17cio");
    }

    public boolean doesContainWord(String word) {
        return dictionary.contains(word);
    }
}