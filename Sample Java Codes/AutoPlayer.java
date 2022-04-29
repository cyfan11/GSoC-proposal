
import java.util.*;

public class AutoPlayer extends AbstractAutoPlayer {
    
    public List<String> findAllValidWords(BoggleBoard board, ILexicon lex) {
    	List<String> list = new ArrayList<String>();
    	boolean[][] visited = new boolean[board.size()][board.size()];
		for (int r = 0; r < board.size(); r++) {
			for (int c = 0; c < board.size(); c++) {
				findWords(board, lex, r, c, visited, list, "");
			}
		}
        return list;
    }
    
    public static void findWords(BoggleBoard board, ILexicon lex, int r, int c,
    		boolean[][] visited, List<String> list, String word) {
    	if(visited[r][c]) {
    		return;
    	}
    	
    	word = word + board.getFace(r,c);
    	
    	if(lex.wordStatus(word) == LexStatus.WORD) {
    		list.add(word);
    	}
    	
    	if(lex.wordStatus(word) == LexStatus.NOT_WORD) {
    		return;
    	}
    	visited[r][c] = true;
    	
    	int row, col;
		int[] x = { -1, -1, -1, 0, 0, 1, 1, 1 };
		int[] y = { -1, 0, 1, -1, 1, -1, 0, 1 };

		for (int pos = 0; pos < 8; pos++) {
			// initialize the starting point
			row = r + x[pos];
			col = c + y[pos];
			
			if (!(row >= board.size() || row < 0 || col >= board.size() || col < 0)) {
				findWords(board, lex, row, col, visited, list, word);
			}
		}
		visited[r][c] = false;
    
    }
}
