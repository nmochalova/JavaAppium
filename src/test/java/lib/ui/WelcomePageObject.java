package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import static lib.Platform.*;

/**
 *     Класс, содержащий методы, которые прощелкивает все приветственные экраны
 *     ВНИМАНИЕ!! Andoroid : для новой версии apks/org.wikipedia_50377_apps.evozi.com.apk (переключить в CoreTestCase)
 *                     iOS : для версии apks/Wikipedia.app
 */
public class WelcomePageObject extends MainPageObject {
    protected static String
            STEP_LEARN_MORE_LINK,
            STEP_NEW_WAYS_TO_EXPLORE ,
            STEP_ADD_OR_EDIT_PREFERRED ,
            STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK ,
            NEXT_LINK,
            GET_STARTED_BUTTON,
            SKIP;

    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void waitForScreenAddLang()
    {
        this.waitForElementPresent(STEP_LEARN_MORE_LINK,
                "Cannot find link 'ADD OR EDIT LANGUAGES'",
                10);
    }

    public void waitForNewWaysToExp()
    {
        this.waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE,
                "Cannot find 'New ways to explore' article",
                10);
    }

    public void waitForReadingList()
    {
        this.waitForElementPresent(STEP_ADD_OR_EDIT_PREFERRED,
                "Cannot find 'Reading lists with sync' article",
                10);
    }

    public void waitForSendAnonymousData()
    {
        this.waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED_LINK,
                "Cannot find 'Send anonymous data' article",
                10);
    }

    public void clickNextButton()
    {
        this.waitForElementAndClick(NEXT_LINK,
                "Cannot find and click 'Continue' button",
                10);
    }

    public void clickGetStartedButton()
    {
        this.waitForElementAndClick(GET_STARTED_BUTTON,
                "Cannot find and click 'Get Started' button",
                10);
    }

    public void clickSkip()
    {
         this.waitForElementAndClick(SKIP,"Cannot find and click skip button", 5);
    }
}
