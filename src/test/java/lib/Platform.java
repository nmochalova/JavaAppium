package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class Platform {
    private static final String PLATFORM_IOS = "iOS";
    private static final String PLATFORM_ANDROID = "android";
    private static final String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    private static Platform instance;
    //приватный контруктор
    public Platform() {}

    public static Platform getInstance()
    {
        if (instance == null) {
            instance = new Platform();
        }
        return instance;
    }

    //метод возвращает настройки Capabilities для нужной платформы
    public AppiumDriver getDriver() throws Exception
    {
        URL URL = new URL(APPIUM_URL);
        if (this.isAndroid()) {
            return new AndroidDriver(URL,this.getAndroidDesiredCapabilities());
        } else if (this.isIOS()) {
            return new AndroidDriver(URL,this.getIOSDesiredCapabilities());
        } else {
            throw new Exception("Cannot detected type of driver. Platform value: " + this.getPlatformVar());
        }
    }

    //метод возвращает значение true, если платформа в переменной среды PLATFORM = Android и false, если iOs
    public boolean isAndroid()
    {
        return isPlatform(PLATFORM_ANDROID);
    }

    //метод возвращает значение true, если платформа в переменной среды PLATFORM = iOs и false, если Android
    public boolean isIOS()
    {
        return isPlatform(PLATFORM_IOS);
    }

    //метод получает параметры Capability для платформы Android
    public static DesiredCapabilities getAndroidDesiredCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium"); //"Appium" "UIAutomator2
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
       // capabilities.setCapability("app","C:\\Work\\Git\\JavaAppiumAutomation\\apks\\org.wikipedia_50377_apps.evozi.com.apk");
        capabilities.setCapability("app","C:\\Work\\Git\\JavaAppiumAutomation\\apks\\old-wiki.apk");
        capabilities.setCapability("orientation","PORTRAIT"); //LANDSCAPE, PORTRAIT

        return capabilities;
    }

    //метод получает параметры Capability для платформы iOS
    private DesiredCapabilities getIOSDesiredCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone SE"); //название симулятора
        capabilities.setCapability("platformVersion", "11.3");
        capabilities.setCapability("app", "C:\\Work\\Git\\JavaAppiumAutomation\\apks\\Wikipedia.app");
        capabilities.setCapability("orientation", "PORTRAIT"); //LANDSCAPE, PORTRAIT

        return capabilities;
    }

    //метод возвращает значение переменной окружения PLATFORM (Edit Configuration/Junit/Environment variables)
    private String getPlatformVar()
    {
        return System.getenv("PLATFORM");
    }

    //метод сравнивает полученную строку с переменной окружения PLATFORM
    private boolean isPlatform(String myPlatform)
    {
        String platform = this.getPlatformVar();
        return myPlatform.equals(platform);
    }
}
