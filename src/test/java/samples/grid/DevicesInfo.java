package test.java.samples.grid;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.apache.http.HttpHost;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;


public class DevicesInfo {

   public static void displayAllDevicesInfo(String serverName, String accesKey) throws Exception {
	System.out.println("Information about devices available in the current project:");
	JsonArray jsonResults = getAllDevicesJSONList(serverName, accesKey);
	ArrayList<String> list = new ArrayList<String>();
	for (JsonObject res : jsonResults.getValuesAs(JsonObject.class)) {     
	    String deviceName = res.getString("deviceName");
	    String deviceStatus = res.getString("displayStatus").toLowerCase();
	    list.add(deviceName + " - " + deviceStatus);
	}
	Collections.sort(list);
	int idx = 0;
	for (String item: list) {
	    System.out.println((++idx) + " - " + item);
	}
    }
    
    public static boolean isAvailable(String deviceName, String serverName, String accesKey) throws Exception {
	JsonArray jsonResults = getAllDevicesJSONList(serverName, accesKey);	
	for (JsonObject res : jsonResults.getValuesAs(JsonObject.class)) {     
	    String name = res.getString("deviceName");
	    String status = res.getString("displayStatus").toLowerCase();   
	    if (name.equalsIgnoreCase(deviceName)) {
		// Case - found & available
		if (status.equals("available")) {
		    System.out.println("Device '"+ deviceName + "' is available.");
		    return true;
		} else {
		    // Case - found & in use
		    if (status.equals("in use")) {
			String currentUser = res.getString("currentUser"); 
			System.out.println("Device '"+ deviceName + "' is used by " + currentUser + ".");
			return false;
		    }
		    System.out.println("Device '"+ deviceName + "' is " + status + ".");
		    return false;
		}
	    }
	} 
	// Case - not found at all
	throw new Exception("Device '"+ deviceName + "' not found!");
    }
    
    private static JsonArray getAllDevicesJSONList(String serverName, String accesKey) throws Exception {
	String endpoint = "https://" + serverName +"/api/v1/devices";	
	Unirest.setProxy(new HttpHost(Data.proxyHost ,8080));
	// Get response
	HttpResponse<String> response = Unirest.get(endpoint).header("authorization", "Bearer " + accesKey).asString();
	if (response.getStatus() != 200) {
	    throw new Exception("HTTP response not valid - " + response.getStatus());
	};
	// Process response
	JsonReader jsonReader = Json.createReader(new StringReader(response.getBody()));
	JsonObject jobj = jsonReader.readObject();
	return jobj.getJsonArray("data");
    }

 
}
