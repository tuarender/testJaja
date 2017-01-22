package com.topgun.shris.masterdata;

public class PollQuestion 
{
	int idQuestion;
	String question;
	int idLanguage;
	boolean exists = false;
	public int getIdQuestion() {
		return idQuestion;
	}
	public void setIdQuestion(int idQuestion) {
		this.idQuestion = idQuestion;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public int getIdLanguage() {
		return idLanguage;
	}
	public void setIdLanguage(int idLanguage) {
		this.idLanguage = idLanguage;
	}
	public boolean isExists() {
		return exists;
	}
	public void setExists(boolean exists) {
		this.exists = exists;
	}
}
