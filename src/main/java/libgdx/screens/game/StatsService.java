package libgdx.screens.game;

public class StatsService {

	public static int calculateScoreForLevel(int blocksCleared, int level) {
		return level * blocksCleared;
	}

	public static String getMovesAndBlocksLeftString(int moves, int blocks) {
		return "Destroy " + blocks + " blocks in " + moves + " moves";
	}

	public static int calculateBlocksForLevel(int level) {
		return 0;
	}
}