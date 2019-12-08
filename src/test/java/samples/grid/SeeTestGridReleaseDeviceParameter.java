package test.java.samples.grid;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;

import com.experitest.client.Client;
import com.experitest.client.GridClient;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SeeTestGridReleaseDeviceParameter {

    @Rule
    public TestName name = new TestName();

    // Add your own data to Data.java before running this!
    // Release all test device from the cloud before starting this!
    
    @BeforeClass
    public static void Prepare() {
	System.setProperty("http.proxyHost", Data.proxyHost);
	System.setProperty("http.proxyPort", Data.proxyPort);
	System.setProperty("https.proxyHost", Data.proxyHost);
	System.setProperty("https.proxyPort", Data.proxyPort);
    }
    
    @Before
    public void beforeTest() {
	System.out.println("Starting test --- " +  name.getMethodName());
    }
    
    // When using release device as false, the device remains reserved to the current user after the node/client is released
    @Test
    public void t1_lockDeviceConfigure_releaseDeviceFalse() throws Exception {
	GridClient grid = new GridClient(Data.accesKey, Data.serverName, Data.port, true);
	// Precondition
	boolean isFree = DevicesInfo.isAvailable(Data.nameDevice1, Data.serverName, Data.accesKey);
	Assert.assertTrue("Test precondition: The device must be free " + Data.nameDevice1, isFree);
	// Test
	Client client = grid.lockDeviceForExecution("TestGrid-releaseDeviceConfig", Data.queryDevice1, false, 60, 180000);
	
	client.sleep(5000);
	client.releaseClient();
	// Verify
	boolean isReserved = !DevicesInfo.isAvailable(Data.nameDevice1, Data.serverName, Data.accesKey);
	Assert.assertTrue("The device is reserved to the user after releasing the client", isReserved);
    }
    
    
    // When using release device as true, the device is NOT reserved to the current user after the node/client is released
    @Test
    public void t2_lockDeviceConfigure_releaseDeviceTrue() throws Exception {
	GridClient grid = new GridClient(Data.accesKey, Data.serverName, Data.port, true);
	// Precondition
	boolean isFree = DevicesInfo.isAvailable(Data.nameDevice2, Data.serverName, Data.accesKey);
	Assert.assertTrue("Test precondition: The device must be free " + Data.nameDevice2, isFree);
	// Test
	Client client = grid.lockDeviceForExecution("TestGrid-releaseDeviceConfig", Data.queryDevice2, true, 60, 180000);
	client.sleep(5000);
	client.releaseClient();
	// Verify
	boolean isReserved = !DevicesInfo.isAvailable(Data.nameDevice2, Data.serverName, Data.accesKey);
	Assert.assertTrue("The device is NOT reserved to the user after releasing the client", isReserved == false);
    }

    
    // When not setting release device as separate parameter, default value should be true
    @Test
    public void t3_lockDevice_noReleaseDeviceParameter() throws Exception {
	GridClient grid = new GridClient(Data.accesKey, Data.serverName, Data.port, true);
	// Precondition
	boolean isFree = DevicesInfo.isAvailable(Data.nameDevice3, Data.serverName, Data.accesKey);
	Assert.assertTrue("Test precondition: The device must be free " + Data.nameDevice3, isFree);
	// Test
	Client client = grid.lockDeviceForExecution("TestGrid-releaseDeviceConfig", Data.queryDevice3, 60, 180000);
	client.sleep(5000);
	client.releaseClient();
	// Verify
	boolean isReserved = !DevicesInfo.isAvailable(Data.nameDevice3, Data.serverName, Data.accesKey);
	Assert.assertTrue("The device is NOT reserved  after releasing the client", isReserved == false);
    }

    
    // When using release device as false, the device remains reserved to the current user after the node/client is released
    // Then, the user is able to reserve the device again - when using the client, this is possible, expecting similar behaviour here
    @Test
    public void t4_lockDeviceConfigure_releaseDeviceFalse_consecutiveReservations() throws Exception {
	
    	GridClient grid = new GridClient(Data.accesKey, Data.serverName, Data.port, true);
    	
    	// Precondition
    	boolean isFree = DevicesInfo.isAvailable(Data.nameDevice4, Data.serverName, Data.accesKey);
    	Assert.assertTrue("Test precondition: The device must be free " + Data.nameDevice4, isFree);
    	
	// Test A - Reserve free device - practically create grid node only + reserve device
	Client client = grid.lockDeviceForExecution("TestGrid-releaseDeviceConfig", Data.queryDevice4, false, 60, 60000);
	client.sleep(5000);
	client.releaseClient();
	// Verify
	boolean isReserved = !DevicesInfo.isAvailable(Data.nameDevice4, Data.serverName, Data.accesKey);
	Assert.assertTrue("The device is reserved after releasing the client", isReserved);
	
	// Test B - Reserve device already reserved to me - practically create grid node only
	client = grid.lockDeviceForExecution("TestGrid-releaseDeviceConfig", Data.queryDevice4, false, 60, 60000);
	client.sleep(5000);
	client.releaseClient();
	// Verify
	isReserved = !DevicesInfo.isAvailable(Data.nameDevice4, Data.serverName, Data.accesKey);
	Assert.assertTrue("The device is reserved after releasing the client", isReserved);
    }
    
    
    // When using release device as true, the device remains is NOT reserved to the current user after the node/client is released
    // Then, the user is able to reserve the device again
    @Test
    public void t5_lockDeviceConfigure_releaseDeviceTrue_consecutiveReservations() throws Exception {
	
    	GridClient grid = new GridClient(Data.accesKey, Data.serverName, Data.port, true);
    	
    	// Precondition
    	boolean isFree =  DevicesInfo.isAvailable(Data.nameDevice5, Data.serverName, Data.accesKey);
    	Assert.assertTrue("Test precondition: The device must be free " + Data.nameDevice5, isFree);
    	
	// Test A - Reserve free device 
    	Client client = grid.lockDeviceForExecution("TestGrid-releaseDeviceConfig", Data.queryDevice5, true, 60, 60000);
	client.sleep(5000);
	client.releaseClient();
	// Verify
	boolean isReserved = !DevicesInfo.isAvailable(Data.nameDevice5, Data.serverName, Data.accesKey);
	Assert.assertTrue("The device is reserved after releasing the client", isReserved == false);
	
	// Test B - Reserve 'freshly released' free device 
	client = grid.lockDeviceForExecution("TestGrid-releaseDeviceConfig", Data.queryDevice5, true, 60, 60000);
	client.sleep(5000);
	client.releaseClient();
	// Verify
	isReserved = !DevicesInfo.isAvailable(Data.nameDevice5, Data.serverName, Data.accesKey);
	Assert.assertTrue("The device is NOT reserved after releasing the client", isReserved == false);
    }



}
