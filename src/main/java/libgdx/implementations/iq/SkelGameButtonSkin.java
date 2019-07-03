package libgdx.implementations.iq;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import libgdx.graphics.GraphicUtils;
import libgdx.resources.Res;

public enum SkelGameButtonSkin implements libgdx.controls.button.ButtonSkin {

    GAME_RED(null, null, null, null, null),
    GAME_BLUE(null, null, null, null, null),
    GAME_GREEN(null, null, null, null, null),
    GAME_YELLOW(null, null, null, null, null),
    GAME_AQUA(null, null, null, null, null),
    GAME_MOV(null, null, null, null, null),
    GAME_BLACK(null, null, null, null, null),
    GAME_WHITE(null, null, null, null, null),
    GAME_BOMB(null, null, null, null, null),
    BOMBED_CELL(null, null, null, null, null),

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
