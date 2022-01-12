//методы для навигации по приложению
package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject {

    protected static String
            MY_LIST_LINK,
            OPEN_NAVIGATION;

    //Инициализация драйвера
    public NavigationUI(RemoteWebDriver driver)
    {
        super(driver);
    }

    @Step("Click by open navigation button (for MobileWeb)")
    public void openNavigation()
    {
        if(Platform.getInstance().isMW()) {
            this.waitForElementAndClick(OPEN_NAVIGATION,"Cannot find and click open navigation button.",5);
        } else {
            System.out.println("Method openNavigation() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    //метод перехоит в раздел WatchList
    @Step("Click My list")
    public void clickMyLists()
    {
        if(Platform.getInstance().isMW()) {
            this.tryClickElementWithFewAttempts(
                    MY_LIST_LINK,
                    "Cannot find navigation button to My list",
                    5
            );
        } else {
            this.waitForElementAndClick(
                    MY_LIST_LINK,
                    "Cannot find navigation button to My list",
                    15
            );
        }
    }
}
