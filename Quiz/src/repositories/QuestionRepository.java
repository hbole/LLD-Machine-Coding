package repositories;

import models.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionRepository {
    private Map<Long, Question> questions;
    private Long id;

    public QuestionRepository(Map<Long, Question> questions) {
        this.questions = questions;
    }

    public Question saveQuestion(Question question) {
        id += 1;
        question.setId(id);

        questions.put(id, question);
        return question;
    }

    public List<Question> getQuestions() {
        List<Question> allQuestions = new ArrayList<>();

        for(Question question : questions.values()) {
            allQuestions.add(question);
        }

        return allQuestions;
    }
}
