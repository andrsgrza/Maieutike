package com.maieutike.components;

public class Question {
	private String question;
    private String answer;
    private boolean isCorrect;
    
    public Question(Question que) {
        this.question = que.getQuestion();
        this.answer = que.getAnswer();
        this.isCorrect = que.isCorrect;
    }
    public Question() {
    	
    }
    
    public Question(String question) {
    	this.question=question;
    }

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	public void setNull() {
		this.question=null;
		this.answer=null;
	}
	
	public void appendQuestion(String question) {
		if(this.question == null) {
			this.question=question;			
		}else {
			this.question="\n"+question;
		}
	}
	
	public void appendAnswer(String answer) {
		if(this.answer == null) {
			this.answer=answer;			
		}else {
			this.answer+="\n"+answer;
		}
	}
    
}
