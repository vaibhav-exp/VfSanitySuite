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
import io.appium.java_client.remote.MobileCapabilityType;
import test.java.sanity.Base;

public class BasicAppiumAndroidTestActionsIT  {

	 public static AndroidDriver<MobileElement> driver = null;
     static String application_path="C:\\Ramya\\CORE\\Seetest_12.1_SanityTest\\EriBank.apk";
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
           capabilities.setCapability("platformName", "Android");
           capabilities.setCapability("accessKey", AccessKey);
           capabilities.setCapability("deviceQuery", "@os='android' and @name='samsung SM-G920F -- GigaTV'");
           String Result = uploadApp();
           System.out.println(Result);
           capabilities.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBank/.LoginActivity");
           driver = new AndroidDriver<MobileElement>(
                         new URL("https://vfdevicecloud.vodafone.com:443/wd/hub"), capabilities);
           
     }
     
     @Test
     public void usingAppiumdesktop(){
           
           MobileElement el1 = (MobileElement) driver.findElementById("com.experitest.ExperiBank:id/usernameTextField");
           el1.click();
           el1.sendKeys("company");
           MobileElement el2 = (MobileElement) driver.findElementById("com.experitest.ExperiBank:id/passwordTextField");
           el2.click();
           el2.sendKeys("company");
           MobileElement el3 = (MobileElement) driver.findElementById("com.experitest.ExperiBank:id/loginButton");
           el3.click();
     
           MobileElement el12 = (MobileElement) driver.findElementById("com.experitest.ExperiBank:id/logoutButton");
           el12.click();

           
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
