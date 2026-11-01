package utilities;

import listeners.SuiteListeners;
import listeners.TestListeners;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;

@Listeners({TestListeners.class, SuiteListeners.class})
public class BaseTest {
    protected SoftAssert softAssert;
    protected final String regresion = "regresion";
    protected final String smoke = "smoke";

    @BeforeMethod(alwaysRun = true)
    public void mastersetUp() {
        Logs.info("Setup del padre");
        softAssert = new SoftAssert();

    }

    @AfterMethod(alwaysRun = true)
    public void mastertearDown() {
        Logs.info("Teardown del padre");
    }
}
