package com.maieutike.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.maieutike.components.Category;
import com.maieutike.components.Question;
import com.maieutike.components.Quiz;
import com.maieutike.handlers.Prompter;

public class QuizPerformer {
	private boolean shuffleQuestions;
	private boolean shuffleCategories;
	private static Prompter prompt= new Prompter(System.in);	
	
	
	public boolean isShuffleQuestions() {
		return shuffleQuestions;
	}
	public void setShuffleQuestions(boolean shuffleQuestions) {
		this.shuffleQuestions = shuffleQuestions;
	}
	public boolean isShuffleCategories() {
		return shuffleCategories;
	}
	public void setShuffleCategories(boolean shuffleCategories) {
		this.shuffleCategories = shuffleCategories;
	}

	
	public static void performQuiz(Quiz quiz) {
		quiz=filterQuizCategories(quiz);
		String answer="";	
		boolean broken=false;
		for(Category category:quiz.getCategories()) {			
			System.out.println(category.getName());			
			for(Question question:category.getQuestions()) {
				answer=prompt.promptString("\n"+question.getQuestion());
				if (answer.equalsIgnoreCase("exit")) {
					broken=true;
	                break;
	            }
				System.out.println("\nCORRECT ANSWER:\n");
				System.out.println(question.getAnswer());				
				String correctness=prompt.promptString("Was your answer correct? y/n");				
				if(correctness.equalsIgnoreCase("y")) {
					question.setCorrect(true);
				}
			}
			if (broken) {
                break;
            }
		}
		if(!broken) {
			System.out.println("Results: "+getResults(quiz));
			int incorrectAmount=quiz.getMistakesAmount();
			if(incorrectAmount>0) {					
				String userInput = prompt.promptString("Do you want to redo the test with only wrong ansered questions?");				
				if (userInput.equals("y")) {
		        	redoQuiz(quiz);
		        }
			}
		}
	}
	
	public static Quiz filterQuizCategories(Quiz quiz) {		
		Quiz filteredQuiz=new Quiz(quiz.getName());
		for(Category category:quiz.getCategories())	{
			
			String userInput = prompt.promptString("Include category: "+category.getName()+"?\n"
					+ "Yes/No \t \t \t \t y/n. \n"
					+ "Include all \t \t \t 'a' \n"
					+ "Only the already selected \t 'o'\n");
			if(userInput.equalsIgnoreCase("y")) {
				filteredQuiz.addCategory(category);
			}
			else if(userInput.equalsIgnoreCase("a")) {
				return quiz;
			}
			else if(userInput.equalsIgnoreCase("o")) {
				return filteredQuiz;
			}
			
		}
		return filteredQuiz;
	}
	public static String getResults(Quiz quiz) {		
		String categoryCode="";			
		for(Category category:quiz.getCategories()) {
			String [] catNameWords=category.getName().split(" ");
			for(String word:catNameWords) {
				if(word.length()>1) {
					categoryCode+=word.substring(0, 1);
				}				
			}
			//
			Long correctResults=category.getQuestions().stream().filter(Question::isCorrect).count();
			Integer totalAmount=category.getQuestions().size();
			categoryCode+=correctResults.toString()+"/"+totalAmount.toString()+":";
		}
		if(categoryCode.length()>1) {
			categoryCode.substring(0,categoryCode.length()-1);
		}
		
		return categoryCode.toString();
		
	}
	public static void redoQuiz(Quiz quiz) {
		Quiz modifiedQuiz = new Quiz(quiz.getName());  // Create a new Quiz object with the same name

		for (Category category : quiz.getCategories()) {
		    Category modifiedCategory = new Category(category.getName());  // Create a new Category object with the same name
		    
		    for (Question question : category.getQuestions()) {
		        if (!question.isCorrect()) {  // Filter out questions with incorrect answers
		            Question modifiedQuestion = new Question(question.getQuestion());
		            modifiedQuestion.setAnswer(question.getAnswer());
		            
		            modifiedCategory.addQuestion(modifiedQuestion);  // Add the modified question to the modified category
		        }
		    }
		    
		    if (!modifiedCategory.getQuestions().isEmpty()) {
		        modifiedQuiz.addCategory(modifiedCategory);  // Add the modified category to the modified quiz if it has questions
		    }
		}
		performQuiz(modifiedQuiz);
	}
	/*public static void logResults() {
		Logger logger = Logger.getLogger(QuizPerformer.class.getName());

	    try {
	        // Create a FileHandler to log to a file named "quiz_results.log"
	        FileHandler fileHandler = new FileHandler("quiz_results.log");

	        // Set the log level (optional)
	        logger.setLevel(Level.INFO);

	        // Configure the formatter for the log messages
	        SimpleFormatter formatter = new SimpleFormatter();
	        fileHandler.setFormatter(formatter);

	        // Add the FileHandler to the Logger
	        logger.addHandler(fileHandler);

	        for (Category category : quiz.getCategories()) {
	            // ...

	            for (Question question : category.getQuestions()) {
	                // ...

	                // Log the question, answer, and correctness
	                logger.info("Question: " + question.getQuestion());
	                logger.info("Answer: " + question.getAnswer());
	                logger.info("Correctness: " + question.isCorrect());
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}*/
	
}
