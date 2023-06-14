package com.maieutike.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.maieutike.components.Category;
import com.maieutike.components.Question;
import com.maieutike.components.Quiz;

public class QuizReader {
	static List<Question> questionList;
	static BufferedReader bufferedReader;
	
	public static Quiz getQuiz(File file) {
		
		questionList= new ArrayList<>();
		Quiz quiz=null;
		Category actualCat = new Category();
		Question actualQues = new Question();
		
		//Trying to read the passed file
		try {
			bufferedReader = new BufferedReader(new FileReader(file));			
		} catch (FileNotFoundException e2) { 
			System.out.println("Could not read file");
			e2.printStackTrace();
		}
				
		String line;									
		try {
			quiz=new Quiz(extractTitle(bufferedReader.readLine()));
			
			while ((line = bufferedReader.readLine()) != null) {
				if(line.isEmpty()) {
					continue;
				}
				if (isSubtitle(line)) {
					if(actualQues.getAnswer() != null) {			    		
			    		actualCat.addQuestion(new Question(actualQues));
			    		actualQues.setNull();
			    	}
					if(!actualCat.isUnamed()) {						
						quiz.addCategory(new Category(actualCat));
						actualCat.reset();
					}					
					actualCat.setName(extractSubtitle(line));			    			    
			    } else if (isQuestion(line)) {
			    	if(actualQues.getAnswer() != null) {			    		
			    		actualCat.addQuestion(new Question(actualQues));
			    		actualQues.setNull();
			    	}
			    	actualQues.appendQuestion(extractQuestion(line));			    	//			    				    				       
			    } else if(isAnswer(line)) {			    	
			    	actualQues.appendAnswer(extractAnswer(line));
			    }			    
			}
			actualCat.addQuestion(actualQues);
			quiz.addCategory(actualCat);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File not found exception");
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("An unexpected exception occurred");
		}
		return quiz;
	}
	

    // Extract the title from a line
    private static String extractTitle(String line) {
        return line.trim();
    }

    // Check if a line is a subtitle
    private static boolean isSubtitle(String line) {
        return line.matches("^(?i)[IVXLCDM]+.*");
    }

    // Extract the subtitle from a line
    private static String extractSubtitle(String line) {
    	return line.trim();       
    }

    // Check if a line is a question
    private static boolean isQuestion(String line) {
        //return line.startsWith("*");
        return line.startsWith("?");
    }

    // Extract the question from a line
    private static String extractQuestion(String line) {
        return line.substring(1).trim();
    }

    // Check if a line is an answer
    private static boolean isAnswer(String line) {
        return line.startsWith("=");
    }

    // Extract the answer from a line
    private static String extractAnswer(String line) {
        return line.substring(1).trim();
    }
}

