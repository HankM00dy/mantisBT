package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.FileReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {

    private final Properties properties;
    private WebDriver wd;

    private String browser;
    private RegistrationHelper registrationHelper;

    // В конструкторе передается тип браузера, выставляется в классе TestBase
    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws Exception {
        String nameOfPropertyFile = System.getProperty("nameOfPropertyFile", "local");
        properties.load(new FileReader(String.format("src/test/resources/%s.properties", nameOfPropertyFile)));
    }

    public void stop() {
        if (wd != null) {
            wd.quit();

        }
    }


    public HttpSession newSession() {
        return new HttpSession(this);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public RegistrationHelper registration() {
        if (registrationHelper == null) {
            registrationHelper = new RegistrationHelper(this);
        }
        return registrationHelper;
    }

    public WebDriver getDriver() {
        if (wd == null) {
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
        return wd;
    }
}
