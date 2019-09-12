package libgdx.implementations.iq;


import libgdx.constants.GameIdEnum;
import libgdx.dbapi.UniqueDbOperationContainer;
import libgdx.game.Game;
import libgdx.game.ScreenManager;
import libgdx.game.external.AppInfoService;
import libgdx.game.external.BillingService;
import libgdx.game.external.FacebookService;
import libgdx.game.external.LoginService;
import libgdx.login.GuestUserLoginService;
import libgdx.screens.AbstractScreen;

public class SkelGame extends Game<AppInfoService,
        SkelGameMainDependencyManager,
        SkelGameDependencyManager,
        AbstractScreen,
        ScreenManager,
        GameIdEnum
        > {

    public SkelGame(FacebookService facebookService,
                    BillingService billingService,
                    AppInfoService appInfoService) {
        super(facebookService, billingService, appInfoService, new SkelGameMainDependencyManager());
    }

    @Override
    public void create() {
        super.create();
        loginService = createLoginService();
        UniqueDbOperationContainer.getInstance().clear();
    }

    private LoginService createLoginService() {
        return new GuestUserLoginService();
    }

    public SkelGameDependencyManager getDependencyManager() {
        return getSubGameDependencyManager();
    }

    public static SkelGame getInstance() {
        return (SkelGame) Game.getInstance();
    }

    @Override
    protected void displayScreenAfterAssetsLoad() {
        ScreenManager screenManager = getScreenManager();
        screenManager.showMainScreen();
    }
}
