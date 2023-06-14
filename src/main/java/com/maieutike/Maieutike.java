package com.maieutike;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

import com.maieutike.components.*;
import com.maieutike.exeptions.InvalidFileException;
import com.maieutike.exeptions.InvalidInputException;
import com.maieutike.exeptions.NotDirectoryException;
import com.maieutike.handlers.Prompter;
import com.maieutike.handlers.QuizReader;
import com.maieutike.service.QuizPerformer;

import com.maieutike.handlers.Validator;

public class Maieutike {	
	private static final Logger logger = LogManager.getLogger(Maieutike.class);
    
    public static void main(String[] args) throws NotDirectoryException, InvalidFileException {    	
    	Prompter prompt = new Prompter(System.in);    	 
    	String folderPath;
    	folderPath = "C:\\Users\\andre\\OneDrive\\Documentos\\Quizes";    	
    	//folderPath = "C:\\Users\\andre\\OneDrive\\Documentos\\Quizes\\"+args[1]+".txt";
    	      	
       	//String TEST_FOLDER = "C:\\Users\\andre\\OneDrive\\Documentos\\Quizes\\Capital Markets.txt";
        File folder = new File(folderPath);
        Validator.validateDirectory(folder);
    	
    	File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
    	Validator.validateFile(files);
    	List<File> selectedFiles=new ArrayList<File>();
    	//List<File> selectedFiles = prompt.promptList("Do you want to use the questionnaire from file: ", files);
    	try {
			selectedFiles = prompt.bigPrompt("Enter, separated by comas, the numbers of selected files", files);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(!selectedFiles.isEmpty()) {
    		logger.info("Session started");
    		startTraining(selectedFiles);
    	}else {
    		System.out.println("No files selected, exit finishing program");
    	}
    	
        }
    
    public static void startTraining(List<File> selectedFiles) {
    	 for (File file : selectedFiles) {            
             Quiz quiz = QuizReader.getQuiz(file);
             System.out.println("Quiz name:"+quiz.getName());
             QuizPerformer.performQuiz(quiz);                            	
         }
    }
        
    }


