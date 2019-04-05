package nqueens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for the N-Queens problem program.
 * 
 * @author Nordryd
 */
public class Main
{
	// Toggle between being submission ready or still in development.
	private static final boolean IS_G2G_SUBMISSION_READY = false;
	private static final int SMALLEST_NON_SINGLETON_BOARD_SIZE_WITH_SOLUTION = 4;

	public static void main(String[] args) {
		Scanner stdin = IS_G2G_SUBMISSION_READY ? (new Scanner(System.in)) : null;
		int testCases = IS_G2G_SUBMISSION_READY ? stdin.nextInt() : Integer.parseInt(args[0]);

		for (int testCase = 1; testCase <= testCases; testCase++) {
			String solutions = "";

			boolean hasMoreSolutions = true;
			while (hasMoreSolutions) {
				int boardSize = IS_G2G_SUBMISSION_READY ? stdin.nextInt() : Integer.parseInt(args[testCase]);
				System.out.printf("Board Size = %d\n", boardSize);
				if (boardSize < SMALLEST_NON_SINGLETON_BOARD_SIZE_WITH_SOLUTION) {
					System.out.println("Only one known solution.");
					solutions = (boardSize == 1) ? "[1 ]" : "-1";
					hasMoreSolutions = false;
				}
				else {
					List<Integer> queens = nQueens(boardSize);
					if (queens.size() != boardSize) {
						System.out.println("End of solutions.");
						if (solutions.equals("")) { // Full pass and no solution has been added to the solutions string, there4 no
													// solution
							solutions = "-1";
						}

						hasMoreSolutions = false;
					}
					else {
						solutions += "[";
						for (Integer queen : queens) {
							solutions += queen + " ";
						}
						solutions += "] ";
					}
					break;
				}

				if (IS_G2G_SUBMISSION_READY) {
					stdin.close();
				}
			}

			System.out.println(solutions);
		}
	}

	private static List<Integer> nQueens(int boardSize) {
		int[][] board = new int[boardSize][boardSize];
		List<Integer> queens = new ArrayList<>();
		
		

		return queens;
	}
	
	private static List<Integer> placeNextQueen(int nextCol, int[][] board){
		
	}
	
	private static boolean isQueenVulnerable(int row, int col, int[][] board) {
		
	}
}
