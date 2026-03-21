package Listeners;

import Utils.ExtentManager;
import Utils.ScreenShotCapture;
import base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;import org.testng.ITestResult;

import org.testng.ITestListener;


public class TestListener implements ITestListener {
    ExtentReports extent = ExtentManager.getInstance();
    ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    @Override
    public void onTestStart(ITestResult result)
    {
       ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
       test.set(extentTest);

    }
    @Override
    public void onTestSkipped(ITestResult result)
    {
        //test.get().skip(result.getThrowable());
        ExtentTest extentTest = test.get();
        if(extentTest!=null)
        {
            extentTest.skip(result.getThrowable());
        }
    }
    @Override
    public void onTestFailure(ITestResult result)
    {   //test.get().fail("Test Failed ");
      //  System.out.println("Test Failed "+ result.getName());
        ExtentTest extentTest = test.get();
        if(extentTest!=null)
        {
            extentTest.fail(result.getThrowable());
        }
        //test.get().fail(result.getThrowable());
        String path = null;
        Object testclass = result.getInstance();

        if(testclass instanceof BaseTest) {
            BaseTest basetest = (BaseTest) testclass;
            if(basetest.getDriver()!=null)
            {
            path = ScreenShotCapture.captureScreeshot(basetest.getDriver(), result.getName());}
           // System.out.println("Screenshot save in " + rootpath);
        }
        try{
            if(path!=null && extentTest!=null) {
                extentTest.addScreenCaptureFromPath(path);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onTestSuccess(ITestResult result)
    {
       // System.out.println("Test Passed "+result.getName());
       // test.get().pass("Test Passed");
        test.get().pass(result.getName()+ " : Passed");
    }
    public void onFinish(ITestContext context)
    {
        extent.flush();
    }
}
