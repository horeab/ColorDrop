package libgdx.screens.game;

import libgdx.implementations.iq.SkelGameLabel;

public class StatsService {

    public static int calculateScoreForLevel(int blocksCleared, int level) {
        return level * blocksCleared;
    }

    public static String getMovesAndBlocksLeftString(int moves, int blocks) {
        return SkelGameLabel.destroy_info.getText(blocks, moves);
    }

}