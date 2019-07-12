package libgdx.screens.game;

public class CurrentGame {

    private int level;
    private int nrColors;
    private int score;
    private int totalBlocksCleared;
    private int levelBlocksCleared;
    private int[][] currentMatrix;
    private GameTypeEnum gameType;
    private boolean bombFound;
    private int bombButtonId;

    private int movesLeft;
    private int blocksLeft;

    public CurrentGame() {
        this.level = 0;
        this.score = 0;
        this.levelBlocksCleared = 0;
        this.totalBlocksCleared = 0;
        this.nrColors = 2;
        this.currentMatrix = Util.generateRandomMatrix(this.nrColors);
        this.gameType = GameTypeEnum.MULTI_LEVEL;
    }

    public int getBombButtonId() {
        return bombButtonId;
    }

    public void setBombButtonId(int bombButtonId) {
        this.bombButtonId = bombButtonId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNrColors() {
        return nrColors;
    }

    public void setNrColors(int nrColors) {
        this.nrColors = nrColors;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int[][] getCurrentMatrix() {
        return currentMatrix;
    }

    public void setCurrentMatrix(int[][] currentMatrix) {
        this.currentMatrix = currentMatrix;
    }

    public GameTypeEnum getGameType() {
        return gameType;
    }

    public void setGameType(GameTypeEnum gameType) {
        this.gameType = gameType;
    }

    public int getLevelBlocksCleared() {
        return levelBlocksCleared;
    }

    public void setLevelBlocksCleared(int levelBlocksCleared) {
        this.levelBlocksCleared = levelBlocksCleared;
    }

    public int getTotalBlocksCleared() {
        return totalBlocksCleared;
    }

    public void setTotalBlocksCleared(int totalBlocksCleared) {
        this.totalBlocksCleared = totalBlocksCleared;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
    }

    public int getBlocksLeft() {
        return blocksLeft;
    }

    public void setBlocksLeft(int blocksLeft) {
        this.blocksLeft = blocksLeft;
    }

    public boolean isBombFound() {
        return bombFound;
    }

    public void setBombFound(boolean bombFound) {
        this.bombFound = bombFound;
    }

}