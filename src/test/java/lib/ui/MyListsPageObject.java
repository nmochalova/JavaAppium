package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject{

    protected static String ARTICLE_BY_TITLE_TPL;
    protected static String SEARCH_RESULT_ELEMENT_BY_LIST;
    protected static String FOLDER_BY_NAME_TPL;
    protected static String REMOVE_FROM_SAVED_BUTTON;

    //метод возвращает Xpath для статьи (заменяет параметр {TITLE} на переданное имя статьи)
    private  String getSavedArticleXpathByTitle(String articleTitle)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}",articleTitle);
    }

    //метод для mobile web, возвращает кнопку удаления статьи из избранного
    private  String getRemoveButtonByTitle(String articleTitle)
    {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}",articleTitle);
    }

    //метод заменяет параметр {FOLDER_NAME} на переданное имя папки
    protected String getFolderXpathByName(String nameOfFolder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}",nameOfFolder);
    }

    //Инициализация драйвера
    public MyListsPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    //метод проверяет что указанная статья присутствует
    @Step("Checks that '{articleTitle}' is present")
    public void waitForArticleToAppearByTitle(String articleTitle)
    {
        String articleXpath;
        if (Platform.getInstance().isMW()) {
            articleXpath = getSavedArticleXpathByTitle(articleTitle);
        } else {
            articleXpath = getFolderXpathByName(articleTitle);
        }

        this.waitForElementPresent(
                articleXpath,
                "Cannot find saved article by title " + articleTitle,
                15);
    }

    //метод проверяет, что указанная статья отсуствует
    @Step("Checks that '{articleTitle}' is not present")
    public void waitForArticleToDisappearByTitle(String articleTitle)
    {
        String articleXpath = getFolderXpathByName(articleTitle);

        this.waitForElementNotPresent(
                articleXpath,
                        "Saved article still present with title" + articleTitle,
                        15);
    }

    //метод удаляет статью из списка путем свайпа влево. Название статьи является параметром.
    @Step("Remove article from My list")
    public void swipeByArticleToDelete(String articleTitle)
    {
        this.waitForArticleToAppearByTitle(articleTitle);
        String articleXpath = getFolderXpathByName(articleTitle);

        if(Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.swipeElementToLeft(
                    articleXpath,
                    "Cannot find saved article ");
        } else {
            String remove_locator = getRemoveButtonByTitle(articleTitle);
            this.waitForElementAndClick(
                    remove_locator,
                    "Cannot click button to remove article from saved.",
                    10
            );
        }
        if(Platform.getInstance().isIOS()) {
                this.clickElementToTheRightUpperCorner(articleXpath,"Cannot find saved article ");
        }

        if(Platform.getInstance().isMW()) {
            driver.navigate().refresh();
        }

        this.waitForArticleToDisappearByTitle(articleTitle);
    }

    //Подсчет количества статей в Избранном
    @Step("Get amount of found article by My list")
    public int getAmountOfFoundArticleByList()
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT_BY_LIST,
                "Cannot find anything by the request",
                15);

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT_BY_LIST);
    }

    //метод, который выбирает ранее созданную папку в My list по имени папки
    @Step("Open folder by name")
    public void openFolderByName(String nameOfFolder)
    {
        String folderNameXpath = getFolderXpathByName(nameOfFolder);

        this.waitForElementAndClick(
                //  By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='" + nameOfFolder + "']"),
                //  By.xpath(folderNameXpath),
                folderNameXpath,
                "Cannot find folder by name" + nameOfFolder,
                15);
    }
}
