package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        TITLE ="css:#content h1";
        FOOTER_ELEMENT ="css:footer.mw-footer";
        OPTIONS_BUTTON ="xpath://android.widget.ImageView[@content-desc='More options']";

        OPTIONS_ADD_TO_MY_LIST_BUTTON ="css:#page-actions a#ca-watch.mw-ui-icon-wikimedia-star-base20";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "css:#page-actions a#ca-watch.mw-ui-icon-wikimedia-unStar-progressive";

        ADD_TO_MY_LIST_OVERLAY ="id:org.wikipedia:id/onboarding_button";
        MY_LIST_NAME_INPUT ="id:org.wikipedia:id/text_input";
        MY_LIST_OK_BUTTON ="xpath://*[@text='OK']";
       // CLOSE_ARTICLE_BUTTON ="xpath://android.widget.ImageButton[@content-desc='Navigate up']";
        FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
