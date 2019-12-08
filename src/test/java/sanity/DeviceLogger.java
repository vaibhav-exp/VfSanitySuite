package test.java.sanity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class DeviceLogger {

    private String mainFolderPath;
    private String currentFilePath;
    private String allMeasurementsFilePath;
    private String deviceName;

    private ArrayList<String> data = new ArrayList<String>();

    public DeviceLogger(String path, String deviceName) {
	super();
	String fileName = "perf_" + deviceName.replaceAll("[-: ]", "_").toLowerCase() + ".csv";
	fileName = fileName.replaceAll("___", "_").replaceAll("__", "_");
	this.mainFolderPath = Paths.get(path).toString();
	this.currentFilePath = Paths.get(path, fileName).toString();
	this.allMeasurementsFilePath = Paths.get(path, "performance_measurements_folder_" + Paths.get(path).getFileName() + ".csv").toString();
	System.out.println(path);
	this.deviceName = deviceName;
    }

    public void addEntry(String context, String action, double duration) {
	System.out.println("['" + action + "' duration: "+ duration + " seconds]"); 
	String info = ZonedDateTime.now().toString();
	info += "," + duration;
	info += "," + deviceName.toLowerCase();
	info += "," + action.toLowerCase();
	info += "," + context.toLowerCase();
	data.add(info);
    };

    public void saveData() throws IOException {
	FileWriter fw = new FileWriter(currentFilePath, true);
	BufferedWriter bw = new BufferedWriter(fw);
	for (String info: data) {
	    bw.write(info);
	    bw.write("\n");
	}
	bw.close();
	data.clear();
    }
    
    // Catching exceptions in this case 
    //  if more tests run in parallel there might be issues when 2 of the try to access it
    
    public void mergeExistingData()  {

	Path output = Paths.get(allMeasurementsFilePath);	
	
	try {
	    Files.deleteIfExists(output);
	    Files.createFile(output);
	} catch (Exception e) {
	    System.out.println("Merge perf data info - ERROR when resetting the file contents: "  + e.toString());
	    return;
	}
	
	Path directory = Paths.get(mainFolderPath);
	File[] files = directory.toFile().listFiles();
	for (File file : files) {
	    if (file.getName().startsWith("perf_")) {
		try {
		    Files.write(output, Files.readAllBytes(file.toPath()), StandardOpenOption.APPEND);
		} catch (Exception e) {
		    System.out.println("Merge perf data info - ERROR when merging " + file.getName() + ": " + e.toString());
		}
	
	    }
	}

	
	
    }
}
