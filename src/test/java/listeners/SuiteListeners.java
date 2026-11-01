package listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import utilities.Logs;

public class SuiteListeners implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        ISuiteListener.super.onStart(suite);
        Logs.info("Suite iniciada: %s", suite.getName());
    }

    @Override
    public void onFinish(ISuite suite) {
        ISuiteListener.super.onFinish(suite);
        Logs.info("Suite finalizada: %s", suite.getName());
    }
}
