package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;

abstract public class MyListsPageObject extends MainPageObject{

    protected static String ARTICLE_BY_TITLE_TPL;
    protected static String SEARCH_RESULT_ELEMENT_BY_LIST;
    protected static String FOLDER_BY_NAME_TPL;

    //метод возвращает Xpath для статьи (заменяет параметр {TITLE} на переданное имя статьи)
    private  String getSavedArticleXpathByTitle(String articleTitle)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}",articleTitle);
    }

    //метод заменяет параметр {FOLDER_NAME} на переданное имя папки
    protected String getFolderXpathByName(String nameOfFolder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}",nameOfFolder);
    }

    //Инициализация драйвера
    public MyListsPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    //метод проверяет что указанная статья присутствует
    public void waitForArticleToAppearByTitle(String articleTitle)
    {
        String articleXpath = getFolderXpathByName(articleTitle);

        this.waitForElementPresent(
                articleXpath,
                "Cannot find saved article by title" + articleTitle,
                15);
    }

    //метод проверяет, что указанная статья отсуствует
    public void waitForArticleToDisappearByTitle(String articleTitle)
    {
        String articleXpath = getFolderXpathByName(articleTitle);

        this.waitForElementNotPresent(
                articleXpath,
                        "Saved article still present with title" + articleTitle,
                        15);
    }

    //метод удаляет статью из списка путем свайпа влево. Название статьи является параметром.
    public void swipeByArticleToDelete(String articleTitle)
    {
        this.waitForArticleToAppearByTitle(articleTitle);
        String articleXpath = getFolderXpathByName(articleTitle);
        this.swipeElementToLeft(
                articleXpath,
                "Cannot find saved article");
        if(Platform.getInstance().isIOS()) {
                this.clickElementToTheRightUpperCorner(articleXpath,"Cannot find saved article");
        }
        this.waitForArticleToDisappearByTitle(articleTitle);
    }

    //Подсчет количества статей в Избранном
    public int getAmountOfFoundArticleByList()
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT_BY_LIST,
                "Cannot find anything by the request",
                15);

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT_BY_LIST);
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
}
