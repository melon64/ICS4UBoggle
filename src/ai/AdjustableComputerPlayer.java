package ICS4UBoggle.src.ai;

/**
 * Names: Adarsh P, Larris X, Felix X, and Hubert X
 * Date: June 20, 2022
 * Description: A program that contains the AI/Computer Player logic
 */

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
import ICS4UBoggle.src.BoggleAlgorithms;

public class AdjustableComputerPlayer {
    ArrayList<String> possibleWords;
    int computerDifficulty;
    ArrayList<String> usedWords;
    char[][] boggleBoard;

    public AdjustableComputerPlayer(ArrayList<String> possibleWords, int computerDifficulty, ArrayList<String> usedWords, char[][] boggleBoard) {
        this.possibleWords = BoggleAlgorithms.sortWordList(possibleWords);
        this.computerDifficulty = computerDifficulty;
        this.usedWords = usedWords;
        this.boggleBoard = boggleBoard;
    }

    /**
     * This method gets the path of a word on the board that the computer wants to guess.
     * 
     * @return The path of the word on the board
     */
    public ArrayList<int[]> getComputerWordPath() {
        String word = getWord();
        ArrayList<int[]> wordPath = BoggleAlgorithms.getWordPath(boggleBoard, word);
        ArrayList<int[]> newWordPath = new ArrayList<int[]>();
        for (int[] charCoordinates: wordPath) {
            newWordPath.add(new int[]{charCoordinates[1], charCoordinates[0]});
        }
        return newWordPath;
    }
    
    /**
     * This method randomly picks a word using gaussian distribution based on the 
     * difficulty level that the user chose.
     * 
     * @return The word that the computer will guess
     */
    public String getWord() {
        if (computerDifficulty == 10) {
            for (int i = possibleWords.size() - 1; i >= 0; i--) {
                String word = possibleWords.get(i);
                if (!usedWords.contains(word)) {
                    return word;
                }
            }
            return "";
        } else {
            String chosenWord;
            Random randNumGen = new Random();
            do {
                double gaussianMean = possibleWords.size() * computerDifficulty / 10;
                double gaussianStd = 5;
                int randIdx = (int)((randNumGen.nextGaussian() * gaussianStd) + gaussianMean);
                if (randIdx < 0 || randIdx >= possibleWords.size()) {
                    chosenWord = "";
                } else {
                    String wordAtIdx = possibleWords.get(randIdx);
                    if (usedWords.contains(wordAtIdx)) {
                        chosenWord = "";
                    } else {
                        chosenWord = wordAtIdx;
                    }
                }
            } while(chosenWord.length() == 0);
            
            return chosenWord;
        }
    }
}
