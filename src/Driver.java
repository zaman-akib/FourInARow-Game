import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome to '4 In A Row' game..\n");
		System.out.println("Choose mode:\n1. Easy\n2. Medium\n3. Hard");
		int mode = sc.nextInt();
		System.out.print("Enter your name: ");
		String name = sc.next();

		Agent human = new HumanAgent(name);
		Agent computer = new AlphaBetaAgent("Computer", mode);
		Game game = new FourInRow(human, computer);
		game.play();
	}
}
