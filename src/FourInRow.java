public class FourInRow extends Game {
	public int[][] board;

	public FourInRow(Agent a, Agent b) {
		super(a, b);

		a.setRole(0);
		b.setRole(1);
		name = "4 In A Row";
		board = new int[6][7];

	}

	@Override
	boolean isFinished() {
		if (checkForWin() != -1)
			return true;
		else if (isBoardFull())
			return true;
		else
			return false;
	}

	@Override
	void initialize(boolean fromFile) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = -1;
			}
		}
	}

	@Override
	void showGameState() {

		System.out.println("-----------------------------");
		for (int i = 0; i < 6; i++) {
			System.out.print("| ");
			for (int j = 0; j < 7; j++) {
				if (board[i][j] == -1)
					System.out.print(" " + " | ");
				else if (board[i][j] == 0)
					System.out.print("O | ");
				else
					System.out.print("X | ");
			}
			System.out.println();
			System.out.println("-----------------------------");
		}
	}

	public boolean isBoardFull() {

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (board[i][j] == -1) {
					return false;
				}
			}
		}

		return true;
	}

	public int checkForWin() {
		winner = null;
		int winRole = -1;
		// row
		for (int i = 5; i >= 0; i--) {
			for (int j = 0; j < 4; j++)
				if (checkRowCol(board[i][j], board[i][j + 1], board[i][j + 2], board[i][j + 3]) == true) {
					winRole = board[i][j];
				}
		}

		// column
		for (int i = 5; i >= 3; i--) {

			for (int j = 0; j < 7; j++)
				if (checkRowCol(board[i][j], board[i - 1][j], board[i - 2][j], board[i - 3][j]) == true) {
					winRole = board[i][j];
				}
		}

		// diagonal
		for (int i = 5; i >= 3; i--) {
			for (int j = 0; j < 4; j++)
				if (checkRowCol(board[i][j], board[i - 1][j + 1], board[i - 2][j + 2], board[i - 3][j + 3]) == true) {
					winRole = board[i][j];
				}
		}
		for (int i = 5; i >= 3; i--) {
			for (int j = 6; j >= 3; j--)
				if (checkRowCol(board[i][j], board[i - 1][j - 1], board[i - 2][j - 2], board[i - 3][j - 3]) == true) {
					winRole = board[i][j];
				}
		}

		if (winRole != -1) {
			winner = agent[winRole];
		}
		return winRole;
	}

	// Check to see if all three values are the same (and not empty) indicating a
	// win.
	private boolean checkRowCol(int c1, int c2, int c3, int c4) {

		return ((c1 != -1) && (c1 == c2) && (c2 == c3) && (c3 == c4));
	}

	public boolean isValidCell(int col) {
		if (col < 0 || col > 6)
			return false;

		for (int i = 5; i >= 0; i--) {
			if (board[i][col] == -1)
				return true;
		}
		return false;
	}

}
