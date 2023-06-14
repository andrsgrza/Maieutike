package com.maieutike.components;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private String name;
    private List<Category> categories;

    public Quiz(String name) {
        this.name = name;
        this.categories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }
    public int getMistakesAmount() {
    	int amount=0;
    	for(Category category:categories) {
    		for(Question question:category.getQuestions()) {
    			if(!question.isCorrect()) {
    				amount++;
    			}
    		}
    	}
    	return amount;
    }
}

