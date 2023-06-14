package com.maieutike.handlers;

import java.io.File;

import com.maieutike.exeptions.*;


public class Validator {
	
	public static void validateDirectory(File folder) throws NotDirectoryException {
		if(!folder.isDirectory()) {
			throw new NotDirectoryException(folder.getAbsolutePath() + "is not a directory");    		            
    	}
	}
	
	public static void validateFile(File[] files) throws InvalidFileException {
		if (files == null || files.length== 0) {
            throw new InvalidFileException("There is no text file in the provided directory");
        }
	}
	public static void validateNumberList(String [] numbers) throws InvalidInputException {		
		for(String number:numbers) {
			if(!number.matches("[0-9]+")) {
				throw new InvalidInputException("Input is incorrect, list should be numbers separated by spaces");
			}
		}
	}
}
