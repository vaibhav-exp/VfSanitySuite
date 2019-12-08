package test.java.samples.grid;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.experitest.client.Client;
import com.experitest.client.GridClient;

public class SeeTestGridBaseConnectivity {
    
    // Add your own data to Data.java before running this!
    // Release all test device from the cloud before starting this!
    
    @BeforeClass
    public static void Prepare() {
	
	System.setProperty("http.proxyHost", Data.proxyHost);
	System.setProperty("http.proxyPort", Data.proxyPort);
	System.setProperty("https.proxyHost", Data.proxyHost);
	System.setProperty("https.proxyPort", Data.proxyPort);
	
	
    }
    
   @Test
   public void testConnectivity1() throws Exception {
		
        // Info 
//        DevicesInfo.displayAllDevicesInfo(Data.serverName, Data.accesKey);
       
        // Precondition
    	boolean isFree = DevicesInfo.isAvailable(Data.nameDevice1, Data.serverName, Data.accesKey);
    	Assert.assertTrue("Test precondition: The device must be free " + Data.nameDevice1, isFree);
	
    	System.out.println("Creating grid client ...");
	GridClient gridClient = new GridClient(Data.accesKey, Data.serverName, 443, true);

	try {
	    System.out.println("Reserving a device ... ");
	    long startTime = System.currentTimeMillis();
	    Client client = gridClient.lockDeviceForExecution("TestGrid", Data.queryDevice1,  30, 180000);
	    long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("time elapsed ------ " + elapsedTime/1000);
	    System.out.println("Waiting for 60 seconds ...");
	    client.sleep(5000);
	    client.releaseClient();
	} catch (Throwable e) {
	    e.printStackTrace();
	    Assert.fail("Test failed:"+ e.getMessage());
	} 
    }
   
   
   @Test
   public void testConnectivity2() throws Exception {
		
        // Info 
//        DevicesInfo.displayAllDevicesInfo(Data.serverName, Data.accesKey);
       
        // Precondition
    	boolean isFree = DevicesInfo.isAvailable(Data.nameDevice2, Data.serverName, Data.accesKey);
    	Assert.assertTrue("Test precondition: The device must be free " + Data.nameDevice2, isFree);
	
    	System.out.println("Creating grid client ...");
	GridClient gridClient = new GridClient(Data.accesKey, Data.serverName, 443, true);

	try {
	    System.out.println("Reserving a device ... ");
	    long startTime = System.currentTimeMillis();
	    Client client = gridClient.lockDeviceForExecution("TestGrid", Data.queryDevice2,  30, 180000);
	    long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("time elapsed ------ " + elapsedTime/1000);
	    System.out.println("Waiting for 60 seconds ...");
	    client.sleep(5000);
	    client.releaseClient();
	} catch (Throwable e) {
	    e.printStackTrace();
	    Assert.fail("Test failed:"+ e.getMessage());
	} 
    }
   
   
   @Test
   public void testConnectivity3() throws Exception {
		
        // Info 
//        DevicesInfo.displayAllDevicesInfo(Data.serverName, Data.accesKey);
       
        // Precondition
    	boolean isFree = DevicesInfo.isAvailable(Data.nameDevice3, Data.serverName, Data.accesKey);
    	Assert.assertTrue("Test precondition: The device must be free " + Data.nameDevice3, isFree);
	
    	System.out.println("Creating grid client ...");
	GridClient gridClient = new GridClient(Data.accesKey, Data.serverName, 443, true);

	try {
	    System.out.println("Reserving a device ... ");
	    long startTime = System.currentTimeMillis();
	    Client client = gridClient.lockDeviceForExecution("TestGrid", Data.queryDevice3, 30, 180000);
	    long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("time elapsed ------ " + elapsedTime/1000);
	    System.out.println("Waiting for 60 seconds ...");
	    client.sleep(5000);
	    client.releaseClient();
	} catch (Throwable e) {
	    e.printStackTrace();
	    Assert.fail("Test failed:"+ e.getMessage());
	} 
    }
   
   
   @Test
   public void testConnectivity4() throws Exception {
		
        // Info 
//        DevicesInfo.displayAllDevicesInfo(Data.serverName, Data.accesKey);
       
        // Precondition
    	boolean isFree = DevicesInfo.isAvailable(Data.nameDevice4, Data.serverName, Data.accesKey);
    	Assert.assertTrue("Test precondition: The device must be free " + Data.nameDevice4, isFree);
	
    	System.out.println("Creating grid client ...");
	GridClient gridClient = new GridClient(Data.accesKey, Data.serverName, 443, true);

	try {
	    System.out.println("Reserving a device ... ");
	    long startTime = System.currentTimeMillis();
	    Client client = gridClient.lockDeviceForExecution("TestGrid", Data.queryDevice4, 30, 180000);
	    long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("time elapsed ------ " + elapsedTime/1000);
	    System.out.println("Waiting for 60 seconds ...");
	    client.sleep(5000);
	    client.releaseClient();
	} catch (Throwable e) {
	    e.printStackTrace();
	    Assert.fail("Test failed:"+ e.getMessage());
	} 
    }
   
   @Test
   public void testConnectivity5() throws Exception {
		
        // Info 
//        DevicesInfo.displayAllDevicesInfo(Data.serverName, Data.accesKey);
       
        // Precondition
    	boolean isFree = DevicesInfo.isAvailable(Data.nameDevice5, Data.serverName, Data.accesKey);
    	Assert.assertTrue("Test precondition: The device must be free " + Data.nameDevice5, isFree);
	
    	System.out.println("Creating grid client ...");
	GridClient gridClient = new GridClient(Data.accesKey, Data.serverName, 443, true);

	try {
	    System.out.println("Reserving a device ... ");
	    long startTime = System.currentTimeMillis();
	    Client client = gridClient.lockDeviceForExecution("TestGrid", Data.queryDevice5,  30, 180000);
	    long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("time elapsed ------ " + elapsedTime/1000);
	    System.out.println("Waiting for 60 seconds ...");
	    client.sleep(5000);
	    client.releaseClient();
	} catch (Throwable e) {
	    e.printStackTrace();
	    Assert.fail("Test failed:"+ e.getMessage());
	} 
    }
}
