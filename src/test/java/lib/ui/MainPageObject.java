//содержит методы, к которым будут обращаться тесты
package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver)
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
        TouchAction action = new TouchAction(driver);           //важно выбрать метод для Appium
        Dimension size = driver.manage().window().getSize();    //взять размер экрана
        int x = size.width / 2;                                 //условные координаты х: ширина экрана пополам
        int startY = (int) (size.height * 0.8);                 //условная координата у: 80% от высоты экрана
        int endY = (int) (size.height * 0.2);                   //условная координата у: свайпаем до 20% по высоте экрана
        action
                .press(PointOption.point(x,startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
                .moveTo(PointOption.point(x, endY))
                .release()
                .perform();
    }

    public void swipeUpQuick()
    {
        swipeUp(200);
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
        int screen_size_by_y = driver.manage().window().getSize().getHeight(); //получаем длину всего экрана
        //пока не доскролим до переменной screen_size_by_y будем возвращать false, как только доберемся - true
        return element_location_by_y < screen_size_by_y;
    }
    ///////////////////////////////////////////////////////////

    // Для iOS: метод будет кликать по кнопке удаления (красная корзина) при удалении статьи из избранного
    public void clickElementToTheRightUpperCorner(String locator, String error_message)
    {
        WebElement element = this.waitForElementPresent(locator + "/..",error_message); //locator + "/.." - означает родительский эл-т локатора
        int right_x = element.getLocation().getX();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getWidth();
        int middle_y = (upper_y + lower_y) / 2;
        int width = element.getSize().getWidth();

        int point_to_click_x = (right_x + width) - 3;  //на 3 пикселя левее чем ширина элемента
        int point_to_click_y = middle_y;

        TouchAction action = new TouchAction(driver);
        action.tap(PointOption.point(point_to_click_x,point_to_click_y)).perform();
    }

    public  void swipeElementToLeft(String locator, String error_messange)
    {
        WebElement element = waitForElementPresent(
                locator,
                error_messange,
                10);
        int leftX = element.getLocation().getX();           //самая левая точка элемента по оси Х
        int rightX = leftX + element.getSize().getWidth();  //к крайней левой точки Х прибавляем ширину экрана
        int upperY = element.getLocation().getY();          //самая верхняя точка элемента по оси У
        int lowerY = upperY + element.getSize().getHeight();//прибавляем к верхней точке по оси У высоту элемента
        int middleY = (upperY + lowerY) / 2;                //середина элемента по оси У

        TouchAction action = new TouchAction(driver);           //важно выбрать метод для Appium
        action.press(PointOption.point(rightX,middleY));
        action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)));

        //В iOS и Android различается отношение к координатам. Если в Android мы работаем с относительными координатами, относительно эл-та
        //и свайпаем от точке к точке, то в iOS надо свайпать на определенную ширину от начальной точки. Поэтому для iOS будем свайпать на
        //всю ширину элемента
        if(Platform.getInstance().isAndroid()) {
                action.moveTo(PointOption.point(leftX, middleY));
        } else {
            int offset_x = (-1 * element.getSize().getWidth());         //(-1 * ширину эл-та), т.е. крайняя левая точка
            action.moveTo(PointOption.point(offset_x, 0));       //свайп на всю ширину эл-та
        }
        action.release();
        action.perform();
    }

    public int getAmountOfElements(String locator)
    {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return  elements.size();
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
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locatorWithType);
        }
    }
}
