package libgdx.implementations.iq;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.I18NBundle;

import libgdx.game.Game;
import libgdx.resources.SpecificResource;

public enum SkelGameSpecificResource implements SpecificResource {

    // @formatter:off

    specific_labels("labels/labels", I18NBundle.class),

    game_aqua_down("buttons/game_aqua_down.png", Texture.class),
    game_aqua_up("buttons/game_aqua_up.png", Texture.class),
    game_black_down("buttons/game_black_down.png", Texture.class),
    game_black_up("buttons/game_black_up.png", Texture.class),
    game_blue_down("buttons/game_blue_down.png", Texture.class),
    game_blue_up("buttons/game_blue_up.png", Texture.class),
    game_bomb_down("buttons/game_bomb_down.png", Texture.class),
    game_bomb_up("buttons/game_bomb_up.png", Texture.class),
    game_green_down("buttons/game_green_down.png", Texture.class),
    game_green_up("buttons/game_green_up.png", Texture.class),
    game_mov_down("buttons/game_mov_down.png", Texture.class),
    game_mov_up("buttons/game_mov_up.png", Texture.class),
    game_red_down("buttons/game_red_down.png", Texture.class),
    game_red_up("buttons/game_red_up.png", Texture.class),
    game_white_down("buttons/game_white_down.png", Texture.class),
    game_white_up("buttons/game_white_up.png", Texture.class),
    game_yellow_down("buttons/game_yellow_down.png", Texture.class),
    game_yellow_up("buttons/game_yellow_up.png", Texture.class),
    game_explosion("buttons/game_explosion.png", Texture.class),

    ;
    // @formatter:on

    private String path;
    private Class<?> classType;

    SkelGameSpecificResource(String path, Class<?> classType) {
        this.path = path;
        this.classType = classType;
    }

    @Override
    public Class<?> getClassType() {
        return classType;
    }

    @Override
    public String getPath() {
        return Game.getInstance().getAppInfoService().getImplementationGameResourcesFolder() + path;
    }

}
