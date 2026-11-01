package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.Logs;

public class TestListeners implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
        Logs.info("Comenzando el test: %s", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        Logs.info("Test exitoso: %s", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        Logs.info("Test fallido: %s", result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
        Logs.info("Test ignorado: %s", result.getName());
    }
}
