package test.java.samples.reporter;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.apache.http.HttpHost;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class SeeTestReporterAPI {
    
    // Server data  
    private String serverName = null;
    private String accessKey = null;
    
    // Reporter API
    private String testFromProject = null;
    private String listTests;
    
    public SeeTestReporterAPI(String serverName, String accessKey) {
	this.accessKey = accessKey;
	this.serverName = serverName;
	initEndPointList();
    }
    
    public SeeTestReporterAPI(String serverName, String accessKey, String proxy) {
	this.accessKey = accessKey;
	this.serverName = serverName;
	Unirest.setProxy(new HttpHost(proxy, 8080));
	initEndPointList();
    }
    
    private void initEndPointList() {
	testFromProject = "https://" + serverName +"/reporter/api/tests/{id}/project/{projectName}/";
	listTests = "https://" + serverName +"/reporter/api/tests/list";
    }

    private String requestGet(String endpoint) throws Exception {
	HttpResponse<String> response = Unirest.get(endpoint).header("authorization", "Bearer " + accessKey)
		.header("content-type", "application/json").asString();
	if (response.getStatus() != 200) {
	    throw new Exception("HTTP response not valid - " + response.getStatus());
	}
	return response.getBody().toString();
    }

    private String requestPOST(String endpoint, String body) throws Exception {
	HttpResponse<String> response = Unirest.post(endpoint)
		.header("authorization", "Bearer " + accessKey)
		.header("content-type", "application/json")
		.body(body)
		.asString();
	if (response.getStatus() != 200) {
	    throw new Exception("HTTP response not valid - " + response.getStatus());
	}
	
	return response.getBody().toString();
    }
    

    private JsonArray getJSONArray(String result) {
	JsonReader jsonReader = Json.createReader(new StringReader(result));
	JsonObject jobj = jsonReader.readObject();
	JsonArray jsonResults = jobj.getJsonArray("data");
	return jsonResults;
    }
      
    public JsonObject getTest(String id, String projectName) throws Exception {
	System.out.println("Getting test info");
	String endPoint = testFromProject.replace("{id}", id).replace("{projectName}", projectName);
	String result = requestGet(endPoint);
	JsonReader jsonReader = Json.createReader(new StringReader(result));
	return jsonReader.readObject();
    }
    
    public JsonArray getTodaysTests() throws Exception {
   	System.out.println("Getting tests information - today only");
   	
   	// Get date
   	LocalDate now = LocalDate.now();
   	String month = now.getMonthValue() < 10 ? "0" + now.getMonthValue() : String.valueOf(now.getMonthValue());
   	String day = now.getDayOfMonth() < 10 ? "0" + now.getDayOfMonth() : String.valueOf(now.getDayOfMonth());
   	String today = now.getYear() + "-" + month + "-" + day;
   	
   	// Request body
   	String body = 
   		"{\"returnTotalCount\": true,"
   	       + "\"sort\":[{\"property\":\"test_id\",\"descending\":true}],\r\n" 
               + "\"filter\":[{\"property\":\"date\",\"operator\":\"=\",\"value\":\""+today+"\"}]}";

      	String result = requestPOST(this.listTests, body);
      	return getJSONArray(result);
    }
    

    public ArrayList<String> getTodaysTestNames() throws Exception {
   	ArrayList<String> names = new ArrayList<String>();	
         	JsonArray resultArray = getTodaysTests();
         	for (int i = 0; i < resultArray.size(); i++) {
         	names.add(resultArray.getJsonObject(i).getString("name"));  
         	}
         	return names;
       }
       
    
}
