import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestCase {
    static WebDriver driver;
    static BatchInfo myBatch;
    static Configuration suiteConfig;
    static EyesRunner testRunner;
    Eyes eyes;

    @BeforeAll
    public static void beforeAll() {
        myBatch = new BatchInfo("My First Batch");
        driver = WebDriverManager.chromedriver().create();
        suiteConfig = new Configuration();
        suiteConfig.setApiKey("2y99A2VCFcvlO5dDNa64OZgIqQf1087CJKd99paEcoqoVqs110");
        suiteConfig.setBatch(myBatch);
//        testRunner = new ClassicRunner();

        //Desktop Browsers for UltraFast Grid
        suiteConfig.addBrowser(800, 600, BrowserType.CHROME);
        suiteConfig.addBrowser(1600, 1200, BrowserType.FIREFOX);
        suiteConfig.addBrowser(1024, 768, BrowserType.SAFARI);

        //Mobile Devices for UltraFast Grid
        suiteConfig.addDeviceEmulation(DeviceName.Pixel_2, ScreenOrientation.PORTRAIT);
        suiteConfig.addDeviceEmulation(DeviceName.Nexus_10, ScreenOrientation.PORTRAIT);
        testRunner = new VisualGridRunner(new RunnerOptions().testConcurrency(5));
    }
    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        eyes = new Eyes(testRunner);
        eyes.setConfiguration(suiteConfig);
        eyes.open(
                driver,
                "My First Tests",
                testInfo.getTestMethod().get().getName(),
                new RectangleSize(1000, 600)
        );
    }

    @Test
    public void applitoolsHelloWorld() {
        driver.get("https://applitools.com/helloworld");
        eyes.check(Target.window());
    }

    @Test
    public void exampleTest(){
        driver.get("https://example.com");
        eyes.check(Target.window());
    }

    @AfterEach
    public void afterEach() {
            eyes.closeAsync();
    }
    @AfterAll
    public static void afterAll(){
            driver.close();
            TestResultsSummary results = testRunner.getAllTestResults();
            System.out.println(results);
        }
    }

