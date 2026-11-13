package qa.base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Quando un test fallisce salva uno screenshot in target/screenshots. */
public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object instance = result.getInstance();
        if (!(instance instanceof BaseTest base) || base.getDriver() == null) {
            return;
        }
        try {
            byte[] png = ((TakesScreenshot) base.getDriver()).getScreenshotAs(OutputType.BYTES);
            Path dir = Paths.get("target", "screenshots");
            Files.createDirectories(dir);
            Files.write(dir.resolve(result.getName() + ".png"), png);
        } catch (IOException e) {
            System.err.println("Screenshot non salvato: " + e.getMessage());
        }
    }
}
