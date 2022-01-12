package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import lib.ui.factories.WelcomePageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

@Epic("Tests for started work")
public class GetStartedTest extends CoreTestCase
{
    //Тест, который прощелкивает все приветственные экраны.
    //Если платформа android и старое приложение wiki, то тест не выполняем.
    //Внимание!!! Чтобы тест заработал для новой wiki нужно сменить app в Platform.getAndroidDesiredCapabilities
    //Чтобы тест заработал для IOS нужно переключить параметр PLATFORM=iOS в (Edit Configuration/JUnit/Environment variables),
    //предварительно удалив все старые запуски JUnit
    @Test
    @Features(value={@Feature(value="Welcome")})
    @DisplayName("Clicks through all welcome screens")
    @Description("When opening the application on iOS and new version Wiki for Android, we skip the welcome screens. Nothing happens for Mobile Web and the old version Wiki for Android. ")
    @Severity(value = SeverityLevel.MINOR)
    @Step("Starting test testPassThroughWelcome")
    public void testPassThroughWelcome()
    {
        DesiredCapabilities capabilities = Platform.getAndroidDesiredCapabilities();
        Object appCapabilities = capabilities.getCapability("app");

        if (Platform.getInstance().isMW()) {
            return;
        }

        if (Platform.getInstance().isAndroid())
        {
           if (appCapabilities.equals("C:\\Work\\Git\\JavaAppiumAutomation\\apks\\old-wiki.apk")) {
                return;
            }
        }

        WelcomePageObject welcomePageObject = WelcomePageObjectFactory.get(driver);

        welcomePageObject.waitForScreenAddLang();
        welcomePageObject.clickNextButton();
        welcomePageObject.waitForNewWaysToExp();
        welcomePageObject.clickNextButton();
        welcomePageObject.waitForReadingList();
        welcomePageObject.clickNextButton();
        welcomePageObject.waitForSendAnonymousData();
        welcomePageObject.clickGetStartedButton();
    }
}
