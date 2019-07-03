package libgdx.screens.game;

import java.util.Random;

public class Util {

	final static int GAME_COLUMNS = 7;
	final static int GAME_ROWS = 9;
	private final static int BOMBS_PER_LEVEL = 1;

	public final static int HIDE_BUTTON_MATRIX_CODE = 999;
	public final static int BOMB_BUTTON_MATRIX_CODE = 1000;

	public final static int LAYOUT_ID_STARTING_INT_VALUE = 200;
	public final static int COLOR_BUTTON_ID_STARTING_INT_VALUE = 0;

	public static int[][] generateRandomMatrix(int nrColors) {
		int[][] randomMatrix = new int[GAME_ROWS][GAME_COLUMNS];
		int bombPlaced = 0;
		for (int i = 0; i < GAME_ROWS; i++) {
			for (int j = 0; j < GAME_COLUMNS; j++) {
				int cellValue = randomNumber(nrColors);
				int bombCell = placeBomb(i, j, bombPlaced, cellValue);
				if (cellValue != bombCell) {
					cellValue = bombCell;
					bombPlaced++;
				}
				randomMatrix[i][j] = cellValue;
			}
		}
		return randomMatrix;
	}

	public static int placeBomb(int i, int j, int bombPlaced, int cellValue) {
		if (bombPlaced < BOMBS_PER_LEVEL) {
			if (Math.random() < 0.03) {
				cellValue = BOMB_BUTTON_MATRIX_CODE;
			}
			if (i == GAME_ROWS - 1 && j == GAME_COLUMNS - 1) {
				cellValue = BOMB_BUTTON_MATRIX_CODE;
			}
		}
		return cellValue;
	}

	public static int randomNumber(int nrColors) {
		if (nrColors <= 4) {
			switch (nrColors) {
			case 2:
				if (Math.random() > 0.5) {
					return 0;
				} else {
					return 1;
				}
			case 3:
				if (Math.random() < 0.33) {
					return 0;
				} else if (Math.random() < 0.66) {
					return 1;
				} else {
					return 2;
				}
			case 4:
				if (Math.random() < 0.25) {
					return 0;
				} else if (Math.random() < 0.5) {
					return 1;
				} else if (Math.random() < 0.75) {
					return 2;
				} else {
					return 3;
				}
			default:
				return 0;
			}
		} else {
			return new Random().nextInt(nrColors);
		}

	}

	public static void processLevelChange(CurrentGame currentGame) {
		if (currentGame.getGameType().equals(GameTypeEnum.MULTI_LEVEL)) {
			currentGame.setLevel(currentGame.getLevel() + 1);
			currentGame.setLevelBlocksCleared(0);
		} else if (currentGame.getGameType().equals(GameTypeEnum.UNLIMITED)) {
			if (currentGame.getTotalBlocksCleared() > 50) {
				if (currentGame.getTotalBlocksCleared() < 100) {
					currentGame.setLevel(2);
				} else if (currentGame.getTotalBlocksCleared() < 200) {
					currentGame.setLevel(3);
				} else if (currentGame.getTotalBlocksCleared() < 300) {
					currentGame.setLevel(4);
				} else if (currentGame.getTotalBlocksCleared() < 400) {
					currentGame.setLevel(5);
				} else if (currentGame.getTotalBlocksCleared() < 500) {
					currentGame.setLevel(6);
				} else if (currentGame.getTotalBlocksCleared() < 600) {
					currentGame.setLevel(7);
				}
			}
		}
		currentGame.setBombFound(false);
		currentGame.setBlocksLeft(calculateBlocksForLevel(currentGame
				.getLevel()));
		currentGame
				.setMovesLeft(calculateMovesForLevel(currentGame.getLevel()));
		processColorNrChange(currentGame);
	}

	private static void processColorNrChange(CurrentGame currentGame) {
		int colorNr = 2;
		if (currentGame.getLevel() <= 1) {
			colorNr = 2;
		} else if (currentGame.getLevel() <= 2) {
			colorNr = 3;
		} else if (currentGame.getLevel() <= 5) {
			colorNr = 4;
		} else if (currentGame.getLevel() <= 8) {
			colorNr = 5;
		} else if (currentGame.getLevel() <= 11) {
			colorNr = 6;
		} else if (currentGame.getLevel() <= 14) {
			colorNr = 7;
		} else {
			colorNr = 7;
		}
		currentGame.setNrColors(colorNr);
	}

	private static int calculateMovesForLevel(int level) {
		int moves = 3;
		if (level <= 1) {
			moves = 3;
		} else if (level <= 2) {
			moves = 6;
		} else if (level <= 5) {
			moves = 6;
		} else if (level <= 8) {
			moves = 8;
		} else if (level <= 11) {
			moves = 9;
		} else if (level <= 14) {
			moves = 10;
		} else {
			moves = 10;
		}
		return moves;
	}

	private static int calculateBlocksForLevel(int level) {
		int blocks = 40;
		if (level <= 1) {
			blocks = 40;
		} else if (level <= 2) {
			blocks = 40;
		} else if (level <= 5) {
			blocks = 35;
		} else if (level <= 8) {
			blocks = 30;
		} else if (level <= 11) {
			blocks = 30;
		} else if (level <= 14) {
			blocks = 25;
		} else {
			blocks = 25 + (level / 5) * 10;
		}
		return blocks;
	}
}