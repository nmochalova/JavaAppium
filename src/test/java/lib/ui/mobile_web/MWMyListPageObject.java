package lib.ui.mobile_web;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListPageObject extends MyListsPageObject {
    static
    {
        ARTICLE_BY_TITLE_TPL = "xpath://ul[contains(@class,'content-unstyled')]//h3[contains(text(),'{TITLE}')]";
        FOLDER_BY_NAME_TPL   = "xpath://ul[contains(@class,'content-unstyled')]//h3[contains(text(),'{TITLE}')]";
        SEARCH_RESULT_ELEMENT_BY_LIST = "xpath://ul[contains(@class,'content-unstyled')]";
        REMOVE_FROM_SAVED_BUTTON = "xpath://ul[contains(@class,'content-unstyled')]//h3[contains(text(),'{TITLE}')]/../../a[contains(@class,'mw-ui-icon')]";
    }

    public MWMyListPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
