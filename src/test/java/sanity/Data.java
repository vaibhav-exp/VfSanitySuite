package test.java.sanity;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Data {

    protected String deviceName;
    Path pathProjectHome;
    Path pathReportsCurrentDevice;
    protected Path pathReportsCurrentDeviceExtraFiles;

    String seeTestGridAccessKey;
    String seeTestGridURL;
    boolean seeTestGridRun;

    Path pathApkLocationAndroid;
    Path pathApkLocationIOS;
    String deviceQuery;
    Path pathPerfReports;
    private String testRunId;
    
    void processScriptSettings() {
	// Required
	if (System.getProperty("device") != null) {
	    deviceName = System.getProperty("device");
	    deviceQuery = String.format("@name='%s'", deviceName);
	}
	
	// Optional
	if (System.getProperty("testRunId") != null) {
	    testRunId = System.getProperty("testRunId");
	}
	if (System.getProperty("seeTestGridAccessKey") != null) {
	    seeTestGridAccessKey = System.getProperty("seeTestGridAccessKey");
	}
	if (System.getProperty("seeTestGridURL") != null) {
	    seeTestGridURL = System.getProperty("seeTestGridURL");
	}
    }

    void validateScriptSettings() throws Exception {
	// Required
	if (deviceName == null) {
	    System.out.println("Invalid setup - deviceName undefined!");
	    throw new Exception("Invalid setup - deviceName undefined!");
	}
	// Optional
	if  ((seeTestGridAccessKey != null) && (seeTestGridURL == null)) {
	    System.out.println("Invalid setup - seeTestGridAccessKey defined, seeTestGridURL undefined - You need to provide both values to enable a grid run!");
	    throw new Exception("Invalid setup - seeTestGridAccessKey defined, seeTestGridURL undefined - You need to provide both values to enable a grid run!"); 
	}
	if  ((seeTestGridAccessKey == null) && (seeTestGridURL != null)) {
	    System.out.println("Invalid setup - seeTestGridAccessKey undefined, seeTestGridURL defined - You need to provide both values to enable a grid run!");
	    throw new Exception("Invalid setup - seeTestGridAccessKey undefined, seeTestGridURL defined - You need to provide both values to enable a grid run!"); 
	}
	seeTestGridRun = ((seeTestGridAccessKey != null) && (seeTestGridURL != null));
	if (testRunId == null) {
	    testRunId = "default";
	}

    }

    public void configurePaths() throws Exception {
	/*
	System.setProperty("http.proxyHost","85.205.122.145");
	System.setProperty("http.proxyPort", "8080");
	System.setProperty("https.proxyHost","85.205.122.145");
	System.setProperty("https.proxyPort","8080");
	*/
	
	System.out.println("Configure paths...");
	pathProjectHome = Paths.get(System.getProperty("user.dir"));
	pathReportsCurrentDevice = Paths.get(pathProjectHome.toString(), "target", "seetest-reports", deviceName);
	pathReportsCurrentDeviceExtraFiles = Paths.get(pathReportsCurrentDevice.toString(), "extrafiles");
	Path pathApkLocation = Paths.get(pathProjectHome.toString(), "src", "resources", "apps");
	pathApkLocationAndroid = Paths.get(pathApkLocation.toString(), "eribank.apk");
	pathApkLocationIOS = Paths.get(pathApkLocation.toString(), "EriBank.ipa");
	
	if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
	    pathPerfReports = Paths.get("C:", "Automation", "SeeTestVerificationWorkspace", "PerformanceMeasurements", testRunId);
	} else {
	    pathPerfReports = Paths.get("/seetestautomation", "SeeTestVerificationWorkspace", "PerformanceMeasurements", testRunId);
	}  
	
	System.out.println("Project home directory: " + pathProjectHome);
	System.out.println("OS: " + System.getProperty("os.name").toLowerCase());
	System.out.println("Report Dir: " + pathPerfReports);
	System.out.println("Extra files Dir: " + pathReportsCurrentDeviceExtraFiles);
	System.out.println("Create necesarry folders ...");
	Files.createDirectories(pathReportsCurrentDeviceExtraFiles);
	Files.createDirectories(pathPerfReports);
    }

    public Data() throws Exception {
	// Settings
	System.out.println("Process script settings");
	processScriptSettings();
	validateScriptSettings();
	configurePaths();

    }

}
