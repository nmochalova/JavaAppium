package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;

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
        FOLDER_BY_NAME_TPL;

    //Инициализация драйвера
    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    //метод заменяет параметр {FOLDER_NAME} на переданное имя папки
    protected String getFolderXpathByName(String nameOfFolder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}",nameOfFolder);
    }

    //метод проверяет наличие статьи на странице
    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(
                TITLE,
                "Cannot find article title on page",
                15);
    }

    //метод возвращает название статьи
    public String getArticleTitle()
    {
        WebElement titleElement = waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            return titleElement.getAttribute("text");
        } else {
            return titleElement.getAttribute("name");
        }
    }

    public void swipeToFooter()
    {
        if(Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    100
            );
        } else {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT,
                    "Cannot find the end of the article",
                    100);
        }
    }

    //метод добавляет статью в новый список Reading list (для Android)
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
    public void addArticlesToMySaved()
    {
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON,"Cannot find option to add article to reading list",5);
    }

    //метод, который выбирает ранее созданную папку в reading list по имени папки
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

    //метод добавляет статью в ранее созданный список Reading list
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
    public void closeArticle()
    {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article,cannot find X link",
                10
        );
    }
}
