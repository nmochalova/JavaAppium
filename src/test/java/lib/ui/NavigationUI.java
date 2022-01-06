//методы для навигации по приложению
package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject {

    protected static String
            MY_LIST_LINK;

    //Инициализация драйвера
    public NavigationUI(RemoteWebDriver driver)
    {
        super(driver);
    }

    public void clickMyLists()
    {
        this.waitForElementAndClick(
                MY_LIST_LINK,
                "Cannot find navigation button to My list",
                15
        );
    }
}
