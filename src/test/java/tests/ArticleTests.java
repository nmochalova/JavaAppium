package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Тесты на статьи
 */
public class ArticleTests extends CoreTestCase
{
    //Тест, который находит в поиске статью, открывает ее и сверяет заголовок с требуемым
    @Test
    public void testCompareArticleTitle()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String articleTitle = ArticlePageObject.getArticleTitle();

        assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                articleTitle);
    }

    //Тест, который открывает статью и несколько раз делает swipe по ней пока не достигнет конца статьи
    @Test
    public void testSwipeArticle()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();
    }

    //Ex6: Тест, который открывает статью и убеждается, что у нее есть элемент title (тест всегда падает!)
//    @Ignore("This test always failed.")
//    @Test
//    public void testPresentOfTitle() throws InterruptedException {
//        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
//
//        SearchPageObject.initSearchInput();
//        SearchPageObject.typeSearchLine("Java");
//        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
//        // Thread.sleep(5000); //чтобы тест не падал не хватает времени для загрузки страницы. Тест демонстрирует важность задержек.
//        SearchPageObject.assertThereIsResultOfSearch();
//    }
}
