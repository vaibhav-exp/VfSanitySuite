package test.java.samples.reporter;

import java.io.File;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;

/*
 * Grid Client example similar to Experitest documentation.
 * https://docs.experitest.com/display/TA/GridClient
 * Using the "disable.property" as defined here https://docs.experitest.com/display/TD/SeeTestAutomation-+SetProperty 
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GridClientJUnit4DisableReporter  extends GridClientAPIValidationBase {
     
    // Required for API validation tests
    private static String reportsLocation;
    
    @Rule
    public TestName testName = new TestName(); 
    
    @BeforeClass
    public static void beforeAll() {
	initializeAPI();
    }
    
    @Before    
    public void beforeTest() {	
	createGridAndExecutionClient(testName.getMethodName());
	executionClient.setProperty("disable.reporter", "true");
    }
 
    @Test
    public void STRSanity_JunitGrid_DisableReporter_ShouldPass_ShouldNotExistInReporter() {
	executionClient.deviceAction("Home");
	System.out.println("Generate report");
	reportsLocation = executionClient.generateReport(true);
	System.out.println("Reports location: " + reportsLocation);
    }
    
    @Test
    public void ValidateUsingAPI_STRSanity_JunitGrid_DisableReporter_ShouldPass_ShouldNotExistInReporter() throws Exception {
	// Reports should be saved locally
	File file = new File(reportsLocation);
	Assert.assertEquals("The local reports folder should not be empty", true, file.list().length > 0);
	System.out.println("Files in reports folder:");
	for (String f : file.list()) {
	    System.out.println(f);
	}
	// There should be no entry for this test in the reporter
	String testName = "STRSanity_JunitGrid_DisableReporter_ShouldPass_ShouldNotExistInReporter";
	boolean testFoundInReporter = reporterAPI.getTodaysTestNames().contains(testName);
	Assert.assertEquals("The test shoulf NOT appear in the reporter", false, testFoundInReporter);
    }
   
}