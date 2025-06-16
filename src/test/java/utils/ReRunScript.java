package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class ReRunScript implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int maxCount = 1;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (retryCount < maxCount){
            retryCount++;
            return true;
        }
        return false;
    }
}
