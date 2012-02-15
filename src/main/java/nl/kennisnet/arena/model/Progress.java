package nl.kennisnet.arena.model;

public class Progress {
	private final int max;
	private final int curr;

	public Progress(int curr, int max) {
		super();
		this.max = max;
		this.curr = curr;
	}

	public int getMax() {
		return max;
	}

	public int getCurr() {
		return curr;
	}
}
