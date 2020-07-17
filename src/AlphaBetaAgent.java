public class AlphaBetaAgent extends Agent {
	int depth;
	float alpha, beta;

	public AlphaBetaAgent(String name, int mode) {
		super(name);
		alpha = -100;
		beta = 100;

		if (mode == 1)
			depth = 1;
		if (mode == 2)
			depth = 2;
		else
			depth = 7;
	}

	@Override
	public void makeMove(Game game) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		FourInRow fir_game = (FourInRow) game;
		CellValueTuple best = alphaValue(fir_game, alpha, beta, depth);

		if (best.col != -1) {
			fir_game.board[best.row][best.col] = role;
		}

	}

	private CellValueTuple alphaValue(FourInRow game, float alpha, float beta, int depth) {
		CellValueTuple maxCVT = new CellValueTuple();
		maxCVT.utility = -100;

		int winner = game.checkForWin();

		// terminal check
		if (winner == role) {
			maxCVT.utility = 1; // agent wins
			return maxCVT;
		} else if (winner != -1) {
			maxCVT.utility = -1; // opponent wins
			return maxCVT;
		} else if (game.isBoardFull()) {
			maxCVT.utility = 0; // draw
			return maxCVT;
		}

		if (depth == 0) {
			maxCVT.utility = evaluate(game.board);
			return maxCVT;
		}

		for (int i = 0; i < 7; i++) {
			int row = -1;
			for (int j = 5; j >= 0; j--) {
				if (game.board[j][i] != -1)
					continue;
				game.board[j][i] = role; // temporarily making a move
				row = j;
				break;
			}
			if (row == -1)
				continue;
			float v = betaValue(game, alpha, beta, depth - 1).utility;
			if (v > maxCVT.utility) {
				maxCVT.utility = v;
				maxCVT.row = row;
				maxCVT.col = i;
			}
			game.board[row][i] = -1; // reverting back to original state
			
			if (maxCVT.utility >= beta) {
				return maxCVT;
			}
			alpha = Math.max(alpha, maxCVT.utility);
			if (maxCVT.utility == 1)
				return maxCVT;
		}
		return maxCVT;

	}

	private CellValueTuple betaValue(FourInRow game, float alpha, float beta, int depth) {
		CellValueTuple minCVT = new CellValueTuple();
		minCVT.utility = 100;

		int winner = game.checkForWin();

		// terminal check
		if (winner == role) {
			minCVT.utility = 1; // max wins
			return minCVT;
		} else if (winner != -1) {
			minCVT.utility = -1; // min wins
			return minCVT;
		} else if (game.isBoardFull()) {
			minCVT.utility = 0; // draw
			return minCVT;
		}

		if (depth == 0) {
			minCVT.utility = evaluate(game.board);
			return minCVT;
		}

		for (int i = 0; i < 7; i++) {
			int row = -1;
			for (int j = 5; j >= 0; j--) {
				if (game.board[j][i] != -1)
					continue;
				game.board[j][i] = minRole(); // temporarily making a move
				row = j;
				break;
			}
			if (row == -1)
				continue;
			float v = alphaValue(game, alpha, beta, depth - 1).utility;
			if (v < minCVT.utility) {
				minCVT.utility = v;
				minCVT.row = row;
				minCVT.col = i;
			}
			game.board[row][i] = -1; // reverting back to original state

			if (minCVT.utility <= alpha) {
				return minCVT;
			}
			beta = Math.min(beta, minCVT.utility);
			if (minCVT.utility == -1)
				return minCVT;
		}
		return minCVT;
	}

	private int minRole() {
		if (role == 0)
			return 1;
		else
			return 0;
	}

	private float evaluate(int[][] board) {
		int count_0 = 0, count_1 = 0;

		for (int i = 0; i < 7; i++) {
			int row = -1, col = -1;
			for (int j = 5; j >= 0; j--) {
				if (board[j][i] != -1)
					continue;
				row = j;
				col = i;
				// diagonal
				if ((j + 3) < 6) {
					if ((i - 3) >= 0) {
						if ((board[j + 1][i - 1] == board[j + 2][i - 2])
								&& (board[j + 2][i - 2] == board[j + 3][i - 3])) {
							// count
							if (board[j + 1][i - 1] == 0) {
								count_0++;
							} else {
								count_1++;
							}
						}

					}
					if ((i + 3) < 7) {
						if ((board[j + 1][i + 1] == board[j + 2][i + 2])
								&& (board[j + 2][i + 2] == board[j + 3][i + 3])) {
							// count
							if (board[j + 1][i + 1] == 0) {
								count_0++;
							} else {
								count_1++;
							}
						}

					}
				}
				// col
				if ((j + 3) < 6) {
					if ((board[j + 1][i] == board[j + 2][i]) && (board[j + 2][i] == board[j + 3][i])) {
						// count
						if (board[j + 1][i] == 0) {
							count_0++;
						} else {
							count_1++;
						}
					}
				}
				// row
				if ((i - 3) >= 0) {
					if ((board[j][i - 1] == board[j][i - 2]) && (board[j][i - 2] == board[j][i - 3])) {
						// count
						if (board[j][i - 1] == 0) {
							count_0++;
						} else {
							count_1++;
						}
					}
				}
				if ((i + 3) < 7) {
					if ((board[j][i + 1] == board[j][i + 2]) && (board[j][i + 2] == board[j][i + 3])) {
						// count
						if (board[j][i + 1] == 0) {
							count_0++;
						} else {
							count_1++;
						}
					}
				}
				break;
			}

		}
		if (((count_0 > count_1) && role == 0) || ((count_0 < count_1) && role == 1))
			return (float) 0.8;
		else if (((count_0 > count_1) && role == 1) || ((count_0 < count_1) && role == 0))
			return (float) -0.8;
		else
			return 0;
	}

	class CellValueTuple {
		int row, col;
		float utility;

		public CellValueTuple() {
			row = -1;
			col = -1;
		}
	}

}
