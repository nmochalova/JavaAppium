package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Ignore;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

/**
 * Тесты на поиск
 */
public class SearchTests extends CoreTestCase
{
    //Тест, который вводит строку поиска и проверяет, что в результатах присутствует нужная статья.
    @Test
    public void testSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    //Тест, который нажимает на строку поиска, а потом на кнопку отмена поиска.
    // Затем проверяет, что поиск был отменен.
    @Test
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
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String searchLine = "Linkin Park Diskography";
        SearchPageObject.typeSearchLine(searchLine);
        int amountOfSearchResult = SearchPageObject.getAmountOfFoundArticle();

        assertTrue(
                "We found too few results!",
                amountOfSearchResult > 0);
    }

    //Тест, который ожидает пустой результат запроса по заданной строке
    @Test
    public void testAmountOfEmptySearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String searchLine = "zaqwetqw";
        SearchPageObject.typeSearchLine(searchLine);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNotResultOfSearch();
    }

    //Ex3: Тест: отмена поиска. Тест ищет слово, убеждается что найдено несколько вариантов, отменяет поиск и
    // убеждается что результат поиска пропал
    @Test
    public void testSearchResultAndCancel()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        int amountOfSearchResult = SearchPageObject.getAmountOfFoundArticle();
        assertTrue(
                "We found only one or less results! We want more one result!",
                amountOfSearchResult > 1);
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNotResultOfSearch();
    }

    //тест, который будет делать поиск по любому запросу на ваш выбор
    // (поиск по этому слову должен возвращать как минимум 3 результата).
    // Далее тест должен убеждаться, что первых три результата присутствуют в результате поиска.
    @Test
    public void testSearchForTitleAndDescription() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");

        HashMap<String, String> TitleAndNDescription = new HashMap<>();
        TitleAndNDescription.put("Java", "Island of Indonesia");
        TitleAndNDescription.put("JavaScript", "Programming language");
        TitleAndNDescription.put("Java (programming language)", "Object-oriented programming language");

        for (Map.Entry<String, String> kv : TitleAndNDescription.entrySet()) {
            SearchPageObject.waitForElementByTitleAndDescription(kv.getKey(), kv.getValue());
        }
    }

    //Тест, который проверяет что в поле поиска "Search..." действительно написано "Search..."
    @Test
    public void testContainText() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.assertTextSearchString();
    }

    // Ex4*: Тест: проверка слов в поиске. Тест делает поиск по какому-то ключевому слову. Например, JAVA.
    // Затем убеждается, что в каждом результате поиска есть это слово. Ошибка выдается в случае, если хотя бы один элемент
    // не содержит ключевого слова.
    @Test
    public void testSearchWordInResultsList()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        String keyWord = "Java";

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(keyWord);
        SearchPageObject.assertForWordByResultsSearch(keyWord);

    }
}
