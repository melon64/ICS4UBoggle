package ICS4UBoggle.src;

import java.io.*;
import java.util.*;

/**
 * Names: Adarsh P, Larris X, Felix X, and Hubert X
 * Date: June 7, 2022
 * Description: A program that contains a number of useful algorithms for the game of Boggle
 */

public class BoggleAlgorithms {
    /**
     * This method checks whether or not a specific word is found on the board.
     * 
     * @param board The letters that are currently on the board
     * @param word  The word to check for on the board
     * @return      An array list of integer arrays that represents the path required 
     *              to form the word - each array contains an x and a y coordinate; 
     *              returns an empty array list if the word isn't found
     */
    public static ArrayList<Integer[]> getWordPath(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    ArrayList<Integer[]> currWordPath = new ArrayList<Integer[]>();
                    currWordPath.add(new Integer[]{i, j});
                    ArrayList<Integer[]> wordPath = getWordPathFromPos(board, word.substring(1, word.length()), i, j, currWordPath);
                    if (wordPath.size() > 0) {
                        // If the program finds the rest of the word if its starts at a 
                        // specific position, return the path of letters used to form the word
                        return wordPath;
                    }
                }
            }
        }
        
        // If the program didn't find the word on the board yet, return an empty path
        return new ArrayList<Integer[]>();
    }
    
    /**
     * This method checks if a word can be formed if you start with a specific 
     * letter on the board.
     * 
     * This method does this by finding the next letter of the word in the adjacent 
     * cells and then recursively calling this function by looking for the next letter 
     * after that and so on until all the letters in the word have been found in one 
     * contiguous sequence.
     * 
     * @param board    The letters that are currently on the board excluding all 
     *                 letters that have already been used to form part of the word
     * @param word     The word to try to find on the board
     * @param currRow  The row which contains the first letter of the word
     * @param currCol  The column which contains the first letter of the word
     * @param wordPath The path of letters taken to form the word so far
     * @return         The path of letters starting from the start position needed to form 
     *                 the word; returns an empty array list if the word cannot be made 
     *                 from the start position
     */
    public static ArrayList<Integer[]> getWordPathFromPos(char[][] board, String word, int currRow, int currCol, ArrayList<Integer[]> wordPath) {
        if (word.length() == 0) {
            return wordPath;
        } else {
            ArrayList<Integer[]> moveList = getMoveList(board, currRow, currCol, word.charAt(0));
            for (Integer[] move: moveList) {
                // Create a copy of word called newWord and remove the first letter of 
                // newWord as this letter was just found
                String newWord = word.substring(1, word.length());
                
                // Create a copy of board called newBoard so that any edits made on 
                // newBoard do not change the original
                char[][] newBoard = clone2DCharArr(board);
                // Remove the letter that was just used to form part of the word from 
                // the board so that it isn't reused (as this would be illegal)
                newBoard[currRow][currCol] = ' ';
                
                ArrayList<Integer[]> newWordPath = cloneIntegerArrList(wordPath);
                newWordPath.add(new Integer[]{currRow + move[0], currCol + move[1]});
                // Make a recursive call to check if the rest of the word can be formed
                newWordPath = getWordPathFromPos(newBoard, newWord, currRow + move[0], currCol + move[1], newWordPath);
                if (newWordPath.size() > 0) {
                    return newWordPath;
                }
            }
            
            // If the program didn't find the word on the board from the starting 
            // position yet, return false
            return new ArrayList<Integer[]>();
        }
    }

    /**
     * This method finds all the possible words on a Boggle board
     *
     * @param board The letters that are currently on the board
     * @param dictionary The list of valid words to check for on the board
     * @param minLength The minimum length required for guessed words
     * @return      An array list of words that can be formed on the board
     *              returns an empty array list if the word isn't found
     */
    public static ArrayList<String> getAllWords(char[][] board, ArrayList<String> dictionary, int minLength) {
        boolean[][] visited = new boolean[board.length][board[0].length];
        String word = "";
        ArrayList<String> words = new ArrayList<String>();
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                traverseBoard(board, visited, minLength, word, i, j, words, dictionary);
            }
        }
        return words;
    }

    // Get all the possible prefixes at a certain length
    private static ArrayList<String> prefixes3 = getPrefixes("prefixes/prefixes3");
    private static ArrayList<String> prefixes4 = getPrefixes("prefixes/prefixes4");
    private static ArrayList<String> prefixes5 = getPrefixes("prefixes/prefixes5");
    private static ArrayList<String> prefixes6 = getPrefixes("prefixes/prefixes6");
    private static ArrayList<String> prefixes7 = getPrefixes("prefixes/prefixes7");
    private static ArrayList<String> prefixes8 = getPrefixes("prefixes/prefixes8");
    private static ArrayList<String> combinations = getPrefixes("combinations");

    /**
     * This method traverses the board and checks if the word formed on the current path is valid
     *
     * @param board The letters that are currently on the board
     * @param visited The grid cells that have already been visited on the path
     * @param minLength The minimum length required for guessed words
     * @param word The current word formed by the path
     * @param currRow The current row of the grid cell
     * @param currCol The current column of the grid cell
     * @param usedWords The list of valid words that has been found
     * @param dictionary The list of valid words to check for on the board
     */
    public static void traverseBoard(char[][] board, boolean[][] visited, int minLength, String word, int currRow, int currCol, ArrayList<String> usedWords, ArrayList<String> dictionary) {
        // Set current cell as visited
        visited[currRow][currCol] = true;

        // Concatenate the letter at the cell to end of the word
        word = word + board[currRow][currCol];
        
        // If the word exists in the dictionary, is not already in the list, and at least the minimum length
        if (getIdxOfWord(dictionary, word) != -1 && getIdxOfWord(usedWords, word) == -1 && word.length() >= minLength) {
            usedWords.add(word);
            // Sort the list to be able to use binary search
            Collections.sort(usedWords);
        }

        boolean prefix3Exists = (word.length() >= 3 && getIdxOfWord(prefixes3, word.substring(0, 3)) != -1);
        boolean prefix4Exists = (word.length() >= 4 && getIdxOfWord(prefixes4, word.substring(0, 4)) != -1);
        boolean prefix5Exists = (word.length() >= 5 && getIdxOfWord(prefixes5, word.substring(0, 5)) != -1);
        boolean prefix6Exists = (word.length() >= 6 && getIdxOfWord(prefixes6, word.substring(0, 6)) != -1);
        boolean prefix7Exists = (word.length() >= 7 && getIdxOfWord(prefixes7, word.substring(0, 7)) != -1);
        boolean prefix8Exists = (word.length() >= 8 && getIdxOfWord(prefixes8, word.substring(0, 8)) != -1);
        
        // Conditions to continue on the path: 
        // - the prefix at the current length possibly forms a word,  
        // - the two letter combinations that never form a word does not exist, 
        // - the length of the word does not exceed 10
        if (word.length() < 3 || prefix3Exists && !combinationExists(word, combinations) && word.length() <= 9) {
            if (word.length() < 4 || (prefix4Exists)) {
                if (word.length() < 5 || (prefix5Exists)) {
                    if (word.length() < 6 || (prefix6Exists)) {
                        if (word.length() < 7 || (prefix7Exists)) {
                            if (word.length() < 8 || (prefix8Exists)) {
                                // Recurse through each of the 8 adjacent cells
                                int[][] adjacentCells = {{currRow-1, currCol-1}, {currRow, currCol-1}, {currRow+1, currCol-1}, {currRow+1, currCol}, {currRow+1, currCol+1}, {currRow, currCol+1}, {currRow-1, currCol+1}, {currRow-1, currCol}};
                                for (int[] pos : adjacentCells) {
                                    // If the adjacent cell is within the bounds and not visited
                                    if (inBounds(board, pos[0], pos[1]) && !visited[pos[0]][pos[1]]) {
                                        traverseBoard(board, visited, minLength, word, pos[0], pos[1], usedWords, dictionary);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        word = "" + word.charAt(word.length()-1); // Remove the last character of the word
        visited[currRow][currCol] = false; // Set the current cell as unvisited
    }

    /**
     * This method checks whether a coordinate is within the bounds of the board
     * 
     * @param word The word to check for the combination within
     * @param dictionary The list of combinations of letters that can't exist in a word
     * @return    A boolean of whether the combination exists
     */
    public static boolean combinationExists(String word, ArrayList<String> combinations) {
        // If the length of the word is less than 2
        if (word.length() < 2) {
            return false; // The two letter combination cannot possibly exist
        }
        else {
            // Iterate through every two letter combination in the word
            for (int i = 0; i < word.length()-1; i++) {
                // If the combination exists in the list
                if (getIdxOfWord(combinations, word.substring(i, i+2)) != -1) {
                    return true; // Combination has been found
                }
            }
        }
        return false; // Combination has not been found
    }

    /**
     * This method checks whether a coordinate is within the bounds of the board
     * 
     * @param board The name of the file to be read from
     * @param x The x coordinate of the board
     * @param y The y coordinate of the board
     * @return    A boolean of whether the coordinates are within the bounds
     */
    public static boolean inBounds(char[][] board, int x, int y) {
        return !(x < 0 || y < 0 || x >= board.length || y >= board[0].length);
    }

    /**
     * This method reads the words from the files and stores them in a list
     * 
     * @param fileName The name of the file to be read from
     * @return  A list of all the prefixes
     */
    public static ArrayList<String> getPrefixes(String fileName) {
        ArrayList<String> prefixList = new ArrayList<String>();
        try {
            File file = new File("ICS4UBoggle/files/" + fileName + ".txt");
            Scanner in = new Scanner(file);
            String prefixes = "";

            while (in.hasNextLine()) {
                prefixes = in.nextLine();
            }
            Collections.addAll(prefixList, prefixes.split(" ")); 
            in.close();
        } catch (FileNotFoundException exception) {
            System.out.println(exception);
        }
        return prefixList;
    }

    /**
     * This method makes a deep copy of a given char array. This means that the method copies 
     * each individual value, not just the references to those values.
     * 
     * @param arr The array that needs to be cloned
     * @return    A deep copy of the char array
     */
    public static char[][] clone2DCharArr(char[][] arr) {
        char[][] newArr = new char[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                newArr[i][j] = arr[i][j];
            }
        }
        return newArr;
    }

    /**
     * This method makes a deep copy of a given array list of integer arrays. This means that 
     * the method copies each individual value, not just the references to those values.
     * 
     * @param arrList The array list that needs to be clones
     * @return        A deep copy of the array list of integer arrays
     */
    public static ArrayList<Integer[]> cloneIntegerArrList(ArrayList<Integer[]> arrList) {
        ArrayList<Integer[]> newArrList = new ArrayList<Integer[]>();
        for (int i = 0; i < arrList.size(); i++) {
            Integer[] intArr = new Integer[arrList.get(i).length];
            for (int j = 0; j < intArr.length; j++) {
                intArr[j] = arrList.get(i)[j];
            }
            newArrList.add(intArr);
        }
        return newArrList;
    }
    
    /**
     * This method finds all the different directions a specific letter can be found 
     * in given the starting coordinates.
     * 
     * @param board        The letters that are currently on the board excluding all 
     *                     letters that have already been used to form part of the word
     * @param currRow      The row where the last letter was found
     * @param currCol      The column where the last letter was found
     * @param letterToFind The next letter that needs to be found
     * @return             The list of all the different directions in which letterToFind 
     *                     can be found
     */
    public static ArrayList<Integer[]> getMoveList(char[][] board, int currRow, int currCol, char letterToFind) {
        ArrayList<Integer[]> moveList = new ArrayList<Integer[]>();
        
        for (int rowMove = -1; rowMove <= 1; rowMove++) {
            for (int colMove = -1; colMove <= 1; colMove++) {
                if (rowMove == 0 && colMove == 0) {
                    // You cannot use the letter in the same spot again - ensure that the 
                    // next letter is found in a different position than the current letter 
                    // - if not, ignore this move
                    continue;
                } else if (currRow + rowMove < 0 || currRow + rowMove >= board.length) {
                    // Ensure that the cell after the move is in a valid row (a row that 
                    // actually exists on the board) - if not, ignore this move
                    continue;
                } else if (currCol + colMove < 0 || currCol + colMove >= board[0].length) {
                    // Ensure that the cell after the move is in a valid row (a row that 
                    // actually exists on the board) - if not, ignore this move
                    continue;
                } else if (board[currRow + rowMove][currCol + colMove] == letterToFind) {
                    // If the cell that is being moved to contains the letter that needs to 
                    // be found, add this move to the list of valid moves
                    moveList.add(new Integer[]{rowMove, colMove});
                }
            }
        }

        return moveList;
    }
    
    /**
     * This method finds the index of word in dictionary using binary search. Any word that 
     * has already been used by either user will be removed from dictionary in the main loop 
     * so that it will no longer be considered valid.
     * 
     * @param dictionary An alphabetically-sorted list of all valid English words that haven't 
     *                   yet been guessed by either user
     * @param word       The word to look for in dictionary
     * @return           The index of the word in dictionary; return -1 if word isn't found
     */
    public static int getIdxOfWord(ArrayList<String> dictionary, String word) {
        // Return not found immediately if the list is empty
        if (dictionary.isEmpty()) {
            return -1;
        }

        // Mantain both a startIdx and an endIdx - these values represent the end bounds 
        // (inclusive) of the range in whcih the word may be found
        int startIdx = 0;
        int endIdx = dictionary.size() - 1;

        // Iterate until you have narrowed down the range to just two values
        while (endIdx - startIdx > 1) {
            // Use the floor function to calculate currIdx to get consistent behaviour
            int currIdx = (int)Math.floor((startIdx + endIdx) / 2);
            int comparison = dictionary.get(currIdx).compareToIgnoreCase(word);
            
            if (comparison == 0) {
                // If the index you are searcing contains the word, return that index
                return currIdx;
            } else if (comparison > 0) {
                // If the index you are searching has a word that comes after the target word 
                // alphabetically, continue searching all the words that come before that word
                endIdx = currIdx - 1;
            } else {
                // If the index you are searching has a word that comes before the target word 
                // alphabetically, continue searching all the words that come after that word
                startIdx = currIdx + 1;
            }
        }

        // After the loop has finished executing, search the remaining two indices for the word
        if (dictionary.get(startIdx).equalsIgnoreCase(word)) {
            // If the word is located at the first of the two indices, return that index
            return startIdx;
        } else if (dictionary.get(endIdx).equalsIgnoreCase(word)) {
            // If the word is located at the second of the two indices, return that index
            return endIdx;
        } else {
            // If the word is in neither of the two indices, it is not in the list at 
            // all, so return -1
            return -1;
        }
    }

    /**
     * This method calculates the number of points a word is worth given that the word is valid.
     * 
     * @param wordLength The length of the valid word
     * @return           The number of points the word is worth
     */
    public static int getScore(int wordLength) {
        if (wordLength >= 8) {
            return 11;
        } else if (wordLength >= 7) {
            return 5;
        } else if (wordLength >= 6) {
            return 3;
        } else if (wordLength >= 5) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * A method that reads in the dictionary from a text file
     * 
     * @return An array list containing strings of all the words in the dictionary
     */
    public static ArrayList<String> getDictionaryFromFile() {
        ArrayList<String> dictionary = new ArrayList<String>();
        try {
            File file = new File("ICS4UBoggle/files/dictionary.txt");
            Scanner in = new Scanner(file);

            while (in.hasNextLine()) {
                String nextLine = in.nextLine();
                dictionary.add(nextLine);
            }
            in.close();
        } catch (FileNotFoundException exception) {
            System.out.println(exception);
        }
        return dictionary;
    }
}