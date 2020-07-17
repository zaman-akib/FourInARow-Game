import java.util.Random;

public abstract class Game {
	Agent agent[];
	String name = "A Generic Game";

	Random random = new Random();
	Agent winner = null;

	public Game(Agent a, Agent b) {
		agent = new Agent[2];
		agent[0] = a;
		agent[1] = b;

	}

	public void play() {
		System.out.println("Starting " + name + " between " + agent[0].name + " and " + agent[1].name + ".");
		int turn = random.nextInt(2);
		initialize(false);

		while (!isFinished()) {
			System.out.println(agent[turn].name + "'s turn. ");
			agent[turn].makeMove(this);
			showGameState();

			turn = (turn + 1) % 2;
		}

		if (winner != null)
			System.out.println(winner.name + " wins!!!");
		else
			System.out.println("Game drawn!!");

	}

	abstract boolean isFinished();

	abstract void initialize(boolean fromFile);

	abstract void showGameState();

}
