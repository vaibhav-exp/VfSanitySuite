package test.java.samples.reporter;

// READ ME
// Uncomment the class+imports and add test ng manually in the build path
// If left like this, they interfeer with tests running fro jenkins+maven
// Same when adding test ng to the pom file
// There was no time to find something better than this

/*
import com.experitest.reporter.testng.Listener;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;
import java.lang.reflect.Method;

// See:
//  https://docs.experitest.com/display/TE/Grid+-+SeeTest+Client#Grid-SeeTestClient-3.4TestNG

//Required:
// 1. com.experitest.reporter.testng.Listener
// 2. A client instance as class property: see parent class 
// 3. Client being initialized before the test: see parent class
// 4. Report being generated after the test: see parent class

@Listeners(Listener.class)
public class GridClientTestNGReporterListener  extends GridClientAPIValidationBase  {

    @BeforeClass
    public static void beforeAll() {
	initializeAPI();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTest(Method method) {
	createGridAndExecutionClient(method.getName());	
    }

    @Test(priority=1)
    public void STRSanity_TestNGGridListener_ShouldPass() {
	executionClient.deviceAction("Home");
	executionClient.report("Using proxy:" + data.usingProxy, true);
    }

    @Test(priority=1)
    public void STRSanity_TestNGGridListener_ShouldFailTestNGAssertion() {
	executionClient.deviceAction("Home");
	executionClient.report("Using proxy:" + data.usingProxy, true);
	Assert.fail("This test should fail");
    }
    
    @Test(priority=1)
    public void STRSanity_TestNGGridListener_ShouldFailJavaException() throws Exception {
	executionClient.deviceAction("Home");
	executionClient.report("Using proxy:" + data.usingProxy, true);
	throw new Exception("I fail!");
    }
    
    
    // KNOWN LIMITATION:
    //   This on will not show up in the see test reporter at all, despite having "alwaysrun=true" in before & after methods
    // 	 See Test Reporter needs a grid & client instance to be able to configure a reporter entry 
    //   If the previous method fails, this is skipped without running before and after methods = no client
    //   What to do instead: for now, you need to check jenkins test ng report
    @Test(dependsOnMethods={"STRSanity_TestNGGridListener_ShouldFailJavaException"},priority=1)
    public void STRSanity_TestNGGridListener_ShouldBeSkippedDependency() {
	executionClient.deviceAction("Home");
	executionClient.report("Using proxy:" + data.usingProxy, true);
        System.out.println("This test should be skipped");
    }
    
    @Test(priority=1)
    public void STRSanity_TestNGGridListener_ShouldBeSkippedException() {
	executionClient.deviceAction("Home");
	executionClient.report("Using proxy:" + data.usingProxy, true);
	throw new SkipException("Skipping test!");
    }
    
    @Test(priority=2)
    public void ValidateUsingAPI_STRSanity_TestNGGridListener_ShouldPass() throws Exception {
	String expTestame = "STRSanity_TestNGGridListener_ShouldPass";
	String expStatus  = "Passed";
	int expAttachmentsCount = 1; // reporter HTML
	apiValidateTest(expTestame, expStatus, expAttachmentsCount); 
    }

    @Test(priority=2)
    public void ValidateUsingAPI_STRSanity_TestNGGridListener_ShouldFailTestNGAssertion() throws Exception {
	String expTestame = "STRSanity_TestNGGridListener_ShouldFailTestNGAssertion";
	String expStatus  = "Failed";
	int expAttachmentsCount = 1; // reporter HTML
	apiValidateTest(expTestame, expStatus, expAttachmentsCount);
    }
    
    @Test(priority=2)
    public void ValidateUsingAPI_STRSanity_TestNGGridListener_ShouldFailJavaException() throws Exception {
	String expTestame = "STRSanity_TestNGGridListener_ShouldFailJavaException";
	String expStatus  = "Failed";
	int expAttachmentsCount = 1; // reporter HTML
	apiValidateTest(expTestame, expStatus, expAttachmentsCount);
    }
    
      // KNOWN LIMITATION:
     //   ... see more explanations above
     // What to do instead: for now, you need to check jenkins test ng report
     //    We will not follow up with experitest on this, because - the issue might not be related to htem
     //    We will keep the test case - as it can be useful later - but it will not be part of sanity = that's why it is commented
     //@Test(priority=2)
    //public void ValidateUsingAPI_STRSanity_TestNGGridListener_ShouldBeSkippedDependency() throws Exception {
    //	String expTestame = "STRSanity_TestNGGridListener_ShouldBeSkippedDependency";
	//String expStatus  = "Skipped";
	//int expAttachmentsCount = 1; // reporter HTML, no stack trace expected, no stack trace expected without a custom listener
	//apiValidateTest(expTestame, expStatus, expAttachmentsCount); 
    //}
    
    @Test(priority=2)
    public void ValidateUsingAPI_STRSanity_TestNGGridListener_ShouldBeSkippedException() throws Exception {
	String expTestame = "STRSanity_TestNGGridListener_ShouldBeSkippedException";
	String expStatus  = "Skipped";
	int expAttachmentsCount = 1; // reporter HTML
	apiValidateTest(expTestame, expStatus, expAttachmentsCount); 
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(Method method, ITestResult result) {
	// This setup is ignored for the API Validate tests 
	if (method.getName().contains("API")) {
	    return;
	}
	
	// Required - Generate report 
	// The report is added automatically to the see test reporter 
	System.out.println("Generate report");
	String report = executionClient.generateReport(true);
	// Gathering all test IDS so API validate tests use them
	String id = report.substring(report.indexOf("/test/") + 6, report.indexOf("/project/"));
	testIDS.put(method.getName(), id);
    }


}
*/

