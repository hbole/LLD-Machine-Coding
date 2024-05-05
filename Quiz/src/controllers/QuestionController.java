package controllers;

import dto.QuestionRequestDTO;
import models.Question;
import services.QuestionService;

import java.util.List;

public class QuestionController {
    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    public void addQuestion(QuestionRequestDTO questionRequest) {
        Question question = questionService.addQuestion(
                questionRequest.getQuestion(),
                questionRequest.getAnswerOptions(),
                questionRequest.getAnswer()
        );
    }

    public List<Question> displayQuestions() {
        return  questionService.displayQuestions();
    }
}
