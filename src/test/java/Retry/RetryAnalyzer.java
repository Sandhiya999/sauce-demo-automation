package Retry;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriverException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import org.openqa.selenium.TimeoutException;


public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY = 1;

    @Override
    public boolean retry(ITestResult result) {

        Throwable t = result.getThrowable();

        if (isRetryable(t) && retryCount < MAX_RETRY) {
            retryCount++;
            System.out.println("Retrying test: " + result.getName() + " Attempt: " + retryCount);
            //BaseTest.killDriver();   // VERY important
            return true;
        }
        return false;
    }

    private boolean isRetryable(Throwable t) {
        while (t != null) {
            if (t instanceof AssertionError || t instanceof WebDriverException || t instanceof TimeoutException
            || t instanceof NoSuchWindowException || t instanceof NoSuchSessionException ) {
                return true;
            }
            t = t.getCause();

        }
        return false;
    }

}
