package libgdx.screens.mainmenu;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.controls.ScreenRunnable;
import libgdx.controls.button.ButtonSkin;
import libgdx.controls.button.MyButton;
import libgdx.implementations.iq.SkelGameButtonSkin;
import libgdx.implementations.iq.SkelGameRatingService;
import libgdx.screens.AbstractScreen;
import libgdx.screens.game.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainMenuScreen extends AbstractScreen {

    private Map<Integer, MyButton> gameButtons = new HashMap<>();
    private CurrentGame currentGame = new CurrentGame();
    private StoreService storeService = new StoreService();


    @Override
    public void buildStage() {
        new SkelGameRatingService(this).appLaunched();
        setUp();
    }

    private void setUp() {
        Util.processLevelChange(currentGame);
        setLettersKeyboard(currentGame.getCurrentMatrix());
    }


    private void setLettersKeyboard(final int[][] matrix) {
        // SUCCESS LEVEL
        if (currentGame.getBlocksLeft() <= 0) {
            boolean previousBombState = currentGame.isBombFound();
            Util.processLevelChange(currentGame);
            currentGame.setBombFound(previousBombState);
            goToNextLevel();
        } else if (isGameOver(matrix)) {
            processGameOver();
        }
        //////////////

        Table gameTable = new Table();
        int i = 0;
        int position = Util.COLOR_BUTTON_ID_STARTING_INT_VALUE;
        for (int rows = 0; rows < matrix.length; rows++) {
            for (int nr = 0; nr < matrix[0].length; nr++) {
                final int finalNr = nr;
                final int finalI = i;
                MyButton myButton = null;
                gameButtons.put(position, myButton);
                if (matrix[i][nr] == Util.BOMB_BUTTON_MATRIX_CODE) {
                    currentGame.setBombButtonId(position);
                }
                myButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        hidePatternButtons(new Coordonate(finalNr, finalI, matrix[finalI][finalNr]));
                        if (currentGame.getGameType().equals(GameTypeEnum.UNLIMITED)) {
                            Util.processLevelChange(currentGame);
                        }
                    }
                });
                ColorUtil.colorButton(myButton, matrix[i][nr]);
                if (matrix[i][nr] == Util.HIDE_BUTTON_MATRIX_CODE) {
                    myButton.setVisible(false);
                }
                gameTable.add(myButton);
                position++;
            }
            gameTable.row();
            i++;
        }
    }

    private boolean isGameOver(int[][] matrix) {
        return !GameService.thereArePossibilities(matrix) || currentGame.getMovesLeft() <= 0;
    }

    private void processGameOver() {
        storeService.incrementGamesPlayed();
        if (storeService.getRecordScore() < currentGame.getLevel()) {
            storeService.putRecordScore(currentGame.getLevel());
        }
        MyButton gameOverButton = null;
        gameOverButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
    }

    private void goToNextLevel() {
        MyButton nextLevelButton = null;
        nextLevelButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                currentGame.setBombFound(false);
                currentGame.setCurrentMatrix(Util.generateRandomMatrix(currentGame.getNrColors()));
                removeAllGameButtons();
                setLettersKeyboard(currentGame.getCurrentMatrix());
            }
        });
    }

    private void colorBombedNeighbours(Set<Coordonate> neigboursToBomb) {
        for (Coordonate cell : neigboursToBomb) {
            MyButton button = getLayoutButton(cell);
            button.setButtonSkin(SkelGameButtonSkin.BOMBED_CELL);
        }
    }

    private void colorNeighbours(Set<Coordonate> neigbours) {
        for (Coordonate cell : neigbours) {
            MyButton button = getLayoutButton(cell);
            button.setButtonSkin(getPressedButtonSkin());
        }
    }

    private ButtonSkin getPressedButtonSkin() {
        return null;
    }

    private void hidePatternButtons(Coordonate coord) {
        Set<Coordonate> searched = new HashSet<>();
        Set<Coordonate> found = new HashSet<Coordonate>();
        Set<Coordonate> pattern = new HashSet<Coordonate>();
        if (coord.getValue() != Util.BOMB_BUTTON_MATRIX_CODE) {
            pattern = GameService.getSameColorCoordNeighbors(currentGame.getCurrentMatrix(), coord, searched, found);
            processClearedBlocksStats(pattern.size());
            enableAllGameButtons(false);
            colorNeighbours(pattern);
            final Set<Coordonate> finalPattern = pattern;
            delayAction(new ScreenRunnable(this) {
                @Override
                public void executeOperations() {
                    clearButtonsAfterClick(finalPattern);
                    enableAllGameButtons(true);
                }
            });
            processClick(pattern.size());

        } else {
            pattern = GameService.getBombNeighbors(coord, currentGame.getCurrentMatrix());
            processClearedBlocksStats(pattern.size());
            enableAllGameButtons(false);
            colorBombedNeighbours(pattern);
            final Set<Coordonate> finalPattern = pattern;
            delayAction(new ScreenRunnable(this) {
                @Override
                public void executeOperations() {
                    clearButtonsAfterClick(finalPattern);
                    enableAllGameButtons(true);
                }
            });
            currentGame.setBombFound(true);
            processClick(pattern.size() - 1);
        }
    }

    private void delayAction(Runnable runnable) {
        RunnableAction action = new RunnableAction();
        action.setRunnable(runnable);
        addAction(Actions.sequence(Actions.delay(0.5f), action));
    }

    private void clearButtonsAfterClick(Set<Coordonate> pattern) {
        removeAllGameButtons();
        setLettersKeyboard(GameService.processMatrixClear(pattern, currentGame.getCurrentMatrix(),
                currentGame.getGameType(), currentGame.getNrColors()));
    }

    private MyButton getLayoutButton(Coordonate coord) {
        int position = Util.COLOR_BUTTON_ID_STARTING_INT_VALUE;
        int y = 0;
        for (int rows = 0; rows < currentGame.getCurrentMatrix().length; rows++) {
            for (int nr = 0; nr < currentGame.getCurrentMatrix()[0].length; nr++) {
                if (coord.getY() == y && coord.getX() == nr) {
                    return gameButtons.get(position);
                }
                position++;
            }
            y++;
        }
        return null;
    }

    private void processClearedBlocksStats(int blocksCleared) {
        currentGame.setLevelBlocksCleared(currentGame.getLevelBlocksCleared() + blocksCleared);
        currentGame.setTotalBlocksCleared(currentGame.getTotalBlocksCleared() + blocksCleared);
        currentGame.setScore(currentGame.getScore() + StatsService.calculateScoreForLevel(currentGame.getLevel(), blocksCleared));
    }

    private void enableAllGameButtons(boolean choice) {
        for (MyButton btn : gameButtons.values()) {
            btn.setTouchable(choice ? Touchable.enabled : Touchable.disabled);
        }
    }

    private void removeAllGameButtons() {
        for (MyButton btn : gameButtons.values()) {
            btn.remove();
        }
    }

    private void processClick(int blocksCleared) {
        int movesLeft = 0;
        int blocksLeft = 0;
        if (currentGame.getMovesLeft() - 1 > 0) {
            movesLeft = currentGame.getMovesLeft() - 1;
        }
        if (currentGame.getBlocksLeft() - blocksCleared > 0) {
            blocksLeft = currentGame.getBlocksLeft() - blocksCleared;
        }
        currentGame.setMovesLeft(movesLeft);
        currentGame.setBlocksLeft(blocksLeft);
    }

    @Override
    public void onBackKeyPress() {
        Gdx.app.exit();
    }

}
