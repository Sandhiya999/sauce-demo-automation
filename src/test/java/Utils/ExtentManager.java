package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;
    public static ExtentReports getInstance()
    {
        if(extent == null)
        {
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/extentreport.html");
            spark.config().setReportName("Automation Test Result");
            spark.config().setDocumentTitle("Test Execution Report");

            extent= new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Tester","Sandhiya");
            extent.setSystemInfo("Framework","Selenium TestNG");
        }
        return extent;
    }
}
