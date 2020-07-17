public abstract class Agent {

	String name;
	int role;

	public Agent(String name) {
		this.name = name;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public abstract void makeMove(Game game);
}
