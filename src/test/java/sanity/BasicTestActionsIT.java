package test.java.sanity;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

public class BasicTestActionsIT extends Base {
      
    // Utilities
    public void eribankLogin() {
	client.elementSendText("NATIVE", eribankApp.loginScreen.usernameField, 0, "company");
	client.elementSendText("NATIVE", eribankApp.loginScreen.passwordField, 0, "company");
	// Sometimes the app changes to 'Landscape' orientation for some iOS devices => Change to 'Portrait'
	if (client.getDeviceProperty("orientation").equals("Landscape")) {
	   client.deviceAction("Change Orientation");
	}
	client.closeKeyboard();
	client.click("NATIVE", eribankApp.loginScreen.loginButton, 0, 1);
	client.waitForElement("NATIVE", eribankApp.homeScreen.logoutButton, 0, 10000);
	client.verifyElementFound("NATIVE", eribankApp.homeScreen.logoutButton, 0);
    }
    
    public boolean isEribankInstalled() {
	return (client.getInstalledApplications().indexOf(eribankApp.packageName) > -1);
    }
    
    public void eribankLaunch(boolean instrument) {
	long start = System.currentTimeMillis();
	client.launch(eribankApp.mainActivity, instrument, true);
	perfLogger.addEntry(testName.getMethodName(),"launch application", (System.currentTimeMillis() - start)/1000);
	client.syncElements(3000, 6000);
	client.verifyElementFound("NATIVE", eribankApp.loginScreen.loginButton, 0);
    }
    
    public void eribankLogout() {
	client.closeKeyboard();
	client.click("NATIVE", eribankApp.homeScreen.logoutButton, 0, 1);
	client.syncElements(3000, 6000);
	client.verifyElementFound("NATIVE", eribankApp.loginScreen.loginButton, 0);
    }
    
    // Tests
  
    @Test
    public void EribankMortgageRequestInstrumented() {
    	// 1 - unistall only if already installed
	if (isEribankInstalled()) {
	    client.startStepsGroup("Uninstall");
	    long start = System.currentTimeMillis();
    	    client.uninstall(eribankApp.packageName);
    	    perfLogger.addEntry(testName.getMethodName(),"uninstall app", (System.currentTimeMillis() - start)/1000);
	}
	// 2
	client.startStepsGroup("Install - instrumented");
	long start = System.currentTimeMillis();
	client.install(eribankApp.installationPath, true, false);
	perfLogger.addEntry(testName.getMethodName(),"install app instrumented", (System.currentTimeMillis() - start)/1000);
	// 3
	client.startStepsGroup("Launch - instrumented");
	eribankLaunch(true);
	// 4
	client.startStepsGroup("Login");
	eribankLogin();
	// 5
	client.startStepsGroup("Click Mortgage Request");
	client.click("NATIVE", eribankApp.homeScreen.mortageRequestButton, 0, 1);
	client.verifyElementFound("NATIVE",eribankApp.mortageRequestScreen.makePaymentView, 0);
	// 6
	client.startStepsGroup("Complete personal information form");
	client.elementSendText("NATIVE", eribankApp.mortageRequestScreen.nameField, 0, "name");
	client.elementSendText("NATIVE", eribankApp.mortageRequestScreen.lastNameField, 0, "lastName");
	client.elementSendText("NATIVE", eribankApp.mortageRequestScreen.ageField, 0, "25");
	client.elementSendText("NATIVE", eribankApp.mortageRequestScreen.addressOneField, 0, "address one");
	client.elementSendText("NATIVE", eribankApp.mortageRequestScreen.addressTwoField, 0, "address two");
	client.click("NATIVE", eribankApp.mortageRequestScreen.countryButton, 0, 1);
	client.verifyElementFound("NATIVE", eribankApp.mortageRequestScreen.countryList, 0);
	client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", "xpath=//*[@text='Germany']", 0, 1000, 5, true);
	String selectedCountry = client.elementGetProperty("NATIVE", eribankApp.mortageRequestScreen.countryField, 0, "text");
	Assert.assertEquals("Germany", selectedCountry);
	// 7
	client.startStepsGroup("Cancel");
	// Sometimes the app changes to 'Landscape' orientation for some iOS devices => Change to 'Portrait'
	if (client.getDeviceProperty("orientation").equals("Landscape")) {
	   client.deviceAction("Change Orientation");
	}
	client.click("NATIVE", eribankApp.mortageRequestScreen.cancelButton, 0, 1);
	client.syncElements(3000, 6000);
	client.verifyElementFound("NATIVE", eribankApp.homeScreen.logoutButton, 0);
	// 8
	client.startStepsGroup("Logout");
	// Sometimes the app changes to 'Landscape' orientation for some iOS devices => Change to 'Portrait'
	if (client.getDeviceProperty("orientation").equals("Landscape")) {
	   client.deviceAction("Change Orientation");
	}
	eribankLogout();
	// 9
	client.startStepsGroup("Uninstall");
	client.uninstall(eribankApp.packageName);
    }
    
    @Test
    public void EribankAppPaymentRequestNotInstrumented() {
	// 1 - unistall only if already installed
	if (isEribankInstalled()) {
	    client.startStepsGroup("Uninstall");
	    long start = System.currentTimeMillis();
	    client.uninstall(eribankApp.packageName);
	    perfLogger.addEntry(testName.getMethodName(), "uninstall app", (System.currentTimeMillis() - start) / 1000);
	};
	// 2
    	client.startStepsGroup("Install - not instrumented");
	long start = System.currentTimeMillis();
	client.install(eribankApp.installationPath, false, false);
	perfLogger.addEntry(testName.getMethodName(),"install not instrumented", (System.currentTimeMillis() - start)/1000);
	// 3 
	client.startStepsGroup("Launch - not instrumented");
	eribankLaunch(false);
	// 4
	client.startStepsGroup("Login");
	eribankLogin();
	// 5
	client.startStepsGroup("Click Payment Request");
	client.click("NATIVE", eribankApp.homeScreen.makePaymentButton, 0, 1);
	client.verifyElementFound("NATIVE", eribankApp.makePaymentScreen.sendPaymentButton, 0);
	// 6
	client.startStepsGroup("Complete info");
	client.elementSendText("NATIVE", eribankApp.makePaymentScreen.nameField, 0, "name");
	client.elementSendText("NATIVE", eribankApp.makePaymentScreen.phoneField, 0, "1234567");
	client.elementSendText("NATIVE", eribankApp.makePaymentScreen.amountField, 0, "5");
	client.elementSendText("NATIVE", eribankApp.makePaymentScreen.countryField, 0, "Germany");
	// 7
	client.startStepsGroup("Make payment");
	client.click("NATIVE", eribankApp.makePaymentScreen.sendPaymentButton, 0, 1);
	client.syncElements(3000, 6000);
	client.verifyElementFound("NATIVE", eribankApp.makePaymentScreen.confirmPaymentDialogMessage, 0);
	// 8
	client.startStepsGroup("Cancel");
	client.click("NATIVE", "xpath=//*[@text='No']", 0, 1);
	client.click("NATIVE", eribankApp.makePaymentScreen.cancelButton, 0, 1);
	client.syncElements(3000, 6000);
	client.verifyElementFound("NATIVE", eribankApp.homeScreen.logoutButton, 0);
	// 9
	client.startStepsGroup("Logout");
	eribankLogout();
	// 10
	client.startStepsGroup("Uninstall");
	client.uninstall(eribankApp.packageName);
    }
  
 

}
