import java.util.ArrayList;

public class Dictionary {
    ArrayList<String> dictionary = new ArrayList<>();

    public Dictionary() {

    }

    public boolean doesContainWord(String word) {
        return dictionary.contains(word);
    }
}
