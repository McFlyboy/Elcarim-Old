package com.nyhammer.p96.util.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.nyhammer.p96.ErrorHandler;
import com.nyhammer.p96.ui.GameWindow;

/**
 * 
 * @author McFlyboy
 * 
 * @since 0.2.2a
 *
 */
public class TextIO {
	public static final int FILE = 0;
	public static final int FILE_LINEWISE = 1;
	public static final int RESOURCE = 2;
	private File rawTextFile;
	private BufferedReader linewiseReader;
	private String rawTextResourcePath;
	public TextIO(int sourceType, String path) {
		if(sourceType == FILE || sourceType == FILE_LINEWISE) {
			rawTextFile = new File(path);
			if(!rawTextFile.exists()) {
				try {
					rawTextFile.createNewFile();
				}
				catch(IOException e) {
					ErrorHandler.printError("Could not create the file: " + path, true);
					ErrorHandler.printError(e);
					GameWindow.close();
				}
			}
			if(sourceType == FILE_LINEWISE) {
				try {
					linewiseReader = new BufferedReader(new FileReader(rawTextFile));
				}
				catch(FileNotFoundException e) {
					ErrorHandler.printError("Could not find the file: " + rawTextFile.getPath(), true);
					ErrorHandler.printError(e);
					GameWindow.close();
				}
			}
		}
		else if(sourceType == RESOURCE) {
			rawTextResourcePath = path;
		}
		else {
			return;
		}
	}
	public String readFileLinewise() {
		if(rawTextFile == null) {
			return "No file specified!";
		}
		if(linewiseReader == null) {
			return "No stream specified!";
		}
		String line = null;
		try {
			line = linewiseReader.readLine();
		}
		catch(IOException e) {
			ErrorHandler.printError("Could not read the file: " + rawTextFile.getPath(), true);
			ErrorHandler.printError(e);
			GameWindow.close();
		}
		return line;
	}
	public String readFileWhole() {
		if(rawTextFile == null) {
			return "No file specified!";
		}
		if(linewiseReader != null) {
			return "Please read the file linewise!";
		}
		StringBuilder content = new StringBuilder();
		try(BufferedReader reader = new BufferedReader(new FileReader(rawTextFile))) {
			String line;
			while((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
		}
		catch(FileNotFoundException e) {
			ErrorHandler.printError("Could not find the file: " + rawTextFile.getPath(), true);
			ErrorHandler.printError(e);
			GameWindow.close();
		}
		catch(IOException e) {
			ErrorHandler.printError("Could not read the file: " + rawTextFile.getPath(), true);
			ErrorHandler.printError(e);
			GameWindow.close();
		}
		return content.toString();
	}
	public void writeFileWhole(String text) {
		if(rawTextFile == null) {
			return;
		}
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(rawTextFile))) {
			writer.write(text);
		}
		catch(IOException e) {
			ErrorHandler.printError("Could not write to file: " + rawTextFile.getPath(), true);
			ErrorHandler.printError(e);
			GameWindow.close();
		}
	}
	public String readTextResourceWhole() {
		if(rawTextResourcePath.equals("")) {
			return "No resource specified!";
		}
		StringBuilder content = new StringBuilder();
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(Class.class.getResourceAsStream(rawTextResourcePath)))) {
			String line;
			while((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
		}
		catch(FileNotFoundException e) {
			ErrorHandler.printError("Could not find the resource: " + rawTextResourcePath, true);
			ErrorHandler.printError(e);
			GameWindow.close();
		}
		catch(IOException e) {
			ErrorHandler.printError("Could not read the resource: " + rawTextResourcePath, true);
			ErrorHandler.printError(e);
			GameWindow.close();
		}
		return content.toString();
	}
	public void disposeLinewise() {
		if(linewiseReader == null) {
			return;
		}
		try {
			linewiseReader.close();
		}
		catch(IOException e) {
			ErrorHandler.printError("Could not close the file: " + rawTextFile.getPath(), true);
			ErrorHandler.printError(e);
			GameWindow.close();
		}
	}
}
