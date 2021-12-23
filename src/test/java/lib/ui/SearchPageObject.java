//методы для поиска
package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertTrue;

abstract public class SearchPageObject extends MainPageObject {
     protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_ARTICLE_FOR_TITLE_AND_DESC,
            SEARCH_RESULT_TITLE,
            SEARCH_STRING_TEXT,
            SEARCH_RESULT_CONTAINER;

    //Инициализация драйвера
    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    /*TEMPLATES METHODS*/
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}",substring);
    }

    //метод заменяет в Xpath одновременно заголовок и описание
    private static String getResultSearchElementForTitleAndDesc(String substringTitle, String substringDesc)
    {
        return SEARCH_ARTICLE_FOR_TITLE_AND_DESC
                .replace("{SUBSTRING_TITLE}",substringTitle)
                .replace("{SUBSTRING_DESC}",substringDesc);
    }
    /*TEMPLATES METHODS*/

    //Метод ожидает на странице элемент "Search Wikipedia" и затем кликает на него
    public void initSearchInput()
    {
        this.waitForElementPresent(SEARCH_INIT_ELEMENT,
                "Cannot find search input after clicking search init element");

        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element",
                5);
    }

    //Ввод переданного значения в строку поиска "Search..."
    public void typeSearchLine(String searchLine)
    {
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                searchLine,
                "Cannot find and type into search input",
                5);
    }

    //Метод проверяет наличие в результатах поиска строку по заданной подстроке
    public void waitForSearchResult(String substring)
    {
        String searchResultXpath = getResultSearchElement(substring);

        this.waitForElementPresent
                (searchResultXpath,
                        "Cannot find search result with substring " + substring);
    }

    //клик по результату поиска с учетом заданной подстроки
    public void clickByArticleWithSubstring(String substring)
    {
        String searchResultXpath = getResultSearchElement(substring);

        this.waitForElementAndClick(
                searchResultXpath,
                "Cannot find and click search result with substring " + substring,
                10);
    }

    //Проверка наличия кнопки отмены поиска Х на странице
    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(
                SEARCH_CANCEL_BUTTON,
                "Cannot find X to cancel search",
                5);
    }

    //Проверка, что кнопки отмены поиска Х нет на странице
    public void waitForCancelButtonToDisAppear()
    {
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "X still present on the page",
                5);
    }

    //Клик по кнопке отмены поиска Х
    public void clickCancelSearch()
    {
        this.waitForElementAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot find and click by button X cancel search",
                5
        );
    }

    //Подсчет количества результатов поиска
    public int getAmountOfFoundArticle()
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15);

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    //метод, который ожидает пустой результат запроса (строку No results found на странице)
    public void waitForEmptyResultsLabel()
    {
         this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result label by the request ",
                15);
    }

    //метод, который проверяет отсуствие результатов поиска
    public void assertThereIsNotResultOfSearch()
    {
        this.assertElementNotPresent(
                SEARCH_RESULT_ELEMENT,
                "We've found some results by request " );
    }

    //метод, который проверяет наличие заголовка в статье
    public void assertThereIsResultOfSearch()
    {
        this.assertElementPresent(
                SEARCH_RESULT_TITLE,
                "A title not present." );
    }

    //метод дожидатся результата поиска по двум строкам - по заголовку и описанию. Если такого не появляется,
    // то ошибка.
    public void waitForElementByTitleAndDescription(String title, String description)
    {
        String searchResultXpath =  getResultSearchElementForTitleAndDesc(title, description);

        this.waitForElementPresent(searchResultXpath,
                "Cannot find element in search result by title and description \n" + searchResultXpath);
    }

   //метод который проверяет, что локатор содержит строку "Search…"
    public void assertTextSearchString()
    {
        this.waitForElementPresent(
                SEARCH_STRING_TEXT,
                "Cannot find attribute text Search...",
                15
        );

        this.assertElementHasText(
                SEARCH_STRING_TEXT,
                "Search…",
                "Attribute text on page does not contain 'Search…' but should"
        );
    }

    public void assertForWordByResultsSearch (String keyWord)
    {
        List<WebElement> elementList = this.waitForElementsPresent(
               //By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                SEARCH_RESULT_CONTAINER,
                "List of elements are empty",
                5
        );

        for (WebElement webElement : elementList)
        {
            String elementAttribute = webElement.getAttribute("text");
            System.out.println(elementAttribute);
            assertTrue("Search result does not contain " + keyWord,elementAttribute.contains(keyWord));
        }
    }
}
