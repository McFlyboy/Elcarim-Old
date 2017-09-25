package com.nyhammer.p96;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ErrorHandler{
	private static File logFile;
	private static PrintStream stream;
	public static PrintStream getStream(){
		return stream;
	}
	public static void init(){
		logFile = new File("Errorlog.txt");
		if(!logFile.exists()){
			try{
				logFile.createNewFile();
			}
			catch(IOException e){
				System.err.println("Failed to create the logfile!!!");
				e.printStackTrace();
				System.exit(1);
			}
		}
		try{
			stream = new PrintStream(logFile);
		}
		catch(FileNotFoundException e){
			System.err.println("Failed to locate the logfile!!!");
			e.printStackTrace();
			System.exit(1);
		}
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e){
			printError("Could not set the proper Look-and-feel!", true);
			printError(e);
		}
	}
	public static void printError(String error, boolean shouldPopUp){
		System.err.println(error);
		stream.println(error);
		if(shouldPopUp){
			JOptionPane.showMessageDialog(null, error + "\nCheck logfile for more info");
		}
	}
	public static void printError(String error){
		printError(error, false);
	}
	public static void printError(Exception e){
		e.printStackTrace(stream);
		e.printStackTrace();
	}
	public static void terminate(){
		stream.flush();
		stream.close();
	}
}