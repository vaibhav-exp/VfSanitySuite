package test.java.sanity.appium;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class BasicAppiumiOSTestActionsIT {
	

	public static IOSDriver<MobileElement> driver = null;
	
    static String application_path="C:\\Ramya\\CORE\\Seetest_12.1_SanityTest\\EriBank.ipa";
    static File f = new File(application_path);
    static String boundary = UUID.randomUUID().toString();
    
    public static String AccessKey = null;
         

    @BeforeClass
    public static void setUp() throws Throwable

    {
          System.setProperty("https.proxyHost", "139.7.95.74");
          System.setProperty("https.proxyPort", "8080"); 
          
          AccessKey = System.getProperty("testCloudAccessKey");
                    
          
          DesiredCapabilities capabilities = new DesiredCapabilities();
          capabilities.setCapability("platformName", "iOS");
          capabilities.setCapability("accessKey", AccessKey);
          capabilities.setCapability("deviceQuery", "@os='iOS' and @name='iPhone 5s -- DEMO'");
          String Result = uploadApp();
          System.out.println(Result);
          capabilities.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank");
          
          driver = new IOSDriver<MobileElement>(
                        new URL("https://vfdevicecloud.vodafone.com:443/wd/hub"), capabilities);
          
    }
    
    @Test
    public void usingAppiumdesktop(){
          
                
          MobileElement el13 = (MobileElement) driver.findElementByXPath("//*[@placeholder='Username']");
          el13.click();
          el13.sendKeys("company");
          MobileElement el14 = (MobileElement) driver.findElementByXPath("//*[@placeholder='Password']");
          el14.click();
          el14.sendKeys("company");
          MobileElement el15 = (MobileElement) driver.findElementByXPath("//*[@text='loginButton']");
          el15.click();
    
          MobileElement el16 = (MobileElement) driver.findElementByXPath("//*[@text='logoutButton']");
          el16.click();

          
    }
    

    @AfterClass
    public static void close() {
          driver.quit();
    }

    
    
    
    public static String uploadApp() throws Throwable {
          URL url = new URL("https://vfdevicecloud.vodafone.com/"+"api/v1/applications/new?project=default");
          HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
          urlConnection.setRequestMethod("POST");
          urlConnection.setDoOutput(true);
          urlConnection.setRequestProperty("Authorization", "Bearer " + AccessKey);
          urlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
          urlConnection.setRequestProperty("Accept", "application/json");
          
          
          
          DataOutputStream request = new DataOutputStream(urlConnection.getOutputStream());

          request.writeBytes("--" + boundary + "\r\n");
          
          request.writeBytes("Content-Disposition: form-data; name=\"description\"\r\n\r\n");
          request.writeBytes("new app" + "\r\n");
          

          request.writeBytes("--" + boundary + "\r\n");
          request.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + f.getName() + "\"\r\n\r\n");
          request.write(FileUtils.readFileToByteArray(f));
          request.writeBytes("\r\n");

          request.writeBytes("--" + boundary + "--\r\n");

          int responseCode = urlConnection.getResponseCode();
          System.out.println("\nSending 'POST' request to URL : " + url);
          System.out.println("Sending Query : " + "");
          System.out.println("Response Code : " + responseCode);
          InputStream stream = null;

          if (urlConnection.getResponseCode() >= 400) {
                 stream = urlConnection.getErrorStream();

          } else {
                 stream = urlConnection.getInputStream();
          }

          BufferedReader in = new BufferedReader(new InputStreamReader(stream));
          String inputLine;
          StringBuffer responseBuffer = new StringBuffer();

          while ((inputLine = in.readLine()) != null) {
                 responseBuffer.append(inputLine);
          }
          in.close();
    
          String result = responseBuffer.toString();
          return result;
    }
	

}
