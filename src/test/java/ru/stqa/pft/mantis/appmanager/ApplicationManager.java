package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {

    private final Properties properties;
    WebDriver wd;

    private String browser;

    // В конструкторе передается тип браузера, выставляется в классе TestBase
    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws Exception {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/$s.properties", target))));

        if (browser.equals(BrowserType.FIREFOX)) {
            System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
            wd = new FirefoxDriver();
        } else if (browser.equals(BrowserType.CHROME)) {
            System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
            wd = new ChromeDriver();
        } else if (browser.equals(BrowserType.OPERA)) {
            System.setProperty("webdriver.opera.driver", "src/main/resources/operadriver.exe");
            wd = new OperaDriver();
        }

        wd.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        wd.manage().window().maximize();
        wd.get(properties.getProperty("web.baseUrl"));
    }

    public void stop() {
        wd.quit();
    }
}
