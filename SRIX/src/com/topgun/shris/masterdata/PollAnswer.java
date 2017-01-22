package com.topgun.shris.masterdata;

public class PollAnswer 
{
	String answer;
	String answer_th;
	int idAnswer;
    int idEmp;
    int idQuestion;
    int score;
    boolean exists = false;
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getAnswer_th() {
		return answer_th;
	}
	public void setAnswer_th(String answer_th) {
		this.answer_th = answer_th;
	}
	public int getIdAnswer() {
		return idAnswer;
	}
	public void setIdAnswer(int idAnswer) {
		this.idAnswer = idAnswer;
	}
	public int getIdEmp() {
		return idEmp;
	}
	public void setIdEmp(int idEmp) {
		this.idEmp = idEmp;
	}
	public int getIdQuestion() {
		return idQuestion;
	}
	public void setIdQuestion(int idQuestion) {
		this.idQuestion = idQuestion;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public boolean isExists() {
		return exists;
	}
	public void setExists(boolean exists) {
		this.exists = exists;
	}
}
