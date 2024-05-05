import controllers.QuestionController;
import exceptions.InvalidAnswerOptionsException;
import models.AnswerOption;
import models.Question;
import repositories.AnswerOptionRepository;
import repositories.QuestionRepository;
import services.QuestionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InvalidAnswerOptionsException {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        Map<Long, AnswerOption> answerOptions = new HashMap<>();
        Map<Long, Question> questionMap = new HashMap<>();

        AnswerOptionRepository answerOptionRepository = new AnswerOptionRepository(answerOptions);
        QuestionRepository questionRepository = new QuestionRepository(questionMap);
        QuestionService questionService = new QuestionService(answerOptionRepository, questionRepository);
        QuestionController questionController = new QuestionController(questionService);

        //PlayQuiz.
        questionController.playQuiz();
    }
}