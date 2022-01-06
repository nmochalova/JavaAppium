package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidMyListPageObject extends MyListsPageObject {
     static
     {
         ARTICLE_BY_TITLE_TPL = "xpath://android.widget.TextView[@text='{TITLE}']";
         FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";
         SEARCH_RESULT_ELEMENT_BY_LIST = "id:org.wikipedia:id/page_list_item_container";
     }

    public AndroidMyListPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
