import java.util.ArrayList;
import java.util.List;

/** This class implements the cellsForward method of the IWordOnBoardFinder interface.
 * It checks whether a word (and where) a given word occurs on the board.
 * 
  * @author Cynthia Fan
 * */
public class WordOnBoardFinder implements IWordOnBoardFinder {

    /**
     * Validating whether (and where) a given word occurs on the board. 
     * @param board - current Boggle Board
     * @param word - word input by human player
     * @return true if the word can be created correctly according to Boggle Board or false otherwise
     */
    public List<BoardCell> cellsForWord(BoggleBoard board, String word) {
		// create list of cells visited
		List<BoardCell> list = new ArrayList<BoardCell>();
		for (int r = 0; r < board.size(); r++) {
			for (int c = 0; c < board.size(); c++) {
				if (searchWord(board, r, c, word, 0, list)) {
					return list;
				}
			}
		}
		return new ArrayList<BoardCell>();
	}
    /**
     * Validating whether (and where) a given word occurs on the board. 
     * @param board - current Boggle Board
	 * @param r - row index
	 * @param c - col index
     * @param word - word input by human player
	 * @param index - character position of the word input
	 * @param list - arraylist containing BoardCell objects
     * @return true if the word can be created correctly according to Boggle Board or false otherwise
     */

    public boolean searchWord(BoggleBoard board, int r, int c, String word, int index, List<BoardCell> list) {

		// base cases
		if (list.contains(new BoardCell(r, c))) {
			return false;
		}
		
		if (index == word.length()){
			return true;
		}

		String letter="";
		if(index < word.length()){
			if (word.charAt(index) == 'q' && word.charAt(index+1) == 'u') {
				letter = "qu";
				index++;
			}
			else {
				letter = ""+ word.charAt(index);
			}

		}


		if (board.getFace(r, c).equals(letter)) {
			list.add(new BoardCell(r, c));

			int row, col;
			int[] x = { -1, -1, -1, 0, 0, 1, 1, 1 };
			int[] y = { -1, 0, 1, -1, 1, -1, 0, 1 };

			for (int pos = 0; pos < 8; pos++) {
				// initialize the starting point
				row = r + x[pos];
				col = c + y[pos];
				
				if (row >= board.size() || row < 0 || col >= board.size() || col < 0) {
					continue;
					
				}
				//the recursive call
				if(searchWord(board, row, col, word, index+1, list)){
					return true;
                }
			}
			//deleting the last BoardCell from list if all the neighbors fail
			for (int i = 0; i< list.size() ; i++) {
				if (i==list.size()-1) {
					list.remove(i);
				}
			}
		}
		return false;
    }
}






        