package test.java.samples.reporter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;
import com.experitest.reporter.junit4.AfterWithSeeTestReporter;
import com.experitest.reporter.junit4.ReporterRule;



// See: 
//  https://docs.experitest.com/display/TE/Grid+-+SeeTest+Client#Grid-SeeTestClient-3.2JUnit4usingaRule
// Required:
//  1: ReporterRule
//  2: A client instance as class property: see parent class 
//  3. Client being initialized before the test: see parent class
//  4. Report being generated after the test: see parent class
// Optional, but needed by Vodafone:
//  1. Test property added using client.setTestProperty


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GridClientJUnit4WithReporterRule extends GridClientAPIValidationBase {

    @Rule
    public ReporterRule reporterRule = new ReporterRule(this);
    
    @Rule
    public TestName testName = new TestName();
    
    @BeforeClass
    public static void beforeAll() {
	initializeAPI();
    }
    
    @Before    
    public void beforeTest() {	
	 createGridAndExecutionClient(testName.getMethodName());
    }
    
    // The documentation required for a Client instance to exist as class member
    // The parent class contains it
    
    // the results of these should go to the reporter
    
    @Test
    public void STRSanity_GridJunit4_WithRule_ShouldPass() {
	executionClient.deviceAction("Home");
	executionClient.report("Using proxy:" + data.usingProxy, true);
    }
    
    /*
    // KNOWN ISSUE: This will appear as failed in the see test reporter - the rule does not seem to detect it
    //    We will not follow up with experitest on this, because workarounds exist ( See: GridClientJUnit4ClientSetReportStatus ) 
    //    We will keep the test case - as it can be useful later - but it will not be part of sanity = that's why it is commented
    @Test
    public void STRSanity_GridJunit4_WithRule_ShouldBeSkippedAssumption() {
	executionClient.deviceAction("Home");
	executionClient.report("Using proxy:" + data.usingProxy, true);
	Assume.assumeFalse(true);
    }
    */
    
    @Test
    public void STRSanity_GridJunit4_WithRule_ShouldFailJUnitAssertion() {
	executionClient.deviceAction("Home");
	executionClient.report("Using proxy:" + data.usingProxy, true);
	Assert.fail("This test should fail");
    }
    
    @Test
    public void STRSanity_GridJunit4_WithRule_ShouldFailClientException() {
	executionClient.deviceAction("Home");
	executionClient.report("Using proxy:" + data.usingProxy, true);
	executionClient.verifyElementFound("NATIVE", "//*[@text='you will not find this text']", 0);
    }
    
    @Test
    public void STRSanity_GridJunit4_WithRule_ShouldFailJavaException() throws Exception {
	executionClient.deviceAction("Home");
	executionClient.report("Using proxy:" + data.usingProxy, true);
	throw new Exception("I fail!");
    }
    
    // these use the reporter API to check if the previous tests were added correctly
    
    @Test
    public void ValidateUsingAPI_ShouldPass() throws Exception {
	String expTestame = "STRSanity_GridJunit4_WithRule_ShouldPass";
	String expStatus  = "Passed";
	int expAttachmentsCount = 1; // reporter HTML
	apiValidateTest(expTestame, expStatus, expAttachmentsCount); 
    }
    
    /*
    // KNOWN ISSUE: This will appear as failed in the see test reporter - the rule does not seem to detect it
    //    We will not follow up with experitest on this, because workarounds exist ( See: GridClientJUnit4ClientSetReportStatus ) 
    //    We will keep the test case - as it can be useful later - but it will not be part of sanity = that's why it is commented
    @Test
    public void ValidateUsingAPI_ShouldBeSkippedAssumption() throws Exception {
	String expTestame = "STRSanity_GridJunit4_WithRule_ShouldBeSkippedAssumption";
	String expStatus  = "Skipped";
	int expAttachmentsCount = 1; // reporter HTML
	apiValidateTest(expTestame, expStatus, expAttachmentsCount); 
    }
    */ 
    
    @Test
    public void ValidateUsingAPI_ShouldFailJUnitAssertion() throws Exception {
	String expTestame = "STRSanity_GridJunit4_WithRule_ShouldFailJUnitAssertion";
	String expStatus  = "Failed";
	int expAttachmentsCount = 1; // reporter HTML
	apiValidateTest(expTestame, expStatus, expAttachmentsCount); 
    }
    
    @Test
    public void ValidateUsingAPI_ShouldFailJavaException() throws Exception {
	String expTestame = "STRSanity_GridJunit4_WithRule_ShouldFailJavaException";
	String expStatus  = "Failed";
	int expAttachmentsCount = 1; // reporter HTML
	apiValidateTest(expTestame, expStatus, expAttachmentsCount); 
    }
  
    @Test
    public void ValidateUsingAPI_ShouldFailClientException() throws Exception {
	String expTestame = "STRSanity_GridJunit4_WithRule_ShouldFailClientException";
	String expStatus  = "Failed";
	int expAttachmentsCount = 1; // reporter HTML
	apiValidateTest(expTestame, expStatus, expAttachmentsCount); 
    }
      
    @AfterWithSeeTestReporter
    public void tearDown() {
	// This setup is ignored for the API Validate tests 
	if (testName.getMethodName().contains("API")) {
	    return;
	}
	// Required - Generate report 
	// The report is added automatically to the see test reporter 
	System.out.println("Generate report");
	String report = executionClient.generateReport(true);
	// Gathering all test IDS so API validate tests use them
	String id = report.substring(report.indexOf("/test/") + 6, report.indexOf("/project/"));
	testIDS.put(testName.getMethodName(), id);
    }
    
}
