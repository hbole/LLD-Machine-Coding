package models;

import java.util.List;

public class Question {
    private Long id;
    private String question;
    private List<AnswerOption> answerOptions;
    private AnswerOption answerOption;

    public Question(Long id, String question, List<AnswerOption> answerOptions, AnswerOption answerOption) {
        this.id = id;
        this.question = question;
        this.answerOptions = answerOptions;
        this.answerOption = answerOption;
    }

    public Question(String question, List<AnswerOption> answerOptions, AnswerOption answerOption) {
        this.question = question;
        this.answerOptions = answerOptions;
        this.answerOption = answerOption;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<AnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public AnswerOption getAnswer() {
        return answerOption;
    }

    public void setAnswer(AnswerOption answerOption) {
        this.answerOption = answerOption;
    }
}
