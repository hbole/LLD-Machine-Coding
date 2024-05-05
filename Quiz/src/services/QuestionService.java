package services;

import models.AnswerOption;
import models.Question;
import repositories.AnswerOptionRepository;
import repositories.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionService {
    private AnswerOptionRepository answerOptionRepository;
    private QuestionRepository questionRepository;

    public QuestionService(AnswerOptionRepository answerOptionRepository, QuestionRepository questionRepository) {
        this.answerOptionRepository = answerOptionRepository;
        this.questionRepository = questionRepository;
    }

    public Question addQuestion(String questionText, List<String> answerOptions, String answer) {
        //1. Create answer options
        List<AnswerOption> answers = new ArrayList<>();
        AnswerOption finalAnswer = null;

        for(String answerOption : answerOptions) {
            AnswerOption finalAnswerOption;
            Optional<AnswerOption>  optionalAnswerOption = answerOptionRepository.findAnswerByText(answerOption);

            if(optionalAnswerOption.isEmpty()) {
                finalAnswerOption = answerOptionRepository.saveAnswerOption(answerOption);
            } else {
                finalAnswerOption = optionalAnswerOption.get();
            }

            if(finalAnswerOption.getOption().equals(answer)) {
                finalAnswer = finalAnswerOption;
            }
            answers.add(finalAnswerOption);
        }

        if(finalAnswer == null) {
            finalAnswer = answerOptionRepository.saveAnswerOption(answer);
        }

        //2. Create question object
        Question question = new Question(questionText, answers, finalAnswer);

        //3. Save the question
        question = questionRepository.saveQuestion(question);

        //4. Return question object;
        return question;
    }

    public List<Question> getAllQuestions() {
        List<Question> questions = questionRepository.getQuestions();
        return questions;
    }
}
