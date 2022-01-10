//содержит методы, к которым будут обращаться тесты
package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver)
    {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(String locator, String error_messanger, long timeoutInSecond)
    {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_messanger + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementPresent(String locator, String error_messanger)
    {
        return waitForElementPresent(locator,error_messanger,5);
    }

    public WebElement waitForElementAndClick(String locator, String error_messanger, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(locator,error_messanger,timeoutInSecond);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String error_messanger, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(locator,error_messanger,timeoutInSecond);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String error_messanger, long timeoutInSecond)
    {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver,timeoutInSecond);
        wait.withMessage(error_messanger + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClear(String locator, String error_messanger, long timeoutInSecond)
    {
        WebElement element = waitForElementPresent(locator,error_messanger,timeoutInSecond);
        element.clear();
        return element;
    }

    /**
     * Метод который принимает локатор текст и сообщение об ошибке.
     * @param locator    Локатор
     * @param text_element  Текст который должен содержаться в элементе локатора
     * @param error_messanger   Сообщение об ошибке если текста нет такого
     * @return  Возвращает true если в таком элементе содержится нужный текст.
     *          false если текст другой.
     */
    public boolean assertElementHasText (String locator, String text_element, String error_messanger)
    {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver,5);
        wait.withMessage(error_messanger + "\n");
        return wait.until(
                ExpectedConditions.attributeContains(by,"text",text_element)
        );
    }

    /**
     * Метод проверяет налиличие нескольких элементов на странице
     * @param locator локатор
     * @param error_messanger ошибка в случае, если элементы не найдены
     * @param timeoutInSecond  время ожидания загрузки страницы
     * @return массив найденных элементов на странице по заданному локатору
     */
    public List<WebElement> waitForElementsPresent(String locator, String error_messanger, long timeoutInSecond)
    {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSecond);
        wait.withMessage(error_messanger + "\n");
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by)
        );
    }

    public void swipeUp(int timeOfSwipe)
    {
        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver) driver);           //важно выбрать метод для Appium
            Dimension size = driver.manage().window().getSize();    //взять размер экрана
            int x = size.width / 2;                                 //условные координаты х: ширина экрана пополам
            int startY = (int) (size.height * 0.8);                 //условная координата у: 80% от высоты экрана
            int endY = (int) (size.height * 0.2);                   //условная координата у: свайпаем до 20% по высоте экрана
            action
                    .press(PointOption.point(x, startY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                    .moveTo(PointOption.point(x, endY))
                    .release()
                    .perform();
        } else {
            System.out.println("Method swipeUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void swipeUpQuick()
    {
        swipeUp(200);
    }

    //метод эмитации скрола для mobile Web
    public void scrollwebPageUp() {
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor JSExecuter = (JavascriptExecutor) driver;
            JSExecuter.executeScript("window.scrollBy(0,250)");
        } else {
            System.out.println("Method scrollwebPageUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    //метод будет делать скрол до тех пор пока элемент не будет в поле зрения пользователя
    public void scrollWebPageTillElementNotVisible(String locator, String error_message, int max_swipes) {
        int already_swiped = 0;

        WebElement element = this.waitForElementPresent(locator, error_message);

        while (!this.isElementLocatedOnTheScreen(locator)) {
            scrollwebPageUp();
            ++already_swiped;
            if (already_swiped > max_swipes) {
                Assert.assertTrue(error_message, element.isDisplayed());
            }
        }
    }

   //Свайп вниз. Метод для Andoid.
    public void swipeUpToFindElement(String locator,String error_messange, int maxSwipes)
    {
        By by = this.getLocatorByString(locator);
        int alreadySwiped = 0;                              //счетчик свайпов
        while (driver.findElements(by).size() == 0) {       //свайпаем пока эл-т не появится на странице
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(locator,"Cannot find element by swiping up.\n" + error_messange,0);
                return;
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    //методы для iOs вместо swipeUpToFindElement, потому что iOS считает что футер есть на странице всегда.
    ///////////////////////////////////////////////////////////
    public void swipeUpTillElementAppear(String locator, String error_message, int max_swipes)
    {
        int alreadySwiped = 0;                              //счетчик свайпов
        while (!this.isElementLocatedOnTheScreen(locator))  //пока элемент не находится на экране, мы будем swipeUpQuick и инкрементировать alreadySwiped
        {
            if(alreadySwiped > max_swipes) {                //при превышение максмального кол-ва свайпов max_swipes выход
                Assert.assertTrue(error_message, this.isElementLocatedOnTheScreen(locator));
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public boolean isElementLocatedOnTheScreen(String locator)
    {
        //находим эл-т по локатору и получаем его расположение по оси Y
        int element_location_by_y = this.waitForElementPresent(locator,"Cannot find element by locator",5)
                .getLocation()
                .getY();
        if (Platform.getInstance().isMW()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            Object js_result = JSExecutor.executeScript("return window.pageYOffset");
            element_location_by_y -= Integer.parseInt(js_result.toString());
            //сначала результат выполнения js-скрипта переводим в стринг, потом парсим его в int, который затем вычитаем из element_location_by_y
        }
        int screen_size_by_y = driver.manage().window().getSize().getHeight(); //получаем длину всего экрана
        //пока не доскролим до переменной screen_size_by_y будем возвращать false, как только доберемся - true
        return element_location_by_y < screen_size_by_y;
    }
    ///////////////////////////////////////////////////////////

    // Для iOS: метод будет кликать по кнопке удаления (красная корзина) при удалении статьи из избранного
    public void clickElementToTheRightUpperCorner(String locator, String error_message)
    {
        if (driver instanceof AppiumDriver) {
            WebElement element = this.waitForElementPresent(locator + "/..", error_message); //locator + "/.." - означает родительский эл-т локатора
            int right_x = element.getLocation().getX();
            int upper_y = element.getLocation().getY();
            int lower_y = upper_y + element.getSize().getWidth();
            int middle_y = (upper_y + lower_y) / 2;
            int width = element.getSize().getWidth();

            int point_to_click_x = (right_x + width) - 3;  //на 3 пикселя левее чем ширина элемента
            int point_to_click_y = middle_y;

            TouchAction action = new TouchAction((AppiumDriver) driver);
            action.tap(PointOption.point(point_to_click_x, point_to_click_y)).perform();
        } else {
            System.out.println("Method clickElementToTheRightUpperCorner() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public  void swipeElementToLeft(String locator, String error_messange)
    {
        if (driver instanceof AppiumDriver) {
            WebElement element = waitForElementPresent(
                    locator,
                    error_messange,
                    10);
            int leftX = element.getLocation().getX();           //самая левая точка элемента по оси Х
            int rightX = leftX + element.getSize().getWidth();  //к крайней левой точки Х прибавляем ширину экрана
            int upperY = element.getLocation().getY();          //самая верхняя точка элемента по оси У
            int lowerY = upperY + element.getSize().getHeight();//прибавляем к верхней точке по оси У высоту элемента
            int middleY = (upperY + lowerY) / 2;                //середина элемента по оси У

            TouchAction action = new TouchAction((AppiumDriver) driver);           //важно выбрать метод для Appium
            action.press(PointOption.point(rightX, middleY));
            action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)));

            //В iOS и Android различается отношение к координатам. Если в Android мы работаем с относительными координатами, относительно эл-та
            //и свайпаем от точке к точке, то в iOS надо свайпать на определенную ширину от начальной точки. Поэтому для iOS будем свайпать на
            //всю ширину элемента
            if (Platform.getInstance().isAndroid()) {
                action.moveTo(PointOption.point(leftX, middleY));
            } else {
                int offset_x = (-1 * element.getSize().getWidth());         //(-1 * ширину эл-та), т.е. крайняя левая точка
                action.moveTo(PointOption.point(offset_x, 0));       //свайп на всю ширину эл-та
            }
            action.release();
            action.perform();
        } else {
            System.out.println("Method swipeElementToLeft() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public int getAmountOfElements(String locator)
    {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return  elements.size();
    }

    //метод, который возвращает true, если элемент присуствует на странице
    public boolean isElementPresent(String locator) {
        return getAmountOfElements(locator) > 0;
    }

    //метод для Web mobile. Кликает по элементу выезжающего меню в момент его выезжания до того пока не кликнет.
    public void tryClickElementWithFewAttempts(String locator, String error_message, int amount_of_attempts) {
        int current_attempts = 0;
        boolean need_more_attempts = true;
        while(need_more_attempts) {
            try {
               this.waitForElementAndClick(locator,error_message,1);
               need_more_attempts = false;
            } catch (Exception e) {
                if(current_attempts > amount_of_attempts) {
                    this.waitForElementAndClick(locator,error_message,1);
                }
            }
            ++current_attempts;
        }
    }

    public void assertElementNotPresent(String locator, String error_messange)
    {
        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements > 0) {
            String defaultMessage = "An element '" + locator + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + error_messange);
        }
    }

    public void assertElementPresent(String locator, String error_messange)
    {
        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements == 0) {
            String defaultMessage = "A title not present.";
            throw new AssertionError(defaultMessage + " " + error_messange);
        }
    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(locator, error_message,timeoutInSeconds);
        return  element.getAttribute(attribute);
    }

    private By getLocatorByString(String locatorWithType)
    {
        String[] explodedLocator = locatorWithType.split(Pattern.quote(":"),2);
        String byType = explodedLocator[0];
        String locator = explodedLocator[1];

        if (byType.equals("xpath")) {
            return By.xpath(locator);
        } else if (byType.equals("id")) {
            return  By.id(locator);
        } else if (byType.equals("css")) {
            return  By.cssSelector(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locatorWithType);
        }
    }
}
