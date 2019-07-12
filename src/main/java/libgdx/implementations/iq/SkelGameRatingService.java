package libgdx.implementations.iq;

import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.controls.popup.RatingPopup;
import libgdx.controls.popup.RatingService;
import libgdx.resources.FontManager;
import libgdx.screen.AbstractScreen;
import libgdx.utils.ScreenDimensionsManager;

public class SkelGameRatingService extends RatingService {


    public SkelGameRatingService(AbstractScreen abstractScreen) {
        super(abstractScreen);
    }

    @Override
    protected RatingPopup createRatingPopup() {
        return new RatingPopup(getScreen()) {
            @Override
            protected void addExtraButtons() {
            }

            @Override
            protected MyWrappedLabelConfigBuilder getInfoLabelConfigBuilder() {
                return super.getInfoLabelConfigBuilder().setFontScale(FontManager.getBigFontDim());
            }

        };
    }
}
