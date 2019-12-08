package test.java.sanity;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.Stopwatch;
import org.junit.rules.TestName;
import org.junit.runner.Description;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import main.java.appmodels.Eribank;

public class Base {

    // Parameters
    protected static Data testData = null;
    // Client
    protected static Client client = null;
    protected static String deviceOSType;
    protected static String pathToApp;
    protected GridClient grid = null;
    
    DeviceLogger perfLogger = null;
    
    protected static Eribank eribankApp = null;
    
    @Rule
    public TestName testName = new TestName();

    // Reserve grid node
    public void reserveSeeTestGridNode(String testDesc) throws Exception {
	// Create grid client
	long start = System.currentTimeMillis();
	grid = new GridClient(testData.seeTestGridAccessKey, testData.seeTestGridURL, 443, true);
	System.out.println("Grid client created.");

	// Reserve
	System.out.println("Reserving device: " + testData.deviceName);
	start = System.currentTimeMillis();
	client = grid.lockDeviceForExecution("sanity_" + testDesc, "@name='" + testData.deviceName + "'", 60, 180000);
	if (client == null) {
	    // It was observed that the lockDeviceForExecution is not throwing exception
	    // when a reservation fails
	    // Adding this to make sure that test stops if we have no execution client
	    String message = "Failed to create grid execution client for device: " + testData.deviceName;
	    throw new Exception(message);
	}
	System.out.println("Grid node and execution client created for: " + testData.deviceName);
	perfLogger.addEntry(testDesc, "reserve grid node", (System.currentTimeMillis() - start) / 1000);
	
   }

    // Use STA client to reserve a cloud device
    // Local device also needs the STA Client, but a separate method is available
    // for this
    public  void reserveCloudDeviceSTA() throws Exception {
	System.out.println("Initialize see test client");
	client = new Client("localhost", 8889, true);
	System.out.println("Start wait for device: " + testData.deviceQuery);
	long start = System.currentTimeMillis();
	String foundDevice = client.waitForDevice(testData.deviceQuery, 180000);
	perfLogger.addEntry(testName.getMethodName(),"reserve cloud device (st client)", (System.currentTimeMillis() - start)/1000);
	System.out.println("Found: " + foundDevice);
	
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	// Load all settings and data - create any necessary folders
	testData = new Data();
	
    }

    @Before
    public void setUp() throws Exception, Throwable {
	perfLogger = new DeviceLogger(testData.pathPerfReports.toString().toString(), testData.deviceName);
	// Create a client
	if (testData.seeTestGridRun) {
	    reserveSeeTestGridNode(testName.getMethodName());
	} else {
	    reserveCloudDeviceSTA();
	}

	// Get device OS type
	deviceOSType = client.getDeviceProperty("device.os").toLowerCase();
	if (deviceOSType.equals("android")) {
	    pathToApp = testData.pathApkLocationAndroid.toString();
	} else {
	    pathToApp = testData.pathApkLocationIOS.toString();
	}

	eribankApp = new Eribank(pathToApp, deviceOSType);
	// Set project folder
	long start = System.currentTimeMillis();
	//client.setProjectBaseDirectory(testData.pathProjectHome.toString());
	//perfLogger.addEntry(testName.getMethodName(),"set project base directory", (System.currentTimeMillis() - start)/1000);

	// Sometimes iOS devices are in 'Landscape' orientation => Change to 'Portrait'
	if (client.getDeviceProperty("orientation").equals("Landscape")) {
	    client.deviceAction("Change Orientation");
	}
	// Report
	client.setReporter("xml", testData.pathReportsCurrentDevice.toString(), testName.getMethodName());
	// Set test property - grid tests only
	if (testData.seeTestGridRun) {
	    client.addTestProperty("context", "automated_sanity_test");
	}
	client.startLoggingDevice(Paths.get(testData.pathProjectHome.toString(), "target", "seetest-reports", testData.deviceName + "_" + testName.getMethodName()).toString());
	client.startStepsGroup("Prepare");
	// For see test grid, it should not be necessary to unlock the device
	if (!testData.seeTestGridRun) {   
	 client.deviceAction("Unlock");
	}
	client.deviceAction("Home");
    }

    @Rule
    public Stopwatch stopWatch = new Stopwatch() {

	@Override
	protected void failed(long nanos, Throwable e, Description description) {
	    if (!(e instanceof com.experitest.client.InternalException)) {
		client.stopStepsGroup();
		client.setReportStatus("Failed", e.getMessage(), e.getStackTrace().toString());
	    }
	    e.printStackTrace();
	}
	
	@Override
	protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
	    client.stopStepsGroup();
	    client.setReportStatus("skipped", e.getMessage(), e.getStackTrace().toString());
	    e.printStackTrace();
	}

	@Override
	protected void finished(long nanos, Description description) {
	     
	    // Save test duration information
	    perfLogger.addEntry(testName.getMethodName(),"test duration", nanos/1000000000);
	    
	    System.out.println("Generating See Test Report ...");
	    long start = System.currentTimeMillis();
	    String reportPath = client.generateReport(false);
	    if (testData.seeTestGridRun) {
		perfLogger.addEntry(testName.getMethodName(),"generate see test report (grid)", (System.currentTimeMillis() - start)/1000);
	    } else {
		perfLogger.addEntry(testName.getMethodName(),"generate see test report (stclient)", (System.currentTimeMillis() - start)/1000);
	    }
	    
	    System.out.println("Reports saved to " + reportPath);
	    start = System.currentTimeMillis();
	    String logPath = client.stopLoggingDevice();
	    perfLogger.addEntry(testName.getMethodName(),"save device log", (System.currentTimeMillis() - start)/1000);
	    
	    System.out.println("Device log saved to " + logPath);
	    if (!testData.seeTestGridRun) {
		client.releaseDevice(testData.deviceName, true, true, true);
	    }
	    client.releaseClient();
	    // Performance reports
	    try {
		perfLogger.saveData();
		perfLogger.mergeExistingData();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    

	}
    };

}
