package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

/**
 * Тесты на поиск
 */
@Epic("Tests for search articles")
public class SearchTests extends CoreTestCase
{
    //Тест, который вводит строку поиска и проверяет, что в результатах присутствует нужная статья.
    @Test
    @Features(value={@Feature(value="Search")})
    @DisplayName("Search for the desired article")
    @Description("A test that enters a search string and checks that the desired article is present in the results.")
    @Severity(value = SeverityLevel.BLOCKER)
    @Step("Starting test testSearch")
    public void testSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    //Тест, который нажимает на строку поиска, а потом на кнопку отмена поиска. Затем проверяет, что поиск был отменен.
    @Test
    @Features(value={@Feature(value="Search")})
    @DisplayName("Cancel search")
    @Description("A test that clicks on the search bar, and then on the cancel search button. Then checks that the search has been canceled.")
    @Severity(value = SeverityLevel.BLOCKER)
    @Step("Starting test testCancelSearch")
    public void testCancelSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisAppear();
    }

    //Тест проверяет, что по результатам поиска выданы данные (более 1 записи)
    @Test
    @Features(value={@Feature(value="Search")})
    @DisplayName("Counting the number of not empty search")
    @Description("The test verifies that data is returned on the search results (more than 1 record).")
    @Severity(value = SeverityLevel.CRITICAL)
    @Step("Starting test testAmountOfNotEmptySearch")
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String searchLine = "Linkin Park Diskography";
        SearchPageObject.typeSearchLine(searchLine);
        int amountOfSearchResult = SearchPageObject.getAmountOfFoundArticle();

        Assert.assertTrue(
                "We found too few results!",
                amountOfSearchResult > 0);
    }

    //Тест, который ожидает пустой результат запроса по заданной строке
    @Test
    @Features(value={@Feature(value="Search")})
    @DisplayName("Waiting for an empty query result")
    @Description("A test that expects an empty query result for a search string.")
    @Severity(value = SeverityLevel.CRITICAL)
    @Step("Starting test testAmountOfEmptySearch")
    public void testAmountOfEmptySearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String searchLine = "zaqwetqw";
        SearchPageObject.typeSearchLine(searchLine);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNotResultOfSearch();
    }

    //Ex3: Тест: отмена поиска. Тест ищет слово, убеждается что найдено несколько вариантов, отменяет поиск и убеждается что результат поиска пропал
    @Test
    @Features(value={@Feature(value="Search")})
    @DisplayName("Search result and cancel")
    @Description("Searched of word and makes sure that several options have been found. Cancels the search and makes sure that the search result is missing.")
    @Severity(value = SeverityLevel.CRITICAL)
    @Step("Starting test testSearchResultAndCancel")
    public void testSearchResultAndCancel()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        int amountOfSearchResult = SearchPageObject.getAmountOfFoundArticle();
        Assert.assertTrue(
                "We found only one or less results! We want more one result!",
                amountOfSearchResult > 1);
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNotResultOfSearch();
    }

    //тест, который будет делать поиск по любому запросу на ваш выбор (поиск по этому слову должен возвращать как минимум 3 результата).
    // Далее тест должен убеждаться, что первых три результата присутствуют в результате поиска.
    @Test
    @Features(value={@Feature(value="Search"),@Feature(value = "Title")})
    @DisplayName("Search for title and description")
    @Description("The test is to make sure that the first 3 results are present in the search result. We check by the title and description.")
    @Severity(value = SeverityLevel.NORMAL)
    @Step("Starting test testSearchForTitleAndDescription")
    public void testSearchForTitleAndDescription() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");

        HashMap<String, String> TitleAndNDescription = new HashMap<>();
//        TitleAndNDescription.put("Java", "Island of Indonesia");
//        TitleAndNDescription.put("JavaScript", "Programming language");
//        TitleAndNDescription.put("Java (programming language)", "Object-oriented programming language");

        TitleAndNDescription.put("Java", "sland");
        TitleAndNDescription.put("JavaScript", "rogramming language");
        TitleAndNDescription.put("Java (programming language)", "bject-oriented programming language");

        for (Map.Entry<String, String> kv : TitleAndNDescription.entrySet()) {
            SearchPageObject.waitForElementByTitleAndDescription(kv.getKey(), kv.getValue());
        }
    }

    //Тест, который проверяет что в поле поиска "Search..." действительно написано "Search..."
//    @Test
//    public void testContainText() {
//        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
//
//        SearchPageObject.initSearchInput();
//        SearchPageObject.assertTextSearchString();
//    }

    // Ex4*: Тест: проверка слов в поиске. Тест делает поиск по какому-то ключевому слову. Например, JAVA.
    // Затем убеждается, что в каждом результате поиска есть это слово. Ошибка выдается в случае, если хотя бы один элемент
    // не содержит ключевого слова.
    @Test
    @Features(value={@Feature(value="Search"),@Feature(value = "Title")})
    @DisplayName("Search word in result list")
    @Description("The test does a word search and makes sure that each search result has this word.")
    @Severity(value = SeverityLevel.BLOCKER)
    @Step("Starting test testSearchWordInResultsList")
    public void testSearchWordInResultsList()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        String keyWord = "Java";

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(keyWord);
        SearchPageObject.assertForWordByResultsSearch(keyWord);

    }
}
