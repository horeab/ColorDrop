package libgdx.implementations.iq;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import libgdx.graphics.GraphicUtils;
import libgdx.resources.MainResource;
import libgdx.resources.Res;

public enum SkelGameButtonSkin implements libgdx.controls.button.ButtonSkin {

    GAME_RED(SkelGameSpecificResource.game_red_up, SkelGameSpecificResource.game_red_down, SkelGameSpecificResource.game_red_up, SkelGameSpecificResource.game_red_up, null),
    GAME_BLUE(SkelGameSpecificResource.game_blue_up, SkelGameSpecificResource.game_blue_down, SkelGameSpecificResource.game_blue_up, SkelGameSpecificResource.game_blue_up, null),
    GAME_GREEN(SkelGameSpecificResource.game_green_up, SkelGameSpecificResource.game_green_down, SkelGameSpecificResource.game_green_up, SkelGameSpecificResource.game_green_up, null),
    GAME_YELLOW(SkelGameSpecificResource.game_yellow_up, SkelGameSpecificResource.game_yellow_down, SkelGameSpecificResource.game_yellow_up, SkelGameSpecificResource.game_yellow_up, null),
    GAME_AQUA(SkelGameSpecificResource.game_aqua_up, SkelGameSpecificResource.game_aqua_down, SkelGameSpecificResource.game_aqua_up, SkelGameSpecificResource.game_aqua_up, null),
    GAME_MOV(SkelGameSpecificResource.game_mov_up, SkelGameSpecificResource.game_mov_down, SkelGameSpecificResource.game_mov_up, SkelGameSpecificResource.game_mov_up, null),
    GAME_BLACK(SkelGameSpecificResource.game_black_up, SkelGameSpecificResource.game_black_down, SkelGameSpecificResource.game_black_up, SkelGameSpecificResource.game_black_up, null),
    GAME_WHITE(SkelGameSpecificResource.game_white_up, SkelGameSpecificResource.game_white_down, SkelGameSpecificResource.game_white_up, SkelGameSpecificResource.game_white_up, null),
    GAME_BOMB(SkelGameSpecificResource.game_bomb_up, SkelGameSpecificResource.game_bomb_down, SkelGameSpecificResource.game_bomb_up, SkelGameSpecificResource.game_bomb_up, null),
    BOMBED_CELL(SkelGameSpecificResource.game_explosion, SkelGameSpecificResource.game_explosion, SkelGameSpecificResource.game_explosion, SkelGameSpecificResource.game_explosion, null),
    ;

    SkelGameButtonSkin(Res imgUp, Res imgDown, Res imgChecked, Res imgDisabled, Color buttonDisabledFontColor) {
        this.imgUp = imgUp;
        this.imgDown = imgDown;
        this.imgChecked = imgChecked;
        this.imgDisabled = imgDisabled;
        this.buttonDisabledFontColor = buttonDisabledFontColor;
    }

    private Res imgUp;
    private Res imgDown;
    private Res imgChecked;
    private Res imgDisabled;
    private Color buttonDisabledFontColor;

    @Override
    public Drawable getImgUp() {
        return GraphicUtils.getImage(imgUp).getDrawable();
    }

    @Override
    public Drawable getImgDown() {
        return GraphicUtils.getImage(imgDown).getDrawable();
    }

    @Override
    public Drawable getImgChecked() {
        return GraphicUtils.getImage(imgChecked).getDrawable();
    }

    @Override
    public Drawable getImgDisabled() {
        return GraphicUtils.getImage(imgDisabled).getDrawable();
    }

    @Override
    public Color getButtonDisabledFontColor() {
        return buttonDisabledFontColor;
    }
}
