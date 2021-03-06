package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Тесты на работу с сохраненным списком статей (My lists)
 */
@Epic("Tests for My Lists")
public class MyListsTests extends CoreTestCase
{
    private static final String nameOfFolder = "prog";
    private static final String
        login = "Nmochalova",
        password = "6ZFsrieNXH";

    //Тест сохраняет статью в список, потом находит ее и удаляет из списка (свайпом влево).
    @Test
    @Features(value={@Feature(value="Auth"),@Feature(value = "Article"),@Feature(value = "My lists"),@Feature(value = "Search")})
    @DisplayName("Save one article to My list")
    @Description("The test saves the article to My list, then finds it and deletes it from My list.")
    @Severity(value = SeverityLevel.CRITICAL)
    @Step("Starting test testSaveFirstArticleToMyList")
    public void testSaveFirstArticleToMyList() throws InterruptedException {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String articleTitle = ArticlePageObject.getArticleTitle();

        if(Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(nameOfFolder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        if(Platform.getInstance().isMW()) {
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login,password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            Assert.assertEquals("We are not on the same page after login.",
                    articleTitle,
                    ArticlePageObject.getArticleTitle()
            );

            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListPageObjectFactory.get(driver);

        if(Platform.getInstance().isAndroid()) {
            MyListsPageObject.openFolderByName(nameOfFolder);
        }

        MyListsPageObject.swipeByArticleToDelete(articleTitle);
    }

    //Ex5: Тест: сохранение двух статей. Тест сохраняет две статьи в одну папку и потом удаляет одну из статей. Убеждается, что вторая осталась.
    @Test
    @Features(value={@Feature(value="Auth"),@Feature(value = "Article"),@Feature(value = "My lists"),@Feature(value = "Search")})
    @DisplayName("Save two articles to My list")
    @Description("The test saves two articles in one folder and then deletes one of the articles. Makes sure that the second one remains.")
    @Severity(value = SeverityLevel.CRITICAL)
    @Step("Starting test testSaveTwoArticlesInOneFolder")
    public void testSaveTwoArticlesInOneFolder() throws InterruptedException {
        //Добавляем первую статью
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);;

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String articleTitle = ArticlePageObject.getArticleTitle();

        String nameOfFolder = "prog";
        if(Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(nameOfFolder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        if(Platform.getInstance().isMW()) {
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login,password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            Assert.assertEquals("We are not on the same page after login.",
                    articleTitle,
                    ArticlePageObject.getArticleTitle()
            );

            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        //Добавляем вторую статью
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Appium");
        ArticlePageObject.waitForTitleElement();
        String titleArcticleExpected = ArticlePageObject.getArticleTitle();
        if(Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToExistingMyList(nameOfFolder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        //Идем в сохраненную группу и удаляем ону статью
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListPageObjectFactory.get(driver);

        if(Platform.getInstance().isAndroid()) {
            MyListsPageObject.openFolderByName(nameOfFolder);
        }
        MyListsPageObject.swipeByArticleToDelete(articleTitle);

        //Проверяем, что в списке осталась 1 статья и ее заголовок соответствует второй статье
        int amountOfArticleInList =  MyListsPageObject.getAmountOfFoundArticleByList();
        Assert.assertTrue(
                "We found two articles but must have only one!!",
                amountOfArticleInList == 1);

        MyListsPageObject.waitForArticleToAppearByTitle(titleArcticleExpected);

        //Переходим в оставшуюся статью и убеждаемся, что title совпадает
//        SearchPageObject.clickByArticleWithSubstring("Appium");
//        ArticlePageObject.waitForTitleElement();
//        String titleArcticleActual = ArticlePageObject.getArticleTitle();
//        assertEquals(
//                "Title Arcticle does not equal 'Appium'",
//                titleArcticleExpected,
//                titleArcticleActual);
    }
}
