package lib.ui;

import io.qameta.allure.Attachment;
import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

    //метод добавляет скриншот к степу (в allure)
    @Attachment
    public static byte[] screenshot (String path)
    {
        byte[] bytes = new byte[0];

        try {
            bytes = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Cannot get bytes from screenshot. Error: " + e.getMessage());
        }
        return bytes;
    }

    //метод создания скриншота (для allure)
    public static String takeScreenshot(String name, TakesScreenshot takesScreenshot)
    {
        TakesScreenshot ts = takesScreenshot;
        File source = ts.getScreenshotAs(OutputType.FILE);
        long systemMilliseconds = System.currentTimeMillis();
        String path = System.getProperty("user.dir") + "/screenshot/" + name +"_" + systemMilliseconds + "_screenshot.png";
        try {
            FileUtil.copyFile(source,new File(path));
            System.out.println("The screenshot was taken: " + path);
        } catch (Exception e) {
            System.out.println("Cannot take screenshot. Error: " + e.getMessage());
        }
        return path;
    }
}
