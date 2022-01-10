package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {
    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input.search.mw-ui-background-icon-search";
        SEARCH_CANCEL_BUTTON = "xpath://div[contains(@class,'header-action')]/button[contains(text(),'Close')]";
       // SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[@class='wikidata-description'][contains(text(),'{SUBSTRING}')]";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[@class='results']//*[contains(text(),'{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";

        SEARCH_RESULT_CONTAINER = "css:ul.page-list>li.page-summary";
        SEARCH_ARTICLE_FOR_TITLE_AND_DESC = "xpath://li[contains(@title,'{SUBSTRING_TITLE}')]//*[contains(text(),'{SUBSTRING_DESC}')]";

        //ВНИМАНИЕ! Эти локаторы необходимо обновить для Web mobile!
        //SEARCH_ARTICLE_FOR_TITLE_AND_DESC = "xpath://android.widget.LinearLayout[*[@text='{SUBSTRING_TITLE}'] and *[@text='{SUBSTRING_DESC}']]";
        SEARCH_RESULT_TITLE = "xpath://*[@resource-id='org.wikipedia:id/view_page_header_container']/*[@resource-id='org.wikipedia:id/view_page_title_text']";
        SEARCH_STRING_TEXT = "id:org.wikipedia:id/search_src_text";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
