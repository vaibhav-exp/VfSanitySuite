package test.java.samples.reporter;

import org.junit.*;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;


// See: 
//  https://docs.experitest.com/display/public/TDB/SeeTestAutomation-+setReportStatus
// Checks how to manage test results using setReportStatus as alternative to recently added:
//  https://docs.experitest.com/display/TE/Grid+-+SeeTest+Client#Grid-SeeTestClient-3.2JUnit4usingaRule
//  https://docs.experitest.com/display/TE/Grid+-+SeeTest+Client#Grid-SeeTestClient-3.1JUnit4usingRunner 
// Approach:
//  1. No special experitest runners or rules
//  2. Use setReportStatus for the following cases:
//  - fail when failure cause is not an experitest client error
//  - skipped test
//  3. Test property added using client.setTestProperty

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GridClientJUnit4ClientSetReportStatus extends GridClientAPIValidationBase {
   
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
    
    // the results of these should go to the reporter
  
    @Test
    public void STRSanity_JUnitGridSetRepStatus_ShouldPass() {
	executionClient.deviceAction("Home");
	executionClient.report("Using proxy:" + data.usingProxy, true);
    }
     
    
    @Test
    public void STRSanity_JUnitGridSetRepStatus_ShouldBeSkippedAssumptionViolatedException() {
	executionClient.deviceAction("Home");
	executionClient.report("Using proxy:" + data.usingProxy, true);
	Assume.assumeFalse(true);
    }
     
    @Test
    public void STRSanity_JUnitGridSetRepStatus_ShouldBeSkippedOverrideStatus() {
	executionClient.deviceAction("Home");
	executionClient.report("Using proxy:" + data.usingProxy, true);
	executionClient.setReportStatus("skipped", "skipping this using set report status");
    }
     
    @Test
    public void STRSanity_JUnitGridSetRepStatus_ShouldFailJUnitAssertion() {
	executionClient.deviceAction("Home");
	executionClient.report("Using proxy:" + data.usingProxy, true);
	Assert.fail("This test should fail");
    }
    
    @Test
    public void STRSanity_JUnitGridSetRepStatus_ShouldFailJavaException() throws Exception {
	executionClient.deviceAction("Home");
	executionClient.report("Using proxy:" + data.usingProxy, true);
	throw new Exception("I fail!");
    }
    
    @Test
    public void STRSanity_JUnitGridSetRepStatus_ShouldFailClientException() {
	executionClient.deviceAction("Home");
	executionClient.report("Using proxy:" + data.usingProxy, true);
	executionClient.verifyElementFound("NATIVE", "//*[@text='you will not find this text']", 0);
    }
    
    
    // these  use the reporter api to check if the previuos tests were added correctly
    
    @Test
    public void ValidateUsingAPI_ShouldBePassed() throws Exception {
	String expTestame = "STRSanity_JUnitGridSetRepStatus_ShouldPass";
	String expStatus  = "Passed";
	int expAttachmentsCount = 1; // reporter HTML
	apiValidateTest(expTestame, expStatus, expAttachmentsCount); 
    }
  
    
    @Test
    public void ValidateUsingAPI_ShouldBeSkippedAssumptionViolatedException() throws Exception {
	String expTestame = "STRSanity_JUnitGridSetRepStatus_ShouldBeSkippedAssumptionViolatedException";
	String expStatus  = "Skipped";
	int expAttachmentsCount = 1; // reporter HTML
	apiValidateTest(expTestame, expStatus, expAttachmentsCount); 
    }
  
    @Test
    public void ValidateUsingAPI_ShouldBeSkippedOverrideStatus() throws Exception {
	String expTestame = "STRSanity_JUnitGridSetRepStatus_ShouldBeSkippedOverrideStatus";
	String expStatus  = "Skipped";
	int expAttachmentsCount = 1; // reporter HTML, no stack trace expected without a custom runner
	apiValidateTest(expTestame, expStatus, expAttachmentsCount); 
    }

    @Test
    public void ValidateUsingAPI_ShouldFailJUnitAssertion() throws Exception {
	String expTestame = "STRSanity_JUnitGridSetRepStatus_ShouldFailJUnitAssertion";
	String expStatus  = "Failed";
	int expAttachmentsCount = 1; // reporter HTML, no stack trace expected, no stack trace expected without a custom runner
	apiValidateTest(expTestame, expStatus, expAttachmentsCount); 
    }
    
    @Test
    public void ValidateUsingAPI_ShouldFailJavaException() throws Exception {
	String expTestame = "STRSanity_JUnitGridSetRepStatus_ShouldFailJavaException";
	String expStatus  = "Failed";
	int expAttachmentsCount = 1; // reporter HTML, no stack trace expected without a custom runner
	apiValidateTest(expTestame, expStatus, expAttachmentsCount); 
    }
  
    @Test
    public void ValidateUsingAPI_ShouldFailClientException() throws Exception {
	String expTestame = "STRSanity_JUnitGridSetRepStatus_ShouldFailClientException";
	String expStatus  = "Failed";
	int expAttachmentsCount = 1; // reporter HTML, no stack trace expected without a custom runner
	apiValidateTest(expTestame, expStatus, expAttachmentsCount); 
    }
    
    @Rule
    public TestWatcher watchman = new TestWatcher() {

	@Override
	protected void failed(Throwable e, Description description) {
	    // This setup is ignored for the API Validate tests 
	    if (testName.getMethodName().contains("API")) {
		    return;
	    }
	    // When the failure cause is not InternalException, the then test must be marked as failed in the code
	    if (!(e instanceof com.experitest.client.InternalException)) {
		// Test override passed status 
		// Without this, we would need to use "report" with "false" parameter.
		executionClient.setReportStatus("Failed", e.getMessage());
	    }
	    e.printStackTrace();
	}

	@Override
	protected void skipped(AssumptionViolatedException e, Description description) {
	    // This setup is ignored for the API Validate tests 
	    if (testName.getMethodName().contains("API")) {
		    return;
	    }
	    // Marking the test as skipped
	    executionClient.setReportStatus("skipped", e.getMessage());
	    e.printStackTrace();
	}
	
	@Override
	protected void finished(Description description) {
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
    };

}