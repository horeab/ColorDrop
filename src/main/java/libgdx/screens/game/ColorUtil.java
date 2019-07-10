package libgdx.screens.game;

import libgdx.controls.button.ButtonSkin;
import libgdx.controls.button.MyButton;
import libgdx.implementations.iq.SkelGameButtonSkin;

public class ColorUtil {

    public static SkelGameButtonSkin getButtonSkinForValue(int value) {
        switch (value) {
            case 0:
                return SkelGameButtonSkin.GAME_RED;
            case 1:
                return SkelGameButtonSkin.GAME_BLUE;
            case 2:
                return SkelGameButtonSkin.GAME_GREEN;
            case 3:
                return SkelGameButtonSkin.GAME_YELLOW;
            case 4:
                return SkelGameButtonSkin.GAME_AQUA;
            case 5:
                return SkelGameButtonSkin.GAME_MOV;
            case 6:
                return SkelGameButtonSkin.GAME_BLACK;
            case Util.BOMB_BUTTON_MATRIX_CODE:
                return SkelGameButtonSkin.GAME_BOMB;
            default:
                return SkelGameButtonSkin.GAME_WHITE;
        }
    }

    public static ButtonSkin getPressedButtonSkinForValue(int value) {
        SkelGameButtonSkin skin = getButtonSkinForValue(value);
        return SkelGameButtonSkin.valueOf(skin.name() + "_PRESSED");
    }

    public static void colorButton(final MyButton button, final int cellValue) {
        ButtonSkin skin = getButtonSkinForValue(cellValue);
        button.setButtonSkin(skin);
    }
}