package test.java.samples.reporter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Data {

    // server data 
    public String accesKey = "eyJ4cC51IjoyNjI4MjksInhwLnAiOjIsInhwLm0iOiJNVFUxTkRNM01UazJOakE1TVEiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE4Njk3MzE5NjYsImlzcyI6ImNvbS5leHBlcml0ZXN0In0._kktyk_CYEm2yw2EBl1Tk7JKBXXpHdPM_tJQPBYFVmE" ;
    public String deviceCloudServer = "testcloud.vodafone.com";
    public String seetestReporterURL = "https://testcloud.vodafone.com/reporter";
    public String seetestReporterProjectName = "default";
    public int port = 443;
    // proxies
    public String proxyHost = "85.205.122.145";
    public String proxyPort = "8080";
    public boolean usingProxy = false;
    // devices 
    public String queryTestDevice2 = "@name='SM-G920F-1'";
    public String queryTestDevice1 = "@name='SM-G920F-1'";
    public Path pathReportFolder = Paths.get(System.getProperty("user.dir"), "src", "resources", "files");

    public Data() {
/*
	  System.out.println("Using proxy");
	    System.setProperty("http.proxyHost", proxyHost);
	    System.setProperty("http.proxyPort", proxyPort);
	    System.setProperty("https.proxyHost", proxyHost);
	    System.setProperty("https.proxyPort", proxyPort);
	    usingProxy = true;
*/	
    }
}
