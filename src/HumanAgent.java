import java.util.Scanner;

public class HumanAgent extends Agent {

	static Scanner in = new Scanner(System.in);

	public HumanAgent(String name) {
		super(name);
	}

	@Override
	public void makeMove(Game game) {
		int row = 5, col;
		FourInRow fiarGame = (FourInRow) game;

		boolean first = true;

		do {
			if (first)
				System.out.println("Insert column number (0-6): ");
			else
				System.out.println("Invalid input! Insert column (0-6) again.");

			col = in.nextInt();
			first = false;

		} while (!fiarGame.isValidCell(col));

		for (int i = 5; i >= 0; i--) {
			if (fiarGame.board[i][col] == -1) {
				row = i;
				break;
			}
		}

		fiarGame.board[row][col] = role;
	}
}
