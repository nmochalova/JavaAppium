package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Тесты на статьи
 */
@Epic("Tests for articles")
public class ArticleTests extends CoreTestCase
{
    //Тест, который находит в поиске статью, открывает ее и сверяет заголовок с требуемым
    @Test
    @Features(value={@Feature(value="Search"),@Feature(value = "Article")})
    @DisplayName("Compare article title with expected one")
    @Description("We open 'Object-oriented programming language' article and make sure the title expected")
    @Step("Starting test testCompareArticleTitle")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testCompareArticleTitle()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String articleTitle = ArticlePageObject.getArticleTitle();

      //  ArticlePageObject.takeScreenshot("article_page");

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                articleTitle);
    }

    //Тест, который открывает статью и несколько раз делает swipe по ней пока не достигнет конца статьи
    @Test
    @Features(value={@Feature(value="Search"),@Feature(value = "Article")})
    @DisplayName("Swipe article to the footer")
    @Description("We open an article and swipe it to the footer")
    @Step("Starting test testSwipeArticle")
    @Severity(value = SeverityLevel.MINOR)
    public void testSwipeArticle()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

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
