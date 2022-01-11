package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Методы для работы со статьями
 */
abstract public class ArticlePageObject extends MainPageObject
{
    protected static String
        TITLE,
        FOOTER_ELEMENT,
        OPTIONS_BUTTON,
        OPTIONS_ADD_TO_MY_LIST_BUTTON,
        ADD_TO_MY_LIST_OVERLAY,
        MY_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON,
        CLOSE_ARTICLE_BUTTON,
        FOLDER_BY_NAME_TPL,
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON;

    //Инициализация драйвера
    public ArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    //метод заменяет параметр {FOLDER_NAME} на переданное имя папки
    protected String getFolderXpathByName(String nameOfFolder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}",nameOfFolder);
    }

    //метод проверяет наличие статьи на странице
    @Step("Waiting for title on the article page")
    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(
                TITLE,
                "Cannot find article title on page",
                15);
    }

    //метод возвращает название статьи
    @Step("Get article title")
    public String getArticleTitle()
    {
        WebElement titleElement = waitForTitleElement();
        screenshot(this.takeScreenshot("article_title"));
        if (Platform.getInstance().isAndroid()) {
            return titleElement.getAttribute("text");
        } else if(Platform.getInstance().isIOS()) {
            return titleElement.getAttribute("name");
        } else {
            return titleElement.getText();
        }
    }

    @Step("Swiping to footer on article page")
    public void swipeToFooter()
    {
        if(Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    100
            );
        } else if(Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    100);
        } else {
            this.scrollWebPageTillElementNotVisible(
                    FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    40
            );
            screenshot(this.takeScreenshot("footer_page"));
        }
    }

    //метод добавляет статью в новый список Reading list (для Android)
    @Step("Adding the article to new My list (for Android)")
    public void addArticleToMyList(String nameOfFolder)
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannon find button to open article options",
                10
        );
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add articale to reading list",
                10
        );
        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' in overlay",
                10
        );
        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of article folder",
                10
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                nameOfFolder,
                "Cannot put text into articles folder input",
                10
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press Ok button",
                10);
    }

    //метод добавляет статью в избранное (для iOS)
    @Step("Adding the article to my saved articles (for IOS) ")
    public void addArticlesToMySaved()
    {
        if (Platform.getInstance().isMW()) {
            this.removeArticleFromSavedIfItAdded();
        }
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,"Cannot find option to add article to reading list",5);
    }

    //метод, который удаляет статью, если она уже была в Избранном (для mobile web)
    @Step("Removing the article from Watchlist if it has been added (for mobile web)")
    public void removeArticleFromSavedIfItAdded() {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) { //если кнопка удаления статьи из Избранного присутствует
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove an article from saved",
                    1
            );
            this.waitForElementPresent(
                    OPTIONS_ADD_TO_MY_LIST_BUTTON,
                    "Cannot find button to add an article to saved list after removing it from this list before",
                    1
            );
        }
    }

    //метод, который выбирает ранее созданную папку в reading list по имени папки
    @Step("Opening folder by name in reading list (for Android)")
    public void openFolderByName(String nameOfFolder)
    {
        String folderNameXpath = getFolderXpathByName(nameOfFolder);

        this.waitForElementAndClick(
                folderNameXpath,
                "Cannot find folder by name" + nameOfFolder,
                15);
    }

    //метод добавляет статью в ранее созданный список Reading list
    @Step("Adding the article to existing My list (for Android)")
    public void addArticleToExistingMyList(String nameOfFolder)
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannon find button to open article options",
                15
        );
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add articale to reading list",
                15
        );

        openFolderByName(nameOfFolder);
    }

    //метод закрывает статью (нажимет на Х в углу статьи)
    @Step("Closing the article by X button")
    public void closeArticle()
    {
        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot close article,cannot find X link",
                    10
            );
        } else {
            System.out.println("Method closeArticle() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }
}
