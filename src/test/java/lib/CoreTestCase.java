package lib;

import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import lib.ui.WelcomePageObject;
import lib.ui.factories.WelcomePageObjectFactory;
import org.openqa.selenium.ScreenOrientation;
import java.time.Duration;

/**
 * Инициализация тестов и их завершение
 * Вся работа по запуску и остановке приложения. Здесь приложение запускается с необходимыми параметрами,
 * в том числе с разворотом в портретную ориентацию в начале тестов. А также здесь тесты останавливаются.
 * Здесь же находятся методы по развору экрана в портретную/альбомную ориентацию и уход в бэкграунд.
 */
public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        driver = Platform.getInstance().getDriver();
        this.skipWelcomePageForIOSApp();
    }

    @Override
    protected void tearDown() throws Exception
    {
        driver.quit();
        super.tearDown();
    }

    //метод переворачивает экран из альбомную в портретную
    protected void rotateScreenPortrait()
    {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    //метод переворачивает экран из портретной в альбомную
    protected void rotateScreenLandscape()
    {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    //метод отправляет приложение в background
    protected void backgroundApp(int seconds)
    {
        driver.runAppInBackground(Duration.ofSeconds(seconds));
    }

    //Метод пропускает приветственные экраны для iOS
    private void skipWelcomePageForIOSApp()
    {
        if(Platform.getInstance().isIOS()) {
            WelcomePageObject welcomePageObject = WelcomePageObjectFactory.get(driver);
            welcomePageObject.clickSkip();
        }
    }
}
