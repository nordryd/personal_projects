package chess.nqueens;

import java.util.Scanner;

/**
 * Main class for the N-Queens problem program.
 * 
 * @author Nordryd
 */
public class Main
{
	private static final boolean USE_SCANNER_FOR_INPUT = false; // BC GfG uses Scanner and Eclipse uses args[]
	private static final int SMALLEST_NON_SINGLETON_BOARD_SIZE_WITH_SOLUTION = 4;
	private static final String NO_SOLUTION = "-1";
	private static final String SINGLETON_BOARD_SOLUTION = "[1 ]";
	
	private static String solutions;

	public static void main(String[] args) {
		Scanner stdin = USE_SCANNER_FOR_INPUT ? (new Scanner(System.in)) : null;
		int testCases = USE_SCANNER_FOR_INPUT ? stdin.nextInt() : Integer.parseInt(args[0]);

		for (int testCase = 1; testCase <= testCases; testCase++) {

			int boardSize = USE_SCANNER_FOR_INPUT ? stdin.nextInt() : Integer.parseInt(args[testCase]);
			System.out.printf("Board Size = %d\n", boardSize);
			if (boardSize < SMALLEST_NON_SINGLETON_BOARD_SIZE_WITH_SOLUTION) {
				System.out.println("Only one known solution.");
				solutions = (boardSize == 1) ? SINGLETON_BOARD_SOLUTION : NO_SOLUTION;
			}
			else {
				String queens = nQueens(boardSize);
				if (queens.length() == 0) {
					System.out.println(NO_SOLUTION);
				}
				else {
					// remember to increment the queen numbers (get rid of 0-indexing)
				}
			}
			
			System.out.println(solutions);
		}

		if (USE_SCANNER_FOR_INPUT) {
			stdin.close();
		}
	}

	private static String nQueens(int boardSize) {
		boolean[][] hasQueen = new boolean[boardSize][boardSize];
		String solutions = "", candidate = "";

		for (int nextFirstQueen = 0; nextFirstQueen < boardSize; nextFirstQueen++) {
			hasQueen[nextFirstQueen][0] = true;
			candidate += nextFirstQueen;
			candidate += placeNextQueen(1, hasQueen); // Next queen always starts at (0, 1) (first entry of next column)

			System.out.printf("candidate.size() = %d\n", candidate.length());

			if (candidate.length() == boardSize) {
				solutions += candidate + " ";
			}
		}

		System.out.println("Solutions = " + solutions);
		return solutions;
	}

	private static String placeNextQueen(int nextCol, boolean[][] hasQueen) {
		System.out.printf("Column = %d\n", nextCol);
		String queens = "";
		// Bottom of board reached (no solution) or end of board reached (solution
		// found)
		for (int nextRow = 0; nextRow < hasQueen.length; nextRow++) {
			if (nextRow == hasQueen.length){
				return queens;
			}
			
			if(nextRow == hasQueen.length) {
				solutions += queens + " ";
			}

			// Check if queen is safe. If yes, add queen and recurse.
			// Upon return, restore the board to its state before the recurse.
			// Keep a global string. i
			if (isQueenSafe(nextRow, nextCol, hasQueen)) { // Queen may be placed here. Reset row, next column
				hasQueen[nextRow][nextCol] = true;
				queens += placeNextQueen(nextCol + 1, hasQueen);
				
				hasQueen[nextRow][nextCol] = false;
			}

		}
		System.out.println(queens);
		return queens;
	}

	private static boolean isQueenSafe(int queenRow, int queenCol, boolean[][] hasQueen) {
		// Horizontal
		for (int col = 0; col < queenCol; col++) {
			if (hasQueen[queenRow][col]) {
				return false;
			}
		}

		// Vertical
		for (int row = 0; row < queenRow; row++) {
			if (hasQueen[row][queenCol]) {
				return false;
			}
		}

		// Down Diagonal
		int row = queenRow, col = queenCol;
		while ((row >= 0) && (col >= 0)) {
			if (hasQueen[row][col]) {
				return false;
			}
			row--;
			col--;
		}

		// Up Diagonal
		row = queenRow;
		col = queenCol;
		while ((row < hasQueen.length) && (col >= 0)) {
			if (hasQueen[row][col]) {
				return false;
			}
			row++;
			col--;
		}
		return true;
	}
}
