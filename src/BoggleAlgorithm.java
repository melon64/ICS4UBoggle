package ICS4UBoggle.src;

/**
 * Names: Adarsh P, Larris X, Felix X, and Hubert X
 * Date: June 7, 2022
 * Description: A program that contains a number of useful algorithms for the game of Boggle
 */

import java.util.ArrayList;

public class BoggleAlgorithm {
    /**
     * This method checks whether or not a specific word is found on the board.
     * 
     * @param board The letters that are currently on the board
     * @param word  The word to check for on the board
     * @return      A boolean that contains whether or not the word is on the board
     */
    public static boolean isWordOnBoard(char[][] board, String word) {        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (canFormWordFromPos(board, word.substring(1, word.length()), i, j)) {
                        return true;
                    }
                }
            }
        }
        
        // If the program didn't find the word on the board yet, return false
        return false;
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
     * @param board   The letters that are currently on the board excluding all 
     *                letters that have already been used to form part of the word
     * @param word    The word to try to find on the board
     * @param currRow The row which contains the first letter of the word
     * @param currCol The column which contains the first letter of the word
     * @return        A boolean that contains whether or not the word can be created if 
     *                you start at the specific position
     */
    public static boolean canFormWordFromPos(char[][] board, String word, int currRow, int currCol) {
        if (word.length() == 0) {
            return true;
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
                
                // Make a recursive call to check if the rest of the word can be formed
                if (canFormWordFromPos(newBoard, newWord, currRow + move[0], currCol + move[1])) {
                    return true;
                }
            }
            
            // If the program didn't find the word on the board from the starting 
            // position yet, return false
            return false;
        }
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
}