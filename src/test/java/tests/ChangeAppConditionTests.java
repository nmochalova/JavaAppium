package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;

/**
 * Тесты на состояние приложения после изменения его состояния (ориентация экрана, Background)
 */
@Epic("Tests for change condition application")
public class ChangeAppConditionTests extends CoreTestCase
{
    //Тест выбирает статью, переворачивает экран и проверяет, что статья не изменилась.
    @Test
    @Features(value={@Feature(value="Search"),@Feature(value = "Article"),@Feature(value = "Condition")})
    @DisplayName("Change screen orientation on search result")
    @Description("The test selects an article, flips the screen and checks that the article has not changed.")
    @Severity(value = SeverityLevel.NORMAL)
    @Step("Starting test testChangeScreenOrientationOnSearchResult")
    public void testChangeScreenOrientationOnSearchResult()
    {
        if (Platform.getInstance().isMW()) {
            return;
        }
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String titleBeforeRotation = ArticlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String titleAfterRotation = ArticlePageObject.getArticleTitle();

        Assert.assertEquals(
                "Article title have been changed after rotation",
                titleBeforeRotation,
                titleAfterRotation);

        this.rotateScreenPortrait();
        String titleAfterSecondRotation = ArticlePageObject.getArticleTitle();

        Assert.assertEquals(
                "Article title have been changed after second rotation",
                titleBeforeRotation,
                titleAfterSecondRotation);
    }

    //Тест проверяет, что после возращения приложения из Background в нем не сбросились результаты поиска.
    @Test
    @Features(value={@Feature(value="Search"),@Feature(value = "Article"),@Feature(value = "Condition")})
    @DisplayName("Check search article in Background")
    @Description("The test checks that after the application returns from the Background, the search results have not been reset in it.")
    @Severity(value = SeverityLevel.NORMAL)
    @Step("Starting test testCheckSearchArticleInBackground")
    public void testCheckSearchArticleInBackground()
    {
        if (Platform.getInstance().isMW()) {
            return;
        }
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);;
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(2);
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }
}
