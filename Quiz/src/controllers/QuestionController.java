package controllers;

import dto.QuestionRequestDTO;
import exceptions.InvalidAnswerOptionsException;
import models.AnswerOption;
import models.Question;
import services.QuestionService;

import java.util.List;
import java.util.Scanner;

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

    public void playQuiz() throws InvalidAnswerOptionsException {
        Scanner sc = new Scanner(System.in);
        List<Question> questions = questionService.getAllQuestions();
        int quizScore = 0;

        for (Question question : questions) {
            int optionNumber = 1;
            List<AnswerOption> answerOptions = question.getAnswerOptions();
            AnswerOption correctAnswer = question.getAnswer();

            System.out.println(question.getQuestion());
            System.out.println("Choose the correct option number: ");
            for (AnswerOption answerOption : answerOptions) {
                String answer = answerOption.getOption();
                System.out.println(optionNumber + ". " + answer);
                optionNumber++;
            }

            int selectedAnswerOption = sc.nextInt();
            if(selectedAnswerOption < 1 && selectedAnswerOption > answerOptions.size()) {
                throw new InvalidAnswerOptionsException("Invalid Answer Option");
            }

            if(correctAnswer.getOption().equals(answerOptions.get(selectedAnswerOption).getOption())) {
                System.out.println("Hurray, Correct Answer!!");
                quizScore++;
            } else {
                System.out.println("OOPS, Wrong Answer!!");
            }
        }
        System.out.println("Quiz over!!");
        System.out.println("Your score is: " + quizScore);
    }
}
