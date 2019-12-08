package test.java.samples.reporter;

import java.util.HashMap;
import com.experitest.client.Client;
import com.experitest.client.GridClient;
import javax.json.JsonArray;
import javax.json.JsonObject;

import org.junit.Assert;

public class GridClientAPIValidationBase {

    static Data data = new Data();

    // Required for the actual test
    GridClient gridClient = null;
    Client executionClient = null;

    public static void initializeAPI() {
	testIDS = new HashMap<String, String>();
	if (data.usingProxy) {
	    reporterAPI = new SeeTestReporterAPI(data.deviceCloudServer, data.accesKey, data.proxyHost); 
	} else {
	    reporterAPI = new SeeTestReporterAPI(data.deviceCloudServer, data.accesKey);
	}
    }

    // Required for API validation tests
    static SeeTestReporterAPI reporterAPI ;
    static HashMap<String, String>  testIDS; 

    // Create grid client, execution client and manager publisher before each test 
    public void createGridAndExecutionClient(String testName) {	
	System.out.println("Running:" + testName + (" - using network proxy:" + data.usingProxy));

	// This setup is ignored for the API Validate tests 
	if (testName.contains("API")) {
	    return;
	}

	// Grid & Reporter 	
	gridClient = new GridClient(data.accesKey , data.deviceCloudServer,  data.port, true);
	executionClient = gridClient.lockDeviceForExecution(testName, data.queryTestDevice1, 10, 60*1000);
	executionClient.setReporter("xml", System.getProperty("user.dir") + "\\", testName);    

	// Add test property
	executionClient.addTestProperty("context", "seetestreporter_sanity");

    }

    public void apiValidateTest(String expTestame, String expStatus, int expAttachmentsCount) throws Exception {

	String expCustomTagName = "context";
	String expCustomTagValue = "seetestreporter_sanity";

	String testID = testIDS.get(expTestame);
	Assert.assertEquals("A test id is displayed when generating a report", true, testID != null);
	JsonObject testInfo = reporterAPI.getTest(testID, data.seetestReporterProjectName);
	Assert.assertEquals("A test with the given id is available", true, testInfo != null);
	System.out.println("Test information: " + testInfo.toString());

	Assert.assertEquals("Test name verification", expTestame, testInfo.getString("name"));
	Assert.assertEquals("Test status verification", expStatus, testInfo.getString("status"));
	Assert.assertEquals("Custom tag/property exists", true, testInfo.getJsonObject("keyValuePairs").containsKey(expCustomTagName));
	Assert.assertEquals("Custom tag/property value exists", expCustomTagValue, testInfo.getJsonObject("keyValuePairs").getString(expCustomTagName));

	JsonArray attachments = testInfo.getJsonArray("testAttachments");
	Assert.assertEquals("Number of attachments", expAttachmentsCount, attachments.size());
    }

   


}
