package com.maieutike.components;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private List<Question> questions;
    
    public Category() {
    	name=null;
    	questions = new ArrayList<Question>();
    }
    
    public Category(Category cat) {
        this.name = cat.getName();
        this.questions = cat.getQuestions();
    }
    
    public Category(String name) {
    	this.name=name;
    	this.questions=new ArrayList<Question>();
    }

    public String getName() {
        return name;
    }

    public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }
    
    public void setUnamed() {
    	this.name=null;    	
    }
        
    public boolean isUnamed() {
    	return this.name==null;
    }
    public void reset() {
    	this.name=null;
    	this.questions=new ArrayList<Question>();
    }
}
