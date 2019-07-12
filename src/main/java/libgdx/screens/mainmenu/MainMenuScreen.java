package libgdx.screens.mainmenu;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import libgdx.controls.ScreenRunnable;
import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MainButtonSkin;
import libgdx.controls.button.MyButton;
import libgdx.controls.button.builders.SoundIconButtonBuilder;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.controls.popup.MyPopup;
import libgdx.graphics.GraphicUtils;
import libgdx.implementations.iq.SkelGameButtonSize;
import libgdx.implementations.iq.SkelGameButtonSkin;
import libgdx.implementations.iq.SkelGameLabel;
import libgdx.implementations.iq.SkelGameRatingService;
import libgdx.implementations.iq.SkelGameSpecificResource;
import libgdx.preferences.SettingsService;
import libgdx.resources.FontManager;
import libgdx.resources.MainResource;
import libgdx.resources.dimen.MainDimen;
import libgdx.screens.AbstractScreen;
import libgdx.screens.game.*;
import libgdx.utils.ActorPositionManager;
import libgdx.utils.ScreenDimensionsManager;
import libgdx.utils.SoundUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainMenuScreen extends AbstractScreen {

    private static final String GAME_TABLE_NAME = "GAME_TABLE";
    private Map<Coordonate, MyButton> gameButtons = new HashMap<>();
    private CurrentGame currentGame = new CurrentGame();
    private StoreService storeService = new StoreService();
    private MyPopup gameOverPopup;
    private String gameOverText;


    @Override
    public void buildStage() {
        new SkelGameRatingService(this).appLaunched();
        setUp();
    }

    private void setUp() {
        Util.processLevelChange(currentGame);
        createGameMatrix(currentGame.getCurrentMatrix());
    }


    private void createGameMatrix(final int[][] matrix) {
        Table gameTable = new Table();
        gameTable.setName(GAME_TABLE_NAME);
        addActor(gameTable);

        // SUCCESS LEVEL
        if (currentGame.getBlocksLeft() <= 0) {
            SoundUtils.playSound(SkelGameSpecificResource.finishlevel);
            goToNextLevel();
            return;
        } else if (!GameService.thereArePossibilities(matrix) || isGameOver(matrix)) {
            SoundUtils.playSound(SkelGameSpecificResource.gameover);
            processGameOver();
        }
        //////////////

        gameTable.add(createHeaderTable()).padTop(MainDimen.vertical_general_margin.getDimen()).grow().width(ScreenDimensionsManager.getScreenWidth()).colspan(Util.GAME_COLUMNS).row();
        int i = 0;
        int position = Util.COLOR_BUTTON_ID_STARTING_INT_VALUE;
        for (int rows = 0; rows < matrix.length; rows++) {
            for (int nr = 0; nr < matrix[0].length; nr++) {
                final int finalNr = nr;
                final int finalI = i;
                MyButton myButton = new ButtonBuilder("").setButtonSkin(MainButtonSkin.DEFAULT).setFixedButtonSize(SkelGameButtonSize.GAME_BUTTON_SIZE).build();
                final Coordonate coordonate = new Coordonate(finalNr, finalI, matrix[finalI][finalNr]);
                gameButtons.put(coordonate, myButton);
                if (matrix[i][nr] == Util.BOMB_BUTTON_MATRIX_CODE) {
                    currentGame.setBombButtonId(position);
                }
                final Set<Coordonate> pattern = new HashSet<Coordonate>();
                if (coordonate.getValue() != Util.BOMB_BUTTON_MATRIX_CODE) {
                    pattern.addAll(GameService.getSameColorCoordNeighbors(currentGame.getCurrentMatrix(), coordonate, new HashSet<>(), new HashSet<>()));
                }
                myButton.addListener(new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        colorNeighbours(pattern, true);
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        colorNeighbours(pattern, false);
                    }
                });
                myButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        hidePatternButtons(coordonate);
                        if (currentGame.getGameType().equals(GameTypeEnum.UNLIMITED)) {
                            Util.processLevelChange(currentGame);
                        }
                    }
                });
                ColorUtil.colorButton(myButton, matrix[i][nr]);
                if (matrix[i][nr] == Util.HIDE_BUTTON_MATRIX_CODE) {
                    myButton.setVisible(false);
                }
                gameTable.add(myButton).height(myButton.getHeight()).width(myButton.getWidth()).pad(MainDimen.horizontal_general_margin.getDimen() / 4);
                position++;
            }
            gameTable.row();
            i++;
        }
        gameTable.padBottom(MainDimen.vertical_general_margin.getDimen() * 3);
        gameTable.setWidth(ScreenDimensionsManager.getScreenWidth());
        gameTable.setHeight(ScreenDimensionsManager.getScreenHeight());
        gameTable.setX(0);
        ActorPositionManager.setActorCenterScreen(gameTable);
    }

    private Table createHeaderTable() {
        Table table = new Table();
        float dimen = MainDimen.horizontal_general_margin.getDimen();
        table.add(new MyWrappedLabel(new MyWrappedLabelConfigBuilder()
                .setText(SkelGameLabel.level_record.getText(storeService.getRecordScore()))
                .setFontScale(FontManager.getSmallFontDim())
                .setSingleLineLabel()
                .build()))
                .padLeft(dimen)
                .align(Align.left);
        table.add().growX();
        table.add(new SoundIconButtonBuilder().createSoundButton())
                .width(SkelGameButtonSize.SOUND_BUTTON_SIZE.getWidth()).height(SkelGameButtonSize.SOUND_BUTTON_SIZE.getHeight())
                .padRight(dimen)
                .align(Align.right)
                .row();
        table.add().growY().row();

        table.add(new MyWrappedLabel(new MyWrappedLabelConfigBuilder()
                .setText(SkelGameLabel.level.getText(currentGame.getLevel()))
                .setFontScale(FontManager.getBigFontDim())
                .setSingleLineLabel()
                .build()))
                .width(ScreenDimensionsManager.getScreenWidth())
                .colspan(3)
                .row();
        table.add(new MyWrappedLabel(new MyWrappedLabelConfigBuilder()
                .setText(StatsService.getMovesAndBlocksLeftString(currentGame.getMovesLeft(), currentGame.getBlocksLeft()))
                .setFontScale(FontManager.calculateMultiplierStandardFontSize(1.1f))
                .setSingleLineLabel()
                .build()))
                .width(ScreenDimensionsManager.getScreenWidth())
                .colspan(3);
        table.add().growY().row();
        return table;
    }

    private boolean isGameOver(int[][] matrix) {
        return !GameService.thereArePossibilities(matrix) || currentGame.getMovesLeft() <= 0;
    }

    private void processGameOver() {
        storeService.incrementGamesPlayed();
        gameOverText = SkelGameLabel.score.getText(currentGame.getLevel());
        if (storeService.getRecordScore() < currentGame.getLevel()) {
            storeService.putRecordScore(currentGame.getLevel());
            gameOverText = SkelGameLabel.score_record.getText(currentGame.getLevel());
        }
        gameOverPopup = createGameOverPopup();
        gameOverPopup.addToPopupManager();
    }

    private void goToNextLevel() {
        gameButtons.clear();
        Util.processLevelChange(currentGame);
        currentGame.setCurrentMatrix(Util.generateRandomMatrix(currentGame.getNrColors()));
        removeGameTable();
        createGameMatrix(currentGame.getCurrentMatrix());
    }

    private MyPopup createGameOverPopup() {
        return new MyPopup(this) {
            @Override
            protected void addButtons() {
                MyButton nextLevelButton = new ButtonBuilder(SkelGameLabel.new_game.getText()).setButtonSkin(MainButtonSkin.DEFAULT).build();
                nextLevelButton.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        gameOverPopup.hide();
                        currentGame = new CurrentGame();
                        removeGameTable();
                        setUp();
                    }
                });
                addButton(nextLevelButton);
            }

            @Override
            protected String getLabelText() {
                return gameOverText;
            }

            @Override
            public float getPrefWidth() {
                return ScreenDimensionsManager.getScreenWidthValue(80);
            }
        };
    }

    private void colorBombedNeighbours(Set<Coordonate> neigboursToBomb) {
        for (Coordonate cell : neigboursToBomb) {
            MyButton button = gameButtons.get(cell);
            button.setButtonSkin(SkelGameButtonSkin.BOMBED_CELL);
        }
    }

    private void colorNeighbours(Set<Coordonate> neigbours, boolean skinForTouchDown) {
        for (Coordonate cell : neigbours) {
            MyButton button = gameButtons.get(cell);
            button.setButtonSkin(skinForTouchDown ? ColorUtil.getPressedButtonSkinForValue(cell.getValue()) : ColorUtil.getButtonSkinForValue(cell.getValue()));
        }
    }

    private void hidePatternButtons(Coordonate coord) {
        Set<Coordonate> searched = new HashSet<>();
        Set<Coordonate> found = new HashSet<Coordonate>();
        Set<Coordonate> pattern = new HashSet<Coordonate>();
        if (coord.getValue() != Util.BOMB_BUTTON_MATRIX_CODE) {
            SoundUtils.playSound(SkelGameSpecificResource.colorclick);
            pattern = GameService.getSameColorCoordNeighbors(currentGame.getCurrentMatrix(), coord, searched, found);
            processClearedBlocksStats(pattern.size());
            enableAllGameButtons(false);
            colorNeighbours(pattern, true);
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
            SoundUtils.playSound(SkelGameSpecificResource.bombclick);
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
        removeGameTable();
        createGameMatrix(GameService.processMatrixClear(pattern, currentGame.getCurrentMatrix(),
                currentGame.getGameType(), currentGame.getNrColors()));
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

    private void removeGameTable() {
        getRoot().findActor(GAME_TABLE_NAME).remove();
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
