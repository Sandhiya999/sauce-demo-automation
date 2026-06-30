package base;
import Utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;


    public class BaseTest {
        protected static Logger log = LogManager.getLogger(base.BaseTest.class);
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected WebDriverWait wait;
    public WebDriver getDriver()
    {
        return driver.get();
    }
    protected void setDriver(WebDriver driverInstance)
    {
        driver.set(driverInstance);
    }
    protected void unload()
    {
        driver.remove();
    }

    @Parameters("browser")
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional String browser) {
        if (browser == null || browser.isEmpty()) {
            browser = ConfigReader.get("browser");
        }

           launchBrowser(browser);

        getDriver().manage().window().maximize();
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        log.info("Browser launched successfully");
    }

    public void launchBrowser(String browser)
    {
        log.info("Launching Browser");
        boolean isCI = System.getenv("CI") != null;
        log.info("isCI = " + isCI);
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless"));
        WebDriver driverInstance;
        if(browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            if(headless) {
                addHeadlessOption(options, headless);
            }
            driverInstance = new ChromeDriver(options);
        }
        else if (browser.equalsIgnoreCase("edge")) {
            //WebDriverManager.edgedriver().setup();
            System.setProperty("webdriver.edge.driver",ConfigReader.get("edgeDriverPath"));
            EdgeOptions options = new EdgeOptions();
            if(headless) {
                addHeadlessOption(options, headless);
            }
            driverInstance =new EdgeDriver(options);
        } else if (browser.equals("firefox"))
            {   ////WebDriverManager.firefoxdriver().setup();
                System.setProperty("webdriver.firefox.driver",ConfigReader.get("firefoxDriverPath"));
                FirefoxOptions options = new FirefoxOptions();
                if(headless)
                {
                    options.addArguments("--headless=new");
                }
                driverInstance = new FirefoxDriver(options);
            }

         else {
            log.error("Browser not supported" + browser);
            throw new RuntimeException("Browser not supported" + browser);
        }
        setDriver(driverInstance);

    }

    private void  addHeadlessOption(ChromiumOptions<?> options,boolean headless)
    {
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
    }

    @AfterMethod(alwaysRun = true)
        public void tearDown()
        {
            if(getDriver()!=null) {
                getDriver().quit();
                unload();
            }
            log.info("browser closed successfully");
    }
}
